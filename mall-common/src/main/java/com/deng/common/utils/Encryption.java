package com.deng.common.utils;

import org.springframework.util.DigestUtils;

public class Encryption {

	/**
	 * 将密码字符串转为md5
	 * @param password
	 * @return cipherText
	 */
	public static String str2MD5(String password) {
		byte[] bytes = password.getBytes();
		String cipherText = DigestUtils.md5DigestAsHex(bytes);
		return cipherText;
	}

}
