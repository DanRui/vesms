package com.jst.vesms.service;

import java.io.File;

import com.jst.common.service.BaseService;

public interface ImportDetailService extends BaseService {
	public void readSpecify(File file) throws Exception;
}
