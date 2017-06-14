<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id = "vehicle-verify-query" class="easyui-panel easyui-panel-style" data-options="title: '资格查询'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header">
				<table id="vehicle-verify-query-table" style="width:100%;">
					<tr class="datagrid-row">
						<td class="view_table_left">号牌号码：</td>
						<td class="view_table_right">
							<input type="text" name="vehiclePlateNum" data-options="required:true" class="easyui-validatebox"/>
							<font color="red">&nbsp;*&nbsp;</font>
						</td>
					</tr>
					<tr>
						<td class="view_table_left">号牌种类：</td>
						<td class="view_table_right">
							<input id="vehiclePlateType" class="easyui-combobox" name="vehiclePlateType" 
							data-options="editable:false,required:true,valueField:'value',textField:'name',url:'data/vehiclePlateType.json',panelHeight:'auto'"/>
							<font color="red">&nbsp;*&nbsp;</font>
						</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">车辆运营类型：</td>
						<td class="view_table_right">
							<!-- <input id="vehicleType" class="easyui-combobox" name="vehicleType" 
							data-options="required:true,valueField:'value',textField:'name',url:'data/carType.json',panelHeight:'auto'"/> -->
							<select name="useOfProperty" data-options="width:100,editable:false,required:true,panelHeight:'auto'" class="easyui-combobox">
								<option>请选择</option>
								<option value="0">非营运</option>
								<option value="1">营运</option>
							</select>
							<font color="red">&nbsp;*&nbsp;</font>
						</td>
					</tr>
					<tr>
						<td class="view_table_left">车架号（后四位）：</td>
						<td class="view_table_right">
							<input type="text" name="vehicleIdentifyNo" class="easyui-numberbox" data-options="required:true"/>
							<font color="red">&nbsp;*&nbsp;</font>
						</td>
					</tr>
					<tr>
						<td class="view_table_left">交车日期：</td>
						<td class="view_table_right">
							<input type="text" name="recycleDate" class="easyui-datebox" data-options="editable:false,required:true"/>
							<font color="red">&nbsp;*&nbsp;</font>
						</td>
					</tr>
				</table>
				<a id="btnQuery" href="#" style="display:block;text-align:center" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#btnQuery").click(function(){
				
				$.messager.alert("信息","号牌号码:粤B6U4D7,号牌种类:蓝牌,车主:叶向东,车辆类型:小型载客车,补贴金额(元):7000");  
				
			})
			  
		});
	</script>
</body>
</html>