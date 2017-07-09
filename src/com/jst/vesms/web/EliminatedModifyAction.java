
package com.jst.vesms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.util.DateUtil;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.ApplyModifyInfo;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedModifyService;
import com.jst.vesms.util.EncryptUtils;

@RequestMapping("/eliminatedModify")
@Controller
public class EliminatedModifyAction extends BaseAction {

private static final Log log = LogFactory.getLog(EliminatedModifyAction.class);
	
	@Resource(name = "eliminatedModifyServiceImpl")
	private EliminatedModifyService eliminatedModifyService;
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	/**
	 * 
	 * <p>Description: 进入一般资料修正列表页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_ELIMINATED_MODIFY_NOR", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		String view = "ELIMINATED_MODIFY.NOR_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 进入补贴对象变更列表页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("updateAccListView")
	@Privilege(modelCode="M_ELIMINATED_MODIFY_UPA", prvgCode="QUERY")
	public ModelAndView updateAccListView() throws Exception {
		String view = "ELIMINATED_MODIFY.UPA_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_NOR", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
		log.debug("EliminatedModifyAction list is start");
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
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GED_applyTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			Date date = DateUtil.parse(endTime, DateUtil.DATE_PATTERN_2);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
			endTime = DateUtil.format(date, DateUtil.DATE_PATTERN_2);
			list.add(new PropertyFilter("LTD_applyTime",endTime));
		}
		list.add(new PropertyFilter("EQS_bussinessStatus", "-1"));
		list.add(new PropertyFilter("EQS_currentPost", "CKSLG"));
		List<String> faultTypes = new ArrayList<String>();
		faultTypes.add("1");
		faultTypes.add("2");
		list.add(new PropertyFilter("INS_faultType", faultTypes));
		try {
			page = eliminatedModifyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
			page = eliminatedModifyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("EliminatedModifyAction list is Error:" + e, e);
		}
		log.debug("EliminatedModifyAction list is end");
	    return returnStr;
	}
	
	@RequestMapping("updateAccList")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_UPA", prvgCode = "QUERY")
	public String updateAccList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
		log.debug("EliminatedModifyAction updateAccList is start");
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
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GED_applyTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			Date date = DateUtil.parse(endTime, DateUtil.DATE_PATTERN_2);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
			endTime = DateUtil.format(date, DateUtil.DATE_PATTERN_2);
			list.add(new PropertyFilter("LTD_applyTime",endTime));
		}
		list.add(new PropertyFilter("EQS_bussinessStatus", "-1"));
		list.add(new PropertyFilter("EQS_currentPost", "CKSLG"));
		list.add(new PropertyFilter("EQS_faultType", "2"));
		try {
			page = eliminatedModifyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
			page = eliminatedModifyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("EliminatedModifyAction updateAccList is Error:" + e, e);
		}
		log.debug("EliminatedModifyAction updateAccList is end");
	    return returnStr;
	}
	
	/**
	 * 
	 * <p>Description: 根据异常类型进入不同页面</p>
	 * @param id 受理表主键  Integer
	 * @param faultType 异常类型（1,2组合）  String
	 * @return ModelAndView 页面视图
	 *
	 */
	@RequestMapping("chooseModifyType")
	public ModelAndView chooseModifyType(@RequestParam("id")Integer id, String faultType) throws Exception {
		String view = "ELIMINATED_MODIFY.MODIFY_TYPE";
		/*if(StringUtil.isNotEmpty(faultType)&&"2".equals(faultType)) {
			view = "ELIMINATED_CHECK.BACK_CHECK";
		}*/
		EliminatedApply object = eliminatedModifyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		mv.addObject("v", object);
			
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 进入补贴账户修正页面</p>
	 * @param id 受理表主键  Integer
	 * @return ModelAndView 页面视图
	 *
	 */
	@RequestMapping("updateAccountView")
	@Privilege(modelCode="M_ELIMINATED_MODIFY_UPA", prvgCode="VIEW")
	public ModelAndView updateAccountView(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_MODIFY.UPDATE_ACCOUNT";
		EliminatedApply object = eliminatedModifyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		mv.addObject("v", object);
		
		return mv;
	}
	
	/**
	 * 
	 * <p>Description: 根据不同修正类型刷新不同页面板块</p>
	 * @param id 受理表主键  Integer
	 * @param modifyTypes 修正类型组合（10,20,30组合）  String
	 * @return ModelAndView 页面视图
	 *
	 */
	@RequestMapping("modifyApply")
	@Privilege(modelCode="M_ELIMINATED_MODIFY_NOR", prvgCode="VIEW")
	public ModelAndView modifyApply(@RequestParam("id")Integer id, String modifyTypes) throws Exception {
		String view = "ELIMINATED_MODIFY.MODIFY_APPLY";
		/*if(StringUtil.isNotEmpty(faultType)&&"2".equals(faultType)) {
			view = "ELIMINATED_CHECK.BACK_CHECK";
		}*/
		EliminatedApply object = eliminatedModifyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = eliminatedModifyService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = eliminatedModifyService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = eliminatedModifyService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = eliminatedModifyService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = eliminatedModifyService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = eliminatedModifyService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = eliminatedModifyService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = eliminatedModifyService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = eliminatedModifyService.getAttachments("QRSLB", object.getApplyNo());
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("signedApplyFiles", signedApplyFiles);
		
		mv.getModel().put("modifyTypes", modifyTypes);
		mv.addObject("v", object);
			
		return mv;
	}
	
	@ResponseBody
	@Privilege(modelCode="M_ELIMINATED_MODIFY_NOR", prvgCode="MODIFY")
	@RequestMapping(value="modifySave", produces={"text/html;charset=UTF-8;","application/json;"})
	public String modifySave(ApplyModifyInfo applyModifyInfo) throws Exception {
		log.debug("EliminatedModifyAction modifySave is start");
		boolean saveOk = false;
		JSONObject json = new JSONObject();
		try {
			// 更新受理单信息,包括一般资料修正和补贴账户变更
			applyModifyInfo.setFaultType("1");
			Map<String, Object> map = eliminatedModifyService.saveApplyInfo(applyModifyInfo);
			if (map.get("isSuccess").equals(true)) {
				saveOk = true;
				json.put("msg", map.get("msg"));
			}
			
		} catch (Exception e) {
			log.error("EliminatedModifyAction modifySave is Error:"+e, e);
			saveOk = false;
			json.put("msg", e.toString());
		}
		
		log.debug("EliminatedModifyAction modifySave is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(json.toString());
		} else
		return JsonUtil.toErrorMsg(json.toString());
	}
	
	@ResponseBody
	@Privilege(modelCode="M_ELIMINATED_MODIFY_UPA", prvgCode="UPDATE_ACCOUNT")
	@RequestMapping(value="accountSave", produces={"text/html;charset=UTF-8;","application/json;"})
	public String accountSave(ApplyModifyInfo applyModifyInfo) throws Exception {
		log.debug("EliminatedModifyAction accountSave is start");
		boolean saveOk = false;
		JSONObject json = new JSONObject();
		try {
			// 更新受理单信息,包括一般资料修正和补贴账户变更
			applyModifyInfo.setFaultType("2");
			Map<String, Object> map = eliminatedModifyService.saveApplyInfo(applyModifyInfo);
			if (map.get("isSuccess").equals(true)) {
				saveOk = true;
				json.put("msg", map.get("msg"));
			}
		} catch (Exception e) {
			log.error("EliminatedModifyAction accountSave is Error:"+e, e);
			json.put("msg", e.toString());
		}
		
		log.debug("EliminatedModifyAction accountSave is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(json.toString());
		} else
		return JsonUtil.toErrorMsg(json.toString());
	}
	
	@RequestMapping("fileUploadAccChange")
	@ResponseBody
	public String fileUploadAccChange(@RequestParam("id")Integer id, HttpServletRequest request) throws Exception {
		log.debug("eliminatedModifyAction uploadConfirm is start");
		long startTime = System.currentTimeMillis();
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		String tmpDir = PropertyUtil.getPropertyValue("uploadTmpDir");
		System.out.println("----tmpDir: "+tmpDir);
		boolean isSuccess = false;
		JSONObject json = new JSONObject();
         // 将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        multipartResolver.setDefaultEncoding("utf-8");
        // 检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            // 将request变成多部分request
        	DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            System.out.println(multipartRequest.getFile("accountChangeProof"));
            Iterator<String> iter = multipartRequest.getFileNames();
            int count = 0;
            // 依次保存补贴账户变更材料到临时目录中。
//            String filenameAttr = iter.next();
            String filenameAttr = "accountChangeProof";
            String filePaths = "";
            List<MultipartFile> files = multipartRequest.getFiles("accountChangeProof");
            for (MultipartFile file : files) {
            	if (filenameAttr.equals("accountChangeProof")) {
                	if (file.getSize() <= 0) {
                		isSuccess = false;
                		break;
                	}
                }
                
                Map<String, Object> saveResult = saveFile(file, tmpDir, contextPath);
                if (saveResult.get("isSuccess").equals(false)) {
                	isSuccess = false;
                	break;
                } else {
                	// 更新确认受理单文件到附件表
                	boolean saveAccountChangeFile = eliminatedModifyService.saveAttachment("BTDXBGZM", id, saveResult.get("imgSrc").toString(), "2");
                	if (saveAccountChangeFile) {
                		count ++;
                		filePaths += saveResult.get("imgSrc").toString() + ",";
                	} else {
                		// 保存文件路径到附件表失败
                		isSuccess = false;
                	}
                }
            }
            json.put(filenameAttr, filePaths.substring(0, filePaths.length() - 1));
            if (files.size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedModifyAction fileUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
        } 
	}
	
	private Map<String, Object> saveFile(MultipartFile file, String tmpDir, String contextPath) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isSuccess = false;
		if(file != null && file.getSize() > 0)
        {
			String imgSrc = "";
        	String originFileName = file.getOriginalFilename();
        	if (StringUtil.isNotEmpty(originFileName)) {
        		String suffix = ".jpg";
            	String filename = "";
            	int index = originFileName.indexOf(".");
            	if (index >= 0) {
            		suffix = originFileName.substring(index);
            		filename = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + suffix;
            		//filename = originFileName.substring(0, index);
            		//filename = filename + "_" + DateUtil.format(new Date(), DateUtil.TIMESTAMPS_PATTERN_1) + suffix;
            	}
            	System.out.println(filename);
            	
            	String relativePath = tmpDir + File.separator + DateUtil.format(new Date(), DateUtil.DATE_PATTERN_1);
            	String path = contextPath + relativePath;
            	File savePath = new File(path);
            	if (! savePath.exists()) {
            		savePath.mkdirs();
            	}
            	
                //保存
            	try {
            		file.transferTo(new File(path, filename));
            		imgSrc = relativePath + File.separator + filename;
            		isSuccess = true;
            		System.out.println("----imgSrc: "+ imgSrc);
                	result.put("imgSrc", imgSrc.replaceAll("\\\\", "\\/"));
            	} catch (Exception e) {
            		log.error("eliminatedApplyAction saveFile is Error:"+e, e);
            		isSuccess = false;
            	}
        	}
        }
		result.put("isSuccess", isSuccess);
		return result;
	}
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_NOR",prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_MODIFY.COMMON_VIEW";
		if(StringUtil.isNotEmpty(type)&&"account".equals(type)) {
			view = "ELIMINATED_MODIFY.ACCOUNT_CHANGE";
		}
		EliminatedApply object = eliminatedModifyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = eliminatedModifyService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = eliminatedModifyService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = eliminatedModifyService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = eliminatedModifyService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = eliminatedModifyService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = eliminatedModifyService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = eliminatedModifyService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = eliminatedModifyService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = eliminatedModifyService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = eliminatedModifyService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("accountChangeProofFiles", accountChangeProofFiles);
		
		// 获取业务流水记录表数据
		List<ActionLog> actionLogList = eliminatedModifyService.getActionLogList(id);
		mv.addObject("actionLogs", actionLogList);
		mv.addObject("v", object);
			
		return mv;
	}
	
	@RequestMapping("viewUpdateAccount")
	@Privilege(modelCode = "M_ELIMINATED_MODIFY_UPA",prvgCode = "VIEW")
	public ModelAndView viewUpdateAccount(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_MODIFY.COMMON_VIEW";
		if(StringUtil.isNotEmpty(type)&&"account".equals(type)) {
			view = "ELIMINATED_MODIFY.ACCOUNT_CHANGE";
		}
		EliminatedApply object = eliminatedModifyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = eliminatedModifyService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = eliminatedModifyService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = eliminatedModifyService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = eliminatedModifyService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = eliminatedModifyService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = eliminatedModifyService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = eliminatedModifyService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = eliminatedModifyService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = eliminatedModifyService.getAttachments("QRSLB", object.getApplyNo());
		// 补贴对象变更证明材料
		List accountChangeProofFiles = eliminatedModifyService.getAttachments("BTZHMBGZM", object.getApplyNo());
		
		mv.addObject("callbackFiles", callbackFiles);
		mv.addObject("vehicleCancelProofFiles", vehicleCancelProofFiles);
		mv.addObject("bankCardFiles", bankCardFiles);
		mv.addObject("vehicleOwnerProofFiles", vehicleOwnerProofFiles);
		mv.addObject("noFinanceProvideFiles", noFinanceProvideFiles);
		mv.addObject("openAccPromitFiles", openAccPromitFiles);
		mv.addObject("agentProxyFiles", agentProxyFiles);
		mv.addObject("agentProofFiles", agentProofFiles);
		mv.addObject("accountChangeProofFiles", accountChangeProofFiles);
		
		// 获取业务流水记录表数据
		List<ActionLog> actionLogList = eliminatedModifyService.getActionLogList(id);
		mv.addObject("actionLogs", actionLogList);
		mv.addObject("v", object);
			
		return mv;
	}
	
	/**
	 * 进行文件上传
	 */
	@ResponseBody
	@RequestMapping(value="fileUpload", produces={"text/html;charset=UTF-8;","application/json;"})
	public String fileUpload(/*@RequestParam("callbackProofFile") CommonsMultipartFile callbackProofFile,
			@RequestParam("vehicleRegisterProof") CommonsMultipartFile vehicleRegisterProof,
			@RequestParam("vehicleLicense") CommonsMultipartFile vehicleLicense,*/
			String isPersonal, String isProxy,
			HttpServletRequest request) throws Exception {
		log.debug("eliminatedModifyAction fileUpload is start");
		long startTime = System.currentTimeMillis();
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		String tmpDir = PropertyUtil.getPropertyValue("uploadTmpDir");
		System.out.println("----tmpDir: "+tmpDir);
		boolean isSuccess = false;
		JSONObject json = new JSONObject();
         // 将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        multipartResolver.setDefaultEncoding("utf-8");
        // 检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            // 将request变成多部分request
        	DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            System.out.println(multipartRequest.getFile("callbackProofFiles"));
            Iterator<String> iter = multipartRequest.getFileNames();
            int count = 0;
            while(iter.hasNext())
            {
                // 依次遍历所有文件
            	String filenameAttr = iter.next();
                MultipartFile file = multipartRequest.getFile(filenameAttr.toString());
                
                // 依次保存报废回收证明、机动车注销证明、银行卡、车主身份证明等到临时目录中。
                if (filenameAttr.equals("callbackProofFiles") || filenameAttr.equals("vehicleCancelProof") || filenameAttr.equals("bankCard") || filenameAttr.equals("vehicleOwnerProof")) {
                	if (file.getSize() <= 0) {
                		isSuccess = false;
                		json.put("msg", "文件不能为空，请重新上传！");
                		break;
                	}
                }
                
                // 办理类型是代办，则代理委托书和代理人身份证必需上传
                if (isProxy.equals("N") && (filenameAttr.equals("agentProxy") || filenameAttr.equals("agentProof")) && file.getSize() <= 0) {
                	isSuccess = false;
                	json.put("msg", "文件不能为空，请重新上传！");
                	break;
                }
                
                // 车主类型是企业，则非财政供养单位证明和开户许可证必需上传
                if (isPersonal.equals("N") && (filenameAttr.equals("noFinanceProvide") || filenameAttr.equals("openAccPromit")) && file.getSize() <= 0) {
                	isSuccess = false;
                	json.put("msg", "文件不能为空，请重新上传！");
                	break;
                }
                // 其它资料种类
                if (file.getSize() == 0) {
                	json.put(filenameAttr, "");
                	continue;
                }
                Map<String, Object> saveResult = saveFile(file, tmpDir, contextPath);
                if (saveResult.get("isSuccess").equals(false)) {
                	isSuccess = false;
                	json.put("msg", "文件上传失败！");
                	break;
                } else {
                	// 保存文件记录到附件表，并置状态为有效
                	count ++;
                	json.put(filenameAttr, saveResult.get("imgSrc").toString());
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedModifyAction fileUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg(JsonUtil.parse(json).toString());
        } 
	}
	
	/**
	 * 测试高拍仪抓拍上传文件
	 * @param request
	 * @param image
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("fileCaptureUpload")
	public String fileCaptureUpload(HttpServletRequest request) throws Exception {
		log.debug("eliminatedModifyAction fileCaptureUpload is start");
		long startTime = System.currentTimeMillis();
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		String tmpDir = PropertyUtil.getPropertyValue("uploadTmpDir");
		System.out.println("----tmpDir: "+tmpDir);
		boolean isSuccess = false;
		JSONObject json = new JSONObject();
         // 将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        multipartResolver.setDefaultEncoding("utf-8");
        // 检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            // 将request变成多部分request
        	DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator<String> iter = multipartRequest.getFileNames();
            int count = 0;
            while(iter.hasNext())
            {
                // 依次遍历所有文件
            	count ++;
            	String filenameAttr = iter.next();
                MultipartFile file = multipartRequest.getFile(filenameAttr.toString());
                
                // 依次保存报废回收证明、机动车注销证明、银行卡、车主身份证明等到临时目录中。
                // 其它资料种类
                if (file.getSize() <= 0) {
                	isSuccess = false;
            		break;
                }
                Map<String, Object> saveResult = saveFile(file, tmpDir, contextPath);
                if (saveResult.get("isSuccess").equals(false)) {
                	isSuccess = false;
                	break;
                } else {
                	// 写入文件成功，将文件路径传到页面
                	json.put(filenameAttr, saveResult.get("imgSrc").toString());
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedModifyAction fileCaptureUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
        } 
	}
	
	// 进入查询业务授权申请列表页面
	@RequestMapping("listAuthView")
	public ModelAndView listAuthView() throws Exception {
		String view = "ELIMINATED_MODIFY.AUTH_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		return mv;
	}
	
}

