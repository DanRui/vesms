package com.jst.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.serializer.ObjectSerializer;
import com.jst.type.DataType;
import com.jst.util.MessageHandlerUtil;

import net.sf.json.JSONObject;

public class SystemUtil {
	
	private static final Log log = LogFactory.getLog(SystemUtil.class);
	
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
				
				JSONObject json = JSONObject.fromObject(bodyString);
				if(!json.getJSONObject(Message.RECORD_NAME).toString().equals("null")&&json.getJSONObject(Message.RECORD_NAME).has("otherMsg")){
					
					String otherMsg = json.getJSONObject(Message.RECORD_NAME).getString("otherMsg");
					if(otherMsg!=null){
						otherMsg.replace("\"", "\\\"");
						otherMsg = "\"" + otherMsg + "\"";
						json.getJSONObject(Message.RECORD_NAME).put("otherMsg", otherMsg);
					}
				}
				
				log.debug("反序列化对象");
				Object obj = ObjectSerializer.getInstance(dataValue, serializeConfig).deserialize(clazz, json.toString());
				return obj;
			}
			
		} catch (Exception e) {
			log.error("SystemUtil_serializeObject Error:"+e , e);
		}
		log.debug("SystemUtil_serializeObject end");
		return null;
	}

	   /**  
     * 通过HttpServletRequest返回IP地址  
     * @param request HttpServletRequest  
     * @return ip String  
     * @throws Exception  
     */    
    public static String getIpAddr(HttpServletRequest request) throws Exception {    
        String ip = request.getHeader("x-forwarded-for");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    }    
    
    
    public static String callCmd(String[] cmd) { 
        String result = ""; 
        String line = ""; 
        try { 
          Process proc = Runtime.getRuntime().exec(cmd); 
          InputStreamReader is = new InputStreamReader(proc.getInputStream()); 
          BufferedReader br = new BufferedReader (is); 
          while ((line = br.readLine ()) != null) { 
          result += line; 
          } 
        } 
        catch(Exception e) { 
          e.printStackTrace(); 
        } 
        return result; 
      }
		    
		/** 
		* 
		* @param cmd 第一个命令 
		* @param another 第二个命令 
		* @return  第二个命令的执行结果 
		*/
		public static String callCmd(String[] cmd,String[] another) { 
		  String result = ""; 
		  String line = ""; 
		  try { 
			Long currentTime = System.currentTimeMillis();
		    Runtime rt = Runtime.getRuntime(); 
		    Process proc =  proc = rt.exec(another); //rt.exec(cmd); 
		    //proc.waitFor(); //已经执行完第一个命令，准备执行第二个命令
		    System.out.println("user time is :"+(System.currentTimeMillis()-currentTime));
		   
		    InputStreamReader is = new InputStreamReader(proc.getInputStream()); 
		    BufferedReader br = new BufferedReader (is); 
		    while ((line = br.readLine ()) != null) { 
		        result += line; 
		    } 
		    
		    System.out.println("user time is :"+(System.currentTimeMillis()-currentTime));
		  } 
		  catch(Exception e) { 
		    e.printStackTrace(); 
		  } 
		  return result; 
		}
		
		/** 
		* 
		* @param ip 目标ip,一般在局域网内 
		* @param sourceString 命令处理的结果字符串 
		* @param macSeparator mac分隔符号 
		* @return mac地址，用上面的分隔符号表示 
		*/
		public static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) { 
		  String result = ""; 
		  String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})"; 
		  Pattern pattern = Pattern.compile(regExp); 
		  Matcher matcher = pattern.matcher(sourceString); 
		  while(matcher.find()){ 
		    result = matcher.group(1); 
		    if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) { 
		        break; //如果有多个IP,只匹配本IP对应的Mac. 
		    } 
		  }
		
		  return result; 
		}
		/** 
		* 
		* @param ip 目标ip 
		* @return  Mac Address 
		* 
		*/
		public static String getMacInWindows(final String ip){ 
		  String result = ""; 
		  String[] cmd = { 
		        "cmd", 
		        "/c", 
		        "ping " + ip 
		        }; 
		  String[] another = { 
		        "cmd", 
		        "/c", 
		        "arp -a"
		        }; 
		
		  String cmdResult = callCmd(cmd,another); 
		  result = filterMacAddress(ip,cmdResult,"-"); 
		
		  return result; 
		} 

		/** 
		* @param ip 目标ip 
		* @return  Mac Address 
		* 
		*/
		public static String getMacInLinux(final String ip){ 
		  String result = ""; 
		  String[] cmd = { 
		        "/bin/sh", 
		        "-c", 
		        "ping " + ip + " -c 2 && arp -a"
		        }; 
		  String cmdResult = callCmd(cmd); 
		  result = filterMacAddress(ip,cmdResult,":"); 
		
		  return result; 
		} 
		/**
		* 获取MAC地址 
		* @return 返回MAC地址
		*/
		public static String getMacAddress(String ip){
		  String macAddress = "";
		  macAddress = getMacInWindows(ip).trim();
		  if(macAddress==null||"".equals(macAddress)){
		    macAddress = getMacInLinux(ip).trim();
		  }
		  return macAddress;
		}
		//做个测试
		public static void main(String[] args) { 
		  System.out.println(getMacAddress("111.13.101.208"));
		}

}
