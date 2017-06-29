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
import com.jst.common.utils.string.StringUtil;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
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
	
	@Resource(name="eliminatedApplyDao")
	private IEliminatedApplyDao eliminatedApplyDao;
	
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Page retPage =eliminatedApplyService.getPageBySql(page, sql);
		return eliminatedApplyService.getPageExtra(retPage);
	}


	// 正常业务拨付结果标记
	public Page getPageSql(Page page,String sql) throws Exception {
/*		StringBuffer sb = new StringBuffer();
		sb.append("select t.*,m.to_Finance_No from t_eliminated_apply t inner join ");
		sb.append(" t_batch_main m on t.batch_no=m.batch_no  where 1 = 1");
		sb.append(" and t.vehicle_plate_num like '%vehiclePlateNum%' and t.vehicle_plate_type = 'vehiclePlateType'");
		sb.append(" and t.vehicle_identify_no = 'vehicleIdentifyNo' and t.vehicle_type = 'vehicleType' ");
		sb.append(" and t.vehicle_owner like '%vehicleOwner%'  and t.apply_no = 'applyNo' ");
		sb.append("and t.batch_no = 'batchNo' and t.vehicleOwner = 'vehicleOwner'");
		sb.append("and t.bussiness_status = '1' and t.current_post = 'BFJGBJG' ");
		sb.append("and t.batch_no is not null and repeated_batch_no is null");*/
	
		List<Object[]> list = eliminatedApplyDao.executeSql(sql);
		if (StringUtil.isEmpty(sql)) {
		//	return this.getPageExtra(page);
		} else {
			List list1 = eliminatedApplyDao.getListBySql(sql, null, page);
			long count = eliminatedApplyDao.getListCounter("select count(*) from (" + sql + ") ");
			if (null != list && list.size() > 0) {
				page.setTotalCount(count);
				page.setResult(list);
			}
			eliminatedApplyService.getPageExtra(page);
		}
		return page;
	}
	
	
	
	
	

	@Override
	public Page filterRepeatedBatchPage(Page page) throws Exception {
		// TODO Auto-generated method stub
		List list = page.getResult();
		Page<EliminatedApply> filterPage = new Page<EliminatedApply>();
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				EliminatedApply apply = (EliminatedApply) list.get(i);
				if (apply.getRepeatedBatchNo() != null) {
					filterPage.getResult().add(apply);
					filterPage.setTotalCount(filterPage.getResult().size());
				}
			}
		}
		return filterPage;
	}



	@Override
	public Page filterBatchPage(Page page) throws Exception {
		// TODO Auto-generated method stub
		List list = page.getResult();
		Page<EliminatedApply> filterPage = new Page<EliminatedApply>();
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				EliminatedApply apply = (EliminatedApply) list.get(i);
				if (apply.getBatchNo() != null) {
					filterPage.getResult().add(apply);
					filterPage.setTotalCount(filterPage.getResult().size());
				}
			}
		}
		return filterPage;
	}
}
