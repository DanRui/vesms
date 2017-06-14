
package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.vesms.dao.ISysDictDao;
import com.jst.common.model.SysDict;
import com.jst.vesms.service.SysDictService;

@Service("sysDictServiceImpl")
public class SysDictServiceImpl extends BaseServiceImpl implements
		SysDictService {
	
	@Resource(name="sysDictDao")
	private ISysDictDao sysDictDao;
	
	@Override
	public List<SysDict> getAllSysDictList() throws Exception {

		return sysDictDao.getAllList();
	}

	@Override
	public BaseDAO getHibernateBaseDAO() {

		return sysDictDao;
	}

}

