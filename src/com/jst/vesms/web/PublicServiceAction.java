
package com.jst.vesms.web;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.PublicServiceService;

@RequestMapping("/publicService")
@Controller
public class PublicServiceAction extends BaseAction {

	private static final Log log = LogFactory.getLog(PublicServiceAction.class);
	
	@Resource(name = "publicServiceServiceImpl")
	private PublicServiceService publicServiceService;
	
	/**
	 * 
	 * <p>Description: 进入公共服务内部查询页面</p>
	 * @return ModelAndView
	 *
	 */
	@RequestMapping("verifyView")
	@Privilege(modelCode="M_PUBLIC_SERVICE_QUERY", prvgCode="QUERY")
	public ModelAndView listView() throws Exception {
		String view = "PUBLIC_QUERY_VERIFY.VIEW";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
	
	@Privilege(modelCode="M_PUBLIC_SERVICE_QUERY", prvgCode="QUERY")
	@RequestMapping("verifyResult")
	public ModelAndView verifyResult(@RequestParam("vehiclePlateNum")String vehiclePlateNum, @RequestParam("vehiclePlateType")String vehiclePlateType,
			@RequestParam("vehicleIdentifyNo")String vehicleIdentifyNo) throws Exception {
		log.debug("PublicServiceAction get verifyResult is start");
		String view = "PUBLIC_QUERY_VERIFY.RESULT";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		EliminatedApply apply = new EliminatedApply();
		
		log.debug("调用存储过程判断车辆补贴资格开始");
		Map<String, Object> map = publicServiceService.verifyVehicle(vehiclePlateNum, vehiclePlateType, vehicleIdentifyNo, "2");
		
		// 获得足够多的车辆信息展示到页面
		if(null != map && map.get("7").equals(1)) {
			// 有补贴资格
			log.debug("车辆号牌号码:"+ vehiclePlateNum + ", 号牌种类:" + vehiclePlateType + "----有补贴资格");
			// 补贴金额
			BigDecimal bigDecimal = (BigDecimal) map.get("6");
			Double subsidiesMoney = bigDecimal.doubleValue();
			// 补贴标准说明
			String subsidiesStandard = (String) map.get("8");
			// 车架号
			
			// 获取交警接口数据
			log.debug("调用交警接口存储过程获取机动车信息开始");
			Map<String, Object> jiaoJingVehicleMap = publicServiceService.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
			if (null != jiaoJingVehicleMap && jiaoJingVehicleMap.get("retCode").equals(1)) {
				// 调用存储过程成功
				log.debug("调用交警接口存储过程获取机动车信息成功，开始返回");
				apply = (EliminatedApply) jiaoJingVehicleMap.get("apply");
				apply.setVehiclePlateNum(vehiclePlateNum);
				apply.setVehiclePlateType(vehiclePlateType);
				apply.setSubsidiesMoney(subsidiesMoney);
				apply.setSubsidiesStandard(subsidiesStandard);
				log.debug("调用交警接口存储过程获取机动车信息成功，返回结束");
			}
			
		}
		
		mv.addObject("v", apply);
		log.debug("PublicServiceAction get verifyResult is end");
		return mv;
	}
	
}

