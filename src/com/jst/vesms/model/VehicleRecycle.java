
package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.jst.common.model.BaseModel;

/**
 * <p>Title:标题</p>
 * <p>Description: 描述</p>
 * <p>Copyright: 版权</p>
 * <p>Company: 公司</p>
 * @author 作者
 * @version 版本号
 */
@Entity
@Table(name="T_VEHICLE_RECYCLE")
public class VehicleRecycle extends BaseModel implements Serializable {
	
	// 主键ID
	private Integer id;
	
	// 号牌号码
	private String vehiclePlateNum;
	
	// 号牌种类Code
	private String vehiclePlateType;
	
	// 号牌种类名称
	private String vehiclePlateTypeName;

	// 车辆类型Code
	private String vehicleType;

	// 车辆类型名称
	private String vehicleTypeName;
	
	// 厂牌型号
	private String vehicleModelNo;
	
	// 车架号
	private String vehicleIdentifyNo;
	
	// 发动机型号
	private String engineNo;
	
	// 使用性质Code
	private String useOfProperty;

	// 使用性质名称
	private String useOfPropertyName;
	
	// 初次登记日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registerDate;
	
	// 燃油类型Code
	private String iolType;

	// 燃料类型名称
	private String iolTypeName;
	
	// 排放标准
	private String emissionStandard;
	
	// 总质量
	@NumberFormat(style = Style.CURRENCY)
	private Double totalWeight;
	
	// 核定载客数
	private Integer vehicleNumPeople;
	
	// 强制报废期止
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deadline;
	
	// 车辆状态Code
	private String vehicleStatus;
	
	// 车辆状态名称
	private String vehicleStatusName;
	
	// 车主
	private String vehicleOwner;
	
	// 车主身份证明号
	private String vehicleOwnerIdentity;
	
	// 交售日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recycleDate;
	
	// 报废回收证明编号
	private String callbackProofNo;
	
	// 报废回收证明文件路径
	private String callbackProofFile;
	
	// 录入人姓名
	private String inputUserName;
	
	// 录入人代码
	private String inputUserCode;
	
	// 录入时间
	private Date inputTime;
	
	// 受理状态（0，未受理；1，已受理）
	private String status;
	
	// 校验码
	private String verifyCode;
	
	// 更新时间
	private Date updateTime;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_VEHICLE_RECYCLE_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "VEHICLE_PLATE_NUM", unique = false, nullable = false, length = 50)
	public String getVehiclePlateNum() {
		return vehiclePlateNum;
	}

	public void setVehiclePlateNum(String vehiclePlateNum) {
		this.vehiclePlateNum = vehiclePlateNum;
	}

	@Column(name = "VEHICLE_PLATE_TYPE", unique = false, nullable = false, length = 20)
	public String getVehiclePlateType() {
		return vehiclePlateType;
	}

	public void setVehiclePlateType(String vehiclePlateType) {
		this.vehiclePlateType = vehiclePlateType;
	}
	
	@Transient
	public String getVehiclePlateTypeName() {
		return vehiclePlateTypeName;
	}

	public void setVehiclePlateTypeName(String vehiclePlateTypeName) {
		this.vehiclePlateTypeName = vehiclePlateTypeName;
	}

	@Column(name = "VEHICLE_TYPE", unique = false, nullable = false, length = 20)
	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Transient
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	
	@Column(name = "VEHICLE_MODEL_NO", unique = false, nullable = true, length = 50)
	public String getVehicleModelNo() {
		return vehicleModelNo;
	}

	public void setVehicleModelNo(String vehicleModelNo) {
		this.vehicleModelNo = vehicleModelNo;
	}

	@Column(name = "VEHICLE_IDENTIFY_NO", unique = true, nullable = false, length = 50)
	public String getVehicleIdentifyNo() {
		return vehicleIdentifyNo;
	}

	public void setVehicleIdentifyNo(String vehicleIdentifyNo) {
		this.vehicleIdentifyNo = vehicleIdentifyNo;
	}

	@Column(name = "ENGINE_NO", unique = false, nullable = true, length = 50)
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@Column(name = "USE_OF_PROPERTY", unique = false, nullable = false, length = 20)
	public String getUseOfProperty() {
		return useOfProperty;
	}
	
	public void setUseOfProperty(String useOfProperty) {
		this.useOfProperty = useOfProperty;
	}

	@Transient
	public String getUseOfPropertyName() {
		return useOfPropertyName;
	}

	public void setUseOfPropertyName(String useOfPropertyName) {
		this.useOfPropertyName = useOfPropertyName;
	}
	
	@Column(name = "REGISTER_DATE", unique = false, nullable = false)
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(name = "IOL_TYPE", unique = false, nullable = false, length = 20)
	public String getIolType() {
		return iolType;
	}

	public void setIolType(String iolType) {
		this.iolType = iolType;
	}
	
	@Transient
	public String getIolTypeName() {
		return iolTypeName;
	}

	public void setIolTypeName(String iolTypeName) {
		this.iolTypeName = iolTypeName;
	}

	@Column(name = "EMISSION_STANDARD", unique = false, nullable = true, length = 20)
	public String getEmissionStandard() {
		return emissionStandard;
	}

	public void setEmissionStandard(String emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	@Column(name = "TOTAL_WEIGHT", unique = false, nullable = true, precision = 5, scale = 2)
	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Column(name = "VEHICLE_NUM_PEOPLE", unique = false, nullable = true, precision = 3, scale = 0)
	public Integer getVehicleNumPeople() {
		return vehicleNumPeople;
	}

	public void setVehicleNumPeople(Integer vehicleNumPeople) {
		this.vehicleNumPeople = vehicleNumPeople;
	}

	@Column(name = "DEADLINE", unique = false, nullable = false)
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Column(name = "VEHICLE_STATUS", unique = false, nullable = false, length = 20)
	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}
	
	@Transient
	public String getVehicleStatusName() {
		return vehicleStatusName;
	}

	public void setVehicleStatusName(String vehicleStatusName) {
		this.vehicleStatusName = vehicleStatusName;
	}

	@Column(name = "VEHICLE_OWNER", unique = false, nullable = false, length = 300)
	public String getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}

	@Column(name = "VEHICLE_OWNER_IDENTITY", unique = false, nullable = true, length = 30)
	public String getVehicleOwnerIdentity() {
		return vehicleOwnerIdentity;
	}

	public void setVehicleOwnerIdentity(String vehicleOwnerIdentity) {
		this.vehicleOwnerIdentity = vehicleOwnerIdentity;
	}

	@Column(name = "RECYCLE_DATE", unique = false, nullable = false)
	public Date getRecycleDate() {
		return recycleDate;
	}

	public void setRecycleDate(Date recycleDate) {
		this.recycleDate = recycleDate;
	}

	@Column(name = "CALLBACK_PROOF_NO", unique = false, nullable = false, length = 50)
	public String getCallbackProofNo() {
		return callbackProofNo;
	}

	public void setCallbackProofNo(String callbackProofNo) {
		this.callbackProofNo = callbackProofNo;
	}

	@Column(name = "CALLBACK_PROOF_FILE", unique = false, nullable = true, length = 200)
	public String getCallbackProofFile() {
		return callbackProofFile;
	}

	public void setCallbackProofFile(String callbackProofFile) {
		this.callbackProofFile = callbackProofFile;
	}

	@Column(name = "INPUT_USER_NAME", unique = false, nullable = false, length = 30)
	public String getInputUserName() {
		return inputUserName;
	}

	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}

	@Column(name = "INPUT_USER_CODE", unique = false, nullable = false, length = 30)
	public String getInputUserCode() {
		return inputUserCode;
	}

	public void setInputUserCode(String inputUserCode) {
		this.inputUserCode = inputUserCode;
	}

	@Column(name = "INPUT_TIME", unique = false, nullable = false)
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "STATUS", unique = false, nullable = false, length = 2)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "VERIFY_CODE", unique = true, nullable = false, length = 3000)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Column(name = "UPDATE_TIME", unique = false, nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public VehicleRecycle(Integer id, String vehiclePlateNum,
			String vehiclePlateType, String vehiclePlateTypeName,
			String vehicleType, String vehicleTypeName, String vehicleModelNo,
			String vehicleIdentifyNo, String engineNo, String useOfProperty,
			String useOfPropertyName, Date registerDate, String iolType,
			String iolTypeName, String emissionStandard, Double totalWeight,
			Integer vehicleNumPeople, Date deadline, String vehicleStatus,
			String vehicleStatusName, String vehicleOwner,
			String vehicleOwnerIdentity, Date recycleDate,
			String callbackProofNo, String callbackProofFile,
			String inputUserName, String inputUserCode, Date inputTime,
			String status, String verifyCode, Date updateTime) {
		super();
		this.id = id;
		this.vehiclePlateNum = vehiclePlateNum;
		this.vehiclePlateType = vehiclePlateType;
		this.vehiclePlateTypeName = vehiclePlateTypeName;
		this.vehicleType = vehicleType;
		this.vehicleTypeName = vehicleTypeName;
		this.vehicleModelNo = vehicleModelNo;
		this.vehicleIdentifyNo = vehicleIdentifyNo;
		this.engineNo = engineNo;
		this.useOfProperty = useOfProperty;
		this.useOfPropertyName = useOfPropertyName;
		this.registerDate = registerDate;
		this.iolType = iolType;
		this.iolTypeName = iolTypeName;
		this.emissionStandard = emissionStandard;
		this.totalWeight = totalWeight;
		this.vehicleNumPeople = vehicleNumPeople;
		this.deadline = deadline;
		this.vehicleStatus = vehicleStatus;
		this.vehicleStatusName = vehicleStatusName;
		this.vehicleOwner = vehicleOwner;
		this.vehicleOwnerIdentity = vehicleOwnerIdentity;
		this.recycleDate = recycleDate;
		this.callbackProofNo = callbackProofNo;
		this.callbackProofFile = callbackProofFile;
		this.inputUserName = inputUserName;
		this.inputUserCode = inputUserCode;
		this.inputTime = inputTime;
		this.status = status;
		this.verifyCode = verifyCode;
		this.updateTime = updateTime;
	}
	public VehicleRecycle(){
		
	}
}

