
package com.jst.vesms.service;

import java.util.List;

import com.jst.common.service.BaseService;
import com.jst.common.model.SysDict;

public interface SysDictService extends BaseService {
	
	public List<SysDict> getAllSysDictList() throws Exception;

}

