package com.spark_web.domain;

public class HeartbeatLog {

	int parkingfacility_id;
	String parkingslot_floor;
	String parkingslot_zone;
	String date;

	public int getParkingfacility_id() {
		return parkingfacility_id;
	}

	public void setParkingfacility_id(int parkingfacility_id) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
