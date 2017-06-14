package com.jst.vesms.dao.impl;


import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IArchivesDao;
import com.jst.vesms.model.Archives;

@Repository("archivesDao")
public class ArchivesDao extends HibernateBaseDAO<Archives> 
implements IArchivesDao{

private static final String modelName = Archives.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}
	
	


}
