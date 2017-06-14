package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IPayResultDao;
import com.jst.vesms.model.BatchMain;


@Repository("payResultDao")
public class PayResultDao extends HibernateBaseDAO<BatchMain> 

implements IPayResultDao
{

	private static final String modelName = BatchMain.class.getName();
	
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}
