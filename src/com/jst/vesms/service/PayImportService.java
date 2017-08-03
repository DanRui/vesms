package com.jst.vesms.service;

import java.io.File;
import java.util.List;

import com.jst.common.service.BaseService;
import com.jst.vesms.model.BatchMain;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;

public interface PayImportService extends BaseService{
	public String savePayImport(String fileName,File file) throws Exception;
	
	public PayResultImport importNoView(Integer id);
	
	public String importPayResult(String ids) throws Exception;

	//获取相同文件名后缀数最大的数值 
	public List getNewFileName(String fileName) throws Exception;
		
/*	// 判断文件名是否存在
	public List getFileNameSql(String fileName) throws Exception;*/
}
