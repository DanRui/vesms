
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IWorkLoggingDao;
import com.jst.vesms.model.WorkLogging;

@Repository("workLoggingDao")
public class WorkLoggingDao extends HibernateBaseDAO<Object> implements IWorkLoggingDao {

	private static final String modelName = WorkLogging.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}

}

