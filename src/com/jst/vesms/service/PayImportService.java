package com.jst.vesms.service;

import java.io.File;

import com.jst.common.service.BaseService;

public interface PayImportService extends BaseService{
	public void readPayImport(File file) throws Exception;
}
