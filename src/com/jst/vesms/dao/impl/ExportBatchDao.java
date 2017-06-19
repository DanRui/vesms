package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IExportBatchDao;
import com.jst.vesms.model.BatchExport;

@Repository("exportBatchDao")
public class ExportBatchDao extends HibernateBaseDAO<BatchExport> 
implements IExportBatchDao{

	private static final String modelName = BatchExport.class.getName();
	@Override
	public String getModelName() {
		// TODO Auto-generated method stub
		return modelName;
	}

}

