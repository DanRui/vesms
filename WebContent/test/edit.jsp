<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ page isELIgnored = "false" %>
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
	<form action="test/update.do"  method="post"  enctype="multipart/form-data">
		<div class="datagrid-header" id="test-add-table-toolbar" style="padding-top:20px">
			<table class="datagrid-htable datagrid-table-s ">
				<tr  class="datagrid-header-row" >
					<td class = "view_table_left" >ID：</td>
					<td class = "view_table_right">
						<input type = "text" name = "id" class = "numberbox easyui-validatebox" data-options= "required:true" validType = "integer" value = "${v.id }"/>
					</td>	
				</tr>
				<tr  class="datagrid-header-row" >
					<td class = "view_table_left">名称：</td>
					<td class = "view_table_right"">
						<input type = "text" name = "name" class = "textbox easyui-validatebox"  data-options="required:true" value = "${v.name }"/>
					</td>
				</tr>
				<tr class="datagrid-header-row" >
					<td class = "view_table_left">脚本</td>
					<td class = "view_table_right">
						<input type = "text" name = "scription" value = "${v.scription }" class = "combobox easyui-validatebox" editable = "false" required = "true" validType= "email"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>