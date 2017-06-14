package com.jst.vesms.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.types.Id;
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
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.VehicleRecycle;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PayApplyService;
import com.jst.vesms.service.VehicleRecycleService;
import com.jst.vesms.service.impl.PayApplyServiceImpl;
import com.jst.vesms.util.excel.ExcelProperties;
import com.jst.vesms.util.excel.exp.ExportExcel;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;


@RequestMapping("/payApply")
@Controller
public class PayApplyAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(PayApplyAction.class);
	
	@Resource(name="payApplyServiceImpl")
	private PayApplyService payApplyService;
		
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
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
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String vehicleType,String vehicleOwner, String applyNo,String vehicleIdentifyNo,String batchNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			list.add(new PropertyFilter("LIKES_vehiclePlateNum",vehiclePlateNum));
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
		}
		if(StringUtil.isNotEmpty(vehicleType)) {
			list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
		}
		try {
			page = payApplyService.getPageBySql(page, "select * from t_eliminated_apply where batch_no is null and current_post = 'BFSBG' and bussiness_status='1'");
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		
		log.debug("PayApplyAction list is end");
	    return returnStr;
	}
	
	
	
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		EliminatedApply object = payApplyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	
	/**
	 * 生成批次号
	 */
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "create")
	@ResponseBody
	@RequestMapping(value = "create" ) 
	public String createBatch(@RequestParam("ids")String ids) {
		log.debug("PayApplyAction createBatch is start");
	//	boolean createOk = false;
		String result = null;
		String strids = "" ;
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = payApplyService.createBatch(strids);
			/*if(null != result) {
				createOk = true;
			}*/
		} catch (Exception e) {
			log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
			result = "VehicleRecycleAction syncVehicleInfo is Error:"+e;
		}		
		log.debug("PayApplyAction createBatch is End");
		//if(createOk) {
			return JsonUtil.toSuccessMsg(result);
		//} else
		//return JsonUtil.toErrorMsg("批次生成失败");
	}
	
	

	/**
	 *	正常内部批次调整查询
	 */
	@RequestMapping("batchAdjustList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String batchAdjustList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String batchNo,String toFinanceStatus,String payResStatus,String payBatchTotalAmount, String createStartDate , String createEndDate,String batchType) throws Exception{
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
		if(StringUtil.isNotEmpty(toFinanceStatus)) {
			list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
		}
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
			list.add(new PropertyFilter("EQS_applyNo",payBatchTotalAmount));
		}
		if(StringUtil.isNotEmpty(createStartDate)) {
			list.add(new PropertyFilter("GTD_createDate",createStartDate));
		}
		if(StringUtil.isNotEmpty(createEndDate)) {
			list.add(new PropertyFilter("LTD_createDate",createEndDate));
		}
		list.add(new PropertyFilter("EQS_batchStatus", "1"));
		list.add(new PropertyFilter("EQS_batchType","1"));
		list.add(new PropertyFilter("EQS_toFinanceStatus","0"));
		try {
			page = payApplyService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		log.debug("PayApplyAction list is end");
	    return returnStr;
	}
	
	
	
	
	
	
	/**
	 *	正常内部批次查询
	 */
	@RequestMapping("batchList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getBatchList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String batchNo,String toFinanceStatus,String payResStatus,String payBatchTotalAmount, String createStartDate , String createEndDate,String batchType) throws Exception{
		log.debug("PayApplyAction getBatchList is start");
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
		if(StringUtil.isNotEmpty(toFinanceStatus)) {
			list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
		}
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
			list.add(new PropertyFilter("EQS_applyNo",payBatchTotalAmount));
		}
		if(StringUtil.isNotEmpty(createStartDate)) {
			list.add(new PropertyFilter("GTD_createDate",createStartDate));
		}
		if(StringUtil.isNotEmpty(createEndDate)) {
			list.add(new PropertyFilter("LTD_createDate",createEndDate));
		}
		list.add(new PropertyFilter("EQS_batchType","1"));
		list.add(new PropertyFilter("EQS_toFinanceStatus","0"));
		try {
			page = payApplyService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction getBatchList is Error:" + e, e);
		}
		log.debug("PayApplyAction getBatchList is end");
	    return returnStr;
	}
	
	
	
	

	@RequestMapping("toExcelPreview")
	public ModelAndView toExcelPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "PAY_APPLY.EXPORT_PREVIEW";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	

	
	//导出预览
	@RequestMapping("exportPreview")
	public String exportPreview (Integer id,String batchNo,HttpServletResponse response) {
		log.debug("PayApplyAction excelList is start");
		int count=0;
		try {
			response.setHeader("Content-disposition", "attachment;  filename="
					+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			OutputStream outputStream = response.getOutputStream();
			List<EliminatedApply> list = new ArrayList<EliminatedApply>();
			list = payApplyService.getBatchApplyList(batchNo);
			List<String[]> dataList = new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				count++;
				String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
						apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
				dataList.add(strings);
			}
			ExcelProperties excelProperties=new ExcelProperties();
			excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表（预览）");
			ExportExcel.exportExcelInWeb(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream);
		} catch (Exception e) {
			log.error("PayApplyAction excelList is Error:"+e, e);
		}		
		log.debug("PayApplyAction excelList is End");
		return null;
	}
	
	
	
	// 内部批次报财务导出
	@RequestMapping("toExportExcel")
	public ModelAndView toExportExcel(@RequestParam("id")Integer id) throws Exception {
		String view = "PAY_APPLY.EXPORT_EXCEL";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	/*
	 * 
	 * 	******************批次公用查看***************************
	 * 
	 * 
	 * */
	
	@RequestMapping("repBatchView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView repBatchView(@RequestParam("id")Integer id,@RequestParam(value = "type")String type) throws Exception {
		log.debug("payApplyAction repBatchView is start");
		String view = "PAY_APPLY.REP_VIEW";
		BatchMain object =  payApplyService.batchNoView(id);
		log.debug("payApplyAction repBatchView is end");
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);			
		return mv;
	}
	
	
	
	
	@RequestMapping("batchView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView bastchView(@RequestParam("id")Integer id,@RequestParam(value = "type")String type) throws Exception {
		log.debug("payApplyAction batchView is start");
		String view = "PAY_APPLY.VIEW";
		BatchMain object =  payApplyService.batchNoView(id);
		log.debug("payApplyAction batchView is end");
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);			
		return mv;
	}
	
	
	
	
	
	//查询该批次内业务数据	
	@RequestMapping("batchApplyList")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String batchApplyList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String applyNo,String batchNo,String repeatedBatchNo) throws Exception{
			String returnStr="";
			List<PropertyFilter> list = new ArrayList<PropertyFilter>();
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setOrder(order);
			page.setOrderBy(orderBy);
			if(StringUtil.isNotEmpty(vehiclePlateNum)) {
				list.add(new PropertyFilter("LIKES_vehiclePlateNum",vehiclePlateNum));
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
			if(StringUtil.isNotEmpty(repeatedBatchNo)) {
				list.add(new PropertyFilter("EQS_repeatedBatchNo",repeatedBatchNo));
			}
			try {
				page = payApplyService.getApplyPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction applylist is Error:" + e, e);
			}
			
				log.debug("PayApplyAction applylist is end");
	    return returnStr;
	}
	
	
	
	
	//调整批次中查看单个批次号数据
	@RequestMapping(value="batchNoList")
	@ResponseBody
	public ModelAndView batchNoList(@RequestParam("id")Integer id){
		log.debug("PayApplyAction batchNoList is start");
		String adjust = "PAY_APPLY.ADJUST";
		BatchMain object = payApplyService.batchNoView(id);
		log.debug("PayApplyAction batchNoList is End");
		ModelAndView mv = new ModelAndView(getReturnPage(adjust));
		mv.addObject("v", object);
		return mv;
	}
	
	
	 //批次调整业务数据查询 
	@RequestMapping("adjustList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getBatchDetail(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum,String vehiclePlateType,String applyNo,String batchNo,String batchStatus,String toFinanceStatus,String batchId) throws Exception{
			String returnStr = "";
			List<PropertyFilter> list = new ArrayList<PropertyFilter>();
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setOrder(order);
			page.setOrderBy(orderBy);
			if(StringUtil.isNotEmpty(batchNo)) {
				list.add(new PropertyFilter("EQS_batchNo",batchNo));
			}
			if(StringUtil.isNotEmpty(vehiclePlateNum)) {
				list.add(new PropertyFilter("EQS_vehiclePlateNum",vehiclePlateNum));
			}
			if(StringUtil.isNotEmpty(vehiclePlateType)) {
				list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(toFinanceStatus)) {
				list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
			}
			try {
				page = payApplyService.getApplyPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction list is Error:" + e, e);
			}
			
			log.debug("PayApplyAction list is end");
		
	    return returnStr;
	}

	
	//批次业务删除
	@ResponseBody
	@RequestMapping("applyDelete")
	//@Privilege(modelCode = "M_TEST_MANAGER" ,prvgCode = "DELETE")
	public String deleteBatchDetail(@RequestParam("ids")String ids,@RequestParam("batchId")String batchId) throws Exception {
		log.debug("payApplyAction applyDelete is start");
		boolean deleteOk = false;
		String strids="";
		String idString[] = ids.split(",");
		for (int i = 0; i < idString.length; i++) {
			strids=strids+idString[i].concat("|");
		}
		strids=strids.substring(0, strids.length()-1);
		String returnObject = payApplyService.deleteApply(batchId,strids);
		if(null != returnObject) {
			deleteOk=true;
		}
		log.debug("payApplyAction applyDelete is End");
		if(deleteOk) {
			return JsonUtil.toSuccessMsg(returnObject);
		} else
			return JsonUtil.toErrorMsg("业务单删除有误");
	}
	
	//批次作废
	@ResponseBody
	@RequestMapping("batchCancel")
	public String batchCancel(@RequestParam("ids")String ids) {
		log.debug("PayApplyAction batchCancel is start");
		String result=null;
		String strids="";
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result= payApplyService.batchCancel(strids);
		} catch (Exception e) {
			log.error("PayApplyAction batchCancel is Error:"+e,e);
		}		
		log.debug("PayApplyAction batchCancel is End");
			return JsonUtil.toSuccessMsg(result);
	}
	
	
	//批次调整新增业务单查询
	@ResponseBody
	@RequestMapping("batchAddList")
	public String getNoBatchApplyList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String vehicleType,String vehicleOwner, String applyNo,String vehicleIdentifyNo,String batchNo,String batchType) throws Exception{
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
		if(StringUtil.isNotEmpty(vehicleType)) {
			list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			list.add(new PropertyFilter("EQS_vehicleOwner",vehicleOwner));
		}
		if(StringUtil.isNotEmpty(batchNo)) {
			list.add(new PropertyFilter("EQS_batchNo",batchNo));
		}
		try {
			page = payApplyService.getApplyPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		
		log.debug("PayApplyAction list is end");
		return returnStr;
	}
	
	//批次调整新增跳转
		@ResponseBody
		@RequestMapping("batchAddApplyView")
		public ModelAndView batchAddApplyView(@RequestParam("batchId")Integer batchId) throws Exception {
			String view = "PAY_APPLY.BATCH_ADD_APPLY";
			BatchMain object = payApplyService.batchNoView(batchId);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}
	//batchAddApplyView
	
	
	
	
	//批次调整新增业务单
	@ResponseBody
	@RequestMapping("addApplyToBatch")
	public String adjustBatchDetail(@RequestParam("ids")String ids,@RequestParam("batchId")Integer batchId) {
		log.debug("PayApplyAction adjustBatchDetail is start");
		boolean addToBatch = false;
		String result=null;
		String batchIdStr=batchId+"";
		String strids="";
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result= payApplyService.addApply(batchIdStr,strids);
			if(null != result) {
				addToBatch = true;
			}
		} catch (Exception e) {
			log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
		}		
		log.debug("PayApplyAction adjustBatchDetail is End");
		if(addToBatch) {
			return JsonUtil.toSuccessMsg(result);
		} else
		return JsonUtil.toErrorMsg("增加业务单失败");
	}
	
	
	//正常报财务批次查询
		@ResponseBody
		@RequestMapping("toFinanceList")
		public String getToFinanceList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
				   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
				   @RequestParam(value="order", defaultValue="DESC")String order, 
				   @RequestParam(value="sort", defaultValue="id")String orderBy,String batchNo,Integer toFinanceNo,String isExported,String toFinanceStatus,String payResStatus,String payBatchTotalAmount, String createStartDate , String createEndDate,String batchType) throws Exception{
			log.debug("PayApplyAction toFinanceList is start");
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
			if(StringUtil.isNotEmpty(isExported)) {
				list.add(new PropertyFilter("EQS_isExported",isExported));
			}
			if(StringUtil.isNotEmpty(toFinanceStatus)) {
				list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
			}
			if(StringUtil.isNotEmpty(payResStatus)) {
				list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
			}
			if(StringUtil.isNotEmpty(createStartDate)) {
				list.add(new PropertyFilter("GTD_createDate",createStartDate));
			}
			if(StringUtil.isNotEmpty(createEndDate)) {
				list.add(new PropertyFilter("LTD_createDate",createEndDate));
			}
			if(StringUtil.isNotEmpty(toFinanceNo+"")) {
				list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo+""));
			}
			list.add(new PropertyFilter("EQS_toFinanceStatus", "1"));//批次报财务状态为已报
			list.add(new PropertyFilter("EQS_batchType", "1"));
			try {
				page = payApplyService.getPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction toFinanceList is Error:" + e, e);
			}
			
			log.debug("PayApplyAction toFinanceList is end");
			return returnStr;
		}
	
		
		
		@RequestMapping("confirmExportExcel")
		public ModelAndView confirmExportExcel(@RequestParam("id")Integer id) throws Exception {
			String view = "PAY_APPLY.CONFIRM_EXCEL";
			BatchMain object = payApplyService.getObj(id);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}
		
		
	//报财务确认
	@RequestMapping("confirmBatch")
	@ResponseBody
	public String confirmBatch(@RequestParam("id")String id) {
		log.debug("PayApplyAction confirmBatch is start");
		boolean confirmOk = false;
		String result = null;
		try {
			result= payApplyService.confirmBatch(id);
			if(null != result) {
				confirmOk = true;
			}
		} catch (Exception e) {
			log.error("PayApplyAction confirmBatch is Error:"+e, e);
		}		
		log.debug("PayApplyAction createBatch is End");
		if(confirmOk) {
			return JsonUtil.toSuccessMsg(result);
		} else
		return JsonUtil.toErrorMsg("报财务异常");
		
	}	
	
	
	
	
	//报财务查询导出
	@RequestMapping("financeExcel")
	public ModelAndView financeExcel(@RequestParam("id")Integer id) {
		String view = "PAY_APPLY.TO_FINANCE_EXCEL";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}	
	
	
	
	//批次报财务导出
		@RequestMapping("toFinanceExcel")
		public ModelAndView toFinanceExcel(@RequestParam("id")Integer id) {
			String view = "PAY_APPLY.TO_EXPORT_EXCEL";
			BatchMain object = payApplyService.getObj(id);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}	
	
		//正常报财务批次导出(加密)
		@RequestMapping("confirmBatchExcel")
		@ResponseBody
		public String confirmBatchExcel(@RequestParam("id")String id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,String password,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPreview is start");
			int count =0;
			try {
				response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
				// 定义输出类型
				response.setContentType("application/vnd.ms-excel");
				OutputStream outputStream = response.getOutputStream();
				//result= payApplyService.batchExport(id);
				List<EliminatedApply> list = new ArrayList<EliminatedApply>();
				list = payApplyService.getBatchApplyList(batchNo);
				List<String[]> dataList = new ArrayList<String[]>();
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					count++;
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
							apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+toFinanceNo+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
					dataList.add(strings);
				}
				//outputStream = new FileOutputStream(new File("d:\\sss.xls"));
				ExcelProperties excelProperties=new ExcelProperties();
				excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+toFinanceNo+"批)");
				excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			//	OutputStream outputStream = null;
				ExportExcel.exportExcelInWebs(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream,password);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			return null;
		}	
	
	
	//正常报财务批次查看
		@RequestMapping("confirmBatchLook")
		@ResponseBody
		public String confirmBatchLook(@RequestParam("id")String id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPreview is start");
			int count =0;
			try {
				response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
				// 定义输出类型
				response.setContentType("application/vnd.ms-excel");
				OutputStream outputStream = response.getOutputStream();
				//result= payApplyService.batchExport(id);
				List<EliminatedApply> list = new ArrayList<EliminatedApply>();
				list = payApplyService.getBatchApplyList(batchNo);
				List<String[]> dataList = new ArrayList<String[]>();
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					count++;
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
							apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+toFinanceNo+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
					dataList.add(strings);
				}
				//outputStream = new FileOutputStream(new File("d:\\sss.xls"));
				ExcelProperties excelProperties=new ExcelProperties();
				excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+toFinanceNo+"批)");
				excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			//	OutputStream outputStream = null;
				ExportExcel.exportExcelInWeb(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			return null;
		}	
	
	
	/*----------------------------------------------------------------------*/
	
	
	/**
	 * 查询待生成重报批次业务数据
	 */
	@RequestMapping("repList")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String repList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String vehicleType,String vehicleOwner, String applyNo,String vehicleIdentifyNo,String batchNo,String toFinanceStatus,String repeatedBatchNo,String currentPost,String bussinessStatus) throws Exception{
		String returnStr = "";
		//判断条件 ：当前岗位在拨付申报岗，且业务状态为“正常”，且首报批次号 TO_FINANCE_STATUS) <= -2,且REPEATED_BATCH_NO is null
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
			if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
			}
			if(StringUtil.isNotEmpty(vehicleType)) {
				list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("EQS_vehicleOwner",vehicleOwner));
			}
			try {
				list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
				page = payApplyService.getPageBySql(page, "select * from t_eliminated_apply where (batch_no is not null and current_post = 'BFSBG' and to_number(TO_FINANCE_STATUS) <= -2 and REPEATED_BATCH_NO is null)");
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction repList is Error:" + e, e);
			}
			
			log.debug("PayApplyAction list is end");
		return returnStr;
	}
	
	
	//查看待生成重报批次的详细信息
	@RequestMapping("repView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView repView(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "VEHICLE_RECYCLE.VIEW";
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)) {
			view = "VEHICLE_RECYCLE.EDIT";
		}
		EliminatedApply object = payApplyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	/**
	 * 生成重报批次号
	 */
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "create")
	@ResponseBody
	@RequestMapping(value = "repCreate" ) 
	public String createRepBatch(@RequestParam("ids")String ids) {
		log.debug("PayApplyAction createBatch is start");
		boolean createOk = false;
		String result = null;
		String strids = "" ;
		try {
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			result = payApplyService.createRepBatch(strids);
			if(null != result) {
				createOk = true;
			}
		} catch (Exception e) {
			log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
		}		
		log.debug("PayApplyAction createBatch is End");
		if(createOk) {
			return JsonUtil.toSuccessMsg(result);
		} else
		return JsonUtil.toErrorMsg("批次生成失败");
	}
	
	
	
	/**
	 *	查询待导出重报批次数据
	 */
	@RequestMapping("repExpBatchList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getRepExpBatchList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String toFinanceNo,String batchNo,String isExported,String toFinanceStatus,String payResStatus,String payBatchTotalAmount,String expStartDate,String expEndDate, String createStartDate , String createEndDate,String batchType) throws Exception{
		//batchType="1";
		//int expValue=0;
		//expValue=Integer.parseInt(isExported);
		String returnStr = "";
		//if(expValue!=1){
			List<PropertyFilter> list = new ArrayList<PropertyFilter>();
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setOrder(order);
			page.setOrderBy(orderBy);
			if(StringUtil.isNotEmpty(batchNo)) {
				list.add(new PropertyFilter("EQS_batchNo",batchNo));
			}
			if(StringUtil.isNotEmpty(isExported)) {
				list.add(new PropertyFilter("EQS_isExported",isExported));
			}
			if(StringUtil.isNotEmpty(toFinanceStatus)) {
				list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
			}
			if(StringUtil.isNotEmpty(payResStatus)) {
				list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
			}
			if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
				list.add(new PropertyFilter("EQS_applyNo",payBatchTotalAmount));
			}
			if(StringUtil.isNotEmpty(expStartDate)) {
				list.add(new PropertyFilter("GTD_expRecentDate",expStartDate));
			}
			if(StringUtil.isNotEmpty(expEndDate)) {
				list.add(new PropertyFilter("LTD_expRecentDate",expEndDate));
			}
			if(StringUtil.isNotEmpty(createStartDate)) {
				list.add(new PropertyFilter("GTD_createDate",createStartDate));
			}
			if(StringUtil.isNotEmpty(createEndDate)) {
				list.add(new PropertyFilter("LTD_createDate",createEndDate));
			}
			if(StringUtil.isNotEmpty(toFinanceNo)) {
				list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo));
			}
			list.add(new PropertyFilter("EQS_batchStatus","1"));
			list.add(new PropertyFilter("EQS_batchType","2"));
			try {
				page = payApplyService.getPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction list is Error:" + e, e);
			}
			
				log.debug("PayApplyAction list is end");
		//}
	    return returnStr;
	}
	
	
	
	
	//去导出重报批次预览
	@RequestMapping("toExportRepBatch")
	public ModelAndView toExportRepBatch(@RequestParam("id")Integer id) throws Exception {
		String view = "PAY_APPLY.REP_Batch_PREVIEW";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	//导出重报批次预览
	@RequestMapping("repBatchPreview")
	public String repBatchPreview (Integer id,String batchNo,HttpServletResponse response) {
		log.debug("PayApplyAction excelList is start");
		int count=0;
		try {
			response.setHeader("Content-disposition", "attachment;  filename="
					+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			OutputStream outputStream = response.getOutputStream();
			List<EliminatedApply> list = new ArrayList<EliminatedApply>();
			list = payApplyService.getRepBatchApplyList(batchNo);
			List<String[]> dataList = new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				count++;
				String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
						apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
				dataList.add(strings);
			}
			ExcelProperties excelProperties=new ExcelProperties();
			excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表（预览）");
			excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			ExportExcel.exportExcelInWeb(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream);
		} catch (Exception e) {
			log.error("PayApplyAction excelList is Error:"+e, e);
		}		
		log.debug("PayApplyAction excelList is End");
		return null;
	}
	
	
	

	
	/**
	 *	重报内部批次调整 查询
	 *	重报批次报财务 查询
	 */
	@RequestMapping("repBatchAdjust")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String repBatchAdjust(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, Integer toFinanceNo,String batchNo,String isExported,String toFinanceStatus,String payResStatus,String payBatchTotalAmount,String expStartDate,String expEndDate, String createStartDate , String createEndDate,String batchType ) throws Exception{
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
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
			list.add(new PropertyFilter("EQS_applyNo",payBatchTotalAmount));
		}
		if(StringUtil.isNotEmpty(createStartDate)) {
			list.add(new PropertyFilter("GTD_createDate",createStartDate));
		}
		if(StringUtil.isNotEmpty(createEndDate)) {
			list.add(new PropertyFilter("LTD_createDate",createEndDate));
		}
		if(StringUtil.isNotEmpty(toFinanceNo+"")) {
			list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo+""));
		}
		list.add(new PropertyFilter("EQS_batchStatus","1"));
		list.add(new PropertyFilter("EQS_batchType","2"));
		list.add(new PropertyFilter("EQS_toFinanceStatus","0"));
		try {
			page = payApplyService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		
		log.debug("PayApplyAction list is end");
	    return returnStr;
	}
	
	
	
	
	
	/**
	 *	重报内部批次查询
	 */
	@RequestMapping("repBatchList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getRepBatchList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, Integer toFinanceNo,String batchNo,String isExported,String toFinanceStatus,String payResStatus,String payBatchTotalAmount,String expStartDate,String expEndDate, String createStartDate , String createEndDate,String batchType ) throws Exception{
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
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(createStartDate)) {
			list.add(new PropertyFilter("GTD_createDate",createStartDate));
		}
		if(StringUtil.isNotEmpty(createEndDate)) {
			list.add(new PropertyFilter("LTD_createDate",createEndDate));
		}
		if(StringUtil.isNotEmpty(toFinanceNo+"")) {
			list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo+""));
		}
		list.add(new PropertyFilter("EQS_batchType","2"));
		list.add(new PropertyFilter("EQS_toFinanceStatus","0"));
		try {
			page = payApplyService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		
		log.debug("PayApplyAction list is end");
	    return returnStr;
	}
	


	
		//重报批次调整页面
		@RequestMapping(value="repBatchNoList")
		@ResponseBody
		public ModelAndView repBatchNoList(@RequestParam("id")Integer id){
			log.debug("PayApplyAction batchNoList is start");
			String adjust = "PAY_APPLY.REPADJUST";
			BatchMain object = payApplyService.batchNoView(id);
			log.debug("PayApplyAction batchNoList is End");
			ModelAndView mv = new ModelAndView(getReturnPage(adjust));
			mv.addObject("v", object);
			return mv;
		}
		
		
		 //批次调整业务数据查询 
		@RequestMapping("repAdjustList")
		@ResponseBody
		//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
		public String repAdjustList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
						   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
						   @RequestParam(value="order", defaultValue="DESC")String order, 
						   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum,String vehiclePlateType,String applyNo,String toFinanceStatus,String batchNo,String batchStatus,String batchType,String repeatedBatchNo) throws Exception{
			String returnStr = "";
				List<PropertyFilter> list = new ArrayList<PropertyFilter>();
				Page page = new Page();
				page.setPageNo(pageNo);
				page.setPageSize(pageSize);
				page.setOrder(order);
				page.setOrderBy(orderBy);
				if(StringUtil.isNotEmpty(batchNo)) {
					list.add(new PropertyFilter("EQS_batchNo",batchNo));
				}
				if(StringUtil.isNotEmpty(vehiclePlateNum)) {
					list.add(new PropertyFilter("EQS_vehiclePlateNum",vehiclePlateNum));
				}
				if(StringUtil.isNotEmpty(vehiclePlateType)) {
					list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
				}
				if(StringUtil.isNotEmpty(toFinanceStatus)) {
					list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
				}
				if(StringUtil.isNotEmpty(applyNo)) {
					list.add(new PropertyFilter("EQS_applyNo",applyNo));
				}
				if(StringUtil.isNotEmpty(batchNo)) {
					list.add(new PropertyFilter("EQS_batchNo",batchNo));
				}
				if(StringUtil.isNotEmpty(repeatedBatchNo)) {
					list.add(new PropertyFilter("EQS_repeatedBatchNo",repeatedBatchNo));
				}
				try {
					page = payApplyService.getApplyPage(page, list);
					returnStr = writerPage(page);
				} catch (Exception e) {
					log.error("PayApplyAction list is Error:" + e, e);
				}
				
				log.debug("PayApplyAction list is end");
		    return returnStr;
		}

		
		//批次业务删除
		@ResponseBody
		@RequestMapping("repApplyDelete")
		public String delRepBatchDetail(@RequestParam("ids")String ids,@RequestParam("batchId")String batchId) throws Exception {
			log.debug("payApplyAction applyDelete is start");
			boolean deleteOk = false;
			String strids="";
			String idString[] = ids.split(",");
			for (int i = 0; i < idString.length; i++) {
				strids=strids+idString[i].concat("|");
			}
			strids=strids.substring(0, strids.length()-1);
			String returnObject = payApplyService.deleteRepApply(batchId,strids);	
			if(null != returnObject) {
				deleteOk=true;
			}
			log.debug("payApplyAction delete is End");
			if(deleteOk) {
				return JsonUtil.toSuccessMsg(returnObject);
			} else
				return JsonUtil.toErrorMsg("业务单删除有误");
		}
		
		//批次作废
		@ResponseBody
		@RequestMapping("repBatchCancel")
		public String repBatchCancel(@RequestParam("ids")String ids) {
			log.debug("PayApplyAction batchCancel is start");
			boolean cancelBatch = false;
			String result=null;
			String strids = "" ; 
			try {
				String idString[] = ids.split(",");
				for (int i = 0; i < idString.length; i++) {
					strids=strids+idString[i].concat("|");
				}
				strids=strids.substring(0, strids.length()-1);
				result= payApplyService.repBatchCancel(strids);
				if(null != result) {
					cancelBatch = true;
				}
			} catch (Exception e) {
				log.error("PayApplyAction batchCancel is Error:"+e, e);
			}		
			log.debug("PayApplyAction batchCancel is End");
			if(cancelBatch) {
				return JsonUtil.toSuccessMsg(result);
			} else
			return JsonUtil.toErrorMsg("批次作废失败");
		}
		
		
		
		//批次调整新增跳转
		@ResponseBody
		@RequestMapping("repBatchAddApplyView")
		public ModelAndView repBatchAddApplyView(@RequestParam("batchId")Integer batchId) throws Exception {
			String view = "PAY_APPLY.REP_BATCH_ADD_APPLY";
			BatchMain object = payApplyService.batchNoView(batchId);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}
		
		
		
		//批次调整新增业务单查询
/*		@ResponseBody
		@RequestMapping("repBatchAddList")
		public String repBatchAddList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
				   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
				   @RequestParam(value="order", defaultValue="DESC")String order, 
				   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String vehicleType,String vehicleOwner, String applyNo,String vehicleIdentifyNo,String batchNo,String batchType) throws Exception{
		//	batchType="2";
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
			if(StringUtil.isNotEmpty(vehicleType)) {
				list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("EQS_vehicleOwner",vehicleOwner));
			}
			if(StringUtil.isNotEmpty(batchNo)) {
				list.add(new PropertyFilter("EQS_vehicleOwner",vehicleOwner));
			}
			try {
				page = payApplyService.getApplyPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction list is Error:" + e, e);
			}
			
			log.debug("PayApplyAction list is end");
			return returnStr;
			}
		*/
		
		
		
		//批次调整新增业务单
		@ResponseBody
		@RequestMapping("addApplyToRepBatch")
		public String addApplyToRepBatch(@RequestParam("ids")String ids,@RequestParam("batchId")String batchId) {
			log.debug("PayApplyAction adjustBatchDetail is start");
			boolean addToBatch = false;
			String result=null;
			String strids = "" ;
			try {
				String idString[] = ids.split(",");
				for (int i = 0; i < idString.length; i++) {
					strids=strids+idString[i].concat("|");
				}
				strids=strids.substring(0, strids.length()-1);
				result= payApplyService.addRepApply(batchId,strids);
				if(null != result) {
					addToBatch = true;
				}
			} catch (Exception e) {
				log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
			}		
			log.debug("PayApplyAction adjustBatchDetail is End");
			if(addToBatch) {
				return JsonUtil.toSuccessMsg(result);
			} else
			return JsonUtil.toErrorMsg("增加业务单失败");
		}
		
		
		//重报财务批次查询
			@ResponseBody
			@RequestMapping("repToFinanceList")
			public String repToFinanceList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy,Integer toFinanceNo,String batchNo,String toFinanceStatus,String batchStatus,String batchType,String toFinanceStartTime,String toFinanceEndTime, String createStartDate , String createEndDate,String payBatchTotalAmount) throws Exception{
				log.debug("PayApplyAction toFinanceList is start");
				String returnStr = "";
					List<PropertyFilter> list = new ArrayList<PropertyFilter>();
					Page page = new Page();
					page.setPageNo(pageNo);
					page.setPageSize(pageSize);
					page.setOrder(order);
					page.setOrderBy(orderBy);
					if(StringUtil.isNotEmpty(batchNo)) {
						list.add(new PropertyFilter("EQS_batchNo",batchNo));
					}
					if(StringUtil.isNotEmpty(toFinanceStartTime)) {
						list.add(new PropertyFilter("GTD_toFinanceStartTime",toFinanceStartTime));
					}
					if(StringUtil.isNotEmpty(toFinanceEndTime)) {
						list.add(new PropertyFilter("GTD_toFinanceEndTime",toFinanceEndTime));
					}
					if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
						list.add(new PropertyFilter("LTD_payBatchTotalAmount",payBatchTotalAmount));
					}
					if(StringUtil.isNotEmpty(toFinanceEndTime)) {
						list.add(new PropertyFilter("LTD_toFinacneEndTime",toFinanceEndTime));
					}
					if(StringUtil.isNotEmpty(createStartDate)) {
						list.add(new PropertyFilter("GTD_createDate",createStartDate));
					}
					if(StringUtil.isNotEmpty(createEndDate)) {
						list.add(new PropertyFilter("LTD_createDate",createEndDate));
					}
					if(StringUtil.isNotEmpty(toFinanceNo+"")) {
						list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo+""));
					}
					list.add(new PropertyFilter("EQS_batchType","2"));
					list.add(new PropertyFilter("EQS_toFinanceStatus","1"));
					try {
						page = payApplyService.getPage(page, list);
						returnStr = writerPage(page);
					} catch (Exception e) {
						log.error("PayApplyAction toFinanceList is Error:" + e, e);
					}
					
					log.debug("PayApplyAction toFinanceList is end");
			//	}
				return returnStr;
				}
		
			
			
			@RequestMapping("confirmRepExportExcel")
			public ModelAndView confirmRepExportExcel(@RequestParam("id")Integer id) throws Exception {
				String view = "PAY_APPLY.CONFIRM_REP_EXCEL";
				BatchMain object = payApplyService.getObj(id);
				ModelAndView mv = new ModelAndView(getReturnPage(view));
				mv.addObject("v", object);
				return mv;
			}
			
			
			
			
			
		//重报批次报财务确认
		@RequestMapping("confirmRepBatch")
		@ResponseBody
		public String confirmRepBatch(@RequestParam("id")String id) {
			log.debug("PayApplyAction confirmBatch is start");
			boolean confirmOk = false;
			String result = null;
			try {
				result= payApplyService.confirmRepBatch(id);
				if(null != result) {
					confirmOk = true;
				}
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatch is Error:"+e, e);
			}		
			log.debug("PayApplyAction createBatch is End");
			if(confirmOk) {
				return JsonUtil.toSuccessMsg(result);
			} else
			return JsonUtil.toErrorMsg("报财务异常");
		}
	
		
		//重报报财务批次查看
		@RequestMapping("confirmRepBatchLook")
		@ResponseBody
		public String confirmRepBatchLook(@RequestParam("id")String id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPreview is start");
			int count =0;
			try {
				response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
				// 定义输出类型
				response.setContentType("application/vnd.ms-excel");
				OutputStream outputStream = response.getOutputStream();
				//result= payApplyService.batchExport(id);
				List<EliminatedApply> list = new ArrayList<EliminatedApply>();
				list = payApplyService.getRepBatchApplyList(batchNo);
				List<String[]> dataList = new ArrayList<String[]>();
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					count++;
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
							apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+toFinanceNo+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
					dataList.add(strings);
				}
				//outputStream = new FileOutputStream(new File("d:\\sss.xls"));
				ExcelProperties excelProperties=new ExcelProperties();
				excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+toFinanceNo+"批)");
				excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			//	OutputStream outputStream = null;
				ExportExcel.exportExcelInWeb(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			return null;
		}	
		
		
		
		
		
		
		
		//重报报财务批次导出(加密)
		@RequestMapping("confirmRepBatchExcel")
		@ResponseBody
		public String confirmRepBatchExcel(@RequestParam("id")String id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,String password,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPreview is start");
			int count =0;
			try {
				response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch"+batchNo+".xls").getBytes(), "iso-8859-1"));
				// 定义输出类型
				response.setContentType("application/vnd.ms-excel");
				OutputStream outputStream = response.getOutputStream();
				//result= payApplyService.batchExport(id);
				List<EliminatedApply> list = new ArrayList<EliminatedApply>();
				list = payApplyService.getRepBatchApplyList(batchNo);
				List<String[]> dataList = new ArrayList<String[]>();
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					count++;
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
							apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+toFinanceNo+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
					dataList.add(strings);
				}
				//outputStream = new FileOutputStream(new File("d:\\sss.xls"));
				ExcelProperties excelProperties=new ExcelProperties();
				excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+toFinanceNo+"批)");
				excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
			//	OutputStream outputStream = null;
				ExportExcel.exportExcelInWebs(excelProperties, "ss", new int[] { 40, 90 }, dataList, outputStream,password);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			return null;
		}	
		
	
		
		
		
		
		//报财务查询导出预览
		@RequestMapping("repFinanceExcel")
		public ModelAndView repFinanceExcel(@RequestParam("id")Integer id) {
			String view = "PAY_APPLY.TO_REP_FINANCE_EXCEL";
			BatchMain object = payApplyService.getObj(id);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}	
		
		
		
		
		//申报批次总查询
		@RequestMapping("batchAllList")
		//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
		@ResponseBody
		//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
		public String getBatchAllList(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
						   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
						   @RequestParam(value="order", defaultValue="DESC")String order, 
						   @RequestParam(value="sort", defaultValue="id")String orderBy,Integer toFinanceNo, String batchNo,String toFinanceStatus,String payResStatus,String payBatchTotalAmount,String toFinanceStartTime,String toFinanceEndTime, String createStartDate , String createEndDate,String batchType,String batchStatus) throws Exception{
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
			if(StringUtil.isNotEmpty(toFinanceStatus)) {
				list.add(new PropertyFilter("EQS_toFinanceStatus",toFinanceStatus));
			}
			if(StringUtil.isNotEmpty(payResStatus)) {
				list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
			}
			if(StringUtil.isNotEmpty(payBatchTotalAmount)) {
				list.add(new PropertyFilter("EQS_applyNo",payBatchTotalAmount));
			}
			if(StringUtil.isNotEmpty(toFinanceStartTime)) {
				list.add(new PropertyFilter("GTD_toFinanceStartTime",toFinanceStartTime));
			}
			if(StringUtil.isNotEmpty(toFinanceEndTime)) {
				list.add(new PropertyFilter("LTD_toFinanceEndTime",toFinanceEndTime));
			}
			if(StringUtil.isNotEmpty(createStartDate)) {
				list.add(new PropertyFilter("GTD_createDate",createStartDate));
			}
			if(StringUtil.isNotEmpty(createEndDate)) {
				list.add(new PropertyFilter("LTD_createDate",createEndDate));
			}
			if(StringUtil.isNotEmpty(batchType)) {
				list.add(new PropertyFilter("EQS_batchType",batchType));
			}
			if(StringUtil.isNotEmpty(batchStatus)) {
				list.add(new PropertyFilter("EQS_batchStatus",batchStatus));
			}
			if(StringUtil.isNotEmpty(toFinanceNo+"")) {
				list.add(new PropertyFilter("EQS_toFinanceNo",toFinanceNo+""));
			}
			try {
				page = payApplyService.getPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction list is Error:" + e, e);
			}
			log.debug("PayApplyAction list is end");
		    return returnStr;
		}
}
