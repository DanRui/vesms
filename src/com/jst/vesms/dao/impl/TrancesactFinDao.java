package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.ITrancesactFinDao;
import com.jst.vesms.model.EliminatedApply;

@Repository("trancesactFinDao")
public class TrancesactFinDao extends  HibernateBaseDAO<EliminatedApply> implements ITrancesactFinDao{

private static final String modelName = EliminatedApply.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}
