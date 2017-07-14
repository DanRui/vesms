package com.jst.vesms.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.IPayImportDao;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;
import com.jst.vesms.service.ImportDetailService;
import com.jst.vesms.service.PayImportService;
import com.jst.vesms.util.excel.imp.ImportExcel;


@Service("payImportServiceImpl")
public class PayImportServiceImpl extends BaseServiceImpl
implements PayImportService{

	@Resource(name="payImportDao")
	private IPayImportDao payImportDao;
	
	@Resource(name="importDetailServiceImpl")
	private ImportDetailService importDetailService;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return payImportDao;
	}
	
	@Override
	public void savePayImport(File file) throws Exception{
		PayResultImport payResultImport = new PayResultImport();
		PayResultImport import1 = ImportExcel.readPayResultImport(file, payResultImport);
		payImportDao.save(import1);
		Integer payImportId = import1.getId();
		// 调用国库详情数据导入进数据库
	//	importDetailService.readSpecify(file);
		/*List<PayResultImportDetail> list = ImportExcel.readSpecify(file);
		for (int i = 0; i < list.size(); i++) {
			 payImportDao.save((PayResultImportDetail)list.get(i));
		}*/
		importDetailService.saveSpecify(file,payImportId);
	}
	
}
