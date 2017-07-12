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
				var modifyTypes = $("#modifyType").combotree("getValues");
				var id = '${v.id}';
				var modifyResult = $('input[name="modifyResult"]:checked').val();
				var bankCode = null;
				var bankName = null;
				if ($("#bankCodeNew").length > 0) {
					bankCode = $("#bankCodeNew").combobox("getValue");
					bankName = $("#bankCodeNew").combobox("getText");
					$("input[name='bankName']").val(bankName);
				}
				
				if(checkRequiredField(modifyTypes)) {
					//检查附件材料是否上传
					var hasUploaded = checkAttachments(modifyTypes);
					if (!hasUploaded) {
						alert("还有附件未上传，请抓拍上传！");
						return;
					}
					
					$("#form-apply-modify").form("submit", {
						url : $(this).attr("action"),
						onSubmit : function(param) {
							param.modifyTypes = modifyTypes;
							param.id = id;
							param.modifyResult = modifyResult;
							param.bankName = bankName;
							param.bankCode = bankCode;
							param.bankAccountNo = $("input[name='bankAccountNo']").val();
							param.agent = $("input[name='agent']").val();
							param.agentMobileNo = $("input[name='agentMobileNo']").val();
							param.agentIdentity = $("input[name='agentIdentity']").val();
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
				} else {
					alert("经办人信息或补贴账户信息未填写完整，请先填写！");
					return;
				}
				
			});
			
		});
		
		// 检查附件材料是否上传
		function checkAttachments(modifyTypeArray) {
			var isOk = true;
			var isPersonal = '${v.isPersonal}';
			var isProxy = '${v.isProxy}';
			
			for (var i = 0 ; i < modifyTypeArray.length; i ++) {
				if (modifyTypeArray[i] == "31") {
					// 机动车注销证明
					if ($("input[name='callbackProofFile']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "32") {
					// 机动车注销证明
					if ($("input[name='vehicleCancelProofFiles']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "33") {
					// 车主身份证明
					if ($("input[name='vehicleOwnerProofFiles']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "34") {
					// 银行卡
					if ($("input[name='bankCardFiles']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "35") {
					// 车主类型为企业
					if (isPersonal == 'N') {
						if ($("input[name='noFinanceProvideFiles']").val() == "") {
							isOk = false;
						}
					}
				}
				
				if (modifyTypeArray[i] == "36") {
					// 办理类型为代理
					if (isProxy == "N") {
						if ($("input[name='agentProxyFiles']").val() == "") {
							isOk = false;
						}
					}
				}
				
				if (modifyTypeArray[i] == "37") {
					// 办理类型为代理
					if (isProxy == "N") {
						if ($("input[name='agentProofFiles']").val() == "") {
							isOk = false;
						}
					}
				}
				
				if (modifyTypeArray[i] == "38") {
					// 车主类型为企业
					if (isPersonal == 'N') {
						if ($("input[name='openAccPromitFiles']").val() == "") {
							isOk = false;
						}
					}
				}
			}
			return isOk;
		}
		
		// 检查页面必填项
		function checkRequiredField(modifyTypeArray) {
			var isOk = true;
			for (var i = 0 ; i < modifyTypeArray.length; i ++) {
				if (modifyTypeArray[i] == "11") {
					// 经办人
					if ($("input[name='agent']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "12") {
					// 经办人手机号码
					if ($("input[name='agentMobileNo']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "13") {
					// 经办人身份证
					if ($("input[name='agentIdentity']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "21") {
					// 补贴账户开户行
					if ($("input[name='bankCode']").val() == "") {
						isOk = false;
					}
				}
				
				if (modifyTypeArray[i] == "22") {
					// 补贴账户卡号
					if ($("input[name='bankAccountNo']").val() == "") {
						isOk = false;
					}
				}
			}
			return isOk;
		}
		
	</script>
		
</body>
</html>