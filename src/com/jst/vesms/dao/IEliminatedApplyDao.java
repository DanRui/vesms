
package com.jst.vesms.dao;

import java.util.List;

import com.jst.common.hibernate.BaseDAO;
import com.jst.vesms.model.EliminatedApply;

public interface IEliminatedApplyDao extends BaseDAO<EliminatedApply> {
	
	public List<Object[]> executeSql(String sql) throws Exception;
	
}

