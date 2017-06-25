package com.jst.test.web;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.system.client.WebServiceClient;
import com.jst.system.util.SystemUtil;
import com.jst.system.wrapper.XssRequestWrapper;
import com.jst.test.model.ClassRecord;
import com.jst.test.model.TestModel;
import com.jst.type.DataType;
import com.jst.util.EncryptUtil;
import com.jst.util.JsonUtil;
import com.jst.util.MessageHandlerUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;

import net.sf.json.JSONObject;



@RequestMapping("/test")
@Controller
public class TestAction extends BaseAction{
	
	
	private static final Log log = LogFactory.getLog(TestAction.class);		//记录运行日志
	
	private static final String  SERVICE_NAME = "TestService";		//用于调用WebService的名称
	
	private static final DataType dataType = DataType.JSON;			//调用WebService时所用的数据类型
	
	/**
	 * 测试进行查询数据
	 */
	@RequestMapping("list")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String name, String scription) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		if(StringUtil.isNotEmpty(name)){
			list.add(new PropertyFilter("LIKES_name",name));
		}
		if(StringUtil.isNotEmpty(scription)){
			list.add(new PropertyFilter("LIKES_scription",scription));
		}
		MessageHandler wrapper = MessageHandlerUtil.getMessageHandler(dataType);
		ObjectSerializeConfig serializeConfig = initPage(wrapper, page, list);
		String requestStr = wrapper.generateRequestMessage();
		String result = WebServiceClient.invokeInterface(SERVICE_NAME, "list", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo ,requestStr}, dataType);
		log.debug("反序列化对象");
	    //response.setCharacterEncoding("UTF-8");  
		serializeConfig.clear();
		serializeConfig.setObjectAlias(Page.class, Message.RECORD_NAME);
		serializeConfig.setObjectAlias(TestModel.class, "testModel");
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
	    Page pageData = (Page)SystemUtil.serializeObject(Page.class , handler , dataType,serializeConfig);
	    String returnStr = writerPage(pageData);
	    return returnStr;
	}
	
	/**
	 * 测试进行保存数据
	 */
	@ResponseBody
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@RequestMapping("save")
	public String save(@Valid TestModel testModel) throws Exception{
		String  result = WebServiceClient.invokeInterface(SERVICE_NAME, "save", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo ,testModel}, dataType);
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
		String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
		String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
		if(Message.RET_CODE_SUCCESS.equals(retCode)){
			return JsonUtil.toSuccessMsg(retMsg);
		}else{
			return JsonUtil.toErrorMsg(retMsg);
		}
	}
	
	/**
	 * 测试进行修改数据
	 */
	@ResponseBody
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
	}
	
	/**
	 * 测试进行删除数据
	 */
	@ResponseBody
	@RequestMapping("delete")
	@Privilege(modelCode = "M_TEST_MANAGER" ,prvgCode = "DELETE")
	public String delete(Integer id) throws Exception{
		String result = WebServiceClient.invokeInterface(SERVICE_NAME, "delete", new Object[]{clientId, clientPwd,sessionInfo,terminalInfo ,id}, dataType);
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
		String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
		String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
		if(Message.RET_CODE_SUCCESS.equals(retCode)){
			return JsonUtil.toSuccessMsg(retMsg);
		}else{
			return JsonUtil.toErrorMsg(retMsg);
		}
	}
	
	/**
	 * 测试进行文件上传
	 */
	@ResponseBody
	@RequestMapping("fileUpload")
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "UPLOAD")
	public String fileUpload(/*@RequestParam("file") CommonsMultipartFile file*/HttpServletRequest request) throws Exception{
	    /* String path="E:/"+new Date().getTime()+file.getOriginalFilename();    
	     File newFile=new File(path);
	        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
	     file.transferTo(newFile);*/
		ShiroHttpServletRequest shiroRequest = null;
		if(request instanceof XssRequestWrapper){
			shiroRequest = (ShiroHttpServletRequest)((XssRequestWrapper) request).getRequest();
		}
		 long  startTime=System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
        	 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());  
           //获取multiRequest 中所有的文件名
            Iterator iter=multipartRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                MultipartFile file=multipartRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String path="E:/springUpload"+file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
            }
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return JsonUtil.toSuccessMsg("文件上传成功！");
	}
	
	/**
	 * 测试进行查看操作
	 */
	@RequestMapping("view")
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@PathParam("id")Integer id, @PathParam(value = "type")String type) throws Exception{
		String result = WebServiceClient.invokeInterface(SERVICE_NAME, "view", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo ,id}, dataType);
		String view = "TEST.VIEW";
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)){
			view = "TEST.UPDATE";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
		String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
		String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
		if(Message.RET_CODE_SUCCESS.equals(retCode)){
			Object object  = SystemUtil.serializeObject(TestModel.class, handler, dataType);
			mv.addObject("v", object);
		}else{
			
		}
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
	
	
	
	
	public static void main(String[] args) throws Exception {
		/*List<TestModel> list = new ArrayList();
		TestModel testModel = new TestModel();
		testModel.setName("asdasdasd");
		testModel.setScription("asdqweqwe");
		list.add(testModel);
		WebServiceClient.invokeInterface(SERVICE_NAME, "test", new Object[]{clientId, clientPwd, sessionInfo, terminalInfo ,list}, dataType);*/
		
		//WebServiceClient.invokeInterface(SERVICE_NAME, "test2", new Object[]{testModel}, dataType);
		
		//System.out.println(TestModel.class.getSimpleName());
		
		System.out.println(EncryptUtil.encryptDES("1111111122222222", "asdasdasdasdasd"));
		System.out.println(EncryptUtil.decryptDES("1122334455667788", "b74298a5de0dee4f62e5a7e42628ef038cf8c1bb450ccea7"));
	}
	
	
	
}
