
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IActionLogDao;
import com.jst.vesms.model.ActionLog;

@Repository("actionLogDao")
public class ActionLogDao extends HibernateBaseDAO<ActionLog> implements
		IActionLogDao {
	
	private static final String modelName = ActionLog.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

	
}

