
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

import com.jst.common.model.BaseModel;

@Entity
@Table(name="APPLY_SPECIAL_AUTHORITY")
public class ApplySpecialAuthority extends BaseModel implements Serializable {

	// 主键ID
	private Integer id;
	
	// 受理单号
	private String applyNo;
	
	// 变更补贴对象
	private String authorityType;
	
	// 申请人代码
	private String askUserCode;
	
	// 申请人名称
	private String askUserName;
	
	// 申请时间
	private Date askTime;
	
	// 审核状态
	private String checkStatus;
	
	// 审核人代码
	private String checkUserCode;
	
	// 审核人名称
	private String checkUserName;
	
	// 审核时间
	private Date checkTime;
	
	// 备注(审核原因)
	private String remark;
	
	// 校验码
	private String verifyCode;
		
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_SPECIAL_AUTHORITY_ID",allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "APPLY_NO", unique = false, nullable = true, length = 50)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "AUTHORITY_TYPE", unique = false, nullable = true, length = 50)
	public String getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}

	@Column(name = "ASK_USER_CODE", unique = false, nullable = true, length = 50)
	public String getAskUserCode() {
		return askUserCode;
	}

	public void setAskUserCode(String askUserCode) {
		this.askUserCode = askUserCode;
	}

	@Column(name = "ASK_USER_NAME", unique = false, nullable = true, length = 50)
	public String getAskUserName() {
		return askUserName;
	}

	public void setAskUserName(String askUserName) {
		this.askUserName = askUserName;
	}

	@Column(name = "ASK_TIME", unique = false, nullable = true)
	public Date getAskTime() {
		return askTime;
	}

	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}

	@Column(name = "CHECK_STATUS", unique = false, nullable = true, length = 10)
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Column(name = "CHECK_USER_CODE", unique = false, nullable = true, length = 50)
	public String getCheckUserCode() {
		return checkUserCode;
	}

	public void setCheckUserCode(String checkUserCode) {
		this.checkUserCode = checkUserCode;
	}

	@Column(name = "CHECK_USER_NAME", unique = false, nullable = true, length = 50)
	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	@Column(name = "CHECK_TIME", unique = false, nullable = true)
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name = "REMARK", unique = false, nullable = true, length = 1000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "VERIFY_CODE", unique = false, nullable = true, length = 1000)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public ApplySpecialAuthority(Integer id, String applyNo,
			String authorityType, String askUserCode, String askUserName,
			Date askTime, String checkStatus, String checkUserCode,
			String checkUserName, Date checkTime, String remark,
			String verifyCode) {
		super();
		this.id = id;
		this.applyNo = applyNo;
		this.authorityType = authorityType;
		this.askUserCode = askUserCode;
		this.askUserName = askUserName;
		this.askTime = askTime;
		this.checkStatus = checkStatus;
		this.checkUserCode = checkUserCode;
		this.checkUserName = checkUserName;
		this.checkTime = checkTime;
		this.remark = remark;
		this.verifyCode = verifyCode;
	}
	
	public ApplySpecialAuthority() {}
}

