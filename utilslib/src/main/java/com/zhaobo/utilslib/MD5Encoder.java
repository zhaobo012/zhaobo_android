package com.zhaobo.utilslib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法
 * 
 * @author zb 2016年3月25日下午3:56:06
 */
public class MD5Encoder {

	/**
	 * 将传入的字符串进行MD5加密
	 * 
	 * @param str 需呀加密的字符串
	 * @return 加密后返回到字符串,异常返回""
	 */
	public static String encoder(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {

				String s = Integer.toHexString(0xff & bytes[i]);
				if (s.length() == 1) {
					s = "0" + s;
				}
				sb.append(s);
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

}
