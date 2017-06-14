
package com.jst.vesms.dao;

import java.util.List;
import java.util.Map;

public interface ICallDao {
	
	/**
	 * 
	 * <p>Description: 调用存储过程或者函数,返回多个结果集方法</p>
	 * @param name 存储过程或函数名称  String
	 * @param inParams 输入参数列表  Map<Integer, Object>
	 * @param outParams 输出参数列表  Map<Integer, Integer>
	 * @param type 存储过程还是函数 String
	 * @return List<List<Map<String, Object>>>
	 *
	 */
	public List<List<Map<String, Object>>> callForMultiRS(final String name, final Map<Integer, Object> inParams, final Map<Integer, Integer> outParams, final String type) throws Exception;
	
	/**
	 * 
	 * <p>Description: 调用函数或存储过程，返回单个结果集方法 </p>
	 * @param name 存储过程或函数名称  String
	 * @param inParams 输入参数列表  Map<Integer, Object>
	 * @param outParams 输出参数列表  Map<Integer, Integer>
	 * @param type 存储过程还是函数 String
	 * @return List<Map<String, Object>>
	 *
	 */
	public List<Map<String, Object>> call(final String name, final Map<Integer, Object> inParams, final Map<Integer, Integer> outParams, final String type) throws Exception;

}

