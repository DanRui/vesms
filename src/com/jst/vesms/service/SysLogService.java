
package com.jst.vesms.service;

import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.SystemLog;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;

public interface SysLogService extends BaseService {
	
	public Page getPageBySql(Page paramPage, List<PropertyFilter> paramList) throws Exception; 
	
	public String getOperateType(String type) throws Exception;
	
	public SystemLog getById(String id) throws Exception;

}

