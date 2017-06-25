
package com.jst.vesms.service;

import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.WorkLogging;

public interface WorkLoggingService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 获取操作岗位列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	public String getActionPost() throws Exception;
	
	public String getActionUserList() throws Exception;
	
	public String getActionNameList() throws Exception;
	
	public String getActionResultList() throws Exception;

	/**
	 * 
	 * <p>Description: 根据查询条件，查出工作记录列表</p>
	 * @param page 分页对象  Page
	 * @param list 查询条件对象  List<PropertyFilter>
	 * @return Page
	 *
	 */
	public Page<WorkLogging> getWorkLoggingPage(String sql, List paramList, Page page) throws Exception; 
}

