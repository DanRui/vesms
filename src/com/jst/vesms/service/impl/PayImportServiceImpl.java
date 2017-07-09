package com.jst.vesms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.PayImportService;
import com.jst.vesms.dao.IPayImportDao;


@Service("payImportServiceImpl")
public class PayImportServiceImpl extends BaseServiceImpl
implements PayImportService{

	@Resource(name="payImportDao")
	private IPayImportDao payImportDao;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		// TODO Auto-generated method stub
		return payImportDao;
	}
	
}
