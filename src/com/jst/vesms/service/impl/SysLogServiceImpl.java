
package com.jst.vesms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jst.common.dao.ISystemLogDAO;
import com.jst.common.hibernate.BaseDAO;
import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.SystemLog;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.service.ISystemLogService;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
import com.jst.vesms.service.SysLogService;

@Service("sysLogServiceImpl")
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {
	
	private static final Log log = LogFactory.getLog(SysLogServiceImpl.class);

	@Resource(name="systemLogService")
	private ISystemLogService systemLogService;
	
	@Resource(name="systemLogDAO")
	private ISystemLogDAO systemLogDao;

	@Override
	public BaseDAO getHibernateBaseDAO() {
		return systemLogDao;
	}

	@Override
	public Page getPageBySql(Page paramPage, List<PropertyFilter> paramList)
			throws Exception {
		log.debug("SysLogServiceImpl getPageBySql is start");
		Page page = systemLogService.getPage(paramPage, paramList);
		log.debug("SysLogServiceImpl getPageBySql is end");
		return page;
	}

	@Override
	public String getOperateType(String type) throws Exception {
		log.debug("SysLogServiceImpl getOperateType is start");
		JSONArray array = new JSONArray();
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotEmpty(type)) {
			if ("MODULE_TYPE".equals(type)) {
				sb.append("select distinct obj_type from t_ope_log where 1=1 ");
			}
			if ("OPE_TYPE".equals(type)) {
				sb.append("select distinct ope_type from t_ope_log where 1=1 ");
			}
			@SuppressWarnings("unchecked")
			List<String> list = systemLogDao.getTableList(sb.toString(), null);
			if (null != list && list.size() > 0) {
				for (int i = 0 ; i < list.size(); i ++) {
					String str = list.get(i);
					if (StringUtil.isEmpty(str)) {
						continue;
					}
					JSONObject json = new JSONObject();
					if ("MODULE_TYPE".equals(type) || "OPE_TYPE".equals(type)) {
						json.put("code", str);
						json.put("value", str);
					}
					array.add(json);
				}
			}
			return array.toString();
		}
		return null;
	}
	
	@Override
	public SystemLog getById(String id) throws Exception {
		return (SystemLog) systemLogDao.getById(id);
	}

}

