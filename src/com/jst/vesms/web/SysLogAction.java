
package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.SystemLog;
import com.jst.common.service.ISystemLogService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.util.StringUtil;
import com.jst.vesms.service.SysLogService;

@RequestMapping("/sysLog")
@Controller
public class SysLogAction extends BaseAction {
	
	private static final Log log = LogFactory.getLog(SysLogAction.class);
	
	@Resource(name="systemLogService")
	private ISystemLogService systemLogService;
	
	@Resource(name="sysLogServiceImpl")
	private SysLogService sysLogService;
	
	/**
	 * 
	 * <p>Description: 进入系统日志查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_SYS_LOG_QUERY", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		String view = "SYS_LOG.QUERY";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	@ResponseBody
	@Privilege(modelCode="M_SYS_LOG_QUERY", prvgCode="QUERY")
	@RequestMapping("list")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy, String opeIp, String objType, String opeType, String opeUserCode, String startTime, String endTime) throws Exception {
		log.debug("SysLogAction list is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(opeIp)) {
			list.add(new PropertyFilter("EQS_opeIp",opeIp));
		}
		if(StringUtil.isNotEmpty(objType)) {
			list.add(new PropertyFilter("EQS_objType",objType));
		}
		if(StringUtil.isNotEmpty(opeType)) {
			list.add(new PropertyFilter("EQS_opeType",opeType));
		}
		if(StringUtil.isNotEmpty(opeUserCode)) {
			list.add(new PropertyFilter("EQS_opeUserCode",opeUserCode));
		}
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GED_opeTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			list.add(new PropertyFilter("LED_opeTime",endTime));
		}
		try {
			page = systemLogService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("SysLogAction list is Error:" + e, e);
		}
		log.debug("SysLogAction list is end");
	    return returnStr;
	}
	
	@RequestMapping("getOperateType")
	@ResponseBody
	public String getOperateType(String type) throws Exception {
		log.debug("SysLogAction getOperateType is start");
		String list = null;
		try {
			// 查询模块列表和操作类型列表
			list = sysLogService.getOperateType(type);
		} catch (Exception e) {
			log.error("SysLogAction getOperateType is Error:" + e, e);
			list = null;
		}
		log.debug("SysLogAction getOperateType is end");
		return list;
	}
	
	@RequestMapping("view")
	@Privilege(modelCode="M_SYS_LOG_QUERY", prvgCode="VIEW")
	public ModelAndView view(String id) throws Exception {
		
		String view = "SYS_LOG.VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		SystemLog sysLog = sysLogService.getById(id);
		mv.addObject("v", sysLog);
		
		return mv;
		
	}

}

