package spark_web;

import static spark.Spark.get;
import static spark.Spark.post;

import java.security.MessageDigest;

import com.spark_web.domain.GracePeriod;
import com.spark_web.domain.Price;
import com.spark_web.domain.Send_Resv_Info;
import com.spark_web.service.ParkingSerivice;
import com.spark_web.service.ReservationService;
import com.spark_web.service.StaticalDataService;
import com.spark_web.util.JsonUtil;

public class WebService {

	// public static UserService userService = new UserService();
	public static ReservationService resvservice = new ReservationService();
	public static ParkingSerivice parkingservice = new ParkingSerivice();
	public static StaticalDataService staticaldataservice = new StaticalDataService();

	public static void main(String[] args) {

		// secure("deploy/server.jks", "123456", null, null);

		// android http communication: reservation
		post("/api/reservation", (req, res) -> {
			res.type("application/json");
			Send_Resv_Info send_resv = JsonUtil.fromJson(req.body(), Send_Resv_Info.class);
			System.out.println("reservation");
			return resvservice.Reservation(send_resv);
		}, JsonUtil.json());

		// android http communication: cancel
		post("/api/cancel", (req, res) -> {
			String authenticationnum = req.body();
			System.out.println("cancel");
			return resvservice.CancelResv(authenticationnum);
		});

		// android http communication: reservation check
		post("/api/reservationcheck", (req, res) -> {
			String phonenumber = req.body();
			System.out.println("reservationcheck");
			return resvservice.reservationcheck(phonenumber);
		}, JsonUtil.json());

		// arduino http communication: reservation check
		post("/api/checkparkingslot", (req, res) -> {

			System.out.println("checkparkingslot");
			System.out.println(req.body());

			return parkingservice.UpdateParkingSlot(req.body());
		});

		post("/api/admin/login", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			System.out.println(req.body());
			String userId = req.queryParams("userId");
			String password = req.queryParams("password");
			String token = userId + password;

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(token.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return String.format("%064x", new java.math.BigInteger(1, digest));

		}, JsonUtil.json());

		get("/api/admin/login", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			System.out.println(req.body());
			return "hello admin";
		}, JsonUtil.json());

		// get("/api/admin/login", (req, res) -> {
		// res.header("Access-Control-Allow-Origin", "*");
		// res.type("application/json");
		// System.out.println(req.body());
		// String userId = req.queryParams("userId");
		// String password = req.queryParams("password");
		// String token = userId + password;
		// return token;
		// },JsonUtil.json());

		get("/api/admin/logout", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			String token = req.queryParams("token");
			System.out.println(token);
			// delete token in database

			//

			return 0;

		}, JsonUtil.json());

		// Login Owner
		// get("/api/admin/login", (req, res) -> {
		// res.header("Access-Control-Allow-Origin", "*");
		// res.type("application/json");
		// System.out.println(req.body());
		// String userId = req.queryParams("userId");
		// String password = req.queryParams("password");
		// String token = userId + password;
		// return token;
		// },JsonUtil.json());

		// Statistics peaktime

		get("/api/admin/stats/peaktime", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");

			int facility_id = Integer.parseInt((req.queryParams("facility_id")));
			String year = req.queryParams("year");
			String month = req.queryParams("month");
			String day = req.queryParams("day");
			String type = req.queryParams("type");
			String token = req.queryParams("token");
			System.out.println(token);

			return staticaldataservice.ParkingPeakTime(facility_id, year, month, day, type);

		}, JsonUtil.json());

		get("/api/admin/stats/revenue", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");

			System.out.println(req.body());
			String type = req.queryParams("type");
			Object obj = new Object();

			return "";
		}, JsonUtil.json());

		get("/api/admin/stats/slot", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");

			String year = req.queryParams("year");
			String month = req.queryParams("month");
			String day = req.queryParams("day");
			String type = req.queryParams("type");
			String token = req.queryParams("token");
			System.out.println(year + month + day + type);
			return staticaldataservice.ParkingSlotUsage(year, month, day, type);
		}, JsonUtil.json());

		get("/api/admin/stats/occupancy", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");

			System.out.println(req.body());
			String year = req.queryParams("year");
			String month = req.queryParams("month");
			String day = req.queryParams("day");
			System.out.println(year + " " + month + " " + day);
			return "hello occupancy";
		}, JsonUtil.json());

		get("/api/admin/setting/charge", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			String m = req.queryParams("morning");
			String a = req.queryParams("afternoon");
			String w = req.queryParams("weekend");
			System.out.println(m + " " + a + " " + w);
			Price price = new Price();
			price.setMorning(1000);
			price.setAfternoon(1500);
			price.setWeekend(2000);

			return price;
		}, JsonUtil.json());

		get("/api/admin/setting/period", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			String grace = req.queryParams("grace");
			System.out.println(grace);
			GracePeriod time = new GracePeriod();
			time.setTime(30);
			return time;
		}, JsonUtil.json());

		post("/api/attendant/authentication", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
			res.type("application/json");
			String authenticationnum = req.queryParams("rsvNum");
			System.out.println(authenticationnum);

			return parkingservice.authentication(authenticationnum);
		}, JsonUtil.json());

		get("/api/attendant/monitor", (req, res) -> {

			res.header("Access-Control-Allow-Origin", "*");
			return parkingservice.ParkingSlotList();

		}, JsonUtil.json());

	}

}
