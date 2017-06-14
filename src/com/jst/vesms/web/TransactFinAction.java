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
import com.jst.common.springmvc.BaseAction;
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.TransactFinService;

@RequestMapping("/transactFin")
@Controller
public class TransactFinAction extends BaseAction{
private static final Log log = LogFactory.getLog(PayResultAction.class);
	
	@Resource(name="transactFinServiceImpl")
	private TransactFinService transactFinService;

	
	/**
	 * 进行查询数据
	 */
	
	@RequestMapping("list")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate) throws Exception{
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
			list.add(new PropertyFilter("GTD_inputTime",payResStartDate));
		}
		if(StringUtil.isNotEmpty(payResEndDate)) {
			list.add(new PropertyFilter("LTD_endTime",payResEndDate));
		}
		try {
			page = transactFinService.getPageBySql(page, "select * from t_eliminated_apply where  current_post = 'YWBJG' and bussiness_status = '1'");
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("TransactFinAction list is Error:" + e, e);
		}
		log.debug("TransactFinAction list is end");
	    return returnStr;
	}
	
	
	
	
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		EliminatedApply object = transactFinService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	

	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "create")
	@ResponseBody
	@RequestMapping(value = "conclude" ) 
	public String concludeApply(@RequestParam("ids")String ids,@RequestParam("concludeStatus")String concludeStatus) {
		log.debug("TransactFinAction concludeApply is start");
		String result = null;
		String strids = "";
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = transactFinService.concludeApply(strids,concludeStatus);			
		} catch (Exception e) {
			log.error("TransactFinAction concludeApply  is Error:"+e, e);
		}		
		log.debug("TransactFinAction concludeApply  is End");
			return 	JsonUtil.toSuccessMsg(result);
	}
	
	
	
	/**
	 * 重报批次拨付查询数据
	 */
	@RequestMapping("exceList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getExcepList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate) throws Exception{
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
			list.add(new PropertyFilter("GTD_inputTime",payResStartDate));
		}
		if(StringUtil.isNotEmpty(payResEndDate)) {
			list.add(new PropertyFilter("LTD_endTime",payResEndDate));
		}
		try {
			page = transactFinService.getPageBySql(page, "select * from t_eliminated_apply where  current_post = 'YWBJG' and bussiness_status = '-1'");
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("TransactFinAction list is Error:" + e, e);
		}
		log.debug("TransactFinAction list is end");
	    return returnStr;
	}
	
	
	
	
}
