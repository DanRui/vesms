
package com.jst.vesms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.BaseDAO;
import com.jst.common.service.BaseServiceImpl;
import com.jst.common.utils.page.Page;
import com.jst.common.utils.string.StringUtil;
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
		// 用户Code
		@SuppressWarnings("unchecked")
		Map<String, Object> loginInfo = (Map<String, Object>) SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		String userCode = (String) loginInfo.get("USER_CODE");
		String userName = (String) loginInfo.get("USER_NAME");
		
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
		String attachments = "";
		String updateType = "1";
		
		// 更新一般资料信息，包括经办人信息、补贴账户信息等。
		if (StringUtil.isNotEmpty(applyModifyInfo.getAgent())) {
			baseInfoDetails += "AGENT:[" + applyModifyInfo.getAgent() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getAgentIdentity())) {
			baseInfoDetails += "AGENT_IDENTITY:[" + applyModifyInfo.getAgentIdentity() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getAgentMobileNo())) {
			baseInfoDetails += "AGENT_MOBILE_NO:[" + applyModifyInfo.getAgentMobileNo() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getBankCode())) {
			baseInfoDetails += "BANK_CODE:[" + applyModifyInfo.getBankCode() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getBankName())) {
			baseInfoDetails += "BANK_NAME:[" + applyModifyInfo.getBankName() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getBankAccountNo())) {
			baseInfoDetails += "BANK_ACCOUNT_NO:[" + applyModifyInfo.getBankAccountNo() + "];";
		}
		
		if (StringUtil.isNotEmpty(applyModifyInfo.getBankAccountName())) {
			updateType = "2";
			baseInfoDetails += "BANK_ACCOUNT_NAME:[" + applyModifyInfo.getBankAccountName() + "];";
		}
		
		// 如果有修改补贴对象户名的，则必须更新补贴账户变更的附件表数据
		if ("2".equals(updateType)) {
			// 更新补贴账户变更证明材料
			if (null != applyModifyInfo.getAccountChangeProofFiles() && applyModifyInfo.getAccountChangeProofFiles().size() > 0) {
				String btzhmFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getAccountChangeProofFiles().size() ; i ++) {
					btzhmFiles += applyModifyInfo.getAccountChangeProofFiles().get(i) + "|";
				}
				attachments += "BTZHMBGZM:[" + btzhmFiles.substring(0, btzhmFiles.length() - 1) + "];";
			}
		}
		
		// 更新附件表信息
		if (hasModifyFiles) {
			// 拼接附件数据
			// 更新报废回收证明材料
			if (null != applyModifyInfo.getCallbackProofFile() && applyModifyInfo.getCallbackProofFile().size() > 0) {
				String bfzhFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getCallbackProofFile().size() ; i ++) {
					bfzhFiles += applyModifyInfo.getCallbackProofFile().get(i) + "|";
				}
				attachments += "JDCHSZM:[" + bfzhFiles.substring(0, bfzhFiles.length() - 1) + "];";
			}
			
			// 更新机动车注销证明材料
			if (null != applyModifyInfo.getVehicleCancelProofFiles() && applyModifyInfo.getVehicleCancelProofFiles().size() > 0) {
				String zxzmFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getVehicleCancelProofFiles().size() ; i ++) {
					zxzmFiles += applyModifyInfo.getVehicleCancelProofFiles().get(i) + "|";
				}
				attachments += "JDCZXZM:[" + zxzmFiles.substring(0, zxzmFiles.length() - 1) + "];";
			}
			
			// 更新银行卡材料
			if (null != applyModifyInfo.getBankCardFiles() && applyModifyInfo.getBankCardFiles().size() > 0) {
				String yhkFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getBankCardFiles().size() ; i ++) {
					yhkFiles += applyModifyInfo.getBankCardFiles().get(i) + "|";
				}
				attachments += "YHK:[" + yhkFiles.substring(0, yhkFiles.length() - 1) + "];";
			}
			// 更新车主身份证明材料
			if (null != applyModifyInfo.getVehicleOwnerProofFiles() && applyModifyInfo.getVehicleOwnerProofFiles().size() > 0) {
				String czsfzmFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getVehicleOwnerProofFiles().size() ; i ++) {
					czsfzmFiles += applyModifyInfo.getVehicleOwnerProofFiles().get(i) + "|";
				}
				attachments += "CZSFZM:[" + czsfzmFiles.substring(0, czsfzmFiles.length() - 1) + "];";
			}
			// 更新非财政供养单位证明材料
			if (null != applyModifyInfo.getNoFinanceProvideFiles() && applyModifyInfo.getNoFinanceProvideFiles().size() > 0) {
				String fczgyFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getNoFinanceProvideFiles().size() ; i ++) {
					fczgyFiles += applyModifyInfo.getNoFinanceProvideFiles().get(i) + "|";
				}
				attachments += "FCZGYZM:[" + fczgyFiles.substring(0, fczgyFiles.length() - 1) + "];";
			}
			// 更新代理人身份证明材料
			if (null != applyModifyInfo.getAgentProofFiles() && applyModifyInfo.getAgentProofFiles().size() > 0) {
				String dlrFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getAgentProofFiles().size() ; i ++) {
					dlrFiles += applyModifyInfo.getAgentProofFiles().get(i) + "|";
				}
				attachments += "DLRSFZ:[" + dlrFiles.substring(0, dlrFiles.length() - 1) + "];";
			}
			// 更新代理委托书材料
			if (null != applyModifyInfo.getAgentProxyFiles() && applyModifyInfo.getAgentProxyFiles().size() > 0) {
				String dlwtsFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getAgentProxyFiles().size() ; i ++) {
					dlwtsFiles += applyModifyInfo.getAgentProxyFiles().get(i) + "|";
				}
				attachments += "DLWTS:[" + dlwtsFiles.substring(0, dlwtsFiles.length() - 1) + "];";
			}
			// 更新开户许可证材料
			if (null != applyModifyInfo.getOpenAccPermitFiles() && applyModifyInfo.getOpenAccPermitFiles().size() > 0) {
				String khxuzFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getOpenAccPermitFiles().size() ; i ++) {
					khxuzFiles += applyModifyInfo.getOpenAccPermitFiles().get(i) + "|";
				}
				attachments += "KHXKZ:[" + khxuzFiles.substring(0, khxuzFiles.length() - 1) + "];";
			}
			// 更新确认的受理表材料
			if (null != applyModifyInfo.getSignedApplyFiles() && applyModifyInfo.getSignedApplyFiles().size() > 0) {
				String qzslbFiles = "";
				for (int i = 0 ; i < applyModifyInfo.getSignedApplyFiles().size() ; i ++) {
					qzslbFiles += applyModifyInfo.getSignedApplyFiles().get(i) + "|";
				}
				attachments += "QRSLB:[" + qzslbFiles.substring(0, qzslbFiles.length() - 1) + "];";
			}
			
			
			
		}
		
		// 调用修正资料存储过程，更新数据
		boolean isSuccess = false;
		String msg = "";
		Map map = new HashMap<String, Object>();
		
		String callName = "{call PKG_APPLY.p_apply_update_info(?,?,?,?,?,?,?,?)}";
		Map<Integer, Object> inParams = new HashMap<Integer, Object>();
		Map<Integer, Integer> outParams = new HashMap<Integer, Integer>();
		
		inParams.put(1, userCode);
		inParams.put(2, userName);
		inParams.put(3, applyId+"");
		inParams.put(4, updateType);
		inParams.put(5, modifyResult);
		inParams.put(6, baseInfoDetails);
		inParams.put(7, attachments);
		
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

