
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
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.util.DateUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.SysDictService;
import com.jst.vesms.service.WorkLoggingService;
import com.jst.vesms.util.EncryptUtils;

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
	 * <p>Description: 进入工作记录查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_COMMON_QUERY_WORK", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		String view = "COMMON_QUERY_WORK.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 进入受理综合查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listApplyView")
	@Privilege(modelCode="M_COMMON_QUERY_ALL", prvgCode="QUERY")
	public ModelAndView listApplyView() throws Exception {
		String view = "COMMON_QUERY_ALL.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 根据查询条件，获取工作记录列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_COMMON_QUERY_WORK", prvgCode = "QUERY")
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
		sb.append("b.emission_standard emissionStandard, b.iol_type iolTpye, b.subsidies_money subsidiesMoney, b.current_post currentPost, b.bussiness_status bussinessStatus, b.id applyId ");
		sb.append("from t_action_log a, t_eliminated_apply b ");
		sb.append("where 1 = 1 and a.apply_no = b.apply_no ");
		
		if (StringUtil.isNotEmpty(post))
		{
			sb.append("and a.current_post = '").append(post).append("' ");
		}
		if (StringUtil.isNotEmpty(actionUser)) {
			if (!"请选择".equals(actionUser)) {
				sb.append("and a.action_user_code = '").append(actionUser).append("' ");
			}
		}
		if (StringUtil.isNotEmpty(action)) {
			if (!"请选择".equals(action)) {
				sb.append("and a.action_name = '").append(action).append("' ");
			}
		}
		if (StringUtil.isNotEmpty(actionResult)) {
			if (!"请选择".equals(actionResult)) {
				sb.append("and a.action_result = '").append(actionResult).append("' ");
			}
		}
		if (StringUtil.isNotEmpty(startTime)) {
			sb.append("and a.action_time >= to_date('").append(startTime).append("', 'yyyy-MM-dd') ");
		}
		if (StringUtil.isNotEmpty(endTime)) {
			sb.append("and a.action_time < to_date('").append(endTime).append("', 'yyyy-MM-dd')+1 ");
		}
		if (StringUtil.isNotEmpty(orderBy)) {
			if (StringUtil.isNotEmpty(order)) {
				sb.append("order by ").append(orderBy).append(" ").append(order).append(" ");
			}
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
	
	/**
	 * 
	 * <p>Description: 获取操作人员列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@ResponseBody
	@RequestMapping("getActionUserList")
	public String getActionUserList(String postCode) throws Exception {
		log.debug("WorkLoggingAction getPostList is start");
		String list = null;
		try {
			// 岗位列表
			list = workLoggingService.getActionUserList(postCode);
		} catch (Exception e) {
			log.error("WorkLoggingAction getPostList is Error:" + e, e);
			list = null;
		}
		log.debug("WorkLoggingAction getPostList is end");
		return list;
	}
	
	/**
	 * 
	 * <p>Description: 获取操作动作列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@ResponseBody
	@RequestMapping("getActionNameList")
	public String getActionNameList(String postCode) throws Exception {
		log.debug("WorkLoggingAction getPostList is start");
		String list = null;
		try {
			// 岗位列表
			list = workLoggingService.getActionNameList(postCode);
		} catch (Exception e) {
			log.error("WorkLoggingAction getPostList is Error:" + e, e);
			list = null;
		}
		log.debug("WorkLoggingAction getPostList is end");
		return list;
	}
	
	/**
	 * 
	 * <p>Description: 获取操作结果列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	@ResponseBody
	@RequestMapping("getActionResultList")
	public String getActionResultList(String postCode) throws Exception {
		log.debug("WorkLoggingAction getActionResultList is start");
		String list = null;
		try {
			// 岗位列表
			list = workLoggingService.getActionResultList(postCode);
		} catch (Exception e) {
			log.error("WorkLoggingAction getActionResultList is Error:" + e, e);
			list = null;
		}
		log.debug("WorkLoggingAction getActionResultList is end");
		return list;
	}
	
	@RequestMapping("listApply")
	@ResponseBody
	@Privilege(modelCode = "M_COMMON_QUERY_ALL", prvgCode = "QUERY")
	public String listApply(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleType, 
					   String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime, String batchNo, String archiveBoxNo,
					   String subsidiesMoney, String concludeStatus, String businessStatus, String currentPost) throws Exception{
		log.debug("WorkLoggingAction listApply is start");
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
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
			list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo",applyNo));
		}
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GED_applyConfirmTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			Date date = DateUtil.parse(endTime, DateUtil.DATE_PATTERN_2);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
			endTime = DateUtil.format(date, DateUtil.DATE_PATTERN_2);
			list.add(new PropertyFilter("LED_applyConfirmTime",endTime));
		}
		if(StringUtil.isNotEmpty(batchNo)) {
			list.add(new PropertyFilter("EQS_batchNo",batchNo));
		}
		if(StringUtil.isNotEmpty(archiveBoxNo)) {
			list.add(new PropertyFilter("EQS_archiveBoxNo",archiveBoxNo));
		}
		if(StringUtil.isNotEmpty(subsidiesMoney)) {
			list.add(new PropertyFilter("EQN_subsidiesMoney",subsidiesMoney));
		}
		if(StringUtil.isNotEmpty(concludeStatus)) {
			list.add(new PropertyFilter("EQS_concludeStatus",concludeStatus));
		}
		if(StringUtil.isNotEmpty(businessStatus)) {
			list.add(new PropertyFilter("EQS_bussinessStatus",businessStatus));
		}
		if(StringUtil.isNotEmpty(currentPost)) {
			list.add(new PropertyFilter("EQS_currentPost",currentPost));
		}
		
		try {
			page = workLoggingService.getApplyPage(list, page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("WorkLoggingAction listApply is Error:" + e, e);
		}
		log.debug("WorkLoggingAction listApply is end");
	    return returnStr;
	}
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_COMMON_QUERY_ALL", prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		if(StringUtil.isNotEmpty(type) && "update".equals(type)) {
			view = "ELIMINATED_APPLY.EDIT";
		} else if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			view = "ELIMINATED_APPLY.LOG_VIEW";
		}
		EliminatedApply object = workLoggingService.getApplyById(id);
		
		object = this.getApplyWithoutEncryption(object);
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = workLoggingService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = workLoggingService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = workLoggingService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = workLoggingService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = workLoggingService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = workLoggingService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = workLoggingService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = workLoggingService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = workLoggingService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = workLoggingService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		// 获取业务流水记录表数据
		if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			List<ActionLog> actionLogList = workLoggingService.getActionLogList(id);
			mv.addObject("actionLogs", actionLogList);
		}
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("signedApplyFiles", signedApplyFiles);
		mv.addObject("accountChangeProofFiles", accountChangeProofFiles);
		
		mv.addObject("v", object);
			
		return mv;
	}
	
	
	@RequestMapping("workLogView")
	@Privilege(modelCode = "M_COMMON_QUERY_WORK", prvgCode = "VIEW")
	public ModelAndView workLogView(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		if(StringUtil.isNotEmpty(type) && "update".equals(type)) {
			view = "ELIMINATED_APPLY.EDIT";
		} else if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			view = "ELIMINATED_APPLY.LOG_VIEW";
		}
		EliminatedApply object = workLoggingService.getApplyById(id);
		
		object = this.getApplyWithoutEncryption(object);
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = workLoggingService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = workLoggingService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = workLoggingService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = workLoggingService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = workLoggingService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = workLoggingService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = workLoggingService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = workLoggingService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = workLoggingService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = workLoggingService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		// 获取业务流水记录表数据
		if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			List<ActionLog> actionLogList = workLoggingService.getActionLogList(id);
			mv.addObject("actionLogs", actionLogList);
		}
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("accountChangeProofFiles", accountChangeProofFiles);
		
		mv.addObject("v", object);
		
		return mv;
	}
	
	@RequestMapping("receiptPreview")
	@Privilege(modelCode="M_COMMON_QUERY_ALL", prvgCode="PRINT_RECEIPT")
	public ModelAndView receiptPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_APPLY.RECEIPT";
		String stage = "over";
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("stage", stage);
		
		EliminatedApply model = workLoggingService.getApplyById(id);
		
		model = this.getApplyWithoutEncryption(model);
		
		// 特殊处理银行账号
		String bankAccountNo = model.getBankAccountNo();
		String pageViewAccountNo = bankAccountNo.replaceAll("(?<=\\d{5})\\d(?=\\d{4})", "*");
		model.setBankAccountNo(pageViewAccountNo);
		
		mv.addObject("v", model);
		return mv;
	} 
	
	/**
	 * 
	 * <p>Description: 解密车架号、车主身份信息、银行账号等信息，返回给页面，防止service层提交。</p>
	 * @param eliminatedApply 受理表对象 EliminatedApply
	 * @return EliminatedApply
	 *
	 */
	private EliminatedApply getApplyWithoutEncryption(
			EliminatedApply eliminatedApply) {
		
		// 解密车架号等数据，防止在service层更新数据到数据库
		String des_key = PropertyUtil.getPropertyValue("DES_KEY");
		// 解密车架号
		eliminatedApply.setVehicleIdentifyNo(EncryptUtils.decryptDes(des_key, eliminatedApply.getVehicleIdentifyNo()));
		
		// 解密银行账号
		eliminatedApply.setBankAccountNo(EncryptUtils.decryptDes(des_key, eliminatedApply.getBankAccountNo()));
		
		// 解密车主手机号
		eliminatedApply.setMobile(EncryptUtils.decryptDes(des_key, (eliminatedApply.getMobile() == null) ? "" : eliminatedApply.getMobile()));
		
		// 解密经办人手机号 
		eliminatedApply.setAgentMobileNo(EncryptUtils.decryptDes(des_key, (eliminatedApply.getAgentMobileNo() == null) ? "" : eliminatedApply.getAgentMobileNo()));
		
		// 解密车主身份证明号
		eliminatedApply.setVehicleOwnerIdentity(EncryptUtils.decryptDes(des_key, (eliminatedApply.getVehicleOwnerIdentity() == null) ? "" : eliminatedApply.getVehicleOwnerIdentity()));
		
		// 解密经办人身份证号
		eliminatedApply.setAgentIdentity(EncryptUtils.decryptDes(des_key, (eliminatedApply.getAgentIdentity() == null) ? "" : eliminatedApply.getAgentIdentity()));
		return eliminatedApply;
	}
}

