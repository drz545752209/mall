package com.hlju.mall.commom;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public class Encryption {

	/**
	 * 将密码字符串转为md5
	 * @param password
	 * @return cipherText
	 */
	public static String str2MD5(String password) {
		if (!StringUtils.isEmpty(password)){
			byte[] bytes = password.getBytes();
			String cipherText = DigestUtils.md5DigestAsHex(bytes);
			return cipherText;
		}

		return null;
	}

}
