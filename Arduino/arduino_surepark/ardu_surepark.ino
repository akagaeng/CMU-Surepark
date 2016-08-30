/*
surepark
CMU-C
- Actuators and Sensors w/o communications. 7/7 yang
- HTTP communication POST 7/16 yang
- PushMQTT 7/18 yang
- integration 7/18 yang
*/

#include <SPI.h>
#include <WiFi.h>
#include <Servo.h> 
#include <PubSubClient.h>

#define WEB_SERVER_PORT 4567
//variables according to the requirement
#define MAXSTALL  4 //최대 주차가능한 대수
#define PARKINGLOT_NO 1 //주차장 ID
#define PARKINGLOT_FLOOR 1 //주차장 층
#define PARKINGLOT_BLOCK 1 //주차장 블록
#define HEARTBEAT 300000 //600000  //heartbeat: ms 단위로 입력 600 000 (10분)

//Define pin numbers
//define Parking Stall LED
char ParkingStallLED[MAXSTALL] = {22,23,24,25}; //parking space 1 ~ MAXSTALL (Green)

//define Gate LED
#define EntryGateGreenLED 26 //Entry LED (Green Leg)
#define EntryGateRedLED   27 //Entry LED (Red Leg)
#define ExitGateGreenLED  28 //Exit LED (Green Leg)
#define ExitGateRedLED    29 //Exit LED (Red Leg)

//define Stall SeosorPin
char StallSensorPin[MAXSTALL] = {30,31,32,33};  //Parking Space 1 ~ MAXSTALL Car Detector

//define IR sensor(receiver)
#define EntryBeamRcvr  34 //Entry IR Receiver
#define ExitBeamRcvr   35 //Exit IR Receiver

//define Servo actuator
#define EntryGateServoPin 5 //Entry Gate Servo
#define ExitGateServoPin 6  //Exit Gate Servo

#define Open  90  //90 degree when opened
#define Close 0 //0 degree when closed

#define STALL_SENSOR_BROKEN_MAX 20  //주차장칸 차지했을때 체크할 최대 센서값(테스트를통해 수정)
                                     //칸마다 이 값을 달리하면 더 정확해질 것..

//Declare data types
Servo EntryGateServo; //입구쪽 서보
Servo ExitGateServo;  //출구쪽 서보

int EntryBeamState; //입구 IR센서 LOW: Beam Broken, HIGH: not broken
int ExitBeamState; //출구 IR센서  LOW: Beam Broken, HIGH: not broken

String inPushMessage = "";
//주차칸 센서 값
long StallSensorVal[MAXSTALL] = {0,};  //Parking Space 4 Car Detector sensor value

//주차칸 센서 상태_이전
boolean StallSensorState_prev[MAXSTALL] = {0,};  //Parking Space 4 Car Detector sensor state

//주차칸 센서 상태_현재
boolean StallSensorState[MAXSTALL] = {0,};  //Parking Space 4 Car Detector sensor state

//입구, 출구 게이트 상태_이전
boolean EntryGateState_prev = 0;  //EntryGate state: 도착하면 1
boolean ExitGateState_prev = 0;  //ExitGate state: 도착하면 1

//입구, 출구 게이트 상태_현재
boolean EntryGateState;  //EntryGate state: 도착하면 1
boolean ExitGateState;  //ExitGate state: 도착하면 1

//주차칸 센서
int StallSensorFlag[MAXSTALL] = {0,};

boolean EntryGateFlag;
boolean ExitGateFlag;

//heartbeat 계산용
unsigned long lastConnectionTime = 0;            // last time you connected to the server, in milliseconds
unsigned long elapsedTime = 0;

//server에 http(post)로 전송할 데이터
String PostData = ""; 

//통신 관련 선언
int status = WL_IDLE_STATUS;

char ssid[] = "LGArchi_Guest1"; //  network's SSID (name)
char pass[] = "16swarchitect";    // network's password (use for WPA)

IPAddress server(192,168,1,166);  // 명기 numeric IP  (웹서버)
IPAddress ip(192,168,1,182); // 경석 numeric IP  (아두이노)
IPAddress pushServer(192,168,1,166);  // 우진 numeric IP (푸시서버)

WiFiClient httpClient;  //web server
WiFiClient pushClient;  //push server

PubSubClient client(pushServer, 1883, callback, pushClient);

//===================================================loop start===================================================//

void setup() 
{
  // setup LED pins outputs.
  InitEntryExitLEDs();

  //initialize pinMode
  pinMode(EntryGateGreenLED, OUTPUT);
  pinMode(EntryGateRedLED, OUTPUT);
  pinMode(ExitGateGreenLED, OUTPUT);
  pinMode(ExitGateRedLED, OUTPUT);

  for(int i=0;i<MAXSTALL;i++){
     pinMode(ParkingStallLED[i], OUTPUT);
  }

  pinMode(EntryBeamRcvr, INPUT);     // Make entry IR rcvr an input
  pinMode(ExitBeamRcvr, INPUT);      // Make exit IR rcvr an input

  //initialize sensors, actuators

  EntryGateLED_OFF(); //입구 LED OFF
  ExitGateLED_OFF(); //출구 LED OFF

  ParkingStallLED_OFF(ParkingStallLED, 0); //ParkingStallLED 켜고끔: 0이면 전부다 끔

  closeEntryGate(); //입구 게이트 close  
  closeExitGate(); //출구 게이트 close

  // enable the built-in pullup
  digitalWrite(EntryBeamRcvr, HIGH); 
  digitalWrite(ExitBeamRcvr, HIGH);

  // Map servo to pin
  EntryGateServo.attach(EntryGateServoPin);
  ExitGateServo.attach(ExitGateServoPin);

  //Initialize serial and wait for port to open:
  Serial.begin(9600);

  // check for the presence of the shield:
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue:
    while (true);
  }

  String fv = WiFi.firmwareVersion(); //check firmware version
  if ( fv != "1.1.0" ){ Serial.println("Please upgrade the firmware"); }

  client.setServer(pushServer, 1883);
  client.setCallback(callback);

  // attempt to connect to Wifi network:
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network
    status = WiFi.begin(ssid, pass);

    // wait 5 seconds for connection:
    delay(5000);
  }
  Serial.println("Connected to wifi");
  printWifiStatus();
  lastConnectionTime = millis(); //초기 heart beat 기준시
}//end setup

//===================================================loop start===================================================//

void loop(){

  EntryBeamState = digitalRead(EntryBeamRcvr);  // Here we read the state of the entry beam.
  ExitBeamState = digitalRead(ExitBeamRcvr);  // Here we read the state of the exit beam.

  if(!client.connected()){  //push server와 연결끊기면 재연결, 처음 연결도 여기서 되는듯.
    reconnect();  
  }
  client.loop();

  if(EntryBeamState == LOW){
    EntryGateLED_Red_ON();
  }
  else{
    EntryGateLED_OFF();
  }


//주차장 슬롯 체크+LED
for(int i=0;i<MAXSTALL;i++){  //슬롯 비었는지 체크
    //StallSensorState[i] = 0;  //센서값 초기화
    //슬롯 1~MAXSTALL 센서값 출력
    StallSensorVal[i] = ProximityVal(StallSensorPin[i]); //Check parking spaces
    StallSensorState[i] = stallSensorStateCheck(StallSensorVal[i]); //주차 하는 경우 1, 주차에서 빼는 경우 2, 그 외 0 리턴
    StallSensorFlag[i] = stallSensorStateChangeCheck(StallSensorState_prev[i], StallSensorState[i]);  //주차상태 변화 있으면 1
                                                                                                      //StallSensorState_prev[i]의 초기값은 0으로 되어있음
}

/*    
    //출력용도
    for(int i=0;i<MAXSTALL;i++){  //stall값, stall상태, stall의 flag
      Serial.print(String("Stall" + (String)(i+1) + "= "));
      Serial.print(StallSensorVal[i]);
      Serial.print("  ");

      Serial.print(String("StallSensorState[" + (String)(i+1) + "]= "));
      Serial.print(StallSensorState[i]);
      Serial.print("  ");
      
      Serial.print(String("StallSensorFlag[" + (String)(i+1) + "]= "));
      Serial.println(StallSensorFlag[i]);
    }                                                                                                  
    Serial.println("========================================================");
*/

//inPushMessage = "xxx";

for(int i=0;i<MAXSTALL;i++){  //슬롯 비었는지 체크
    //주차장 슬롯1~MAXSTALL까지 LED체크
    if(StallSensorState[i] == 1){ //주차되어있으면 LED끔 
      ParkingStallLED_OFF(ParkingStallLED, i); //ParkingStallLED배열 주소, 핀번호(i)의 LED OFF시킴
    } 
    else{ //주차되어있으면 LED켬
      ParkingStallLED_ON(ParkingStallLED, i); //ParkingStallLED배열 주소, 핀번호(i)의 LED ON시킴
    }
}

for(int i=0;i<MAXSTALL;i++){  //슬롯 비었는지 체크
    //주차 상태 변경된 경우 전송. 주차 상태 변경은 한번에 하나만 가능하다고 가정
    if(StallSensorFlag[i] == 1){ //주차 하러 들어가는 경우 HTTP Request전송
      //PostData="";
      PostData = (String)makePostData(inPushMessage, StallSensorState,i+1); //상태 변경된것만 PostData에 저장, 주차상태만 변경시에는 MOVE가 ID가 됨 
      httpRequest(PostData);  //PostData 전송, 딜레이 포함되어 있음
    }
    else if(StallSensorFlag[i] == 2){ //주차에서 빼는 경우 HTTP Request전송 
      //PostData="";
      PostData = (String)makePostData("MOVE", StallSensorState,i+1); //상태 변경된것만 PostData에 저장, 주차상태만 변경시에는 MOVE가 ID가 됨 
      httpRequest(PostData);  //PostData 전송, 딜레이 포함되어 있음
    }    
    else{
      //주차 상태 변경되지 않은 경우      
    }
}

//센서 정보 체크한 이후에는 현재 정보를 prev 정보로 저장.
for(int j=0;j<MAXSTALL;j++){
  StallSensorState_prev[j] = StallSensorState[j];                                                                                                        
}
//주차장슬롯체크 끝

/*
//주차장 슬롯 상태 체크 출력
for(int i=0;i<MAXSTALL;i++){
  Serial.print(String("StallSensorState[" + (String)i + "]"));
  Serial.println(StallSensorState[i]);
}
*/
      
//입구 출입문+LED는 callback 내에서 처리함.

//출구 출입문+LED
  if (ExitBeamState == LOW){  // if ExitBeamState is LOW the beam is broken (출구 문 앞에 차량 도착한 경우)      
    openExitGate();  //출구문 열기
    ExitGateLED_Green_ON(); //출구 녹색 LED 켜기
    
    PostData = makePostData("PAYMENT", StallSensorState,0); //모든 주차상태 PostData에 저장, 출구도착시에는 EXIT가 ID가 됨
    httpRequest(PostData);
    ExitGateState=1;
  } 
  
  else {  //출구 문 앞에 차량 없음(차 지나간 경우)
    closeExitGate();  //출구문 닫기
    ExitGateLED_Red_ON(); //출구 빨간색 LED 켜기
    //이때는 전송x
    ExitGateState=0;
  }

  elapsedTime = millis() - lastConnectionTime;

  heartbeat(elapsedTime);
  //delay(2000);
} //end loop





