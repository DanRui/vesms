package com.jst.system.client;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.constant.Message;
import com.jst.type.DataType;
import com.jst.util.WebServiceUtil;

public class WebServiceClient {
		//日志
		private static final Log log = LogFactory.getLog(WebServiceClient.class);
		
		//服务地址前缀
		private static final String SERVICE_URL_PREFIX = Message.SERVICE_URL_PREFIX;
		
		//目标命名空间
		private static final String TARGET_NAMESPACE = Message.TARGET_NAMESPACE;
		
		/**
		 * @see 调用接口
		 * @param serviceName
		 * @param interfaceName
		 * @param paramValues
		 * @param dataType
		 * @return String
		 * @throws Exception
		 */
		public static String invokeInterface(String serviceName, String interfaceName, Object[] paramValues, DataType dataType) throws Exception {
			Object[] object = Arrays.copyOf(paramValues, paramValues.length + 1);
			
			object[object.length - 1] = null == dataType ? DataType.XML.toString() : dataType.toString();
			
			try {
				return (String) WebServiceUtil.invokeInterface(SERVICE_URL_PREFIX + serviceName, TARGET_NAMESPACE, interfaceName, null, object, new Class[]{String.class}, true, null)[0];
			} catch (Exception e) {
				log.error("invokeInterface error: " + e);
				
				throw e;
			}
		}
}
