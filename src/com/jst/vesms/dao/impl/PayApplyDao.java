package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IPayApplyDao;
import com.jst.vesms.model.BatchMain;

@Repository("payApplyDao")
public class PayApplyDao extends HibernateBaseDAO<BatchMain> 
	implements IPayApplyDao{

	private static final String modelName = BatchMain.class.getName();
	
	@Override
	public String getModelName() {
		return modelName;
	}



}
