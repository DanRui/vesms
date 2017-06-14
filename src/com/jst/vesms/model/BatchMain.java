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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.jst.common.model.BaseModel;


/**
 * <p>Title:批次主记录</p>
 * <p>Description: 定义批次主记录的各属性与数据库批次表的映射</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
@Entity
@Table(name="T_BATCH_MAIN")
public class BatchMain extends BaseModel implements Serializable{
		// 主键ID
		private Integer id;
		
		//批次报财务序号
		private Integer toFinanceNo;
		
		// 批次号
		private String batchNo;
		
		// 批次生成时间
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date createDate;
		
		// 批次生成人
		private String createUser;
		
		// 批次生成人代码
		private String createUserCode;
		
		// 批次状态
		private String batchStatus;
		
		// 批次导出状态
		private String isExported;
		
		//最近到处时间
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date expRecentDate;
		
		//报财务状态
		private String toFinanceStatus;
		
		//报财务时间
		private Date toFinanceTime;
		
		//批次导出次数
		private Integer exportTimes;
		
		//批次拨付结果标记状态
		private String payResStatus;
		
		//拨付结果标记时间
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date payResDate;
		
		//拨付业务单数
		private Integer payBussCount;
		
		//拨付批次总金额
		@NumberFormat(style = Style.CURRENCY)
		private Double payBatchTotalAmount;
				
		// 校验码
		private String verifyCode;
		
		// 更新时间
		private Date updateTime;
		
		// 批次类型
		private String batchType;
		
		// 批次拨付说明
		private String payResRemark;
		
		@SequenceGenerator(name = "generator",sequenceName = "SEQ_BATCH_MAIN_ID", allocationSize = 1)
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
		@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
		@Column(name = "BATCH_NO", unique = true, nullable = false, length = 20)
		public String getBatchNo() {
			return batchNo;
		}

		
		public void setBatchNo(String batchNo) {
			this.batchNo = batchNo;
		}
		
		@Column(name = "CREATE_DATE", unique = false, nullable = false)
		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		
		@Column(name = "CREATE_USER", unique = false, nullable = false, length = 20)
		public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}

		@Column(name = "CREATE_USER_CODE", unique = false, nullable = false, length = 20)
		public String getCreateUserCode() {
			return createUserCode;
		}

		public void setCreateUserCode(String createUserCode) {
			this.createUserCode = createUserCode;
		}

		@Column(name = "BATCH_STATUS", unique = false, nullable = false, length = 2)
		public String getBatchStatus() {
			return batchStatus;
		}

		public void setBatchStatus(String batchStatus) {
			this.batchStatus = batchStatus;
		}

		@Column(name = "is_exported", unique = false, nullable = false, length = 20)
		public String getIsExported() {
			return isExported;
		}

		public void setIsExported(String isExported) {
			this.isExported = isExported;
		}

		@Column(name = "EXP_RECENT_DATE", unique = false, nullable = true)
		public Date getExpRecentDate() {
			return expRecentDate;
		}

		public void setExpRecentDate(Date expRecentDate) {
			this.expRecentDate = expRecentDate;
		}

		@Column(name = "TO_FINANCE_STATUS", unique = false, nullable = false, length = 2)
		public String getToFinanceStatus() {
			return toFinanceStatus;
		}

		public void setToFinanceStatus(String toFinanceStatus) {
			this.toFinanceStatus = toFinanceStatus;
		}

		@Column(name = "TO_FINANCE_TIME", unique = false, nullable = true)
		public Date getToFinanceTime() {
			return toFinanceTime;
		}

		public void setToFinanceTime(Date toFinanceTime) {
			this.toFinanceTime = toFinanceTime;
		}
		
		@Column(name = "EXPORT_TIMES", unique = false, nullable = true, length = 2)
		public Integer getExportTimes() {
			return exportTimes;
		}

		public void setExportTimes(Integer exportTimes) {
			this.exportTimes = exportTimes;
		}

		@Column(name = "PAY_RES_STATUS", unique = false, nullable = false, length = 20)
		public String getPayResStatus() {
			return payResStatus;
		}

		public void setPayResStatus(String payResStatus) {
			this.payResStatus = payResStatus;
		}

		@Column(name = "PAY_RES_DATE", unique = false, nullable = true)
		public Date getPayResDate() {
			return payResDate;
		}

		public void setPayResDate(Date payResDate) {
			this.payResDate = payResDate;
		}
		
		@Column(name = "PAY_BUSS_COUNT", unique = false, nullable = false, length = 4)
		public Integer getPayBussCount() {
			return payBussCount;
		}

		public void setPayBussCount(Integer payBussCount) {
			this.payBussCount = payBussCount;
		}
		
		@Column(name = "PAY_BATCH_TOTAL_AMOUNT", unique = false, nullable = false, length = 20)
		public Double getPayBatchTotalAmount() {
			return payBatchTotalAmount;
		}

		public void setPayBatchTotalAmount(Double payBatchTotalAmount) {
			this.payBatchTotalAmount = payBatchTotalAmount;
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

		@Column(name = "BATCH_TYPE", unique = false, nullable = false, length = 2)
		public String getBatchType() {
			return batchType;
		}

		public void setBatchType(String batchType) {
			this.batchType = batchType;
		}

		@Column(name = "PAY_RES_REMARK", unique = false, nullable = true, length = 3000)
		public String getPayResRemark() {
			return payResRemark;
		}

		public void setPayResRemark(String payResRemark) {
			this.payResRemark = payResRemark;
		}
		
		@Column(name = "TO_FINANCE_NO", unique = false, nullable = true, precision = 5, scale = 0)
		public Integer getToFinanceNo() {
			return toFinanceNo;
		}

		public void setToFinanceNo(Integer toFinanceNo) {
			this.toFinanceNo = toFinanceNo;
		}

}
