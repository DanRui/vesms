package com.jst.test.model;

import java.io.Serializable;
import java.util.Date;

import com.jst.common.model.BaseModel;

public class ClassRecord extends BaseModel implements Serializable{
		
		private Integer id;
	 	private String inscode;			//机构编号
	 	private String insname;			//机构名称
	    private String stunum;			//学员编号
	    private String stuname;			//学员姓名
	    private String idcard;			//学员身份证号码
	    private String coachnum;		//教练编号
	    private String coachname;		//教练员姓名
	    private String carnum;			//车辆编号
	    private String carcode;		//车牌号码
	    private String simunum;			//设备编号
	    private String platnum;			//车牌号
	    private String recnum;			//电子教学日志编号
	    private String subjcode;		//课程编码
	    private String subjtype;		//课程方式
	    private String subjname;		//课程名称
	    private String part;			//培训部分吗
	    private String partname;		//培训部分名称
	    private String project;			//培训项目码
	    private String projectname;		//培训项目名称
	    private Integer classid;			//课堂ID
	    private String photo1;			//签到照片
	    private String photo2;			//随机照片
	    private String photo3;			//签退照片
	    private Date starttime;			//开始时间
	    private Date endtime;			//结束时间
	    private Short duration;			//培训学时
	    private float mileage;			//里程
	    private Double avevelocity;		//平均速度
	    private String coacmt;			//教练点评
	    private Integer total;			//累计总学时
	    private Integer part1;			//第一部分累计学时
	    private Integer part2;			//第二部分累计学时
	    private Integer part3;			//第三部分累计学时
	    private Integer part4;			//第四部分累计学时
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getInscode() {
			return inscode;
		}
		public void setInscode(String inscode) {
			this.inscode = inscode;
		}
		public String getStunum() {
			return stunum;
		}
		public void setStunum(String stunum) {
			this.stunum = stunum;
		}
		public String getCoachnum() {
			return coachnum;
		}
		public void setCoachnum(String coachnum) {
			this.coachnum = coachnum;
		}
		public String getCarnum() {
			return carnum;
		}
		public void setCarnum(String carnum) {
			this.carnum = carnum;
		}
		public String getSimunum() {
			return simunum;
		}
		public void setSimunum(String simunum) {
			this.simunum = simunum;
		}
		public String getPlatnum() {
			return platnum;
		}
		public void setPlatnum(String platnum) {
			this.platnum = platnum;
		}
		public String getRecnum() {
			return recnum;
		}
		public void setRecnum(String recnum) {
			this.recnum = recnum;
		}
		public String getSubjcode() {
			return subjcode;
		}
		public void setSubjcode(String subjcode) {
			this.subjcode = subjcode;
		}
		public Integer getClassid() {
			return classid;
		}
		public void setClassid(Integer classid) {
			this.classid = classid;
		}
		public String getPhoto1() {
			return photo1;
		}
		public void setPhoto1(String photo1) {
			this.photo1 = photo1;
		}
		public String getPhoto2() {
			return photo2;
		}
		public void setPhoto2(String photo2) {
			this.photo2 = photo2;
		}
		public String getPhoto3() {
			return photo3;
		}
		public void setPhoto3(String photo3) {
			this.photo3 = photo3;
		}
		public Date getStarttime() {
			return starttime;
		}
		public void setStarttime(Date starttime) {
			this.starttime = starttime;
		}
		public Date getEndtime() {
			return endtime;
		}
		public void setEndtime(Date endtime) {
			this.endtime = endtime;
		}
		public Short getDuration() {
			return duration;
		}
		public void setDuration(Short duration) {
			this.duration = duration;
		}
		public float getMileage() {
			return mileage;
		}
		public void setMileage(float mileage) {
			this.mileage = mileage;
		}
		public Double getAvevelocity() {
			return avevelocity;
		}
		public void setAvevelocity(Double avevelocity) {
			this.avevelocity = avevelocity;
		}
		public String getCoacmt() {
			return coacmt;
		}
		public void setCoacmt(String coacmt) {
			this.coacmt = coacmt;
		}
		public Integer getTotal() {
			return total;
		}
		public void setTotal(Integer total) {
			this.total = total;
		}
		public Integer getPart1() {
			return part1;
		}
		public void setPart1(Integer part1) {
			this.part1 = part1;
		}
		public Integer getPart2() {
			return part2;
		}
		public void setPart2(Integer part2) {
			this.part2 = part2;
		}
		public Integer getPart3() {
			return part3;
		}
		public void setPart3(Integer part3) {
			this.part3 = part3;
		}
		public Integer getPart4() {
			return part4;
		}
		public void setPart4(Integer part4) {
			this.part4 = part4;
		}
		public String getInsname() {
			return insname;
		}
		public void setInsname(String insname) {
			this.insname = insname;
		}
		public String getStuname() {
			return stuname;
		}
		public void setStuname(String stuname) {
			this.stuname = stuname;
		}
		public String getIdcard() {
			return idcard;
		}
		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}
		public String getCoachname() {
			return coachname;
		}
		public void setCoachname(String coachname) {
			this.coachname = coachname;
		}
		public String getCarcode() {
			return carcode;
		}
		public void setCarcode(String carcode) {
			this.carcode = carcode;
		}
		public String getSubjtype() {
			return subjtype;
		}
		public void setSubjtype(String subjtype) {
			this.subjtype = subjtype;
		}
		public String getSubjname() {
			return subjname;
		}
		public void setSubjname(String subjname) {
			this.subjname = subjname;
		}
		public String getPart() {
			return part;
		}
		public void setPart(String part) {
			this.part = part;
		}
		public String getPartname() {
			return partname;
		}
		public void setPartname(String partname) {
			this.partname = partname;
		}
		public String getProject() {
			return project;
		}
		public void setProject(String project) {
			this.project = project;
		}
		public String getProjectname() {
			return projectname;
		}
		public void setProjectname(String projectname) {
			this.projectname = projectname;
		}
	    
	    
	    
	    
}
