package com.jst.vesms.web;

import java.util.ArrayList;
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
import com.jst.util.StringUtil;
import com.jst.vesms.model.Archives;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ArchivesService;
import com.jst.vesms.service.ConcludeService;
import com.jst.vesms.service.EliminatedApplyService;



@RequestMapping("/archives")
@Controller
public class ArchivesAction extends BaseAction {
private static final Log log = LogFactory.getLog(ConcludeAction.class);
	
	@Resource(name="archivesServiceImpl")
	private ArchivesService archivesService;
	
	
	
	
	@RequestMapping("archiveView")
	@Privilege(modelCode = "M_ARCHIVE_APPLY", prvgCode = "QUERY")
	public ModelAndView archiveView() throws Exception {
		String view = "ARCHIVES.LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 进行查询数据
	 */
	@RequestMapping("list")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String archiveBoxNo, String archivedStartDate,String archivedEndDate) throws Exception{
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(archiveBoxNo)) {
			list.add(new PropertyFilter("EQS_vehiclePlateNum",archiveBoxNo));
		}
		if(StringUtil.isNotEmpty(archivedStartDate)) {
			list.add(new PropertyFilter("GTD_archivedDate",archivedStartDate));
		}
		if(StringUtil.isNotEmpty(archivedEndDate)) {
			list.add(new PropertyFilter("LTD_archivedDate",archivedEndDate));
		}
		try {
		//	page = eliminatedApplyService.getPageBySql(page, "select * from t_eliminated_apply where batch_no is null");
			page = archivesService.getPage(page, list);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ArchivesAction list is Error:" + e, e);
		}
		log.debug("ArchivesAction list is end");
	    return returnStr;
	}	
	
	
	
	
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView View(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ARCHIVES_APPLY.VIEW";
		Archives object = archivesService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		mv.addObject("v", object);
		return mv;
	}
	
	
	
	
	//查询该档案盒内业务数据	
	@RequestMapping("archiveApplyList")
	@ResponseBody
	//@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String archiveApplyList(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType,String applyNo,String batchNo,String archiveBoxNo) throws Exception{
			String returnStr="";
			List<PropertyFilter> list = new ArrayList<PropertyFilter>();
			Page page = new Page();
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setOrder(order);
			page.setOrderBy(orderBy);
			if(StringUtil.isNotEmpty(vehiclePlateNum)) {
				list.add(new PropertyFilter("LIKES_vehiclePlateNum",vehiclePlateNum));
			}
			if(StringUtil.isNotEmpty(vehiclePlateType)) {
				list.add(new PropertyFilter("EQS_vehiclePlateType",vehiclePlateType));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(batchNo)) {
				list.add(new PropertyFilter("EQS_batchNo",batchNo));
			}
			if(StringUtil.isNotEmpty(archiveBoxNo)) {  
				list.add(new PropertyFilter("EQS_archiveBoxNo",archiveBoxNo));
			}
			try {
				page = archivesService.getApplyPage(page, list);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("PayApplyAction applylist is Error:" + e, e);
			}
				log.debug("PayApplyAction applylist is end");
	    return returnStr;
	}
	
}
