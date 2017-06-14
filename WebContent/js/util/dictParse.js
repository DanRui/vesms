/**
 * @date 2017-05-18
 * @author DanRui
 * @see 页面获取字典表数据，解析字典类型等定义
 */

function getDictNameByCode(type, code) {
	var dictType = type || "";
	var dictCode = code || "";
	
	if (null == dictType || "" == dictType || undefined == dictType) {
		return null;
	}
	if (null == dictCode || "" == dictCode || undefined == dictCode) {
		return null;
	}
	var name = "";
	$.post("sysDict/getDictNameByCode.do", {
		"dictType" : dictType,
		"dictCode" : dictCode
	}, function(data) {
		//alert(data.dictValue);
		name = data.dictValue;
	});
	
	return name;
}