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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import com.jst.vesms.dao.IAppointmentVehicleDao;
import com.jst.vesms.dao.IAttachmentDao;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.AppointmentVehicle;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.PostBaseInfo;
import com.jst.vesms.model.VehicleRecycle;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.SysDictService;
import com.jst.vesms.service.VehicleRecycleService;
import com.jst.vesms.util.PhotoUtil;

@Service("eliminatedApplyServiceImpl")
public class EliminatedApplyServiceImpl extends BaseServiceImpl implements EliminatedApplyService {

	@Resource(name="eliminatedApplyDao")
	private IEliminatedApplyDao eliminatedApplyDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Resource(name="actionLogDao")
	private IActionLogDao actionLogDao;
	
	@Resource(name="attachmentDao")
	private IAttachmentDao attachmentDao;
	
	@Resource(name="postBaseInfoDao")
	private IPostBaseInfoDao postBaseInfoDao;
	
	@Resource(name="vehicleRecycleServiceImpl")
	private VehicleRecycleService vehicleRecycleService;
	
	@Resource(name="appointmentVehicleDao")
	private IAppointmentVehicleDao appointmentVehicleDao;
	
	@Resource
	private BFGSWebServiceClient bfgsWebServiceClient;
	
	@Resource(name="sysDictServiceImpl")
	private SysDictService sysDictService;

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
				eliminatedApply.setUseOfPropertyName(sysDictUseOfProperty.getDictValue());
				
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
				eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentMobileNo() == null) ? "" : eliminatedApply.getAgentMobileNo()));
				
				// 解密车主身份证明号
				eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getVehicleOwnerIdentity() == null) ? "" : eliminatedApply.getVehicleOwnerIdentity()));
				
				// 解密经办人身份证号
				eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentIdentity() == null) ? "" : eliminatedApply.getAgentIdentity()));
				
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
	public Map<String, Object> save(EliminatedApply eliminatedApply, String callbackProofFile, String vehicleCancelProofFiles,
			  String bankCardFiles, String vehicleOwnerProofFiles, String agentProxyFiles, String agentProofFiles,
			  String noFinanceProvideFiles, String openAccPromitFiles) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 号牌号码
		String vehiclePlateNum = eliminatedApply.getVehiclePlateNum();
		// 号牌种类
		String vehiclePlateType = eliminatedApply.getVehiclePlateType();
		
		// 调用资格校验存储过程，判断补贴资格
		/*Map<String, Object> verifyResult = this.verifyVehicle(vehiclePlateNum, vehiclePlateType, eliminatedApply.getVehicleIdentifyNo(), "1");
		
		if (null != verifyResult) {
			// 存储过程调用成功
			Integer flag = (Integer) verifyResult.get("7");
			
			String msg = (String) verifyResult.get("8");
			
			if (flag == 1) {
				
				// 设置各业务状态字段值
				
				// 暂时跳过资格校验，直接从交警接口获取
				Map<String, Object> vehicleMap = this.getJiaoJingVehicle(vehiclePlateNum, vehiclePlateType);
				
				if (null != vehicleMap) {
					if (vehicleMap.get("retCode").equals(1)) {
						// 调用成功
						EliminatedApply jiaoJingVehicle = (EliminatedApply) vehicleMap.get("apply");
						
						if (null != jiaoJingVehicle) {
							// 从交警接口获取数据
							eliminatedApply.setVehiclePlateTypeName(jiaoJingVehicle.getVehiclePlateTypeName());
							// 车架号
							//EncryptUtil.encryptDES(des_key, jiaoJingVehicle.getVehicleIdentifyNo())
							eliminatedApply.setVehicleIdentifyNo(EncryptUtil.encryptDES(des_key, jiaoJingVehicle.getVehicleIdentifyNo()));
							// 燃料种类
							eliminatedApply.setIolType(jiaoJingVehicle.getIolType());
							eliminatedApply.setIolTypeName(jiaoJingVehicle.getIolTypeName());
							// 使用性质
							eliminatedApply.setUseOfProperty(jiaoJingVehicle.getUseOfProperty());
							eliminatedApply.setUseOfPropertyName(jiaoJingVehicle.getUseOfPropertyName());
							// 车辆类型
							eliminatedApply.setVehicleType(jiaoJingVehicle.getVehicleType());
							eliminatedApply.setVehicleTypeName(jiaoJingVehicle.getVehicleTypeName());
							// 发动机型号
							eliminatedApply.setEngineNo(jiaoJingVehicle.getEngineNo());
							// 强制报废期止
							eliminatedApply.setDeadline(jiaoJingVehicle.getDeadline());
							// 注销日期
							eliminatedApply.setDestroyDate(jiaoJingVehicle.getDestroyDate());
							// 注销类别
							eliminatedApply.setCancelReason(jiaoJingVehicle.getCancelReason());
							// 车主
							eliminatedApply.setVehicleOwner(jiaoJingVehicle.getVehicleOwner());
							// 排放阶段
							eliminatedApply.setEmissionStandard(jiaoJingVehicle.getEmissionStandard());
							// 初次登记日期
							eliminatedApply.setRegisterDate(jiaoJingVehicle.getRegisterDate());
							// 车辆状态
							eliminatedApply.setVehicleStatus(jiaoJingVehicle.getVehicleStatus());
							eliminatedApply.setVehicleStatusName(jiaoJingVehicle.getVehicleStatusName());
							// 报废交售日期
							eliminatedApply.setRecycleDate(jiaoJingVehicle.getRecycleDate());
							// 报废回收证明编号
							eliminatedApply.setCallbackProofNo(jiaoJingVehicle.getCallbackProofNo());
							// 提前报废时长
							eliminatedApply.setAdvancedScrapDays(jiaoJingVehicle.getAdvancedScrapDays());
							// 补贴对象名称
							eliminatedApply.setBankAccountName(jiaoJingVehicle.getBankAccountName());
							// 补贴金额
							eliminatedApply.setSubsidiesMoney(jiaoJingVehicle.getSubsidiesMoney());
							// 补贴标准
							eliminatedApply.setSubsidiesStandard(jiaoJingVehicle.getEmissionStandard());
						}*/
						
						// 获得银行名称
						String bankCode = eliminatedApply.getBankCode();
						if (StringUtil.isNotEmpty(bankCode)) {
							List<SysDict> bankListSysDict = sysDictService.getListByPorperty("dictCode", bankCode, "state = '1'");
							if (null != bankListSysDict && bankListSysDict.size() > 0) {
								eliminatedApply.setBankName(bankListSysDict.get(0).getDictValue());
							}
						}
						
						// 加密车架号
						String des_key = PropertyUtil.getPropertyValue("DES_KEY");
						//EncryptUtil.encryptDES(des_key, jiaoJingVehicle.getVehicleIdentifyNo())
						eliminatedApply.setVehicleIdentifyNo(EncryptUtil.encryptDES(des_key, eliminatedApply.getVehicleIdentifyNo()));
						
						// 加密银行账号
						eliminatedApply.setBankAccountNo(EncryptUtil.encryptDES(des_key, eliminatedApply.getBankAccountNo()));
						
						// 加密车主手机号
						eliminatedApply.setMobile(EncryptUtil.encryptDES(des_key, eliminatedApply.getMobile()));
						
						// 加密经办人手机号
						eliminatedApply.setAgentMobileNo(EncryptUtil.encryptDES(des_key, eliminatedApply.getAgentMobileNo()));
						
						// 加密车主身份证明号
						eliminatedApply.setVehicleOwnerIdentity(EncryptUtil.encryptDES(des_key, eliminatedApply.getVehicleOwnerIdentity()));
						
						// 加密经办人身份证明号
						eliminatedApply.setAgentIdentity(EncryptUtil.encryptDES(des_key, eliminatedApply.getAgentIdentity()));
						
						
						// 正常，无退回
						eliminatedApply.setIsFault("0");
						
						// 业务状态
						eliminatedApply.setBussinessStatus("1");//正常：1,异常：-1
						
						// 当前岗位,窗口受理岗
						PostBaseInfo currentPost = postBaseInfoDao.getByCode("CKSLG");
						eliminatedApply.setCurrentPost("CKSLG");
						eliminatedApply.setCurrentPost(currentPost.getPrePost());
						
						eliminatedApply.setBussinessDataSrc("1"); // 业务来源，受理录入
						eliminatedApply.setIsModified("N"); // 正常，无修改
						
						eliminatedApply.setToFinanceStatus("-1"); // 置报财务状态-1
						
						eliminatedApply.setArchivedStatus("0");
						
						// 生成受理单号
						Map<Integer, Integer> outParamsApplyNo = new HashMap<Integer, Integer>();
						outParamsApplyNo.put(1, OracleTypes.VARCHAR);
						List<Map<String, Object>> callGenApplyNoRes = callDao.call("{?= call PKG_APPLY.f_create_apply_no}", null, outParamsApplyNo, "function");
						String applyNo = (String) callGenApplyNoRes.get(0).get("1");
						eliminatedApply.setApplyNo(applyNo);
						
						// 录入时间
						eliminatedApply.setApplyTime(new Date());
						
						// 办结状态
						eliminatedApply.setConcludeStatus("0");
						
						// 校验码
						eliminatedApply.setVerifyCode("1");
						
						// 数据更新时间
						eliminatedApply.setUpdateTime(new Date());
						
						//List list1 = eliminatedApplyDao.getByPropertys(new String[] {"applyNo", "vehiclePlateNum"}, new Object[] {"12", "1"}, null);
						
						Serializable id = eliminatedApplyDao.save(eliminatedApply);
						
						if (null != id) {
							// 更新附件表数据
							Map<String, Object> saveFilesRes = this.saveAttachments(applyNo, callbackProofFile, vehicleCancelProofFiles, bankCardFiles, vehicleOwnerProofFiles, agentProxyFiles, agentProofFiles, noFinanceProvideFiles, openAccPromitFiles);
							if (saveFilesRes.get("isSave").equals(true)) {
								map.put("isSuccess", true);
								map.put("id", id);
								map.put("msg", "受理单保存成功！");
							} else {
								map.put("isSuccess", false);
								map.put("msg", "受理单证明材料保存失败！");
							}
							
						} else {
							map.put("isSuccess", false);
							map.put("msg", "受理单保存失败！");
						}
						
					/*} else {
						map.put("retCode", vehicleMap.get("retCode"));
						map.put("msg", vehicleMap.get("msg"));
					}
				} else {
					// 存储过程执行返回结果，资格校验不通过
					map.put("isSuccess", false);
					map.put("msg", "系统异常");
				}*/
		
				
			/*} else {
				// 存储过程执行返回结果，资格校验不通过
				map.put("isSuccess", false);
				map.put("msg", msg);
			}
		}*/
		
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
		
		String des_key = PropertyUtil.getPropertyValue("DES_KEY");
		// 解密车架号
		eliminatedApply.setVehicleIdentifyNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getVehicleIdentifyNo()));
		
		// 解密银行账号
		eliminatedApply.setBankAccountNo(EncryptUtil.decryptDES(des_key, eliminatedApply.getBankAccountNo()));
		
		// 解密车主手机号
		eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getMobile() == null) ? "" : eliminatedApply.getMobile()));
		
		// 解密经办人手机号 
		eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentMobileNo() == null) ? "" : eliminatedApply.getAgentMobileNo()));
		
		// 解密车主身份证明号
		eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getVehicleOwnerIdentity() == null) ? "" : eliminatedApply.getVehicleOwnerIdentity()));
		
		// 解密经办人身份证号
		eliminatedApply.setMobile(EncryptUtil.decryptDES(des_key, (eliminatedApply.getAgentIdentity() == null) ? "" : eliminatedApply.getAgentIdentity()));
		
		return eliminatedApply;
	}

	@Override
	public Map<String, Object> getVehicleInfo(String vehiclePlateNum,
			String vehiclePlateType) throws Exception {
		EliminatedApply eliminatedApply = new EliminatedApply();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> result = this.verifyVehicle(vehiclePlateNum, vehiclePlateType, null, "1");
		if (result != null) {
			// 分情况返回信息
			// 返回标识
			Integer returnFlag = (Integer) result.get("7");
			
			// 返回信息
			String returnMsg = (String) result.get("8");
			
			if (returnFlag == 1) {
				// 资格校验通过
				
				// 车架号
				String vehicleIdentifyNo = (String) result.get("9");
				
				// 燃料种类
				String iolType = (String) result.get("10");
				
				// 使用性质
				String useOfProperty = (String) result.get("11");
				
				// 车辆类型
				String vehicleType = (String) result.get("12");
				
				// 发动机号
				String engineNo = (String) result.get("13");
				
				// 强制报废期止
				java.sql.Date sqlDeadline = (java.sql.Date) result.get("14");
				Date deadline = new Date(sqlDeadline.getTime());
				
				// 注销日期
				java.sql.Date sqlDestroyDate = (java.sql.Date) result.get("15");
				Date destroyDate = new Date(sqlDestroyDate.getTime());
				
				// 注销类别
				String cancelReason = (String) result.get("16");
				
				// 车主
				String vehicleOwner = (String) result.get("17");
				
				// 车主身份证明号
				String vehicleOwnerIdentity = (String) result.get("18");
				
				// 排放标准
				String emissionStandard = (String) result.get("19");
				
				// 初次登记日期
				java.sql.Date sqlRegisterDate = (java.sql.Date) result.get("20");
				Date registerDate = new Date(sqlRegisterDate.getTime());
				
				// 车辆状态
				String vehicleStatus = (String) result.get("21");
				
				// 报废交售日期
				java.sql.Date sqlVehicleDate = (java.sql.Date) result.get("22");
				Date vehicleRecycleDate = new Date(sqlVehicleDate.getTime());
				
				// 报废回收证明编号
				String callbackProofNo = (String) result.get("23");
				
				// 补贴金额类别
				BigDecimal bigDecimalStandType = (BigDecimal)result.get("5");
				Double subsidiesStandardType = bigDecimalStandType.doubleValue();
				
				// 补贴金额
				BigDecimal bigDecimal = (BigDecimal)result.get("6");
				Double subsidiesMoney = bigDecimal.doubleValue();
				
				
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
				
				// 补贴金额信息
				eliminatedApply.setSubsidiesStandard(returnMsg);
				eliminatedApply.setSubsidiesMoney(bigDecimal.doubleValue());
				
				// 交售信息
				eliminatedApply.setRecycleDate(vehicleRecycleDate);
				eliminatedApply.setCallbackProofNo(callbackProofNo);
				
				// 提前报废时长
				Integer advancedScrapDays = DateUtil.getDateDifference(deadline, vehicleRecycleDate, "day");
				eliminatedApply.setAdvancedScrapDays(advancedScrapDays);
				
				// 补贴对象名称
				eliminatedApply.setBankAccountName(vehicleOwner);
				
				// 通过缓存，获取号牌种类、车辆类型、燃油种类、使用性质、车辆状态等字典类型字段的名称
				// 号牌种类
				if (StringUtil.isNotEmpty(vehiclePlateType)) {
					SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
					if (null != sysDictVehiclePlateType) {
						String vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
						eliminatedApply.setVehiclePlateType(vehiclePlateType);
						eliminatedApply.setVehiclePlateTypeName(vehiclePlateTypeName);
					}
				}
				
				// 车辆类型
				if (StringUtil.isNotEmpty(vehicleType)) {
					SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
					if (null != sysDictVehicleType) {
						String vehicleTypeName = sysDictVehicleType.getDictValue();
						eliminatedApply.setVehicleType(vehicleType);
						eliminatedApply.setVehicleTypeName(vehicleTypeName);
					}
				}
				
				// 燃油种类
				if (StringUtil.isNotEmpty(iolType)) {
					SysDict sysDictIOLType = CacheRead.getSysDictByCode("IOL_TYPE", iolType);
					if (null != sysDictIOLType) {
						String iolTypeName = sysDictIOLType.getDictValue();
						eliminatedApply.setIolType(iolType);
						eliminatedApply.setIolTypeName(iolTypeName);;
					}
				}
				
				// 使用类型
				if (StringUtil.isNotEmpty(useOfProperty)) {
					SysDict sysDictUseType = CacheRead.getSysDictByCode("USE_OF_PROPERTY", useOfProperty);
					if (null != sysDictUseType) {
						String useOfPropertyName = sysDictUseType.getDictValue();
						eliminatedApply.setUseOfProperty(useOfProperty);
						eliminatedApply.setUseOfPropertyName(useOfPropertyName);
					}
				}
				
				// 车辆状态
				if (StringUtil.isNotEmpty(vehicleStatus)) {
					SysDict sysDictVehicleStatus = CacheRead.getSysDictByCode("VEHICLE_STATUS", vehicleStatus);
					if (null != sysDictVehicleStatus) {
						String vehicleStatusName = sysDictVehicleStatus.getDictValue();
						eliminatedApply.setVehicleStatus(vehicleStatus);
						eliminatedApply.setVehicleStatusName(vehicleStatusName);
					}
				}
				
				map.put("retCode", 1);
				map.put("apply", eliminatedApply);
				
			} else {
				// 资格校验不通过或者数据库存储过程执行异常，返回错误代码和消息
				map.put("retCode", returnFlag);
				map.put("msg", returnMsg);
			}
		} else {
			map.put("retCode", -1);
			map.put("msg", "数据获取异常");
		}
		
		return map;
		
		
		// 报废信息(报废回收证明编号、交售日期)
/*		VehicleRecycle vehicleRecycle = vehicleRecycleService.getByNumAndType(vehiclePlateNum, vehiclePlateType);
		if (vehicleRecycle != null) {
			eliminatedApply.setRecycleDate(vehicleRecycle.getRecycleDate());
			eliminatedApply.setCallbackProofNo(vehicleRecycle.getCallbackProofNo());
		} else {
			// 获取不到数据，或者车辆已经受理录入过，无法再次受理。
			return null;
		}
		
		// 从受理表查询，如果受理过，则无法继续受理。
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_eliminated_apply t where t.apply_time is not null ");
		sb.append("and t.vehicle_plate_num = '").append(vehiclePlateNum).append("' ");
		sb.append("and t.vehicle_plate_type = '").append(vehiclePlateType).append("'");
		List count = eliminatedApplyDao.getListBySql(sb.toString());
		if (count != null && count.size() > 0) {
			return null;
		}
		
		// 传入号牌号码、号牌种类从交警接口获取车辆数据
		//String callName = "{?=call PKG_TEST.test_msg(?)}";
		String callName = "{call PKG_VEHICLES.p_get_jiaojin_vehicle(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
		//Date date = new Date();
		//DateUtil.format(date, DateUtil.TIMESTAMPS_PATTERN_1);
		String vehicleIdentifyNo = (String) result.get(0).get("3");
		
		// 燃料种类
		String iolType = (String) result.get(0).get("4");
		
		// 使用性质
		String useOfProperty = (String) result.get(0).get("5");
		
		// 车辆类型
		String vehicleType = (String) result.get(0).get("6");
		
		// 发动机号
		String engineNo = (String) result.get(0).get("7");
		
		// 强制报废期止
		java.sql.Date sqlDeadline = (java.sql.Date) result.get(0).get("8");
		Date deadline = new Date(sqlDeadline.getTime());
		
		// 注销日期
		java.sql.Date sqlDestroyDate = (java.sql.Date) result.get(0).get("9");
		Date destroyDate = new Date(sqlDestroyDate.getTime());
		
		// 注销类别
		String cancelReason = (String) result.get(0).get("10");
		
		// 车主
		String vehicleOwner = (String) result.get(0).get("11");
		
		// 车主身份证明号
		String vehicleOwnerIdentity = (String) result.get(0).get("12");
		
		// 排放标准
		String emissionStandard = (String) result.get(0).get("13");
		
		// 初次登记日期
		java.sql.Date sqlRegisterDate = (java.sql.Date) result.get(0).get("14");
		Date registerDate = new Date(sqlRegisterDate.getTime());
		
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
		
		// 提前报废时长
		Integer advancedScrapDays = DateUtil.getDateDifference(deadline, vehicleRecycle.getRecycleDate(), "day");
		eliminatedApply.setAdvancedScrapDays(advancedScrapDays);
		
		// 补贴对象名称
		eliminatedApply.setBankAccountName(vehicleOwner);
		
		// 通过缓存，获取号牌种类、车辆类型、燃油种类、使用性质、车辆状态等字典类型字段的名称
		// 号牌种类
		if (StringUtil.isNotEmpty(vehiclePlateType)) {
			SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
			if (null != sysDictVehiclePlateType) {
				String vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
				eliminatedApply.setVehiclePlateType(vehiclePlateType);
				eliminatedApply.setVehiclePlateTypeName(vehiclePlateTypeName);
			}
		}
		
		// 车辆类型
		if (StringUtil.isNotEmpty(vehicleType)) {
			SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
			if (null != sysDictVehicleType) {
				String vehicleTypeName = sysDictVehicleType.getDictValue();
				eliminatedApply.setVehicleType(vehicleType);
				eliminatedApply.setVehicleTypeName(vehicleTypeName);
			}
		}
		
		// 燃油种类
		if (StringUtil.isNotEmpty(iolType)) {
			SysDict sysDictIOLType = CacheRead.getSysDictByCode("IOL_TYPE", iolType);
			if (null != sysDictIOLType) {
				String iolTypeName = sysDictIOLType.getDictValue();
				eliminatedApply.setIolType(iolType);
				eliminatedApply.setIolTypeName(iolTypeName);;
			}
		}
		
		// 使用类型
		if (StringUtil.isNotEmpty(useOfProperty)) {
			SysDict sysDictUseType = CacheRead.getSysDictByCode("USE_OF_PROPERTY", useOfProperty);
			if (null != sysDictUseType) {
				String useOfPropertyName = sysDictUseType.getDictValue();
				eliminatedApply.setUseOfProperty(useOfProperty);
				eliminatedApply.setUseOfPropertyName(useOfPropertyName);
			}
		}
		
		// 车辆状态
		if (StringUtil.isNotEmpty(vehicleStatus)) {
			SysDict sysDictVehicleStatus = CacheRead.getSysDictByCode("VEHICLE_STATUS", vehicleStatus);
			if (null != sysDictVehicleStatus) {
				String vehicleStatusName = sysDictVehicleStatus.getDictValue();
				eliminatedApply.setVehicleStatus(vehicleStatus);
				eliminatedApply.setVehicleStatusName(vehicleStatusName);
			}
		}
		
		// 判断车辆补贴资格和补贴金额，暂时定位固定值
		eliminatedApply.setSubsidiesMoney(1000d);
		eliminatedApply.setSubsidiesStandard(eliminatedApply.getVehicleTypeName()+":补贴金额--"+eliminatedApply.getSubsidiesMoney());*/

		//return eliminatedApply;
	}

	@Override
	public boolean saveConfirm(Integer id, String signedApplyFiles) throws Exception {
		// 更新受理确认时间、业务流水记录
		EliminatedApply apply = this.getById(id);
		if (null != apply) {
			// 增加受理确认表附件记录
			if (StringUtil.isNotEmpty(signedApplyFiles)) {
				String[] callbackProofs = signedApplyFiles.split(",");
				for (int i = 0 ; i < callbackProofs.length ; i ++) {
					String fileType = callbackProofs[i].substring(callbackProofs[i].lastIndexOf(".")+1);
					Attachment file = new Attachment();
					file.setApplyNo(apply.getApplyNo());
					file.setName("签字确认的受理表"+(i+1));
					file.setFileType(fileType);
					file.setBussinessType("2");
					file.setFilePath(callbackProofs[i]);
					file.setType("QRSLB");
					file.setStatus("1");
					file.setUploadUser("admin");
					file.setUploadUserCode("admin");
					file.setUploadTime(new Date());
					file.setUpdateTime(new Date());
					file.setVerifyCode("1");
					
					Serializable fileId = attachmentDao.save(file);
					if (null == fileId) {
						return false;
					}
				}
				
			}
			
			apply.setApplyConfirmTime(new Date()); // 受理确认时间
			apply.setLastUpdateTimeDate(new Date()); // 业务最近更新时间
			apply.setUpdateTime(new Date());  // 数据更新时间
			apply.setLastUpdateUser("管理员"); // 处理人
			apply.setLastUpdatUserCode("admin"); // 处理人代码
			
			// 新增业务流水记录
			ActionLog actionLog = new ActionLog();
			actionLog.setActionName("受理确认");
			actionLog.setActionResult("受理成功");
			actionLog.setActionTime(new Date());
			actionLog.setActionUser("admin");
			actionLog.setActionUserCode("admin");
			actionLog.setApplyNo(apply.getApplyNo());
			actionLog.setVerifyCode("1");
			actionLog.setUpdateTime(new Date());
			actionLog.setCurrentPost("CKSLG");
			Serializable logId = actionLogDao.save(actionLog);
			if (null != logId) {
				// 流水记录表插入成功，则更新业务受理表；否则，回滚。
				apply.setPrePost("CKSLG"); // 更新上一岗位是窗口受理岗
				apply.setCurrentPost("KJCSG"); // 更新下一岗位为会计初审岗，流程跳转。
				
				Map<String, Object> map = this.updateById(id, apply);
				if (map.get("isSuccess").equals(true)) {
					// 更新成功，报废车辆信息表受理状态更改为已受理
					String vehiclePlateNum = apply.getVehiclePlateNum();
					String vehiclePlateType = apply.getVehiclePlateType();
					VehicleRecycle vehicle = vehicleRecycleService.getByNumAndType(vehiclePlateNum, vehiclePlateType);
					if (null != vehicle) {
						vehicle.setStatus("1"); // 更新受理状态为已受理
						vehicleRecycleService.update(vehicle);
					} else {
						return false;
					}
				} else {
					return false;
				}
				
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		// 更新确认受理表文件位置到正式路径
		
		// 
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
		
		// 更新业务最近更新时间
		eliminatedApply.setLastUpdateTimeDate(new Date());
		EliminatedApply updatedApply = (EliminatedApply) this.update(id, eliminatedApply);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != updatedApply) {
			map.put("isSuccess", true);
			map.put("id", updatedApply.getId());
			map.put("applyNo", updatedApply.getApplyNo());
			map.put("archiveBoxNo", updatedApply.getArchiveBoxNo());
			map.put("archivedInnerNo", updatedApply.getArchivedInnerNo());
			map.put("msg", "受理单更新成功！");
		} else {
			map.put("isSuccess", false);
			map.put("msg", "受理单保存失败！");
		}
		
		return map;
	}

	/**
	 * 
	 * TODO 根据sql语句查询受理单.
	 * @see com.jst.vesms.service.EliminatedApplyService#getPageBySql(com.jst.common.utils.page.Page, java.lang.String)
	 */
	@Override
	public Page<EliminatedApply> getPageBySql(Page<EliminatedApply> page,
			String sql) throws Exception {
		
		if (StringUtil.isEmpty(sql)) {
			return this.getPageExtra(page);
		} else {
			List list = eliminatedApplyDao.getListBySql(sql);
			if (null != list && list.size() > 0) {
				page.setTotalCount(list.size());
				page.setResult(list);
			}
		}
		
		return page;
	}

	/**
	 * 
	 * TODO 查询受理未确认的受理单.
	 * @see com.jst.vesms.service.EliminatedApplyService#filterNoConfirm(com.jst.common.utils.page.Page)
	 */
	@Override
	public Page filterNoConfirm(Page page) throws Exception {
		
		Page<EliminatedApply> filterPage = new Page<EliminatedApply>();
		List<EliminatedApply> list = page.getResult();
		for (int i = 0 ; i < list.size() ; i ++) {
			EliminatedApply apply = list.get(i);
			if (apply.getApplyConfirmTime() == null) {
				filterPage.getResult().add(apply);
				filterPage.setTotalCount(filterPage.getResult().size());
			}
		}
		
		return filterPage;
	}
	
	/**
	 * 
	 * TODO 通过Id获取业务流水表记录.
	 * @see com.jst.vesms.service.EliminatedCheckService#getActionLogList(java.lang.Integer)
	 */
	@Override
	public List<ActionLog> getActionLogList(Integer id) throws Exception {
		List list = new ArrayList<ActionLog>();
		EliminatedApply apply = this.getById(id);
		if (null != apply) {
			// 按照处理时间顺序排列
			list = actionLogDao.getByPropertys(new String[]{"applyNo"}, new Object[]{apply.getApplyNo()}, "1=1 order by actionTime asc");
		}
		return list;
	}

	/**
	 * 
	 * TODO 调用档案盒生成存储过程.
	 * @see com.jst.vesms.service.EliminatedApplyService#saveArchive(java.io.Serializable)
	 */
	@Override
	public Map<String, Object> saveArchive(Integer id) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		EliminatedApply apply = (EliminatedApply) this.get(id);
		
		if (apply != null) {
			// 档案盒编号,调用存储过程创建档案盒号
			Map<Integer, Object> inParamsBox = new HashMap<Integer, Object>();
			Map<Integer, Integer> outParamsBox = new HashMap<Integer, Integer>();
			inParamsBox.put(1, "admin");
			inParamsBox.put(2, "admin");
			inParamsBox.put(3, apply.getApplyNo());
			
			outParamsBox.put(4, OracleTypes.VARCHAR); // 档案盒编号
			outParamsBox.put(5, OracleTypes.VARCHAR); // 盒内编号
			outParamsBox.put(6, OracleTypes.VARCHAR); // 是否满了,0,未满;1,满了
			outParamsBox.put(7, OracleTypes.VARCHAR); // 返回信息
			
			List<Map<String, Object>> callArchiveRes = callDao.call("{call PKG_APPLY.p_apply_create_archiveno(?,?,?,?,?,?,?)}", inParamsBox, outParamsBox, "procedure");
			
			if (callArchiveRes != null && callArchiveRes.size() > 0) {
				Map<String, Object> archiveResMap = callArchiveRes.get(0);
				if (archiveResMap != null) {
					String isOk = (String) archiveResMap.get("7");
					String archiveBoxNo = (String) archiveResMap.get("4");
					String archiveBoxInnerNo = (String) archiveResMap.get("5");
					String ifEnd = (String) archiveResMap.get("6");
					if ("ok".equals(isOk)) {
						// 档案盒号生成成功
						map.put("isSuccess", true);
						map.put("id", id);
						map.put("applyNo", apply.getApplyNo());
						map.put("archiveBoxNo", archiveBoxNo);
						map.put("archivedInnerNo", archiveBoxInnerNo);
						map.put("ifEnd", ifEnd);
						map.put("msg", isOk);
					} else {
						map.put("isSuccess", false);
						map.put("id", id);
						map.put("applyNo", apply.getApplyNo());
						map.put("msg", isOk);
					}
				}
			}
		} else {
			throw new Exception("受理单不存在，请检查！");
		}
		return map;
	}

	// 保存受理单附件表
	@Override
	public Map<String, Object> saveAttachments(String applyNo,
			String callbackProofFile, String vehicleCancelProofFiles,
			String bankCardFiles, String vehicleOwnerProofFiles,
			String agentProxyFiles, String agentProofFiles,
			String noFinanceProvideFiles, String openAccPromitFiles)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isSuccess = true;
		
		// 报废回收证明
		if (StringUtil.isNotEmpty(callbackProofFile)) {
			String[] callbackProofs = callbackProofFile.split(",");
			for (int i = 0 ; i < callbackProofs.length ; i ++) {
				String fileType = callbackProofs[i].substring(callbackProofs[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("报废回收证明"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(callbackProofs[i]);
				file.setType("JDCHSZM");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 机动车注销证明
		if (StringUtil.isNotEmpty(vehicleCancelProofFiles)) {
			String[] vehicleRegisterProofs = vehicleCancelProofFiles.split(",");
			for (int i = 0 ; i < vehicleRegisterProofs.length ; i ++) {
				String fileType = vehicleRegisterProofs[i].substring(vehicleRegisterProofs[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("机动车注销证明"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleRegisterProofs[i]);
				file.setType("JDCZXZM");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 银行卡
		if (StringUtil.isNotEmpty(bankCardFiles)) {
			String[] vehicleLicenses = bankCardFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("银行卡"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("YHK");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 车主身份证明
		if (StringUtil.isNotEmpty(vehicleOwnerProofFiles)) {
			String[] vehicleLicenses = vehicleOwnerProofFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("车主身份证明"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("ZCSFZM");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
				
		// 非财政供养单位证明
		if (StringUtil.isNotEmpty(noFinanceProvideFiles)) {
			String[] vehicleLicenses = noFinanceProvideFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("非财政供养单位证明"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("FCZGYZM");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 开户许可证
		if (StringUtil.isNotEmpty(openAccPromitFiles)) {
			String[] vehicleLicenses = openAccPromitFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("开户许可证"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("KHXKZ");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 代理委托书
		if (StringUtil.isNotEmpty(agentProxyFiles)) {
			String[] vehicleLicenses = agentProxyFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("代理委托书"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("DLWTS");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		// 代理人身份证
		if (StringUtil.isNotEmpty(agentProofFiles)) {
			String[] vehicleLicenses = agentProofFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setApplyNo(applyNo);
				file.setName("代理人身份证"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("2");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("DLRSFZ");
				file.setStatus("1");
				file.setUploadUser("admin");
				file.setUploadUserCode("admin");
				file.setUploadTime(new Date());
				file.setUpdateTime(new Date());
				file.setUpdateTime(new Date());
				file.setVerifyCode("1");
				
				Serializable id = attachmentDao.save(file);
				if (null == id) {
					isSuccess = false;
				}
			}
			
		}
		
		map.put("isSave", isSuccess);
		return map;
	}

	@Override
	public List<Attachment> getAttachments(String type, String applyNo)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_attachment t ");
		sb.append("where 1 = 1 and t.type = '").append(type).append("' ");
		sb.append("and t.apply_no = '").append(applyNo).append("' ");
		sb.append("and t.status = '1' ");
		List<Attachment> list = attachmentDao.getListBySql(sb.toString());
		return list;
	}

	@Override
	public String getAppointmentInfo(String appointmentNo) throws Exception {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*, decode(c.apply_time, null, '未受理', '已受理') apply_status from t_appointment_vehicle a ");
		sb.append(" left join t_appointment b on a.appointment_no = b.appointment_no ");
		sb.append(" left join t_eliminated_apply c on a.vehicle_plate_num = c.vehicle_plate_num ");
		sb.append(" and a.vehicle_plate_type = c.vehicle_plate_type ");
		sb.append(" where b.appointment_no = '").append(appointmentNo).append("' ");
		
		List<Object[]> list = eliminatedApplyDao.executeSql(sb.toString());
		if (null != list && list.size() > 0) {
			for (int i = 0 ; i < list.size() ; i ++) {
				JSONObject jsonObject = new JSONObject();
				Object[] result = list.get(i);
				// 循环获取数据，构造json字符串
				jsonObject.put("vehiclePlateNum", result[2]);
				jsonObject.put("vehiclePlateType", result[3]);
				jsonObject.put("vehiclePlateTypeName", CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", result[3].toString()).getDictValue());
				
				jsonObject.put("bankCode", result[8]);
				jsonObject.put("bankName", result[9]);
				jsonObject.put("bankAccount", result[10]);
				
				// 判断受理状态
				jsonObject.put("applyStatus", result[11]);
				
				jsonArray.add(jsonObject);
			}
			json.put("total", list.size());
			json.put("list", jsonArray.toString());
		} else {
			return null;
		} 
		
		return json.toString();
	}

	@Override
	public Map<String, Object> checkHasAppointed(Integer id) throws Exception {
		boolean hasAppointed = false;
		Map<String, Object> map = new HashMap<String, Object>();
		EliminatedApply model = this.getById(id);
		if (null != model) {
			// 查询当前车辆是否有预约号
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.* from t_appointment_vehicle a ");
			sb.append(" inner join t_appointment b on a.appointment_no = b.appointment_no ");
			sb.append(" where a.vehicle_plate_num = '").append(model.getVehiclePlateNum()).append("' ");
			sb.append(" and a.vehicle_plate_type = '").append(model.getVehiclePlateType()).append("' ");
			sb.append(" and b.state = '1'");
			
			List<AppointmentVehicle> list = appointmentVehicleDao.getListBySql(sb.toString());
			if (null != list && list.size() > 0) {
				hasAppointed = true;
				map.put("appointmentNo", list.get(0).getAppointmentNo());
			} else {
				hasAppointed = false;
			}
		}
		map.put("hasAppointed", hasAppointed);
		return map;
	}

	@Override
	public Map<String, Object> verifyVehicle(String vehiclePlateNum,
			String vehiclePlateType, String vehicleIdentifyNo, String type)
			throws Exception {
		String callName = "{call PKG_VEHICLES.p_vehicle_verify_for_neibu(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1,  vehiclePlateNum);
		inParams.put(2,  vehiclePlateType);
		inParams.put(3,  vehicleIdentifyNo);
		inParams.put(4,  type);
		
		outParams.put(5,  OracleTypes.NUMBER); // 补贴类别
		outParams.put(6,  OracleTypes.NUMBER); // 淘汰补贴金额
		outParams.put(7,  OracleTypes.INTEGER); // 校验标志
		outParams.put(8,  OracleTypes.VARCHAR); // 校验备注
		outParams.put(9,  OracleTypes.VARCHAR); // 车架号
		outParams.put(10,  OracleTypes.VARCHAR); // 燃料种类
		outParams.put(11,  OracleTypes.VARCHAR); // 使用性质
		outParams.put(12,  OracleTypes.VARCHAR); // 车辆类型
		outParams.put(13,  OracleTypes.VARCHAR); // 发动机型号
		outParams.put(14,  OracleTypes.DATE); // 强制报废期止
		outParams.put(15,  OracleTypes.DATE); // 注销日期
		outParams.put(16,  OracleTypes.VARCHAR); // 注销类别
		outParams.put(17,  OracleTypes.VARCHAR); // 车主
		outParams.put(18,  OracleTypes.VARCHAR); // 车主身份证明号
		outParams.put(19,  OracleTypes.VARCHAR); // 排放阶段
		outParams.put(20,  OracleTypes.DATE); // 初次登记日期
		outParams.put(21,  OracleTypes.VARCHAR); // 车辆状态
		outParams.put(22,  OracleTypes.DATE); // 报废交售日期
		outParams.put(23,  OracleTypes.VARCHAR); // 回收证明编号
		
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		for (Map<String, Object> map : result) {
			System.out.println(map.get("5"));   // 补贴类别
			System.out.println(map.get("6"));   // 淘汰补贴金额
			System.out.println(map.get("7"));   // 校验标志
			System.out.println(map.get("8"));   // 校验备注
			System.out.println(map.get("9"));    // 车架号
			System.out.println(map.get("10"));    // 燃料种类
			System.out.println(map.get("11"));    // 使用性质
			System.out.println(map.get("12"));    // 车辆类型
			System.out.println(map.get("13"));    // 发动机号
			System.out.println(map.get("14"));    // 强制报废期止
			System.out.println(map.get("15"));    // 注销日期
			System.out.println(map.get("16"));   // 注销类别
			System.out.println(map.get("17"));   // 车主
			System.out.println(map.get("18"));   // 车主身份证明号
			System.out.println(map.get("19"));   // 排放标准
			System.out.println(map.get("20"));   // 初次登记日期
			System.out.println(map.get("21"));   // 车辆状态
			System.out.println(map.get("22"));   // 报废交售日期
			System.out.println(map.get("23"));   // 回收证明编号
		}
		
		if (null != result && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getJiaoJingVehicle(String vehiclePlateNum,
			String vehiclePlateType) throws Exception {
		Map<String, Object> vehicleMap = new HashMap<String, Object>();
		String callName = "{call PKG_VEHICLES.p_get_jiaojin_vehicle_lyq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1,  vehiclePlateNum);
		inParams.put(2,  vehiclePlateType);
		
		outParams.put(3,  OracleTypes.VARCHAR); // 车架号
		outParams.put(4,  OracleTypes.VARCHAR); // 燃料种类
		outParams.put(5,  OracleTypes.VARCHAR); // 使用性质
		outParams.put(6,  OracleTypes.VARCHAR); // 车辆类型
		outParams.put(7,  OracleTypes.VARCHAR); // 发动机型号
		outParams.put(8,  OracleTypes.DATE); // 强制报废期止
		outParams.put(9,  OracleTypes.DATE); // 注销日期
		outParams.put(10,  OracleTypes.VARCHAR); // 注销类别
		outParams.put(11,  OracleTypes.VARCHAR); // 车主
		outParams.put(12,  OracleTypes.VARCHAR); // 车主身份证明号
		outParams.put(13,  OracleTypes.VARCHAR); // 排放阶段
		outParams.put(14,  OracleTypes.DATE); // 初次登记日期
		outParams.put(15,  OracleTypes.VARCHAR); // 车辆状态
		
		outParams.put(16,  OracleTypes.INTEGER); // 返回标识
		outParams.put(17,  OracleTypes.VARCHAR); // 返回信息
		
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
		
		if (null != result && result.size() > 0) {
			// 存储过程调用成功
			// 返回标识
			Integer retFlag = (Integer) result.get(0).get("16");
			
			// 返回信息
			String msg = (String) result.get(0).get("17");
			
			if (retFlag == 1) {
				EliminatedApply eliminatedApply = new EliminatedApply();
				// 车架号
				String vehicleIdentifyNo = (String) result.get(0).get("3");
				
				// 燃料种类
				String iolType = (String) result.get(0).get("4");
				
				// 使用性质
				String useOfProperty = (String) result.get(0).get("5");
				
				// 车辆类型
				String vehicleType = (String) result.get(0).get("6");
				
				// 发动机号
				String engineNo = (String) result.get(0).get("7");
				
				// 强制报废期止
				java.sql.Date sqlDeadline = (java.sql.Date) result.get(0).get("8");
				Date deadline = new Date(sqlDeadline.getTime());
				
				// 注销日期
				java.sql.Date sqlDestroyDate = (java.sql.Date) result.get(0).get("9");
				Date destroyDate = (sqlDestroyDate != null) ? new Date(sqlDestroyDate.getTime()) : new Date(); 
				
				// 注销类别
				String cancelReason = (result.get(0).get("10") != null) ? (String) result.get(0).get("10") : "";
				
				// 车主
				String vehicleOwner = (String) result.get(0).get("11");
				
				// 车主身份证明号
				//String vehicleOwnerIdentity = (String) result.get(0).get("12");
				String vehicleOwnerIdentity = (result.get(0).get("12") != null) ? (String) result.get(0).get("12") : "";
				
				// 排放标准
				String emissionStandard = (String) result.get(0).get("13");
				
				// 初次登记日期
				java.sql.Date sqlRegisterDate = (java.sql.Date) result.get(0).get("14");
				Date registerDate = new Date(sqlRegisterDate.getTime());
				
				// 车辆状态
				String vehicleStatus = (String) result.get(0).get("15");
				
				// 报废交售日期
				Date vehicleRecycleDate = null;
				
				// 报废回收证明编号
				String callbackProofNo = null;
				
				VehicleRecycle vehicleRecycle = vehicleRecycleService.getByNumAndType(vehiclePlateNum, vehiclePlateType);
				if (vehicleRecycle != null) {
					vehicleRecycleDate = vehicleRecycle.getRecycleDate();
					callbackProofNo = vehicleRecycle.getCallbackProofNo();
				} else {
					// 获取不到数据，或者车辆已经受理录入过，无法再次受理。
					// 报废交售日期
					vehicleRecycleDate = DateUtil.parse("2017-07-10", DateUtil.DATE_PATTERN_2);
					
					// 报废回收证明编号
					callbackProofNo = "HS-730000-1323-20140710-20";
				}
				
				
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
				
				// 交售信息
				eliminatedApply.setRecycleDate(vehicleRecycleDate);
				eliminatedApply.setCallbackProofNo(callbackProofNo);
				
				// 提前报废时长
				Integer advancedScrapDays = DateUtil.getDateDifference(deadline, vehicleRecycleDate, "day");
				eliminatedApply.setAdvancedScrapDays(advancedScrapDays);
				
				// 补贴对象名称
				eliminatedApply.setBankAccountName(vehicleOwner);
				
				// 通过缓存，获取号牌种类、车辆类型、燃油种类、使用性质、车辆状态等字典类型字段的名称
				// 号牌种类
				if (StringUtil.isNotEmpty(vehiclePlateType)) {
					SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
					if (null != sysDictVehiclePlateType) {
						String vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
						eliminatedApply.setVehiclePlateType(vehiclePlateType);
						eliminatedApply.setVehiclePlateTypeName(vehiclePlateTypeName);
					}
				}
				
				// 车辆类型
				if (StringUtil.isNotEmpty(vehicleType)) {
					SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
					if (null != sysDictVehicleType) {
						String vehicleTypeName = sysDictVehicleType.getDictValue();
						eliminatedApply.setVehicleType(vehicleType);
						eliminatedApply.setVehicleTypeName(vehicleTypeName);
					}
				}
				
				// 燃油种类
				if (StringUtil.isNotEmpty(iolType)) {
					SysDict sysDictIOLType = CacheRead.getSysDictByCode("IOL_TYPE", iolType);
					if (null != sysDictIOLType) {
						String iolTypeName = sysDictIOLType.getDictValue();
						eliminatedApply.setIolType(iolType);
						eliminatedApply.setIolTypeName(iolTypeName);;
					}
				}
				
				// 使用类型
				if (StringUtil.isNotEmpty(useOfProperty)) {
					SysDict sysDictUseType = CacheRead.getSysDictByCode("USE_OF_PROPERTY", useOfProperty);
					if (null != sysDictUseType) {
						String useOfPropertyName = sysDictUseType.getDictValue();
						eliminatedApply.setUseOfProperty(useOfProperty);
						eliminatedApply.setUseOfPropertyName(useOfPropertyName);
					}
				}
				
				// 车辆状态
				if (StringUtil.isNotEmpty(vehicleStatus)) {
					SysDict sysDictVehicleStatus = CacheRead.getSysDictByCode("VEHICLE_STATUS", vehicleStatus);
					if (null != sysDictVehicleStatus) {
						String vehicleStatusName = sysDictVehicleStatus.getDictValue();
						eliminatedApply.setVehicleStatus(vehicleStatus);
						eliminatedApply.setVehicleStatusName(vehicleStatusName);
					}
				}
				
				// 补贴金额信息
				// 判断车辆补贴资格和补贴金额，暂时定位固定值
				eliminatedApply.setSubsidiesMoney(1000d);
				eliminatedApply.setSubsidiesStandard(eliminatedApply.getVehicleTypeName() + ":补贴金额--" + eliminatedApply.getSubsidiesMoney());
				
				vehicleMap.put("retCode", 1);
				vehicleMap.put("apply", eliminatedApply);
			} else {
				// 获取失败
				vehicleMap.put("retCode", retFlag);
				vehicleMap.put("msg", msg);
			}
			
			return vehicleMap;
		}
		return null;
	}
	
}