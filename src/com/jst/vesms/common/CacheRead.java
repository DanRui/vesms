
package com.jst.vesms.common;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.common.model.SysDict;
import com.jst.common.utils.RedisUtil;
import com.jst.type.DataType;
import com.jst.vesms.util.SerializeUtil;

/**
 *
 * <p>Title:从缓存读取数据</p>
 * <p>Description: 将不常更新的数据放入缓存</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public class CacheRead {
	
	/**
	 * 设置日志输出
	 */
	private static Log log = LogFactory.getLog(CacheRead.class);
	
	/**
	 * 设置数据格式
	 */
	private static final DataType dataType = DataType.JSON;
	
	/*public static List<SysDict> getSysDictAllList(String dictType) {
		try {
			log.debug("CacheRead getSysDictAllList is start");
			String listStr = RedisUtil.getSysDictList(dictType);
			List<Object> list = SerializeUtil.serializeList(SysDict.class, listStr, dataType);
			return list;
		} catch (Exception e) {
			log.error("CacheRead getSysDictAllList is error:" + e, e);
		}
		log.debug("CacheRead getSysDictAllList is end");
		return null;
	}*/
	
	/**
	 * 
	 * <p>Description: 根据字典类型，获取这类字典的集合</p>
	 * @param dictType 字典类型  String
	 * @return List
	 *
	 */
	@SuppressWarnings("null")
	public static List<SysDict> getSysDictListByType(String dictType) {
		List<SysDict> sysDictList = new ArrayList<SysDict>();
		try {
			log.debug("CacheRead getSysDictListByType is start");
			String listStr = RedisUtil.getSysDictList(dictType);
			List<Object> list = SerializeUtil.serializeList(SysDict.class, listStr, dataType);
			for (Object object : list) {
				sysDictList.add((SysDict) object);
			}
		} catch (Exception e) {
			log.error("CacheRead getSysDictListByType is error:" + e, e);
		}
		log.debug("CacheRead getSysDictListByType is end");
		return (sysDictList.size() > 0 ? sysDictList : null);
	}
	
	/**
	 * 
	 * <p>Description: 根据字典类型和DictCode获取字典</p>
	 * @param dictType 字典类型  String
	 * @param dictType 字典编码  String
	 * @return SysDict
	 *
	 */
	public static SysDict getSysDictByCode(String dictType, String dictCode) {
		SysDict sysDict = null;
		try {
			log.debug("CacheRead getSysDictByCode is start");
			String retStr = RedisUtil.getDictNameByDictCode(dictType, dictCode);
			JSONObject retObj = JSONObject.fromObject(retStr);
			sysDict = (SysDict) JSONObject.toBean(retObj, SysDict.class);
		} catch (Exception e) {
			log.error("CacheRead getSysDictByCode is error:"+e, e);
		}
		log.debug("CacheRead getSysDictByCode is end");
		return sysDict;
	}
	
	/**
	 * 
	 * <p>Description: 根据字典类型和DictValue获取字典</p>
	 * @param dictType 字典类型  String
	 * @param dictType 字典编码  String
	 * @return SysDict
	 *
	 */
	public static SysDict getSysDictByValue(String dictType, String dictValue) {
		SysDict sysDict = null;
		try {
			log.debug("CacheRead getSysDictByValue is start");
			String retStr = RedisUtil.getDictNameByDictValue(dictType, dictValue);
			JSONObject retObj = JSONObject.fromObject(retStr);
			sysDict = (SysDict) JSONObject.toBean(retObj, SysDict.class);
		} catch (Exception e) {
			log.error("CacheRead getSysDictByValue is error:"+e, e);
		}
		log.debug("CacheRead getSysDictByValue is start");
		return sysDict;
	}
	
	public static void main(String[] args) {
		System.out.println(CacheRead.getSysDictByCode("VEHICLE_TYPE", "B11"));
		System.out.println(CacheRead.getSysDictListByType("VEHICLE_TYPE"));
	}

}


