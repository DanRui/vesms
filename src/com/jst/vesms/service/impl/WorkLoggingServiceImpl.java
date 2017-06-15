
package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.dao.IWorkLoggingDao;
import com.jst.vesms.model.PostBaseInfo;
import com.jst.vesms.model.WorkLogging;
import com.jst.vesms.service.WorkLoggingService;

@Service("workLoggingServiceImpl")
public class WorkLoggingServiceImpl extends BaseServiceImpl implements WorkLoggingService {

	@Resource(name="postBaseInfoDao")
	private IPostBaseInfoDao postBaseInfoDao;
	
	@Resource(name="workLoggingDao")
	private IWorkLoggingDao workLoggingDao;
	
	
	@Override
	public String getActionPost() throws Exception {
		// 获取岗位信息表中的岗位列表
		JSONArray jsonArray = new JSONArray();
		List<PostBaseInfo> postList = postBaseInfoDao.getAllList();
		for (PostBaseInfo post : postList) {
			JSONObject json = new JSONObject();
			json.put("value", post.getPostName());
			json.put("code", post.getPostCode());
			jsonArray.add(json);
		}
		return jsonArray.toString();
	}

	@Override
	public BaseDAO getHibernateBaseDAO() {
		
		return workLoggingDao;
	}

	@Override
	public Page<WorkLogging> getWorkLoggingPage(String sql, List paramList, Page page) throws Exception {
		List list = workLoggingDao.getListBySql(sql);
		if (null != list && list.size() > 0) {
			page.setTotalCount(list.size());
			page.setResult(list);
		}
		
		return page;
	}

}

