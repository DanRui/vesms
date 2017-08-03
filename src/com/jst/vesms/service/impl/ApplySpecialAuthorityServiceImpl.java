
package com.jst.vesms.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.vesms.dao.IApplySpecialAuthorityDao;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.ApplySpecialAuthority;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.ApplySpecialAuthorityService;
import com.jst.vesms.service.EliminatedApplyService;

@Service("applySpecialAuthorityServiceImpl")
public class ApplySpecialAuthorityServiceImpl extends BaseServiceImpl implements ApplySpecialAuthorityService {

	@Resource(name="applySpecialAuthorityDao")
	private IApplySpecialAuthorityDao applySpecialAuthorityDao;
	
	@Resource(name="eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	@Override
	public BaseDAO getHibernateBaseDAO() {
		
		return applySpecialAuthorityDao;
	}

	@Override
	public Page<EliminatedApply> getPageBySql(Page<EliminatedApply> page,
			String sql) throws Exception {
		return eliminatedApplyService.getPageBySql(page, sql);
	}

	@Override
	public Page<EliminatedApply> getPageExtra(Page<EliminatedApply> page)
			throws Exception {
		return eliminatedApplyService.getPageExtra(page);
	}

	/**
	 * 
	 * TODO 新增受理单到业务授权申请表中.
	 * @see com.jst.vesms.service.ApplySpecialAuthorityService#addApplySpecial(java.lang.String)
	 */
	@Override
	public Map<String, Object> addApplySpecial(String id, String applyReason) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户Code
		@SuppressWarnings("unchecked")
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		
		boolean isSuccess = false;
		String msg = "";
		Integer applyId = Integer.parseInt(id);
		EliminatedApply apply = eliminatedApplyService.getById(applyId);
		if (null != apply) {
			// 判断是否重复授权申请
			/*if (checkValid(apply.getApplyNo())) {
				isSuccess = false;
				msg = "有重复申请的业务授权，请先处理！";
			}
			
			if (checkNotApproved(apply.getApplyNo())) {
				isSuccess = false;
				msg = "该业务授权申请已审批通过,无法重新申请！";
			}*/
			
			ApplySpecialAuthority applySpecialAuthority = new ApplySpecialAuthority();
			applySpecialAuthority.setApplyNo(apply.getApplyNo());
			
			applySpecialAuthority.setAskTime(new Date());
			applySpecialAuthority.setAskUserCode(userCode);
			applySpecialAuthority.setAskUserName(userName);
			applySpecialAuthority.setCheckStatus("0");
			applySpecialAuthority.setStatus("0");
			applySpecialAuthority.setRemark("申请原因："+applyReason);
			
			Serializable applySpecialId = applySpecialAuthorityDao.save(applySpecialAuthority);
			if (null != applySpecialId) {
				isSuccess = true;
			} else {
				isSuccess = false;
				msg = "授权申请表新增失败！";
			}
				
		} else {
			isSuccess = false;
			msg = "该业务不存在,无法申请！";
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 
	 * TODO 审核授权申请列表记录.
	 * @see com.jst.vesms.service.ApplySpecialAuthorityService#check(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> check(String ids, String checkType, String checkOpinion) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户Code
		@SuppressWarnings("unchecked")
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		
		boolean isSuccess = false;
		String msg = "";
		
		String strIds = "";
		String idString[] = ids.split(",");
		int count = 0;
		for (int i = 0; i < idString.length; i ++) {
			// 循环更新授权表数据审核状态信息
			Integer id = Integer.parseInt(idString[i]);
			ApplySpecialAuthority applySpecialAuthority = (ApplySpecialAuthority) applySpecialAuthorityDao.getById(id);
			if (null != applySpecialAuthority) {
				
				applySpecialAuthority.setCheckTime(new Date());
				applySpecialAuthority.setCheckUserCode(userCode);
				applySpecialAuthority.setCheckUserName(userName);
				applySpecialAuthority.setCheckStatus(checkType);
				
				String remark = applySpecialAuthority.getRemark();
				
				applySpecialAuthority.setRemark(remark+";审核原因："+checkOpinion);
				
				applySpecialAuthorityDao.update(applySpecialAuthority);
				count ++;
			} else {
				continue;
			}
		}
		if (count == idString.length) {
			isSuccess = true;
		} else {
			isSuccess = false;
			msg = "受理单授权审核失败";
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	@Override
	public EliminatedApply getApplyById(String applyNo) throws Exception {
		List list = eliminatedApplyService.getListByPorperty("applyNo", applyNo, null);
		if (null != list && list.size() > 0) {
			return (EliminatedApply) list.get(0);
		}
		return null;
	}

	@Override
	public List<ActionLog> getActionLogList(Integer id) throws Exception {
		
		return eliminatedApplyService.getActionLogList(id);
	}

	@Override
	public List<Attachment> getAttachments(String type, String applyNo)
			throws Exception {
		
		return eliminatedApplyService.getAttachments(type, applyNo);
	}

	/**
	 * 
	 * 根据受理单号检查是否有有效的授权申请.
	 * @see com.jst.vesms.service.ApplySpecialAuthorityService#checkValid(java.lang.String)
	 */
	@Override
	public boolean checkValid(String applyNo) throws Exception {
		
		boolean hasRepeatedApply = false;
		String sql = "select count(*) from t_apply_special_authority where apply_no = '" + applyNo + "' and status = '0' and check_status = '0' ";
		List list = applySpecialAuthorityDao.getTableList(sql, null);
		if (null != list && list.size() > 0) {
			BigDecimal bigDecimal = (BigDecimal)list.get(0);;
			if (bigDecimal.intValue() > 0) {
				hasRepeatedApply = true;
			}
		}
		return hasRepeatedApply;
	}

	@Override
	public boolean checkNotApproved(String applyNo) throws Exception {
		
		boolean hasChecked = false;
		String sql = "select count(*) from t_apply_special_authority where apply_no = '" + applyNo + "' and status = '0' and check_status = '1' ";
		List list = applySpecialAuthorityDao.getTableList(sql, null);
		if (null != list && list.size() > 0) {
			BigDecimal bigDecimal = (BigDecimal)list.get(0);
			if (bigDecimal.intValue() > 0) {
				hasChecked = true;
			}
		}
		return hasChecked;
	}

}

