package com.jst.vesms.service;

import java.io.Serializable;
import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.VehicleRecycle;



/**
 * <p>Title:拨付批次接口</p>
 * <p>Description: 用于报废车辆信息的新增、修改、删除、查询、查看等功能</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public interface PayApplyService extends BaseService {

	
	public String createBatch(String ids) throws Exception;

	public BatchMain batchNoView(Integer id);
	
	public Page getApplyPage(Page page, List<PropertyFilter> list);

	public EliminatedApply  getById(Integer id) throws Exception;

	public String batchExport(Integer id, String exportPath,String password) throws Exception;

	public String deleteApply(String batchId, String ids)  throws Exception;

	public String addApply(String batchNo, String ids)throws Exception;

	public String confirmBatch(String ids) throws Exception;

	public String batchCancel(String ids) throws Exception;

	public String createRepBatch(String ids)throws Exception;

	public String deleteRepApply(String batchNo, String ids)throws Exception;

	public String repBatchCancel(String ids) throws Exception;

	public String addRepApply(String batchNo, String ids)throws Exception;

	public String confirmRepBatch(String ids)throws Exception;

	public Page getPageBySql(Page page, String sql) throws Exception;

	public String batchRepExport(Integer id,String exportPath,String password)throws Exception;

	public List<EliminatedApply> getBatchApplyList(String batchNo) throws Exception;

	public BatchMain getObj(Integer id);

	public List<EliminatedApply> getRepBatchApplyList(String batchNo)throws Exception;
	
	public List getBySql(String batchNo) throws Exception ;

	public Page filterRepeatedBatchPage(Page page);

	public Page filterBatchPage(Page page);

	public List<String[]> batchExcelList(String batchNo,String type,Integer id) throws Exception ;
	
	public List getListBySql(String sql) throws Exception;
	
	public String getTotalAmount(String batchNo,String batchType) throws Exception;
	
	public List<String[]> repBatchExcelPreview(String batchNo) throws Exception;
	
	public List<String[]> repToFinExcelPreview(String batchNo) throws Exception;
	
	public Page getPageSql(Page page,String sql) throws Exception;
}
