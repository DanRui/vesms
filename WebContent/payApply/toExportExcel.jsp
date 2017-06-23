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
<title>报财务导出</title>
</head>
<body>
	<form action="payApply/confirmBatchExcel.do" method="post" id="confirmId">
		<br>
		<table id="searchTable"class="list_table" cellspacing="1" cellpadding="1" align="center">
		<tr>
			<td>设置导出文件的加密密码:</td>
			<td>
				<input id="password" name="password" type="text" class="easyui-validatebox" data-options="required:true"/>
			</td>
			<a id="excel_file"></a>
		</tr>
		<tr>
		<td>
			<input type="button" value="导出" class="button" id="ConfirmButton">
		</td>
		</tr>
	</table>
	<form>
	
	<script type="text/javascript">
	$().ready(function(){
		
		$("#excel_file").hide();
		
		$("#ConfirmButton").click(function(){
			var password=$("#password").val();
			if (password!=""){
			$("#confirmId").form("submit", {
				url : $("#confirmId").attr("action")+"?id="+'${v.id}'+"&toFinanceNo="+'${v.toFinanceNo}'+"&batchNo="+'${v.batchNo}'+"&password="+password,
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data.success) {
						Messager.alert({
							type : 'info',
							title : '&nbsp',
							content : "批次报财务成功"
						});
						
						$("#excel_file").show();
						var excelPath = '<%=basePath%>/' + data.message;
						$("#excel_file").attr("href", excelPath);
						$("#excel_file").text("批次文件，请下载");
						
					} else {
						Messager.alert({
							type : 'error',
							title : '&nbsp',
							content : data.message
						});
					}
				}
			});
			}else {
				alert("请设置有效密码");
			}
		});
	});
	</script>
</body>
</html>