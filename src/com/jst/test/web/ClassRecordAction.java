package com.jst.test.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.system.client.WebServiceClient;
import com.jst.system.util.SystemUtil;
import com.jst.test.model.ClassRecord;
import com.jst.test.model.TestModel;
import com.jst.type.DataType;
import com.jst.util.JsonUtil;
import com.jst.util.MessageHandlerUtil;
import com.jst.util.StringUtil;


/**
 * 
 * @author AF
 * @version 1.0
 * @see ClassRecordAction
 */
@RequestMapping("/classrecord")
@Controller
public class ClassRecordAction extends BaseAction {
	
	
	/**
	 * 用户记录运行日志
	 */
	private static final Log log = LogFactory.getLog(ClassRecordAction.class);
	
	
	/**
	 * 用于调用的WebService名称
	 */
	private static final String SERVICE_NAME = "ClassRecordService";

	/**
	 * 用于和WebService 之间数据交互时的数据结构类型
	 */
	private static final DataType dataType = DataType.JSON;
	
	
	/**
	 * 电子教学日志查询
	 * @param pageNo 当前页
	 * @param pageSize  每页显示条数
	 * @param order		排序方式
	 * @param orderBy	排序字段
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @param carnum	车牌号码
	 * @param idcard	学员身份证号码
	 * @param part		培训部分
	 * @param project	培训项目
	 * @param coachname	教练姓名
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("list")
	@Privilege(modelCode  = "M_CLASS_RECORD_MANAGER", prvgCode = "QUERY" )
	public String list(@RequestParam(value="pageNo", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy,String inscode,String starttime,String endtime,String stuname,String carnum,String idcard,String part,String project,String carcode){
		
		Page page= new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if(StringUtil.isNotEmpty(inscode)){
			filters.add(new PropertyFilter("EQS_inscode",inscode));
		}
		if(StringUtil.isNotEmpty(starttime)){
			filters.add(new PropertyFilter("GED_starttime",starttime));
		}
		
		if(StringUtil.isNotEmpty(endtime)){
			filters.add(new PropertyFilter("LED_starttime",endtime));
		}
		
		if(StringUtil.isNotEmpty(carnum)){
			filters.add(new PropertyFilter("LIKES_carnum",carnum));
		}
		
		if(StringUtil.isNotEmpty(idcard)){
			filters.add(new PropertyFilter("LIKES_idcard",idcard));
		}
		if(StringUtil.isNotEmpty(part)){
			filters.add(new PropertyFilter("EQS_part",part));
		}
		
		if(StringUtil.isNotEmpty(project)){
			filters.add(new PropertyFilter("EQS_project",project));
		}
		if(StringUtil.isNotEmpty(carcode)){
			filters.add(new PropertyFilter("LIKES_carcode",carcode));
		}
		if(StringUtil.isNotEmpty(stuname)){
			filters.add(new PropertyFilter("LIKES_stuname" , stuname));
		}		
		String returnStr = null;
		
		try {
			
			log.debug("ClassRecordAction list start");
			MessageHandler wrapper = MessageHandlerUtil.getMessageHandler(dataType);
			ObjectSerializeConfig serializeConfig = initPage(wrapper, page, filters);
			String requestStr = wrapper.generateRequestMessage();
			//调用webservice进行操作
			log.debug("ClassRecordAction list call webservice start");
			String result = WebServiceClient.invokeInterface(SERVICE_NAME, "list", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo,requestStr}, dataType);
			log.debug("ClassRecordAction list call webservice end");
			serializeConfig.clear();
			serializeConfig.setObjectAlias(Page.class, Message.RECORD_NAME);
			serializeConfig.setObjectAlias(ClassRecord.class, "classRecord");
			MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
		    Page pageData = (Page)SystemUtil.serializeObject(Page.class , handler , dataType,serializeConfig);
		    returnStr = writerPage(pageData);
		    log.debug("ClassRecordAction list end");
		} catch (Exception e) {
			log.error("ClassRecordAction list is Error:"+e,e);
			returnStr = JsonUtil.toErrorMsg("数据获取失败！");
		}
		
		return returnStr;
	}
	
	@RequestMapping("view")
	@Privilege(modelCode  = "M_CLASS_RECORD_MANAGER", prvgCode = "VIEW" )
	public ModelAndView view(@PathParam("id") Integer id,@PathParam(value = "type") String type){
		log.debug("ClassRecordAction view is start");
		String view = "CLASSRECORD.VIEW";
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)){
			view = "CLASSRECORD.UPDATE";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		try {
			String result = WebServiceClient.invokeInterface(SERVICE_NAME, "view", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo,id}, dataType);
			MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
			String retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
			String retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
			if(Message.RET_CODE_SUCCESS.equals(retCode)){
				Object object  = SystemUtil.serializeObject(ClassRecord.class, handler, dataType);
				mv.addObject("v", object);
			}else{
				
			}
		} catch (Exception e) {
			log.error("ClassRecordAction view is Error:"+e,e);
		}
		
		log.debug("ClassRecordAction view is End");
		return mv;
	}

	/**
	 * 进行逻辑删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("delete")
	@Privilege(modelCode  = "M_CLASS_RECORD_MANAGER", prvgCode = "DELETE" )
	public String delete(@RequestParam(value = "id") Integer id){
		String retCode = "";
		String retMsg = "";
		log.debug("ClassRecordAction delete is start");
		try {
			String result = WebServiceClient.invokeInterface(SERVICE_NAME, "delete", new Object[]{clientId,clientPwd,sessionInfo,terminalInfo,id}, dataType);
			MessageHandler handler = MessageHandlerUtil.getMessageHandler(dataType, result);
			retCode = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_CODE, dataType));
			retMsg = handler.getParamValue(MessageHandlerUtil.getElementPath(Message.RET_MSG, dataType));
			
		} catch (Exception e) {
			log.error("ClassRecordAction delete is Error:"+e,e);
		}
		log.debug("ClassRecordAction delete is end");
		if(Message.RET_CODE_SUCCESS.equals(retCode)){
			return JsonUtil.toSuccessMsg(retMsg);
		}else{
			return JsonUtil.toErrorMsg(retMsg);
		}
		
	}
}
