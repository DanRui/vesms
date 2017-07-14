package com.jst.vesms.service;

import java.io.File;

import com.jst.common.service.BaseService;

public interface ImportDetailService extends BaseService {
	public void saveSpecify(File file,Integer payImportId) throws Exception;
}
