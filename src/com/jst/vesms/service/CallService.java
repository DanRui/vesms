
package com.jst.vesms.service;

import java.util.List;
import java.util.Map;

public interface CallService {
	
	/**
	 * 
	 * <p>Description: 调用存储过程，返回结果集</p>
	 * @param name 存储过程或者函数名称  String
	 * @param inParams 输出参数  Map<Integer, Object>
	 * @param outParams 输出参数  Map<Integer, Object>
	 * @param type 存储过程或者函数  String
	 * @return List<Map<String, Object>>
	 *
	 */
	public List<Map<String, Object>> call(final String name, final Map<Integer, Object> inParams,
			final Map<Integer, Integer> outParams, final String type) throws Exception;
	
}

