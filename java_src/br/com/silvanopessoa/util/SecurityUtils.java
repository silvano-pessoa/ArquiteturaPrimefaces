package br.com.silvanopessoa.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SecurityUtils {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(str.getBytes()));
			return hash.toString(16);
		} catch (Exception e) {
			return str;
		}
	}

}
