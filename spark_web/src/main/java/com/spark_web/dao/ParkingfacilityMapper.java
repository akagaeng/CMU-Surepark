package com.spark_web.dao;

import com.spark_web.domain.ParkingFacility;

public interface ParkingfacilityMapper {

	ParkingFacility FindParkingFacilityInfo(int parkingfacility_id);

	void UpdateParkingFacilityCharge(ParkingFacility parkingfacility);

	void UpdateParkingFacilityGracePeriod(ParkingFacility parkingfacility);

}