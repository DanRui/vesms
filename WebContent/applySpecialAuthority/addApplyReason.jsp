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
	<div title="填写申请原因" class="datagrid-body" data-options="selected:true">
		<div class="datagrid-header" style="overflow:hidden">
			<table class="datagrid-table-s datagrid-htable">
				<!-- <tr class="datagrid-row">
					<td class="view_table_left">审批操作：</td>
					<td class="view_table_right">
						<select id="checkType" name="checkType" class="easyui-combobox" data-options="editable:false,required:true,width:100,panelHeight:'auto'">   
   							<option>请选择</option>   
   							<option value="1">审核通过</option>   
   							<option value="2">审核不通过</option>
   							<option value="-1">终结</option>   
						</select>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">申请具体原因：</td>
					<td class="view_table_right" colspan="3">
						<textarea id="applyReason" name="applyReason" rows="3" class="easyui-validatebox" style="width: 450px"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var basePath = "<%=basePath%>";
			var applyId = "${id}";
			
			$("#applyReason").validatebox({required:true});
			
			// 保存提交，后台新增受理授权表
			$("#apply_reason_confirm").click(function() {
				var applyReason = $("#applyReason").val();
				
				if (applyReason == "") {
					alert("请填写申请具体原因！");
					return false;
				}
				
				$.ajax({
	                //cache: true,
	                type : "POST",
	                url : basePath+"/applySpecialAuthority/addApply.do?id="+applyId,
	                data : {"applyReason":applyReason},//授权申请原因，传入后台
	                async : false,
	                success : function(data) {
	                	
	                	if(Object.prototype.toString.call(data) === "[object String]") {
							data = eval("(" + data + ")");
						}
						
						if(data.success) {
							
							Messager.alert({
								type : "info",
								title:"&nbsp;",
								content:data.message.msg
							});
							
							// 授权申请成功
							$("#apply_reason").dialog("close");
							$("#common-dialog").dialog("close");
							$("#apply-auth-list #apply-auth-grid").datagrid('load');
	                	} else {
	                		Messager.alert({
								type : "error",
								title:"&nbsp;",
								content:data.message
							});
	                		
	                		// 授权申请失败
	                		$("#apply_reason").dialog("close");
	                		
	                	}
	                }
				});
			});
			
		});
	</script>
	
</body>
</html>