
package com.jst.vesms.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.jst.vesms.constant.SysConstant;
import com.jst.vesms.dao.IAttachmentDao;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IVehicleRecycleDao;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.RecycleInfoEntity;
import com.jst.vesms.model.VehicleRecycle;
import com.jst.vesms.service.VehicleRecycleService;
import com.jst.vesms.util.PhotoUtil;

@Service("vehicleRecycleServiceImpl")
public class VehicleRecycleServiceImpl extends BaseServiceImpl implements VehicleRecycleService {

	@Resource(name="vehicleRecycleDao")
	private IVehicleRecycleDao vehicleRecycleDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Resource(name="attachmentDao")
	private IAttachmentDao attachmentDao;
	
	@Resource
	private BFGSWebServiceClient bfgsWebServiceClient;

	/*public void setVehicleRecycleDao(IVehicleRecycleDao vehicleRecycleDao) {
		this.vehicleRecycleDao = vehicleRecycleDao;
	}*/

	@Override
	public BaseDAO getHibernateBaseDAO() {
		return vehicleRecycleDao;
	}

	/**
	 * 
	 * TODO 通过回收证明编号获取报废车辆数据.
	 * @see com.jst.vesms.service.VehicleRecycleService#getVehicleRecycleByProof(java.lang.String)
	 */
	@Override
	public VehicleRecycle getVehicleRecycleByProof(String callbackProofNo)
			throws Exception {
		if (StringUtil.isEmpty(callbackProofNo)) {
			return null;
		}
		VehicleRecycle vehicleRecycle = new VehicleRecycle();
		//调用报废公司接口获取号牌号码、号牌种类、报废回收证明、交售日期
		RecycleInfoEntity recycleInfoEntity = (RecycleInfoEntity) bfgsWebServiceClient.invoke("http://hbc.szjdc.net/mobileservice.asmx", "http://hbc.szjdc.net/", "GetMobileInfo", new String[] {"gjhszm"}, new String[] {callbackProofNo});
		if (null == recycleInfoEntity) {
			return null;
		}
		
		// 报废回收证明编号
		vehicleRecycle.setCallbackProofNo(recycleInfoEntity.getHuiShouZhengMingHao());
		// 交售日期
		vehicleRecycle.setRecycleDate(DateUtil.parse(recycleInfoEntity.getJiaoShouRiQi(), "YYYY-MM-DD"));
		// 号牌号码
		vehicleRecycle.setVehiclePlateNum(recycleInfoEntity.getChePaiHaoMa());
		// 号牌种类
		String vehiclePlateColor = recycleInfoEntity.getChePaiLeiBie();
		String vehiclePlateType = SysConstant.VEHICLE_PLATE_COLOR.get(vehiclePlateColor);
		if (StringUtil.isNotEmpty(vehiclePlateType)) {
			SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
			if (null != sysDictVehiclePlateType) {
				String vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
				vehicleRecycle.setVehiclePlateType(vehiclePlateType);
				vehicleRecycle.setVehiclePlateTypeName(vehiclePlateTypeName);
			}
		}
		
		//传入号牌号码、号牌种类调用存储过程，判断车辆补贴资格
		/*String callName = "{?=call PKG_TEST.test_msg(?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(2, "Hello,Oracle,");
		outParams.put(1, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "function");
		for (Map<String, Object> map : result) {
			System.out.println(map.get("1"));
		}*/
		
		// 车辆类型
		String vehicleTypeName = recycleInfoEntity.getCheLiangLeiXing();
		if (StringUtil.isNotEmpty(vehicleTypeName)) {
			SysDict sysDictVehicleType = CacheRead.getSysDictByValue("VEHICLE_TYPE", vehicleTypeName);
			if (null != sysDictVehicleType) {
				String vehicleType = sysDictVehicleType.getDictCode();
				vehicleRecycle.setVehicleType(vehicleType);
				vehicleRecycle.setVehicleTypeName(vehicleTypeName);
			}
		}
		// 车架号
		vehicleRecycle.setVehicleIdentifyNo(recycleInfoEntity.getCheJiaHao());
		// 发动机型号
		vehicleRecycle.setEngineNo(recycleInfoEntity.getFaDongJiHao());
		// 燃油种类
		String iolTypeName = recycleInfoEntity.getRanYouLeiXing();
		if (StringUtil.isNotEmpty(iolTypeName)) {
			SysDict sysDictIOLType = CacheRead.getSysDictByValue("IOL_TYPE", iolTypeName);
			if (null != sysDictIOLType) {
				String iolType = sysDictIOLType.getDictCode();
				vehicleRecycle.setIolType(iolType);
				vehicleRecycle.setIolTypeName(iolTypeName);;
			}
		}
		// 使用类型
		String useOfPropertyName = recycleInfoEntity.getShiYongXingZhi();
		if (StringUtil.isNotEmpty(useOfPropertyName)) {
			SysDict sysDictUseType = CacheRead.getSysDictByValue("USE_OF_PROPERTY", useOfPropertyName);
			if (null != sysDictUseType) {
				String useOfProperty = sysDictUseType.getDictCode();
				vehicleRecycle.setUseOfProperty(useOfProperty);
				vehicleRecycle.setUseOfPropertyName(useOfPropertyName);
			}
		}
		// 总质量
		vehicleRecycle.setTotalWeight(recycleInfoEntity.getHuoCheZongZhiLiang()*1.0);;
		// 核定载客数
		vehicleRecycle.setVehicleNumPeople(recycleInfoEntity.getChengZuoRenShu());;
		// 初次登记日期
		vehicleRecycle.setRegisterDate(DateUtil.parse(recycleInfoEntity.getChuShiDengJiRiQi(), "YYYY-MM-DD"));
		// 强制报废期止
		vehicleRecycle.setDeadline(DateUtil.parse("2028-12-31", "YYYY-MM-DD"));
		// 车辆状态
		vehicleRecycle.setVehicleStatus("A");
		vehicleRecycle.setVehicleStatusName("正常");
		// 车主
		vehicleRecycle.setVehicleOwner(recycleInfoEntity.getCheZhu());
		// 车主身份证
		//vehicleRecycle.setVehicleOwnerIdentity("123123123");
		
		vehicleRecycle.setInputTime(new Date());
		vehicleRecycle.setInputUserName("admin");
		vehicleRecycle.setInputUserCode("admin");
		vehicleRecycle.setStatus("0");
		vehicleRecycle.setVerifyCode("1");
		vehicleRecycle.setUpdateTime(new Date());
		
		//List list = this.vehicleRecycleDao.queryForSql("select * from T");
		
		return vehicleRecycle;
	}

	@Override
	public Page<VehicleRecycle> getPageExtra(Page<VehicleRecycle> page) throws Exception {
		
		List<VehicleRecycle> vehicleList = new ArrayList<VehicleRecycle>();
		if (null != page && null != page.getResult() && !page.getResult().isEmpty()) {
			List result = page.getResult();
			for (Object obj : result) {
				VehicleRecycle vehicleRecycle = (VehicleRecycle)obj;
				// 获得号牌种类名称
				String vehiclePlateType = vehicleRecycle.getVehiclePlateType();
				if (StringUtil.isNotEmpty(vehiclePlateType)) {
					String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(vehicleRecycle.getVehiclePlateType());
					if (null == vehiclePlateTypeName) {
						SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
						if (null != sysDictVehiclePlateType) {
							vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
						}
					}
					vehicleRecycle.setVehiclePlateType(vehiclePlateType);
					vehicleRecycle.setVehiclePlateTypeName(vehiclePlateTypeName);
				}
				
				// 获得车辆类型名称
				SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleRecycle.getVehicleType());
				vehicleRecycle.setVehicleTypeName(sysDictVehicleType.getDictValue());
				
				// 获得使用性质
				SysDict sysDictUseOfProperty = CacheRead.getSysDictByCode("USE_OF_PROPERTY", vehicleRecycle.getUseOfProperty());
				vehicleRecycle.setUseOfPropertyName(sysDictUseOfProperty.getDictValue());
				
				// 获得燃油种类
				SysDict sysDictIolType = CacheRead.getSysDictByCode("IOL_TYPE", vehicleRecycle.getIolType());
				if (null != sysDictIolType) {
					vehicleRecycle.setIolTypeName(sysDictIolType.getDictValue());
				}
				
				// 解密关键数据
				String des_key = PropertyUtil.getPropertyValue("DES_KEY");
				// 解密车架号
				vehicleRecycle.setVehicleIdentifyNo(EncryptUtil.decryptDES(des_key, vehicleRecycle.getVehicleIdentifyNo()));
				
				vehicleList.add(vehicleRecycle);
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
	public Serializable save(VehicleRecycle vehicleRecycle) throws Exception {
		
		// 号牌号码
		String vehiclePlateNum = vehicleRecycle.getVehiclePlateNum();
		// 号牌种类
		String vehiclePlateType = vehicleRecycle.getVehiclePlateType();
		// 车架号
		String vehicleIdentifyNo = vehicleRecycle.getVehicleIdentifyNo();
		
		// 传入号牌号码、号牌种类、车架号从交警接口获取车辆数据
		/*String callName = "{?=call PKG_TEST.test_msg(?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(2, "Hello,Oracle,");
		outParams.put(1, OracleTypes.VARCHAR);
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "function");
		for (Map<String, Object> map : result) {
			System.out.println(map.get("1"));
		}*/
		
		// 车辆类型
		String vehicleTypeName = vehicleRecycle.getVehicleTypeName();
		vehicleRecycle.setVehicleType("B11");
		// 使用类型
		String useOfPropertyName = vehicleRecycle.getUseOfPropertyName();
		vehicleRecycle.setUseOfProperty("A");
		
		vehicleRecycle.setInputTime(new Date());
		vehicleRecycle.setInputUserName("admin");
		vehicleRecycle.setInputUserCode("admin");
		vehicleRecycle.setStatus("0");
		vehicleRecycle.setVerifyCode("1");
		vehicleRecycle.setUpdateTime(new Date());
		Serializable id = vehicleRecycleDao.save(vehicleRecycle);
		return id;
	}

	@Override
	public VehicleRecycle getById(Integer id) throws Exception {
		
		//通过Id获取对象
		VehicleRecycle vehicleRecycle = (VehicleRecycle) vehicleRecycleDao.get(id);
		
		// 通过缓存，获取号牌种类、车辆类型、燃油种类、使用性质、车辆状态等字典类型字段的名称
		// 号牌种类
		String vehiclePlateType = vehicleRecycle.getVehiclePlateType();
		if (StringUtil.isNotEmpty(vehiclePlateType)) {
			String vehiclePlateTypeName = SysConstant.VEHICLE_PALTE_TYPE.get(vehicleRecycle.getVehiclePlateType());
			if (null == vehiclePlateTypeName) {
				SysDict sysDictVehiclePlateType = CacheRead.getSysDictByCode("VEHICLE_PLATE_TYPE", vehiclePlateType);
				if (null != sysDictVehiclePlateType) {
					vehiclePlateTypeName = sysDictVehiclePlateType.getDictValue();
				}
			}
			vehicleRecycle.setVehiclePlateType(vehiclePlateType);
			vehicleRecycle.setVehiclePlateTypeName(vehiclePlateTypeName);
		}
		
		// 车辆类型
		String vehicleType = vehicleRecycle.getVehicleType();
		if (StringUtil.isNotEmpty(vehicleType)) {
			SysDict sysDictVehicleType = CacheRead.getSysDictByCode("VEHICLE_TYPE", vehicleType);
			if (null != sysDictVehicleType) {
				String vehicleTypeName = sysDictVehicleType.getDictValue();
				vehicleRecycle.setVehicleType(vehicleType);
				vehicleRecycle.setVehicleTypeName(vehicleTypeName);
			}
		}
		
		// 燃油种类
		String iolType = vehicleRecycle.getIolType();
		if (StringUtil.isNotEmpty(iolType)) {
			SysDict sysDictIOLType = CacheRead.getSysDictByCode("IOL_TYPE", iolType);
			if (null != sysDictIOLType) {
				String iolTypeName = sysDictIOLType.getDictValue();
				vehicleRecycle.setIolType(iolType);
				vehicleRecycle.setIolTypeName(iolTypeName);;
			}
		}
		
		// 使用类型
		String useOfProperty = vehicleRecycle.getUseOfProperty();
		if (StringUtil.isNotEmpty(useOfProperty)) {
			SysDict sysDictUseType = CacheRead.getSysDictByCode("USE_OF_PROPERTY", useOfProperty);
			if (null != sysDictUseType) {
				String useOfPropertyName = sysDictUseType.getDictValue();
				vehicleRecycle.setUseOfProperty(useOfProperty);
				vehicleRecycle.setUseOfPropertyName(useOfPropertyName);
			}
		}
		
		// 车辆状态
		String vehicleStatus = vehicleRecycle.getVehicleStatus();
		if (StringUtil.isNotEmpty(vehicleStatus)) {
			SysDict sysDictVehicleStatus = CacheRead.getSysDictByCode("VEHICLE_STATUS", vehicleStatus);
			if (null != sysDictVehicleStatus) {
				String vehicleStatusName = sysDictVehicleStatus.getDictValue();
				vehicleRecycle.setVehicleStatus(vehicleStatus);
				vehicleRecycle.setVehicleStatusName(vehicleStatusName);
			}
		}
		
		return vehicleRecycle;
	}

	/**
	 * 
	 * TODO 根据号牌号码、号牌种类获取，已经报废录入但未受理 的车辆数据.
	 * @see com.jst.vesms.service.VehicleRecycleService#getByNumAndType(java.lang.String, java.lang.String)
	 */
	@Override
	public VehicleRecycle getByNumAndType(String vehiclePlateNum,
			String vehiclePlateType) throws Exception {
		
		String sql = "select * from T_Vehicle_Recycle where vehicle_Plate_Num = '" + vehiclePlateNum
					 + "' and vehicle_Plate_Type = '" + vehiclePlateType + "' and status = '0'";
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(1);
		List list = vehicleRecycleDao.getListBySql(sql, null, page);
		//(new String[] {"vehiclePlateNum", "vehiclePlateType"}, new Object[] {vehiclePlateNum, vehiclePlateType}, "model.status = '0'");
		if (list != null && list.size() > 0) {
			return (VehicleRecycle) list.get(0);
		}
		
		return null;
	}

	@Override
	public Map<String, Object> saveAttachments(Integer vehicleRecycleId, String callbackProofFile,
			String vehicleRegisterProofFiles, String vehicleLicenseFiles)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isSuccess = true;
		
		// 报废回收证明
		if (StringUtil.isNotEmpty(callbackProofFile)) {
			String[] callbackProofs = callbackProofFile.split(",");
			for (int i = 0 ; i < callbackProofs.length ; i ++) {
				String fileType = callbackProofs[i].substring(callbackProofs[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setVehicleRecycleId(vehicleRecycleId);
				file.setName("报废回收证明"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("1");
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
		
		// 机动车注册登记证书
		if (StringUtil.isNotEmpty(vehicleRegisterProofFiles)) {
			String[] vehicleRegisterProofs = vehicleRegisterProofFiles.split(",");
			for (int i = 0 ; i < vehicleRegisterProofs.length ; i ++) {
				String fileType = vehicleRegisterProofs[i].substring(vehicleRegisterProofs[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setVehicleRecycleId(vehicleRecycleId);
				file.setName("机动车注册登记证书"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("1");
				file.setFilePath(vehicleRegisterProofs[i]);
				file.setType("JDCZCDJZS");
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
		
		// 行驶证
		if (StringUtil.isNotEmpty(vehicleLicenseFiles)) {
			String[] vehicleLicenses = vehicleLicenseFiles.split(",");
			for (int i = 0 ; i < vehicleLicenses.length ; i ++) {
				String fileType = vehicleLicenses[i].substring(vehicleLicenses[i].lastIndexOf(".")+1);
				Attachment file = new Attachment();
				file.setVehicleRecycleId(vehicleRecycleId);
				file.setName("行驶证"+(i+1));
				file.setFileType(fileType);
				file.setBussinessType("1");
				file.setFilePath(vehicleLicenses[i]);
				file.setType("SSZ");
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
	public List<Attachment> getAttachments(String type, Integer id)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_attachment t ");
		sb.append("where 1 = 1 and t.type = '").append(type).append("' ");
		sb.append("and t.vehicle_recycle_id = ").append(id).append(" ");
		sb.append("and t.status = '1' ");
		List<Attachment> list = attachmentDao.getListBySql(sb.toString());
		return list;
	}
	
}

