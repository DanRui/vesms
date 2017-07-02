
package com.jst.vesms.service;

import java.util.Map;

import com.jst.common.service.BaseService;

public interface PublicServiceService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 通过号牌号牌、号牌种类、车架号、业务类别获取车辆补贴资格信息，通用查询接口</p>
	 * @param vehiclePlateNum 号牌号码  String
	 * @param vehiclePlateType 号牌种类  String
	 * @param vehicleIdentifyNo 车架号  String
	 * @param type 业务类别  String
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> verifyVehicle(String vehiclePlateNum, String vehiclePlateType, String vehicleIdentifyNo, String type) throws Exception;
	
	/**
	 * 
	 * <p>Description: 通过号牌号码、号牌种类获取交警接口数据，通用接口</p>
	 * @param vehiclePlateNum  号牌号码   String
	 * @param vehiclePlateType 号牌种类   String
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> getJiaoJingVehicle(String vehiclePlateNum, String vehiclePlateType) throws Exception;


}

