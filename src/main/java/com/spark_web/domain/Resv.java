package com.spark_web.domain;

public class Resv {

	int resv_id;
	int parkingslot_seq;
	String resv_phonenum;
	String resv_creditnum;
	String resv_starttime;
	String resv_authenticationnum;
	String parking_starttime;
	String parking_exittime;
	String resv_cancel;
	int parking_price;

	public Resv() {
	}

	public int getResv_id() {
		return resv_id;
	}

	public void setResv_id(int resv_id) {
		this.resv_id = resv_id;
	}

	public int getParkingslot_seq() {
		return parkingslot_seq;
	}

	public void setParkingslot_seq(int parkingslot_seq) {
		this.parkingslot_seq = parkingslot_seq;
	}

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

	public String getResv_authenticationnum() {
		return resv_authenticationnum;
	}

	public void setResv_authenticationnum(String resv_authenticationnum) {
		this.resv_authenticationnum = resv_authenticationnum;
	}

	public String getParking_starttime() {
		return parking_starttime;
	}

	public void setParking_starttime(String parking_starttime) {
		this.parking_starttime = parking_starttime;
	}

	public String getParking_exittime() {
		return parking_exittime;
	}

	public void setParking_exittime(String parking_exittime) {
		this.parking_exittime = parking_exittime;
	}

	public String getResv_cancel() {
		return resv_cancel;
	}

	public void setResv_cancel(String resv_cancel) {
		this.resv_cancel = resv_cancel;
	}

	public int getParking_price() {
		return parking_price;
	}

	public void setParking_price(int parking_price) {
		this.parking_price = parking_price;
	}

}
