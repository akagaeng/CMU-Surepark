package com.spark_web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.spark_web.dao.ParkingfacilityDAO;
import com.spark_web.dao.ParkingslotDAO;
import com.spark_web.dao.ResvDAO;
import com.spark_web.domain.ParkingFacility;
import com.spark_web.domain.ParkingSlot;
import com.spark_web.domain.Result_Resv_Info;
import com.spark_web.domain.Resv;

public class ParkingSerivice extends PushNotificationService {

	public static ResvDAO resvdao = new ResvDAO();
	public static ParkingslotDAO parkingslotdao = new ParkingslotDAO();
	public static ParkingfacilityDAO parkingfacilitydao = new ParkingfacilityDAO();

	public String authentication(String resv_authenticationnum) {
		// TODO Auto-generated method stub
		Result_Resv_Info result_resv = new Result_Resv_Info();
		result_resv = resvdao.FindResvAuthenticationInfo(resv_authenticationnum);

		if (result_resv != null) {
			//�����ؼ� ����Ʈ ����� ����
			PushNotification(result_resv.getParkingfacility_id() + "-" + result_resv.getParkingslot_floor() + "-"
					+ result_resv.getParkingslot_zone(), resv_authenticationnum);

			return "success";
		} else {
			return "fail";
		}

	}

	public String UpdateParkingSlot(String message) {
		// TODO Auto-generated method stub
		String[] message_parsing1 = message.split(",");
		String[] message_parsing2 = message_parsing1[1].split(":");
		String[] message_parsing3 = message_parsing2[0].split("-");

		String resv_authenticationnum = message_parsing1[0];
		String parking_state = message_parsing2[1];
		String parkingfacility_id = message_parsing3[0];
		String parkingslot_floor = message_parsing3[1];
		String parkingslot_zone = message_parsing3[2];
		String parkingslot_id = message_parsing3[3];

		Result_Resv_Info result_resv = new Result_Resv_Info();
		result_resv.setParkingfacility_id(parkingfacility_id);
		result_resv.setParkingslot_floor(parkingslot_floor);
		result_resv.setParkingslot_zone(parkingslot_zone);
		result_resv.setParkingslot_id(parkingslot_id);
		result_resv.setResv_authenticationnum(resv_authenticationnum);

		int parkingslotseq = parkingslotdao.FindResvParkingSlotSeq(result_resv);//������ ���� seq

		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date currentdate = new Date();
		String date = mSimpleDateFormat.format(currentdate);

		try {
			if (parking_state.equals("1")) {//������ ��Ȳ
				// in
				if (resv_authenticationnum.equals("MOVE") || resv_authenticationnum.equals("")) {
					//����ó��
				} else {//������Ȳ
					
					if (parkingslotseq != -1) {//����üũ
										
						Result_Resv_Info compareresultresv = new Result_Resv_Info();//���� �����ؾߵǴ� ���� seq������ �޾ƿ���
						compareresultresv = resvdao.FindResvAuthenticationInfo(resv_authenticationnum);												
						int originalparkingslotseq = parkingslotdao.FindResvParkingSlotSeq(compareresultresv);//���� �����ؾ��ϴ� ���� seq
						
						if (parkingslotseq != originalparkingslotseq) { //������ ���� ���� ������ �ٸ���
							// ���Ҵ�
							int parkingslotstate = parkingslotdao.FindResvParkingSlotState(parkingslotseq);//������ ������ �̹� ����Ǿ� �ִ� �� Ȯ��
							
							if (parkingslotstate != -1) {//����üũ
								if (parkingslotstate == 1) {
									// �ڸ��� ����Ǿ� �ִ� ���� ����
									// ���� ���� �ڸ��� ��ȯ
			
									String otherreallocateuserauthenticationnum = resvdao
											.FindResvAuthenticationNum(parkingslotseq);//���� ������ seq�� ����Ǿ��ִ� ������ȣ
									Resv otherreallocateuserresv = new Resv();
									otherreallocateuserresv.setParkingslot_seq(originalparkingslotseq);//������ ����� ���� �����ؾ��� ���� �Է�
									otherreallocateuserresv
											.setResv_authenticationnum(otherreallocateuserauthenticationnum);//���� ������ ������ ���� ������ ������ȣ �Է�
						
									resvdao.UpdateParkingSlotSeq(otherreallocateuserresv);//���� �����Ѱ��� ���� ���ο��� ���� �����ؾ��Ұ����� �����ؼ� ������Ʈ
									
									Result_Resv_Info otheruserresv = new Result_Resv_Info();
									otheruserresv = resvdao
											.FindResvAuthenticationInfo(otherreallocateuserauthenticationnum);//�� ������ ���� �޾ƿ���
									System.out.println(otherreallocateuserauthenticationnum);
									PushNotification(otherreallocateuserauthenticationnum,
											"reallocation:" + otheruserresv.getParkingfacility_id() + "-"
													+ otheruserresv.getParkingslot_floor() + "-"
													+ otheruserresv.getParkingslot_zone() + "-"
													+ otheruserresv.getParkingslot_id());//Ǫ���� ���� ���ŵ� �������� Ǫ��

								} else {
									// �ڸ��� ����ִ� ���� ����
									parkingslotdao.UpdateParkingSlotEmptyStatus(originalparkingslotseq);//���� seq ����
									parkingslotdao.UpdateParkingSlotResvStatus(parkingslotseq);//�����ο� seqä���
								
								}
							}
							System.out.println(resv_authenticationnum);//������ ���� ������ȣ
							PushNotification(resv_authenticationnum, "reallocation:" + message_parsing2[0]);//������ �������� ���Ҵ� �޼���
							//���� �������� ������Ʈ
							
						} 
						//���� �������� ������Ʈ
						Resv resv = new Resv();//������ ������ �� ����
						resv.setParkingslot_seq(parkingslotseq);
						resv.setResv_authenticationnum(resv_authenticationnum);
						resv.setParking_starttime(date);//���� ���� ��¥ �Է�
						resv.setResv_cancel("parking");//���� ���� �Է�
						resvdao.UpdateParkingStartTime(resv);//������Ʈ
						
					}
				}

			} else {
				// out
				//�������¸� ���
				if (resv_authenticationnum.equals("MOVE")) {
					Resv resv = new Resv();
					resv.setParkingslot_seq(parkingslotseq);
					resv.setResv_cancel("MOVE,"+ parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);
					resvdao.UpdateParkingStatetoMoving(resv);
				} else if (resv_authenticationnum.equals("PAYMENT")) {
					// ������� ����Ʈ ������ �� ��Ȳ
					// payment and exit
					// ���� ����� �� �����غ��߰��� ���� ���� �� �޾ƿ;���
					int parking_price = 0;
					
					ParkingFacility parkingfacility = new ParkingFacility();
					parkingfacility = parkingfacilitydao.FindParkingFacilityInfo(Integer.parseInt(parkingfacility_id));
					
					int am_price = parkingfacility.getParkingfacility_am_price();
					int pm_price = parkingfacility.getParkingfacility_pm_price();
					
					Resv resv = new Resv();
					resv = resvdao.FindParkingSlotStateMoving("MOVE,"+ parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);
					
					parking_price = CalculatePay(resv.getParking_starttime(), date, am_price, pm_price);

					resv.setParking_exittime(date);
					resv.setParking_price(parking_price);
					resv.setResv_cancel("MOVE,"+ parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);
					
					resvdao.UpdateParkingExitTime(resv);
					parkingslotdao.UpdateParkingSlotEmptyStatus(resv.getParkingslot_seq());

					PaymentService payment = new PaymentService();
					System.out.println(parking_price);
					payment.payment(resv.getResv_authenticationnum(), parking_price);

				} else {
					System.out.println("Error");
				}
			}
			return "success";

		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	}

	public int CalculatePay(String starttime, String exittime, int am_price, int pm_price) {

		String[] datetime = starttime.split(" ");
		String[] dates = datetime[0].split("-");
		String[] time = datetime[1].split(":");

		int startyear = Integer.parseInt(dates[0]);
		int startmon = Integer.parseInt(dates[1]);
		int startday = Integer.parseInt(dates[2]);
		int starthour = Integer.parseInt(time[0]);
		int startmin = Integer.parseInt(time[1]);

		datetime = exittime.split(" ");
		dates = datetime[0].split("-");
		time = datetime[1].split(":");

		int exityear = Integer.parseInt(dates[0]);
		int exitmon = Integer.parseInt(dates[1]);
		int exitday = Integer.parseInt(dates[2]);
		int exithour = Integer.parseInt(time[0]);
		int exitmin = Integer.parseInt(time[1]);

		int year = (exityear - startyear) * 365 * 24;
		int mon = (exitmon - startmon) * 31 * 24;
		int day = (exitday - startday) * 24;
		int hour = (exithour - starthour);
		int min = exitmin - startmin;

		if (min > 0) {
			min = 1;
		} else {
			min = 0;
		}

		int parkingtime = year + mon + day + hour + min;

		int bigpay = (parkingtime) / 24 * ((12 * am_price) + (12 * pm_price));
		int endhour = (parkingtime) % 24 + starthour;
		int paydetail = 0;

		if (starthour < 12 && endhour < 12) {
			paydetail = (endhour - starthour) * am_price;
		} else if (starthour < 12 && 12 <= endhour && endhour < 24) {
			paydetail = ((12 - starthour) * am_price) + ((endhour - 12) * pm_price);
		} else if (starthour >= 12 && endhour < 24) {
			paydetail = (endhour - starthour) * pm_price;
		} else if (starthour < 12 && endhour >= 24) {
			paydetail = ((12 - starthour) * am_price) + (12 * pm_price) + ((endhour - 24) * am_price);
		} else {
			paydetail = (24 - starthour) * pm_price + ((endhour - 24) * am_price);
		}

		int totalpay = bigpay + paydetail;

		return totalpay;

	}
	
	public List<ParkingSlot> ParkingSlotList() {
		// TODO Auto-generated method stub
		return parkingslotdao.FindParkingSlotInfo();
	}
}
