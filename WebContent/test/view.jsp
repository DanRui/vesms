<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test-update</title>
</head>
<body>
	<div class="datagrid-header datagrid-height-default"
		id="test-view-toolbar">
		<table class=" datagrid-table-s datagrid-htable">
		<tr class="datagrid-head-row classify-tr">
				<td colspan="4">基本信息</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">课堂编号：</td>
				<td class="view_table_right" colspan="3">${v.id}</td>
			</tr>
			<tr class="datagrid-head-row">
				<td class="view_table_left">培训机构代码：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">培训机构名称：</td>
				<td class="view_table_right">${v.scription}</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">学员编号：</td>
				<td class="view_table_right">${v.id }</td>
				<td class="view_table_left">学员姓名：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">学员身份证号码：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">教练员编号：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
			<tr class="datagrid-head-row classify-tr">
				<td colspan="4">审核信息</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">教练员名称：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">教练车编号：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">教练车车牌号码：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">计时终端号码：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
			<tr class="datagrid-head-row classify-tr">
				<td colspan="4">注销信息</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">教学日志编号：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">课程方式：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">培训部分：</td>
				<td class="view_table_right">${v.name }</td>
				<td class="view_table_left">培训项目：</td>
				<td class="view_table_right">${v.name}</td>
			</tr>
		</table>
	</div>
</body>
</html>