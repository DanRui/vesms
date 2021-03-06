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
			<td>
				<a id="excel_file"></a>
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td>
				<a id="pdf_file"></a>
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td>
				<a id="preview_file"></a>
			</td>
		</tr>
		<tr>
		<td>
			<input type="button" value="生成文件" class="button" id="ConfirmButton">
		</td>
		</tr>
	</table>
	<form>
	
	<script type="text/javascript">
	$().ready(function(){
		
		$("#excel_file").hide();
		$("#pdf_file").hide();
		$("#ConfirmButton").click(function(){
			$.ajaxSetup({  
			    async : false  
			});  
			$("#ConfirmButton").attr("disabled","true"); //设置变灰按钮  
			$("#confirmId").form("submit", {
				url : $("#confirmId").attr("action")+"?id="+'${v.id}'+"&batchNo="+'${v.batchNo}'+"&batchType="+'${v.batchType}',
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data.success) {
						Messager.alert({
							type : 'info',
							title : '&nbsp',
							content : "批次报财务成功!"
						});
						
						$("#repFinance-list #repFinance-grid").datagrid("load");
						
						$("#excel_file").show();
						$("#pdf_file").show();
						$("#preview_file").show();
						var filepath = data.message.split(",");
						
						
						var excelPath = '<%=basePath%>/payApply/fileDownload.do?batchNo='+'${v.batchNo}'+'&filepath=' + filepath[0];
						$("#excel_file").attr("href", excelPath);
						$("#excel_file").text("批次报财务文件，点击下载");
						
						var pdfPath = '<%=basePath%>/payApply/fileDownload.do?batchNo='+'${v.batchNo}'+'&filepath=' + filepath[1];
						$("#pdf_file").attr("href", pdfPath);
						$("#pdf_file").text("批次图片文件，点击下载");
						
						var previewPath = '<%=basePath%>/payApply/fileDownload.do?batchNo='+'${v.batchNo}'+'&filepath=' + filepath[2];
						$("#preview_file").attr("href", previewPath);
						$("#preview_file").text("审批表文件，点击下载");
						
					} else {
						Messager.alert({
							type : 'error',
							title : '&nbsp',
							content : data.message
						});
					}
				}
			});
		});
	});
	</script>
</body>
</html>