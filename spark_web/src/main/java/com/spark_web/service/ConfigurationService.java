package com.spark_web.service;

import java.io.IOException;

import com.spark_web.dao.ParkingfacilityDAO;
import com.spark_web.domain.ParkingFacility;

public class ConfigurationService {

	public static ParkingfacilityDAO parkingfacilitydao = new ParkingfacilityDAO();
	
	//주자장 설정 조회
	public ParkingFacility ParkingFacilityState(int facility_id) throws IOException {
		// TODO Auto-generated method stub
		
		return parkingfacilitydao.FindParkingFacilityInfo(facility_id);
	}
	
	//가격 설정
	public ParkingFacility ChargeConfiguration(int facility_id, int amprice, int pmprice) throws IOException {
		// TODO Auto-generated method stub
		
		ParkingFacility parkingfacility = new ParkingFacility();
		parkingfacility.setParkingfacility_id(facility_id);
		parkingfacility.setParkingfacility_am_price(amprice);
		parkingfacility.setParkingfacility_pm_price(pmprice);
		
		parkingfacilitydao.UpdateParkingFacilityCharge(parkingfacility);
		
		return parkingfacilitydao.FindParkingFacilityInfo(facility_id);
	}
	
	//GracePeriod 설정
	public ParkingFacility GracePeriodConfiguration(int facility_id, int grace) throws IOException {
		// TODO Auto-generated method stub
		
		ParkingFacility parkingfacility = new ParkingFacility();
		parkingfacility.setParkingfacility_id(facility_id);
		parkingfacility.setParkingfacility_graceperiod(grace);
		
		parkingfacilitydao.UpdateParkingFacilityGracePeriod(parkingfacility);
		
		return parkingfacilitydao.FindParkingFacilityInfo(facility_id);
	}
}
