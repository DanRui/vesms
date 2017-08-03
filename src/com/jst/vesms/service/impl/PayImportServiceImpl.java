package com.jst.vesms.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IPayImportDao;
import com.jst.vesms.model.PayResult;
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
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Override
	public String savePayImport(String fileName,File file) throws Exception{
		PayResultImport payResultImport = new PayResultImport();
		PayResultImport import1 = ImportExcel.readPayResultImport(fileName,file, payResultImport);
		payImportDao.save(import1);
		Integer payImportId = import1.getId();
		// 调用国库详情数据导入进数据库
	//	importDetailService.readSpecify(file);
		/*List<PayResultImportDetail> list = ImportExcel.readSpecify(file);
		for (int i = 0; i < list.size(); i++) {
			 payImportDao.save((PayResultImportDetail)list.get(i));
		}*/
		importDetailService.saveSpecify(file,payImportId);
	/*	String result = this.importPayResult(payImportId+"");
		return result;*/
		return payImportId+"";
	}

	@Override
	public PayResultImport importNoView(Integer id) {
		// TODO Auto-generated method stub
		PayResultImport  payResultImport =(PayResultImport) payImportDao.get(id);
		return payResultImport;
	}
	
	
/*	public List getListBySql(String sql) throws Exception{
		List list = payImportDao.getTableList(sql, null);
		return list;
	}*/
	
	
/*	// 获取数据库中所有路径
	public String[] getUrlList() throws Exception {
		String sql = "select t.file_path from pay_result_import t";
		this.getListBySql(sql);
		List list = this.getListBySql(sql);
		String[] paths= null;
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				paths=(String[]) objs;
				
			//	paths.append(objs[0]);
			}
		}
		return paths;
	}*/
	
	//获取相同文件名后缀数最大的数值 
	@Override
	public List getNewFileName(String fileName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select substr('"+fileName+"',1,instr('"+fileName+"','.')-1) ");
		sb.append(" ||to_char(sysdate,'yyyymmddhh24miss')||'.'");	
		sb.append(" ||(substr('"+fileName+"',instr('"+fileName+"','.')+1)) from dual");
		String sql = sb.toString();
		List list = payImportDao.getTableList(sql, null);
		return list;
	}
	
	
/*	// 判断文件名是否存在
	@Override
	public List getFileNameSql(String fileName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.file_name from t_pay_result_import t where (");
		sb.append("( substr(t.file_name,0,instr(t.file_name,'_')-1) ");
		sb.append(" ) ='"+fileName+"')");
		String sql = sb.toString();
		List list = payImportDao.getTableList(sql, null);
		return list;
	}
	*/
	
	
	@Override
	public String importPayResult(String payImportId) throws Exception {
		String callName = "{call PKG_APPLY.p_apply_import_payresult(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
		inParams.put(3, payImportId);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}


	
}
