
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.ISysDictDao;
import com.jst.common.model.SysDict;

@Repository("sysDictDao")
public class SysDictDao extends HibernateBaseDAO<SysDict> implements ISysDictDao {

	private static final String modelName = SysDict.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}

