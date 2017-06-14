package com.jst.vesms.webservice.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.extensions.spring.receivers.SpringServletContextObjectSupplier;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jst.common.system.LogConstants;
import com.jst.common.utils.ConvertJsonUtil;
import com.jst.common.utils.RedisUtil;
import com.jst.common.utils.string.StringUtil;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.platformClient.client.WebServiceClient;
import com.jst.platformClient.entity.UserPrvg;
import com.jst.platformClient.utils.Constants;
import com.jst.system.security.LoginSecurity;
import com.jst.system.util.SystemUtil;
import com.jst.type.DataType;
import com.jst.util.ByteUtil;
import com.jst.util.DateUtil;
import com.jst.util.EncryptUtil;
import com.jst.util.MessageHandlerUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.ReflectUtil;
import com.jst.util.RequestUtil;
import com.jst.util.ResponseUtil;

public class WebServiceHandler extends AbstractHandler {

	private static final Log log = LogFactory.getLog(WebServiceHandler.class);
	
	private static Map<String, Map<String, Object>> cacheMap = new HashMap<String, Map<String, Object>>();
	private Map<String, Object> loginInfo;
	
	public static Map<Long,Map<String,Object>> threadMap = new HashMap<Long,Map<String,Object>>();
	
	/**
	 * @see 签名信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static String _sign() throws Exception {
		// 以当前时间字符串为例yyyyMMdd
		// 密钥生成规则如下
		// 第一位 + 第八位的数值转为16进制字符串(A1)
		// 第二位 + 第七位的数值转为16进制字符串(A2)
		// 第三位 + 第六位的数值转为16进制字符串(A3)
		// 第四位 + 第五位的数值转为16进制字符串(A4)
		// 密钥为：A1 + A2 + A3 + A4
		// 签名信息：先根据密钥加密当前日期字符串，再做MD5加密后转换为大写

		try {
			String dateString = DateUtil.format(DateUtil.getCurrentDate(), DateUtil.DATE_PATTERN_1);

			int one = Integer.parseInt(String.valueOf(dateString.charAt(0))) + Integer.parseInt(String.valueOf(dateString.charAt(7)));
			int two = Integer.parseInt(String.valueOf(dateString.charAt(1))) + Integer.parseInt(String.valueOf(dateString.charAt(6)));
			int three = Integer.parseInt(String.valueOf(dateString.charAt(2))) + Integer.parseInt(String.valueOf(dateString.charAt(5)));
			int four = Integer.parseInt(String.valueOf(dateString.charAt(3))) + Integer.parseInt(String.valueOf(dateString.charAt(4)));

			String key = ByteUtil.toHexString(one) + ByteUtil.toHexString(two) + ByteUtil.toHexString(three) + ByteUtil.toHexString(four);

			return EncryptUtil.encryptMD5(EncryptUtil.encryptDES(key, dateString)).toUpperCase();
		} catch (Exception e) {
			log.error("_generateSignature error: " + e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @see 用户认证
	 * @param userCode
	 * @param password
	 * @param signature
	 * @return boolean
	 * @throws Exception
	 */
	private boolean _authenticate(String userCode, String password, String signature) throws Exception {
		try {
			WebServiceClient client = new WebServiceClient();

			// log.debug("current login password is " + password);
			// 登录认证，认证过后的信息放入static变量
			String success = "F";
			if (!cacheMap.containsKey(userCode)) {
				success = client.checkkLogin(userCode, password);
				if (success.equals("T")) {
					loginInfo = new HashMap<String, Object>();
					loginInfo.put("password", password);
					cacheMap.put(userCode, loginInfo);
				}
			} else {
				loginInfo = cacheMap.get(userCode);
				// log.debug("current loginInfo password value is " + loginInfo.get("password"));
				if (password.equals(loginInfo.get("password"))) {
					success = "T";
				}
			}

			if (success.equals("T")) {
				return true;
				// return StringUtil.isNotEmpty(signature) && _sign().equals(signature.toUpperCase());
			}
		} catch (Exception e) {
			log.error("_authenticate error: " + e);
			e.printStackTrace();

			throw e;
		}

		return false;
	}

	/**
	 * @see 用户授权
	 * @param userCode
	 * @param permissions
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean _authorize(String userCode, RequiresPermissions permissions) throws Exception {
		if (null == permissions || null == permissions.value()) {
			return true;
		}

		int count = 0;
		List<UserPrvg> userPrvgList = null;

		try {
			WebServiceClient client = new WebServiceClient();
			if (!cacheMap.containsKey(userCode)) {
				String type = "SYSTEM_PRVG";
				String userPrvgKey = "PRVG_LIST_"+userCode;
				String redisValue = RedisUtil.getValue(type, userPrvgKey);
				if(StringUtil.isNotEmpty(redisValue)){
					userPrvgList = ConvertJsonUtil.deserialize(redisValue);
				}else{
					userPrvgList = client.getUserPrvgList(Constants.CURRENT_APPCODE, userCode, null);
					loginInfo.put("userPrvgList", userPrvgList);
					cacheMap.put(userCode, loginInfo);
				}
			} else {
				loginInfo = cacheMap.get(userCode);
				if (loginInfo.containsKey("userPrvgList")) {
					userPrvgList = (List<UserPrvg>) loginInfo.get("userPrvgList");
				} else {
					userPrvgList = client.getUserPrvgList(Constants.CURRENT_APPCODE, userCode, null);
					loginInfo.put("userPrvgList", userPrvgList);
				}
			}

			String[] value = permissions.value();
			Logical logical = permissions.logical();

			log.debug("权限校验逻辑：" + logical);

			for (String string : value) {
				log.debug("访问所需权限：" + string);

				String mdlCode = string.split(":")[0];
				String mdlPrvg = string.split(":")[1];

				int i = count;

				for (UserPrvg userPrvg : userPrvgList) {
					if (mdlCode.equals(userPrvg.getMdlCode()) && userPrvg.getMdlPrvgList().indexOf(mdlPrvg) != -1) {
						count++;
					}
				}

				if (i == count) {
					log.debug("用户缺失权限：" + string);
				}
			}

			if (logical.equals(Logical.AND) && value.length == count) {
				return true;
			} else if (logical.equals(Logical.OR) && count >= 1) {
				return true;
			}
		} catch (Exception e) {
			log.debug("_authorize error: " + e);
			e.printStackTrace();

			throw e;
		}

		return false;
	}
	
	@Override
	public InvocationResponse invoke(MessageContext context) throws AxisFault {
		log.debug("authentication begin");
		boolean isRecordLog = Boolean.parseBoolean(PropertyUtil.getPropertyValue("isrecord.log"));
		Thread thread = Thread.currentThread();
		System.out.println("接口拦截器内的："+thread.getId());
		LogConstants.put(LogConstants.CURRENT_START_TIME, System.currentTimeMillis());
		String errorMsg = null;

		ServletOutputStream out = null;

		HttpServletResponse response = null;
		HttpServletRequest request = null;

		String dataType = "XML";

		try {
			log.debug("检测到请求");

			request = RequestUtil.getRequestFromAxis(context);

			// webservice调用其他地方的service，则不需要验证
			if (null == request) {
				request = RequestUtil.getRequestFromAxis();
				if (null != request) {
					return InvocationResponse.CONTINUE;
				}
			}

			// 获取response对象并设置编码
			response = ResponseUtil.getResponseFromAxis(context);
			response.setCharacterEncoding(Message.ENCODING);
			response.setContentType("application/xml;charset=" + Message.ENCODING);

			// 获取请求SOAP报文
			SOAPEnvelope requestEnvelope = context.getEnvelope();
			// SOAPHeader header = requestEnvelope.getHeader();
			SOAPBody body = requestEnvelope.getBody();

			// log.debug("接收SOAP报文内容：" + requestEnvelope.toString());

			if (null != body) {
				String userCode = null;
				String password = null;

				Iterator<OMElement> authIt = body.getChildrenWithLocalName(requestEnvelope.getSOAPBodyFirstElementLocalName());
				if (null != authIt && authIt.hasNext()) {
					OMElement authentication = authIt.next();

					Iterator<OMElement> items = authentication.getChildElements();

					List<OMElement> list = new LinkedList();
					while (items.hasNext()) {
						list.add(items.next());
					}
					
					// 必须参数格式、顺序一致
					if (!(list.size() < 5)) {
						dataType = list.get(list.size() - 1).getText();
						log.debug("获取返回报文格式：" + dataType);

						log.debug("判断来源IP地址：" + request.getRemoteAddr());
						String ipFilter = PropertyUtil.getPropertyValue("ipFilter");
						String[] ipArr = ipFilter.split(",");
						boolean checkIp = false;
						String currentIp = request.getRemoteAddr();
						for (int i = 0; i < ipArr.length; i++) {
							String loopIp = ipArr[i];
							if (StringUtil.isNotEmpty(loopIp)) {
								if (loopIp.contains(currentIp)) {
									checkIp = true;
									break;
								}
								if (loopIp.contains("*")) {
									String reg = loopIp.substring(0, loopIp.indexOf("*")) + "(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
									Pattern pattern = Pattern.compile(reg);
									Matcher m = pattern.matcher(currentIp);
									checkIp = m.matches();
									if (checkIp) {
										break;
									}
								}
							}
						}
						if (checkIp) {

							log.debug("开始进行登录认证");

							userCode = list.get(0).getText();
							password = list.get(1).getText();
						
							// 签名信息
							/*
							 * Iterator<OMElement> signatureIt = authentication.getChildrenWithLocalName(Message.SIGNATURE);
							 * 
							 * if (null != signatureIt && signatureIt.hasNext()) { signature = signatureIt.next().getText(); }
							 */

							if (StringUtil.isNotEmpty(userCode) && StringUtil.isNotEmpty(password)) {
								log.debug("用户代码：" + userCode);
								boolean success = false;

								log.debug("实例化登录安全对象");
								LoginSecurity loginSecurity = new LoginSecurity(userCode);

								log.debug("检查用户登录锁定状态");
								if (loginSecurity.isLocked()) {
									log.debug("用户已被锁定，尝试解锁");
									if (!loginSecurity.releaseLock()) {

										log.debug("解锁失败，" + loginSecurity.getUnlockTimeRemaining() + "分钟后解锁");
										errorMsg = "用户已被锁定," + loginSecurity.getUnlockTimeRemaining() + "分钟后解锁";
										success = false;
									}
								}

								log.debug("用户未被锁定");

								success = _authenticate(userCode, password, "");

								if (success) {
									log.debug("登陆认证成功");

									String serviceName = context.getServiceContext().getName();
									String operationName = context.getOperationContext().getOperationName();

									log.debug("待访问服务名称：" + serviceName);
									log.debug("待访问方法名称：" + operationName);

									log.debug("开始进行权限验证");

									// Method method = (Method) context.getOperationContext().getAxisOperation().getParameterValue("myMethod");
									Method method = ReflectUtil.getMethod(new SpringServletContextObjectSupplier().getServiceObject(context.getServiceContext().getAxisService()).getClass(), operationName);

									RequiresPermissions permissions = ReflectUtil.getAnnotation(method, RequiresPermissions.class);
									
									
									String sessionInfo = list.get(2).getText();
									String terminalInfo = list.get(3).getText();
									
									/**
									 * 是否进行日志记录，设置在config.properties 中的 isrecord.log为true时进行记录
									 */
										//开始设置日志记录所需信息
										String terminalStr =  "x-requested-with:" + request.getHeader("x-requested-with") +", accept :" +request.getHeader("accept")+",content-type:"+request.getHeader("content-type");
										String ip = SystemUtil.getIpAddr(request);
										String mac = SystemUtil.getMacAddress(ip);
										String sessionStr = "ip:"+ip+",mac:"+mac;
										LogConstants.put(LogConstants.USER_CODE, userCode);
										LogConstants.put("password", password);
										LogConstants.put(LogConstants.WEB_SESSION_INFO, sessionInfo);
										LogConstants.put(LogConstants.WEB_TERMINAL_INFO, terminalInfo);
										LogConstants.put(LogConstants.IP_STR, currentIp);
									
										LogConstants.put(LogConstants.CURRENT_TERMINAL, terminalStr);
										
										LogConstants.put(LogConstants.CURRENT_SESSION, sessionStr);
										LogConstants.put(LogConstants.WEB_SERVICE_METHOD, operationName);
										LogConstants.put(LogConstants.SERVICE_NAME, serviceName);
										String args = "";
										for (Iterator iterator = list.iterator(); iterator.hasNext();) {
											OMElement omElement = (OMElement) iterator.next();
											args += StringUtil.isEmpty(args)?omElement.getText():","+omElement.getText();
										}
										LogConstants.put(LogConstants.WEB_SERVICE_ARGS, args);
										//判断Webservice方法体上是否包含权限注解
										if(null!=permissions && null!=permissions.value()){
											String[] value = permissions.value();
											String mdlCode = null;
											String mdlPrvg = null;
											for (String prv :value) {
												if(StringUtil.isEmpty(mdlCode)){
													mdlCode = prv.split(":")[0];
												}else{
													if(!mdlCode.contains(prv.split(":")[0])){
														mdlCode += ","+prv.split(":")[0];
													}
												}
												if(StringUtil.isEmpty(mdlPrvg)){
													mdlPrvg = prv.split(":")[1];
												}else{
													mdlPrvg += ","+prv.split(":")[1];
												}
											}
											LogConstants.put(LogConstants.MDL_CODE, mdlCode);
											LogConstants.put(LogConstants.MDL_PRVG, mdlPrvg);
										}
									//将信息存入线程集存储中，在切面内使用，
									
									boolean hasPrvg =  _authorize(userCode, permissions);

									if (!hasPrvg) {
										log.debug("权限认证失败");

										errorMsg = "权限认证失败";
									} else {
										log.debug("权限认证成功");
									}
								} else {
									log.debug("登陆认证失败，用户名或密码信息错误");

									errorMsg = "登陆认证失败，用户名或密码信息错误";
								}
							} else {
								log.debug("登陆认证失败，未找到用户名或密码节点");

								errorMsg = "登陆认证失败，未找到用户名或密码节点";
							}
						} else {
							log.debug("登陆认证失败，非法域名及网址" + request.getRemoteAddr());

							errorMsg = "登陆认证失败，非法域名及网址" + request.getRemoteAddr();
						}
					} else {
						
						
						log.debug("登陆认证失败，请求参数不够");

						errorMsg = "登陆认证失败，请求参数不够";
						return InvocationResponse.CONTINUE;
					}
				} else {
					log.debug("登陆认证失败，未找到请求报文内容");

					errorMsg = "登陆认证失败，未找到请求报文内容";
				}
			} else {
				log.debug("登陆认证失败，未找到SOAP报文头部节点");

				errorMsg = "登陆认证失败，未找到SOAP报文头部节点";
			}

			// 线程先挂起 然后finally设置返回值
			if (StringUtil.isNotEmpty(errorMsg)) {
				return InvocationResponse.ABORT;
			}
		} catch (Exception e) {
			log.error("authentication error: " + e,e);
			e.printStackTrace();
			errorMsg = "系统异常，接口调用失败";
			return InvocationResponse.ABORT;
		} finally {
			try {
				if (StringUtil.isNotEmpty(errorMsg)) {
					OMFactory omFactory = OMAbstractFactory.getOMFactory();

					String targetNamespace = context.getServiceContext().getAxisService().getSchemaTargetNamespace();

					String targetNamespacePrefix = context.getServiceContext().getAxisService().getSchemaTargetNamespacePrefix();

					OMNamespace namespace = omFactory.createOMNamespace(targetNamespace, targetNamespacePrefix);

					// 获取待访问方法名称
					String methodName = context.getOperationContext().getOperationName();

					OMElement methodResponse = omFactory.createOMElement(methodName + "Response", namespace);

					OMElement nsReturn = omFactory.createOMElement("return", namespace);

					if (DataType.valueOf(dataType).equals(DataType.JSON)) {
						MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf("JSON"));

						handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
						handler.addHeadParam(Message.RET_MSG_NAME, errorMsg);

						nsReturn.setText(handler.generateResponseMessage());
					} else {
						OMElement msg = omFactory.createOMElement(Message.ROOT_NAME, null);

						OMElement head = omFactory.createOMElement(Message.HEAD_NAME, null);

						OMElement retCode = omFactory.createOMElement(Message.RET_CODE_NAME, null);

						retCode.setText(Message.RET_CODE_FAILURE);

						OMElement retMsg = omFactory.createOMElement(Message.RET_MSG_NAME, null);

						retMsg.setText(errorMsg);

						head.addChild(retCode);
						head.addChild(retMsg);

						msg.addChild(head);

						nsReturn.setText(Message.DOC_TYPE + msg.toString());
					}

					methodResponse.addChild(nsReturn);

					out = response.getOutputStream();

					out.write(methodResponse.toString().getBytes(Message.ENCODING));
					/*
					 * // 转成json,本系统的IP传JSON if (request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("172.17.0.178") || request.getRemoteAddr().equals("172.17.0.180")) { MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf("JSON"));
					 * 
					 * handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE); handler.addHeadParam(Message.RET_MSG_NAME, errorMsg);
					 * 
					 * String str = "<ns:" + methodName + " xmlns:ns=\"" + targetNamespace + "\"><ns:return>"; str += handler.generateResponseMessage() + "</ns:return></ns:" + methodName + ">"; out.write(str.getBytes(Message.ENCODING)); } else {
					 * 
					 * }
					 */
				}

				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				log.error("authentication error: " + e);
				e.printStackTrace();
			}
		}

		log.debug("authentication end");
		return InvocationResponse.CONTINUE;
	}


}
