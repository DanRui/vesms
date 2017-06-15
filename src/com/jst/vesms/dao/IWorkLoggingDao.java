
package com.jst.vesms.dao;

import java.util.List;

import com.jst.common.hibernate.BaseDAO;

public interface IWorkLoggingDao extends BaseDAO<Object> {

	/**
	 * 
	 * <p>Description: 通过执行sql返回字段数组集合</p>
	 * @param sql 脚本  String
	 * @return List
	 *
	 */
	public List<Object[]> executeBySql(String sql) throws Exception;	
	
}

