
package com.jst.vesms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.vesms.dao.IActionLogDao;
import com.jst.vesms.dao.IAttachmentDao;
import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.dao.IEliminatedApplyDao;
import com.jst.vesms.dao.IPostBaseInfoDao;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.ApplyModifyInfo;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.service.EliminatedApplyService;
import com.jst.vesms.service.EliminatedCheckService;
import com.jst.vesms.service.EliminatedModifyService;

@Service("eliminatedModifyServiceImpl")
public class EliminatedModifyServiceImpl extends BaseServiceImpl implements
		EliminatedModifyService {
	
	
	@Resource(name="eliminatedApplyServiceImpl")
	private EliminatedApplyService eliminatedApplyService;
	
	@Resource(name="eliminatedCheckServiceImpl")
	private EliminatedCheckService eliminatedCheckService;
	
	@Resource(name="eliminatedApplyDao")
	private IEliminatedApplyDao eliminatedApplyDao;
	
	@Resource(name="callDao")
	private ICallDao callDao;
	
	@Resource(name="postBaseInfoDao")
	private IPostBaseInfoDao postBaseInfoDao;
	
	@Resource(name="actionLogDao")
	private IActionLogDao actionLogDao;
	
	@Resource(name="attachmentDao")
	private IAttachmentDao attachmentDao;

	@Override
	public BaseDAO getHibernateBaseDAO() {

		return eliminatedApplyDao;
	}

	@Override
	public Page<EliminatedApply> getPageExtra(Page<EliminatedApply> page)
			throws Exception {
		
		return eliminatedApplyService.getPageExtra(page);
	}

	@Override
	public EliminatedApply getById(Integer id) throws Exception {
		
		return eliminatedApplyService.getById(id);
	}

	@Override
	public Map<String, Object> saveApplyInfo(ApplyModifyInfo applyModifyInfo)
			throws Exception {
		Integer applyId = applyModifyInfo.getId();
		boolean hasModifyFiles = false;
		// 修正类型
		if (applyModifyInfo.getModifyType() != null) {
			String[] modifyTypes = applyModifyInfo.getModifyType().split(",");
			for (int i = 0 ; i < modifyTypes.length ; i ++) {
				if (modifyTypes[i].startsWith("3")) {
					hasModifyFiles = true;
				}
			}
		}
		
		// 修正结果
		String modifyResult = applyModifyInfo.getModifyResult();
		
		// 拼接字符串
		String baseInfoDetails = "";
		String updateType = "1";
		
		// 更新一般资料信息，包括经办人信息、补贴账户信息等。
		if (null != applyModifyInfo.getAgent()) {
			baseInfoDetails += "AGENT:[" + applyModifyInfo.getAgent() + "];";
		}
		
		if (null != applyModifyInfo.getAgentIdentity()) {
			baseInfoDetails += "AGENT_IDENTITY:[" + applyModifyInfo.getAgentIdentity() + "];";
		}
		
		if (null != applyModifyInfo.getAgentMobileNo()) {
			baseInfoDetails += "AGENT_MOBILE_NO:[" + applyModifyInfo.getAgentMobileNo() + "];";
		}
		
		if (null != applyModifyInfo.getBankName()) {
			baseInfoDetails += "BANK_NAME:[" + applyModifyInfo.getBankName() + "];";
		}
		
		if (null != applyModifyInfo.getBankAccountNo()) {
			baseInfoDetails += "BANK_ACCOUNT_NO:[" + applyModifyInfo.getBankAccountNo() + "];";
		}
		
		if (null != applyModifyInfo.getBankAccountName()) {
			updateType = "2";
			baseInfoDetails += "BANK_ACCOUNT_NAME:[" + applyModifyInfo.getBankAccountName() + "];";
		}
		
		// 更新附件表信息
		if (hasModifyFiles) {
			// 拼接附件数据
		}
		
		// 调用修正资料存储过程，更新数据
		boolean isSuccess = false;
		String msg = "";
		Map map = new HashMap<String, Object>();
		
		String callName = "{call PKG_APPLY.p_apply_update_info(?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1, "admin");
		inParams.put(2, "管理员");
		inParams.put(3, applyId+"");
		inParams.put(4, updateType);
		inParams.put(5, modifyResult);
		inParams.put(6, baseInfoDetails);
		inParams.put(7, "");
		
		outParams.put(8, OracleTypes.VARCHAR); // 存储过程执行结果，消息字符串
		List<Map<String, Object>> result = callDao.call(callName, inParams, outParams, "procedure");
		if (null != result && result.size() > 0) {
			Map<String, Object> tmp = result.get(0);
			if (tmp.get("8") != null) {
				isSuccess = true;
				msg = (String) tmp.get("8");
				map.put("msg", msg);
			}
		}
		
		map.put("isSuccess", isSuccess);
		
		return map;
	}

	@Override
	public boolean saveAttachment(String type, Integer id, String absFilePath,
			String businessType) throws Exception {
		
		// 保存文件到附件表，提交后将临时目录文件转移到正式目录中。
		
		
		return true;
	}

	@Override
	public List<ActionLog> getActionLogList(Integer id) throws Exception {
		
		return eliminatedCheckService.getActionLogList(id);
	}

	@Override
	public List<Attachment> getAttachments(String type, String applyNo)
			throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from t_attachment t ");
		sb.append("where 1 = 1 and t.type = '").append(type).append("' ");
		sb.append("and t.apply_no = '").append(applyNo).append("' ");
		sb.append("and t.status = '1' ");
		List<Attachment> list = attachmentDao.getListBySql(sb.toString());
		return list;
	}

}

