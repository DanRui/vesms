
package com.jst.vesms.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedApplyService;

import net.sf.json.JSONObject;

@RequestMapping("/eliminatedApply")
@Controller
public class EliminatedApplyAction extends BaseAction {
	
	private static final Log log = LogFactory.getLog(EliminatedApplyAction.class);
	
	@Resource(name = "eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
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
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
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
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GTD_applyTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			list.add(new PropertyFilter("LTD_applyTime",endTime));
		}
		try {
			//page = eliminatedApplyService.getPageBySql(page, "select * from t_eliminated_apply where apply_confirm_time is null");
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			page = eliminatedApplyService.filterNoConfirm(page);
			page = eliminatedApplyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("eliminatedApplyAction list is Error:" + e, e);
		}
		log.debug("eliminatedApplyAction list is end");
	    return returnStr;
	}
	
	@RequestMapping("listAll")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String listAll(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleType, 
					   String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime, String batchNo, String archiveBoxNo,
					   String subsidiesMoney, String concludeStatus, String businessStatus, String currentPost) throws Exception{
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
		if(StringUtil.isNotEmpty(startTime)) {
			list.add(new PropertyFilter("GTD_applyConfirmTime",startTime));
		}
		if(StringUtil.isNotEmpty(endTime)) {
			list.add(new PropertyFilter("LTD_applyConfirmTime",endTime));
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
			//page = eliminatedApplyService.getPageBySql(page, "select * from t_eliminated_apply where apply_confirm_time is null");
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			//page = eliminatedApplyService.filterNoConfirm(page);
			page = eliminatedApplyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("eliminatedApplyAction listAll is Error:" + e, e);
		}
		log.debug("eliminatedApplyAction listAll is end");
	    return returnStr;
	}
	
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@ResponseBody
	@RequestMapping(value = "save" )
	public String save(EliminatedApply eliminatedApply, String callbackProofFile, String vehicleCancelProofFiles,
					  String bankCardFiles, String vehicleOwnerProofFiles, String agentProxyFiles, String agentProofFiles,
					  String noFinanceProvideFiles, String openAccPromitFiles) {
		log.debug("eliminatedApplyAction save is start");
		boolean saveOk = false;
		JSONObject json = new JSONObject();
		try {
			// 保存受理单
			Map<String, Object> result = eliminatedApplyService.save(eliminatedApply, callbackProofFile, vehicleCancelProofFiles,
					bankCardFiles, vehicleOwnerProofFiles, agentProxyFiles, agentProofFiles, noFinanceProvideFiles, openAccPromitFiles);
			if(null != result && result.get("isSuccess").equals(true)) {
				// 生成档案盒号、盒内编号
				Map<String, Object> archiveRes = eliminatedApplyService.saveArchive((Integer)result.get("id"));
				
				if (null != archiveRes && archiveRes.get("isSuccess").equals(true)) {
					// 生成成功
					saveOk = true;
					json.put("id", result.get("id"));
					//json.put("applyNo", result.get("applyNo"));
					json.put("msg", "受理单保存成功！");
				} else {
					saveOk = false;
					json.put("msg", archiveRes.get("msg"));
				}
			} else {
				saveOk = false;
				json.put("msg", result.get("msg"));
			}
		} catch (Exception e) {
			log.error("eliminatedApplyAction save is Error:"+e, e);
			saveOk = false;
			json.put("msg", e.toString());
		}
		
		log.debug("eliminatedApplyAction save is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
		} else {
			return JsonUtil.toErrorMsg(JsonUtil.parse(json).toString());
		}
	}
	
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@ResponseBody
	@RequestMapping(value = "getVehicleInfo" )
	public String getVehicleInfo(@RequestParam("vehiclePlateNum")String vehiclePlateNum, @RequestParam("vehiclePlateType")String vehiclePlateType) {
		log.debug("eliminatedApplyAction getVehicleInfo is start");
		boolean saveOk = false;
		EliminatedApply eliminatedApply = null;
		String msg = "";
		try {
			//Map<String, Object> map = eliminatedApplyService.getVehicleInfo(vehiclePlateNum, vehiclePlateType);
			Map<String, Object> map = eliminatedApplyService.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
			if(null != map && map.get("retCode").equals(1)) {
				// 有补贴资格
				saveOk = true;
				eliminatedApply = (EliminatedApply) map.get("apply");
			} else {
				// 无补贴资格或数据库存储过程执行异常
				saveOk = false;
				msg = map.get("msg").toString();
			}
		} catch (Exception e) {
			log.error("eliminatedApplyAction getVehicleInfo is Error:"+e, e);
			msg = "补贴资格存储过程调用失败：" + e.getMessage();
		}
		
		log.debug("eliminatedApplyAction getVehicleInfo is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(eliminatedApply).toString());
		} else {
			return JsonUtil.toErrorMsg(msg);
		}
	}
	
	/**
	 * 测试进行修改数据
	 */
	/*@ResponseBody
	@RequestMapping("update")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "UPDATE")
	public String update(TestModel testModel)throws Exception {
	}*/
	
	/**
	 * 测试进行删除数据
	 */
	@ResponseBody
	@RequestMapping("delete")
	//@Privilege(modelCode = "M_TEST_MANAGER" ,prvgCode = "DELETE")
	public String delete(@RequestParam("id")Integer id) throws Exception {
		log.debug("eliminatedApplyAction delete is start");
		boolean deleteOk = false;
		//将未受理的数据删除，触发器保存到删除表
		Serializable returnObject = eliminatedApplyService.delete(id);
		if(null != returnObject) {
			deleteOk=true;
		}
		log.debug("eliminatedApplyAction delete is End");
		if(deleteOk) {
			return JsonUtil.toSuccessMsg("删除成功");
		} else
		return JsonUtil.toErrorMsg("报废车辆数据删除失败");

	}
	
	/**
	 * 测试进行文件上传
	 */
	@ResponseBody
	@RequestMapping(value="fileUpload", produces={"text/html;charset=UTF-8;","application/json;"})
//	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "UPLOAD")
	public String fileUpload(/*@RequestParam("callbackProofFile") CommonsMultipartFile callbackProofFile,
			@RequestParam("vehicleRegisterProof") CommonsMultipartFile vehicleRegisterProof,
			@RequestParam("vehicleLicense") CommonsMultipartFile vehicleLicense,*/
			String isPersonal, String isProxy,
			HttpServletRequest request) throws Exception {
		log.debug("eliminatedApplyAction fileUpload is start");
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
            	count ++;
            	String filenameAttr = iter.next();
                MultipartFile file = multipartRequest.getFile(filenameAttr.toString());
                
                // 依次保存报废回收证明、机动车注销证明、银行卡、车主身份证明等到临时目录中。
                if (filenameAttr.equals("callbackProofFiles") || filenameAttr.equals("vehicleCancelProof") || filenameAttr.equals("bankCard") || filenameAttr.equals("vehicleOwnerProof")) {
                	if (file.getSize() <= 0) {
                		isSuccess = false;
                		break;
                	}
                }
                
                // 办理类型是代办，则代理委托书和代理人身份证必需上传
                if (isProxy.equals("N") && (filenameAttr.equals("agentProxy") || filenameAttr.equals("agentProof")) && file.getSize() <= 0) {
                	isSuccess = false;
                	break;
                }
                
                // 车主类型是企业，则非财政供养单位证明和开户许可证必需上传
                if (isPersonal.equals("N") && (filenameAttr.equals("noFinanceProvide") || filenameAttr.equals("openAccPromit")) && file.getSize() <= 0) {
                	isSuccess = false;
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
                	break;
                } else {
                	// 保存文件记录到附件表，并置状态为有效
                	json.put(filenameAttr, saveResult.get("imgSrc").toString());
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedApplyAction fileUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
        } 
	}
	
	@RequestMapping("uploadConfirm")
	@ResponseBody
	public String uploadConfirm(@RequestParam("id")Integer id, HttpServletRequest request) throws Exception {
		log.debug("eliminatedApplyAction uploadConfirm is start");
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
            System.out.println(multipartRequest.getFile("signedApplyFile"));
            Iterator<String> iter = multipartRequest.getFileNames();
            int count = 0;
            while(iter.hasNext())
            {
                // 依次遍历所有文件
            	count ++;
            	String filenameAttr = iter.next();
                MultipartFile file = multipartRequest.getFile(filenameAttr.toString());
                
                // 依次保存签字确认的受理表到临时目录中。
                if (filenameAttr.equals("signedApplyFile")) {
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
                	boolean saveSignedApplyFile = eliminatedApplyService.saveAttachment("QZSLB", id, saveResult.get("imgSrc").toString(), "2");
                	if (saveSignedApplyFile) {
                		json.put(filenameAttr, saveResult.get("imgSrc").toString());
                	} else {
                		// 保存文件路径到附件表失败
                		isSuccess = false;
                	}
                	
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedApplyAction fileUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
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
		log.debug("eliminatedApplyAction fileCaptureUpload is start");
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
                	// 保存文件记录到附件表，并置状态为有效
                	json.put(filenameAttr, saveResult.get("imgSrc").toString());
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("eliminatedApplyAction fileCaptureUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
        } 
	}
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_APPLY.VIEW";
		if(StringUtil.isNotEmpty(type) && "update".equals(type)) {
			view = "ELIMINATED_APPLY.EDIT";
		} else if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			view = "ELIMINATED_APPLY.LOG_VIEW";
		}
		EliminatedApply object = eliminatedApplyService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		// 获得附件表数据
		// 报废回收证明
		List callbackFiles = eliminatedApplyService.getAttachments("JDCHSZM", object.getApplyNo());
		// 机动车注销证明
		List vehicleCancelProofFiles = eliminatedApplyService.getAttachments("JDCZXZM", object.getApplyNo());
		// 银行卡
		List bankCardFiles = eliminatedApplyService.getAttachments("YHK", object.getApplyNo());
		// 车主身份证明
		List vehicleOwnerProofFiles = eliminatedApplyService.getAttachments("CZSFZM", object.getApplyNo());
		// 非财政供养单位证明
		List noFinanceProvideFiles = eliminatedApplyService.getAttachments("FCZGYZM", object.getApplyNo());
		// 开户许可证
		List openAccPromitFiles = eliminatedApplyService.getAttachments("KHXKZ", object.getApplyNo());
		// 代理委托书
		List agentProxyFiles = eliminatedApplyService.getAttachments("DLWTS", object.getApplyNo());
		// 代理人身份证
		List agentProofFiles = eliminatedApplyService.getAttachments("DLRSFZ", object.getApplyNo());
		// 确认的受理表
		List signedApplyFiles = eliminatedApplyService.getAttachments("QRSLB", object.getApplyNo());
		
		// 获取业务流水记录表数据
		if (StringUtil.isNotEmpty(type) && "applyLog".equals(type)) {
			List<ActionLog> actionLogList = eliminatedApplyService.getActionLogList(id);
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
		//mv.addObject("vehicleLicenses", vehicleLicenses);
		
		mv.addObject("v", object);
			
		return mv;
	}
	
	/**
	 * 受理单录入入口
	 * <p>Description: 受理单录入入口</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request) throws Exception {
		String view = "ELIMINATED_APPLY.ADD";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("stage", "add");
		
		return mv;
	}
	
	@RequestMapping("applyPreview")
	public ModelAndView applyPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_APPLY.PREVIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("stage", "applyPreview");
		
		EliminatedApply model = eliminatedApplyService.getById(id);
		
		mv.addObject("v", model);
		return mv;
	}
	
	@RequestMapping("confirmPreview")
	public ModelAndView confirmPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_APPLY.CONFIRM";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("stage", "confirm");
		
		EliminatedApply model = eliminatedApplyService.getById(id);
		
		mv.addObject("v", model);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("confirmApply")
	public String confirmApply(@RequestParam("id")Integer id, @RequestParam("signedApplyFiles")String signedApplyFiles) throws Exception {
		log.debug("eliminatedApplyAction confirmApply is start");
		boolean isOk = false;
		boolean hasConfirmed = false;
		// 确认受理表，更新受理确认时间，写ActionLog
		try {
			hasConfirmed = eliminatedApplyService.saveConfirm(id, signedApplyFiles);
			if(hasConfirmed) {
				isOk = true;
			}
		} catch (Exception e) {
			log.error("eliminatedApplyAction confirmApply is Error:"+e, e);
		}
		
		log.debug("eliminatedApplyAction confirmApply is End");
		if(isOk) {
			return JsonUtil.toSuccessMsg("受理表确认成功！");
		} else {
			return JsonUtil.toErrorMsg("受理表确认失败！");
		}
	} 
	
	@RequestMapping("receiptPreview")
	public ModelAndView receiptPreview(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_APPLY.RECEIPT";
		String stage = "over";
		
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("stage", stage);
		
		EliminatedApply model = eliminatedApplyService.getById(id);
		
		mv.addObject("v", model);
		return mv;
	} 
	
	@RequestMapping("continueApply")
	public ModelAndView continueApply(@RequestParam("id")Integer id) throws Exception {
		String view = "ELIMINATED_APPLY.ADD";
		String appointmentNo = null;
		String result = null;
		// 查询当前车辆是否是预约号受理
		Map<String, Object> map = eliminatedApplyService.checkHasAppointed(id);
		if (map.get("hasAppointed").equals(true)) {
			// 预约过，查询出预约的车辆列表传到前台解析；未预约过直接到受理录入页面
			appointmentNo = (String) map.get("appointmentNo");
			result = eliminatedApplyService.getAppointmentInfo(appointmentNo);
			view = "ELIMINATED_APPLY.ADD_NEW_APPLY";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.getModel().put("appointments", result);
		mv.getModel().put("appointmentNo", appointmentNo);
		return mv;
	} 
	
	@ResponseBody
	@RequestMapping("edit")
	public String edit(@RequestParam("id")Integer id, EliminatedApply eliminatedApply) throws Exception {
		log.debug("EliminatedApplyAction edit is start");
		boolean saveOk = false;
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> result = eliminatedApplyService.updateById(id, eliminatedApply);
			if(null != result && result.get("isSuccess").equals(true)) {
				saveOk = true;
				json.put("id", result.get("id"));
				json.put("applyNo", result.get("applyNo"));
				json.put("msg", result.get("msg"));
			}
		} catch (Exception e) {
			log.error("EliminatedApplyAction edit is Error:"+e, e);
		}
		
		log.debug("eliminatedApplyAction edit is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
		} else
		return JsonUtil.toErrorMsg("受理信息保存失败");
	}
	
	/**
	 * 
	 * <p>Description: 根据预约单号，查询出预约车辆列表</p>
	 * @param appointmentNo 预约单号  String
	 * @return String
	 *
	 */
	@ResponseBody
	@RequestMapping("getAppointmentInfo")
	public String getAppointmentInfo(String appointmentNo) throws Exception {
		log.debug("EliminatedApplyAction getAppointmentInfo is start");
		boolean isOk = false;
		String result = null;
		try {
			result = eliminatedApplyService.getAppointmentInfo(appointmentNo);
			if(null != result) {
				isOk = true;
			}
		} catch (Exception e) {
			log.error("EliminatedApplyAction getAppointmentInfo is Error:"+e, e);
		}
		
		log.debug("EliminatedApplyAction getAppointmentInfo is End");
		if(isOk) {
			return JsonUtil.toSuccessMsg(result);
		} else
		return JsonUtil.toErrorMsg("预约数据获取失败");
	}
	
	@ResponseBody
	@RequestMapping("verifyVehicle")
	public String verifyVehicle(@RequestParam("vehiclePlateNum")String vehiclePlateNum, @RequestParam("vehiclePlateType")String vehiclePlateType,
								@RequestParam("vehicleIdentifyNo")String vehicleIdentifyNo) throws Exception {
		log.debug("eliminatedApplyAction verifyVehicle is start");
		boolean saveOk = false;
		EliminatedApply eliminatedApply = null;
		String msg = "";
		try {
			Map<String, Object> map = eliminatedApplyService.verifyVehicle(vehiclePlateNum, vehiclePlateType, vehicleIdentifyNo, "2");
			if(null != map && map.get("7").equals(1)) {
				// 有补贴资格
				saveOk = true;
				
			} else {
				// 无补贴资格或数据库存储过程执行异常
				saveOk = false;
				msg = map.get("8").toString();
			}
		} catch (Exception e) {
			log.error("eliminatedApplyAction verifyVehicle is Error:"+e, e);
			msg = "补贴资格存储过程调用失败：" + e.getMessage();
		}
		
		log.debug("eliminatedApplyAction verifyVehicle is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(eliminatedApply).toString());
		} else {
			return JsonUtil.toErrorMsg(msg);
		}
	}
	
	
	@RequestMapping("verifyResult")
	public ModelAndView verifyResult(@RequestParam("vehiclePlateNum")String vehiclePlateNum, @RequestParam("vehiclePlateType")String vehiclePlateType,
			@RequestParam("vehicleIdentifyNo")String vehicleIdentifyNo) throws Exception {
		String view = "PUBLIC_QUERY_VERIFY.RESULT";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		EliminatedApply apply = new EliminatedApply();
		
		Map<String, Object> map = eliminatedApplyService.verifyVehicle(vehiclePlateNum, vehiclePlateType, vehicleIdentifyNo, "2");
		
		// 获得足够多的车辆信息展示到页面
		if(null != map && map.get("7").equals(1)) {
			// 有补贴资格
			// 补贴金额
			BigDecimal bigDecimal = (BigDecimal) map.get("6");
			Double subsidiesMoney = bigDecimal.doubleValue();
			// 补贴标准说明
			String subsidiesStandard = (String) map.get("8");
			// 车架号
			
			// 获取交警接口数据
			Map<String, Object> jiaoJingVehicleMap = eliminatedApplyService.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
			if (null != jiaoJingVehicleMap && jiaoJingVehicleMap.get("retCode").equals(1)) {
				// 调用存储过程成功
				apply = (EliminatedApply) jiaoJingVehicleMap.get("apply");
				apply.setVehiclePlateNum(vehiclePlateNum);
				apply.setVehiclePlateType(vehiclePlateType);
				apply.setSubsidiesMoney(subsidiesMoney);
				apply.setSubsidiesStandard(subsidiesStandard);
			}
			
		}
		
		System.out.println(map.get("6"));   // 淘汰补贴金额
		System.out.println(map.get("8"));   // 校验备注
		System.out.println(map.get("9"));    // 车架号
		System.out.println(map.get("10"));    // 燃料种类
		System.out.println(map.get("11"));    // 使用性质
		System.out.println(map.get("12"));    // 车辆类型
		System.out.println(map.get("13"));    // 发动机号
		System.out.println(map.get("14"));    // 强制报废期止
		System.out.println(map.get("15"));    // 注销日期
		System.out.println(map.get("16"));   // 注销类别
		System.out.println(map.get("17"));   // 车主
		System.out.println(map.get("18"));   // 车主身份证明号
		System.out.println(map.get("19"));   // 排放标准
		System.out.println(map.get("20"));   // 初次登记日期
		System.out.println(map.get("21"));   // 车辆状态
		System.out.println(map.get("22"));   // 报废交售日期
		System.out.println(map.get("23"));   // 回收证明编号
		
		mv.addObject("v", apply);
		
		return mv;
	}
	
	/**
	 * 测试进行文件下载
	 */
	@RequestMapping("fileDown")
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "DOWN")
	public String fileDown(String fileName ,HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/force-download");// 设置强制下载不打开
		   response.addHeader("Content-Disposition",
		    "attachment;fileName=" + fileName);// 设置文件名
		   File file = new File(fileName);
		   byte[] buffer = new byte[1024];
		   FileInputStream fis = null;
		   BufferedInputStream bis = null;
		   try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
				    os.write(buffer, 0, i);
				    i = bis.read(buffer);
				}
		   } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				   } finally {
				if (bis != null) {
				    try {
				 bis.close();
				    } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				    }
				}
				if (fis != null) {
				    try {
				 fis.close();
				    } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				    }
				}
		   }
		   
		   return null;
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
	
	
	public static void main(String[] args) throws Exception {
		/*List<TestModel> list = new ArrayList();
		TestModel testModel = new TestModel();
		testModel.setName("asdasdasd");
		testModel.setScription("asdqweqwe");
		list.add(testModel);
		WebServiceClient.invokeInterface(SERVICE_NAME, "test", new Object[]{clientId, clientPwd, sessionInfo, terminalInfo ,list}, dataType);*/
		
		//WebServiceClient.invokeInterface(SERVICE_NAME, "test2", new Object[]{testModel}, dataType);
		
		//System.out.println(RedisUtil.getValue(RedisUtil.SYS_DICT_LIST, "VEHICLE_TYPE"));
		//System.out.println(RedisUtil.getValue(RedisUtil.SYS_DICT+RedisUtil.SPLIT_STR+"VEHICLE_TYPE", "B11"));
		//System.out.println(RedisUtil.getValue(RedisUtil.SYS_DICT+RedisUtil.SPLIT_STR+"VEHICLE_TYPE", "重型普通半挂车"));
		
		int id = 1;
		
		
	}
	
	
}

