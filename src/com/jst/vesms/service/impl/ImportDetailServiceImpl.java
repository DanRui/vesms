package com.jst.vesms.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.IImportDetailDao;
import com.jst.vesms.dao.impl.ImportDetailDao;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;
import com.jst.vesms.service.ImportDetailService;
import com.jst.vesms.util.excel.imp.ImportExcel;

@Service("importDetailServiceImpl")
public class ImportDetailServiceImpl extends BaseServiceImpl
implements ImportDetailService{

	@Resource(name="importDetailDao")
	private IImportDetailDao importDetailDao ;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return importDetailDao;
	}

	@Override
	public void saveSpecify(File file,Integer payImportId) throws Exception{
		PayResultImportDetail payResultImportDetail = new PayResultImportDetail();
	//	ImportExcel.readSpecify(file, payResultImportDetail);
		List<PayResultImportDetail> list = ImportExcel.readSpecify(file,payImportId);
		for (int i = 0; i < list.size(); i++) {
			importDetailDao.save((PayResultImportDetail)list.get(i));
		}
	}
	    
	
}
