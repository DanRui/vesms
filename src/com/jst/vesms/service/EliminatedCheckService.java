
package com.jst.vesms.service;

import java.util.List;
import java.util.Map;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.ActionLog;
import com.jst.vesms.model.EliminatedApply;


/**
 * <p>Title:受理单审核接口</p>
 * <p>Description: 用于受理单的查询、查看、审核等功能</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public interface EliminatedCheckService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 通过号牌号码、号牌种类获取交警接口最新数据</p>
	 * @param vehiclePlateNum  号牌号码   String
	 * @param vehiclePlateType 号牌种类   String
	 * @return EliminatedApply
	 *
	 */
	public EliminatedApply getVehicleInfo(String vehiclePlateNum, String vehiclePlateType) throws Exception;
	
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
	
	public Map<String, Object> save(EliminatedApply eliminatedApply) throws Exception;
	
	public Map<String, Object> updateById(Integer id, EliminatedApply eliminatedApply) throws Exception;
	
	public EliminatedApply getById(Integer id) throws Exception;
	
	public boolean confirm(Integer id) throws Exception;
	
	public boolean saveAttachment(String type, Integer id, String absFilePath, String businessType) throws Exception;
	
	public Map<String, Object> check(String ids, String checkType, String faultType, String checkOpinion, String currentPost) throws Exception;

	public List<ActionLog> getActionLogList(Integer id) throws Exception;
}

