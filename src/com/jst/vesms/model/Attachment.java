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
@Table(name="T_ATTACHMENT")
public class Attachment extends BaseModel implements Serializable {
	
	// 主键ID
	private Integer id;
	
	// 资料类型
	private String type;
	
	// 名称
	private String name;
	
	// 资料存放路径
	private String filePath;
	
	// 所属业务类型
	private String bussinessType;
	
	// 受理单号
	private String applyNo;
	
	// 报废车辆录入表ID
	private Integer vehicleRecycleId;
	
	// 文件类型
	private String fileType;
	
	// 上传资料人
	private String uploadUser;
	
	// 上传资料人代码
	private String uploadUserCode;
	
	// 上传时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date uploadTime;
	
	// 状态
	private String status;
	
	// 校验码
	private String verifyCode;
	
	// 更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_ATTACHMENT_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TYPE", unique = false, nullable = false, length = 30)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NAME", unique = false, nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "FILEPATH", unique = false, nullable = false, length = 500)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "BUSSINESS_TYPE", unique = false, nullable = false, length = 20)
	public String getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	@Column(name = "APPLY_NO", unique = false, nullable = true, length = 20)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "VEHICLE_RECYCLE_ID", unique = false, nullable = true, precision = 8, scale = 0)
	public Integer getVehicleRecycleId() {
		return vehicleRecycleId;
	}

	public void setVehicleRecycleId(Integer vehicleRecycleId) {
		this.vehicleRecycleId = vehicleRecycleId;
	}

	@Column(name = "FILE_TYPE", unique = false, nullable = false, length = 30)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "UPLOAD_USER", unique = false, nullable = false, length = 20)
	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	@Column(name = "UPLOAD_USER_CODE", unique = false, nullable = false, length = 20)
	public String getUploadUserCode() {
		return uploadUserCode;
	}

	public void setUploadUserCode(String uploadUserCode) {
		this.uploadUserCode = uploadUserCode;
	}

	@Column(name = "UPLOAD_TIME", unique = false, nullable = false)
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
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
	
}

