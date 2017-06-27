package com.jst.vesms.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jst.test.web.PDFUtil;
import com.jst.util.EncryptUtil;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.BatchExport;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ExportBatchService;
import com.jst.vesms.service.PayApplyService;
import com.jst.vesms.util.excel.ExcelProperties;
import com.jst.vesms.util.excel.exp.ExportExcel;


@RequestMapping("/payApply")
@Controller
public class PayApplyAction extends BaseAction{
	
	private static final Log log = LogFactory.getLog(PayApplyAction.class);
	
	@Resource(name="payApplyServiceImpl")
	private  PayApplyService payApplyService;
		
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	@Resource(name = "exportBatchServiceImpl")
	private ExportBatchService exportBatchService;
	
	/**
	 * 进行查询数据
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_ADD_BATCH", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String vehicleType,String vehicleOwner, String applyNo,String vehicleIdentifyNo,String batchNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		StringBuffer sb = new StringBuffer("select * from t_eliminated_apply t where 1 = 1 ");
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num like '%").append(vehiclePlateNum).append("%' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtil.encryptDES(key, vehicleIdentifyNo);
			sb.append("and t.vehicle_identify_no = '").append(vehicleIdentifyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleType)) {
			sb.append("and t.vehicle_type = '").append(vehicleType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicle_owner like '%").append(vehicleOwner).append("%' ");
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(batchNo)) {
			sb.append("and t.batch_no = '").append(batchNo).append("' ");
		}
		sb.append("and t.bussiness_status = '1' and t.current_post = 'BFSBG' and t.batch_no is null ");
		try {
			page = payApplyService.getPageBySql(page, sb.toString());
			//page = payApplyService.getApplyPage(page, list);
		 	//page = payApplyService.filterBatchPage(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayApplyAction list is Error:" + e, e);
		}
		
		log.debug("PayApplyAction list is end");
	    return returnStr;
	}
	
	
	
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_ADD_BATCH",prvgCode = "VIEW")
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
	@Privilege(modelCode = "M_ADD_BATCH",prvgCode = "ADD_BATCH")
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
	 *	正常内部可调整批次查询
	 */
	@RequestMapping("batchAdjustList")
	@Privilege(modelCode = "M_NOR_BATCH_ADJUST" ,prvgCode = "QUERY")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String batchAdjustList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	public String getBatchList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	
	
	
	
	//去导出预览页面
	@RequestMapping("toExcelPreview")
	public ModelAndView toExcelPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "PAY_APPLY.EXPORT_PREVIEW";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	

	
	//正常内部批次文件预览
	@RequestMapping("exportPreview")
	public String exportPreview (Integer id,String batchNo,HttpServletResponse response) {
		log.debug("PayApplyAction excelList is start");
		int count=0;
		try {
			response.setHeader("Content-disposition", "attachment;  filename="
					+ new String(("view_batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			OutputStream outputStream = response.getOutputStream();
			List<EliminatedApply> list = new ArrayList<EliminatedApply>();
			list = payApplyService.getBatchApplyList(batchNo);
			List<String[]> dataList = new ArrayList<String[]>();
			for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				count++;
				// 车架号解密
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				apply.setVehicleIdentifyNo(EncryptUtil.decryptDES(des_key, apply.getBankAccountNo()));				
				apply.setBankAccountNo(EncryptUtil.decryptDES(des_key, apply.getBankAccountNo()));
				String[] strings = new String[]{count+"", apply.getVehicleOwner(),apply.getVehiclePlateNum(),apply.getVehicleTypeName(),apply.getSubsidiesStandard(),
						apply.getVehicleIdentifyNo(),apply.getAdvancedScrapDays().toString(),apply.getRecycleDate().toString(),"",apply.getIsPersonal().toString(),
						apply.getBankName(),apply.getBankAccountNo(),apply.getSubsidiesMoney().toString()};
				dataList.add(strings);
			}
			ExcelProperties excelProperties=new ExcelProperties();
			excelProperties.setColsHeader(new String[] { "序号", "车主名称", "车牌号码", "车辆类型", "燃油类型及排放标准", "车架号", "提前淘汰时间(天)", "报废交售日期","注销类别","财政供养信息","开户银行","开户银行账号","补贴金额"});
			excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴车辆信息审批表(预览)");
			ExportExcel.exportExcelInWeb(excelProperties, "ss", new int[] { 5,20,10,10,15,15,10,15,8,15,15,20,10 }, dataList, outputStream);
		} catch (Exception e) {
			log.error("PayApplyAction excelList is Error:"+e, e);
		}		
		log.debug("PayApplyAction excelList is End");
		return "";
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
	
	// 去重报批次查看页面
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
	
	
	
	// 去批次查看页面
	@RequestMapping("batchView")
	@Privilege(modelCode = "M_NOR_BATCH_ADJUST",prvgCode = "VIEW")
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
	public String batchApplyList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	
	
	
	
	//去批次调整页面
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
/*	@RequestMapping("adjustList")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String getBatchDetail(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	}*/

	
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
	@Privilege(modelCode = "M_NOR_BATCH_ADJUST" ,prvgCode = "CANCEL")
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
	public String getNoBatchApplyList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	
	//去批次调整新增页面
		@ResponseBody
		@RequestMapping("batchAddApplyView")
		public ModelAndView batchAddApplyView(@RequestParam("batchId")Integer batchId) throws Exception {
			String view = "PAY_APPLY.BATCH_ADD_APPLY";
			BatchMain object = payApplyService.batchNoView(batchId);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}
	
	
	
	
	//批次调整新增业务单
	@ResponseBody
	@RequestMapping("addApplyToBatch")
	public String addApplyToBatch(@RequestParam("ids")String ids,@RequestParam("batchId")Integer batchId) {
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
		public String getToFinanceList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
/*	@RequestMapping("confirmBatch")
	@ResponseBody
	public String confirmBatch(@RequestParam("id")String id) {
		log.debug("PayApplyAction confirmBatch is start");
		boolean confirmOk = false;
		String result = "";
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
		
	}	*/
	
	
	
	
	//去文件查看页面
	@RequestMapping("financeExcel")
	public ModelAndView financeExcel(@RequestParam("id")Integer id) {
		String view = "PAY_APPLY.TO_FINANCE_EXCEL";
		BatchMain object = payApplyService.getObj(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}	
	
	
	
	//去报财务导出页面
		@RequestMapping("toFinanceExcel")
		public ModelAndView toFinanceExcel(@RequestParam("id")Integer id) {
			String view = "PAY_APPLY.TO_EXPORT_EXCEL";
			BatchMain object = payApplyService.getObj(id);
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			mv.addObject("v", object);
			return mv;
		}	
	
		//正常报财务批次导出(加密)
		@RequestMapping(value = "confirmBatchExcel", produces={"text/html;charset=UTF-8;","application/json;"})
		@ResponseBody
		public String confirmBatchExcel(@RequestParam("id")Integer id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,HttpServletResponse response,HttpServletRequest request) {
			log.debug("PayApplyAction confirmBatchPreview is start");
			int count = 0;
			boolean isOk = false;
			String resString = "";
			 int num = 0;//定义变量
		     Random ne=new Random();//实例化一个random的对象
			try {
				
				String confirmrResult = payApplyService.confirmBatch(id.toString());
				String str[]=confirmrResult.split("|");
				String str1 = "" ;
				String str2 = "" ;
				for (int i = 0; i < str.length; i++) {
					str1 = str[0];
					str2 = str[1];
				}
				if (str1.equals("-1")) {
					isOk = false;
					resString = str2;
				} else {
					// 设置随机数加密
				     num=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
					String password = num+"";
					BatchMain batchMain = (BatchMain)payApplyService.get(id);
					// String contextPath = request.getSession().getServletContext().getRealPath("/");
				//	String excelPath = PropertyUtil.getPropertyValue("excelPath");
					String excelPath = "D:/excel";
				//	String filePath = excelPath+ new String(("batch_"+batchNo+".xls").getBytes(),"iso-8859-1");
					// filePath = filePath.replace("_fxg", File.separator);
					/*response.setHeader("Content-disposition", "attachment;  filename="
							+ new String(("batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));
					// 定义输出类型
					response.setContentType("application/vnd.ms-excel");*/
					//OutputStream outputStream = response.getOutputStream();
					List<EliminatedApply> list = new ArrayList<EliminatedApply>();
					list = payApplyService.getBatchApplyList(batchNo);
					List<String[]> dataList = new ArrayList<String[]>();
					for (int i = 0; i < list.size(); i++) {
						EliminatedApply apply = list.get(i);
						count++;
						String des_key = PropertyUtil.getPropertyValue("DES_KEY");
						apply.setBankAccountNo(EncryptUtil.decryptDES(des_key, apply.getBankAccountNo()));
						String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999","003",apply.getVehicleOwner().toString(),
								apply.getBankAccountNo().toString(),apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+batchMain.getToFinanceNo()+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
						dataList.add(strings);
					}
			//		exportPath = contextPath + excelPath + File.separator + "batch_"+batchNo+".xls";
					//result = exportPath.replaceAll("\\\\", "\\/");
	            	if (! new File(excelPath).exists()) {
	            		new File(excelPath).mkdirs();
	            	}
	            	File savePath = new File("D:" + File.separator + "excel"+ File.separator + "batch_"+batchNo+".xls");
	            	//	OutputStream outputStream = new FileOutputStream(new File(savePath, "batch_"+batchNo+".xls"));
	            	OutputStream outputStream = new FileOutputStream(savePath);
	            	ExcelProperties excelProperties=new ExcelProperties();
					excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+batchMain.getToFinanceNo()+"批)");
					excelProperties.setColsHeader(new String[] { "序号", "金额", "经济分类编码", "收款人行别编码", "收款人名称", "收款人账户", "开户银行", "摘要" });
					int exportResult = ExportExcel.exportExcelInWebs(excelProperties, "ss", new int[] { 5,10,15,15,25,15,15,45 }, dataList, outputStream, password);
					PDFUtil.generatePDF("深圳市老旧车提前淘汰奖励补贴资金发放表(第"+batchMain.getToFinanceNo()+"批)", "D:" + "/" + "excel"+ "/" + "batch_"+batchNo+".pdf", dataList);
					outputStream.close();
					if (exportResult != 1) {
						isOk = false;
						resString = "批次文件生成失败！";
					} else {
						// 后台调用导出存储过程
						payApplyService.batchExport(id, "D:" + "/" + "excel/" + "batch_"+batchNo+".xls"+","+"D:" + "/" + "excel/" + "batch_"+batchNo+".pdf");
						isOk = true;
						resString = "D:" + "/" + "excel/" + "batch_"+batchNo+".xls"+","+"D:" + "/" + "excel/" + "batch_"+batchNo+".pdf";
					}
					
					// 下载保存在服务器的文件到本地
					/*FileInputStream inputStream = new FileInputStream(exportPath);
					OutputStream resOutStream = response.getOutputStream();
					byte[] bytes = new byte[4096];
					
					while(inputStream.read(bytes) != -1) {
						resOutStream.write(bytes, 0, bytes.length);
					}
					inputStream.close();
					resOutStream.close();*/
				}
				
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
				isOk = false;
				resString = "系统异常，请联系管理员";
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			if (isOk) {
				return JsonUtil.toSuccessMsg(resString);
			} else {
				return JsonUtil.toErrorMsg(resString);
			}
		}	
	
	
	//正常报财务excel文件查看
		@RequestMapping("confirmBatchLook")
		@ResponseBody
		public String confirmBatchLook(@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchLook is start");
			String excelPath = "";
			try {
			/*	response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));*/
				// 定义输出类型
			//	response.setContentType("application/vnd.ms-excel");
				String filePath = "" ;
				List<BatchExport> list = exportBatchService.getListByPorperty("batchNo", batchNo, null);
				for (int i = 0; i < list.size(); i++) {
					BatchExport apply = list.get(0);
					filePath = apply.getExportRoute();
				}
				String pathString[]=filePath.split(",");
				for (int i = 0; i < pathString.length; i++) {
					excelPath = pathString[0];
				}
			// 	fileDownload(excelPath, batchNo, response);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchLook is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchLook is End");
			return JsonUtil.toSuccessMsg(excelPath);
		}	
	
		
		
		//正常报财务pdf文件查看
		@RequestMapping("confirmBatchPdf")
		@ResponseBody
		public String confirmBatchPdf(@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPdf is start");
			String excelPath = "";
			try {
				String filePath = "" ;
				List<BatchExport> list = exportBatchService.getListByPorperty("batchNo", batchNo, null);
				for (int i = 0; i < list.size(); i++) {
					BatchExport apply = list.get(0);
					filePath = apply.getExportRoute();
				}
				String pathString[]=filePath.split(",");
				for (int i = 0; i < pathString.length; i++) {
					excelPath = pathString[1];
				}
			//	fileDownload(excelPath, batchNo, response);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPdf is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPdf is End");
			return JsonUtil.toSuccessMsg(excelPath);
		}	
	
	/*----------------------------------------------------------------------*/
	
	
	/**
	 * 查询待生成重报批次业务数据
	 */
	@RequestMapping("repList")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String repList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
			StringBuffer sb = new StringBuffer("select * from t_eliminated_apply t where 1 = 1 ");
			if(StringUtil.isNotEmpty(vehiclePlateNum)) {
				sb.append("and t.vehicle_plate_num like '%").append(vehiclePlateNum).append("%' ");
			}
			if(StringUtil.isNotEmpty(vehiclePlateType)) {
				sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
			}
			if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
				String key = PropertyUtil.getPropertyValue("DES_KEY");
				vehicleIdentifyNo = EncryptUtil.encryptDES(key, vehicleIdentifyNo);
				sb.append("and t.vehicle_identify_no = '").append(vehicleIdentifyNo).append("' ");
			}
			if(StringUtil.isNotEmpty(vehicleType)) {
				sb.append("and t.vehicle_type = '").append(vehicleType).append("' ");
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				sb.append("and t.vehicle_owner like '%").append(vehicleOwner).append("%' ");
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				sb.append("and t.apply_no = '").append(applyNo).append("' ");
			}
			if(StringUtil.isNotEmpty(batchNo)) {
				sb.append("and t.batch_no = '").append(batchNo).append("' ");
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				sb.append("and t.vehicleOwner = '").append(vehicleOwner).append("' ");
			}
			sb.append("and t.bussiness_status = '1' and t.current_post = 'BFSBG' and t.batch_no is not null and repeated_batch_no is null and to_number(TO_FINANCE_STATUS)<=-2");
			try {
				page = payApplyService.getPageBySql(page, sb.toString());
				//page = payApplyService.getApplyPage(page, list);
			 	//page = payApplyService.filterBatchPage(page);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction repList is Error:" + e, e);
			}
			
			log.debug("PayApplyAction repLlist is end");
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
	public String getRepExpBatchList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	@ResponseBody
	public String repBatchPreview (Integer id,String batchNo,HttpServletResponse response) {
		log.debug("PayApplyAction excelList is start");
		int count=0;
		try {
			response.setHeader("Content-disposition", "attachment;  filename="
					+ new String(("view_batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));
			// 定义输出类型
			response.setContentType("application/vnd.ms-excel");
			OutputStream outputStream = response.getOutputStream();
		//	List<EliminatedApply> list = new ArrayList<EliminatedApply>();
		//	list = payApplyService.getRepBatchApplyList(batchNo);
			List<String[]> dataList = new ArrayList<String[]>();
			//调用sql 
			List sqlList = payApplyService.getBySql(batchNo);
			for (int i = 0; i < sqlList.size(); i++) {
				count++;
				Object[] object = (Object[]) sqlList.get(i);
				//序号
				//BigDecimal xuhaoBigDecimal = (BigDecimal)object[0];
				//String number = (String) object[0];
				//车牌号码
				String vehiclePlateNum = (String)object[3];
				//补贴金额
				BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)object[5];
				String subsidiesMoney = subsidiesMoneyBigDecimal.toPlainString();
				//原车主姓名
				String lastAccountName = (String)object[7];
				//原开户行
				String lastBankName = (String)object[6];
				//原开户账户
				String lastAccountNo = (String)object[8];	
				String key = PropertyUtil.getPropertyValue("DES_KEY");
				lastAccountNo = EncryptUtil.decryptDES(key, lastAccountNo);
				//变更后补贴对象
				String thisBankName = (String)object[10];
				//变更后银行
				String thisAccountName = (String)object[9];
				//变更后银行账号
				String thisAccountNo = (String)object[11];
				String key1 = PropertyUtil.getPropertyValue("DES_KEY");
				thisAccountNo = EncryptUtil.decryptDES(key1, thisAccountNo);
				//变更内容
				String thisType = (String)object[12];
				if(thisType.equals("1")){
					thisType="一般资料修正";
				}else if(thisType.equals("2")){
					thisType="补贴账户错误修正";
				}
			//	thisType = EncryptUtil.decryptDES(key, thisType);
				//当前报送序号
				String thisToFinanceNo = (String)object[2];
				String[] strings = new String[] {count+"",vehiclePlateNum,subsidiesMoney,
						lastAccountName,lastBankName,lastAccountNo,thisBankName,thisAccountName,thisAccountNo,
						thisType,thisToFinanceNo};
				dataList.add(strings);
			}
			
			/*for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				count++;
				String[] strings = new String[]{count+"", apply.getVehiclePlateNum().toString(),apply.getSubsidiesStandard().toString(),apply.getVehicleOwner().toString(),
						};
				dataList.add(strings);
			}*/
			
			ExcelProperties excelProperties=new ExcelProperties();
			excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴退款重新支付审核表(预览)");
			excelProperties.setColsHeader(new String[] { "序号", "车牌号码", "补贴金额", "车主姓名", "原开户银行", "原开户账户", "变更后补贴对象", "变更后银行","变更后银行账号","变更内容","报送序号" });
			ExportExcel.repExportExcelPreview(excelProperties, "ss", new int[] {5,13,10,18,15,18,20,15,18,23,8 }, dataList, outputStream);
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
	public String repBatchAdjust(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
	public String getRepBatchList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
		public String repAdjustList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
		
		
		
		//去重报批次调整新增页面
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
			log.debug("PayApplyAction addApplyToRepBatch is start");
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
			log.debug("PayApplyAction addApplyToRepBatch is End");
			if(addToBatch) {
				return JsonUtil.toSuccessMsg(result);
			} else
			return JsonUtil.toErrorMsg("增加业务单失败");
		}
		
		
		//重报财务批次查询
			@ResponseBody
			@RequestMapping("repToFinanceList")
			public String repToFinanceList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy,Integer toFinanceNo,String batchNo,String toFinanceStatus,String batchStatus,String batchType,String toFinanceStartTime,String toFinanceEndTime, String createStartDate , String createEndDate,String payBatchTotalAmount,String isExported) throws Exception{
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
					if(StringUtil.isNotEmpty(isExported)) {
						list.add(new PropertyFilter("EQS_isExported",isExported));
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
	/*	@RequestMapping("confirmRepBatch")
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
		}*/
	
		
		//重报报财务批次excel查看
		@RequestMapping("confirmRepBatchLook")
		@ResponseBody
		public String confirmRepBatchLook(@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			
			log.debug("PayApplyAction confirmBatchLook is start");
			String excelPath = "";
			try {
			/*	response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(("batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));*/
				// 定义输出类型
			//	response.setContentType("application/vnd.ms-excel");
				String filePath = "" ;
				List<BatchExport> list = exportBatchService.getListByPorperty("batchNo", batchNo, null);
				for (int i = 0; i < list.size(); i++) {
					BatchExport apply = list.get(0);
					filePath = apply.getExportRoute();
				}
				String pathString[]=filePath.split(",");
				for (int i = 0; i < pathString.length; i++) {
					excelPath = pathString[0];
				}
			// 	fileDownload(excelPath, batchNo, response);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchLook is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchLook is End");
			return JsonUtil.toSuccessMsg(excelPath);
		}
			
		//重报报财务批次pdf查看
		@RequestMapping("confirmRepBatchPdf")
		@ResponseBody
		public String confirmRepBatchPdf(@RequestParam("batchNo")String batchNo,HttpServletResponse response) {
			log.debug("PayApplyAction confirmBatchPdf is start");
			String excelPath = "";
			try {
				String filePath = "" ;
				List<BatchExport> list = exportBatchService.getListByPorperty("batchNo", batchNo, null);
				for (int i = 0; i < list.size(); i++) {
					BatchExport apply = list.get(0);
					filePath = apply.getExportRoute();
				}
				String pathString[]=filePath.split(",");
				for (int i = 0; i < pathString.length; i++) {
					excelPath = pathString[1];
				}
			//	fileDownload(excelPath, batchNo, response);
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPdf is Error:"+e, e);
			}		
			log.debug("PayApplyAction confirmBatchPdf is End");
			return JsonUtil.toSuccessMsg(excelPath);
		}	
		
		
		
		//重报报财务批次导出(加密)
		@RequestMapping("confirmRepBatchExcel")
		@ResponseBody
		public String confirmRepBatchExcel(@RequestParam("id")Integer id,@RequestParam("toFinanceNo")Integer toFinanceNo,@RequestParam("batchNo")String batchNo,String password,HttpServletResponse response,HttpServletRequest request) {
			log.debug("PayApplyAction confirmRepBatchExcel is start");
			int count = 0;
			boolean isOk = false;
			String resString = "";
			try {
				// 报财务确认
				String confirmResult = payApplyService.confirmRepBatch(id.toString());
				String str[]=confirmResult.split("|");
				String str1 = "" ;
				String str2 = "" ;
				for (int i = 0; i < str.length; i++) {
					str1 = str[0];
					str2 = str[1];
				}
				if (str1.equals("-1")) {
					isOk = false;
					resString = str2;
				} else {
					BatchMain batchMain = (BatchMain)payApplyService.get(id);
					String excelPath = "D:/repExcel";
				/*	response.setHeader("Content-disposition", "attachment;  filename="
							+ new String(("batch_"+batchNo+".xls").getBytes(), "iso-8859-1"));*/
					// 定义输出类型
			// 		response.setContentType("application/vnd.ms-excel");
					List<String[]> dataList = new ArrayList<String[]>();
					//调用sql 
					List sqlList = payApplyService.getBySql(batchNo);
					for (int i = 0; i < sqlList.size(); i++) {
						count++;
						Object[] object = (Object[]) sqlList.get(i);
						//序号
						//BigDecimal xuhaoBigDecimal = (BigDecimal)object[0];
						//String number = (String) object[0];
						//车牌号码
						String vehiclePlateNum = (String)object[3];
						//补贴金额
						BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)object[5];
						String subsidiesMoney = subsidiesMoneyBigDecimal.toPlainString();
						//原车主姓名
						String lastAccountName = (String)object[7];
						//原开户行
						String lastBankName = (String)object[6];
						//原开户账户
						String lastAccountNo = (String)object[8];	
						String key = PropertyUtil.getPropertyValue("DES_KEY");
						lastAccountNo = EncryptUtil.decryptDES(key, lastAccountNo);
						//变更后补贴对象
						String thisBankName = (String)object[10];
						//变更后银行
						String thisAccountName = (String)object[9];
						//变更后银行账号
						String thisAccountNo = (String)object[11];
						String key1 = PropertyUtil.getPropertyValue("DES_KEY");
						thisAccountNo = EncryptUtil.decryptDES(key1, thisAccountNo);
						//变更后补贴对象
						//变更内容
						String thisType = (String)object[12];
						if(thisType .equals("1")){
							thisType="一般资料修正";
						}else if(thisType.equals("2")){
							thisType="补贴账户错误修正";
						}
						//当前报送序号
						BigDecimal thisToFinanceNoBigDecimal = (BigDecimal)object[2];
						String thisToFinanceNo = thisToFinanceNoBigDecimal.toPlainString();
						String[] strings = new String[] {count+"",vehiclePlateNum,subsidiesMoney,
								lastAccountName,lastBankName,lastAccountNo,thisBankName,thisAccountName,thisAccountNo,
								thisType,thisToFinanceNo};
						dataList.add(strings);
					}
	            	if (! new File(excelPath).exists()) {
	            		new File(excelPath).mkdirs();
	            	}
	            	File savePath = new File("D:" + File.separator + "repExcel"+ File.separator + "batch_"+batchNo+".xls");
	            	OutputStream outputStream = new FileOutputStream(savePath);
	            	ExcelProperties excelProperties=new ExcelProperties();
					
					excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴退款重新支付审核表(第"+batchMain.getToFinanceNo()+"批)");
					excelProperties.setColsHeader(new String[] { "序号", "车牌号码", "补贴金额", "车主姓名", "原开户银行", "原开户账户", "变更后补贴对象", "变更后银行","变更后银行账号","变更内容","批次号" });
					int exportResult = ExportExcel.repExportExcelToFinance(excelProperties, "ss", new int[] {5,13,10,18,15,18,20,15,18,23,8 }, dataList, outputStream,password);
					PDFUtil.repGeneratePDF("深圳市老旧车提前淘汰奖励补贴退款重新支付审核表((第"+batchMain.getToFinanceNo()+"批)", "D:" + "/" + "repExcel"+ "/" + "batch_"+batchNo+".pdf", dataList);
					outputStream.close();
					if (exportResult != 1) {
						isOk = false;
						resString = "批次文件生成失败！";
					} else {
						// 后台调用导出存储过程
						payApplyService.batchRepExport(id, "D:" + "/" + "repExcel/" + "batch_"+batchNo+".xls"+","+"D:" + "/" + "repExcel/" + "batch_"+batchNo+".pdf");
						isOk = true;
						resString = "D:" + "/" + "repExcel/" + "batch_"+batchNo+".xls"+","+"D:" + "/" + "repExcel/" + "batch_"+batchNo+".pdf";
					}
				}
			} catch (Exception e) {
				log.error("PayApplyAction confirmBatchPreview is Error:"+e, e);
				isOk = false;
				resString = "系统异常，请联系管理员";
			}		
			log.debug("PayApplyAction confirmBatchPreview is End");
			if (isOk) {
				return JsonUtil.toSuccessMsg(resString);
			} else {
				return JsonUtil.toErrorMsg(resString);
			}
		}	
	
	
		
		
		
		
		//报财务查询导出查看
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
		@ResponseBody
		//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
		//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
		public String getBatchAllList(@RequestParam(value="page", defaultValue="1")int pageNo, 
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
		
	
		//点击文件路径，下载文件
		@ResponseBody
		@RequestMapping("fileDownload")
		public String fileDownload(String filepath, String batchNo, HttpServletResponse response) throws Exception {
			log.debug("PayApplyAction fileDownload is start");
			try {
				String path="";
				String pathString[] = filepath.split("/");
				for (int i = 0; i < pathString.length; i++) {
					path=pathString[pathString.length-1];
				}
				response.setHeader("Content-disposition", "attachment;  filename="
						+ new String(path.getBytes(), "iso-8859-1"));
				// 定义输出类型
				response.setContentType("application/force-download");
				//String filePath = "" ;
				/*List<BatchExport> list = exportBatchService.getListByPorperty("batchNo", batchNo, null);
				for (int i = 0; i < list.size(); i++) {
					BatchExport apply = list.get(0);
					filePath = apply.getExportRoute();
				}*/
				// 下载保存在服务器的文件到本地
				FileInputStream inputStream = new FileInputStream(filepath);
				OutputStream resOutStream = response.getOutputStream();
				byte[] bytes = new byte[4096];
				
				while(inputStream.read(bytes) != -1) {
					resOutStream.write(bytes, 0, bytes.length);
				}
				inputStream.close();
				resOutStream.close();
			} catch (Exception e) {
				log.error("PayApplyAction fileDownload is Error:"+e, e);
			}		
			log.debug("PayApplyAction fileDownload is End");
			return null;
			
		}
		
		
}
