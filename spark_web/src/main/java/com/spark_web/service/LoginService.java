package com.spark_web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.spark_web.dao.AdminDAO;
import com.spark_web.domain.Admin;

public class LoginService {

	public static AdminDAO admindao = new AdminDAO();

	public String Login(String userId, String password) throws IOException {
		// TODO Auto-generated method stub

		if (admindao.FindAdminData(userId, password)) {
			Random random = new Random();

			int result = random.nextInt(10000) + 1000;

			if (result > 10000) {
				result = result - 1000;
			}

			String token = userId + result;
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				md.update(token.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			byte[] digest = md.digest();

			Admin admin = new Admin();
			admin.setAdmin_id(userId);
			admin.setAdmin_token(String.format("%064x", new java.math.BigInteger(1, digest)));
			if (admindao.UpdateAdminToken(admin)) {
				return String.format("%064x", new java.math.BigInteger(1, digest));
			} else {
				return "fail";
			}

		} else {

			return "fail";
		}
	}

	public String Logout(String token) throws IOException {
		// TODO Auto-generated method stub

		Admin admin = new Admin();
		admin.setAdmin_token(token);
		if (admindao.UpdateAdminlogout(token)) {
			return "success";
		} else {
			return "fail";
		}

	}

	public boolean TokenCheck(String token) throws IOException {
		// TODO Auto-generated method stub

		if (token != null) {

			return admindao.FindAdminToken(token);

		} else {
			return false;
		}

	}

}
