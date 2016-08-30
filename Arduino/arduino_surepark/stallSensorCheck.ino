boolean stallSensorStateCheck(long _StallSensorVal){ //센서값을 주차했는지 아닌지 체크해서 주차했으면 1을 리턴, 아니면 0 리턴
    boolean _StallSensorState=0;

    if(_StallSensorVal < STALL_SENSOR_BROKEN_MAX){ //주차된 경우: 15는 센서값을통한 판단을위한 값
      _StallSensorState = 1;
    }
    else{
      _StallSensorState = 0;
    }
    return _StallSensorState;
}

int stallSensorStateChangeCheck(boolean _StallSensorState_prev, boolean _StallSensorState){
    int _StallSensorFlag;
    
    if(_StallSensorState == 1 && _StallSensorState_prev == 0){//주차 하는 경우
      _StallSensorFlag = 1;
    }

    else if(_StallSensorState == 0 && _StallSensorState_prev == 1){//주차에서 빼는 경우
      _StallSensorFlag = 2;
    }

    else{
      _StallSensorFlag = 0;
    }
    return _StallSensorFlag;
}
