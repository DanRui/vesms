
package com.jst.vesms.common;

import java.util.Arrays;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.system.client.WebServiceClient;
import com.jst.type.DataType;
import com.jst.util.MessageHandlerUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.WebServiceUtil;

public class SuperviseWebServiceClient {
	
	//日志
	private static final Log log = LogFactory.getLog(WebServiceClient.class);
	
	//服务地址前缀
	private static final String SERVICE_URL_PREFIX = PropertyUtil.getPropertyValue("superviseWebServiceUrl");
	
	//目标命名空间
	private static final String TARGET_NAMESPACE = PropertyUtil.getPropertyValue("superviseWebServiceNamespace");
	
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
	
	
	public static void main(String[] args) throws Exception {
		Object[] paramValues;
		String result = SuperviseWebServiceClient.invokeInterface("checkService", "checkData", new Object[] {"vesms", "ljc", "2017-06-23 18:54:00", null, "T_ELIMINATED_APPLY", null, "asdas", null, "ADD"}, DataType.JSON);
		System.out.println(result);
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.JSON, result);
		String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, DataType.JSON));
		String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.HEAD, DataType.JSON));
		JSONObject json = JSONObject.fromObject(retMsg);
		System.out.println(json.get("checkCode"));
	}

}

