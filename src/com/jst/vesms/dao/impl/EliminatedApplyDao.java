
package com.jst.vesms.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.model.EliminatedApply;

@Repository("eliminatedApplyDao")
public class EliminatedApplyDao extends HibernateBaseDAO<EliminatedApply>
		implements IEliminatedApplyDao {

	@Resource
	private SessionFactory sessionFactory;
	
	private static final String modelName = EliminatedApply.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

	@Override
	public List<Object[]> executeSql(String sql) throws Exception {
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		
		List list = query.list();
		
		return list;
	}
	
	
	
}

