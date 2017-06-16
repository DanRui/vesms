package com.jst.vesms.service.impl;

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
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IPayResultDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PayResultService;


@Service("payResultServiceImpl")
public class PayResultServiceImpl extends BaseServiceImpl 
implements PayResultService{

	@Resource(name = "eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return payResultDao;
	}
		
	@Resource(name="payResultDao")
	private IPayResultDao payResultDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	
	/**
	 * 
	 *拨付结果标记
	 */

	@Override
	public String payResultMark(String ids) throws Exception {
		String callName = "{call PKG_APPLY.pro_create_batch(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, ids);
		inParams.put(2, "admin");
		inParams.put(3, "admin");
		outParams.put(4, OracleTypes.INTEGER);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		
		Map<String, Object> map = result.get(0);
		if (map.get("4").toString().equals("1")) {
			return map.get("5").toString();
		}
		return null;
	}



	@Override
	public EliminatedApply getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		EliminatedApply object = eliminatedApplyService.getById(id);
		return object;
	}
	
	
	@Override
	public BatchMain batchNoView(Integer id) {
		// TODO Auto-generated method stub
		BatchMain  batchMain =(BatchMain) payResultDao.get(id);
		return batchMain;
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



	@Override
	public String markBatchApply(String ids, String payResStatus, String faultType, String faultDesc) throws Exception {
		// TODO Auto-generated method stub
			String callName = "{call PKG_APPLY.p_apply_update_payresult(?,?,?,?,?,?,?)}";
			Map<Integer, Object> inParams = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
			inParams.put(1, "admin");
			inParams.put(2, "admin");
			inParams.put(3, ids);
			inParams.put(4, payResStatus);
			inParams.put(5, faultType);
			inParams.put(6, faultDesc);
			outParams.put(7, OracleTypes.VARCHAR);
			List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
			Map<String, Object> map = result.get(0);
			return map.get("7").toString();
	}



	public Page getPageBySql(Page page,
			String sql) throws Exception {
		page=eliminatedApplyService.getPageBySql(page, sql);
		page = eliminatedApplyService.getPageExtra(page);
		return page;
	}



	@Override
	public Page filterRepeatedBatchPage(Page page) throws Exception {
		List list = page.getResult();
		
		List retList = new ArrayList();
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				EliminatedApply apply = (EliminatedApply) list.get(i);
				if (apply.getRepeatedBatchNo() != null) {
					retList.add(apply);
				}
			}
			page.setResult(retList);
			page.setTotalCount(retList.size());
		}
		
		return page;
	}
}
