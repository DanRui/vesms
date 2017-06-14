package com.jst.vesms.service;

import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.EliminatedApply;

public interface TransactFinService extends BaseService{

	Page getPageBySql(Page page, String sql)throws Exception;

	EliminatedApply getById(Integer id) throws Exception;

	String concludeApply(String strids,String type)throws Exception;

}
