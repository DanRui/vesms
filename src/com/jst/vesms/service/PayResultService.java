package com.jst.vesms.service;

import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.EliminatedApply;
import com.jst.vesms.model.VehicleRecycle;


/**
 * <p>Title:拨付结果管理接口</p>
 * <p>Description: 用于拨付结果管理的结果标记、查询、查看等功能</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public interface PayResultService extends BaseService{

	String payResultMark(String ids) throws Exception;

	EliminatedApply getById(Integer id) throws Exception;

	BatchMain batchNoView(Integer id) ;

	Page getApplyPage(Page page, List<PropertyFilter> list);

	String markBatchApply(String ids, String payResStatus, String faultType, String faultDesc) throws Exception;

	Page getPageBySql(Page page, String string) throws Exception;
	
	Page filterRepeatedBatchPage(Page page) throws Exception;
	
}
