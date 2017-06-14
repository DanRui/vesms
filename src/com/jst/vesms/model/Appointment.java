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

@Entity
@Table(name = "T_APPOINTMENT")
public class Appointment extends BaseModel implements Serializable {

	// 主键ID
	private Integer id;
	
	// 预约号
	private String appointmentNo;
	
	// 预约日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appointmentDate;
	
	// 预约开始时间
	private String StartTime;
	
	// 预约结束时间
	private String EndTime;
	
	// 预约手机号
	private String messagePhone;
	
	// 生成日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inputTime;
	
	// 预约客户端IP
	private String ipAddress;
	
	// 校验码
	private String verifyCode;
	
	// 数据更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
	// 状态
	private String state;

	@SequenceGenerator(name = "generator",sequenceName = "SEQ_APPOINTMENT_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "APPOINTMENT_NO", unique = true, nullable = true, length = 50)
	public String getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(String appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	@Column(name = "APPOINTMENT_DATE", unique = false, nullable = true)
	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@Column(name = "START_TIME", unique = false, nullable = true, length = 10)
	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	@Column(name = "END_TIME", unique = false, nullable = true, length = 10)
	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	@Column(name = "MESSAGE_PHONE", unique = false, nullable = true, length = 50)
	public String getMessagePhone() {
		return messagePhone;
	}

	public void setMessagePhone(String messagePhone) {
		this.messagePhone = messagePhone;
	}

	@Column(name = "INPUT_TIME", unique = false, nullable = true)
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "IP_ADDRESS", unique = false, nullable = true, length = 50)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "VERIFY_CODE", unique = false, nullable = true, length = 3000)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Column(name = "UPDATE_TIME", unique = false, nullable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATE", unique = false, nullable = false, length = 1)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
