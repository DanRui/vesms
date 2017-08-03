package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.jst.common.model.BaseModel;

public class PayResult  implements Serializable {
	// 主键ID
	private Integer id;
	// 报送序号
	private Integer toFinanceNo;
	// 批次号
	private String batchNo; 
	// 业务单号
	private String applyNo;
	// 号牌号码
	private String vehiclePlateNum;
	// 号牌种类
	private String vehiclePlateTypeName;
	// 车辆类型
	private String vehicleTypeName;
	// 车架号
	private String vehicleIdentifyNo;
	// 车主
	private String vehicleOwner;
	// 补贴金额（元）
	private Double subsidiesMoney;
	// 受理确认时间
	private Date applyConfirmTime;
	//重报批次号
	private String repeatedBatchNo;
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getToFinanceNo() {
		return toFinanceNo;
	}
	public void setToFinanceNo(Integer toFinanceNo) {
		this.toFinanceNo = toFinanceNo;
	}
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
	public Date getApplyConfirmTime() {
		return applyConfirmTime;
	}
	public void setApplyConfirmTime(Date applyConfirmTime) {
		this.applyConfirmTime = applyConfirmTime;
	}
	public String getRepeatedBatchNo() {
		return repeatedBatchNo;
	}
	public void setRepeatedBatchNo(String repeatedBatchNo) {
		this.repeatedBatchNo = repeatedBatchNo;
	}
	
	
	
}