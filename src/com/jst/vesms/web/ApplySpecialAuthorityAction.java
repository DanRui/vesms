
package com.jst.vesms.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.util.PropertyUtil;
import com.jst.util.StringUtil;
import com.jst.vesms.model.ApplySpecialAuthority;
import com.jst.vesms.util.EncryptUtils;

/**
 * <p>Title:受理表授权申请</p>
 * <p>Description: 受理表授权申请、查询、查看、审核等功能Action</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: Jst</p>
 * @author DanRui
 * @version 0.1
 * 2017年7月12日-上午11:02:29
 */
@RequestMapping("/applySpecialAuthority")
@Controller
public class ApplySpecialAuthorityAction extends BaseAction {

	private static final Log log = LogFactory.getLog(ApplySpecialAuthority.class);

	/**
	 * 
	 * <p>Description: 进入受理单授权申请列表查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listView")
	@Privilege(modelCode="M_ELIMINATED_APPLY_NO_LIST", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		String view = "ELIMINATED_APPLY.NO_LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 根据条件查询已申请的受理单授权列表
	 */
	@RequestMapping("list")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_APPLY_NO_LIST", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
		
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		StringBuffer sb = new StringBuffer("select * from t_eliminated_apply t where 1 = 1 ");
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num = '").append(vehiclePlateNum).append("' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
			sb.append("and t.vehicle_identify_no = '").append(vehicleIdentifyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicle_owner like '%").append(vehicleOwner).append("%' ");
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(startTime)) {
			sb.append("and t.apply_time >= to_date('").append(startTime).append("', 'yyyy-MM-dd') ");
		}
		if(StringUtil.isNotEmpty(endTime)) {
			sb.append("and t.apply_time < to_date('").append(endTime).append("', 'yyyy-MM-dd')+1 ");
		}
		sb.append("and t.apply_confirm_time is null order by t.last_update_time desc ");
		try {
			//page = eliminatedApplyService.getPageBySql(page, sb.toString());
			//page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			//page = eliminatedApplyService.filterNoConfirm(page, list);
			//page = eliminatedApplyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("eliminatedApplyAction list is Error:" + e, e);
		}
		log.debug("eliminatedApplyAction list is end");
	    return returnStr;
	}
	
	/**
	 * 
	 * <p>Description: 进入查询符合授权申请的受理单页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("listApplyView")
	@Privilege(modelCode="M_ELIMINATED_APPLY_NO_LIST", prvgCode="QUERY")
	public ModelAndView listApplyView() throws Exception {
		String view = "ELIMINATED_APPLY.NO_LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 根据条件查询符合授权申请的受理单列表
	 */
	@RequestMapping("listApply")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_APPLY_NO_LIST", prvgCode = "QUERY")
	public String listApply(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
		
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		StringBuffer sb = new StringBuffer("select * from t_eliminated_apply t where 1 = 1 ");
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num = '").append(vehiclePlateNum).append("' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
			sb.append("and t.vehicle_identify_no = '").append(vehicleIdentifyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicle_owner like '%").append(vehicleOwner).append("%' ");
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(startTime)) {
			sb.append("and t.apply_time >= to_date('").append(startTime).append("', 'yyyy-MM-dd') ");
		}
		if(StringUtil.isNotEmpty(endTime)) {
			sb.append("and t.apply_time < to_date('").append(endTime).append("', 'yyyy-MM-dd')+1 ");
		}
		sb.append("and t.apply_confirm_time is null order by t.last_update_time desc ");
		try {
			//page = eliminatedApplyService.getPageBySql(page, sb.toString());
			//page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			//page = eliminatedApplyService.filterNoConfirm(page, list);
			//page = eliminatedApplyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("eliminatedApplyAction list is Error:" + e, e);
		}
		log.debug("eliminatedApplyAction list is end");
	    return returnStr;
	}
	
	/**
	 * 
	 * <p>Description: 进入受理单授权审核页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("applyAuthorityView")
	@Privilege(modelCode="M_ELIMINATED_APPLY_NO_LIST", prvgCode="QUERY")
	public ModelAndView applyAuthorityView() throws Exception {
		String view = "ELIMINATED_APPLY.NO_LIST_VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	/**
	 * 受理单授权申请审核
	 */
	@RequestMapping("applyAuthority")
	@ResponseBody
	@Privilege(modelCode = "M_ELIMINATED_APPLY_NO_LIST", prvgCode = "QUERY")
	public String applyAuthority(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String vehiclePlateNum, String vehiclePlateType, String vehicleOwner, String applyNo, String vehicleIdentifyNo, String startTime, String endTime) throws Exception{
		
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		StringBuffer sb = new StringBuffer("select * from t_eliminated_apply t where 1 = 1 ");
		String returnStr = "";
		if(StringUtil.isNotEmpty(vehiclePlateNum)) {
			sb.append("and t.vehicle_plate_num = '").append(vehiclePlateNum).append("' ");
		}
		if(StringUtil.isNotEmpty(vehiclePlateType)) {
			sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleIdentifyNo)) {
			String key = PropertyUtil.getPropertyValue("DES_KEY");
			vehicleIdentifyNo = EncryptUtils.encryptDes(key, vehicleIdentifyNo);
			sb.append("and t.vehicle_identify_no = '").append(vehicleIdentifyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(vehicleOwner)) {
			sb.append("and t.vehicle_owner like '%").append(vehicleOwner).append("%' ");
		}
		if(StringUtil.isNotEmpty(applyNo)) {
			sb.append("and t.apply_no = '").append(applyNo).append("' ");
		}
		if(StringUtil.isNotEmpty(startTime)) {
			sb.append("and t.apply_time >= to_date('").append(startTime).append("', 'yyyy-MM-dd') ");
		}
		if(StringUtil.isNotEmpty(endTime)) {
			sb.append("and t.apply_time < to_date('").append(endTime).append("', 'yyyy-MM-dd')+1 ");
		}
		sb.append("and t.apply_confirm_time is null order by t.last_update_time desc ");
		try {
			//page = eliminatedApplyService.getPageBySql(page, sb.toString());
			//page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			//page = eliminatedApplyService.filterNoConfirm(page, list);
			//page = eliminatedApplyService.getPageExtra(page);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("eliminatedApplyAction list is Error:" + e, e);
		}
		log.debug("eliminatedApplyAction list is end");
	    return returnStr;
	}
	
}

