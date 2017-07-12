package com.jst.vesms.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jst.common.springmvc.BaseAction;
import com.jst.vesms.service.ImportDetailService;

@RequestMapping("/importDetail")
@Controller
public class ImportDetailAction extends BaseAction{
private static final Log log = LogFactory.getLog(ImportDetailAction.class);
	
	@Resource(name="importDetailServiceImpl")
	private ImportDetailService importDetailService;
}
