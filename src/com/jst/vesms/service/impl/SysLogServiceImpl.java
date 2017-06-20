
package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jst.common.dao.ISystemLogDAO;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.service.ISystemLogService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.service.SysLogService;

public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {

	@Resource(name="systemLogService")
	private ISystemLogService systemLogService;
	
	@Resource
	private ISystemLogDAO systemLogDao;

	@Override
	public BaseDAO getHibernateBaseDAO() {
		return systemLogDao;
	}

	@Override
	public Page getPageBySql(Page paramPage, List<PropertyFilter> paramList)
			throws Exception {
		
		return systemLogService.getPage(paramPage, paramList);
	}

}

