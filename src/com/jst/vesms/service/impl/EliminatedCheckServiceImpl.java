
package com.jst.vesms.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.model.SysDict;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
import com.jst.util.DateUtil;
import com.jst.util.EncryptUtil;
import com.jst.util.PropertyUtil;
import com.jst.vesms.common.BFGSWebServiceClient;
import com.jst.vesms.common.CacheRead;
import com.jst.vesms.dao.IActionLogDao;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.PostBaseInfo;
import com.jst.vesms.model.VehicleRecycle;
import com.jst.vesms.service.EliminatedCheckService;
import com.jst.vesms.service.VehicleRecycleService;
import com.jst.vesms.util.PhotoUtil;

@Service("eliminatedCheckServiceImpl")
public class EliminatedCheckServiceImpl extends BaseServiceImpl implements EliminatedCheckService {

	@Resource(name="eliminatedApplyDao")
	private IEliminatedApplyDao eliminatedApplyDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Resource(name="postBaseInfoDao")
	private IPostBaseInfoDao postBaseInfoDao;
	
	@Resource(name="actionLogDao")
	private IActionLogDao actionLogDao;
	
	@Resource(name="vehicleRecycleServiceImpl")
	private VehicleRecycleService vehicleRecycleService;
	
	@Resource
	private BFGSWebServiceClient bfgsWebServiceClient;

	/*public void seteliminatedApplyDao(IeliminatedApplyDao eliminatedApplyDao) {
		this.eliminatedApplyDao = eliminatedApplyDao;
	}*/

	@Override
	public BaseDAO getHibernateBaseDAO() {
		return eliminatedApplyDao;
	}

	@Override
	public Page<EliminatedApply> getPageExtra(Page<EliminatedApply> page) throws Exception {
		
		List<EliminatedApply> vehicleList = new ArrayList<EliminatedApply>();
		if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
			List result = page.getResult();
			for (Object obj : result) {
				EliminatedApply eliminatedApply = (EliminatedApply)obj;
				// 获得号牌种类名称
				SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", eliminatedApply.getVehiclePlateType());
				eliminatedApply.setVehiclePlateTypeName(sysDictVehiclePlateType.getDictValue());
				
				// 获得车辆类型名称
				SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", eliminatedApply.getVehicleType());
				eliminatedApply.setVehicleTypeName(sysDictVehicleType.getDictValue());
				
				// 获得使用性质
				SysDict sysDictUseOfProperty = CacheRead.getSysDictByCode("USE_OF_PROPERTY", eliminatedApply.getUseOfProperty());
				if (null != sysDictUseOfProperty) {
					eliminatedApply.setUseOfPropertyName(sysDictUseOfProperty.getDictValue());
				}
				
				// 获得燃油种类
				SysDict sysDictIolType = CacheRead.getSysDictByCode("IOL_TYPE", eliminatedApply.getIolType());
				if (null != sysDictIolType) {
					eliminatedApply.setIolTypeName(sysDictIolType.getDictValue());
				}
				
				// 解密关键数据
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				// 解密车架号
				eliminatedApply.setVehicleIdentifyNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getVehicleIdentifyNo()));
				
				// 解密银行账号
				eliminatedApply.setBankAccountNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getBankAccountNo()));
				
				// 解密车主手机号
				eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getMobile() == null) ? "" : eliminatedApply.getMobile()));
				
				// 解密经办人手机号 
				eliminatedApply.setAgentMobileNo(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentMobileNo() == null) ? "" : eliminatedApply.getAgentMobileNo()));
				
				// 解密车主身份证明号
				eliminatedApply.setVehicleOwnerIdentity(EncryptUtil.decryptDES(des_key, (eliminatedApply.getVehicleOwnerIdentity() == null) ? "" : eliminatedApply.getVehicleOwnerIdentity()));
				
				// 解密经办人身份证号
				eliminatedApply.setAgentIdentity(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentIdentity() == null) ? "" : eliminatedApply.getAgentIdentity()));
				
				vehicleList.add(eliminatedApply);
			}
			result.clear();
			page.setResult(vehicleList);
		}
		
		return page;
	}

	@Override
	public String fileCaptureUpload(String image) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String pictureName = sdf.format(new Date())+".jpg";
		System.out.println("----picture:"+pictureName+"----");
		boolean isSuccess = PhotoUtil.generateFile(image, pictureName);
		if (isSuccess) {
			return pictureName;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Object> save(EliminatedApply eliminatedApply) throws Exception {
		
		// 号牌号码
		String vehiclePlateNum = eliminatedApply.getVehiclePlateNum();
		// 号牌种类
		String vehiclePlateType = eliminatedApply.getVehiclePlateType();
		
		// 传入号牌号码、号牌种类判断车辆补贴资格和补贴金额
		String callNameForVerify = "{call PKG_VEHICLE_VERIFY.pro_vehicle_verify(?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParamsVerify = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParamsVerify = new HashMap<Integer, Integer>();
		
		inParamsVerify.put(1, vehiclePlateNum);
		inParamsVerify.put(2, vehiclePlateType);
		inParamsVerify.put(3, eliminatedApply.getVehicleIdentifyNo() == null ? "" : eliminatedApply.getVehicleIdentifyNo());
		inParamsVerify.put(4, 3);
		Date recycleDate = new java.sql.Date(DateUtil.parse("2017-07-10", DateUtil.DATE_PATTERN_2).getTime());
		inParamsVerify.put(5, recycleDate);
		
		outParamsVerify.put(6, OracleTypes.NUMBER);
		outParamsVerify.put(7, OracleTypes.INTEGER);
		outParamsVerify.put(8, OracleTypes.VARCHAR);
		
		List<Map<String, Object>> verifyResult = callDao.call(callNameForVerify, inParamsVerify, outParamsVerify, "procedure");
		BigDecimal bigDecimal = (BigDecimal)verifyResult.get(0).get("6");
		eliminatedApply.setSubsidiesMoney(bigDecimal.doubleValue());
		eliminatedApply.setSubsidiesStandard((String)verifyResult.get(0).get("8"));
		
		// 传入号牌号码、号牌种类从交警接口获取车辆数据
		//String callName = "{?=call PKG_TEST.test_msg(?)}";
		String callName = "{call PKG_VEHICLES.pro_get_jiaojin_vehicle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1, vehiclePlateNum);
		inParams.put(2, vehiclePlateType);
		
		outParams.put(3, OracleTypes.VARCHAR);
		outParams.put(4, OracleTypes.VARCHAR);
		outParams.put(5, OracleTypes.VARCHAR);
		outParams.put(6, OracleTypes.VARCHAR);
		outParams.put(7, OracleTypes.VARCHAR);
		outParams.put(8, OracleTypes.DATE);
		outParams.put(9, OracleTypes.DATE);
		outParams.put(10, OracleTypes.VARCHAR);
		outParams.put(11, OracleTypes.VARCHAR);
		outParams.put(12, OracleTypes.VARCHAR);
		outParams.put(13, OracleTypes.VARCHAR);
		outParams.put(14, OracleTypes.DATE);
		outParams.put(15, OracleTypes.VARCHAR);
		outParams.put(16, OracleTypes.INTEGER);
		outParams.put(17, OracleTypes.VARCHAR);
		
		
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		for (Map<String, Object> map : result) {
			System.out.println(map.get("3"));    // 车架号
			System.out.println(map.get("4"));    // 燃料种类
			System.out.println(map.get("5"));    // 使用性质
			System.out.println(map.get("6"));    // 车辆类型
			System.out.println(map.get("7"));    // 发动机号
			System.out.println(map.get("8"));    // 强制报废期止
			System.out.println(map.get("9"));    // 注销日期
			System.out.println(map.get("10"));   // 注销类别
			System.out.println(map.get("11"));   // 车主
			System.out.println(map.get("12"));   // 车主身份证明号
			System.out.println(map.get("13"));   // 排放标准
			System.out.println(map.get("14"));   // 初次登记日期
			System.out.println(map.get("15"));   // 车辆状态
			System.out.println(map.get("16"));   // 返回标识
			System.out.println(map.get("17"));   // 返回信息
		}
		
		// 车架号
		Date date = new Date();
		String vehicleIdentifyNo = (String) result.get(0).get("3") + DateUtil.format(date, DateUtil.TIMESTAMPS_PATTERN_1);
		
		// 燃料种类
		String iolType = (String) result.get(0).get("4");
		
		// 使用性质
		String useOfProperty = (String) result.get(0).get("5");
		
		// 车辆类型
		String vehicleType = (String) result.get(0).get("6");
		
		// 发动机号
		String engineNo = (String) result.get(0).get("7");
		
		// 强制报废期止
		Date deadline = (Date) result.get(0).get("8");
		
		
		// 注销日期
		Date destroyDate = (Date) result.get(0).get("9");
		
		// 注销类别
		String cancelReason = (String) result.get(0).get("10");
		
		// 车主
		String vehicleOwner = (String) result.get(0).get("11");
		
		// 车主身份证明号
		String vehicleOwnerIdentity = (String) result.get(0).get("12");
		
		// 排放标准
		String emissionStandard = (String) result.get(0).get("13");
		
		// 初次登记日期
		Date registerDate = (Date) result.get(0).get("14");
		
		// 车辆状态
		String vehicleStatus = (String) result.get(0).get("15");
		
		// 返回标识
		Integer returnFlag = (Integer) result.get(0).get("16");
		
		// 返回信息
		String returnMsg = (String) result.get(0).get("17");
		
		// 设置字段值，交警车辆数据
		eliminatedApply.setVehicleIdentifyNo(vehicleIdentifyNo);
		eliminatedApply.setVehicleType(vehicleType);
		eliminatedApply.setVehicleOwner(vehicleOwner);
		eliminatedApply.setVehicleOwnerIdentity(vehicleOwnerIdentity);
		eliminatedApply.setDestroyDate(destroyDate);
		eliminatedApply.setRegisterDate(registerDate);
		eliminatedApply.setVehicleStatus(vehicleStatus);
		eliminatedApply.setEmissionStandard(emissionStandard);
		eliminatedApply.setIolType(iolType);
		eliminatedApply.setUseOfProperty(useOfProperty);
		eliminatedApply.setEngineNo(engineNo);
		eliminatedApply.setCancelReason(cancelReason);
		eliminatedApply.setDeadline(deadline);
		
		// 报废信息(报废回收证明编号、交售日期)
		VehicleRecycle vehicleRecycle = vehicleRecycleService.getByNumAndType(vehiclePlateNum, vehiclePlateType);
		if (vehicleRecycle != null) {
			eliminatedApply.setRecycleDate(vehicleRecycle.getRecycleDate());
			eliminatedApply.setCallbackProofNo(vehicleRecycle.getCallbackProofNo());
		} else {
			// 获取不到数据，或者车辆已经受理录入过，无法再次受理。
			return null;
		}
		
		// 设置其它字段属性
		
		// 提前报废时长
		Integer advancedScrapDays = DateUtil.getDateDifference(deadline, vehicleRecycle.getRecycleDate(), "day");
		eliminatedApply.setAdvancedScrapDays(advancedScrapDays);
		
		// 补贴对象名称
		eliminatedApply.setBankAccountName(vehicleOwner);
		
		// 正常，无退回
		eliminatedApply.setIsFault("1");
		
		// 业务状态
		eliminatedApply.setBussinessStatus("1");//正常：1
		
		// 当前岗位,受理录入岗
		PostBaseInfo currentPost = postBaseInfoDao.getByCode("CKSLG");
		eliminatedApply.setCurrentPost("CKSLG");
		eliminatedApply.setCurrentPost(currentPost.getPrePost());
		
		eliminatedApply.setBussinessDataSrc("1");
		eliminatedApply.setIsModified("N");
		
		eliminatedApply.setToFinanceStatus("-1");
		
		eliminatedApply.setArchivedStatus("0");
		
		// 生成受理单号
		Map<Integer, Integer> outParamsApplyNo = new HashMap<Integer, Integer>();
		outParamsApplyNo.put(1, OracleTypes.VARCHAR);
		List<Map<String, Object>> callGenApplyNoRes = callDao.call("{?= call PKG_APPLY.func_create_apply_no}", null, outParamsApplyNo, "function");
		String applyNo = (String) callGenApplyNoRes.get(0).get("1");
		eliminatedApply.setApplyNo(applyNo);
		
		// 档案盒编号,调用存储过程创建档案盒号
		Map<Integer, Object> inParamsBox = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParamsBox = new HashMap<Integer, Integer>();
		inParamsBox.put(1, applyNo);
		
		outParamsBox.put(2, OracleTypes.VARCHAR);
		outParamsBox.put(3, OracleTypes.VARCHAR);
		outParamsBox.put(4, OracleTypes.INTEGER);
		outParamsBox.put(5, OracleTypes.VARCHAR);
		
		List<Map<String, Object>> callArchiveRes = callDao.call("{call PKG_ARCHIVE.pro_create_archived_box(?,?,?,?,?)}", inParamsBox, outParamsBox, "procedure");
		
		// 赋值
		eliminatedApply.setArchiveBoxNo((String)callArchiveRes.get(0).get("2"));
		// 档案盒内编号
		eliminatedApply.setArchivedInnerNo((String)callArchiveRes.get(0).get("3"));
		
		// 录入时间
		eliminatedApply.setApplyTime(new Date());
		
		// 办结状态
		eliminatedApply.setConcludeStatus("0");
		
		// 校验码
		eliminatedApply.setVerifyCode("1");
		
		// 数据更新时间
		eliminatedApply.setUpdateTime(new Date());
		
		List list1 = eliminatedApplyDao.getByPropertys(new String[] {"applyNo", "vehiclePlateNum"}, new Object[] {"12", "1"}, null);
		
		Serializable id = eliminatedApplyDao.save(eliminatedApply);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != id) {
			map.put("isSuccess", true);
			map.put("id", id);
			map.put("applyNo", applyNo);
			map.put("archiveBoxNo", eliminatedApply.getArchiveBoxNo());
			map.put("archivedInnerNo", eliminatedApply.getArchivedInnerNo());
			map.put("msg", "受理单保存成功！");
		} else {
			map.put("isSuccess", false);
			map.put("msg", "受理单保存失败！");
		}
		
		return map;
	}

	@Override
	public EliminatedApply getById(Integer id) throws Exception {
		
		//通过Id获取对象
		EliminatedApply eliminatedApply = (EliminatedApply) eliminatedApplyDao.get(id);
		
		// 通过缓存，获取号牌种类、车辆类型、燃油种类、使用性质、车辆状态等字典类型字段的名称
		// 号牌种类
		String vehiclePlateType = eliminatedApply.getVehiclePlateType();
		if (StringUtil.isNotEmpty(vehiclePlateType)) {
			SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
			if (null != sysDictVehiclePlateType) {
				String vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
				eliminatedApply.setVehiclePlateType(vehiclePlateType);
				eliminatedApply.setVehiclePlateTypeName(vehiclePlateTypeName);
			}
		}
		
		// 车辆类型
		String vehicleType = eliminatedApply.getVehicleType();
		if (StringUtil.isNotEmpty(vehicleType)) {
			SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
			if (null != sysDictVehicleType) {
				String vehicleTypeName = sysDictVehicleType.getDictValue();
				eliminatedApply.setVehicleType(vehicleType);
				eliminatedApply.setVehicleTypeName(vehicleTypeName);
			}
		}
		
		// 燃油种类
		String iolType = eliminatedApply.getIolType();
		if (StringUtil.isNotEmpty(iolType)) {
			SysDict sysDictIOLType = CacheRead.getSysDictByCode("IOL_TYPE", iolType);
			if (null != sysDictIOLType) {
				String iolTypeName = sysDictIOLType.getDictValue();
				eliminatedApply.setIolType(iolType);
				eliminatedApply.setIolTypeName(iolTypeName);;
			}
		}
		
		// 使用类型
		String useOfProperty = eliminatedApply.getUseOfProperty();
		if (StringUtil.isNotEmpty(useOfProperty)) {
			SysDict sysDictUseType = CacheRead.getSysDictByCode("USE_OF_PROPERTY", useOfProperty);
			if (null != sysDictUseType) {
				String useOfPropertyName = sysDictUseType.getDictValue();
				eliminatedApply.setUseOfProperty(useOfProperty);
				eliminatedApply.setUseOfPropertyName(useOfPropertyName);
			}
		}
		
		// 车辆状态
		String vehicleStatus = eliminatedApply.getVehicleStatus();
		if (StringUtil.isNotEmpty(vehicleStatus)) {
			SysDict sysDictVehicleStatus = CacheRead.getSysDictByCode("VEHICLE_STATUS", vehicleStatus);
			if (null != sysDictVehicleStatus) {
				String vehicleStatusName = sysDictVehicleStatus.getDictValue();
				eliminatedApply.setVehicleStatus(vehicleStatus);
				eliminatedApply.setVehicleStatusName(vehicleStatusName);
			}
		}
		
		// 解密关键数据
		String des_key = PropertyUtil.getPropertyValue("DES_KEY");
		// 解密车架号
		eliminatedApply.setVehicleIdentifyNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getVehicleIdentifyNo()));
		
		// 解密银行账号
		eliminatedApply.setBankAccountNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getBankAccountNo()));
		
		// 解密车主手机号
		eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getMobile() == null) ? "" : eliminatedApply.getMobile()));
		
		// 解密经办人手机号 
		eliminatedApply.setAgentMobileNo(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentMobileNo() == null) ? "" : eliminatedApply.getAgentMobileNo()));
		
		// 解密车主身份证明号
		eliminatedApply.setVehicleOwnerIdentity(EncryptUtil.decryptDES(des_key, (eliminatedApply.getVehicleOwnerIdentity() == null) ? "" : eliminatedApply.getVehicleOwnerIdentity()));
		
		// 解密经办人身份证号
		eliminatedApply.setAgentIdentity(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentIdentity() == null) ? "" : eliminatedApply.getAgentIdentity()));
		
		return eliminatedApply;
	}

	@Override
	public EliminatedApply getVehicleInfo(String vehiclePlateNum,
			String vehiclePlateType) throws Exception {
		
		return null;
	}

	@Override
	public boolean confirm(Integer id) throws Exception {
		
		return true;
	}

	@Override
	public boolean saveAttachment(String type, Integer id, String absFilePath,
			String businessType) throws Exception {
		
		return true;
	}

	@Override
	public Map<String, Object> updateById(Integer id,
			EliminatedApply eliminatedApply) throws Exception {
		
		eliminatedApply.setApplyConfirmTime(new Date());
		EliminatedApply updatedApply = (EliminatedApply) this.update(id, eliminatedApply);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != updatedApply) {
			map.put("isSuccess", true);
			map.put("id", updatedApply.getId());
			map.put("applyNo", updatedApply.getApplyNo());
			map.put("archiveBoxNo", updatedApply.getArchiveBoxNo());
			map.put("archivedInnerNo", updatedApply.getArchivedInnerNo());
			map.put("msg", "受理单保存成功！");
		} else {
			map.put("isSuccess", false);
			map.put("msg", "受理单保存失败！");
		}
		
		return map;
	}

	/**
	 * 
	 * TODO 按照受理单id集合审批.
	 * @see com.jst.vesms.service.EliminatedCheckService#check(java.lang.String)
	 */
	@Override
	public Map<String, Object> check(String ids, String checkType, String faultType, String checkOpinion, String currentPost) throws Exception {
		boolean isSuccess = false;
		String msg = "";
		Map map = new HashMap<String, Object>();
		
		String strIds = "";
		String idString[] = ids.split(",");
		for (int i = 0; i < idString.length; i ++) {
			strIds += idString[i].concat("|");
		}
		strIds = strIds.substring(0, strIds.length() - 1);
		
		String callName = "{call PKG_APPLY.p_apply_check(?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1, "admin");
		inParams.put(2, "管理员");
		inParams.put(3, currentPost);
		inParams.put(4, strIds);
		inParams.put(5, checkType);
		inParams.put(6, faultType);
		inParams.put(7, checkOpinion);
		
		outParams.put(8, OracleTypes.VARCHAR); // 存储过程执行结果，消息字符串
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		if (null != result && result.size() > 0) {
			Map<String, Object> tmp = result.get(0);
			if (tmp.get("8") != null) {
				isSuccess = true;
				msg = (String) tmp.get("8");
				map.put("msg", msg);
			}
		}
		map.put("isSuccess", isSuccess);
		
		return map;
	}

	/**
	 * 
	 * TODO 通过Id获取业务流水表记录.
	 * @see com.jst.vesms.service.EliminatedCheckService#getActionLogList(java.lang.Integer)
	 */
	@Override
	public List<ActionLog> getActionLogList(Integer id) throws Exception {
		List list = new ArrayList<ActionLog>();
		EliminatedApply apply = (EliminatedApply) this.get(id);
		if (null != apply) {
			// 按照处理时间顺序排列
			list = actionLogDao.getByPropertys(new String[]{"applyNo"}, new Object[]{apply.getApplyNo()}, "1=1 order by actionTime asc");
		}
		return list;
	}
	
}

