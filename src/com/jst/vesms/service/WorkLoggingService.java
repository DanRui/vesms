
package com.jst.vesms.service;

import java.util.List;
import java.util.Map;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.WorkLogging;

public interface WorkLoggingService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 获取操作岗位列表</p>
	 * @param name description type
	 * @return String
	 *
	 */
	public String getActionPost() throws Exception;
	
	public String getActionUserList() throws Exception;
	
	public String getActionNameList() throws Exception;
	
	public String getActionResultList() throws Exception;

	/**
	 * 
	 * <p>Description: 根据查询条件，查出工作记录列表</p>
	 * @param page 分页对象  Page
	 * @param list 查询条件对象  List<PropertyFilter>
	 * @return Page
	 *
	 */
	public Page<WorkLogging> getWorkLoggingPage(String sql, List paramList, Page page) throws Exception; 
	
	/**
	 * 
	 * <p>Description: 根据条件查询出受理综合列表</p>
	 * @param list 查询条件  List<PropertyFilter>
	 * @param page 分页对象  Page
	 * @return Page
	 *
	 */
	public Page<EliminatedApply> getApplyPage(List<PropertyFilter> list, Page page) throws Exception;
	
	public EliminatedApply getApplyById(Integer id) throws Exception;
	
	public List<ActionLog> getActionLogList(Integer id) throws Exception;
	
	public List<Attachment> getAttachments(String type, String applyNo) throws Exception;
}

