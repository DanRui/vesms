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
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.PayResultService;


@RequestMapping("/payResult")
@Controller
public class PayResultAction extends BaseAction{
	private static final Log log = LogFactory.getLog(PayResultAction.class);
	
	@Resource(name="payResultServiceImpl")
	private PayResultService payResultService;
		
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	/**
	 * 进行查询数据
	 */
	
	@RequestMapping("list")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate,String batchNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(batchNo)) {
			list.add(new PropertyFilter("EQS_batchNo",batchNo));
		}
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
		page = payResultService.getPageBySql(page, "select * from t_eliminated_apply where repeated_batch_no is null and current_post = 'BFJGBJG' and bussiness_status = '1'");
		if (page.getTotalCount() != 0) {
			page = payResultService.getPage(page, list);
		}
		try {
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayResultAction list is Error:" + e, e);
		}
		log.debug("PayResultAction list is end");
	    return returnStr;
	}
	
/*	
	@RequestMapping("batchList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getBatchList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String batchNo,String payResStatus,String payBatchTotalAmount,String toFinStartTime,String toFinEndTime, String payResStartDate , String payResEndDate,String batchType,String toFinanceStatus) throws Exception{
		String returnStr = "";
		//batchType="1";//批次类型为正常批次
		//toFinanceStatus="1";//报财务状态为已报(正常已报)
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		
		if(StringUtil.isNotEmpty(batchNo)) {
			list.add(new PropertyFilter("EQS_batchNo",batchNo));
		}
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
			list.add(new PropertyFilter("EQS_payBatchTotalAmount",payBatchTotalAmount));
		}
		if(StringUtil.isNotEmpty(toFinStartTime)) {
			list.add(new PropertyFilter("GTD_toFinanceTime",toFinStartTime));
		}
		if(StringUtil.isNotEmpty(toFinEndTime)) {
			list.add(new PropertyFilter("LTD_toFinanceTime",toFinEndTime));
		}
		if(StringUtil.isNotEmpty(payResStartDate)) {
			list.add(new PropertyFilter("GTD_payResDate",payResStartDate));
		}
		if(StringUtil.isNotEmpty(payResEndDate)) {
			list.add(new PropertyFilter("LTD_payResDate",payResEndDate));
		}
		if(StringUtil.isNotEmpty(batchType)) {
			list.add(new PropertyFilter("EQS_batchType",batchType));
		}
		list.add(new PropertyFilter("EQS_batchStatus", "1"));
		list.add(new PropertyFilter("EQS_toFinanceStatus", "1"));
		try {
			page = payResultService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayResultAction list is Error:" + e, e);
		}
		
		log.debug("PayResultAction list is end");
	    return returnStr;
	}
	
	*/
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		EliminatedApply object = payResultService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}

	
	
	/**
	 * 拨付结果标记
	 */
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "create")
	@ResponseBody
	@RequestMapping(value = "payMark" ) 
	public String markBatchApply(@RequestParam("ids")String ids,@RequestParam("payResStatus")String payResStatus,@RequestParam("faultType")String faultType,@RequestParam("faultDesc")String faultDesc) {
		log.debug("PayResultAction createBatch is start");
		boolean markOk = false;
		String result = null;
		String strids = "";
		try {
			// ids.replaceAll(",","|");
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = payResultService.markBatchApply(strids,payResStatus,faultType,faultDesc);
			if(null != result) {
				markOk = true;
			}
		} catch (Exception e) {
			log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
		}
		log.debug("PayResultAction createBatch is End");
		if(markOk) {
			return JsonUtil.toSuccessMsg(result);
		} else
		return JsonUtil.toErrorMsg("标记失败");
	}
	
	
	
/*	@RequestMapping("batchView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView batchView(@RequestParam("id")Integer id,@RequestParam(value = "type")String type) throws Exception {
		log.debug("PayResultAction batchView is start");
		String view = "PAY_RESULT.VIEW";
		BatchMain object =  payResultService.batchNoView(id);
		log.debug("PayResultAction batchView is end");
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);			
		return mv;
	}*/
	
	//
	/*
	 * 查看
	 * 	******************批次业务数据公用查询业务数据***************************
	 * 
	 * */
/*	@RequestMapping("batchResultList")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String batchApplyList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String applyNo,String batchNo,String vehicleOwener,String vehicleIdentifyNo) throws Exception{
			String returnStr="";
			List<PropertyFilter> list = new ArrayList<PropertyFilter>();
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setOrder(order);
			page.setOrderBy(orderBy);
			if(StringUtil.isNotEmpty(vehiclePlateNum)) {
				list.add(new PropertyFilter("EQS_vehiclePlateNum",vehiclePlateNum));
			}
			if(StringUtil.isNotEmpty(vehiclePlateType)) {
				list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(batchNo)) {
				list.add(new PropertyFilter("EQS_batchNo",batchNo));
			}
			if(StringUtil.isNotEmpty(vehicleOwener)) {
				list.add(new PropertyFilter("EQS_vehicleOwener",vehicleOwener));
			}
			if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
			}
			try {
				page = payResultService.getApplyPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayResultAction applylist is Error:" + e, e);
			}
				log.debug("PayResultAction applylist is end");
	    return returnStr;
	}*/
	
	
	//拨付标记查看
/*	@RequestMapping("batchNoView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView bastchNoView(@RequestParam("id")Integer id) throws Exception {
		log.debug("PayResultAction batchView is start");
		String view = "PAY_RESULT.BATCH_MARK";
		BatchMain object =  payResultService.batchNoView(id);
		log.debug("PayResultAction batchView is end");
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);			
		return mv;
	}*/
	
	
	//查询该批次内业务数据	
/*		@RequestMapping("markResultList")
		@ResponseBody
		//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
		public String markApplyList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
						   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
						   @RequestParam(value="order", defaultValue="DESC")String order, 
						   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String applyNo,String batchNo,String payResStatus) throws Exception{
			String returnStr="";
				List<PropertyFilter> list = new ArrayList<PropertyFilter>();
				Page page = new Page();
				page.setPageNo(pageNo);
				page.setPageSize(pageSize);
				page.setOrder(order);
				page.setOrderBy(orderBy);
				if(StringUtil.isNotEmpty(vehiclePlateNum)) {
					list.add(new PropertyFilter("LIKES_isExported",vehiclePlateNum));
				}
				if(StringUtil.isNotEmpty(vehiclePlateType)) {
					list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
				}
				if(StringUtil.isNotEmpty(applyNo)) {
					list.add(new PropertyFilter("EQS_applyNo",applyNo));
				}
				if(StringUtil.isNotEmpty(payResStatus)) {
					list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
				}
				if(StringUtil.isNotEmpty(batchNo)) {
					list.add(new PropertyFilter("EQS_batchNo",batchNo));
				}
				try {
					page = payResultService.getApplyPage(page, list);
					returnStr = writerPage(page);
				} catch (Exception e) {
					log.error("PayResultAction applylist is Error:" + e, e);
				}
				
					log.debug("PayResultAction applylist is end");
			
		    return returnStr;
		}*/
	
	


	
	
	
	
	/*  ---------------------------------------------------------------------------------    */
	
	
	
	/**
	 * 重报批次拨付查询数据
	 */
	@RequestMapping("repList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getRepBatchList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate,String repeatedBatchNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(repeatedBatchNo)) {
			list.add(new PropertyFilter("EQS_repeatedBatchNo",repeatedBatchNo));
		}
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
			page = payResultService.getPageBySql(page, "select * from t_eliminated_apply where repeated_batch_no is not null and current_post = 'BFJGBJG' and bussiness_status = '1'");
			if (page.getTotalCount() != 0) {
				page = payResultService.getPage(page, list);
			}
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayResultAction list is Error:" + e, e);
		}
		log.debug("PayResultAction list is end");
	    return returnStr;
	}
	
	
	
					
	
}
