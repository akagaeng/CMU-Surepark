package com.spark_web.domain;

public class ParkingSlot {

	int parkingslot_seq;
	int parkingslot_id;
	int parkingfacility_id;
	String parkingslot_floor;
	String parkingslot_zone;
	Boolean parkingslot_state;

	public int getParkingslot_seq() {
		return parkingslot_seq;
	}

	public void setParkingslot_seq(int parkingslot_seq) {
		this.parkingslot_seq = parkingslot_seq;
	}

	public int getParkingslot_id() {
		return parkingslot_id;
	}

	public void setParkingslot_id(int parkingslot_id) {
		this.parkingslot_id = parkingslot_id;
	}

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

	public Boolean getParkingslot_state() {
		return parkingslot_state;
	}

	public void setParkingslot_state(Boolean parkingslot_state) {
		this.parkingslot_state = parkingslot_state;
	}

}