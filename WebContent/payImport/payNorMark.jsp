<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored = "false" %>
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

	<form id="pay-res-check-form">
			<div id="pay-res-check" class="datagrid-header">
				<table class="datagrid-table-s datagrid-htable">
					<tr class="datagrid-row">
						<td class="view_table_left">拨付操作：</td>
						<td class="view_table_right">
							拨付成功
						</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">备注：</td>
						<td class="view_table_right" colspan="3">
							<textarea id="resultOpinion" name="resultOpinion" rows="3" style="width: 450px" class="easyui-validatebox"/>
						</td>
					</tr>
				</table>
			</div>
	</form>
</body>
</html>