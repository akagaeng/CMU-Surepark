package com.spark_web.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.spark_web.dao.ParkingslotDAO;
import com.spark_web.dao.ResvDAO;
import com.spark_web.domain.Result_Resv_Info;
import com.spark_web.domain.Resv;
import com.spark_web.domain.Send_Resv_Info;

public class ReservationService {

	public static ParkingslotDAO parkingslotdao = new ParkingslotDAO();
	public static ResvDAO resvdao = new ResvDAO();
	
	public Result_Resv_Info Reservation(Send_Resv_Info send_resv) throws IOException {
		// TODO Auto-generated method stub
		int parkingslot = parkingslotdao.FindAvailableParkingSlot();	
		if(parkingslot == -1){
			return null;
		}else{
			
			int currentresvid = (resvdao.FindMaximumResvid()+1)*10000;
			
			Random random = new Random();
	        
			int result = random.nextInt(10000)+1000;
			 
			if(result>10000){
			    result = result - 1000;
			}
			
			int authenticationnum = currentresvid+result;
			
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd");
			Date currentdate = new Date();
			String date = mSimpleDateFormat.format(currentdate);
					
			Resv resv = new Resv();	
			resv.setParkingslot_seq(parkingslot);
			resv.setResv_phonenum(send_resv.getResv_phonenum());
			resv.setResv_creditnum(send_resv.getResv_creditnum());
			resv.setResv_starttime(date+" "+send_resv.getResv_starttime());
			resv.setResv_authenticationnum(authenticationnum+"");
			
			if(resvdao.InsertResv(resv) && parkingslotdao.UpdateParkingSlotResvStatus(parkingslot)){
				return resvdao.FindResv(resv.getResv_phonenum());
			}else{
				return null;
			}
		}
			
		
	}

	public String CancelResv(String authenticationnum) throws IOException {
		// TODO Auto-generated method stub
		
		if(resvdao.UpdateCancelResv(authenticationnum)){
			return "success";
		}else{
			return "fail";
		}
	}
	
	public Result_Resv_Info reservationcheck(String phonenumber) {
		// TODO Auto-generated method stub
		
		return resvdao.FindResv(phonenumber); 
		
	}

}
