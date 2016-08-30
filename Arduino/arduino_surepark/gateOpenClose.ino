//입구문 열기
void openEntryGate(){ 
    //Serial.println("Entry beam broken");
    //Serial.println( "Open Entry Gate" );   //Here we open the entry gate
    EntryGateServo.write(Open); //입구 게이트 open
}

//입구문 닫기
void closeEntryGate(){
    //Serial.println("Entry beam is not broken.");
    //Serial.println( "Close Entry Gate" );   //Here we open the entry gate
    EntryGateServo.write(Close);  //입구 게이트 close
}

//출구문 열기
void openExitGate(){ 
    //Serial.println("Exit beam broken");
    //Serial.println( "Open Exit Gate" );    //Here we open the exit gate
    ExitGateServo.write(Open);  //출구 게이트 open
    //출구는 자동으로 문열림. 인증필요x
}

//출구문 닫기
void closeExitGate(){
    //Serial.println("Exit beam is not broken.");
    //Serial.println( "Close Exit Gate" );    //Here we open the exit gate
    ExitGateServo.write(Close); //출구 게이트 close
}

