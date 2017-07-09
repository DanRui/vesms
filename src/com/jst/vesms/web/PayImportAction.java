package com.jst.vesms.web;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.vesms.PayImportService;
import com.jst.vesms.service.PayResultService;


@RequestMapping("/payImport")
@Controller
public class PayImportAction extends BaseAction{
private static final Log log = LogFactory.getLog(PayImportAction.class);
	
	@Resource(name="payImportServiceImpl")
	private PayImportService payImportService;
	
	
	@RequestMapping("payImportView")
//	@Privilege(modelCode = "M_PAY_IMPORT_LIST",prvgCode = "QUERY")
	public ModelAndView payImportView() throws Exception {
		String view = "PAY_IMPORT.IMPORT_LIST";
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		return mv;
	}
}
