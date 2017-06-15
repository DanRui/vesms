
package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.SysDict;
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.utils.page.Page;
import com.jst.util.StringUtil;
import com.jst.vesms.constant.SysConstant;
import com.jst.vesms.service.SysDictService;
import com.jst.vesms.service.WorkLoggingService;

@RequestMapping("/workLogging")
@Controller
public class WorkLoggingAction extends BaseAction {
	
private static final Log log = LogFactory.getLog(WorkLoggingAction.class);
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	@Resource(name = "sysDictServiceImpl")
	private SysDictService sysDictService;
	
	@Resource(name = "workLoggingServiceImpl")
	private WorkLoggingService workLoggingService;

	/**
	 * 
	 * <p>Description: 根据查询条件，获取工作记录列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@RequestMapping("list")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String post, String actionUser, String action, String actionResult, String startTime, String endTime) throws Exception{
		log.debug("WorkLoggingAction list is start");
		List<Object> list = new ArrayList<Object>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select a.id id, a.current_post post, a.action_user actionUser, a.action_name actionName, a.action_result actionResult,");
		sb.append("a.action_time actionTime, b.apply_no applyNo, b.vehicle_plate_num vehiclePlateNum, b.vehicle_plate_type vehiclePlateType,");
		sb.append("b.emission_standard emissionStandard, b.iol_type iolTpye, b.subsidies_money subsidiesMoney, b.current_post currentPost, b.bussiness_status bussinessStatus ");
		sb.append("from t_action_log a, t_eliminated_apply b ");
		sb.append("where 1 = 1 and a.apply_no = b.apply_no ");
		
		if (StringUtil.isNotEmpty(post))
		{
			sb.append("and a.current_post = '").append(post).append("' ");
		}
		if (StringUtil.isNotEmpty(actionUser)) {
			sb.append("and a.actionUser = '").append(actionUser).append("' ");
		}
		if (StringUtil.isNotEmpty(action)) {
			sb.append("and a.action_name = '").append(action).append("' ");
		}
		if (StringUtil.isNotEmpty(actionResult)) {
			sb.append("and a.actionResult = '").append(actionResult).append("' ");
		}
		if (StringUtil.isNotEmpty(startTime)) {
			sb.append("and a.action_time >= to_date('").append(startTime).append("', 'yyyy-MM-dd') ");
		}
		if (StringUtil.isNotEmpty(endTime)) {
			sb.append("and a.action_time <= to_date('").append(endTime).append("', 'yyyy-MM-dd') ");
		}
		if (StringUtil.isNotEmpty(orderBy)) {
			sb.append("order by ").append(orderBy).append(" ").append(order).append(" ");
		}
		try {
			// 查询
			page = workLoggingService.getWorkLoggingPage(sb.toString(), list, page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("WorkLoggingAction list is Error:" + e, e);
		}
		log.debug("WorkLoggingAction list is end");
	    return returnStr;
	}
	
	/**
	 * 
	 * <p>Description: 获取操作岗位列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@ResponseBody
	@RequestMapping("getPostList")
	public String getPostList() throws Exception {
		log.debug("WorkLoggingAction getPostList is start");
		String list = null;
		try {
			// 岗位列表
			list = workLoggingService.getActionPost();
		} catch (Exception e) {
			log.error("WorkLoggingAction getPostList is Error:" + e, e);
			list = null;
		}
		log.debug("WorkLoggingAction getPostList is end");
		return list;
	}
	
}

