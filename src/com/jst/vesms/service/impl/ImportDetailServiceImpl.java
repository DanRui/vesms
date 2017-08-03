package com.jst.vesms.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
import com.jst.vesms.dao.IImportDetailDao;
import com.jst.vesms.dao.impl.ImportDetailDao;
import com.jst.vesms.model.EliminatedApply;
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

	// 把国库详情数据导入数据库
	@Override
	public void saveSpecify(File file,Integer payImportId) throws Exception{
		PayResultImportDetail payResultImportDetail = new PayResultImportDetail();
	//	ImportExcel.readSpecify(file, payResultImportDetail);
		List<PayResultImportDetail> list = ImportExcel.readSpecify(file,payImportId);
		for (int i = 0; i < list.size(); i++) {
			importDetailDao.save((PayResultImportDetail)list.get(i));
		}
	}
	
	
	@Override
	public PayResultImportDetail detailNoView(Integer id) {
		// TODO Auto-generated method stub
		PayResultImportDetail  payResultImportDetail =(PayResultImportDetail) importDetailDao.get(id);
		return payResultImportDetail;
	}
	
/*	// 判断传进来的业务id 和国库申请单号 在数据库是否存在   
	public boolean isExist(){
		boolean flag = false;
		//先获取数据库所有的业务id的int数组  和 国库申请单号的String数组
		Integer count = (int) importDetailDao.getAllListCounter();
		for (int i = 0; i < count; i++) {
			PayResultImportDetail importDetail = new PayResultImportDetail();
		}
		return flag;
	}*/
	
	

	@Override
	public Page<PayResultImportDetail> getPageBySql(Page<PayResultImportDetail> page,
			String sql) throws Exception {
		
		List list = importDetailDao.getListBySql(sql, null, page);
		long count = importDetailDao.getListCounter("select count(*) from (" + sql + ") ");
		if (null != list && list.size() > 0) {
			page.setTotalCount(count);
			page.setResult(list);
		}
		return page;
	}
	
	
	
	
}
