
package com.jst.vesms.service;

import java.util.List;
import java.util.Map;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.EliminatedApply;


/**
 * <p>Title:受理录入接口</p>
 * <p>Description: 用于受理信息的新增、修改、删除、查询、查看等功能</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public interface EliminatedApplyService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 通过号牌号码、号牌种类校验补贴资格，有资格再返回车辆信息，无资格直接返回失败原因</p>
	 * @param vehiclePlateNum  号牌号码   String
	 * @param vehiclePlateType 号牌种类   String
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> getVehicleInfo(String vehiclePlateNum, String vehiclePlateType) throws Exception;
	
	/**
	 * 
	 * <p>Description: 通过号牌号牌、号牌种类、车架号、业务类别获取车辆补贴资格信息，通用查询接口</p>
	 * @param vehiclePlateNum 号牌号码  String
	 * @param vehiclePlateType 号牌种类  String
	 * @param vehicleIdentifyNo 车架号  String
	 * @param type 业务类别  String
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> verifyVehicle(String vehiclePlateNum, String vehiclePlateType, String vehicleIdentifyNo, String type) throws Exception;
	
	/**
	 * 
	 * <p>Description: 通过号牌号码、号牌种类获取交警接口数据，通用接口</p>
	 * @param vehiclePlateNum  号牌号码   String
	 * @param vehiclePlateType 号牌种类   String
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> getJiaoJingVehicle(String vehiclePlateNum, String vehiclePlateType) throws Exception;
	
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
	
	/**
	 * 
	 * <p>Description: 抓拍图片上传</p>
	 * @param image 前台传递的Base64字符串  String
	 * @return String
	 *
	 */
	public String fileCaptureUpload(String image) throws Exception;
	
	public Map<String, Object> save(EliminatedApply eliminatedApply, String callbackProofFile, String vehicleCancelProofFiles,
			  String bankCardFiles, String vehicleOwnerProofFiles, String agentProxyFiles, String agentProofFiles,
			  String noFinanceProvideFiles, String openAccPromitFiles) throws Exception;
	
	public Map<String, Object> updateById(Integer id, EliminatedApply eliminatedApply) throws Exception;
	
	public EliminatedApply getById(Integer id) throws Exception;
	
	public boolean saveConfirm(Integer id, String signedApplyFiles) throws Exception;
	
	public boolean saveAttachment(String type, Integer id, String absFilePath, String businessType) throws Exception;

	public Page filterNoConfirm(Page page) throws Exception;
	
	public List<ActionLog> getActionLogList(Integer id) throws Exception;
	
	public Map<String, Object> saveArchive(Integer id) throws Exception;
	
	public Map<String, Object> saveAttachments(String applyNo, String callbackProofFile, String vehicleCancelProofFiles, String bankCardFiles,
			String vehicleOwnerProofFiles, String agentProxyFiles, String agentProofFiles, String noFinanceProvideFiles, String openAccPromitFiles) throws Exception;
	
	public List<Attachment> getAttachments(String type, String applyNo) throws Exception;
	
	/**
	 * 
	 * <p>Description: 通过预约号获取预约车辆列表数据</p>
	 * @param appointmentNo 预约号  String
	 * @return String
	 *
	 */
	public String getAppointmentInfo(String appointmentNo) throws Exception;
	
	/**
	 * 
	 * <p>Description: 检查车辆是否预约过</p>
	 * @param id 受理表主键  Integer
	 * @return Map<String, Object>
	 *
	 */
	public Map<String, Object> checkHasAppointed(Integer id) throws Exception;
}

