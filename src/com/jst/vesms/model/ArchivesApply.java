package com.jst.vesms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <p>Title:标题</p>
 * <p>Description: 描述</p>
 * <p>Copyright: 版权</p>
 * <p>Company: 公司</p>
 * @author 作者
 * @version 版本号
 */

public class ArchivesApply {
	
	// 主键ID
	private Integer id;
	
	// 档案盒号
	private String archiveBoxNo;
	
	// 档案盒内编号
	private String archiveBoxInnerNo;
	
	// 业务单号
	private String applyNo;
	
	// 归档状态
	private String archivedStatus;
	
	// 校验码
	private String verifyCode;
	
	// 更新时间
	private Date updateTime;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_ARCHIVES_RELATION_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ARCHIVE_BOX_NO", unique = true, nullable = false, length = 6)
	public String getArchiveBoxNo() { 
		return archiveBoxNo;
	}

	public void setArchiveBoxNo(String archiveBoxNo) {
		this.archiveBoxNo = archiveBoxNo;
	}

	@Column(name = "ARCHIVE_BOX_INNERNO", unique = false, nullable = false, length = 10)
	public String getArchiveBoxInnerNo() {
		return archiveBoxInnerNo;
	}

	public void setArchiveBoxInnerNo(String archiveBoxInnerNo) {
		this.archiveBoxInnerNo = archiveBoxInnerNo;
	}

	@Column(name = "APPLY_NO", unique = false, nullable = false, length = 20)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "ARCHIVED_STATUS", unique = false, nullable = false, length = 2)
	public String getArchivedStatus() {
		return archivedStatus;
	}

	public void setArchivedStatus(String archivedStatus) {
		this.archivedStatus = archivedStatus;
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
