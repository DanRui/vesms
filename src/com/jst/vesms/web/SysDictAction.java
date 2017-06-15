
package com.jst.vesms.web;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.common.model.SysDict;
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.utils.RedisUtil;
import com.jst.type.DataType;
import com.jst.vesms.constant.SysConstant;
import com.jst.vesms.service.SysDictService;
import com.jst.vesms.util.SerializeUtil;

@RequestMapping("/sysDict")
@Controller
public class SysDictAction extends BaseAction {

	private static final Log log = LogFactory.getLog(SysDictAction.class);
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	@Resource(name = "sysDictServiceImpl")
	private SysDictService sysDictService;
	
	/**
	 * 
	 * <p>Description: 通过字典类型和Code返回名称</p>
	 * @param name description type
	 * @return List
	 *
	 */
	@ResponseBody
	@RequestMapping("getDictNameByCode")
	public String getDictNameByCode(@RequestParam("dictType") String dictType, @RequestParam("dictCode") String dictCode) {
		String result = "";
		log.debug("SysDictAction getDictNameByCode is start");
//		SysDict sysDict = cacheService.getSysDictByDictTypeAndCode(dictType, dictCode);
		try {
			result = RedisUtil.getDictNameByDictCode(dictType, dictCode);
		} catch (Exception e) {
			log.error("SysDictAction getDictNameByCode is Error:" + e, e);
		}
		log.debug("SysDictAction getDictNameByCode is end");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("getDictListByType")
	public String getDictListByType(@RequestParam("dictType") String dictType) {
		log.debug("SysDictAction getDictListByType is start");
		List<Object> list = null;
		JSONArray jsonArray = new JSONArray();
		try {
			String result = RedisUtil.getSysDictList(dictType);
			list = SerializeUtil.serializeList(SysDict.class, result, DataType.JSON);
			for (Object object : list) {
				JSONObject json = new JSONObject();
				json.put("value", ((SysDict)object).getDictValue());
				json.put("code", ((SysDict)object).getDictCode());
				jsonArray.add(json);
			}
		} catch (Exception e) {
			log.error("SysDictAction getDictListByType is Error:" + e, e);
			list = null;
		}
		log.debug("SysDictAction getDictListByType is end");
		return jsonArray.toString();
	}
	
	@ResponseBody
	@RequestMapping("getDictListFromMap")
	public String getDictListFromMap(@RequestParam("dictType") String dictType) {
		log.debug("SysDictAction getDictListFromMap is start");
		List<Object> list = null;
		JSONArray jsonArray = new JSONArray();
		try {
			if ("VEHICLE_PLATE_TYPE".equals(dictType)) {
				// 号牌种类
				Hashtable<String, String> table = SysConstant.VEHICLE_PALTE_TYPE;
				Enumeration e = table.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					JSONObject json = new JSONObject();
					json.put("value", table.get(key));
					json.put("code", key);
					jsonArray.add(json);
				}
			} else {
				// 其他类型（车辆类型、燃油种类、机动车状态）
				List<SysDict> sysDictList = sysDictService.getListByPorperty("dictType", dictType, "state = '1'");
				for (SysDict dict : sysDictList) {
					JSONObject json = new JSONObject();
					json.put("value", dict.getDictValue());
					json.put("code", dict.getDictCode());
					jsonArray.add(json);
				}
			}
		} catch (Exception e) {
			log.error("SysDictAction getDictListFromMap is Error:" + e, e);
			list = null;
		}
		log.debug("SysDictAction getDictListFromMap is end");
		return jsonArray.toString();
	}
	
}

