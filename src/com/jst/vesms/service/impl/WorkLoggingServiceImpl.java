
package com.jst.vesms.service.impl;

import javax.annotation.Resource;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.service.WorkLoggingService;

public class WorkLoggingServiceImpl extends BaseServiceImpl implements WorkLoggingService {

	@Resource(name="postBaseInfoDao")
	private IPostBaseInfoDao postBaseInfoDao;
	
	@Override
	public String getActionPost() throws Exception {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDAO getHibernateBaseDAO() {
		
		// TODO Auto-generated method stub
		return null;
	}

}

