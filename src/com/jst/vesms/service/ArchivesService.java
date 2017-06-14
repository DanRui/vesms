package com.jst.vesms.service;

import java.util.List;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.service.BaseService;
import com.jst.common.utils.page.Page;
import com.jst.vesms.model.Archives;
import com.jst.vesms.model.EliminatedApply;

public interface ArchivesService extends BaseService{

	Archives getById(Integer id) throws Exception;

	Page getApplyPage(Page page, List<PropertyFilter> list);

}
