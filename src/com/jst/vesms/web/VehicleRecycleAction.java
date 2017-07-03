
package com.jst.vesms.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jst.vesms.model.VehicleRecycle;
import com.jst.vesms.service.VehicleRecycleService;
import com.jst.vesms.util.EncryptUtils;

@RequestMapping("/vehicleRecycle")
@Controller
public class VehicleRecycleAction extends BaseAction {
	
	private static final Log log = LogFactory.getLog(VehicleRecycleAction.class);
	
	@Resource(name = "vehicleRecycleServiceImpl")
	private VehicleRecycleService vehicleRecycleService;
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	/**
	 * 
	 * <p>Description: 进入报废车辆查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_VEHICLE_RECYCLE_LIST", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		log.debug("VehicleRecycleAction listView is start");
		String view = "VEHICLE_RECYCLE.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		log.debug("VehicleRecycleAction listView is end");
		return mv;
	}
	
	/**
	 * 进行查询数据
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_VEHICLE_RECYCLE_LIST", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleIdentifyNo, String recycleStartDate, String recycleEndDate, String inputStartTime, String inputEndTime) throws Exception{
		log.debug("VehicleRecycleAction list is start");
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
			// 加密车架号
			String des_key = PropertyUtil.getPropertyValue("DES_KEY");
			list.add(new PropertyFilter("EQS_vehicleIdentifyNo", EncryptUtils.encryptDes(des_key, vehicleIdentifyNo)));
		}
		if(StringUtil.isNotEmpty(recycleStartDate)) {
			list.add(new PropertyFilter("GTD_recycleDate", recycleStartDate));
		}
		if(StringUtil.isNotEmpty(recycleEndDate)) {
			list.add(new PropertyFilter("LTD_recycleDate", recycleEndDate));
		}
		if(StringUtil.isNotEmpty(inputStartTime)) {
			list.add(new PropertyFilter("GTD_inputTime", inputStartTime));
		}
		if(StringUtil.isNotEmpty(inputEndTime)) {
			list.add(new PropertyFilter("LTD_inputTime", inputEndTime));
		}
		try {
			page = vehicleRecycleService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			page = vehicleRecycleService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("VehicleRecycleAction list is error:" + e, e);
		}
		log.debug("VehicleRecycleAction list is end");
	    return returnStr;
	}
	
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@ResponseBody
	@RequestMapping(value = "save" )
	public String save(VehicleRecycle vehicleRecycle, String callbackProofFile,
					   String vehicleRegisterProofFiles, String vehicleLicenseFiles) {
		log.debug("TestAction save is start");
		boolean saveOk = false;
		try {
			Serializable returnId = vehicleRecycleService.save(vehicleRecycle);
			if(null != returnId) {
				// 保存附件表
				// 报废回收证明、机动车注册登记证书、行驶证等
				Map<String, Object> map = vehicleRecycleService.saveAttachments((Integer)returnId, callbackProofFile, vehicleRegisterProofFiles, vehicleLicenseFiles);
				if (null != map && map.get("isSave").equals(true)) {
					saveOk = true;
				} else {
					saveOk = false;
				}
			}
		} catch (Exception e) {
			log.error("VehicleRecycleAction save is Error:"+e, e);
			saveOk = false;
		}
		
		log.debug("VehicleRecycleAction save is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg("保存成功");
		} else
		return JsonUtil.toErrorMsg("保存失败");
	}
	
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@ResponseBody
	@RequestMapping(value = "syncVehicleInfo" )
	public String syncVehicleInfo(@RequestParam("callbackProofNo")String callbackProofNo) {
		log.debug("VehicleRecycleAction syncVehicleInfo is start");
		boolean saveOk = false;
		VehicleRecycle vehicleRecycle = null;
		try {
			vehicleRecycle = vehicleRecycleService.getVehicleRecycleByProof(callbackProofNo);
			if(null != vehicleRecycle) {
				saveOk = true;
			}
		} catch (Exception e) {
			log.error("VehicleRecycleAction syncVehicleInfo is Error:"+e, e);
		}
		
		log.debug("VehicleRecycleAction syncVehicleInfo is End");
		if(saveOk) {
			return JsonUtil.toSuccessMsg(JsonUtil.parse(vehicleRecycle).toString ());
		} else
		return JsonUtil.toErrorMsg("报废数据获取失败");
	}
	
	
	/*
	*//**
	 * 测试进行修改数据
	 */
	/*@ResponseBody
	@RequestMapping("update")
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "UPDATE")
	public String update(TestModel testModel)throws Exception{
		String result = WebServiceClient.invokeInterface(SERVICE_NAME, "update", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo ,testModel}, dataType);
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
		String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
		String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
		if(Message.RET_CODE_SUCCESS.equals(retCode)){
			return JsonUtil.toSuccessMsg(retMsg);
		}else{
			return JsonUtil.toErrorMsg(retMsg);
		}
	}*/
	
	/**
	 * 测试进行删除数据
	 */
	@ResponseBody
	@RequestMapping("delete")
	//@Privilege(modelCode = "M_TEST_MANAGER" ,prvgCode = "DELETE")
	public String delete(@RequestParam("id")Integer id) throws Exception {
		log.debug("VehicleRecycleAction delete is start");
		boolean deleteOk = false;
		//将未受理的数据删除，触发器保存到删除表
		Serializable returnObject = vehicleRecycleService.delete(id);
		if(null != returnObject) {
			deleteOk=true;
		}
		log.debug("VehicleRecycleAction delete is End");
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
			HttpServletRequest request) throws Exception {
		log.debug("VehicleRecycleAction fileUpload is start");
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
                // 依次保存报废回收证明、机动车注册登记证书、行驶证等到临时目录中。
                if (filenameAttr.equals("callbackProofFiles") && file.getSize() <= 0) {
                	//必需上传的文件
                	isSuccess = false;
                	break;
                }
                if (file.getSize() == 0) {
                	json.put(filenameAttr, "");
                	continue;
                }
                Map<String, Object> saveResult = saveFile(file, tmpDir, contextPath);
                if (saveResult.get("isSuccess").equals(false)) {
                	isSuccess = false;
                	break;
                } else {
                	json.put(filenameAttr, saveResult.get("imgSrc").toString());
                }
            }
            if (multipartRequest.getFileMap().keySet().size() == count) {
            	isSuccess = true;
            }
        }
        long endTime = System.currentTimeMillis();
		log.debug("VehicleRecycleAction fileUpload is end");
        if (isSuccess) {
        	System.out.println("上传文件的花费时间："+String.valueOf(endTime-startTime)+"ms");
        	return JsonUtil.toSuccessMsg(JsonUtil.parse(json).toString());
        } else {
        	return JsonUtil.toErrorMsg("文件上传失败！");
        } 
	}
	
	@RequestMapping("view")
	@Privilege(modelCode = "M_VEHICLE_RECYCLE_LIST",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		log.debug("VehicleRecycleAction View is start");
		String view = "VEHICLE_RECYCLE.VIEW";
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)) {
			view = "VEHICLE_RECYCLE.EDIT";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		try {
			VehicleRecycle object = vehicleRecycleService.getById(id);
			
			// 获得附件表数据
			// 报废回收证明
			List callbackFiles = vehicleRecycleService.getAttachments("JDCHSZM", object.getId());
			// 机动车注销证明
			List vehicleRegisterFiles = vehicleRecycleService.getAttachments("JDCZCDJZS", object.getId());
			// 行驶证
			List vehicleLicenses = vehicleRecycleService.getAttachments("SSZ", object.getId());
			
			mv.addObject("callbackFiles", callbackFiles);
			mv.addObject("vehicleRegisterFiles", vehicleRegisterFiles);
			mv.addObject("vehicleLicenses", vehicleLicenses);
			
			mv.addObject("v", object);
		} catch (Exception e) {
			log.error("VehicleRecycleAction is error:"+e, e);
		}
		log.debug("VehicleRecycleAction View is end");
		return mv;
	}
	
	/**
	 * 测试进行文件下载
	 */
	@RequestMapping("fileDown")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "DOWN")
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
            		filename = DateUtil.format(new Date(), DateUtil.TIMESTAMPS_PATTERN_1+"SSS") + suffix;
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
            		log.error("VehicleRecycleAction saveFile is Error:"+e, e);
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

