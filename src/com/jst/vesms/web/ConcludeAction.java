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
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ConcludeService;


@RequestMapping("/conclude")
@Controller
public class ConcludeAction extends BaseAction{
	private static final Log log = LogFactory.getLog(ConcludeAction.class);
	
	@Resource(name="concludeServiceImpl")
	private ConcludeService concludeService;
		
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	
	@RequestMapping("concludeView")
	@Privilege(modelCode = "M_NOR_APPLY_CONCLUDE", prvgCode = "QUERY")
	public ModelAndView concludeView() throws Exception {
		log.debug("ConcludeAction concludeView is start");
		String view = "CONCLUDE.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		log.debug("ConcludeAction concludeView is end");
		return mv;
	}
	
	@RequestMapping("conculdeFaultView")
	@Privilege(modelCode = "M_FAULT_APPLY_CONCLUDE", prvgCode = "QUERY")
	public ModelAndView conculdeFaultView() throws Exception {
		log.debug("ConcludeAction conculdeFaultView is start");
		String view = "CONCLUDE.FAULT_LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		log.debug("ConcludeAction conculdeFaultView is end");
		return mv;
	}
	
	/**
	 * 进行查询数据
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_NOR_APPLY_CONCLUDE", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate) throws Exception{
		log.debug("ConcludeAction list is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			list.add(new PropertyFilter("EQS_vehiclePlateNum",vehiclePlateNum));
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(payResStartDate)) {
			list.add(new PropertyFilter("GED_inputTime",payResStartDate));
		}
		if(StringUtil.isNotEmpty(payResEndDate)) {
			list.add(new PropertyFilter("LED_endTime",payResEndDate));
		}
		list.add(new PropertyFilter("EQS_currentPost", "YWBJG"));
		list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
		try {
			page = concludeService.getApplyPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ConcludeAction list is Error:" + e, e);
		}
		log.debug("ConcludeAction list is end");
	    return returnStr;
	}
	
	
	
	
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		log.debug("ConcludeAction view is start");
		String view = "ELIMINATED_APPLY.VIEW";
		EliminatedApply object = concludeService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		log.debug("ConcludeAction view is end");
		return mv;
	}
	
	
	

	@ResponseBody
	@RequestMapping(value = "conclude" ) 
	@Privilege(modelCode = "M_NOR_APPLY_CONCLUDE",prvgCode = "CONCLUDE_MARK")
	public String concludeApply(@RequestParam("ids")String ids,@RequestParam("concludeStatus")String concludeStatus) {
		log.debug("ConcludeAction concludeApply is start");
		String result = null;
		String strids = "";
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = concludeService.concludeApply(strids,concludeStatus);			
		} catch (Exception e) {
			log.error(" concludeApply  is Error:"+e, e);
		}		
		log.debug("ConcludeAction concludeApply  is End");
		return 	JsonUtil.toSuccessMsg(result);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "faultConclude" ) 
	@Privilege(modelCode = "M_FAULT_APPLY_CONCLUDE",prvgCode = "CONCLUDE_MARK")
	public String faultConcludeApply(@RequestParam("ids")String ids,@RequestParam("concludeStatus")String concludeStatus) {
		log.debug("ConcludeAction faultConcludeApply is start");
		String result = null;
		String strids = "";
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = concludeService.concludeApply(strids,concludeStatus);			
		} catch (Exception e) {
			log.error(" ConcludeAction faultConcludeApply  is Error:"+e, e);
		}		
		log.debug("ConcludeAction faultConcludeApply  is End");
			return 	JsonUtil.toSuccessMsg(result);
	}
	
	
	

	@RequestMapping("faultList")
	@ResponseBody
	@Privilege(modelCode = "M_FAULT_APPLY_CONCLUDE", prvgCode = "QUERY")
	public String getFaultpList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate) throws Exception{
		log.debug("ConcludeAction getFaultpList  is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			list.add(new PropertyFilter("EQS_vehiclePlateNum",vehiclePlateNum));
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(payResStartDate)) {
			list.add(new PropertyFilter("GED_inputTime",payResStartDate));
		}
		if(StringUtil.isNotEmpty(payResEndDate)) {
			list.add(new PropertyFilter("LED_endTime",payResEndDate));
		}
		list.add(new PropertyFilter("EQS_currentPost", "YWBJG"));
		list.add(new PropertyFilter("EQS_bussinessStatus", "-1"));
		try {
			page = concludeService.getApplyPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ConcludeAction getFaultpList is Error:" + e, e);
		}
		log.debug("ConcludeAction getFaultpList  is end");
	    return returnStr;
	}
}

