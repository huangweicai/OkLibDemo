package com.oklib.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

	private static Random rand = new Random();

//	public static String smsCode(int count) {
//		return RandomStringUtils.randomNumeric(count);
//	}
	public static synchronized long getId() {
		return Math.abs(rand.nextLong());
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getSalt() {
		return "{" + getUUID() + "}";
	}

	public static String getRandStr() {
		return getUUID().replace("-", "");
	}

	public static String getAccessToken() {
		return UUID.randomUUID().toString().substring(0, 16);
	}

//	public static String getRefreshToken() {
//		return MD5.encrypt(getUUID());
//	}

	public static void main(String[] args) {
		System.err.println(getRandStr());
	}

}
