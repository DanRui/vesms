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
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.PayResultService;
import com.jst.vesms.util.EncryptUtils;


@RequestMapping("/payResult")
@Controller
public class PayResultAction extends BaseAction{
	private static final Log log = LogFactory.getLog(PayResultAction.class);
	
	@Resource(name="payResultServiceImpl")
	private PayResultService payResultService;
		
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	
	
	@RequestMapping("payView")
	@Privilege(modelCode = "M_MARK_NOR_APPLY", prvgCode = "QUERY")
	public ModelAndView payView() throws Exception {
		String view = "PAY_RESULT.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	@RequestMapping("repeatPayView")
	@Privilege(modelCode = "M_MARK_REP_APPLY", prvgCode = "QUERY")
	public ModelAndView repeatPayView() throws Exception {
		String view = "PAY_RESULT.REPEAT_LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 进行查询数据
	 */
	
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_MARK_NOR_APPLY", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String vehicleType, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate,String batchNo,String toFinanceNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		StringBuffer sb = new StringBuffer("select t.id,m.to_finance_No,t.batch_No,t.apply_no,t.vehicle_Plate_Num,t.vehicle_Plate_Type,t.vehicle_Type,");
		sb.append(" t.vehicle_identify_no,t.vehicle_Owner,t.subsidies_Money,t.apply_Time");
		sb.append(" from t_eliminated_apply t ");
		sb.append("inner join t_batch_main m on t.batch_no=m.batch_no  where 1 = 1 ");
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num like '%").append(vehiclePlateNum).append("%' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
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
		if(StringUtil.isNotEmpty(toFinanceNo)) {
			sb.append("and m.toFinanceNo = '").append(toFinanceNo).append("' ");
		}
		sb.append("and t.bussiness_status = '1' and t.current_post = 'BFJGBJG' and t.batch_no is not null and repeated_batch_no is null");
		try {
			page = payResultService.getPageSql(page, sb.toString());
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayResultAction list is Error:" + e, e);
		}
		log.debug("PayResultAction list is end");
	    return returnStr;
	}
	

	@RequestMapping("view")
	@Privilege(modelCode = "M_MARK_NOR_APPLY", prvgCode = "QUERY")
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
	@ResponseBody
	@RequestMapping(value = "payMark" ) 
	@Privilege(modelCode = "M_MARK_NOR_APPLY",prvgCode = "RESULT_MARK")
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
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum,String vehicleType, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String payResStartDate , String payResEndDate,String repeatedBatchNo,String toFinanceNo) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		StringBuffer sb = new StringBuffer("select t.id,m.to_finance_No,t.repeated_batch_No,t.apply_no,t.vehicle_Plate_Num,t.vehicle_Plate_Type,t.vehicle_Type,");
		sb.append(" t.vehicle_identify_no,t.vehicle_Owner,t.subsidies_Money,t.apply_Time");
		sb.append(" from t_eliminated_apply t ");
		sb.append("inner join t_batch_main m on t.repeated_batch_no=m.batch_no  where 1 = 1 ");
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num like '%").append(vehiclePlateNum).append("%' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
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
		if(StringUtil.isNotEmpty(repeatedBatchNo)) {
			sb.append("and t.repeated_batch_No = '").append(repeatedBatchNo).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicleOwner = '").append(vehicleOwner).append("' ");
		}
		if(StringUtil.isNotEmpty(toFinanceNo)) {
			sb.append("and m.toFinanceNo = '").append(toFinanceNo).append("' ");
		}
		sb.append("and t.bussiness_status = '1' and t.current_post = 'BFJGBJG' and repeated_batch_no is not null");
		try {
			page = payResultService.getRepPageSql(page, sb.toString());
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayResultAction list is Error:" + e, e);
		}
		log.debug("PayResultAction list is end");
	    return returnStr;
	}
	
	
	
					
	
}
