package com.spark_web.dao;

import com.spark_web.domain.ParkingStaticalData;
import com.spark_web.domain.Result_Resv_Info;
import com.spark_web.domain.Resv;
import com.spark_web.domain.SlotUsage;

public interface ResvMapper {

	public int FindMaximumResvid();

	public void InsertResv(Resv resv);

	public Result_Resv_Info FindResv(String phonenum);

	public void UpdateCancelResv(String resv_authenticationnum);

	public Result_Resv_Info FindResvAuthenticationInfo(String resv_authenticationnum);
	
	public String FindResvAuthenticationNum(int parkingslot_seq);
	
	public void UpdateParkingSlotSeq(Resv otherreallocateuserresv);

	public void UpdateParkingStartTime(Resv resv);
	
	public void UpdateParkingStatetoMoving(Resv resv);
	
	public Resv FindParkingSlotStateMoving(String resv_cancel);
	
	public void UpdateParkingExitTime(Resv resv);

	public int FindStaricalParkingPeakTimeHour(ParkingStaticalData parkingstaticaldata);

	public int FindStaricalParkingPeakTimeDay(ParkingStaticalData parkingstaticaldata);

	public int FindStaricalParkingPeakTimeMonth(ParkingStaticalData parkingstaticaldata);

	public int FindStaricalParkingRevenueDay(ParkingStaticalData parkingstaticaldata);

	public int FindStaricalParkingRevenueMonth(ParkingStaticalData parkingstaticaldata);
	
	public int FindStaricalParkingSlotDayUsage(SlotUsage slotusage);
	
	public int FindStaricalParkingSlotMonthUsage(SlotUsage slotusage);

	public double FindStaricalParkingOccupancyHour(ParkingStaticalData parkingstaticaldata);

	public double FindStaricalParkingOccupancyDay(ParkingStaticalData parkingstaticaldata);

	public double FindStaricalParkingOccupancyMonth(ParkingStaticalData parkingstaticaldata);

	public String FindResvState(int parkingslot_seq);

}