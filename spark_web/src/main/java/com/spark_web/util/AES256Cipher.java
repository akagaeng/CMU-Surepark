package com.spark_web.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Cipher {

	public static volatile AES256Cipher INSTANCE;
	
	final static String secretKey = "89071632405379023843194080915302"; // 32bit
	static String IV = ""; // 16bit

	public static AES256Cipher getInstance() {
		if (INSTANCE == null) {
			synchronized (AES256Cipher.class) {
				if (INSTANCE == null)
					INSTANCE = new AES256Cipher();
			}
		}
		return INSTANCE;
	}

	public AES256Cipher() {
		IV = secretKey.substring(0, 16);
	}

	//
	public String AES_Encode(String str)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();

		SecretKey secureKey = new SecretKeySpec(keyData, "AES");

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));
		//System.out.print(enStr);
		return enStr;
	}

	//
	public String AES_Decode(String str)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] keyData = secretKey.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

		byte[] byteStr = Base64.decodeBase64(str.getBytes());
		//System.out.print(new String(c.doFinal(byteStr), "UTF-8"));

		return new String(c.doFinal(byteStr), "UTF-8");
	}	
	
}