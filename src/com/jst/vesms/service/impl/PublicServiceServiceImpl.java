
package com.jst.vesms.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PublicServiceService;

@Service("publicServiceServiceImpl")
public class PublicServiceServiceImpl extends BaseServiceImpl implements PublicServiceService {

	@Resource(name="eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	@Override
	public Map<String, Object> verifyVehicle(String vehiclePlateNum,
			String vehiclePlateType, String vehicleIdentifyNo, String type)
			throws Exception {
		
		return eliminatedApplyService.verifyVehicle(vehiclePlateNum, vehiclePlateType, vehicleIdentifyNo, type);
	}

	@Override
	public Map<String, Object> getJiaoJingVehicle(String vehiclePlateNum,
			String vehiclePlateType) throws Exception {
		
		return eliminatedApplyService.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
	}

	@Override
	public BaseDAO getHibernateBaseDAO() {
		return null;
	}

}

