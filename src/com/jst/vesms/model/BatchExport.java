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
/**
 * <p>Title:标题</p>
 * <p>Description: 描述</p>
 * <p>Copyright: 版权</p>
 * <p>Company: 公司</p>
 * @author 作者
 * @version 版本号
 */
@Entity
@Table(name="T_BATCH_EXPORT")
public class BatchExport extends BaseModel implements Serializable{
	// 主键id
	private Integer id;
	
	// 批次号
	private String batchNo;
	
	// 批次导出时间
	private Date exportDate;
	
	//批次导出路径
	private String exportRoute;
	
	//批次导出人名
	private String exportUser;
	
	//批次导出人代码
	private String exportUserCode;
	
	//校验码
	private String verifyCode;
	
	//更新时间
	private Date updateTime;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_BATCH_EXPORT_ID", allocationSize = 1)
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

	@Column(name = "EXPORT_DATE", unique = false, nullable = true)
	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	@Column(name = "EXPORT_ROUTE", unique = true, nullable = false, length = 200)
	public String getExportRoute() {
		return exportRoute;
	}

	public void setExportRoute(String exportRoute) {
		this.exportRoute = exportRoute;
	}

	@Column(name = "EXPORT_USER", unique = false, nullable = false, length = 20)
	public String getExportUser() {
		return exportUser;
	}

	public void setExportUser(String exportUser) {
		this.exportUser = exportUser;
	}
	
	@Column(name = "EXPORT_USER_CODE", unique = false, nullable = false, length = 200)
	public String getExportUserCode() {
		return exportUserCode;
	}

	public void setExportUserCode(String exportUserCode) {
		this.exportUserCode = exportUserCode;
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
	
}
