package com.oklib.util;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES128 {
	static final String KEY_ALGORITHM = "AES";
	static final String CIPHER_ECB_PKCS5 = "AES/ECB/PKCS5Padding";

	public static String encrypt(String src, String seed) {
		try {
			byte[] result = encode(src.getBytes("utf-8"), seed.getBytes());
			return bytesToHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String src, String seed) {
		try {
			byte[] enc = hexStringToBytes(src);
			byte[] result = decode(enc, seed.getBytes());
			if (result != null) {
				return new String(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] encode(byte[] clear, byte[] raw) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ECB_PKCS5);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(clear);
			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] decode(byte[] encrypted, byte[] raw) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(CIPHER_ECB_PKCS5);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(encrypted);
			return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			// stringBuilder.append(hv + " ");
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	private static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	public static byte[] kkencrypt(Key keySpec, byte[] src) {

		Cipher cip = null;
		// SecretKey key=new SceretKey();
		byte[] res = null;
		try {
			cip = Cipher.getInstance(CIPHER_ALGORITHM);
			cip.init(Cipher.ENCRYPT_MODE, keySpec);
			res = cip.doFinal(src);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static byte[] kkdecrypt(Key keySpec, byte[] src){
		Cipher cip = null;
		// SecretKey key=new SceretKey();
		byte[] res = null;
		try {
			cip = Cipher.getInstance(CIPHER_ALGORITHM);
			cip.init(Cipher.DECRYPT_MODE, keySpec);
			res = cip.doFinal(src);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return res;
	}

	
	public static void main(String[] args){

		String appKey="37dd3fb0502243a68a5a0e690c6ddbb3";
		String pwd = AES128.encrypt("123456", appKey.substring(0, 16));
		System.out.println(pwd);
		Log.d("TAG", "pwd:"+pwd);
		
	}
}