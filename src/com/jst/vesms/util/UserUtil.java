
package com.jst.vesms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.internal.OracleTypes;

import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.impl.CallDao;

public class UserUtil {
	
	@Resource(name="callDao")
	private static ICallDao callDao = new CallDao();
	
	/**
	 * 
	 * <p>Description: 查询当前数据预警列表</p>
	 * @param name description type
	 * @return List
	 *
	 */
	public static List getNotifyList() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String callName = "{call PKG_QUERY.p_get_workdata(?)}";
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		outParams.put(1, OracleTypes.CURSOR);
		
		List<Map<String, Object>> result = callDao.call(callName, null, outParams, "procedure");
		System.out.println(result);
		
		return list;
	}
	
	public static void main(String[] args) {
		try {
			getNotifyList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

