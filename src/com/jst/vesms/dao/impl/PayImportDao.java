package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IPayImportDao;
import com.jst.vesms.model.PayResultImport;

@Repository("payImportDao")
public class PayImportDao extends HibernateBaseDAO<PayResultImport>
implements IPayImportDao{

	private static final String modelName = PayResultImport.class.getName();
	
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}
