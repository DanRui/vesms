package com.jst.vesms.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.serializer.ObjectSerializer;
import com.jst.type.DataType;
import com.jst.util.MessageHandlerUtil;

/**
 * 用于反序列化WebService返回的值
 * @author DanRui
 *
 */
public class SerializeUtil {
	
	/**
	 * 用于记录日志
	 */
	private static final Log log = LogFactory.getLog(SerializeUtil.class);
	
	
	/**
	 * 反序列化集合对象
	 * @param clazz
	 * @param returnMsg
	 * @param dataValue
	 * @return
	 */
	public static List<Object> serializeList(Class clazz , String returnMsg , DataType dataValue){
		try {
			MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataValue, returnMsg);
			String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataValue));
			String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataValue));
			if (retCode.equals(Message.RET_CODE_SUCCESS)) {
				String bodyString = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.BODY, dataValue));
				
				if (dataValue.equals(DataType.XML)) {
					bodyString = "<list>" + bodyString + "</list>";
				} else {
					bodyString = "{\"list\":" + bodyString + "}";
				}
				ObjectSerializeConfig serializeConfig= new ObjectSerializeConfig();
				
				serializeConfig.setObjectAlias(Page.class, "page");
				serializeConfig.setObjectAlias(List.class, "list");
				serializeConfig.setObjectAlias(clazz, Message.RECORD_NAME);
				serializeConfig.setObjectAlias(Date.class, "date");
				//本地调用方式
				
				List<Object> list = null;
				List tempList = ObjectSerializer.getInstance(dataValue, serializeConfig).deserialize(List.class, bodyString);
				if(tempList!=null&&tempList.size()>0){
					list = (ArrayList)ObjectSerializer.getInstance(dataValue, serializeConfig).deserialize(List.class, bodyString).get(0) ;
				}else{
					list = new ArrayList<Object>();
				}
				return list;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	
	/**
	 * 反序列化单个对象
	 * @param clazz
	 * @param messageHandler
	 * @param dataValue
	 * @return
	 */
	public static Object serializeObject(Class clazz, MessageHandler messageHandler, DataType dataValue){
		ObjectSerializeConfig serializeConfig = new ObjectSerializeConfig();
		serializeConfig.setObjectAlias(clazz, Message.RECORD_NAME);
		return serializeObject(clazz, messageHandler, dataValue, serializeConfig);
	}
	
	/**
	 * 解析返回报文（用于单个对象解析）
	 * @param clazz
	 * @param returnMsg
	 * @param dataValue
	 * @return
	 */
	public static Object serializeObject(Class clazz,MessageHandler handler, DataType dataValue ,ObjectSerializeConfig serializeConfig){
		log.debug("SystemUtil_serializeObject begin");
		try {
			String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataValue));
			String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataValue));
			
			log.debug("retCode: " + retCode);
			log.debug("retMsg: " + retMsg);
			
			if (retCode.equals(Message.RET_CODE_SUCCESS)) {
				String bodyString = null;
				
				if (dataValue.equals(DataType.XML)) {
					bodyString = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.BODY, dataValue));
				} else {
					bodyString = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.BODY, dataValue) + "[0]");
				}
								
				log.debug("设置对象序列化信息");
				
				log.debug("反序列化对象");
				Object obj = ObjectSerializer.getInstance(dataValue, serializeConfig).deserialize(clazz, bodyString);
				return obj;
			}
			
		} catch (Exception e) {
			log.error("SystemUtil_serializeObject Error:"+e , e);
		}
		log.debug("SystemUtil_serializeObject end");
		return null;
	}


}
