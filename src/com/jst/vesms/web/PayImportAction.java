package com.jst.vesms.web;

import java.io.File;
import java.io.IOException;
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
import com.jst.util.StringUtil;
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
	

	
	//去国库导入页面
		@RequestMapping("toFileImport")
	//	@Privilege(modelCode = "M_REP_BATCH_LIST", prvgCode = "BATCH_PREVIEW")
		public ModelAndView toFileImport() throws Exception {
			log.debug("PayImportAction toFileImport is start");
			String view = "PAY_IMPORT.FILE_IMPORT";
			ModelAndView mv = new ModelAndView(getReturnPage(view));
			log.debug("PayImportAction toFileImport is end");
			return mv;
		}
		
		
	// importExcel
		@ResponseBody
		@RequestMapping("importExcel")
		 public String importExcel(HttpServletRequest req, HttpServletResponse res)
		            throws ServletException, IOException {
			log.debug("PayImportAction importExcel is start");
			//int count = 0;
			 //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
			String path = "";
			String result="";
			boolean isOk = false;
	        CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver(
	        		req.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"
	        // 判断 request 是否有文件上传,即多部分请
	        if(multipartResolver.isMultipart(req))
	        {
	            //将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)req;
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multiRequest.getFileNames();
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file = multiRequest.getFile(iter.next().toString());
	                if(file!=null)
	                {
	                    path ="E:/"+file.getOriginalFilename();
	                    //上传
	                    file.transferTo(new File(path));
	                }
	            }
	         //   isOk=true;
	         //   result="文件上传成功";
	         }
		        File file1 = new File(path);
		        try {
		        	// 把国库数据导入数据库
					payImportService.savePayImport(file1);
					isOk=true;
					result="文件导入成功";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isOk=false;
					result="文件导入失败";
				}
		        if(isOk){
		        	return JsonUtil.toSuccessMsg(result);      
		        }else{
		        	return JsonUtil.toErrorMsg(result);
		        }
		  }
		
		
		
}
