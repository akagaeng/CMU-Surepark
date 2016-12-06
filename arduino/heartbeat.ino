void heartbeat(unsigned long _elapsedTime){
  //heartbeat. elapsed time 조건 보고 그 이상이면 무조건 httpRequest 전송
  if(_elapsedTime > (HEARTBEAT)){  //HEARTBEAT 기준시 지난 경우
    PostData = makePostData("HEARTBEAT", StallSensorState,0); //모든 주차상태 PostData에 저장, 출구도착시에는 EXIT가 ID가 됨

    lastConnectionTime = millis(); 
    Serial.println(String("Elapsed time: " + (String)_elapsedTime + " ms"));
    
    httpRequest(PostData);

  }
  else{}

}
