package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;
import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IImportDetailDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.PayResultImportDetail;


@Repository("importDetailDao")
public class ImportDetailDao extends HibernateBaseDAO<PayResultImportDetail>
	implements IImportDetailDao{

	private static final String modelName = PayResultImportDetail.class.getName();
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}
