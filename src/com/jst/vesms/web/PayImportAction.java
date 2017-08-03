package com.jst.vesms.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.DateUtil;
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.BatchExport;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;
import com.jst.vesms.service.PayImportService;


@RequestMapping("/payImport")
@Controller
public class PayImportAction extends BaseAction{
private static final Log log = LogFactory.getLog(PayImportAction.class);
	
	@Resource(name="payImportServiceImpl")
	private PayImportService payImportService;
	
	@RequestMapping("payImportView")
//	@Privilege(modelCode = "M_MARK_IMPORT",prvgCode = "QUERY")
//	@Privilege(modelCode = "M_PAY_IMPORT_LIST",prvgCode = "QUERY")
	public ModelAndView payImportView() throws Exception {
		log.debug("PayImportAction payImportView is start");
		String view = "PAY_IMPORT.IMPORT_LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		log.debug("PayImportAction payImportView is end");
		return mv;
	}
	
	
	/**
	 * 进行查询数据
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String makeStartTime,String makeEndTime,String importStartTime,String importEndTime) throws Exception{
		log.debug("PayImportAction list is start");
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(makeStartTime)) {
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
		}
		try {
			page = payImportService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("PayImportAction list is Error:" + e, e);
		}
		log.debug("PayImportAction list is end");
	    return returnStr;
	}
	

	
	
	@RequestMapping("importView")
	@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "VIEW")
	public ModelAndView importView(@RequestParam("id")Integer id) throws Exception {
		log.debug("PayImportAction importView is start");
		PayResultImport object = payImportService.importNoView(id);
		ModelAndView mv = null;
		String view = "PAY_IMPORT.VIEW";
		mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		log.debug("PayImportAction importView is end");
		return mv;
	}
	
	
	
	
	
	
	
		//去国库导入页面
		@RequestMapping("toFileImport")
		@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "FILE_IMPORT")
		public ModelAndView toFileImport() throws Exception {
			log.debug("PayImportAction toFileImport is start");
			String view = "PAY_IMPORT.FILE_IMPORT";
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			log.debug("PayImportAction toFileImport is end");
			return mv;
		}
		
		
		@ResponseBody
		@RequestMapping(value="/importExcel",produces="application/json;charset=UTF-8")
		@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "FILE_IMPORT")
		 public String importExcel(HttpServletRequest req, HttpServletResponse res)
		            throws ServletException, IOException {
			log.debug("PayImportAction importExcel is start");
			//int count = 0;
			 //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
			String path = "";
			String result="";
			boolean isOk = false;
			String fileName="";
			String newFileName="";
			String excelImport="";
			File file1=null;
	        CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver(
	        		req.getSession().getServletContext());
	        multipartResolver.setDefaultEncoding("utf-8");	      
	        // 判断 request 是否有文件上传,即多部分请求(这里只有一个请求)
	        if(multipartResolver.isMultipart(req))
	        {
	            // 将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)req;
	            // 获取multiRequest 中所有的文件名
	            Iterator<String> iter=multiRequest.getFileNames();
	            //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                fileName=file.getOriginalFilename();               
                if (file!=null)
                {	
                	try {              		
            			List sqlList1 = payImportService.getNewFileName(fileName);
            			for (int i = 0; i < sqlList1.size(); i++) {
            				newFileName =  (String) sqlList1.get(0);              			
            			}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("PayImportAction importExcel is Error:"+e, e);
						result="数据执行失败，请联系管理员";
						return JsonUtil.toErrorMsg(result);
					}
                	 // 创建服务器路径
    	            excelImport = PropertyUtil.getPropertyValue("excelImport");
    	            if (! new File(excelImport).exists()) {
                		new File(excelImport).mkdirs();
                	}
    	            path =excelImport+newFileName;
                    file1 = new File(path);
                    file.transferTo(file1);	//上传
                }
	         }
		        try {
		        	// 把国库数据导入数据库
					String payImportId= payImportService.savePayImport(newFileName,file1);
					// 调用存储过程
					result = payImportService.importPayResult(payImportId);
					isOk=true;
				} catch (Exception e) {					
					// TODO Auto-generated catch block
					log.error("PayImportAction importExcel is Error:"+e, e);
					isOk=false;
					result="文件不满足国库文件格式";
				}		        
		        if(isOk){
		        	result = JsonUtil.toSuccessMsg("文件导入标记执行成功，标记结果请查看文件导入标记详情"); 
		        	return result;      
		        }else{
		        	return JsonUtil.toErrorMsg(result);
		        }
		  }
		
		
		
		//去国库文件标记页面
		@RequestMapping(value="importMarkView")
		@Privilege(modelCode = "M_MARK_NOR_APPLY",prvgCode = "RESULT_MARK")
		@ResponseBody
		public ModelAndView importMarkView(@RequestParam("id")Integer id){
			log.debug("PayImportAction importMarkView is start");
			String mark = "PAY_IMPORT.IMPORT_MARK";
			PayResultImport object = payImportService.importNoView(id);
			ModelAndView mv = new ModelAndView(getReturnPage(mark));
			mv.addObject("v", object);
			log.debug("PayImportAction importMarkView is End");
			return mv;
		}
		
		
		//去退款业务标记页面
		@RequestMapping("payResView")
		@Privilege(modelCode = "M_MARK_NOR_APPLY",prvgCode = "RESULT_MARK")
		public ModelAndView payResView() {
			log.debug("PayImportAction payResView is start");
			String view = "PAY_IMPORT.PAY_RES_MARK";
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			log.debug("PayImportAction payResView is end");
			return mv;
		}
		
		//去拨付成功业务标记页面
		@RequestMapping("payNorView")
		@Privilege(modelCode = "M_MARK_NOR_APPLY",prvgCode = "RESULT_MARK")
		public ModelAndView payNorView() {
			log.debug("PayImportAction payNorView is start");
			String view = "PAY_IMPORT.PAY_NOR_MARK";
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			log.debug("PayImportAction payNorView is end");
			return mv;
		}
		
		
		//获取导入文件的路径
		@RequestMapping(value="/getImportPath",produces="application/json;charset=UTF-8")
		@ResponseBody
		@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "TREASURY_QUERY")
		public String getImportPath(@RequestParam("id")Integer id,HttpServletResponse response) {
			log.debug("PayImportAction getImportPath is start");
			String filePath = "" ;
			try {
				List<PayResultImport> list = payImportService.getListByPorperty("id", id, null);
				for (int i = 0; i < list.size(); i++) {
					PayResultImport apply = list.get(0);
					filePath = apply.getFilePath();
				}
				filePath=filePath.replaceAll("\\\\", "/");
			//	fileDownload(filePath, response);
			} catch (Exception e) {
				log.error("PayImportAction getImportPath is Error:"+e, e);
			}		
			log.debug("PayImportAction getImportPath is End");
			log.debug("传入的文件名:"+filePath);
			return JsonUtil.toSuccessMsg(filePath);
		}
		
		
		
		//根据路径下载相应文件
		@ResponseBody
		@RequestMapping("fileDownload")
		@Privilege(modelCode = "M_MARK_IMPORT", prvgCode = "TREASURY_QUERY")
		public String fileDownload(String filepath,HttpServletResponse response, HttpServletRequest request) throws Exception {
			log.debug("PayImportAction fileDownload is start");
			try {
				//request.setCharacterEncoding("UTF-8");
				//filepath = request.getParameter("filepath");
				log.debug("前台返回的文件名:"+filepath);
				filepath = URLDecoder.decode(filepath, "UTF-8");
				
				String path="";
				String pathString[] = filepath.split("/");
				for (int i = 0; i < pathString.length; i++) {
					path=pathString[pathString.length-1];
				}
				log.debug("---------path value is _--------"+path);
				log.debug("前台返回的文件名转码后的文件名:"+filepath);
				response.setHeader("Content-Disposition", "attachment;  filename=\""
						+ URLEncoder.encode(path,"utf-8")+"\"");			
				// 定义输出类型
				//response.setContentType("application/force-download");
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("UTF-8");
				FileInputStream inputStream = new FileInputStream(filepath);
				OutputStream resOutStream = response.getOutputStream();
				byte[] bytes = new byte[4096];
				
				while(inputStream.read(bytes) != -1) {
					resOutStream.write(bytes, 0, bytes.length);
				}
				inputStream.close();
				resOutStream.close();
			} catch (Exception e) {
				log.error("PayImportAction fileDownload is Error:"+e, e);
			}		
			log.debug("PayApplyAction fileDownload is End");
			return null;
		}
		
		
		

}
