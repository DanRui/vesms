package com.jst.common.shiro.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jst.common.json.WriterUtil;
import com.jst.common.system.LogConstants;
import com.jst.common.system.annotation.Privilege;
import com.jst.demo.login.web.LoginAction;
import com.jst.platformClient.client.WebServiceClient;
import com.jst.platformClient.entity.User;
import com.jst.platformClient.entity.UserPrvg;
import com.jst.platformClient.utils.Constants;
import com.jst.system.util.SystemUtil;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.RequestUtil;
import com.jst.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ControllerInterceptor extends HandlerInterceptorAdapter implements Serializable{

	private static final long serialVersionUID = -4431222295384819382L;

	private static final String UN_LOGIN = "当前尚未登陆或会话已失效，请登陆后再进行操作";
	
	//日志
	private static final Log log = LogFactory.getLog(ControllerInterceptor.class);
	
	private static final String LACK_PERMISSION = "缺失当前操作所需权限";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*super.postHandle(request, response, handler, modelAndView);*/
	
	}

	private static final String OPE_ERR_INFO = "系统异常，当前操作失败";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("preHandle begin");
		boolean isRecordLog = Boolean.parseBoolean(PropertyUtil.getPropertyValue("isrecord.log"));
		Thread thread = Thread.currentThread();
		System.out.println("接口拦截器内的：" + thread.getId());
		LogConstants.put(LogConstants.CURRENT_START_TIME, System.currentTimeMillis());
		
		Subject subject = SecurityUtils.getSubject();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class action = handlerMethod.getBeanType();
		Long currentTime = System.currentTimeMillis();
		Field sessionInfo = action.getField("sessionInfo");
		Field terminalInfo = action.getField("terminalInfo");
		sessionInfo.setAccessible(true);
		terminalInfo.setAccessible(true);
		String ip = SystemUtil.getIpAddr(request);
		String mac = SystemUtil.getMacAddress(ip);
		String sessionStr = "ip:"+ip+",mac:"+mac;
		sessionInfo.set(handlerMethod.getBean(), sessionStr);
		String terminalStr =  "x-requested-with:" + request.getHeader("x-requested-with") +", accept :" +request.getHeader("accept")+",content-type:"+request.getHeader("content-type");
		terminalInfo.set(handlerMethod.getBean(),terminalStr);
		Method currentMethod = handlerMethod.getMethod();
		String methodName = currentMethod.getName();
		log.debug("user time is :"+ (System.currentTimeMillis() - currentTime));
		if(handlerMethod.getBean() instanceof LoginAction) {
			
		} else {
			String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
			if(!subject.isAuthenticated()) {
				
				if (RequestUtil.isSynchronized(request)) {// 不需要
					// WriterUtil.writeJSONString(response, "<script type='text/javascript'>alert('" + UN_LOGIN + "');</script>");
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter writer = response.getWriter();
	
					String script = "";
					
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='" + url+"/login.jsp" + "';";
					script += "</script>";
	
					writer.print(script);
					writer.flush();
	
				} else {
					// 判断是不是list页面
					if (methodName.indexOf("list") > -1 || methodName.indexOf("List") > -1) {
						JSONObject json = new JSONObject();
						json.accumulate("success", false);
						json.accumulate("message", UN_LOGIN);
						json.accumulate("total", 0);
						json.accumulate("rows", new JSONArray());
						json.accumulate("url", url+"/login.jsp");
						// WriterUtil.writeJSONString(response, EasyuiGrid.toString(Boolean.FALSE, UN_LOGIN, null, null));
						WriterUtil.writeJSONString(response, json.toString());
					} else {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("text/html;charset=UTF-8");
	
						PrintWriter writer = response.getWriter();
	
						String script = "";
						
						script += "<script type='text/javascript'>";
						script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
						script += "window.location.href='" + url+"/login.jsp" + "';";
						script += "</script>";
	
						writer.print(script);
						writer.flush();
					}
				}
					/*response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter writer = response.getWriter();
					
					String script = "";
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='"+basePath+"/index.jsp';";
					script += "</script>";
					writer.print(script);
					writer.flush();*/
					/*String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					response.sendRedirect(request.getContextPath()+"/login.jsp");*/
					return false;
			} else {
				Object obj = SecurityUtils.getSubject().getPrincipal();
				User user = (User)obj;
				String userCode = user.getUserCode();
				String userName = user.getUserName();
				LogConstants.put(LogConstants.USER_CODE, userCode);
			}
		}
	

		if (isRecordLog) {
			// 开始设置日志记录所需信息
			//LogConstants.put(LogConstants., PropertyUtil.getPropertyValue("current.system.appcode"));
		
			LogConstants.put(LogConstants.WEB_SESSION_INFO, sessionInfo);
			LogConstants.put(LogConstants.WEB_TERMINAL_INFO, terminalInfo);
			LogConstants.put(LogConstants.IP_STR, ip);
			LogConstants.put(LogConstants.CURRENT_TERMINAL, terminalStr);
			
			LogConstants.put(LogConstants.CURRENT_SESSION, sessionStr);
			//LogConstants.put(LogConstants.SERVICE_NAME, serviceName);
		
		}
		
		// 读取岗位配置信息，判断当前岗位操作的模块是否有权限
		String requestUrlPath = request.getRequestURL().toString();
		String pageMdlCode = request.getParameter("mdlCode");
		String currentPost = request.getParameter("currentPost");
		if (StringUtil.isNotEmpty(pageMdlCode)) {
			// 检查是否是审核模块的请求
			log.debug("检查是否有当前审核岗位的权限");
			if (requestUrlPath.contains("eliminatedCheck")) {
				boolean check = false;
				if (StringUtil.isNotEmpty(currentPost)) {
					if (pageMdlCode.substring(19, 24).equals(currentPost)) {
						Map prvg = (Map) SecurityUtils.getSubject().getSession().getAttribute("userPrvg");
						Iterator<Map.Entry<String, String>> it = prvg.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry<String, String> entry = it.next();
							
							if ("M_ELIMINATED_CHECK_PRVG".equals(entry.getKey()) && currentPost.equals(entry.getValue())) {
								// 当前岗位的权限与配置的岗位权限一致
								check = true;
							}
						}
					}
				}
				if (!check) {
					log.debug("用户操作权限不足。当前操作模块："+ pageMdlCode + ", 岗位代码：" + currentPost);
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
					
					PrintWriter writer = response.getWriter();
					
					String script = "";
					
					script += "<script type='text/javascript'>";
					script += "window.alert('权限不足，无法进行此操作');";
					script += "";
					// script += "window.location.href='userLogin_logout.action';";
					script += "</script>";
					
					writer.print(script);
					writer.flush();
					
					log.debug("此次操作拦截成功");
					
					return false;
					
				}
			}
		}
		
		
		Privilege pri = currentMethod.getAnnotation(Privilege.class);
		
		if(pri != null ) {
			Object obj = SecurityUtils.getSubject().getPrincipal();
			User user = (User)obj;
			String userCode = user.getUserCode();
			String userName = user.getUserName();
			
			Hashtable<String, Object> loginInfo = (Hashtable<String, Object>) subject.getSession().getAttribute("LOGIN_INFO");
			if(null==loginInfo || loginInfo.isEmpty()){
				log.debug("获取用户信息失败，当前用户尚未登陆或会话已失效");
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				
				PrintWriter writer = response.getWriter();
				
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				String requestUrl = request.getRequestURL().toString();
				
				String script = "";
				
				if(basePath.length() == requestUrl.indexOf("/")){
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='index.jsp';";
					script += "</script>";
				}else if(basePath.length() == requestUrl.indexOf("/index.jsp")){
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='userLogin_logout.action';";
					script += "</script>";
				}

				writer.print(script);
				writer.flush();
				
				log.debug("此次操作拦截成功，返回登录页面");
				
				return false;
			}
			
			log.debug("当前用户代码：" + userCode + ", 用户名称："+ userName);
			String modelCode = pri.modelCode();
			String prvgCode = pri.prvgCode();
			log.debug("当前访问action实例的方法对应模块：" + modelCode);
			log.debug("当前访问action实例的方法对应权限：" + prvgCode);
			
			LogConstants.put(LogConstants.MDL_CODE, modelCode);
			LogConstants.put(LogConstants.MDL_PRVG, prvgCode);
			/**
			 * 是否进行日志记录，设置在config.properties 中的 isrecord.log为true时进行记录
			 */
			
			boolean hasPrvg = subject.isPermitted(modelCode + ":"+ prvgCode);
			if(!hasPrvg) {
				log.debug("用户权限不足，无法访问当前action实例方法");
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				
				PrintWriter writer = response.getWriter();
				
				String script = "";
				
				script += "<script type='text/javascript'>";
				script += "window.alert('权限不足，无法进行此操作');";
//				script += "window.location.href='userLogin_logout.action';";
				script += "</script>";
				
				writer.print(script);
				writer.flush();
				
				log.debug("此次操作拦截成功");
				
				return false;
			}
		} else {
			return super.preHandle(request, response, handlerMethod);
		}
		/*Annotation [] annotations =method.getAnnotations();
		Privilege p = method.getAnnotation(Privilege.class);*/
		
		
		
		return super.preHandle(request, response, handler);
	}

}
