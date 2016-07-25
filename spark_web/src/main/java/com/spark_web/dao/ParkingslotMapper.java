package com.spark_web.dao;

import java.util.ArrayList;

import com.spark_web.domain.ParkingSlot;
import com.spark_web.domain.Result_Resv_Info;

public interface ParkingslotMapper {

	public ArrayList<ParkingSlot> FindAvailableParkingSlot();

	public void UpdateParkingSlotResvStatus(int parkingslot_seq);
	
	public void UpdateParkingSlotEmptyStatus(int parkingslot_seq);
	
	public int FindResvParkingSlotSeq(Result_Resv_Info result_resv);
	
	public int FindResvParkingSlotState(int parkingslot_seq);

	public ArrayList<ParkingSlot> FindParkingSlotInfo();
}