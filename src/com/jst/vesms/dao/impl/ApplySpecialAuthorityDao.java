
package com.jst.vesms.dao.impl;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IApplySpecialAuthorityDao;
import com.jst.vesms.model.ApplySpecialAuthority;

@Repository("applySpecialAuthorityDao")
public class ApplySpecialAuthorityDao extends HibernateBaseDAO<ApplySpecialAuthority> implements IApplySpecialAuthorityDao {

	private static final String modelName = ApplySpecialAuthority.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

}

