
package com.jst.vesms.dao;

import com.jst.common.hibernate.BaseDAO;
import com.jst.vesms.model.PostBaseInfo;

public interface IPostBaseInfoDao extends BaseDAO<PostBaseInfo> {

	public PostBaseInfo getByCode(String PostCode) throws Exception; 
	
}

