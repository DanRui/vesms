package com.jst.vesms.service;

import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.EliminatedApply;

public interface ConcludeService extends BaseService{

	//Page getPageBySql(Page page, String sql)throws Exception;

	EliminatedApply getById(Integer id) throws Exception;

	String concludeApply(String strids,String type)throws Exception;

	Page getApplyPage(Page page, List<PropertyFilter> list);
}
