<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<form id="form-apply-modify" action="eliminatedModify/modifySave.do" method="post">
		<div id="choose-modify-type" class="datagrid-header">
			<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td class="view_table_right">请选择修正类型：</td>
					<td class="view_table_right">
						<input id="modifyType" class="easyui-combotree" name="modifyType" style="width:100%"
						data-options="editable:false,required:true,multiple:true,checkbox:true,url:'data/modifyType.json',panelHeight:'auto'"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<!-- <td class="view_table_right">
						<a id="btnConfirmModifyType" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
					</td> -->
				</tr>
			</table>
		</div>
		<!-- 报废汽车回收证明 -->
		<input type="hidden" name="callbackProofFile"/>
		<!-- 机动车注销证明 -->
		<input type="hidden" name="vehicleCancelProofFiles"/>
		<!-- 银行卡 -->
		<input type="hidden" name="bankCardFiles"/>
		<!-- 车主身份证明 -->
		<input type="hidden" name="vehicleOwnerProofFiles"/>
		<!-- 代理委托书 -->
		<input type="hidden" name="agentProxyFiles"/>
		<!-- 代理人身份证 -->
		<input type="hidden" name="agentProofFiles"/>
		<!-- 非财政供养单位证明 -->
		<input type="hidden" name="noFinanceProvideFiles"/>
		<!-- 开户许可证 -->
		<input type="hidden" name="openAccPromitFiles"/>
	</form>
	<div id="modify-apply" class="easyui-panel"/>
		
	<script type="text/javascript">
		$(function() {
			var basePath = $("#basePath").val();
			//获取所有可以修改的字段(补贴账户、经办人、证明资料)，上传至服务器
			/* $("#btnConfirmModifyType").click(function() {
				var selected = $("#modifyType").combotree("getValues");
				if (selected == "") {
					alert("请选择修正类型！");
					return false;
				}
				
			}); */
			
			$("#modifyType").combotree({
				onChange : function() {
					var selected = $(this).combotree("getValues");
					$("#modify-apply").panel({
						href : basePath+"/eliminatedModify/modifyApply.do?modifyTypes="+selected.toString()+"&id="+'${v.id}',
						onLoad : function() {
							//根据传递的值，显示相应的内容
							
						} 
					});
				}
			});
			
			
			// 点击提交按钮，更新数据
			$("#common-dialog-choose_modify_type").click(function() {
				var isValid = $("#form-apply-modify form").form("enableValidation").form("validate");
				var modifyTypes = $("#modifyType").combotree("getValues");
				var id = '${v.id}';
				var modifyResult = $('input[name="modifyResult"]:checked').val();
				//alert(modifyResult);
				
				if(isValid) {
					$("#form-apply-modify").form("submit", {
						url : $(this).attr("action"),
						onSubmit : function(param) {
							param.modifyTypes = modifyTypes;
							param.id = id;
							param.modifyResult = modifyResult;
						},
						success : function(data) {
							var result = eval('(' + data + ')');
	        		 		if (result.success) {
	        		 			Messager.alert({
	        		 				type : 'info',
	        		 				title : '&nbsp;',
	        		 				content : result.message.msg
	        		 			});
	        		 			//alert(result.message.msg);
	        		 			$("#common-dialog").dialog("close");
	        		 			
	        		 			$("#editData-apply-list #editData-apply-grid").datagrid("load");
	        		 			
	        		 			/* $.messager.defaults = { ok: "结束修正", cancel: "待继续修正" };
	        		 			Messager.confirm({
	        		 				title : "提示",
	        		 				content : "保存修改成功，请选择：",
	        		 				handler : function(result) {
	        		 					if (result) {
	        		 						// 结束修正，则更新流程到窗口会计初审岗
	        		 						$.ajax({
	        		 							url : basePath + '/eliminatedModify/modifySave.do',
	        		 							method : 'POST',
	        		 							data : {
	        		 										modifyTypes : modifyTypes,
	        		 										id : id
	        		 									},
	        		 							success : function(result) {
	        		 								result = eval('(' + result + ')');
	        		 								
	        		 								if (result.success) {
	        		 									$("#common-dialog").dialog("close");
	        		 								}
	        		 							}
	        		 						});
	        		 					} else {
	        		 					// 继续修正
        		 							//
        		 							$("#common-dialog").dialog("close");
	        		 					}
	        		 					
	        		 				}
	        		 			}); */
	        		 			
	        		 		} else {
	        		 			Messager.alert({
	        		 				type : 'error',
	        		 				title : '&nbsp;',
	        		 				content : result.message.msg
	        		 			});
	        		 			//alert(result.message.msg);
	        		 			$("#common-dialog").dialog("close");
	        		 			
	        		 			$("#editData-apply-list #editData-apply-grid").datagrid("load");
		        		 	}
						}
						
					});
				}
				
			});
			
		});
	</script>
		
</body>
</html>