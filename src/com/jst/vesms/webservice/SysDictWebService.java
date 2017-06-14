
package com.jst.vesms.webservice;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

import com.jst.common.model.SysDict;
import com.jst.common.service.CacheService;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.test.model.TestModel;
import com.jst.type.DataType;
import com.jst.util.MessageHandlerUtil;
import com.jst.vesms.service.SysDictService;

@Service("sysDictWebService")
public class SysDictWebService {
	
	
private static final Log log = LogFactory.getLog(SysDictWebService.class);
	
	@Resource(name = "sysDictServiceImpl")
	private SysDictService sysDictService;
	
	@Resource(name = "cacheService")
	private CacheService  cacheService;
	
	/**
	 * 测试查询列表
	 * @param clientId
	 * @param clientPwd
	 * @param requestStr
	 * @param dataType
	 * @return
	 */
//	@RequiresPermissions(logical= Logical.AND, value= {"M_TEST_MANAGER:QUERY"})
	public String list(String clientId, String clientPwd, String sessionInfo , String terminalInfo, String dataType){
		//cacheService.getMenu();
		log.debug("SysDictWebService list is start");
		MessageHandler messageHandler = null;
		try {
			messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			
			log.debug("设置反序列化配置");
			
			//开始查询所有字典表数据
			List<SysDict> list = sysDictService.getAllList();
			
			//将查询数据开始进行返回
			ObjectSerializeConfig serializeConfig2 = new ObjectSerializeConfig();
			messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据查询成功");
			serializeConfig2.setObjectAlias(Page.class, Message.RECORD_NAME);
			serializeConfig2.setObjectAlias(SysDict.class , Message.RECORD_NAME);
			log.debug("SysDictWebService_设置身体返回信息，序列化对象");
			messageHandler.addBodyParam(list, serializeConfig2);
		} catch (Exception e) {
			if(null == messageHandler) {
				messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			}
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据查询失败");
			log.error("SysDictWebService list is Error:"+e,e);
		}
		log.debug("SysDictWebService list is end");
		return messageHandler.generateResponseMessage();
	}

}

