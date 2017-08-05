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
@Table(name="T_PAY_RESULT_IMPORT")
public class PayResultImport extends BaseModel implements Serializable{

	// 主键ID
	private Integer id;
	
	// 制表时间
	private Date makeTime;
	
	// 导入时间
	private Date importTime;

	// 记录数
	private Integer recordCountTotal;
	
	// 无效业务单数
	private Integer applyCountInvalid;
	
	// 有效业务单数
	private Integer applyCountValid;
	
	// 正常支付单数
	private Integer applyCountPayOk;
	
	// 退款单数
	private Integer applyCountPayNotok;

	// 尚未标记的单数
	private Integer applyCountWaitforSign;
	
	// 传入的文件名称
	private String fileName;
	
	// 文件路径
	private String filePath;
	
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_PAY_IMPORT_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "MAKE_TIME", unique = false, nullable = true)
	public Date getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(Date makeTime) {
		this.makeTime = makeTime;
	}

	@Column(name = "IMPORT_TIME", unique = false, nullable = true)
	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	@Column(name = "RECORD_count_total", unique = false, nullable = true,length=8)
	public Integer getRecordCountTotal() {
		return recordCountTotal;
	}

	public void setRecordCountTotal(Integer recordCountTotal) {
		this.recordCountTotal = recordCountTotal;
	}
	
	@Column(name = "APPLY_COUNT_INVALID", unique = false, nullable = true,length=8)
	public Integer getApplyCountInvalid() {
		return applyCountInvalid;
	}

	public void setApplyCountInvalid(Integer applyCountInvalid) {
		this.applyCountInvalid = applyCountInvalid;
	}

	@Column(name = "APPLY_COUNT_VALID", unique = false, nullable = true,length=8)
	public Integer getApplyCountValid() {
		return applyCountValid;
	}

	public void setApplyCountValid(Integer applyCountValid) {
		this.applyCountValid = applyCountValid;
	}

	@Column(name = "APPLY_COUNT_PAY_OK", unique = false, nullable = true,length=8)
	public Integer getApplyCountPayOk() {
		return applyCountPayOk;
	}

	public void setApplyCountPayOk(Integer applyCountPayOk) {
		this.applyCountPayOk = applyCountPayOk;
	}

	@Column(name = "APPLY_COUNT_PAY_NOTOK", unique = false, nullable = true,length=8)
	public Integer getApplyCountPayNotok() {
		return applyCountPayNotok;
	}

	public void setApplyCountPayNotok(Integer applyCountPayNotok) {
		this.applyCountPayNotok = applyCountPayNotok;
	}

	@Column(name = "APPLY_COUNT_WAITFOR_SIGN", unique = false, nullable = true,length=8)
	public Integer getApplyCountWaitforSign() {
		return applyCountWaitforSign;
	}

	public void setApplyCountWaitforSign(Integer applyCountWaitforSign) {
		this.applyCountWaitforSign = applyCountWaitforSign;
	}

	@Column(name = "FILE_NAME", unique = false, nullable = true,length=200)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_PATH", unique = false, nullable = true,length=200)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

}
