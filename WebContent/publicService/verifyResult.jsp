<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.jst.common.utils.DateUtil"%>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vehicle-verify-result</title>
</head>
<body>
	<div id="vehicle-verify-result" class="datagrid-header datagrid-height-default">
		<table class="datagrid-table-s datagrid-htable">
			<!-- <tr class="datagrid-head-row classify-tr">
				<td colspan="6">车辆信息查询</td>
			</tr> -->
			<tr class="datagrid-row">
				<td class="view_table_left">号牌号码：</td>
				<td class="view_table_right">${v.vehiclePlateNum}</td>
				<td class="view_table_left">号牌种类：</td>
				<td class="view_table_right">${v.vehiclePlateTypeName}</td>
				<td class="view_table_left">厂牌型号：</td>
				<td class="view_table_right">${v.vehicleModelNo}</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车辆类型：</td>
				<td class="view_table_right">${v.vehicleTypeName}</td>
				<td class="view_table_left">车架号：</td>
				<td class="view_table_right">${v.vehicleIdentifyNo}</td>
				<td class="view_table_left">发动机号：</td>
				<td class="view_table_right">${v.engineNo}</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">使用性质：</td>
				<td class="view_table_right">${v.useOfPropertyName}</td>
				<td class="view_table_left">燃油类型：</td>
				<td class="view_table_right">${v.iolTypeName}</td>
				<td class="view_table_left">总质量（千克）</td>
				<td class="view_table_right">${v.totalWeight}</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">核定载客数（人）：</td>
				<td class="view_table_right">${v.vehicleNumPeople}</td>
				<td class="view_table_left">初次登记日期：</td>
				<td class="view_table_right"><fmt:formatDate value="${v.registerDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td class="view_table_left">强制报废期：</td>
				<td class="view_table_right"><fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车主：</td>
				<td class="view_table_right">${v.vehicleOwner}</td>
				<td class="view_table_left">车主联系电话：</td>
				<td class="view_table_right">${v.mobile}</td>
				<td class="view_table_left">排放标准：</td>
				<td class="view_table_right">${v.emissionStandard}</td>
			</tr>
			<!-- <tr class="datagrid-row">
				<td class="view_table_left">机构类型：</td>
				<td class="view_table_right">企业</td>
				<td class="view_table_left">组织机构代码：</td>
				<td class="view_table_right">400009880</td>
				<td class="view_table_left">车辆状态：</td>
				<td class="view_table_right">正常</td>
			</tr> -->
			<tr class="datagrid-row">
				<td class="view_table_left">补贴金额（元）：</td>
				<td class="view_table_right">${v.subsidiesMoney}</td>
				<td class="view_table_left">补贴标准：</td>
				<td class="view_table_right">${v.subsidiesStandard}</td>
				<td class="view_table_left">说明：</td>
				<td class="view_table_right"></td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">当前查询日期：</td>
				<td class="view_table_right"><%=DateUtil.format(new Date(),"yyyy 年 MM 月 dd 日")%>></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>