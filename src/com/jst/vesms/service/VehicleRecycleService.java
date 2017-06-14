
package com.jst.vesms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.Attachment;
import com.jst.vesms.model.VehicleRecycle;


/**
 * <p>Title:报废录入接口</p>
 * <p>Description: 用于报废车辆信息的新增、修改、删除、查询、查看等功能</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public interface VehicleRecycleService extends BaseService {
	
	/**
	 * 
	 * <p>Description: 通过报废证明编号获取报废公司接口数据</p>
	 * @param callbackProofNo 报废回收证明编号  String
	 * @return VehicleRecycle
	 *
	 */
	public VehicleRecycle getVehicleRecycleByProof(String callbackProofNo) throws Exception;

	/**
	 * 
	 * <p>Description: 获得包含号牌种类、车辆类型、使用性质、燃油种类等字典类型的名称page对象。</p>
	 * @param page 传入的page对象  Page
	 * @return Page
	 *
	 */
	public Page<VehicleRecycle> getPageExtra(Page<VehicleRecycle> page) throws Exception;
	
	/**
	 * 
	 * <p>Description: 抓拍图片上传</p>
	 * @param image 前台传递的Base64字符串  String
	 * @return String
	 *
	 */
	public String fileCaptureUpload(String image) throws Exception;
	
	public Serializable save(VehicleRecycle vehicleRecycle) throws Exception;
	
	public VehicleRecycle getById(Integer id) throws Exception;
	
	public VehicleRecycle getByNumAndType(String vehiclePlateNum, String vehiclePlateType) throws Exception;
	
	public Map<String, Object> saveAttachments(Integer id, String callbackProofFile, String vehicleRegisterProofFiles, String vehicleLicenseFiles) throws Exception;
	
	public List<Attachment> getAttachments(String type, Integer id) throws Exception;
}

