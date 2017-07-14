package com.jst.vesms.service;

import java.io.File;

import com.jst.common.service.BaseService;

public interface PayImportService extends BaseService{
	public void savePayImport(File file) throws Exception;
}
