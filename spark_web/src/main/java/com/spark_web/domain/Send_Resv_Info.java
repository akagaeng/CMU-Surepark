package com.spark_web.domain;

import java.io.Serializable;

/**
 * Created by ajou on 2016-07-05.
 */
public class Send_Resv_Info implements Serializable {

    String resv_phonenum;
    String resv_creditnum;
    String resv_starttime;
    
    public String getResv_phonenum() {
        return resv_phonenum;
    }

    public void setResv_phonenum(String resv_phonenum) {
        this.resv_phonenum = resv_phonenum;
    }

    public String getResv_creditnum() {
        return resv_creditnum;
    }

    public void setResv_creditnum(String resv_creditnum) {
        this.resv_creditnum = resv_creditnum;
    }

    public String getResv_starttime() {
        return resv_starttime;
    }

    public void setResv_starttime(String resv_starttime) {
        this.resv_starttime = resv_starttime;
    }


}