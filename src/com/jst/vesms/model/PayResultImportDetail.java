package com.jst.vesms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>Title:标题</p>
 * <p>Description: 描述</p>
 * <p>Copyright: 版权</p>
 * <p>Company: 公司</p>
 * @author 作者
 * @version 版本号
 */

@Entity
@Table(name="T_PAY_RESULT_IMPORT_DETAIL")
public class PayResultImportDetail {
	// 主键ID
	private Integer id;

	// 国库主表Id
	private Integer payImportId;
	
	// 支付单号
	private String payNo;
	
	// 原支付单号
	private String originalPayNo;
	
	// 批次文件内备注
	private String remark;
	
	// 收款人银行账号
	private String accountNo;
	
	// 收款人开户户名
	private String accountName;
	
	// 收款人开户行
	private String bankName;
	
	// 清算时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date confirmTime;
	
	// 支付金额
	private Integer payAmount;
	
	// 支付结果
	private String payResult;
	
	// 支付时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payTime;
	
	// 对应业务表主键Id
	private Integer applyId;
	
	// 对应业务单号
	private String applyNo;
	
	// 对应批次号
	private String batchNo;
	
	// 对应批次类型
	private String batchType;
	
	// 对应拨付结果状态
	private String payResStatus;
	
	// 国库申请单号
	private String requertNo;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_PAY_IMPORT_DETAIL_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ACCOUNT_NO", unique = false, nullable = true, length = 200)
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "ACCOUNT_NAME", unique = false, nullable = true, length = 200)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "BANK_NAME", unique = false, nullable = true, length = 200)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "PAY_AMOUNT", unique = false, nullable = true, length = 6)
	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}
	
	@Column(name = "PAY_IMPORT_ID", unique = false, nullable = false, length = 8)
	public Integer getPayImportId() {
		return payImportId;
	}

	public void setPayImportId(Integer payImportId) {
		this.payImportId = payImportId;
	}

	@Column(name = "PAY_NO", unique = false, nullable = true, length = 50)
	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	@Column(name = "ORIGINAL_PAY_NO", unique = false, nullable = true, length = 50)
	public String getOriginalPayNo() {
		return originalPayNo;
	}

	public void setOriginalPayNo(String originalPayNo) {
		this.originalPayNo = originalPayNo;
	}

	@Column(name = "CONFIRM_TIME", unique = false, nullable = true, length = 200)
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Column(name = "PAY_RESULT", unique = false, nullable = true, length = 200)
	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	@Column(name = "PAY_TIME", unique = false, nullable = true)
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Column(name = "APPLY_ID", unique = false, nullable = false, length = 8)
	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	
	@Column(name = "APPLY_NO", unique = false, nullable = true, length = 200)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "BATCH_NO", unique = false, nullable = true, length = 20)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "BATCH_TYPE", unique = false, nullable = true, length = 20)
	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	@Column(name = "PAY_RES_STATUS", unique = false, nullable = true, length = 20)
	public String getPayResStatus() {
		return payResStatus;
	}

	public void setPayResStatus(String payResStatus) {
		this.payResStatus = payResStatus;
	}

	@Column(name = "REQUEST_NO", unique = false, nullable = true, length = 20)
	public String getRequertNo() {
		return requertNo;
	}

	public void setRequertNo(String requertNo) {
		this.requertNo = requertNo;
	}
	
	
}
