<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored = "false" %>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
 
	<form id="pay-res-check-form" action=basePath+"/payApply/payMark.do?ids"+ids+& method="post">
		<div id="pay-res-check" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-row">
					<td class="view_table_left">拨付操作：</td>
					<td class="view_table_right">
						<select id="checkType" name="checkType" class="easyui-combobox" data-options="required:true,editable:false,width:150,panelHeight:'auto'">   
   							<option>请选择</option>   
   							<option value="1">拨付成功</option>   
   							<option value="2">拨付失败</option>   
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left retreat">异常类型：</td>
					<td class="view_table_right retreat">
						<input id="faultType" name="faultType" class="easyui-combobox" data-options="editable:false,required:true,valueField:'id',textField:'text',url:'data/faultType.json',panelHeight:'auto'"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>  
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">备注：</td>
					<td class="view_table_right" colspan="3">
						<textarea id="checkOpinion" name="checkOpinion" rows="3" style="width: 450px" class="easyui-validatebox"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<script type="text/javascript">
		$(function() {
			$("td[class*='retreat']").each(function() {
				$(this).hide();
			});
			$("#checkType").combobox({
				onChange : function() {
					var checkedType = $(this).combobox("getValue");
					$("td[class*='retreat']").each(function() {
						if (checkedType == "2") {
							$(this).show();
							$("#checkOpinion").validatebox({required:true});
						}  else {
							$(this).hide();
							$("#checkOpinion").validatebox({required:false});
						}
					});
				}
			});
		});
	</script>
	
</body>
</html>