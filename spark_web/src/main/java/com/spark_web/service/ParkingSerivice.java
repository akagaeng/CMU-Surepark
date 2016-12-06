package com.spark_web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.spark_web.dao.HeartbeatLogDAO;
import com.spark_web.dao.ParkingfacilityDAO;
import com.spark_web.dao.ParkingslotDAO;
import com.spark_web.dao.ResvDAO;
import com.spark_web.domain.HeartbeatLog;
import com.spark_web.domain.Pair;
import com.spark_web.domain.ParkingFacility;
import com.spark_web.domain.ParkingSlot;
import com.spark_web.domain.Result_Resv_Info;
import com.spark_web.domain.Resv;
import com.spark_web.util.AES256Cipher;

public class ParkingSerivice extends PushNotificationService {

	public static ResvDAO resvdao = new ResvDAO();
	public static ParkingslotDAO parkingslotdao = new ParkingslotDAO();
	public static ParkingfacilityDAO parkingfacilitydao = new ParkingfacilityDAO();
	public static HeartbeatLogDAO heartbeatlogdao = new HeartbeatLogDAO();

	public String authentication(String resv_authenticationnum)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub

		AES256Cipher a256 = AES256Cipher.getInstance();

		String authenticationnum = a256.AES_Encode(resv_authenticationnum);

		Result_Resv_Info result_resv = new Result_Resv_Info();
		result_resv = resvdao.FindResvAuthenticationInfo(authenticationnum);

		if (result_resv != null) {
			// 인증해서게이트열라고보냄
			PushNotification(result_resv.getParkingfacility_id() + "-" + result_resv.getParkingslot_floor() + "-"
					+ result_resv.getParkingslot_zone(), authenticationnum);

			return "success";
		} else {
			return "fail";
		}

	}

	public String UpdateParkingSlot(String message) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
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

		int parkingslotseq = parkingslotdao.FindResvParkingSlotSeq(result_resv);// 주차한곳의seq

		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date currentdate = new Date();
		String date = mSimpleDateFormat.format(currentdate);

		AES256Cipher a256 = AES256Cipher.getInstance();
		if (resv_authenticationnum.equals("HEARTBEAT")) {
			return RecordHeartBeat(parkingfacility_id, parkingslot_floor, parkingslot_zone, date);

		} else if (resv_authenticationnum.equals("MOVE")) {
			Resv resv = new Resv();
			resv.setParkingslot_seq(parkingslotseq);
			resv.setResv_cancel("MOVE," + parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);
			resvdao.UpdateParkingStatetoMoving(resv);

		} else if (resv_authenticationnum.equals("PAYMENT")) {
			// payment and exit
			int parking_price = 0;

			ParkingFacility parkingfacility = new ParkingFacility();
			parkingfacility = parkingfacilitydao.FindParkingFacilityInfo(Integer.parseInt(parkingfacility_id));

			int am_price = parkingfacility.getParkingfacility_am_price();
			int pm_price = parkingfacility.getParkingfacility_pm_price();

			Resv resv = new Resv();
			resv = resvdao.FindParkingSlotStateMoving(
					"MOVE," + parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);

			parking_price = CalculatePay(resv.getParking_starttime(), date, am_price, pm_price);

			resv.setParking_exittime(date);
			resv.setParking_price(parking_price);
			resv.setResv_cancel("MOVE," + parkingfacility_id + "-" + parkingslot_floor + "-" + parkingslot_zone);

			resvdao.UpdateParkingExitTime(resv);
			parkingslotdao.UpdateParkingSlotEmptyStatus(resv.getParkingslot_seq());

			PaymentService payment = new PaymentService();
			System.out.println(parking_price);
			payment.payment(a256.AES_Decode(resv.getResv_authenticationnum()), parking_price);

		} else {
			if (parking_state.equals("1")) {// 입장한상황
				// in

				if (resv_authenticationnum.equals("")) {
					// 에러처리
					return "error";
				} else {// 주차상황

					if (parkingslotseq != -1) {// 에러체크

						Result_Resv_Info compareresultresv = new Result_Resv_Info();// 원래주차해야되는곳의seq정보를받아오기
						compareresultresv = resvdao.FindResvAuthenticationInfo(resv_authenticationnum);
						int originalparkingslotseq = parkingslotdao.FindResvParkingSlotSeq(compareresultresv);// 원래주차해야하는곳의seq

						if (parkingslotseq != originalparkingslotseq) { // 주차한곳과예약정보가다르면
							// 재할당
							int parkingslotstate = parkingslotdao.FindResvParkingSlotState(parkingslotseq);// 주차된공간이이미예약되어있는지확인

							if (parkingslotstate != -1) {// 에러체크
								if (parkingslotstate == 1) {
									// 자리가예약되어있는곳에주차
									// 서로예약자리만교환

									String otherreallocateuserauthenticationnum = resvdao
											.FindResvAuthenticationNum(parkingslotseq);// 지금주차한seq에저장되어있는인증번호
									Resv otherreallocateuserresv = new Resv();
									otherreallocateuserresv.setParkingslot_seq(originalparkingslotseq);// 주차한사람이원래주차해야할공간입력
									otherreallocateuserresv
											.setResv_authenticationnum(otherreallocateuserauthenticationnum);// 지금주차된공간의원래주인의인증번호입력

									resvdao.UpdateParkingSlotSeq(otherreallocateuserresv);// 지금주차한곳의원래주인에게내가주차해야할곳으로변경해서업데이트

									Result_Resv_Info otheruserresv = new Result_Resv_Info();
									otheruserresv = resvdao
											.FindResvAuthenticationInfo(otherreallocateuserauthenticationnum);// 그유저의정보받아오기
									String otherauthenticationnum = a256
											.AES_Decode(otherreallocateuserauthenticationnum);
									System.out.println(otherauthenticationnum);// 입장한차량인증번호
									PushNotification(otherauthenticationnum,
											"reallocation:" + otheruserresv.getParkingfacility_id() + "-"
													+ otheruserresv.getParkingslot_floor() + "-"
													+ otheruserresv.getParkingslot_zone() + "-"
													+ otheruserresv.getParkingslot_id());// 푸쉬로새로갱신된예약정보푸시

								} else {
									// 자리가비어있는곳에주차
									parkingslotdao.UpdateParkingSlotEmptyStatus(originalparkingslotseq);// 기존seq비우고
									parkingslotdao.UpdateParkingSlotResvStatus(parkingslotseq);// ㅅㅐ로운seq채우고

								}
							}

							String authenticationnum = a256.AES_Decode(resv_authenticationnum);
							System.out.println(authenticationnum);// 입장한차량인증번호
							PushNotification(authenticationnum, "reallocation:" + message_parsing2[0]);// 입장한차량으로재할당메세지

						}
						// 주차시작으로업데이트
						Resv resv = new Resv();// 실제로 주차된 곳 정보
						resv.setParkingslot_seq(parkingslotseq);
						resv.setResv_authenticationnum(resv_authenticationnum);
						resv.setParking_starttime(date);// 주차시작날짜입력
						resv.setResv_cancel("parking");// 주차상태입력
						resvdao.UpdateParkingStartTime(resv);// 업데이트
					}
				}

			}
		}
		return "success";

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

	public ArrayList<Pair<ParkingSlot, String>> ParkingSlotList() {
		// TODO Auto-generated method stub
		ArrayList<Pair<ParkingSlot, String>> ParkingSlotStatelist = new ArrayList<>();
		List<ParkingSlot> parkingslotlist = parkingslotdao.FindParkingSlotInfo();
		for (int i = 0; i < parkingslotlist.size(); i++) {
			String resvcancel = resvdao.FindResvState(parkingslotlist.get(i).getParkingslot_seq());
			Pair<ParkingSlot, String> p = new Pair<>(parkingslotlist.get(i), resvcancel);
			ParkingSlotStatelist.add(i, p);
		}

		return ParkingSlotStatelist;
	}

	public String RecordHeartBeat(String parkingfacility_id, String parkingslot_floor, String parkingslot_zone,
			String date) throws IOException {
		// TODO Auto-generated method stub

		HeartbeatLog heartbeatlog = new HeartbeatLog();

		heartbeatlog.setParkingfacility_id(Integer.parseInt(parkingfacility_id));
		heartbeatlog.setParkingslot_floor(parkingslot_floor);
		heartbeatlog.setParkingslot_zone(parkingslot_zone);
		heartbeatlog.setDate(date);

		if (heartbeatlogdao.UpdateHeartbeatLog(heartbeatlog)) {
			return "success";
		} else {
			return "fail";
		}
	}

	public List<HeartbeatLog> CheckHeartBeat() {
		// TODO Auto-generated method stub
		List<HeartbeatLog> heartbeatlog = heartbeatlogdao.FindHeartbeatLog();
		if (1 > heartbeatlog.size()) {
			return null;
		} else {
			return heartbeatlog;
		}
	}
}
