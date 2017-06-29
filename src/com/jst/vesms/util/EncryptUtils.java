
package com.jst.vesms.util;

import com.jst.common.utils.string.StringUtil;
import com.jst.util.EncryptUtil;
import com.jst.util.PropertyUtil;

public class EncryptUtils {
	
	private static final String KEY = PropertyUtil.getPropertyValue("DES_KEY");
	
	
	/**
	 * 
	 * <p>Description: 加密方法，封装原DES加密算法，异常忽略，返回原数据。</p>
	 * @param key 加密的key  String
	 * @param data 传入原数据  String
	 * @return String
	 *
	 */
	public static String encryptDes(String key, String data) {
		if (StringUtil.isEmpty(key)) {
			key = KEY;
		}
		try {
			String result = EncryptUtil.encryptDES(key, data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 
	 * <p>Description: 解密方法，封装原DES解密算法，异常忽略，返回未解密数据。</p>
	 * @param key 解密的key  String
	 * @param data 传入加密后的数据  String
	 * @return String
	 *
	 */
	public static String decryptDes(String key, String data) {
		if (StringUtil.isEmpty(key)) {
			key = KEY;
		}
		try {
			String result = EncryptUtil.decryptDES(key, data);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

}

