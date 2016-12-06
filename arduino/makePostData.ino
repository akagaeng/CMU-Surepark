//PostData String을 만드는 함수
String makePostData(String _authID, boolean *_StallSensorState, int _stallArr){  //_arrNo가 0인 경우는 모두다 전송
  String _PostData = "";

  if(_stallArr == 0){  //모든 주차장 정보 포함
    _PostData = "";
    _PostData+=String((String)_authID + ",");

    for(int i=0;i<MAXSTALL;i++){
      _PostData+=String((String)PARKINGLOT_NO + "-");
      _PostData+=String((String)PARKINGLOT_FLOOR + "-");
      _PostData+=String((String)PARKINGLOT_BLOCK + "-");
      _PostData+=String((String)(i+1) + ":" + (String)_StallSensorState[i] + ",");
    }
  }

  else{ //해당되는 주차장 정보만 포함
      _PostData = "";
      _PostData+=String((String)_authID + ",");
      _PostData+=String((String)PARKINGLOT_NO + "-");
      _PostData+=String((String)PARKINGLOT_FLOOR + "-");
      _PostData+=String((String)PARKINGLOT_BLOCK + "-");
      _PostData+=String((String)(_stallArr) + ":" + (String)_StallSensorState[_stallArr-1] + ",");
  }
  Serial.println(String("PostData: " + _PostData ));
  
  return _PostData;
}
