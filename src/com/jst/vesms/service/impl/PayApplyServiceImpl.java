package com.jst.vesms.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.util.PropertyUtil;
import com.jst.vesms.constant.SysConstant;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IPayApplyDao;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.PayApplyService;
import com.jst.vesms.util.EncryptUtils;


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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
			page = eliminatedApplyService.getPage(page, list, true, "vehiclePlateTypeName", "vehicleTypeName", "useOfPropertyName", "iolTypeName", "vehicleStatusName", "isFinancialSupportName");
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
	public String batchExport(Integer id,String exportPath,String password) throws Exception {
		// TODO Auto-generated method stub
			String sid=id+"";
			String callName = "{call PKG_BATCH.p_normal_batch_export(?,?,?,?,?,?)}";
			Map<Integer, Object> inParams = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
			Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
			String userCode = (String) loginInfo.get("USER_CODE");
			String userName = (String) loginInfo.get("USER_NAME");
			inParams.put(1, userCode);
			inParams.put(2, userName);
			inParams.put(3, sid);
			inParams.put(4, exportPath);
			inParams.put(5, password);
			outParams.put(6, OracleTypes.VARCHAR);
			List<Map<String, Object>> result1 = callDao.call(callName, inParams, outParams, "procedure");		
			Map<String, Object> map = result1.get(0);
		return map.get("6").toString();
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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		String callName = "{call PKG_BATCH.p_normal_batch_to_finance(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		String str = map.get("4").toString();
		return str+"|"+map.get("5").toString();
	}


	@Override
	public String batchCancel(String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_normal_batch_cancel(?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		return map.get("4").toString();
	}

	
	//重报批次导出
	public String batchRepExport(Integer id,String exportPath,String password) throws Exception {
		// TODO Auto-generated method stub
			String sid=id+"";
			//File file = new File("D:\\123.txt");
			String callName = "{call PKG_BATCH.p_repeat_batch_export(?,?,?,?,?,?)}";
			Map<Integer, Object> inParams = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
			Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
			String userCode = (String) loginInfo.get("USER_CODE");
			String userName = (String) loginInfo.get("USER_NAME");
			inParams.put(1, userCode);
			inParams.put(2, userName);
			inParams.put(3, sid);
			inParams.put(4, exportPath);
			inParams.put(5, password);
			outParams.put(6, OracleTypes.VARCHAR);
			List<Map<String, Object>> result1 = callDao.call(callName, inParams, outParams, "procedure");		
			Map<String, Object> map = result1.get(0);
		return map.get("6").toString();
	}


	@Override
	public String deleteRepApply(String batchNo, String ids) throws Exception {
		// TODO Auto-generated method stub
		String callName = "{call PKG_BATCH.p_repeat_batch_delids(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
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
		String callName = "{call PKG_BATCH.p_repeat_batch_to_finance(?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		inParams.put(1, userCode);
		inParams.put(2, userName);
		inParams.put(3, ids);
		outParams.put(4, OracleTypes.VARCHAR);
		outParams.put(5, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		Map<String, Object> map = result.get(0);
		String str = map.get("4").toString();
		return str+"|"+map.get("5").toString();
	}



	/**
	 * 
	 * TODO 根据sql语句查询受理单.
	 * @see com.jst.vesms.service.EliminatedApplyService#getPageBySql(com.jst.common.utils.page.Page, java.lang.String)
	 */
	@Override
	public Page getPageBySql(Page page,
			String sql) throws Exception {
		Page retPage = eliminatedApplyService.getPageBySql(page, sql);
		return eliminatedApplyService.getPageExtra(retPage);
	}

	
	public List getListBySql(String sql) throws Exception{
		List list = payApplyDao.getTableList(sql, null);
		return list;
	}
	
	// 通过批次返回对应的业务数据
	@Override
	public List<EliminatedApply> getBatchApplyList(String batchNo) throws Exception {
		// TODO Auto-generated method stub
	    // ArrayList<EliminatedApply> list =  (ArrayList<EliminatedApply>) eliminatedApplyService.getAllList();
		// List<EliminatedApply> list = null ;
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

	@Override
	public List getBySql(String batchNo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select rownum xuhao,");
		sb.append("thisbatchno,thistofinanceno,vehicle_plate_num,vehicle_plate_type,subsidies_money,");
		sb.append("lastbankname,lastaccountname,lastaccountno,");
		sb.append("thisbankname,thisaccountname,thisaccountno,thisfaultType,");
		sb.append("'第'||tofinancecishu||'次报送，上一次报送批号：'||lasttofinanceno note from");
		sb.append(" (select tbm.batch_no thisbatchno,tbm.to_finance_no thistofinanceno,");
		sb.append("tea.vehicle_plate_num, tea.vehicle_plate_type,");
		sb.append("tea.subsidies_money,tbd.remark,");
		sb.append("abs(tea.to_finance_status) tofinancecishu,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'PCH:','|') lastbatchno,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'BSXH:','|') lasttofinanceno,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHYH:','|') lastbankname,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHHM:','|') lastaccountname,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'YHZH:','|') lastaccountno,");
		sb.append("tea.bank_name thisbankname,");
		sb.append("tea.bank_account_name thisaccountname,");
		sb.append("tea.bank_account_no thisaccountno,tea.fault_type thisfaultType ");
		sb.append("from t_eliminated_apply tea,t_batch_detail tbd,t_batch_main tbm ");
		sb.append("where tbm.batch_no= '"+batchNo+"' ");
		sb.append("and tbm.batch_no=tbd.batch_no ");
		sb.append("and tea.apply_no=tbd.apply_no ");
		sb.append("order by tea.apply_confirm_time)");
		String sql = sb.toString();
		/* String sql = "select rownum xuhao,"
				+ "thisbatchno,thistofinanceno,vehicle_plate_num,vehicle_plate_type,subsidies_money,"
				+ "lastbankname,lastaccountname,lastaccountno,"
				+ "thisbankname,thisaccountname,thisaccountno,thisfaultType,"
				+ "'第'||tofinancecishu||'次报送，上一次报送批号：'||lasttofinanceno note "
				+ "from"
				+ "(select tbm.batch_no thisbatchno,"
				+ " tbm.to_finance_no thistofinanceno,"
				+ "tea.vehicle_plate_num,"
				+ "tea.vehicle_plate_type,"
				+ "tea.subsidies_money,"
				+ "tbd.remark,"
				+ "abs(tea.to_finance_status) tofinancecishu,"
				+ "f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'PCH:','|') lastbatchno,"
				+ "f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'BSXH:','|') lasttofinanceno,"
				+ "f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHYH:','|') lastbankname,"
				+ "f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHHM:','|') lastaccountname,"
				+ "f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'YHZH:','|') lastaccountno,"
				+ "tea.bank_name thisbankname,"
				+ "tea.bank_account_name thisaccountname,"
				+ "tea.bank_account_no thisaccountno,tea.fault_type thisfaultType "
				+ "from t_eliminated_apply tea,t_batch_detail tbd,t_batch_main tbm "
				+ "where tbm.batch_no= '"+batchNo+"' "
				+ "and tbm.batch_no=tbd.batch_no "
				+ "and tea.apply_no=tbd.apply_no "
				+ "order by tea.apply_confirm_time)";*/
			List list = payApplyDao.getTableList(sql, null);
			return list;
	}


	// 重报批次待生成
	@Override
	public Page filterRepeatedBatchPage(Page page) {
		// TODO Auto-generated method stub
		Page<EliminatedApply> filterPage = new Page<EliminatedApply>();
		List list = page.getResult();
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				EliminatedApply apply = (EliminatedApply) list.get(i);
				String toFinanceString = apply.getToFinanceStatus().toString();
				int toFinanceNum = Integer.parseInt(toFinanceString);
				if (apply.getRepeatedBatchNo() == null&& toFinanceNum <=-2&& apply.getBatchNo()!=null) {
					filterPage.getResult().add(apply);
					filterPage.setTotalCount(filterPage.getResult().size());
				}
			}
		}
		return filterPage;
	}


	// 批次为空的数据
	@Override
	public Page filterBatchPage(Page page) {
		// TODO Auto-generated method stub
		Page<EliminatedApply> filterPage = new Page<EliminatedApply>();
		List list = page.getResult();
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				EliminatedApply apply = (EliminatedApply)list.get(i);
				if (apply.getBatchNo() == null) {
					filterPage.getResult().add(apply);
					filterPage.setTotalCount(filterPage.getResult().size());
				}
			}
		}
		return filterPage;
	}
	
	
	// 正常批次excel文件数据获取(正常预览 重报预览  正常报财务excel报表 正常报财务预览   重报报财务预览  同时调用这个方法)
	public List<String[]> batchExcelList(String batchNo,String type,String batchType,Integer id) throws Exception {
		// TODO Auto-generated method stub
		int count =0;
		List<EliminatedApply> list = new ArrayList<EliminatedApply>();
		
		List<String[]> dataList = new ArrayList<String[]>();
		if(batchType.equals("1")){
			list = getBatchApplyList(batchNo);
			if(type.equals("1")){
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					String vehicleIdentifyNo = "";
					String bankAccountNo = "";
					count++;
					// 车架号解密
					String des_key = PropertyUtil.getPropertyValue("DES_KEY");
					vehicleIdentifyNo=EncryptUtils.decryptDes(des_key, apply.getVehicleIdentifyNo());			
					bankAccountNo=EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
					// 获得号牌种类名称(从字典表获取)
					String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(apply.getVehiclePlateType());
					apply.setVehiclePlateTypeName(vehiclePlateTypeName);
					String isPersonal = apply.getIsPersonal();
					if (isPersonal.equals("Y")) {
						isPersonal="个人";
					}else if(isPersonal.equals("N")){
						isPersonal="车主自证非财政供养";
					}
					String[] strings = new String[]{count+"", apply.getVehicleOwner(),apply.getVehiclePlateNum(),apply.getVehiclePlateTypeName(),apply.getSubsidiesStandard(),
							vehicleIdentifyNo,apply.getAdvancedScrapDays().toString(),apply.getRecycleDate().toString(),"",isPersonal,
							apply.getBankName(),bankAccountNo,apply.getSubsidiesMoney().toString()};
					dataList.add(strings);
				}
			}else if(type.equals("2")){
				BatchMain batchMain = (BatchMain)get(id);
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					String bankAccountNo = "";
					count++;
					String des_key = PropertyUtil.getPropertyValue("DES_KEY");
					bankAccountNo = EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999",apply.getBankCode(),apply.getVehicleOwner().toString(),
							bankAccountNo,apply.getBankName().toString(),apply.getVehiclePlateNum().toString()+"(第"+batchMain.getToFinanceNo()+"批老旧车淘汰补贴)已核非公务卡结算("+apply.getId().toString()+")"};
					dataList.add(strings);
				}
			}
		}else if(batchType.equals("2")){
			list = getRepBatchApplyList(batchNo);
			for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				String vehicleIdentifyNo = "";
				String bankAccountNo = "";
				count++;
				// 车架号解密
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				vehicleIdentifyNo=EncryptUtils.decryptDes(des_key, apply.getVehicleIdentifyNo());			
				bankAccountNo=EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
				// 获得号牌种类名称(从字典表获取)
				String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(apply.getVehiclePlateType());
				apply.setVehiclePlateTypeName(vehiclePlateTypeName);
				String[] strings = new String[]{count+"", apply.getVehicleOwner(),apply.getVehiclePlateNum(),apply.getVehiclePlateTypeName(),apply.getSubsidiesStandard(),
						vehicleIdentifyNo,apply.getAdvancedScrapDays().toString(),apply.getRecycleDate().toString(),"",apply.getIsPersonal().toString(),
						apply.getBankName(),bankAccountNo,apply.getSubsidiesMoney().toString()};
				dataList.add(strings);
			}
		}
		return dataList;
		
	}
	


}
