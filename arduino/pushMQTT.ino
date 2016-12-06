//push MQTT 코드
void callback(char* topicCallback, byte* payload, unsigned int length) {
  Serial.print("Message arrived topic: [");
  Serial.print(topicCallback);  //topic이라고 옴
  Serial.print("] :");

  inPushMessage="";
  
  for (int i=0;i<length;i++) {
    inPushMessage+=(char)payload[i];
    //Serial.print((char)payload[i]); //hello arduino 라고 옴
  }

  //Serial.print(String("inPushMessage"+(String)inPushMessage));

  if(inPushMessage!=NULL){
    
    while( digitalRead(EntryBeamRcvr) == LOW){

      openEntryGate();  //입구문 열기
      EntryGateLED_Green_ON(); //입구 녹색 LED 켜기
    }
     
      delay(1000);  //1초 
      closeEntryGate();  //입구문 닫기
      EntryGateLED_OFF(); //입구 LED 끄기
      
    

      //EntryGateLED_Red_ON();  //입구 빨간 LED 켜기

    

    /*  입구 도착시에는 상태 저장 필요x
    PostData = makePostData("AUTH", StallSensorState,0); //모든 주차상태 PostData에 저장, 입구도착시에는 AUTH가 ID가 됨
    httpRequest(PostData);
    */
  }
  Serial.println();
}

//push MQTT 코드
void reconnect() {

  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("arduinoClient")) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      //client.publish("outTopic","hello world"); //아두이노가 퍼블리시 안해도될듯해서 일단주석
      // ... and resubscribe
      String _ParkingslotID = String("topic/" + (String)PARKINGLOT_NO + "-" + (String)PARKINGLOT_FLOOR + "-" + (String)PARKINGLOT_BLOCK);
      char _ParkingslotIDchar[512]={0,};
      _ParkingslotID.toCharArray(_ParkingslotIDchar, 512);
      
      client.subscribe(_ParkingslotIDchar);  //서버랑 일치시켜야 함. 현재는 주차장 NO-FLOOR-BLOCK 으로 하기로 함. OK => 7/24에 topic/ + 로 수정함
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }

}
