//입구 LED(빨/녹)켜기/끄기

void EntryGateLED_Red_ON(){ //입구 빨간색 LED 켜기
  //Serial.println( "Turn ON entry red LED" );
  digitalWrite(EntryGateGreenLED, HIGH); //녹색불 끔
  digitalWrite(EntryGateRedLED, LOW);  //빨간불 켬
}

void EntryGateLED_Green_ON(){ //입구 녹색 LED 켜기
  //Serial.println( "Turn ON entry green LED" );
  digitalWrite(EntryGateRedLED, HIGH);  //빨간불 끔
  digitalWrite(EntryGateGreenLED, LOW); //녹색불 켬
}

void EntryGateLED_OFF(){  //입구 LED 끄기(빨강/녹색 둘다 안나옴)
  //Serial.println( "Turn OFF entry green LED" );
  digitalWrite(EntryGateRedLED, HIGH);  //빨간불 끔
  digitalWrite(EntryGateGreenLED, HIGH); //녹색불 끔
}


//입구 LED(빨/녹)켜기/끄기

void ExitGateLED_Red_ON(){ //출구 빨간색 LED 켜기
  //Serial.println( "Turn ON Exit red LED" );
  digitalWrite(ExitGateGreenLED, HIGH); //녹색불 끔
  digitalWrite(ExitGateRedLED, LOW);  //빨간불 켬
}

void ExitGateLED_Green_ON(){ //출구 녹색 LED 켜기
  //Serial.println( "Turn ON Exit green LED" );
  digitalWrite(ExitGateRedLED, HIGH);  //빨간불 끔
  digitalWrite(ExitGateGreenLED, LOW); //녹색불 켬
}

void ExitGateLED_OFF(){  //출구 LED 끄기(빨강/녹색 둘다 안나옴)
  //Serial.println( "Turn OFF Exit green LED" );
  digitalWrite(ExitGateRedLED, HIGH);  //빨간불 끔
  digitalWrite(ExitGateGreenLED, HIGH); //녹색불 끔
}

//Parking Stall LED 끄기
void ParkingStallLED_OFF(char * _ParkingStallLED, int _parkingStallNo){ //ParkingStallLED배열 주소, 핀번호 입력받아서 LED ON시킴
  if(_parkingStallNo == 0){
    for(int i=0;i<MAXSTALL;i++){
      digitalWrite(_ParkingStallLED[i], LOW);
    }
  }
  else{
     digitalWrite(_ParkingStallLED[_parkingStallNo], LOW); 
  }
}

//Parking Stall LED 켜기
void ParkingStallLED_ON(char *_ParkingStallLED, int _parkingStallNo){ //ParkingStallLED배열 주소, 핀번호 입력받아서 LED OFF시킴
  if(_parkingStallNo == 0){
    for(int i=0;i<MAXSTALL;i++){
      digitalWrite(_ParkingStallLED[i], HIGH);
    }
  }
  else{
     digitalWrite(_ParkingStallLED[_parkingStallNo], HIGH); 
  }
}

