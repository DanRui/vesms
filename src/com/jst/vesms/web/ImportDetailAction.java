package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.DateUtil;
import com.jst.common.utils.page.Page;
import com.jst.util.StringUtil;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.PayResultImportDetail;
import com.jst.vesms.service.ImportDetailService;

@RequestMapping("/importDetail")
@Controller
public class ImportDetailAction extends BaseAction{
private static final Log log = LogFactory.getLog(ImportDetailAction.class);
	
	@Resource(name="importDetailServiceImpl")
	private ImportDetailService importDetailService;
	
	
	/**
	 * 进行查询数据(查看)
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "VIEW")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String payImportId,String applyNo,String accountName,String payResStatus,String payResult) throws Exception{
		log.debug("PayImportAction list is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(payImportId)) {
			list.add(new PropertyFilter("EQI_payImportId",payImportId));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(accountName)) {
			list.add(new PropertyFilter("LIKES_accountName",accountName));
		}
		if(StringUtil.isNotEmpty(payResStatus)) {
			list.add(new PropertyFilter("EQS_payResStatus",payResStatus));
		}
		if(StringUtil.isNotEmpty(payResult)) {
			list.add(new PropertyFilter("EQS_payResult",payResult));
		}
		/*if(StringUtil.isNotEmpty(makeStartTime)) {
			list.add(new PropertyFilter("GED_makeTime",makeStartTime));
		}
		if(StringUtil.isNotEmpty(makeEndTime)) {
			Date date = DateUtil.parse(makeEndTime, DateUtil.DATE_PATTERN);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
			makeEndTime = DateUtil.format(date, DateUtil.DATE_PATTERN);
			list.add(new PropertyFilter("LTD_makeTime",makeEndTime));
		}
		if(StringUtil.isNotEmpty(importStartTime)) {
			list.add(new PropertyFilter("GED_importTime",importStartTime));
		}
		if(StringUtil.isNotEmpty(importEndTime)) {
			Date date = DateUtil.parse(importEndTime, DateUtil.DATE_PATTERN);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
			importEndTime = DateUtil.format(date, DateUtil.DATE_PATTERN);
			list.add(new PropertyFilter("LTD_importTime",importEndTime));
		}*/
		try {
			page = importDetailService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayImportAction list is Error:" + e, e);
		}
		log.debug("PayImportAction list is end");
	    return returnStr;
	}
	
	
	
	
	
	
	/**
	 * 进行查询数据（标记页面）
	 */
	@RequestMapping("importMarkList")
	@ResponseBody
	@Privilege(modelCode = "M_MARK_NOR_APPLY",prvgCode = "RESULT_MARK")
	public String importMarkList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String payImportId,String applyNo,String payResStatus,String accountName,String payResult,String payStartTime,String payEndTime,String payNo) throws Exception{
		log.debug("PayImportAction importMarkList is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		StringBuffer sb = new StringBuffer("select * from t_pay_result_import_detail t where 1 = 1 ");
		if(StringUtil.isNotEmpty(payImportId)) {
			sb.append("and t.pay_Import_id = '").append(payImportId).append("' ");
		}
		if(StringUtil.isNotEmpty(payResult)) {
			sb.append("and t.pay_result = '").append(payResult).append("' ");
		}
		if(StringUtil.isNotEmpty(accountName)) {
			sb.append("and t.account_name = '").append(accountName).append("' ");
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(payStartTime)) {
			sb.append("and t.pay_Time >= to_date('").append(payStartTime).append("','yyyy-MM-dd') ");
		}
		if(StringUtil.isNotEmpty(payEndTime)) {
			sb.append("and t.pay_Time < to_date('").append(payEndTime).append("','yyyy-MM-dd')+1 ");
		}
		if(StringUtil.isNotEmpty(payResStatus)) {
			sb.append("and t.pay_res_status = '").append(payResStatus).append("' ");
		}else{		
			sb.append("and t.pay_res_status in ('0','3')  ");
		}
		sb.append(" and t.pay_No is not null order by t.id");
		try {
			page = importDetailService.getPageBySql(page, sb.toString());
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayImportAction importMarkList is Error:" + e, e);
		}
		log.debug("PayImportAction importMarkList is end");
	    return returnStr;
	}
	
	
	
	
	
	
	
}
