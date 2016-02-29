package com.dataup.finance.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	private static String charset = "UTF-8";

	//解密
	public static String Decrypt(String src, String key) {
		try {
			if (key == null)
				return null;
			else {
				int length = key.length();
				if (length % 16 != 0 || length == 0)
					return null;
			}
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(charset),	"AES"));
			return new String(cipher.doFinal(hex2byte(src)), charset);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	//加密
	public static String Encrypt(String src, String key) {
		if (key == null)
			return null;
		else {
			int length = key.length();
			if (length % 16 != 0 || length == 0)
				return null;
		}
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(charset), "AES"));
			return byte2hex(cipher.doFinal(src.getBytes(charset)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] hex2byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	
}
