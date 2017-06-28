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
	<form action="payApply/exportPreview.do" enctype="multipart/form-data" method="post" id="confirmId">
		<br>
		<table id="searchTable"class="list_table" cellspacing="1" cellpadding="1" align="center">
		<tr>
		<td>
			<input type="button" value="预览" class="button" id="ConfirmButton">
		</td>
		</tr>
	</table>
	<form>
	
	<script type="text/javascript">

	$().ready(function(){
		$("#ConfirmButton").click(function(){
			var password=$("#password").val();
				 $("#confirmId").form("submit",{
						url : $("#confirmId").attr("action")+"?id="+'${v.id}'+"&batchNo="+'${v.batchNo}',
						success : function(data) {
							Messager.alert({
								type : "info",
								title : "提示",
								content : data.message
							})
						}
				}); 
		});
	});
	</script>
</body>
</html>