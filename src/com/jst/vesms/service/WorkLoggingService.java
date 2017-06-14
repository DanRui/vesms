
package com.jst.vesms.service;

import com.jst.common.service.BaseService;

public interface WorkLoggingService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 获取操作岗位列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	public String getActionPost() throws Exception;

}

