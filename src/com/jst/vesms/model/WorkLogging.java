
package com.jst.vesms.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class WorkLogging implements Serializable {
	
	// 列表ID
	private Integer id;
	
	// 操作岗位
	private String post;
	
	// 操作人
	private String actionUserCode;
	
	// 操作人名称
	private String actionUserName;
	
	// 操作动作
	private String action;
	
	// 操作结果
	private String actionResult;
	
	// 操作时间
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date actionTime;
	
	// 业务受理单号
	private String applyNo;
	
	// 号牌号码
	private String vehiclePlateNum;
	
	// 号牌种类
	private String vehiclePlateType;
	
	// 号牌种类名称
	private String vehiclePlateTypeName;
	
	// 排放标准
	private String emissionStandard;
	
	// 燃油种类
	private String iolType;
	
	// 燃油种类名称
	private String iolTypeName;
	
	// 补贴金额
	@NumberFormat(style = Style.CURRENCY)
	private Double subsidiesMoney;
	
	// 业务当前岗位
	private String currentPost;
	
	// 业务当前状态
	private String bussinessStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getActionUserCode() {
		return actionUserCode;
	}

	public void setActionUserCode(String actionUserCode) {
		this.actionUserCode = actionUserCode;
	}

	public String getActionUserName() {
		return actionUserName;
	}

	public void setActionUserName(String actionUserName) {
		this.actionUserName = actionUserName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionResult() {
		return actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
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

	public String getVehiclePlateType() {
		return vehiclePlateType;
	}

	public void setVehiclePlateType(String vehiclePlateType) {
		this.vehiclePlateType = vehiclePlateType;
	}

	public String getVehiclePlateTypeName() {
		return vehiclePlateTypeName;
	}

	public void setVehiclePlateTypeName(String vehiclePlateTypeName) {
		this.vehiclePlateTypeName = vehiclePlateTypeName;
	}

	public String getEmissionStandard() {
		return emissionStandard;
	}

	public void setEmissionStandard(String emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	public String getIolType() {
		return iolType;
	}

	public void setIolType(String iolType) {
		this.iolType = iolType;
	}

	public String getIolTypeName() {
		return iolTypeName;
	}

	public void setIolTypeName(String iolTypeName) {
		this.iolTypeName = iolTypeName;
	}

	public Double getSubsidiesMoney() {
		return subsidiesMoney;
	}

	public void setSubsidiesMoney(Double subsidiesMoney) {
		this.subsidiesMoney = subsidiesMoney;
	}

	public String getCurrentPost() {
		return currentPost;
	}

	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}

	public String getBussinessStatus() {
		return bussinessStatus;
	}

	public void setBussinessStatus(String bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}

	public WorkLogging(Integer id, String post, String actionUserCode,
			String actionUserName, String action, String actionResult,
			Date actionTime, String applyNo, String vehiclePlateNum,
			String vehiclePlateType, String vehiclePlateTypeName,
			String emissionStandard, String iolType, String iolTypeName,
			Double subsidiesMoney, String currentPost, String bussinessStatus) {
		super();
		this.id = id;
		this.post = post;
		this.actionUserCode = actionUserCode;
		this.actionUserName = actionUserName;
		this.action = action;
		this.actionResult = actionResult;
		this.actionTime = actionTime;
		this.applyNo = applyNo;
		this.vehiclePlateNum = vehiclePlateNum;
		this.vehiclePlateType = vehiclePlateType;
		this.vehiclePlateTypeName = vehiclePlateTypeName;
		this.emissionStandard = emissionStandard;
		this.iolType = iolType;
		this.iolTypeName = iolTypeName;
		this.subsidiesMoney = subsidiesMoney;
		this.currentPost = currentPost;
		this.bussinessStatus = bussinessStatus;
	}
	
	public WorkLogging() {}
	
}


