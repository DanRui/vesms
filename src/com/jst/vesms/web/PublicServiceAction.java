
package com.jst.vesms.web;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

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
		String view = "PUBLIC_QUERY_VERIFY.RESULT";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		EliminatedApply apply = new EliminatedApply();
		
		Map<String, Object> map = publicServiceService.verifyVehicle(vehiclePlateNum, vehiclePlateType, vehicleIdentifyNo, "2");
		
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
			Map<String, Object> jiaoJingVehicleMap = publicServiceService.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
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
	
}

