
package com.jst.vesms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jst.common.hibernate.HibernateBaseDAO;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.model.PostBaseInfo;

@Repository("postBaseInfoDao")
public class PostBaseInfoDao extends HibernateBaseDAO<PostBaseInfo> implements IPostBaseInfoDao {

private static final String modelName = PostBaseInfo.class.getName();
	
	@Override
	public String getModelName() {
		
		return modelName;
	}

	@Override
	public PostBaseInfo getByCode(String PostCode) throws Exception {
		
		List list = this.getByPorperty("postCode", PostCode, null);
		if (null != list && list.size() > 0) {
			return (PostBaseInfo) list.get(0);
		}
		return null;
	}

}

