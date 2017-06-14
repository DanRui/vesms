
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IEliminatedCheckDao;
import com.jst.vesms.model.EliminatedApply;

@Repository("eliminatedCheckDao")
public class EliminatedCheckDao extends HibernateBaseDAO<EliminatedApply>
		implements IEliminatedCheckDao {

	private static final String modelName = EliminatedApply.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}

