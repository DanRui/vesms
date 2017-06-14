package com.jst.vesms.service.impl;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.util.StringUtil;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IPayApplyDao;
import com.jst.vesms.dao.impl.CallDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PayApplyService;


@Service("payApplyServiceImpl")
public class PayApplyServiceImpl extends BaseServiceImpl 
	implements PayApplyService {

	@Resource(name = "eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	@Resource(name="payApplyDao")
	private IPayApplyDao payApplyDao;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		return payApplyDao;
	}
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	/*
	 * 
	 *批次生成
	 *
	 */

	@Override
	public String createBatch(String ids) throws Exception {
		String callName = "{call PKG_BATCH.p_normal_batch_create(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}
	
	
	/*
	 * 
	 *获取业务查询信息
	 *
	 */
	@Override
	public Page getApplyPage(Page page, List<PropertyFilter> list) {
		try {
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName");
			page = eliminatedApplyService.getPageExtra(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 *批次导出
	 * @throws Exception 
	 */

	@Override
	public String batchExport(Integer id) throws Exception {
		// TODO Auto-generated method stub
		//	File file = new File("D:\\123.txt");
			String file = "D:\\123.txt" ;
			String sid=id+"";
			String callName = "{call PKG_BATCH.p_normal_batch_export(?,?,?,?,?)}";
			Map<Integer, Object> inParams = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
			inParams.put(1, "用户code");
			inParams.put(2, "用户名称");
			inParams.put(3, sid);
			inParams.put(4, file);
			outParams.put(5, OracleTypes.VARCHAR);
			List<Map<String, Object>> result1 = callDao.call(callName, inParams, outParams, "procedure");		
			Map<String, Object> map = result1.get(0);
		return map.get("5").toString();
	}


	@Override
	public BatchMain batchNoView(Integer id) {
		// TODO Auto-generated method stub
		BatchMain  batchMain =(BatchMain) payApplyDao.get(id);
		return batchMain;
	}
	
	//批次业务删除
	@Override
	public String deleteApply(String batchId, String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_normal_batch_delids(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "用户code");
		inParams.put(2, "用户名称");
		inParams.put(3, batchId);
		inParams.put(4, ids);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");		
		Map<String, Object> map = result.get(0);
		return map.get("5").toString();
	}
	
	
	@Override
	public String addApply(String batchId, String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_normal_batch_addids(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "用户code");
		inParams.put(2, "用户名称");
		inParams.put(3, batchId);
		inParams.put(4, ids);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");		
		Map<String, Object> map = result.get(0);		
		return map.get("5").toString();
	}

	@Override
	public EliminatedApply getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		//List<EliminatedApply> list= eliminatedApplyService.getListByPorperty("batchNo", batchNo, null);
		EliminatedApply object = eliminatedApplyService.getById(id);
		return object;
	}


	@Override
	public String confirmBatch(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_normal_batch_to_finance(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}


	@Override
	public String batchCancel(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_normal_batch_cancel(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "用户code");
		inParams.put(2, "用户名称");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");		
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}

	

	@Override
	public String createRepBatch(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_create(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}

	

	public String batchRepExport(Integer id) throws Exception {
		// TODO Auto-generated method stub
			String sid=id+"";
			String file = "D:\\123.txt" ;
			//File file = new File("D:\\123.txt");
			String callName = "{call PKG_BATCH.p_repeat_batch_export(?,?,?,?,?)}";
			Map<Integer, Object> inParams = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
			inParams.put(1, "用户code");
			inParams.put(2, "用户名称");
			inParams.put(3, sid);
			inParams.put(4, file);
			outParams.put(5, OracleTypes.VARCHAR);
			List<Map<String, Object>> result1 = callDao.call(callName, inParams, outParams, "procedure");		
			Map<String, Object> map = result1.get(0);
		return map.get("5").toString();
	}


	@Override
	public String deleteRepApply(String batchNo, String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_delids(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "用户code");
		inParams.put(2, "用户名称");
		inParams.put(3, batchNo);
		inParams.put(4, ids);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");		
		Map<String, Object> map = result.get(0);
		return map.get("5").toString();
	}


	@Override
	public String repBatchCancel(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_cancel(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}


	@Override
	public String addRepApply(String batchId, String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_addids(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "用户code");
		inParams.put(2, "用户名称");
		inParams.put(3, batchId);
		inParams.put(4, ids);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");		
		Map<String, Object> map = result.get(0);
		return map.get("5").toString();
	}


	@Override
	public String confirmRepBatch(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_to_finance(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}



	/**
	 * 
	 * TODO 根据sql语句查询受理单.
	 * @see com.jst.vesms.service.EliminatedApplyService#getPageBySql(com.jst.common.utils.page.Page, java.lang.String)
	 */
	@Override
	public Page getPageBySql(Page page,
			String sql) throws Exception {
		page=eliminatedApplyService.getPageBySql(page, sql);
		page = eliminatedApplyService.getPageExtra(page);
		return page;
	}


	@Override
	public List<EliminatedApply> getBatchApplyList(String batchNo) throws Exception {
		// TODO Auto-generated method stub
	    // ArrayList<EliminatedApply> list =  (ArrayList<EliminatedApply>) eliminatedApplyService.getAllList();
		List<EliminatedApply> list = eliminatedApplyService.getListByPorperty("batchNo", batchNo, null);
		return list;
	}


	@Override
	public BatchMain getObj(Integer id) {
		// TODO Auto-generated method stub
		BatchMain batchMain = (BatchMain) payApplyDao.get(id);
		return batchMain;
	}


	@Override
	public List<EliminatedApply> getRepBatchApplyList(String batchNo) throws Exception {
		// TODO Auto-generated method stub
		List<EliminatedApply> list = eliminatedApplyService.getListByPorperty("repeatedBatchNo", batchNo, null);
		return list;
	}





}
