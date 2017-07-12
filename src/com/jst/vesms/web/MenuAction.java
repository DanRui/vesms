
package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.common.model.Menu;
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.platformClient.client.WebServiceClient;
import com.jst.platformClient.entity.UserPrvg;
import com.jst.platformClient.utils.Constants;

@RequestMapping("/menu")
@Controller
public class MenuAction extends BaseAction {

	private static final Log log = LogFactory.getLog(MenuAction.class);
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	
	@ResponseBody
	@RequestMapping("list")
	public String list() throws Exception {
		log.debug("MenuAction list begin");
		Map<String, Object> loginInfo = null;

		List<Menu> menuList = new ArrayList<Menu>();

		JSONArray allMenu = new JSONArray();;
		JSONArray children = null;
		JSONObject parentMenu = null;
		JSONObject subMenu = null;
		String userCode = "";
		// 从基础平台加载用户权限列表
		try {
			log.debug("获取用户信息");
			if (null != this.getCurrentSession().getAttribute("LOGIN_INFO")) {
				loginInfo = (Map<String, Object>) this.getCurrentSession().getAttribute("LOGIN_INFO");

				if (loginInfo.containsKey("USER_CODE")) {
					userCode = (String) loginInfo.get("USER_CODE");
				}
			}
			log.debug("获取session中的菜单信息");
			if (null != this.getCurrentSession().getAttribute("MENU_INFO")) {
				allMenu = (JSONArray)this.getCurrentSession().getAttribute("MENU_INFO");
			} else {
				log.debug("从基础平台重新加载菜单信息");
				List<UserPrvg> userPrvgList = new WebServiceClient().getUserPrvgList(Constants.CURRENT_APPCODE, userCode, null);
				
				// 返回菜单列表
				menuList = cacheService.getMenuListByUserPrvg(userPrvgList);
		
				for (Menu menu : menuList) {
					String menuLevel = menu.getMenuLevel();
		
					if ("1".equals(menuLevel)) {
						parentMenu = new JSONObject();
						parentMenu.accumulate("menuCode", menu.getMenuCode());
						parentMenu.accumulate("menuName", menu.getMenuName());
						parentMenu.accumulate("menuPic", menu.getMenuPic());
		
						String menuCode = menu.getMenuCode();
		
						children = new JSONArray();
		
						for (Menu m : menuList) {
							if ("1".equals(m.getMenuLevel())) {
								continue;
							}
		
							String parentMenuCode = m.getParentMenuCode();
		
							if (parentMenuCode.equals(menuCode)) {
								subMenu = new JSONObject();
								subMenu.accumulate("menuCode", m.getMenuCode());
								subMenu.accumulate("menuName", m.getMenuName());
								subMenu.accumulate("menuUrl", m.getMenuUrl());
								subMenu.accumulate("openMode", m.getOpenMode());
		
								if ("dialog".equals(m.getOpenMode())) {
									subMenu.accumulate("showType", m.getShowType());
									subMenu.accumulate("width", m.getWidth());
									subMenu.accumulate("height", m.getHeight());
									subMenu.accumulate("params", m.getRemark());
								}
								
								// 将模块的代码放到菜单中，传到页面
								subMenu.accumulate("mdlCode", m.getMdlCode());
		
								children.add(subMenu);
							}
						}
		
						parentMenu.accumulate("children", children);
		
						allMenu.add(parentMenu);
					}
				}
				this.getCurrentSession().setAttribute("MENU_INFO", allMenu);
			}
			
			/*Object obj = SecurityUtils.getSubject().getPrincipal();
			User user = (User)obj;
			String userCode = user.getUserCode();
			String userName = user.getUserName();*/
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("MenuAction list error:" + e, e);
		}
		log.debug("MenuAction list end");
		return allMenu.toString();
	}
	
}

