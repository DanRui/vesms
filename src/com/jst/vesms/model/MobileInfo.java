
package com.jst.vesms.model;

import java.io.Serializable;
import java.lang.reflect.Field;

public class MobileInfo implements Serializable {
	
	// 号牌号码
	private String HPHM;
	
	// 号牌种类
	private String HPZL;
	
	// 车架号
	private String CJH;
	
	// 交售日期
	private String JSRQ;
	
	// 回收证明编号
	private String HSZMBH;

	public String getHPHM() {
		return HPHM;
	}

	public void setHPHM(String hPHM) {
		HPHM = hPHM;
	}

	public String getHPZL() {
		return HPZL;
	}

	public void setHPZL(String hPZL) {
		HPZL = hPZL;
	}

	public String getCJH() {
		return CJH;
	}

	public void setCJH(String cJH) {
		CJH = cJH;
	}

	public String getJSRQ() {
		return JSRQ;
	}

	public void setJSRQ(String jSRQ) {
		JSRQ = jSRQ;
	}

	public String getHSZMBH() {
		return HSZMBH;
	}

	public void setHSZMBH(String hSZMBH) {
		HSZMBH = hSZMBH;
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		Field[] fields = MobileInfo.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				sb.append(field.getName()).append(":").append(field.get(this)).append("\n");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} 
		return sb.toString();
	}
	
}

