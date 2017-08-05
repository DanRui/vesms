package com.jst.vesms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtil {

	public static String encodeURIComponent(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
