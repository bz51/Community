package com.community.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEcoder {
	public static String ecode(String str){
		String str_new = null;
		try {
			str_new = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return str_new;
	}
}
