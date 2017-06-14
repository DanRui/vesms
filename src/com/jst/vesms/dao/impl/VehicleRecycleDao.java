
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IVehicleRecycleDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.VehicleRecycle;

@Repository("vehicleRecycleDao")
public class VehicleRecycleDao extends HibernateBaseDAO<VehicleRecycle>
		implements IVehicleRecycleDao {

	private static final String modelName = VehicleRecycle.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}

