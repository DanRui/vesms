package com.jst.vesms.service;

import java.io.File;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.PayResultImportDetail;

public interface ImportDetailService extends BaseService {
	public void saveSpecify(File file,Integer payImportId) throws Exception;

	public PayResultImportDetail detailNoView(Integer id);
	
	public Page<PayResultImportDetail> getPageBySql(Page<PayResultImportDetail> page,
			String sql) throws Exception;
}
