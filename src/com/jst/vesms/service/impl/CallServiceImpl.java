
package com.jst.vesms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jst.vesms.dao.ICallDao;
import com.jst.vesms.service.CallService;

@Service("callServiceImpl")
public class CallServiceImpl implements CallService {
	
	@Resource(name = "callDao")
	private ICallDao callDao;
	
	@Override
	public List<Map<String, Object>> call(String name,
			Map<Integer, Object> inParams, Map<Integer, Integer> outParams,
			String type) throws Exception {
		return callDao.call(name, inParams, outParams, type);
	}

}

