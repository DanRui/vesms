package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name="T_BATCH_DETAIL")
public class BatchDetail extends BaseModel implements Serializable{
	// 主键ID
	private Integer id;
	
	// 业务单号
	private String applyNo;
	
	// 批次号
	private String batchNo;
	
	// 初次批次生成时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date firstJoinDate;
	
	// 拨付结果状态
	private String payStatus;
	
	// 拨付结果标记时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date payDate;
	
	// 拨付结果描述
	private String remark;
	
	// 校验码
	private String verifyCode;
	
	// 更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
	// 批次类型
	private String batchType;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_BATCH_APPLY_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "APPLY_NO", unique = false, nullable = false, length = 20)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "BATCH_NO", unique = false, nullable = false, length = 20)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "FIRST_JOIN_DATE", unique = false, nullable = true)
	public Date getFirstJoinDate() {
		return firstJoinDate;
	}

	public void setFirstJoinDate(Date firstJoinDate) {
		this.firstJoinDate = firstJoinDate;
	}

	@Column(name = "REMARK", unique = false, nullable = false, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "PAY_STATUS", unique = false, nullable = false, length = 20)
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "PAY_DATE", unique = false, nullable = true)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "BATCH_TYPE", unique = false, nullable = true, length = 10)
	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	
}
