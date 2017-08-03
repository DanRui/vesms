
package com.jst.vesms.service;

import java.util.List;
import java.util.Map;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;

public interface ApplySpecialAuthorityService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 通过sql条件查询page对象。</p>
	 * @param page 传入的page对象  Page
	 * @param sql sql语句  String
	 * @return Page
	 *
	 */
	public Page<EliminatedApply> getPageBySql(Page<EliminatedApply> page, String sql) throws Exception;
	
	/**
	 * 
	 * <p>Description: 获得包含号牌种类、车辆类型、使用性质、燃油种类等字典类型的名称page对象。</p>
	 * @param page 传入的page对象  Page
	 * @return Page
	 *
	 */
	public Page<EliminatedApply> getPageExtra(Page<EliminatedApply> page) throws Exception;
	
	public Map<String,Object> addApplySpecial(String id, String applyReason) throws Exception; 
	
	public Map<String, Object> check(String ids, String checkType, String checkOpinion) throws Exception;
	
	public EliminatedApply getApplyById(String applyNo) throws Exception;
	
	public List<ActionLog> getActionLogList(Integer id) throws Exception;
	
	public List<Attachment> getAttachments(String type, String applyNo) throws Exception;
	
	public boolean checkValid(String applyNo) throws Exception;
	
	public boolean checkNotApproved(String applyNo) throws Exception;
}

