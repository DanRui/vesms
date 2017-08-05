package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

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
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.ApplySpecialAuthority;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ApplySpecialAuthorityService;
import com.jst.vesms.util.EncryptUtils;

/**
 * <p>
 * Title:受理表授权申请
 * </p>
 * <p>
 * Description: 受理表授权申请、查询、查看、审核等功能Action
 * </p>
 * <p>
 * Copyright: DanRui
 * </p>
 * <p>
 * Company: Jst
 * </p>
 * 
 * @author DanRui
 * @version 0.1 2017年7月12日-上午11:02:29
 */
@RequestMapping("/applySpecialAuthority")
@Controller
public class ApplySpecialAuthorityAction extends BaseAction {

	private static final Log log = LogFactory
			.getLog(ApplySpecialAuthority.class);

	@Resource(name = "applySpecialAuthorityServiceImpl")
	private ApplySpecialAuthorityService applySpecialAuthorityService;

	/**
	 * 
	 * <p>
	 * Description: 进入受理单授权申请列表查询页面
	 * </p>
	 * 
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "QUERY")
	public ModelAndView listView() throws Exception {
		String view = "APPLY_SPECIAL_AUTHORITY.LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}

	/**
	 * 根据条件查询已申请的受理单授权列表
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "QUERY")
	public String list(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "DESC") String order,
			@RequestParam(value = "sort", defaultValue = "id") String orderBy,
			String askStartTime, String askEndTime, String applyNo,
			String checkStartTime, String checkEndTime) throws Exception {
		log.debug("ApplySpecialAuthorityAction list is start");
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		String returnStr = "";
		if (StringUtil.isNotEmpty(applyNo)) {
			list.add(new PropertyFilter("EQS_applyNo", applyNo));
		}
		if (StringUtil.isNotEmpty(askStartTime)) {
			list.add(new PropertyFilter("GED_askTime", askStartTime));
		}
		if (StringUtil.isNotEmpty(askEndTime)) {
			list.add(new PropertyFilter("LED_askTime", askEndTime));
		}
		if (StringUtil.isNotEmpty(checkStartTime)) {
			list.add(new PropertyFilter("GED_checkTime", checkStartTime));
		}
		if (StringUtil.isNotEmpty(checkEndTime)) {
			list.add(new PropertyFilter("LED_checkTime", checkEndTime));
		}
		try {
			page = applySpecialAuthorityService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction list is error:" + e, e);
		}
		log.debug("ApplySpecialAuthorityAction list is end");
		return returnStr;
	}

	/**
	 * 
	 * <p>
	 * Description: 进入查询符合授权申请的受理单页面
	 * </p>
	 * 
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listApplyView")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "APPLY")
	public ModelAndView listApplyView() throws Exception {
		String view = "APPLY_SPECIAL_AUTHORITY.APPLY_AUTH_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}

	/**
	 * 根据条件查询符合授权申请的受理单列表
	 */
	@RequestMapping("listSpecialApply")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "APPLY")
	public String listApply(
			@RequestParam(value = "page", defaultValue = "1") int pageNo,
			@RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "order", defaultValue = "DESC") String order,
			@RequestParam(value = "sort", defaultValue = "id") String orderBy,
			String vehiclePlateNum, String vehiclePlateType,
			String vehicleOwner, String applyNo, String vehicleIdentifyNo,
			String startTime, String endTime) throws Exception {
		log.debug("ApplySpecialAuthorityAction listSpecialApply is start");
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		StringBuffer sb = new StringBuffer(
				"select t.* from t_eliminated_apply t where 1 = 1 ");
		String returnStr = "";
		if (StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num = '").append(vehiclePlateNum)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
			sb.append("and t.vehicle_identify_no = '")
					.append(vehicleIdentifyNo).append("' ");
		}
		if (StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicle_owner like '%").append(vehicleOwner)
					.append("%' ");
		}
		if (StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if (StringUtil.isNotEmpty(startTime)) {
			sb.append("and t.apply_time >= to_date('").append(startTime)
					.append("', 'yyyy-MM-dd') ");
		}
		if (StringUtil.isNotEmpty(endTime)) {
			sb.append("and t.apply_time < to_date('").append(endTime)
					.append("', 'yyyy-MM-dd')+1 ");
		}
		//sb.append("and tas.status is null or (tas.apply_no = t.apply_no and tas.status = '0') ");
		sb.append("and (t.apply_confirm_time is null or (t.bussiness_status = '-1' and t.current_post = 'CKSLG' and fault_type = '2')) ");
		sb.append("order by t.last_update_time desc ");
		try {
			page = applySpecialAuthorityService.getPageBySql(page,
					sb.toString());
			page = applySpecialAuthorityService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction listSpecialApply is error:"
					+ e, e);
		}
		log.debug("ApplySpecialAuthorityAction listSpecialApply is end");
		return returnStr;
	}
	
	/**
	 * 
	 * <p>Description: 生成受理单业务授权表</p>
	 * @param ids 受理单id  String
	 * @return String
	 *
	 */
	@RequestMapping("addApply")
	@ResponseBody
	public String addApplySpecial(@RequestParam("id")String id, @RequestParam("applyReason")String applyReason) throws Exception {
		log.debug("ApplySpecialAuthorityAction addApplySpecial is start");
		boolean saveOk = false;
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> result = applySpecialAuthorityService.addApplySpecial(id, applyReason);
			if(null != result && result.get("isSuccess").equals(true)) {
				saveOk = true;
				json.put("msg", "受理单授权申请成功！");
			} else {
				saveOk = false;
				json.put("msg", result.get("msg"));
			}
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction addApplySpecial is error:"+e, e);
			saveOk = false;
			json.put("msg", e.getMessage());
		}
		
		log.debug("ApplySpecialAuthorityAction addApplySpecial is end");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
		} else {
			return JsonUtil.toErrorMsg(JsonUtil.parse(json).toString());
		}
	}

	/**
	 * 
	 * <p>
	 * Description: 进入受理单授权审核页面
	 * </p>
	 * 
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("applyAuthorityView")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "CHECK")
	public ModelAndView applyAuthorityView(String ids) throws Exception {
		String view = "APPLY_SPECIAL_AUTHORITY.CHECK_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("ids", ids);
		return mv;
	}

	/**
	 * 受理单授权申请审核
	 */
	@RequestMapping("check")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "CHECK")
	public String check(@RequestParam("ids") String ids,
			@RequestParam("checkType") String checkType,
			@RequestParam("checkOpinion") String checkOpinion) throws Exception {
		log.debug("ApplySpecialAuthorityAction check is start");
		boolean checkOk = false;
		JSONObject json = new JSONObject();
		String errorStr = "";
		try {
			Map<String, Object> map = applySpecialAuthorityService.check(ids,
					checkType, checkOpinion);
			if (map.get("isSuccess").equals(true)) {
				checkOk = true;
				json.put("msg", "受理单授权审核成功！");
			} else {
				checkOk = true;
				json.put("msg", map.get("msg"));
			}
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction check is error:" + e, e);
			errorStr = e.getMessage();
		}

		log.debug("ApplySpecialAuthorityAction check is end");
		if (checkOk) {
			return JsonUtil.toSuccessMsg(json.toString());
		} else {
			return JsonUtil.toErrorMsg(json.toString());
		}
	}
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("applyNo")String applyNo) throws Exception {
		String view = "APPLY_SPECIAL_AUTHORITY.LIST_DETAIL_VIEW";
		EliminatedApply object = applySpecialAuthorityService.getApplyById(applyNo);
		
		object = this.getApplyWithoutEncryption(object);
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = applySpecialAuthorityService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = applySpecialAuthorityService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = applySpecialAuthorityService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = applySpecialAuthorityService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = applySpecialAuthorityService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = applySpecialAuthorityService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = applySpecialAuthorityService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = applySpecialAuthorityService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = applySpecialAuthorityService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = applySpecialAuthorityService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		// 获取业务流水记录表数据
		List<ActionLog> actionLogList = applySpecialAuthorityService.getActionLogList(object.getId());
		mv.addObject("actionLogs", actionLogList);
		
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
	
	/**
	 * 检查受理单是否能够进行授权申请
	 */
	@RequestMapping("checkApplySpecial")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "APPLY")
	public String checkApplySpecial(@RequestParam("applyNo") String applyNo) throws Exception {
		log.debug("ApplySpecialAuthorityAction checkApplySpecial is start");
		boolean checkOk = true;
		JSONObject json = new JSONObject();
		String errorStr = "";
		try {
			boolean hasRepeatedApply = applySpecialAuthorityService.checkValid(applyNo);
			if (hasRepeatedApply) {
				checkOk = false;
				json.put("msg", "有重复申请的业务授权, 请先处理！");
			} else {
				boolean hasChecked = applySpecialAuthorityService.checkNotApproved(applyNo);
				if (hasChecked) {
					checkOk = false;
					json.put("msg", "该业务授权申请已审批通过, 无法重新申请！");
				}
			}
			
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction checkApplySpecial is error:" + e, e);
			errorStr = e.getMessage();
		}

		log.debug("ApplySpecialAuthorityAction checkApplySpecial is end");
		if (checkOk) {
			return JsonUtil.toSuccessMsg("该受理单可以进行业务授权申请！");
		} else {
			return JsonUtil.toErrorMsg(json.toString());
		}
	}
	
	/**
	 * 检查受理单是否进行了授权申请并审核通过
	 */
	@RequestMapping("checkApplyHasApproved")
	@ResponseBody
	public String checkApplyHasApproved(@RequestParam("applyNo") String applyNo) throws Exception {
		log.debug("ApplySpecialAuthorityAction checkApplyHasApproved is start");
		boolean checkOk = true;
		JSONObject json = new JSONObject();
		String errorStr = "";
		try {
			
			boolean hasChecked = applySpecialAuthorityService.checkNotApproved(applyNo);
			if (!hasChecked) {
				checkOk = false;
				json.put("msg", "该业务无有效的变更补贴对象授权, 请先进行业务授权！");
			}
			
		} catch (Exception e) {
			log.error("ApplySpecialAuthorityAction checkApplyHasApproved is error:" + e, e);
			errorStr = e.getMessage();
		}

		log.debug("ApplySpecialAuthorityAction checkApplyHasApproved is end");
		if (checkOk) {
			return JsonUtil.toSuccessMsg("该受理单可以进行修改！");
		} else {
			return JsonUtil.toErrorMsg(json.toString());
		}
	}
	
	/**
	 * 
	 * <p>
	 * Description: 进入受理单授权填写申请原因页面
	 * </p>
	 * 
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("addApplyReason")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_AUTH", prvgCode = "APPLY")
	public ModelAndView addApplyReason(String id) throws Exception {
		String view = "APPLY_SPECIAL_AUTHORITY.ADD_APPLY_REASON";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("id", id);
		return mv;
	}
	
	
}
