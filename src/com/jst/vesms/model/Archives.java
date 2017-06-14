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
@Table(name="T_ARCHIVES")
public class Archives extends BaseModel implements Serializable{
		
	// 主键ID
	private Integer id;
	
	// 档案盒编号
	private String archiveBoxNo;
	
	// 档案盒生成时间
	private Date createDate;
	
	// 档案盒生成人姓名
	private String createUser;
	
	// 档案生成年月
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date archivedDate;
	
	// 档案盒内文件总数
	private Integer documentNum;
	
	// 立卷时间
	private Date filingDate;
	
	// 立卷责任人
	private String filingUser;
	
	// 保管期限
	private Date keepDate;
	
	// 备注说明
	private String remark;
	
	// 校验码
	private String verifyCode;
			
	// 更新时间
	private Date updateTime;


	@SequenceGenerator(name = "generator",sequenceName = "SEQ_ARCHIVES_ID", allocationSize = 1)
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

	@Column(name = "ARCHIVED_DATE", unique = false, nullable = false)
	public Date getArchivedDate() {
		return archivedDate;
	}

	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
	}

	@Column(name = "DOCUMENT_NUM", unique = false, nullable = true, length=8)
	public Integer getDocumentNum() {
		return documentNum;
	}

	public void setDocumentNum(Integer documentNum) {
		this.documentNum = documentNum;
	}

	@Column(name = "FILING_DATE", unique = false, nullable = true)
	public Date getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(Date filingDate) {
		this.filingDate = filingDate;
	}

	@Column(name = "FILING_USER", unique = false, nullable = true,length=20)
	public String getFilingUser() {
		return filingUser;
	}

	public void setFilingUser(String filingUser) {
		this.filingUser = filingUser;
	}

	@Column(name = "KEEP_DATE", unique = false, nullable = true)
	public Date getKeepDate() {
		return keepDate;
	}

	public void setKeepDate(Date keepDate) {
		this.keepDate = keepDate;
	}

	@Column(name = "REMARK", unique = false, nullable = true,length=2000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "VERIFY_CODE", unique = true, nullable = false,length=3000)
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
