<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导出预览</title>
</head>
<body>
	<form action="payApply/exportList.do" enctype="multipart/form-data" method="post" id="formId">
		<br>
		<table id="searchTable"class="list_table" cellspacing="1" cellpadding="1">
		<tr><td>
				<input type="button" value="导出预览" class="button" id="exportButton">
		</td>
		</tr>
	</table>
	<form>
	
	<script type="text/javascript">
	$().ready(function(){
		$("#exportButton").click(function(){
			$("#formId").form("submit", {
				url : $("#formId").attr("action")+"?id="+'${v.id}'+"&batchNo="+'${v.batchNo}',
				success : function(data) {
					alert("导出成功");
				}
			});
		});
	});
	</script>
</body>
</html>