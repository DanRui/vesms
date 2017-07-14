package com.jst.vesms.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.DateUtil;
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedCheckService;
import com.jst.vesms.util.EncryptUtils;

@RequestMapping("/eliminatedCheck")
@Controller
public class EliminatedCheckAction extends BaseAction {
	
private static final Log log = LogFactory.getLog(EliminatedCheckAction.class);
	
	@Resource(name = "eliminatedCheckServiceImpl")
	private EliminatedCheckService eliminatedCheckService;
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	
	/**
	 * 
	 * <p>Description: 进入正常审核页面</p>
	 * @param currentPost 当前岗位  String
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_ELIMINATED_CHECK_PRVG", prvgCode="QUERY")
	public ModelAndView listView(String currentPost) throws Exception {
		String view = "ELIMINATED_CHECK.LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("currentPost", currentPost);
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 进入修正业务重审核页面</p>
	 * @param currentPost 当前岗位  String
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("backListView")
	@Privilege(modelCode="M_ELIMINATED_CHECK_PRVG", prvgCode="QUERY")
	public ModelAndView backListView(String currentPost) throws Exception {
		String view = "ELIMINATED_CHECK.BACK_LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("currentPost", currentPost);
		return mv;
	}
	
	
	/**
	 * 
	  * <p>Description: 正常审核查询列表方法</p>
	  * @param name description type
	  * @return List
	  *
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode="M_ELIMINATED_CHECK_PRVG", prvgCode="QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, 
			   String vehiclePlateType, String vehicleType, String vehicleOwner, String applyNo, 
			   String vehicleIdentifyNo, String applyStartDate, String applyEndDate, String currentPost) throws Exception{
			log.debug("EliminatedCheckAction list is start");
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
			if(StringUtil.isNotEmpty(vehicleType)) {
				list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
			}
			if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
				// 加密车架号
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo", EncryptUtils.encryptDes(des_key, vehicleIdentifyNo)));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(applyStartDate)) {
				list.add(new PropertyFilter("GED_applyConfirmTime",applyStartDate));
			}
			if(StringUtil.isNotEmpty(applyEndDate)) {
				Date date = DateUtil.parse(applyEndDate, DateUtil.DATE_PATTERN);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				date = calendar.getTime();
				applyEndDate = DateUtil.format(date, DateUtil.DATE_PATTERN);
				list.add(new PropertyFilter("LTD_applyConfirmTime",applyEndDate));
			}
			list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
			list.add(new PropertyFilter("EQS_isFault", "0"));
			if (StringUtil.isNotEmpty(currentPost)) {
				list.add(new PropertyFilter("EQS_currentPost", currentPost));// 当前岗位是在当前登陆用户的岗位的数据
			}
			try {
				page = eliminatedCheckService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
				page = eliminatedCheckService.getPageExtra(page);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("EliminatedCheckAction list is Error:" + e, e);
				returnStr = e.getMessage();
			}
			log.debug("EliminatedCheckAction list is end");
			return returnStr;
		}
	
	@RequestMapping("backList")
	@ResponseBody
	@Privilege(modelCode="M_ELIMINATED_CHECK_PRVG", prvgCode="QUERY")
	public String backList(@RequestParam(value="page", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, 
			   String vehiclePlateType, String vehicleType, String vehicleOwner, String applyNo, 
			   String vehicleIdentifyNo, String applyStartDate, String applyEndDate, String currentPost) throws Exception{
			log.debug("EliminatedCheckAction backList is start");
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
			if(StringUtil.isNotEmpty(vehicleType)) {
				list.add(new PropertyFilter("EQS_vehicleType",vehicleType));
			}
			if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
				// 加密车架号
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo", EncryptUtils.encryptDes(des_key, vehicleIdentifyNo)));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(applyStartDate)) {
				list.add(new PropertyFilter("GED_applyConfirmTime",applyStartDate));
			}
			if(StringUtil.isNotEmpty(applyEndDate)) {
				Date date = DateUtil.parse(applyStartDate, DateUtil.DATE_PATTERN);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				date = calendar.getTime();
				applyEndDate = DateUtil.format(date, DateUtil.DATE_PATTERN);
				list.add(new PropertyFilter("LTD_applyConfirmTime",applyEndDate));
			}
			
			if (StringUtil.isNotEmpty(currentPost)) {
				list.add(new PropertyFilter("EQS_currentPost", currentPost));// 当前岗位是在当前登陆用户的岗位的数据
			}
			
			list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
			list.add(new PropertyFilter("EQS_isFault", "1"));
			try {
				page = eliminatedCheckService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
				page = eliminatedCheckService.getPageExtra(page);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("EliminatedCheckAction backList is Error:" + e, e);
			}
			log.debug("EliminatedCheckAction backList is end");
			return returnStr;
		}
	
	@RequestMapping("checkView")
	@Privilege(modelCode = "M_ELIMINATED_CHECK_PRVG",prvgCode = "CHECK")
	public ModelAndView checkView(@RequestParam("ids")String ids, @RequestParam("currentPost")String currentPost) throws Exception {
		String view = "ELIMINATED_CHECK.CHECK_VIEW";
		//EliminatedApply object = eliminatedCheckService.getById(ids);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		mv.addObject("currentPost", currentPost);
			
		return mv;
	}
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_ELIMINATED_CHECK_PRVG",prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_CHECK.NORMAL_CHECK";
		if(StringUtil.isNotEmpty(type)&&"back".equals(type)) {
			view = "ELIMINATED_CHECK.BACK_CHECK";
		}
		EliminatedApply object = eliminatedCheckService.getById(id);
		
		object = this.getApplyWithoutEncryption(object);
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = eliminatedCheckService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = eliminatedCheckService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = eliminatedCheckService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = eliminatedCheckService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = eliminatedCheckService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = eliminatedCheckService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = eliminatedCheckService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = eliminatedCheckService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = eliminatedCheckService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = eliminatedCheckService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("accountChangeProofFiles", accountChangeProofFiles);
		mv.addObject("signedApplyFiles", signedApplyFiles);
		
		// 获取业务流水记录表数据
		List<ActionLog> actionLogList = eliminatedCheckService.getActionLogList(id);
		mv.getModel().put("busStatus", type);
		mv.addObject("actionLogs", actionLogList);
		mv.addObject("v", object);
			
		return mv;
	}
	
	@RequestMapping("check")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_CHECK_PRVG", prvgCode = "CHECK")
	public String check(@RequestParam("ids")String ids, @RequestParam("checkType")String checkType,
						@RequestParam("faultType")String faultType, @RequestParam("checkOpinion")String checkOpinion, @RequestParam("currentPost")String currentPost) throws Exception {
		log.debug("EliminatedCheckAction check is start");
		boolean checkOk = false;
		JSONObject json = new JSONObject();
		String errorStr = "";
		try {
			/*if (StringUtil.isNotEmpty(checkOpinion)) {
				checkOpinion = new String(checkOpinion.getBytes("ISO8859-1"), "UTF-8"); 
			}*/
			faultType = faultType.equals("请选择") == true ? null : faultType;
			Map<String, Object> map = eliminatedCheckService.check(ids, checkType, faultType, checkOpinion, currentPost);
			if (map.get("isSuccess").equals(true)) {
				checkOk = true;
				json.put("msg", map.get("msg"));
			} else {
				// 存储过程调用或者执行异常，如何处理
			}
		} catch (Exception e) {
			log.error("EliminatedCheckAction check is Error:"+e, e);
			errorStr = e.getMessage();
		}
		
		log.debug("EliminatedCheckAction check is End");
		if(checkOk) {
			return JsonUtil.toSuccessMsg(json.toString());
		} else {
			return JsonUtil.toErrorMsg(json.toString());
		}
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
