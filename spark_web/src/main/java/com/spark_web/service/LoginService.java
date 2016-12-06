package com.spark_web.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.spark_web.dao.AdminDAO;
import com.spark_web.domain.Admin;
import com.spark_web.util.AesUtil;
import com.spark_web.util.SHA256;

public class LoginService {

	public static AdminDAO admindao = new AdminDAO();
	private static final int KEY_SIZE = 128;
	private static final int ITERATION_COUNT = 10000;
	private static final String IV = "F27D5C9927726BCEFE7510B1BDD3D137";
	private static final String SALT = "3FF2EC019C627B945225DEBAD71A01B6985FE84C95A70EB132882F88C0A59A55";
	private static final String PASSPHRASE = "passPhrase passPhrase aes encoding algorithm";

	public String Login(String userId, String password)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException,
			NoSuchProviderException, InvalidKeySpecException {
		// TODO Auto-generated method stub

        AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
        String decryptid = util.decrypt(SALT, IV, PASSPHRASE, userId);
        String decryptpassword = util.decrypt(SALT, IV, PASSPHRASE, password);
        
		String adminid = SHA256.setSHA256(decryptid);
		String adminpassword = SHA256.setSHA256(decryptpassword);

		if (admindao.FindAdminData(adminid, adminpassword)) {
			Random random = new Random();

			int result = random.nextInt(10000) + 1000;

			if (result > 10000) {
				result = result - 1000;
			}

			String token = SHA256.setSHA256(Integer.toString(result));

			Admin admin = new Admin();
			admin.setAdmin_id(adminid);
			admin.setAdmin_token(token);
			if (admindao.UpdateAdminToken(admin)) {
				return token;
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
