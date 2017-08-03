package com.jst.vesms.service.impl;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
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
import com.jst.vesms.dao.IPayApplyDao;
import com.jst.vesms.model.BatchApplyDetail;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.PayResult;
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

	// 获取重报预览数据的sql
	@Override
	public List getBySql(String batchNo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select rownum xuhao,");
		sb.append("thisbatchno,thistofinanceno,vehicle_plate_num,vehicle_plate_type,subsidies_money,");
		sb.append("lastbankname,lastaccountname,lastaccountno,");
		sb.append("thisbankname,thisaccountname,thisaccountno,thisfaultType,");
		sb.append("firsttofinanceno from ");
		sb.append(" (select tbm.batch_no thisbatchno,tbm.to_finance_no thistofinanceno,");
		sb.append("tea.vehicle_plate_num, tea.vehicle_plate_type,");
		sb.append("tea.subsidies_money,tbd.remark,");
		sb.append("abs(tea.to_finance_status) tofinancecishu,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'PCH:','|') lastbatchno,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'BSXH:','|') lasttofinanceno,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHYH:','|') lastbankname,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'KHHM:','|') lastaccountname,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,instr(tbd.remark,'[',-1)+1),']',''),'YHZH:','|') lastaccountno,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,1,instr(tbd.remark,']')-1),'[',''),'BSXH:','|') firsttofinanceno,");
		sb.append("tea.bank_name thisbankname,");
		sb.append("tea.bank_account_name thisaccountname,");
		sb.append("tea.bank_account_no thisaccountno,tea.fault_type thisfaultType ");
		sb.append("from t_eliminated_apply tea,t_batch_detail tbd,t_batch_main tbm ");
		sb.append("where tbm.batch_no= '"+batchNo+"' ");
		sb.append("and tbm.batch_no=tbd.batch_no ");
		sb.append("and tea.apply_no=tbd.apply_no ");
		sb.append("order by tea.apply_confirm_time)");
		String sql = sb.toString();
		List list = payApplyDao.getTableList(sql, null);
		return list;
	}

	
	//获取重报报财务数据的sql
	public List getRepSql(String batchNo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" select tea.subsidies_money,");
		sb.append(" tea.bank_code,tea.bank_account_name,tea.bank_account_no,");
		sb.append(" tea.bank_name, tea.vehicle_plate_num,");
		sb.append("f_getitemvalue(replace(substr(tbd.remark,1,instr(tbd.remark,']')-1),'[',''),'BSXH:','|') firsttofinanceno , ");
		sb.append("tbm.to_finance_no,tea.id,tea.is_personal ");
		sb.append(" from t_eliminated_apply tea,t_batch_Main tbm,t_batch_detail tbd ");
		sb.append(" where   tbd.batch_no=tbm.batch_no and tea.apply_no=tbd.apply_no ");
		sb.append("and tbm.batch_no= '"+batchNo+"' ");
		sb.append("order by tea.apply_confirm_time");
		String sql = sb.toString();
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
	
	
	// 正常批次excel文件数据获取(正常预览   正常报财务excel报表    正常审批表预览     重报报财务excel报表 共同调用这个方法)
	@Override
	public List<String[]> batchExcelList(String batchNo,String type,Integer id) throws Exception {
		// TODO Auto-generated method stub
		int count =0;
		List<String[]> dataList = new ArrayList<String[]>();
		//if(batchType.equals("1")){
			List<EliminatedApply> list = getBatchApplyList(batchNo);
			if(type.equals("1")){
				//从数据库获取正常审批预览表数据
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					String vehicleIdentifyNo = "";
					String bankAccountNo = "";
					count++;
					// 车架号解密
					String des_key = PropertyUtil.getPropertyValue("DES_KEY");
					vehicleIdentifyNo=EncryptUtils.decryptDes(des_key, apply.getVehicleIdentifyNo());			
					bankAccountNo=EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
					/* 获得号牌种类名称(从字典表获取)
					String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(apply.getVehiclePlateType());
					apply.setVehiclePlateTypeName(vehiclePlateTypeName);*/
					//获取财政供养信息
					String isFinancialSupport = apply.getIsFinancialSupport();
					if(isFinancialSupport.equals("1")){
						isFinancialSupport="个人";
					}else if(isFinancialSupport.equals("2")){
						isFinancialSupport="车主自证非财政供养";
					}
					//截取补贴标准 获取车辆类型 和 排放标准
					String fullName[] = apply.getSubsidiesStandard().split("）");//补贴标准全名称
					String subsidiesStandard = fullName[0]+"）";
					String vehicleType = fullName[1];
					// 报废交售时间格式转换
					String recycleDate = new SimpleDateFormat("yyyy-MM-dd").format(apply.getRecycleDate());
					String[] strings = new String[]{count+"", apply.getVehicleOwner(),apply.getVehiclePlateNum(),vehicleType,subsidiesStandard,
							vehicleIdentifyNo,apply.getAdvancedScrapDays().toString(),recycleDate,apply.getCancelReason(),isFinancialSupport,
							apply.getBankAccountName(),apply.getBankName(),bankAccountNo,apply.getSubsidiesMoney().toString()};
					dataList.add(strings);
				}
			}else if(type.equals("2")){
				//从数据库获取正常报财务excel数据
				BatchMain batchMain = (BatchMain)get(id);
				for (int i = 0; i < list.size(); i++) {
					EliminatedApply apply = list.get(i);
					String bankAccountNo = "";
					count++;
					String des_key = PropertyUtil.getPropertyValue("DES_KEY");
					bankAccountNo = EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
					//正常报财务批次摘要 ： 车牌号+老旧车补贴toFinanceNo批 已核定非公务卡结算|applyId
					String summary = "";
					if(apply.getIsPersonal().equals("Y")){
						summary = apply.getVehiclePlateNum()+"老旧车补贴第"+batchMain.getToFinanceNo()+"批已核非公务卡结算|"+apply.getId();
					}else if((apply.getIsPersonal().equals("N"))){
						summary = apply.getVehiclePlateNum()+"老旧车补贴第"+batchMain.getToFinanceNo()+"批|"+apply.getId();
					}
					String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999",apply.getBankCode(),apply.getBankAccountName(),
							bankAccountNo,apply.getBankName(),summary};
					dataList.add(strings);
				}
			}
		/*}else if(batchType.equals("2")){
			//从数据库获取重报报财务数据
			List<EliminatedApply> list = getRepBatchApplyList(batchNo);
			BatchMain batchMain = (BatchMain)get(id);
			for (int i = 0; i < list.size(); i++) {
				EliminatedApply apply = list.get(i);
				String bankAccountNo = "";
				count++;
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				bankAccountNo = EncryptUtils.decryptDes(des_key, apply.getBankAccountNo());
				//正常报财务批次摘要 ： 车牌号+老旧车补贴toFinanceNo批 已核定非公务卡结算|applyId
				String summary = "";
				if(apply.getIsPersonal().equals("Y")){
					summary = apply.getVehiclePlateNum()+"老旧车"+""+"批重报"+batchMain.getToFinanceNo()+"批已核非公务卡结算|"+apply.getId();
				}else if((apply.getIsPersonal().equals("N"))){
					summary = apply.getVehiclePlateNum()+"老旧车"+""+"批重报"+batchMain.getToFinanceNo()+"批|"+apply.getId()+")";
				}
				String[] strings = new String[]{count+"", apply.getSubsidiesMoney().toString(),"39999",apply.getBankCode(),apply.getVehicleOwner(),
						bankAccountNo,apply.getBankName(),summary};
				dataList.add(strings);
			}
		}*/
		return dataList;
	}
	
	
	// 重报批次预览文件数据获取
	@Override
	public List<String[]> repBatchExcelPreview(String batchNo) throws Exception{
		int count=0;
		List<String[]> dataList = new ArrayList<String[]>();
		//调用sql 
		List sqlList = this.getBySql(batchNo);
		for (int i = 0; i < sqlList.size(); i++) {
			count++;
			Object[] object = (Object[]) sqlList.get(i);
			//序号
			//BigDecimal xuhaoBigDecimal = (BigDecimal)object[0];
			//String number = (String) object[0];
			//车牌号码
			String vehiclePlateNum = (String)object[3];
			//补贴金额
			BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)object[5];
			String subsidiesMoney = subsidiesMoneyBigDecimal.toPlainString();
			//原车主姓名
			String lastAccountName = (String)object[7];
			//原开户行
			String lastBankName = (String)object[6];
			//原开户账户
			String lastAccountNo = (String)object[8];	
			String key = PropertyUtil.getPropertyValue("DES_KEY");
		//	lastAccountNo = EncryptUtils.decryptDes(key, lastAccountNo);
			//变更后补贴对象
			String thisBankName = (String)object[9];
			//变更后银行
			String thisAccountName = (String)object[10];
			//变更后银行账号
			String thisAccountNo = (String)object[11];
			thisAccountNo = EncryptUtils.decryptDes(key, thisAccountNo);
			//变更后补贴对象
			//变更内容
			String thisType = (String)object[12];
			if(thisType .equals("1")){
				thisType="一般资料修正";
			}else if(thisType.equals("2")){
				thisType="补贴账户错误修正";
			}
			//当前报送序号
		/*	BigDecimal thisToFinanceNoBigDecimal = (BigDecimal)object[2];
			String thisToFinanceNo = thisToFinanceNoBigDecimal.toPlainString();*/
			// 初次报送序号
			String firstToFinanceno = (String)object[13];
			
			String[] strings = new String[] {count+"",vehiclePlateNum,subsidiesMoney,
					lastAccountName,lastBankName,lastAccountNo,thisAccountName,thisBankName,thisAccountNo,
					thisType,firstToFinanceno};
			dataList.add(strings);
		}
		return dataList;
	}
	
	
	
	
	
	// 重报批次预览文件数据获取
	@Override
	public List<String[]> repToFinExcelPreview(String batchNo) throws Exception{
		int count=0;
		List<String[]> dataList = new ArrayList<String[]>();
		//调用sql 
		List sqlList = this.getRepSql(batchNo);
		//解密所需的key
		String key = PropertyUtil.getPropertyValue("DES_KEY");
		for (int i = 0; i < sqlList.size(); i++) {
			//序号
			count++;
			Object[] object = (Object[]) sqlList.get(i);
			//补贴金额
			BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)object[0];
			String subsidiesMoney = subsidiesMoneyBigDecimal.toPlainString();
			//收款人行别编码
			String bankCode = (String)object[1];
			// 补贴对象
			String bankAccountName = (String)object[2];
			// 开户账户(double)
			String bankAccountNo = (String)object[3];
			bankAccountNo = EncryptUtils.decryptDes(key, bankAccountNo);
			// 开户银行
			String bankName = (String)object[4];
			// 号牌号码
			String vehiclePlateNum = (String) object[5];
			// 初次报送序号
			String firstToFinanceNo = (String) object[6];
			// 当前报送序号 (int)
			BigDecimal toFinanceNoBigDecimal = (BigDecimal)object[7];
			String toFinanceNo = toFinanceNoBigDecimal.toPlainString();
			// 业务id号 (int)
			BigDecimal applyIdBigDecimal = (BigDecimal)object[8];
			String applyId = applyIdBigDecimal.toPlainString();
			//摘要
			String summary ="";
			// 是否自然人
			String isPersonal = (String)object[9];
			if(isPersonal.equals("Y")){
				summary=vehiclePlateNum+"老旧车"+firstToFinanceNo+"批重报"+toFinanceNo+"批"+"已核非公务卡结算|"+applyId;
			}else if(isPersonal.equals("N")){
				summary=vehiclePlateNum+"老旧车"+firstToFinanceNo+"批重报"+toFinanceNo+"批"+"|"+applyId;
			}			
			String[] strings = new String[] {count+"",subsidiesMoney,"39999",
					bankCode,bankAccountName,bankAccountNo,bankName,summary};
			dataList.add(strings);
		}
		return dataList;
	}
	
	
	
	
	
	public String getTotalAmount(String batchNo,String batchType) throws Exception{
		List<EliminatedApply> list = null;
		if(batchType.equals("1")){
			list = getBatchApplyList(batchNo);
		}else if(batchType.equals("2")){
			list = getRepBatchApplyList(batchNo);
		}
		double totalAmount = 0;
		for (int i = 0; i < list.size(); i++) {
			EliminatedApply apply = list.get(i);
			totalAmount=apply.getSubsidiesMoney()+totalAmount;
		}
		BigDecimal bigDecimal = new BigDecimal(totalAmount);  
	    String result = bigDecimal.toString();
		return result;
	}
	
	
	
	// 正常业务拨付结果标记查询sql
	public Page getPageSql(Page page,String sql) throws Exception {
			List<BatchApplyDetail> list2 = new ArrayList<BatchApplyDetail>();
			List list = payApplyDao.getTableList(sql, page);
			long count = payApplyDao.getListCounter("select count(*) from (" + sql + ")");
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] objs = (Object[]) list.get(i);
					BatchApplyDetail batchApplyDetail = new BatchApplyDetail();
					
					BigDecimal idBigDecimal = (BigDecimal)objs[0];
					Integer id = idBigDecimal.intValue();
					batchApplyDetail.setId(id);
					
/*					BigDecimal toFinanceNoBigDecimal = (BigDecimal)objs[1];
					Integer toFinanceNo = toFinanceNoBigDecimal.intValue();
					batchApplyDetail.setToFinanceNo(toFinanceNo);*/
					
					String batchNo = (String) objs[1];
					batchApplyDetail.setBatchNo(batchNo);
					
					String applyNo = (String) objs[2];
					batchApplyDetail.setApplyNo(applyNo);
					
					String vehicleOwner = (String) objs[3];
					batchApplyDetail.setVehicleOwner(vehicleOwner);
					
					BigDecimal subsidiesMoneyBigDecimal = (BigDecimal)objs[4];
					Double subsidiesMoney = subsidiesMoneyBigDecimal.doubleValue();
					batchApplyDetail.setSubsidiesMoney(subsidiesMoney);
					
					String vehiclePlateNum = (String) objs[5];
					batchApplyDetail.setVehiclePlateNum(vehicleOwner);
					
					// 号牌种类
					String vehiclePlateType = (String) objs[6];
					if (StringUtil.isNotEmpty(vehiclePlateType)) {
						String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(vehiclePlateType);
						if (null == vehiclePlateTypeName) {
							SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
							if (null != sysDictVehiclePlateType) {
								vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
							}
						}
						batchApplyDetail.setVehiclePlateTypeName(vehiclePlateTypeName);
					}
					
					String toFinanceStatus = (String) objs[7];
					batchApplyDetail.setToFinanceStatus(toFinanceStatus);
					
					// 车辆类型
					String vehicleType = (String) objs[8];;
					if (StringUtil.isNotEmpty(vehicleType)) {
						SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
						if (null != sysDictVehicleType) {
							String vehicleTypeName = sysDictVehicleType.getDictValue();
							//eliminatedApply.setVehicleType(vehicleType);
							batchApplyDetail.setVehicleTypeName(vehicleTypeName);
						}
					}
					
					String vehicleIdentifyNo = (String) objs[9];
					String key = PropertyUtil.getPropertyValue("DES_KEY");
					vehicleIdentifyNo = EncryptUtils.decryptDes(key, vehicleIdentifyNo);
					batchApplyDetail.setVehicleIdentifyNo(vehicleIdentifyNo);
					
					
					java.sql.Timestamp confirmDate = (Timestamp) objs[10];
					if (confirmDate != null){
						Date applyConfirmTime = new Date(confirmDate.getTime());
						batchApplyDetail.setApplyConfirmTime(applyConfirmTime);
					}
					
					String payStatus = (String) objs[11];
					batchApplyDetail.setPayStatus(payStatus);
					
					list2.add(batchApplyDetail);
				}
				
				page.setTotalCount(count);
				page.setResult(list2);
			}
		
		return page;
	}
	


}
