package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.jst.common.model.BaseModel;

/**
 * <p>Title:受理单实体类</p>
 * <p>Description: 受理录入信息实体类，定义相关字段属性</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 * 2017年5月24日-上午10:58:56
 */

@Entity
@Table(name="T_ELIMINATED_APPLY")
public class EliminatedApply extends BaseModel implements Serializable {
	
	// 主键ID
	private Integer id;
	
	// 是否个人或企业
	private String isPersonal;
	
	// 是否财政供养
	private String isFinancialSupport;
	
	// 是否财政供养名称
	private String isFinancialSupportName;
	
	// 申报受理单号
	private String applyNo;
	
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
	
	// 提前报废时长（天数）
	private Integer advancedScrapDays;
	
	// 车辆状态Code
	private String vehicleStatus;
	
	// 车辆状态名称
	private String vehicleStatusName;
	
	// 车主
	private String vehicleOwner;
	
	// 车主身份证明号
	private String vehicleOwnerIdentity;
	
	// 车主手机号
	private String mobile;
	
	// 补贴账户户名
	private String bankAccountName;
	
	// 补贴账户开户银行
	private String bankName;
	
	// 补贴账户银行账号
	private String bankAccountNo;
	
	// 自办或代理
	private String isProxy;
	
	// 经办人
	private String agent;
	
	// 经办人身份证号
	private String agentIdentity;
	
	// 经办人手机号
	private String agentMobileNo;
	
	// 注销日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date destroyDate;
	
	// 注销类别
	private String cancelReason;
	
	// 是否异常
	private String isFault;
	
	// 异常类型
	private String faultType;
	
	// 异常描述
	private String faultDesc;
	
	// 业务状态
	private String bussinessStatus;
	
	// 当前岗位
	private String currentPost;
	
	// 上一岗位
	private String prePost;
	
	// 业务状态说明
	private String bussinessStatusRemark;
	
	// 补贴标准说明
	private String subsidiesStandard;
	
	// 补贴金额
	@NumberFormat(style = Style.CURRENCY)
	private Double subsidiesMoney;
	
	// 业务来源
	private String bussinessDataSrc;
	
	// 是否修改过资料
	private String isModified;
	
	// 批次号
	private String batchNo;
	
	// 所属重报批次号
	private String repeatedBatchNo;
	
	// 报财务状态
	private String toFinanceStatus;
	
	// 拨付结果
	private String payResStatus;
	
	// 归档状态
	private String archivedStatus;
	
	// 档案盒编号
	private String archiveBoxNo;
	
	// 档案盒盒内编号	
	private String archivedInnerNo;
	
	// 最近更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastUpdateTimeDate;
	
	// 最近处理人
	private String lastUpdateUser;
	
	// 最近处理人代码
	private String lastUpdatUserCode;
	
	// 受理录入时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyTime;
	
	// 受理确认时间 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date applyConfirmTime;
		
	// 交售日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date recycleDate;
	
	// 报废回收证明编号
	private String callbackProofNo;
	
	// 业务结束时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	
	// 校验码
	private String verifyCode;
	
	// 数据最近更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
	// 办结状态
	private String concludeStatus;
	
	// 银行代码
	private String bankCode;
	
	// 历次报财务的批次号
	private String allBatchNos;
	
	// 历次报财务的银行资料信息
	private String allToFinBankInfos;
	
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_ELIMINATED_APPLY_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "IS_PERSONAL", unique = false, nullable = false, length = 20)
	public String getIsPersonal() {
		return isPersonal;
	}

	public void setIsPersonal(String isPersonal) {
		this.isPersonal = isPersonal;
	}

	@Column(name = "IS_FINANCIAL_SUPPORT", unique = false, nullable = false, length = 2)
	public String getIsFinancialSupport() {
		return isFinancialSupport;
	}

	public void setIsFinancialSupport(String isFinancialSupport) {
		this.isFinancialSupport = isFinancialSupport;
	}
	
	@Transient
	public String getIsFinancialSupportName() {
		return isFinancialSupportName;
	}

	public void setIsFinancialSupportName(String isFinancialSupportName) {
		this.isFinancialSupportName = isFinancialSupportName;
	}

	@Column(name = "APPLY_NO", unique = true, nullable = false, length = 20)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	@Column(name = "USE_OF_PROPERTY", unique = false, nullable = false, length = 50)
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

	@Column(name = "EMISSION_STANDARD", unique = false, nullable = false, length = 20)
	public String getEmissionStandard() {
		return emissionStandard;
	}

	public void setEmissionStandard(String emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	@Column(name = "TOTAL_WEIGHT", unique = false, nullable = true, precision = 10, scale = 0)
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

	@Column(name = "ADVANCED_SCRAP_DAYS", unique = false, nullable = false, precision = 5, scale = 0)
	public Integer getAdvancedScrapDays() {
		return advancedScrapDays;
	}

	public void setAdvancedScrapDays(Integer advancedScrapDays) {
		this.advancedScrapDays = advancedScrapDays;
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

	@Column(name = "VEHICLE_OWNER", unique = false, nullable = false, length = 50)
	public String getVehicleOwner() {
		return vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}

	@Column(name = "VEHICLE_OWNER_IDENTITY", unique = false, nullable = false, length = 30)
	public String getVehicleOwnerIdentity() {
		return vehicleOwnerIdentity;
	}

	public void setVehicleOwnerIdentity(String vehicleOwnerIdentity) {
		this.vehicleOwnerIdentity = vehicleOwnerIdentity;
	}

	@Column(name = "MOBILE", unique = false, nullable = true, length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "BANK_ACCOUNT_NAME", unique = false, nullable = false, length = 200)
	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "BANK_NAME", unique = false, nullable = false, length = 100)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "BANK_ACCOUNT_NO", unique = false, nullable = false, length = 50)
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	@Column(name = "IS_PROXY", unique = false, nullable = false, length = 2)
	public String getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}

	@Column(name = "AGENT", unique = false, nullable = false, length = 20)
	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Column(name = "AGENT_IDENTITY", unique = false, nullable = false, length = 30)
	public String getAgentIdentity() {
		return agentIdentity;
	}

	public void setAgentIdentity(String agentIdentity) {
		this.agentIdentity = agentIdentity;
	}

	@Column(name = "AGENT_MOBILE_NO", unique = false, nullable = false, length = 20)
	public String getAgentMobileNo() {
		return agentMobileNo;
	}

	public void setAgentMobileNo(String agentMobileNo) {
		this.agentMobileNo = agentMobileNo;
	}

	@Column(name = "DESTROY_DATE", unique = false, nullable = false)
	public Date getDestroyDate() {
		return destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	@Column(name = "CANCEL_REASON", unique = false, nullable = false, length=200)
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "IS_FAULT", unique = false, nullable = false, length=2)
	public String getIsFault() {
		return isFault;
	}

	public void setIsFault(String isFault) {
		this.isFault = isFault;
	}

	@Column(name = "FAULT_TYPE", unique = false, nullable = true, length=200)
	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	@Column(name = "FAULT_DESC", unique = false, nullable = true, length=2000)
	public String getFaultDesc() {
		return faultDesc;
	}

	public void setFaultDesc(String faultDesc) {
		this.faultDesc = faultDesc;
	}

	@Column(name = "BUSSINESS_STATUS", unique = false, nullable = false, length=20)
	public String getBussinessStatus() {
		return bussinessStatus;
	}

	public void setBussinessStatus(String bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}

	@Column(name = "CURRENT_POST", unique = false, nullable = false, length=20)
	public String getCurrentPost() {
		return currentPost;
	}

	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}

	@Column(name = "PRE_POST", unique = false, nullable = true, length=20)
	public String getPrePost() {
		return prePost;
	}

	public void setPrePost(String prePost) {
		this.prePost = prePost;
	}

	@Column(name = "BUSSINESS_STATUS_REMARK", unique = false, nullable = true, length=2000)
	public String getBussinessStatusRemark() {
		return bussinessStatusRemark;
	}

	public void setBussinessStatusRemark(String bussinessStatusRemark) {
		this.bussinessStatusRemark = bussinessStatusRemark;
	}

	@Column(name = "SUBSIDIES_STANDARD", unique = false, nullable = false, length=200)
	public String getSubsidiesStandard() {
		return subsidiesStandard;
	}

	public void setSubsidiesStandard(String subsidiesStandard) {
		this.subsidiesStandard = subsidiesStandard;
	}

	@Column(name = "SUBSIDIES_MONEY", unique = false, nullable = false, precision = 6, scale = 2)
	public Double getSubsidiesMoney() {
		return subsidiesMoney;
	}

	public void setSubsidiesMoney(Double subsidiesMoney) {
		this.subsidiesMoney = subsidiesMoney;
	}

	@Column(name = "BUSSINESS_DATA_SRC", unique = false, nullable = false, length=20)
	public String getBussinessDataSrc() {
		return bussinessDataSrc;
	}

	public void setBussinessDataSrc(String bussinessDataSrc) {
		this.bussinessDataSrc = bussinessDataSrc;
	}

	@Column(name = "IS_MODIFIED", unique = false, nullable = false, length=20)
	public String getIsModified() {
		return isModified;
	}

	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}

	@Column(name = "BATCH_NO", unique = false, nullable = true, length=20)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "REPEATED_BATCH_NO", unique = false, nullable = true, length=20)
	public String getRepeatedBatchNo() {
		return repeatedBatchNo;
	}

	public void setRepeatedBatchNo(String repeatedBatchNo) {
		this.repeatedBatchNo = repeatedBatchNo;
	}

	@Column(name = "TO_FINANCE_STATUS", unique = false, nullable = false, length=20)
	public String getToFinanceStatus() {
		return toFinanceStatus;
	}

	public void setToFinanceStatus(String toFinanceStatus) {
		this.toFinanceStatus = toFinanceStatus;
	}

	@Column(name = "PAY_RES_STATUS", unique = false, nullable = true, length=20)
	public String getPayResStatus() {
		return payResStatus;
	}

	public void setPayResStatus(String payResStatus) {
		this.payResStatus = payResStatus;
	}

	@Column(name = "ARCHIVED_STATUS", unique = false, nullable = false, length=20)
	public String getArchivedStatus() {
		return archivedStatus;
	}

	public void setArchivedStatus(String archivedStatus) {
		this.archivedStatus = archivedStatus;
	}

	@Column(name = "ARCHIVE_BOX_NO", unique = false, nullable = true, length=6)
	public String getArchiveBoxNo() {
		return archiveBoxNo;
	}

	public void setArchiveBoxNo(String archiveBoxNo) {
		this.archiveBoxNo = archiveBoxNo;
	}

	@Column(name = "ARCHIVED_INNER_NO", unique = false, nullable = true, length=10)
	public String getArchivedInnerNo() {
		return archivedInnerNo;
	}

	public void setArchivedInnerNo(String archivedInnerNo) {
		this.archivedInnerNo = archivedInnerNo;
	}

	@Column(name = "LAST_UPDATE_TIME", unique = false, nullable = true)
	public Date getLastUpdateTimeDate() {
		return lastUpdateTimeDate;
	}

	public void setLastUpdateTimeDate(Date lastUpdateTimeDate) {
		this.lastUpdateTimeDate = lastUpdateTimeDate;
	}

	@Column(name = "LAST_UPDATE_USER", unique = false, nullable = true, length =50)
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Column(name = "LAST_UPDATE_USER_CODE", unique = false, nullable = true, length =20)
	public String getLastUpdatUserCode() {
		return lastUpdatUserCode;
	}

	public void setLastUpdatUserCode(String lastUpdatUserCode) {
		this.lastUpdatUserCode = lastUpdatUserCode;
	}

	@Column(name = "APPLY_TIME", unique = false, nullable = false)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Column(name = "APPLY_CONFIRM_TIME", unique = false, nullable = true)
	public Date getApplyConfirmTime() {
		return applyConfirmTime;
	}

	public void setApplyConfirmTime(Date applyConfirmTime) {
		this.applyConfirmTime = applyConfirmTime;
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

	@Column(name = "END_TIME", unique = false, nullable = true)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	
	@Column(name = "CONCLUDE_STATUS", unique = false, nullable = false, length = 20)
	public String getConcludeStatus() {
		return concludeStatus;
	}
	
	public void setConcludeStatus(String concludeStatus) {
		this.concludeStatus = concludeStatus;
	}

	@Column(name = "BANK_CODE", unique = false, nullable = true, length = 20)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "ALL_BATCH_NOS", unique = false, nullable = true, length = 500)
	public String getAllBatchNos() {
		return allBatchNos;
	}

	public void setAllBatchNos(String allBatchNos) {
		this.allBatchNos = allBatchNos;
	}

	@Column(name = "ALL_TO_FINANCE_BANKINFOS", unique = false, nullable = true, length = 4000)
	public String getAllToFinBankInfos() {
		return allToFinBankInfos;
	}

	public void setAllToFinBankInfos(String allToFinBankInfos) {
		this.allToFinBankInfos = allToFinBankInfos;
	}

	public EliminatedApply(Integer id, String isPersonal,
			String isFinancialSupport, String applyNo, String vehiclePlateNum,
			String vehiclePlateType, String vehiclePlateTypeName,
			String vehicleType, String vehicleTypeName, String vehicleModelNo,
			String vehicleIdentifyNo, String engineNo, String useOfProperty,
			String useOfPropertyName, Date registerDate, String iolType,
			String iolTypeName, String emissionStandard, Double totalWeight,
			Integer vehicleNumPeople, Date deadline, Integer advancedScrapDays,
			String vehicleStatus, String vehicleStatusName,
			String vehicleOwner, String vehicleOwnerIdentity, String mobile,
			String bankAccountName, String bankName, String bankAccountNo,
			String isProxy, String agent, String agentIdentity,
			String agentMobileNo, Date destroyDate, String cancelReason,
			String isFault, String faultType, String faultDesc,
			String bussinessStatus, String currentPost,
			String prePost, String bussinessStatusRemark,
			String subsidiesStandard, Double subsidiesMoney,
			String bussinessDataSrc, String isModified, String batchNo,
			String repeatedBatchNo, String toFinanceStatus,
			String payResStatus, String archivedStatus, String archiveBoxNo,
			String archivedInnerNo, Date lastUpdateTimeDate,
			String lastUpdateUser, String lastUpdatUserCode, Date applyTime,
			Date applyConfirmTime, Date recycleDate, String callbackProofNo,
			Date endTime, String verifyCode, Date updateTime,
			String concludeStatus, String bankCode, String allBatchNos, String allToFinBankInfos) {
		super();
		this.id = id;
		this.isPersonal = isPersonal;
		this.isFinancialSupport = isFinancialSupport;
		this.applyNo = applyNo;
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
		this.advancedScrapDays = advancedScrapDays;
		this.vehicleStatus = vehicleStatus;
		this.vehicleStatusName = vehicleStatusName;
		this.vehicleOwner = vehicleOwner;
		this.vehicleOwnerIdentity = vehicleOwnerIdentity;
		this.mobile = mobile;
		this.bankAccountName = bankAccountName;
		this.bankName = bankName;
		this.bankAccountNo = bankAccountNo;
		this.isProxy = isProxy;
		this.agent = agent;
		this.agentIdentity = agentIdentity;
		this.agentMobileNo = agentMobileNo;
		this.destroyDate = destroyDate;
		this.cancelReason = cancelReason;
		this.isFault = isFault;
		this.faultType = faultType;
		this.faultDesc = faultDesc;
		this.bussinessStatus = bussinessStatus;
		this.currentPost = currentPost;
		this.prePost = prePost;
		this.bussinessStatusRemark = bussinessStatusRemark;
		this.subsidiesStandard = subsidiesStandard;
		this.subsidiesMoney = subsidiesMoney;
		this.bussinessDataSrc = bussinessDataSrc;
		this.isModified = isModified;
		this.batchNo = batchNo;
		this.repeatedBatchNo = repeatedBatchNo;
		this.toFinanceStatus = toFinanceStatus;
		this.payResStatus = payResStatus;
		this.archivedStatus = archivedStatus;
		this.archiveBoxNo = archiveBoxNo;
		this.archivedInnerNo = archivedInnerNo;
		this.lastUpdateTimeDate = lastUpdateTimeDate;
		this.lastUpdateUser = lastUpdateUser;
		this.lastUpdatUserCode = lastUpdatUserCode;
		this.applyTime = applyTime;
		this.applyConfirmTime = applyConfirmTime;
		this.recycleDate = recycleDate;
		this.callbackProofNo = callbackProofNo;
		this.endTime = endTime;
		this.verifyCode = verifyCode;
		this.updateTime = updateTime;
		this.concludeStatus = concludeStatus;
		this.bankCode = bankCode;
		this.allBatchNos = allBatchNos;
		this.allToFinBankInfos = allToFinBankInfos;
	}
	
	public EliminatedApply() {
		
	}
}

