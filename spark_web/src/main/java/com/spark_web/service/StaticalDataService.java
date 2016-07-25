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

	// 선택된 시간에 얼마나 주차되어 있는가 시간(날,월)-횟수
	public ArrayList<Pair<String, Integer>> ParkingPeakTime(int Parkingfacilityid, String year, String month,
			String day, String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<String, Integer>> ParkingPeakTimelist = new ArrayList<>();
		ParkingStaticalData parkingstaticaldata = new ParkingStaticalData();
		if (type.equals("h")) {
			for (int i = 0; i < 24; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(month);
				parkingstaticaldata.setDay(day);
				parkingstaticaldata.setHour(i+"");
				int timecount = resvdao.FindStaricalParkingPeakTimeHour(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i + "hour", timecount);
				ParkingPeakTimelist.add(i, p);
			}
		} else if (type.equals("d")) {
			for (int i = 0; i < 31; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(month);
				parkingstaticaldata.setDay(i+1+"");
				int timecount = resvdao.FindStaricalParkingPeakTimeDay(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i + 1 + "day", timecount);
				ParkingPeakTimelist.add(i, p);
			}

		} else if (type.equals("m")) {
			for (int i = 0; i < 12; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(i+1+"");
				int timecount = resvdao.FindStaricalParkingPeakTimeMonth(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i + 1 + "month", timecount);
				ParkingPeakTimelist.add(i, p);
			}
		}

		return ParkingPeakTimelist;
	}

	// 언제 가장 많이 수익이 나는가? 수익
	public ArrayList<Pair<String, Integer>> ParkingRevenue(int Parkingfacilityid, String year, String month, String day,
			String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<String, Integer>> ParkingRevenuelist = new ArrayList<>();
		ParkingStaticalData parkingstaticaldata = new ParkingStaticalData();
		if (type.equals("d")) {
			for (int i = 0; i < 31; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(month);
				parkingstaticaldata.setDay(i+1+"");
				int price = resvdao.FindStaricalParkingRevenueDay(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i + 1 + "day", price);
				ParkingRevenuelist.add(i, p);
			}
		} else if (type.equals("m")) {
			for (int i = 0; i < 12; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(i+1+"");
				int price = resvdao.FindStaricalParkingRevenueMonth(parkingstaticaldata);
				Pair<String, Integer> p = new Pair<>(i + 1 + "month", price);
				ParkingRevenuelist.add(i, p);
			}
		}

		return ParkingRevenuelist;
	}

	// 슬롯을 얼마나 많이 사용하는가
	public ArrayList<Pair<ParkingSlot, Integer>> ParkingSlotUsage(String year, String month, String day, String type) {
		// TODO Auto-generated method stub

		ArrayList<Pair<ParkingSlot, Integer>> ParkingSlotUsagelist = new ArrayList<>();
		List<ParkingSlot> parkingslotlist = parkingslotdao.FindParkingSlotInfo();
		SlotUsage slotusage = new SlotUsage();

		if (type.equals("d")) {
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
	
	//사람수분의 이용시간 평균 이용률
	public ArrayList<Pair<String, Double>> ParkingOccupancy(int Parkingfacilityid, String year, String month, String day, String type) {
		// TODO Auto-generated method stub
		ArrayList<Pair<String, Double>> ParkingOccupancylist = new ArrayList<>();
		ParkingStaticalData parkingstaticaldata = new ParkingStaticalData();
		if (type.equals("h")) {
			for (int i = 0; i < 24; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(month);
				parkingstaticaldata.setDay(day);
				parkingstaticaldata.setHour(i+"");
				double clientaveragetime = resvdao.FindStaricalParkingOccupancyHour(parkingstaticaldata);
				Pair<String, Double> p = new Pair<>(i + "hour", clientaveragetime);
				ParkingOccupancylist.add(i, p);
			}
		} else if (type.equals("d")) {
			for (int i = 0; i < 31; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(month);
				parkingstaticaldata.setDay(i+1+"");
				double clientaveragetime = resvdao.FindStaricalParkingOccupancyDay(parkingstaticaldata);
				Pair<String, Double> p = new Pair<>(i + 1 + "day", clientaveragetime);
				ParkingOccupancylist.add(i, p);
			}

		} else if (type.equals("m")) {
			for (int i = 0; i < 12; i++) {
				parkingstaticaldata.setParkingfacility_id(Parkingfacilityid);
				parkingstaticaldata.setYear(year);
				parkingstaticaldata.setMonth(i+1+"");
				double clientaveragetime = resvdao.FindStaricalParkingOccupancyMonth(parkingstaticaldata);
				Pair<String, Double> p = new Pair<>(i + 1 + "month", clientaveragetime);
				ParkingOccupancylist.add(i, p);
			}
		}

		return ParkingOccupancylist;
	}

}