package com.jst.vesms.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.SysDict;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
import com.jst.util.PropertyUtil;
import com.jst.vesms.common.CacheRead;
import com.jst.vesms.constant.SysConstant;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IPayResultDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.PayResult;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PayResultService;
import com.jst.vesms.util.EncryptUtils;


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
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
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
			List<PayResult> list2 = new ArrayList<PayResult>();
			List list = payResultDao.getTableList(sql, page);
			long count = payResultDao.getListCounter("select count(*) from (" + sql + ")");
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					PayResult payResult = new PayResult();
					
					BigDecimal idBigDecimal = (BigDecimal)objs[0];
					Integer id = idBigDecimal.intValue();
					payResult.setId(id);
					
					BigDecimal toFinanceNoBigDecimal = (BigDecimal)objs[1];
					Integer toFinanceNo = toFinanceNoBigDecimal.intValue();
					payResult.setToFinanceNo(toFinanceNo);
					
					String batchNo = (String) objs[2];
					payResult.setBatchNo(batchNo);
					
					String applyNo = (String) objs[3];
					payResult.setApplyNo(applyNo);
					
					String vehiclePlateNum = (String) objs[4];
					payResult.setVehiclePlateNum(vehiclePlateNum);
					
					// 号牌种类
					String vehiclePlateType = (String) objs[5];
					if (StringUtil.isNotEmpty(vehiclePlateType)) {
						String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(vehiclePlateType);
						if (null == vehiclePlateTypeName) {
							SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
							if (null != sysDictVehiclePlateType) {
								vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
							}
						}
						payResult.setVehiclePlateTypeName(vehiclePlateTypeName);
					}
					
					// 车辆类型
					String vehicleType = (String) objs[6];;
					if (StringUtil.isNotEmpty(vehicleType)) {
						SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
						if (null != sysDictVehicleType) {
							String vehicleTypeName = sysDictVehicleType.getDictValue();
							//eliminatedApply.setVehicleType(vehicleType);
							payResult.setVehicleTypeName(vehicleTypeName);
						}
					}
					
					String vehicleIdentifyNo = (String) objs[7];
					String key = PropertyUtil.getPropertyValue("DES_KEY");
					vehicleIdentifyNo = EncryptUtils.decryptDes(key, vehicleIdentifyNo);
					payResult.setVehicleIdentifyNo(vehicleIdentifyNo);
					
					String vehicleOwner = (String) objs[8];
					payResult.setVehicleOwner(vehicleOwner);
					
					BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)objs[9];
					Double subsidiesMoney = subsidiesMoneyBigDecimal.doubleValue();
					payResult.setSubsidiesMoney(subsidiesMoney);
					
					java.sql.Timestamp applyDate = (Timestamp) objs[10];
					if (applyDate != null){
						Date applyTime = new Date(applyDate.getTime());
						payResult.setApplyTime(applyTime);
					}
					list2.add(payResult);
				}
				
				page.setTotalCount(count);
				page.setResult(list2);
			}
		
		return page;
	}
	
	
	public Page getRepPageSql(Page page,String sql) throws Exception {
		List<PayResult> list2 = new ArrayList<PayResult>();
		List list = payResultDao.getTableList(sql, page);
		long count = payResultDao.getListCounter("select count(*) from (" + sql + ")");
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objs = (Object[]) list.get(i);
				PayResult payResult = new PayResult();
				
				BigDecimal idBigDecimal = (BigDecimal)objs[0];
				Integer id = idBigDecimal.intValue();
				payResult.setId(id);
				
				BigDecimal toFinanceNoBigDecimal = (BigDecimal)objs[1];
				Integer toFinanceNo = toFinanceNoBigDecimal.intValue();
				payResult.setToFinanceNo(toFinanceNo);
				
				String repeatedBatchNo = (String) objs[2];
				payResult.setRepeatedBatchNo(repeatedBatchNo);
				
				String applyNo = (String) objs[3];
				payResult.setApplyNo(applyNo);
				
				String vehiclePlateNum = (String) objs[4];
				payResult.setVehiclePlateNum(vehiclePlateNum);
				
				// 号牌种类
				String vehiclePlateType = (String) objs[5];
				if (StringUtil.isNotEmpty(vehiclePlateType)) {
					String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(vehiclePlateType);
					if (null == vehiclePlateTypeName) {
						SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
						if (null != sysDictVehiclePlateType) {
							vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
						}
					}
					payResult.setVehiclePlateTypeName(vehiclePlateTypeName);
				}
				
				// 车辆类型
				String vehicleType = (String) objs[6];;
				if (StringUtil.isNotEmpty(vehicleType)) {
					SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
					if (null != sysDictVehicleType) {
						String vehicleTypeName = sysDictVehicleType.getDictValue();
						//eliminatedApply.setVehicleType(vehicleType);
						payResult.setVehicleTypeName(vehicleTypeName);
					}
				}
				
				String vehicleIdentifyNo = (String) objs[7];
				String key = PropertyUtil.getPropertyValue("DES_KEY");
				vehicleIdentifyNo = EncryptUtils.decryptDes(key, vehicleIdentifyNo);
				payResult.setVehicleIdentifyNo(vehicleIdentifyNo);
				
				String vehicleOwner = (String) objs[8];
				payResult.setVehicleOwner(vehicleOwner);
				
				BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)objs[9];
				Double subsidiesMoney = subsidiesMoneyBigDecimal.doubleValue();
				payResult.setSubsidiesMoney(subsidiesMoney);
				
				java.sql.Timestamp applyDate = (Timestamp) objs[10];
				if (applyDate != null){
					Date applyTime = new Date(applyDate.getTime());
					payResult.setApplyTime(applyTime);
				}
				list2.add(payResult);
			}
			
			page.setTotalCount(count);
			page.setResult(list2);
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
