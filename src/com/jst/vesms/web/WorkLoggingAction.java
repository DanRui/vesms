
package com.jst.vesms.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.vesms.service.SysDictService;

@RequestMapping("/workLogging")
@Controller
public class WorkLoggingAction extends BaseAction {
	
private static final Log log = LogFactory.getLog(WorkLoggingAction.class);
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	@Resource(name = "sysDictServiceImpl")
	private SysDictService sysDictService;
	
	@Resource(name = "workLoggingServiceImpl")
	private WorkLoggingService workLoggingService;

}

