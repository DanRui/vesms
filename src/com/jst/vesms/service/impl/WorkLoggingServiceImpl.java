
package com.jst.vesms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.model.SysDict;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.vesms.common.CacheRead;
import com.jst.vesms.constant.SysConstant;
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
		List<Object[]> list = workLoggingDao.getTableList(sql, page);
		long total = workLoggingDao.executeBySql(sql).size();
		List<WorkLogging> workLogs = new ArrayList<WorkLogging>();
		if (null != list && list.size() > 0) {
			page.setTotalCount(total);
			for (int i = 0 ; i < list.size() ; i ++) {
				WorkLogging workLogging = new WorkLogging();
				
				Object[] objs = list.get(i);
						
				// 设置主键序号
				BigDecimal IdBigDecimal = (BigDecimal)objs[0];
				workLogging.setId(IdBigDecimal.intValue());
				// 操作岗位
				workLogging.setPost(objs[1].toString());
				// 操作人
				// 操作人名称
				workLogging.setActionUserName(objs[2].toString());
				
				// 操作动作
				workLogging.setAction(objs[3].toString());
				// 操作结果
				workLogging.setActionResult(objs[4].toString());
				// 操作时间
				java.sql.Timestamp sqlActionTime = (java.sql.Timestamp) objs[5];
				Date actionTime = new Date(sqlActionTime.getTime());
				workLogging.setActionTime(actionTime);
				// 业务受理单号
				workLogging.setApplyNo(objs[6].toString());
				// 号牌号码
				workLogging.setVehiclePlateNum(objs[7].toString());
				// 号牌种类
				String vehiclePlateType = objs[8].toString();
				workLogging.setVehiclePlateType(vehiclePlateType);
				// 号牌种类名称
				workLogging.setVehiclePlateTypeName(SysConstant.VEHICLE_PALTE_TYPE.get(vehiclePlateType));
				
				// 排放标准
				workLogging.setEmissionStandard(objs[9].toString());
				// 燃油种类
				String iolType = objs[10].toString();
				workLogging.setIolType(iolType);
				// 燃油种类名称
				workLogging.setIolTypeName(CacheRead.getSysDictByCode("IOL_TYPE", iolType).getDictValue());
				
				// 补贴金额
				BigDecimal bigDecimal = (BigDecimal)objs[11];
				workLogging.setSubsidiesMoney(bigDecimal.doubleValue());
				// 业务当前岗位
				workLogging.setCurrentPost(objs[12].toString());
				// 业务当前状态
				workLogging.setBussinessStatus(objs[13].toString());
				// 受理表Id
				BigDecimal applyId = (BigDecimal)objs[14];
				workLogging.setApplyId(applyId.intValue());
				
				workLogs.add(workLogging);
			}
			page.setResult(workLogs);
		}
		
		return page;
	}

}

