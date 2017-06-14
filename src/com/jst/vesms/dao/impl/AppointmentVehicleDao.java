
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IAppointmentVehicleDao;
import com.jst.vesms.model.AppointmentVehicle;

@Repository("appointmentVehicleDao")
public class AppointmentVehicleDao extends HibernateBaseDAO<AppointmentVehicle>
		implements IAppointmentVehicleDao {

	private static final String modelName = AppointmentVehicle.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}

