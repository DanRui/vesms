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
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.utils.page.Page;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedCheckService;

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
	  * <p>Description: 描述</p>
	  * @param name description type
	  * @return List
	  *
	 */
	@RequestMapping("list")
	@ResponseBody
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
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(applyStartDate)) {
				list.add(new PropertyFilter("GTD_applyConfirmTime",applyStartDate));
			}
			if(StringUtil.isNotEmpty(applyEndDate)) {
				list.add(new PropertyFilter("LTD_applyConfirmTime",applyEndDate));
			}
			list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
			list.add(new PropertyFilter("EQS_isFault", "0"));
			if (StringUtil.isNotEmpty(currentPost)) {
				list.add(new PropertyFilter("EQS_currentPost", currentPost));// 当前岗位是在当前登陆用户的岗位的数据
			}
			try {
				page = eliminatedCheckService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
				page = eliminatedCheckService.getPageExtra(page);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("EliminatedCheckAction list is Error:" + e, e);
			}
			log.debug("EliminatedCheckAction list is end");
			return returnStr;
		}
	
	@RequestMapping("backList")
	@ResponseBody
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
				list.add(new PropertyFilter("EQS_vehicleIdentifyNo",vehicleIdentifyNo));
			}
			if(StringUtil.isNotEmpty(vehicleOwner)) {
				list.add(new PropertyFilter("LIKES_vehicleOwner",vehicleOwner));
			}
			if(StringUtil.isNotEmpty(applyNo)) {
				list.add(new PropertyFilter("EQS_applyNo",applyNo));
			}
			if(StringUtil.isNotEmpty(applyStartDate)) {
				list.add(new PropertyFilter("GTD_applyConfirmTime",applyStartDate));
			}
			if(StringUtil.isNotEmpty(applyEndDate)) {
				list.add(new PropertyFilter("LTD_applyConfirmTime",applyEndDate));
			}
			
			if (StringUtil.isNotEmpty(currentPost)) {
				list.add(new PropertyFilter("EQS_currentPost", currentPost));// 当前岗位是在当前登陆用户的岗位的数据
			}
			
			list.add(new PropertyFilter("EQS_bussinessStatus", "1"));
			list.add(new PropertyFilter("EQS_isFault", "1"));
			try {
				page = eliminatedCheckService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
				page = eliminatedCheckService.getPageExtra(page);
				returnStr = writerPage(page);
			} catch (Exception e) {
				log.error("EliminatedCheckAction backList is Error:" + e, e);
			}
			log.debug("EliminatedCheckAction backList is end");
			return returnStr;
		}
	
	@RequestMapping("checkView")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView checkView(@RequestParam("ids")String ids, @RequestParam("currentPost")String currentPost) throws Exception {
		String view = "ELIMINATED_CHECK.CHECK_VIEW";
		//EliminatedApply object = eliminatedCheckService.getById(ids);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		mv.addObject("currentPost", currentPost);
			
		return mv;
	}
	
	@RequestMapping("view")
	//@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "VIEW")
	public ModelAndView view(@RequestParam("id")Integer id, @RequestParam(value = "type")String type) throws Exception {
		String view = "ELIMINATED_CHECK.NORMAL_CHECK";
		if(StringUtil.isNotEmpty(type)&&"back".equals(type)) {
			view = "ELIMINATED_CHECK.BACK_CHECK";
		}
		EliminatedApply object = eliminatedCheckService.getById(id);
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		
		// 获得图片附件表数据
		
		// 获取业务流水记录表数据
		List<ActionLog> actionLogList = eliminatedCheckService.getActionLogList(id);
		mv.getModel().put("busStatus", type);
		mv.addObject("actionLogs", actionLogList);
		mv.addObject("v", object);
			
		return mv;
	}
	
	@RequestMapping("check")
	@ResponseBody
	public String check(@RequestParam("ids")String ids, @RequestParam("checkType")String checkType,
						@RequestParam("faultType")String faultType, @RequestParam("checkOpinion")String checkOpinion, @RequestParam("currentPost")String currentPost) throws Exception {
		log.debug("EliminatedCheckAction check is start");
		boolean checkOk = false;
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> map = eliminatedCheckService.check(ids, checkType, faultType, checkOpinion, currentPost);
			if (map.get("isSuccess").equals(true)) {
				checkOk = true;
				json.put("msg", map.get("msg"));
			} else {
				// 存储过程调用或者执行异常，如何处理
			}
		} catch (Exception e) {
			log.error("EliminatedCheckAction check is Error:"+e, e);
		}
		
		log.debug("EliminatedCheckAction check is End");
		if(checkOk) {
			return JsonUtil.toSuccessMsg(json.toString());
		} else
		return JsonUtil.toErrorMsg("受理单审批提交失败");
	}
	
}
