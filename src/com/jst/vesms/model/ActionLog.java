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
@Entity
@Table(name="T_ACTION_LOG")
public class ActionLog {
	// 主键ID
	private Integer id;
	
	// 受理单号
	private String applyNo;
	
	//当前岗位
	private String currentPost;
	
	//操作名称
	private String actionName;
	
	//处理人
	private String actionUser;
	
	//处理人代码
	private String actionUserCode;
	
	//处理人部门
	private String actionUserDept;
	
	//处理人部门代码
	private String actionUserDeptCode;
	
	//处理时间
	private Date actionTime;
	
	//操作结果
	private String actionResult;
	
	//详情说明
	private String actionDetail;
	
	//备注
	private String note;
	
	// 校验码
	private String verifyCode;
		
	// 更新时间
	private Date updateTime;
	
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_ACTION_LOG_ID",allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "APPLY_NO", unique = true, nullable = false, length = 20)
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	@Column(name = "CURRENT_POST", unique = false, nullable = false, length = 20)
	public String getCurrentPost() {
		return currentPost;
	}

	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}

	@Column(name = "ACTION_NAME", unique = false, nullable = true, length = 20)
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "ACTION_USER", unique = false, nullable = false, length = 20)
	public String getActionUser() {
		return actionUser;
	}

	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}

	@Column(name = "ACTION_USER_CODE", unique = false, nullable = false, length = 20)
	public String getActionUserCode() {
		return actionUserCode;
	}

	public void setActionUserCode(String actionUserCode) {
		this.actionUserCode = actionUserCode;
	}

	@Column(name = "ACTION_USER_DEPT", unique = false, nullable = true, length = 20)
	public String getActionUserDept() {
		return actionUserDept;
	}

	public void setActionUserDept(String actionUserDept) {
		this.actionUserDept = actionUserDept;
	}

	@Column(name = "ACTION_USER_DEPT_CODE", unique = false, nullable = true, length = 100)
	public String getActionUserDeptCode() {
		return actionUserDeptCode;
	}

	public void setActionUserDeptCode(String actionUserDeptCode) {
		this.actionUserDeptCode = actionUserDeptCode;
	}

	@Column(name = "ACTION_TIME", unique = false, nullable = false)
	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	@Column(name = "ACTION_RESULT", unique = false, nullable = false, length = 20)
	public String getActionResult() {
		return actionResult;
	}

	public void setActionResult(String actionResult) {
		this.actionResult = actionResult;
	}

	@Column(name = "ACTION_DETAIL", unique = false, nullable = true, length = 4000)
	public String getActionDetail() {
		return actionDetail;
	}

	public void setActionDetail(String actionDetail) {
		this.actionDetail = actionDetail;
	}

	@Column(name = "NOTE", unique = false, nullable = true, length = 4000)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
