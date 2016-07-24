package com.spark_web.service;

import java.util.ArrayList;
import java.util.List;

import com.spark_web.dao.ParkingslotDAO;
import com.spark_web.dao.ResvDAO;
import com.spark_web.domain.Pair;
import com.spark_web.domain.ParkingSlot;
import com.spark_web.domain.ParkingStaticalData;
import com.spark_web.domain.SlotUsage;

public class StaticalDataService {

	public static ParkingslotDAO parkingslotdao = new ParkingslotDAO();
	public static ResvDAO resvdao = new ResvDAO();

	// ������ �󸶳� ���� ����ϴ°�
	public ArrayList<Pair<ParkingSlot, Integer>> ParkingSlotUsage(String year, String month, String day, String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<ParkingSlot, Integer>> ParkingSlotUsagelist = new ArrayList<>();
		List<ParkingSlot> parkingslotlist = parkingslotdao.FindParkingSlotInfo();
		SlotUsage slotusage = new SlotUsage();

		if (type.equals("h")) {
			// HourObj hour = new HourObj();
			// hour.setHour0(10);

		} else if (type.equals("d")) {
			for (int i = 0; i < parkingslotlist.size(); i++) {
				slotusage.setParkingslot_seq(parkingslotlist.get(i).getParkingslot_seq());
				slotusage.setDatetime(year + "-" + month + "-" + day);
				int usagecount = resvdao.FindStaricalParkingSlotDayUsage(slotusage);
				Pair<ParkingSlot, Integer> p = new Pair<>(parkingslotlist.get(i), usagecount);
				ParkingSlotUsagelist.add(i, p);
			}

		} else if (type.equals("m")) {
			for (int i = 0; i < parkingslotlist.size(); i++) {
				slotusage.setParkingslot_seq(parkingslotlist.get(i).getParkingslot_seq());
				slotusage.setDatetime(year + "-" + month + "-" + day);
				int usagecount = resvdao.FindStaricalParkingSlotMonthUsage(slotusage);
				Pair<ParkingSlot, Integer> p = new Pair<>(parkingslotlist.get(i), usagecount);
				ParkingSlotUsagelist.add(i, p);
			}
		}

		return ParkingSlotUsagelist;
	}

	// ���� ���� ���� ������ �Ǿ� �ִ°� �ð�(��,��)-Ƚ��
	public ArrayList<Pair<String, Integer>> ParkingPeakTime(int Parkingfacilityid, String year, String month, String day, String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<String, Integer>> ParkingPeakTimelist = new ArrayList<>();
		List<ParkingSlot> parkingslotlist = parkingslotdao.FindParkingSlotInfo();
		ParkingStaticalData parkingstaticaldata = new ParkingStaticalData();
		if (type.equals("h")) {
			for (int i = 0; i < 24; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setDatetime(year + "-" + month + "-" + day+" "+i+":00");
				int timecount = resvdao.FindStaricalParkingPeakTimeHour(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i+"hour", timecount);
				ParkingPeakTimelist.add(i, p);
			}
		} else if (type.equals("d")) {
			for (int i = 0; i < 31; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setDatetime(year + "-" + month + "-" + i);
				int timecount = resvdao.FindStaricalParkingPeakTimeDay(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i+1+"day", timecount);
				ParkingPeakTimelist.add(i, p);
			}

		} else if (type.equals("m")) {
			for (int i = 0; i < 12; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setDatetime(year + "-" + i);
				int timecount = resvdao.FindStaricalParkingPeakTimeMonth(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i+1+"month", timecount);
				ParkingPeakTimelist.add(i, p);
			}
		}

		return ParkingPeakTimelist;
	}

	// ���� ���� ���� ������ ���°�? ����
	public ArrayList<Pair<ParkingSlot, Integer>> ParkingRevenue(String year, String month, String day, String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<ParkingSlot, Integer>> ParkingSlotUsagelist = new ArrayList<>();
		List<ParkingSlot> parkingslotlist = parkingslotdao.FindParkingSlotInfo();
		SlotUsage slotusage = new SlotUsage();

		if (type.equals("h")) {

		} else if (type.equals("d")) {
			

		} else if (type.equals("m")) {
		
		}

		return ParkingSlotUsagelist;
	}

}
