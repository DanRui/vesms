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
@Table(name = "T_APPOINTMENT_VEHICLE")
public class AppointmentVehicle extends BaseModel implements Serializable {

	// 主键ID
	private Integer id;
	
	// 预约号	
	private String appointmentNo;
	
	// 号牌号码
	private String vehiclePlateNum;
	
	// 号牌种类
	private String vehiclePlateType;
	
	// 车辆类型
	private String vehiclePlate;
	
	// 车架号
	private String vehicleIdentifyNo;
	
	// 银行代码
	private String bankCode;
	
	// 银行名称
	private String bankName;
	
	// 银行账号
	private String bankAccount;
	
	// 数据更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
	// 校验码
	private String verifyCode;
	
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_APPOINTMENT_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "APPOINTMENT_NO", unique = false, nullable = true, length = 50)
	public String getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(String appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	@Column(name = "VEHICLE_PLATE_NUM", unique = false, nullable = true, length = 50)
	public String getVehiclePlateNum() {
		return vehiclePlateNum;
	}

	public void setVehiclePlateNum(String vehiclePlateNum) {
		this.vehiclePlateNum = vehiclePlateNum;
	}

	@Column(name = "VEHICLE_PLATE_TYPE", unique = false, nullable = true, length = 20)
	public String getVehiclePlateType() {
		return vehiclePlateType;
	}

	public void setVehiclePlateType(String vehiclePlateType) {
		this.vehiclePlateType = vehiclePlateType;
	}

	@Column(name = "VEHICLE_TYPE", unique = false, nullable = true, length = 20)
	public String getVehiclePlate() {
		return vehiclePlate;
	}

	public void setVehiclePlate(String vehiclePlate) {
		this.vehiclePlate = vehiclePlate;
	}

	@Column(name = "VEHICLE_IDENTIFY_NO", unique = false, nullable = true, length = 50)
	public String getVehicleIdentifyNo() {
		return vehicleIdentifyNo;
	}

	public void setVehicleIdentifyNo(String vehicleIdentifyNo) {
		this.vehicleIdentifyNo = vehicleIdentifyNo;
	}

	@Column(name = "BANK_CODE", unique = false, nullable = true, length = 50)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "BANK_NAME", unique = false, nullable = true, length = 200)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BANK_ACCOUNT", unique = false, nullable = true, length = 200)
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "UPDATE_TIME", unique = false, nullable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "VERIFY_CODE", unique = true, nullable = true, length = 50)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
}
