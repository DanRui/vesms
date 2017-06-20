package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IExportBatchDao;
import com.jst.vesms.model.BatchExport;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ExportBatchService;

@Service("exportBatchServiceImpl")
public class ExportBatchServiceImpl extends BaseServiceImpl 
implements ExportBatchService{

	
	@Resource(name="exportBatchDao")
	private IExportBatchDao exportBatchDao ;
	
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return exportBatchDao;
	}


	@Override
	public BatchExport getExportPath(String batchNo) {
		// TODO Auto-generated method stub
		//BatchExport object = (BatchExport)exportBatchDao.get(id);
		//List<EliminatedApply> list = eliminatedApplyService.getListByPorperty("batchNo", batchNo, null);
		return null;
	}

}
