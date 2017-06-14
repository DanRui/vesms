package com.jst.system.shiro.realm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.jst.platformClient.client.WebServiceClient;
import com.jst.platformClient.entity.User;
import com.jst.platformClient.entity.UserPrvg;
import com.jst.platformClient.utils.Constants;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;

public class UserRealm extends AuthorizingRealm{
	
	private static final Log log = LogFactory.getLog(UserRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		log.debug(getName() + " doGetAuthorizationInfo begin");
		
		SimpleAuthorizationInfo simpleAuthInfo = null;
		
		try {
//			userCode = ((Hashtable<String, Object>)SecurityUtils.getSubject().getSession().getAttribute(SessionConstant.LOGIN_INFO)).get(SessionConstant.USER_CODE).toString();
			
			log.debug("获取User对象");
			User user = (User) super.getAvailablePrincipal(principal);
			
			String quanxian = PropertyUtil.getPropertyValue("quanxian");
			simpleAuthInfo = new SimpleAuthorizationInfo();
			Map<String, String> moduleMap = new HashMap<String,String>();
			
			
			log.debug("usercode: " + user.getUserCode());
			
			//从当前会话中获取用户权限列列表,其中模块对应的权限格式为 ***$***$***, $为分割符
			List<UserPrvg> userPrvgList = new WebServiceClient().getUserPrvgList(Constants.CURRENT_APPCODE, user.getUserCode(), null);
			log.debug("获取用户权限列表");
			
			
			
			
			log.debug("循环权限列表，保存至用户授权信息");
			for(UserPrvg userPrvg : userPrvgList){
				String mdlCode = userPrvg.getMdlCode();
				String prvgString = userPrvg.getMdlPrvgList();
				moduleMap.put(mdlCode, prvgString);
				for(String prvg : prvgString.split("\\$")){
					simpleAuthInfo.addStringPermission(mdlCode + ":" + prvg);
				}
			}
			
			if(StringUtil.isNotEmpty(quanxian)){
				String[] xx = quanxian.split(",");
				for (int i = 0; i < xx.length; i++) {
					if(StringUtil.isNotEmpty(xx[i])&&xx[i].contains(":")){
						String mdl = xx[i].split(":")[0];
						String code = xx[i].split(":")[1];
						if(moduleMap.containsKey(mdl)){
							String hasCode = moduleMap.get(mdl);
							if(!hasCode.contains(code)){
								hasCode += "$"+code;
								moduleMap.put(mdl,hasCode);
							}
						}
					}
					simpleAuthInfo.addStringPermission(xx[i]);
				}
			}
			
			SecurityUtils.getSubject().getSession().setAttribute("userPrvg", moduleMap);
		} catch (Exception e) {
			log.error(getName() + " doGetAuthorizationInfo error: ", e);
		}
		
		log.debug(getName() + " doGetAuthorizationInfo end");
		
		return simpleAuthInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		log.debug(getName() + " doGetAuthenticationInfo begin");
			SimpleAuthenticationInfo simpleAuthInfo = null;
			
			try {
				UsernamePasswordToken token = (UsernamePasswordToken) authToken;
				
				log.debug("usercode: " + token.getUsername());
				
				WebServiceClient webServiceClient = new  WebServiceClient();
				
				log.debug("判断用户是否存在");
				String loginFlag = webServiceClient.checkkLogin(token.getUsername(), String.valueOf(token.getPassword()));
				
				if (StringUtil.isNotEmpty(loginFlag) && "T".equals(loginFlag)) {
					log.debug("用户存在，获取User对象");
					//这部分是使用技术平台内的用户进行注入shiro session 如许使用自己用户，请在此部分获取自己的用户信息
					User user = new WebServiceClient().getUserInfo((String) authToken.getPrincipal());
					
					log.debug("设置认证信息");
					simpleAuthInfo = new SimpleAuthenticationInfo(user, user.getUserPassword(), this.getName());
				}else{
					throw new AuthenticationException(loginFlag);
				}
			} catch (Exception e) {
				log.error(getName() + " doGetAuthenticationInfo error: " + e.getMessage(),e);
				throw new AuthenticationException(e.getMessage());
			}
			
			log.debug(getName() + " doGetAuthenticationInfo end");
		
		return simpleAuthInfo;
	}

}
