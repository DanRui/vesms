
package com.jst.vesms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>Title:定义资料修正信息实体</p>
 * <p>Description: 受理资料修正实体类，包括一般资料修正和补贴账户变更</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 * 2017年6月1日-上午11:48:16
 */
public class ApplyModifyInfo {
	
	// 所属受理单Id
	private Integer id;
	
	// 修正类型，一般资料修正和补贴账户变更
	private String faultType;
	
	// 资料修正类型，字符串格式（每种类型标识逗号分割）
	private String modifyType;
	
	// 修正结果，1，结束修正；2，继续
	private String modifyResult;
	
	// 经办人
	private String agent;
	
	// 经办人身份证
	private String agentIdentity;
	
	// 经办人手机号
	private String agentMobileNo;
	
	// 补贴账户户名
	private String bankAccountName;
	
	// 补贴账户银行Code
	private String bankCode;
	
	// 补贴账户银行
	private String bankName;
	
	// 补贴账户账号
	private String bankAccountNo;
	
	// 报废回收证明材料路径
	private List<String> callbackProofFile = new ArrayList<String>();
	
	// 机动车注销证明
	private List<String> vehicleCancelProofFiles = new ArrayList<String>();
	
	// 车主身份证明
	private List<String> vehicleOwnerProofFiles = new ArrayList<String>();
	
	// 银行卡
	private List<String> bankCardFiles = new ArrayList<String>();
	
	// 代理委托书
	private List<String> agentProxyFiles = new ArrayList<String>();
	
	// 代理人身份证
	private List<String> agentProofFiles = new ArrayList<String>();
	
	// 非财政供养单位证明
	private List<String> noFinanceProvideFiles = new ArrayList<String>();
	
	// 开户许可证
	private List<String> openAccPermitFiles = new ArrayList<String>();
	
	// 签字确认的受理表
	private List<String> signedApplyFiles = new ArrayList<String>();
	
	// 补贴账户变更材料证明
	private List<String> accountChangeProofFiles = new ArrayList<String>();
	
	// 机动车注册登记证书
	private List<String> vehicleRegisterProofFiles = new ArrayList<String>();
	
	// 行驶证
	private List<String> vehicleLicenseFiles = new ArrayList<String>();

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFaultType() {
		return faultType;
	}

	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public String getModifyResult() {
		return modifyResult;
	}

	public void setModifyResult(String modifyResult) {
		this.modifyResult = modifyResult;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgentIdentity() {
		return agentIdentity;
	}

	public void setAgentIdentity(String agentIdentity) {
		this.agentIdentity = agentIdentity;
	}

	public String getAgentMobileNo() {
		return agentMobileNo;
	}

	public void setAgentMobileNo(String agentMobileNo) {
		this.agentMobileNo = agentMobileNo;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public List<String> getCallbackProofFile() {
		return callbackProofFile;
	}

	public void setCallbackProofFile(List<String> callbackProofFile) {
		this.callbackProofFile = callbackProofFile;
	}

	public List<String> getVehicleCancelProofFiles() {
		return vehicleCancelProofFiles;
	}

	public void setVehicleCancelProofFiles(List<String> vehicleCancelProofFiles) {
		this.vehicleCancelProofFiles = vehicleCancelProofFiles;
	}

	public List<String> getVehicleOwnerProofFiles() {
		return vehicleOwnerProofFiles;
	}

	public void setVehicleOwnerProofFiles(List<String> vehicleOwnerProofFiles) {
		this.vehicleOwnerProofFiles = vehicleOwnerProofFiles;
	}

	public List<String> getBankCardFiles() {
		return bankCardFiles;
	}

	public void setBankCardFiles(List<String> bankCardFiles) {
		this.bankCardFiles = bankCardFiles;
	}

	public List<String> getAgentProxyFiles() {
		return agentProxyFiles;
	}

	public void setAgentProxyFiles(List<String> agentProxyFiles) {
		this.agentProxyFiles = agentProxyFiles;
	}

	public List<String> getAgentProofFiles() {
		return agentProofFiles;
	}

	public void setAgentProofFiles(List<String> agentProofFiles) {
		this.agentProofFiles = agentProofFiles;
	}

	public List<String> getNoFinanceProvideFiles() {
		return noFinanceProvideFiles;
	}

	public void setNoFinanceProvideFiles(List<String> noFinanceProvideFiles) {
		this.noFinanceProvideFiles = noFinanceProvideFiles;
	}

	public List<String> getOpenAccPermitFiles() {
		return openAccPermitFiles;
	}

	public void setOpenAccPermitFiles(List<String> openAccPermitFiles) {
		this.openAccPermitFiles = openAccPermitFiles;
	}

	public List<String> getSignedApplyFiles() {
		return signedApplyFiles;
	}

	public void setSignedApplyFiles(List<String> signedApplyFiles) {
		this.signedApplyFiles = signedApplyFiles;
	}

	public List<String> getAccountChangeProofFiles() {
		return accountChangeProofFiles;
	}

	public void setAccountChangeProofFiles(List<String> accountChangeProofFiles) {
		this.accountChangeProofFiles = accountChangeProofFiles;
	}

	public List<String> getVehicleRegisterProofFiles() {
		return vehicleRegisterProofFiles;
	}

	public void setVehicleRegisterProofFiles(List<String> vehicleRegisterProofFiles) {
		this.vehicleRegisterProofFiles = vehicleRegisterProofFiles;
	}

	public List<String> getVehicleLicenseFiles() {
		return vehicleLicenseFiles;
	}

	public void setVehicleLicenseFiles(List<String> vehicleLicenseFiles) {
		this.vehicleLicenseFiles = vehicleLicenseFiles;
	}
	

}

