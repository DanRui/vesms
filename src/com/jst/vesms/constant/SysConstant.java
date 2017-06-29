
package com.jst.vesms.constant;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * <p>Title:系统常量类</p>
 * <p>Description: 定义系统常量</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public final class SysConstant {
	
	// 黄牌对应----01
	private static final String VEHICLE_PLATE_COLOR_YELLOW = "黄牌"; 
	
	// 蓝牌对应----02
	private static final String VEHICLE_PLATE_COLOR_BLUE = "蓝牌";
	
	// 黑牌对应----06
	private static final String VEHICLE_PLATE_COLOR_BLACK = "黑牌";
	
	// 学牌对应----16
	private static final String VEHICLE_PLATE_COLOR_XUE = "学";
	
	// 号牌颜色与种类的对应关系
	public static final Map<String, String> VEHICLE_PLATE_COLOR = new HashMap<String, String>();
	
	// 项目中用到的号牌种类
	public static final Hashtable<String, String> VEHICLE_PALTE_TYPE = new Hashtable<String, String>();
	
	static {
		// 将键值对放入字典
		VEHICLE_PLATE_COLOR.put(VEHICLE_PLATE_COLOR_YELLOW, "01");
		VEHICLE_PLATE_COLOR.put(VEHICLE_PLATE_COLOR_BLUE, "02");
		VEHICLE_PLATE_COLOR.put(VEHICLE_PLATE_COLOR_BLACK, "06");
		VEHICLE_PLATE_COLOR.put(VEHICLE_PLATE_COLOR_XUE, "16");
		
		// 生成号牌种类Code和Value对应关系
		VEHICLE_PALTE_TYPE.put("01", "大型汽车");
		VEHICLE_PALTE_TYPE.put("02", "小型汽车");
		VEHICLE_PALTE_TYPE.put("06", "外籍汽车");
		VEHICLE_PALTE_TYPE.put("16", "教练汽车");
		
	}

}

