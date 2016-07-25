package com.spark_web.domain;

import java.io.Serializable;

public class Result_Resv_Info implements Serializable {

	String parkingslot_id;
	String parkingfacility_id;
	String parkingslot_floor;
	String parkingslot_zone;
	String resv_starttime;
	String resv_authenticationnum;

	public String getParkingslot_id() {
		return parkingslot_id;
	}

	public void setParkingslot_id(String parkingslot_id) {
		this.parkingslot_id = parkingslot_id;
	}

	public String getParkingfacility_id() {
		return parkingfacility_id;
	}

	public void setParkingfacility_id(String parkingfacility_id) {
		this.parkingfacility_id = parkingfacility_id;
	}

	public String getParkingslot_floor() {
		return parkingslot_floor;
	}

	public void setParkingslot_floor(String parkingslot_floor) {
		this.parkingslot_floor = parkingslot_floor;
	}

	public String getParkingslot_zone() {
		return parkingslot_zone;
	}

	public void setParkingslot_zone(String parkingslot_zone) {
		this.parkingslot_zone = parkingslot_zone;
	}

	public String getResv_starttime() {
		return resv_starttime;
	}

	public void setResv_starttime(String resv_starttime) {
		this.resv_starttime = resv_starttime;
	}

	public String getResv_authenticationnum() {
		return resv_authenticationnum;
	}

	public void setResv_authenticationnum(String resv_authenticationnum) {
		this.resv_authenticationnum = resv_authenticationnum;
	}

}