package com.jst.demo.login.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.internal.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jst.common.springmvc.BaseAction;
import com.jst.platformClient.client.WebServiceClient;
import com.jst.platformClient.entity.Dept;
import com.jst.platformClient.entity.User;
import com.jst.system.SystemConstants;
import com.jst.system.security.LoginSecurity;
import com.jst.vesms.service.CallService;

@RequestMapping("/login")
@Controller
public class LoginAction extends BaseAction{
	
	
	private static final Log log = LogFactory.getLog(LoginAction.class);
	
	@Resource(name = "callServiceImpl")
	private CallService callService;
	
	/**
	 * 生成 验证码
	 * @return
	 */
	@RequestMapping("getCode")
	public void createCode(HttpServletResponse response,HttpSession session) throws Exception{
        response.setCharacterEncoding("utf-8");  
        response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		// 在内存中创建图象
		int width = 70, height = 23;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// 获取图形上下文
		Graphics g = image.getGraphics();
		
		// 生成随机类
		Random random = new Random();
		
		// 设定背景色
		g.setColor(_getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		
		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		
		// 画边框
		g.setColor(_getRandColor(160, 200));
		g.drawRect(0, 0, width - 1, height - 1);
		
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(_getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 10 * i +4, 20);
		}

		// 将认证码存入SESSION
		session.setAttribute(SystemConstants.VERIFY_CODE, sRand);
		// 图象生效
		g.dispose();  
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	@RequestMapping("login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("verifycode")String verifycode,HttpSession session ,RedirectAttributes attributes) throws Exception{
//		return redirectPage("INDEX")+SystemConstants.JSP_SUFFIX;
		String errorMessage = null;
		boolean loginstate = false;
		Subject currentUser = SecurityUtils.getSubject();
		session.removeAttribute("org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS");
		log.debug("判断当前用户是否已经登录");
		if (null == session.getAttribute(SystemConstants.VERIFY_CODE) && SecurityUtils.getSubject().isAuthenticated()) {
			log.debug("用户已登录，无需重新验证");
			try {
				getIndexData(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logremark = "当前会话拥有用户信息，系统自动登录";
			loginstate = true;
		}else{
			Object sessionVerifyCode = session.getAttribute(SystemConstants.VERIFY_CODE);
			if(null!=sessionVerifyCode && null!= verifycode && verifycode.equals(sessionVerifyCode.toString())){
				log.debug("验证码校验成功");
				log.debug("清除当前会话中的验证码");
				session.removeAttribute(SystemConstants.VERIFY_CODE);
				log.debug("实例化登录安全对象");
				LoginSecurity loginSecurity = new LoginSecurity(username);

				boolean islock = false;
				log.debug("检查用户登录锁定状态");
				if (loginSecurity.isLocked()) {
					log.debug("用户已被锁定，尝试解锁");
					if (!loginSecurity.releaseLock()) {
						//addActionMessage("此账户已被锁定，" + loginSecurity.getUnlockTimeRemaining() + "分钟后解锁");
						errorMessage = "此账户已被锁定，" + loginSecurity.getUnlockTimeRemaining() + "分钟后解锁";
						logremark = "当前用户已被锁定";
						islock = true;
					}
				}

				log.debug("用户未被锁定");

				if(!islock){
					log.debug("开始登录");
					try {
						currentUser.login(new UsernamePasswordToken(username, password));
					} catch (UnknownAccountException e) {// 账号不存在
						log.error("userLogin_login error: " + e);
	
						errorMessage = "用户名或密码错误，登录失败";
					} catch (IncorrectCredentialsException e) {// 密码错误
						log.error("userLogin_login error: " + e);
	
						errorMessage = "用户名或密码错误，登录失败";
					} catch (AuthenticationException e) {// 其他错误
						log.error("userLogin_login error: " + e);
						if(e.getMessage().contains("权限")){
							errorMessage = e.getMessage();
						}else {
							errorMessage = "用户名或密码错误，登陆失败";
						}
					}
	
					if (null != errorMessage) {
						log.debug("登录失败");
	
						log.debug("设置登录错误信息");
						loginSecurity.setErrorInfo();
	
						//addActionMessage(errorMessage);
						logremark = errorMessage;
					} else {
	
						log.debug("登录成功");
						Map<String, Object> loginInfo = new Hashtable<String, Object>();
						try {
							SecurityUtils.getSubject().isPermitted("aaa");
							User user = new WebServiceClient().getUserInfo(username);
							Dept dept = new WebServiceClient().getDept(user.getDeptId());
							loginInfo.put("USER_CODE", user.getUserCode());
							loginInfo.put("USER_NAME", user.getUserName());
							loginInfo.put("DEPT_ID", user.getDeptId());
							loginInfo.put("DEPT_NAME", dept.getDeptName());
							session.setAttribute("LOGIN_INFO", loginInfo);
							
						} catch (Exception e) {
							log.error("获取登录用户失败！");
						}
						log.debug("清除登陆错误信息");
						loginSecurity.clearErrorInfo();
						try {
							getIndexData(session);
						} catch (Exception e) {
							log.error("获取工作提醒数据异常！");
							e.printStackTrace();
						}
						loginstate = true;
					}
				}
			}else{
				errorMessage = "验证码错误，请重新输入";
			}
		}
		String page = null;
		if(loginstate){
			page =  redirectPage("INDEX")+SystemConstants.JSP_SUFFIX;
		}else{
			attributes.addFlashAttribute("errormessage", errorMessage);
			page = redirectPage("LOGIN")+SystemConstants.JSP_SUFFIX;
		}
		return page; 
	} 
	
	@RequestMapping("logout")
	public String logout() {
		log.debug("userLogin_logout begin");

		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			log.error("userLogin_logout error: " + e);
		}

		log.debug("userLogin_logout end");

		return redirectPage("LOGIN")+SystemConstants.JSP_SUFFIX;
	}
	
	private Color _getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 
	 * <p>Description: 获取首页提醒数据列表</p>
	 * @param session 会话对象  HttpSession
	 * @return void
	 *
	 */
	@RequestMapping("refreshWorkData")
	public void getIndexData(HttpSession session) throws Exception {
		// 受理信息
		Map<String, Object> applyInfo = new HashMap<String, Object>();
		// 会计初审岗业务
		Map<String, Object> kjcsgApply = new HashMap<String, Object>();
		// 窗口审核岗业务
		Map<String, Object> ckshgApply = new HashMap<String, Object>();
		// 科长审核岗业务
		Map<String, Object> kzshgApply = new HashMap<String, Object>();
		// 处长审核岗业务
		Map<String, Object> czshgApply = new HashMap<String, Object>();
		// 会计复审岗业务
		Map<String, Object> kjfsgApply = new HashMap<String, Object>();
		// 拨付申报岗业务
		Map<String, Object> bfsbgApply = new HashMap<String, Object>();
		// 拨付结果标记岗业务
		Map<String, Object> bfjgbjgApply = new HashMap<String, Object>();
		// 业务办结岗业务
		Map<String, Object> ywbjgApply = new HashMap<String, Object>();
		// 超期预警业务
		Map<String, Object> cqywApply = new HashMap<String, Object>();
		
		List<Map<String, Object>> workdata = new ArrayList<Map<String, Object>>();
		
		String callName = "{call PKG_QUERY.p_get_workdata(?)}";
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		outParams.put(1, OracleTypes.CURSOR);
		
		List<Map<String, Object>> result = callService.call(callName, null, outParams, "procedure");
		if (result != null && result.size() > 0) {
			applyInfo = result.get(0);
			kjcsgApply = result.get(1);
			ckshgApply = result.get(2);
			kzshgApply = result.get(3);
			czshgApply = result.get(4);
			kjfsgApply = result.get(5);
			bfsbgApply = result.get(6);
			bfjgbjgApply = result.get(7);
			ywbjgApply = result.get(8);
			cqywApply = result.get(9);
			
			workdata.add(applyInfo);
			workdata.add(kjcsgApply);
			workdata.add(ckshgApply);
			workdata.add(kzshgApply);
			workdata.add(czshgApply);
			workdata.add(kjfsgApply);
			workdata.add(bfsbgApply);
			workdata.add(bfjgbjgApply);
			workdata.add(ywbjgApply);
			workdata.add(cqywApply);
			
			session.setAttribute("WORKDATA", workdata);
		} 
		
		System.out.println(result);
	}
	
}
