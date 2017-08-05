package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

public class BatchApplyDetail implements Serializable {
	// 业务Id
	private Integer applyId;
	//批次号
	private String batchNo;
	//受理单号
	private String applyNo;
	// 车主
	private String vehicleOwner;
	// 补贴金额（元）
	private Double subsidiesMoney;
	// 号牌号码
	private String vehiclePlateNum;
	// 号牌种类
	private String vehiclePlateTypeName;
	// 批次报财务状态
	private String toFinanceStatus;
	// 车辆类型
	private String vehicleTypeName;
	//车架号
	private String vehicleIdentifyNo;
	// 受理时间
	private Date applyConfirmTime; 
	// 拨付状态
	private String payStatus;
	// 银行名称
	private String bankName;
	// 银行户名
	private String bankAccountName;
	// 银行号
	private String bankAccountNo;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getVehicleOwner() {
		return vehicleOwner;
	}
	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}
	public Double getSubsidiesMoney() {
		return subsidiesMoney;
	}
	public void setSubsidiesMoney(Double subsidiesMoney) {
		this.subsidiesMoney = subsidiesMoney;
	}
	public String getVehiclePlateNum() {
		return vehiclePlateNum;
	}
	public void setVehiclePlateNum(String vehiclePlateNum) {
		this.vehiclePlateNum = vehiclePlateNum;
	}
	public String getVehiclePlateTypeName() {
		return vehiclePlateTypeName;
	}
	public void setVehiclePlateTypeName(String vehiclePlateTypeName) {
		this.vehiclePlateTypeName = vehiclePlateTypeName;
	}
	public String getToFinanceStatus() {
		return toFinanceStatus;
	}
	public void setToFinanceStatus(String toFinanceStatus) {
		this.toFinanceStatus = toFinanceStatus;
	}
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public String getVehicleIdentifyNo() {
		return vehicleIdentifyNo;
	}
	public void setVehicleIdentifyNo(String vehicleIdentifyNo) {
		this.vehicleIdentifyNo = vehicleIdentifyNo;
	}
	public Date getApplyConfirmTime() {
		return applyConfirmTime;
	}
	public void setApplyConfirmTime(Date applyConfirmTime) {
		this.applyConfirmTime = applyConfirmTime;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getApplyId() {
		return applyId;
	}
	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	
	
}
