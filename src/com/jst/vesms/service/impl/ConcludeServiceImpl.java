package com.jst.vesms.service.impl;

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
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ConcludeService;
import com.jst.vesms.service.EliminatedApplyService;

@Service("concludeServiceImpl")
public class ConcludeServiceImpl extends BaseServiceImpl 
implements ConcludeService{
	

	@Resource(name = "eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	
	@Resource(name="eliminatedApplyDao")
	private IEliminatedApplyDao eliminatedApplyDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return eliminatedApplyDao;
	}

/*	@Override
	public Page getPageBySql(Page page, String sql) throws Exception {
		// TODO Auto-generated method stub
		page=eliminatedApplyService.getPageBySql(page, sql);
		page = eliminatedApplyService.getPageExtra(page);
		return page;
	}*/

	@Override
	public EliminatedApply getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		EliminatedApply object = eliminatedApplyService.getById(id);
		return object;
	}

	@Override
	public String concludeApply(String ids,String type) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_APPLY.p_apply_end(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		inParams.put(1, "admin");
		inParams.put(2, "admin");
		inParams.put(3, ids);
		inParams.put(4, type);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("5").toString();
	}

	@Override
	public Page getApplyPage(Page page, List<PropertyFilter> list) {
		try {
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
			page = eliminatedApplyService.getPageExtra(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
}
