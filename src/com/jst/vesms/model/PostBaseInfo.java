package com.jst.vesms.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jst.common.model.BaseModel;

/**
 * <p>Title:岗位对照表</p>
 * <p>Description: 描述</p>
 * <p>Copyright: 版权</p>
 * <p>Company: 公司</p>
 * @author 作者
 * @version 版本号
 */
@Entity
@Table(name="T_POST_BASE_INFO")
public class PostBaseInfo extends BaseModel implements Serializable {
	// 主键ID
	private Integer id;
	
	// 岗位编号
	private String postCode;
	
	// 岗位名称
	private String postName;
	
	// 上一级岗位
	private String prePost;
	
	// 下一级岗位
	private String nextPost;
	
	// 备注
	private String note;
	
	@SequenceGenerator(name = "generator",sequenceName = "SEQ_POST_BASE_INFO_ID", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 3, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "POST_CODE", unique = true, nullable = false, length = 20)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "POST_NAME", unique = false, nullable = false, length = 30)
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name = "PRE_POST", unique = false, nullable = true, length = 20)
	public String getPrePost() {
		return prePost;
	}

	public void setPrePost(String prePost) {
		this.prePost = prePost;
	}
	
	@Column(name = "NEXT_POST", unique = false, nullable = true, length = 20)
	public String getNextPost() {
		return nextPost;
	}

	public void setNextPost(String nextPost) {
		this.nextPost = nextPost;
	}

	@Column(name = "NOTE", unique = false, nullable = true, length = 4000)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PostBaseInfo(Integer id, String postCode, String postName,
			String prePost, String nextPost, String note
			) {
		super();
		this.id = id;
		this.postCode = postCode;
		this.postName = postName;
		this.prePost = prePost;
		this.nextPost = nextPost;
		this.note = note;
	}
	
	public PostBaseInfo() {
	}
	
}
