<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div id="dataEdit-apply-add" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">经办人信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						${v.agent}			
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						${v.agentMobileNo}
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right">
						${v.agentIdentity}					
					</td>
				</tr>

				<c:if test="${fn:contains(modifyTypes, '10') || fn:contains(modifyTypes, '11') || fn:contains(modifyTypes, '12') || fn:contains(modifyTypes, '13')}">
				<tr class="datagrid-row">
					<c:if test="${fn:contains(modifyTypes, '11')}">
						<td class="view_table_left">新的经办人：</td>
						<td class="view_table_right">
						<input type="text" name="agent" class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '12')}">
					<td class="view_table_left">新的经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" 
						class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '13')}">
					<td class="view_table_left">新的经办人身份证号：</td>
					<td class="view_table_right">
						<input type="text" name="agentIdentity" 
						class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
				</tr>
				</c:if>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<!-- <tr class="datagrid-header-row">
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right" colspan="5" >					
						自办
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						${v.vehicleOwner}
					</td>
					<td class="view_table_left">车主手机号码：</td>
					<td class="view_table_right">
						${v.mobile}
					</td>
					<td class="view_table_left">车主身份证号码：</td>
					<td class="view_table_right">
						${v.vehicleOwnerIdentity}
					</td>
				</tr>
				<!-- <tr>
					<td class="view_table_left">邮政编码：</td>
					<td class="view_table_right">
						850050
					</td>
					<td class="view_table_left">联系地址：</td>
					<td class="view_table_right">
						广东省深圳市福田区深南中路2018号兴华大厦商业十一层11-G号						
					</td>
					<td></td><td></td>	
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right">
						${v.bankAccountName}				
					</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right">
						${v.bankName}
					</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right">
						${v.bankAccountNo}				
					</td>
				</tr>
				
				<c:if test="${fn:contains(modifyTypes, '20') || fn:contains(modifyTypes, '21') || fn:contains(modifyTypes, '22')}">
				<tr id="accountInfo" class="datagrid-row">
					<c:if test="${fn:contains(modifyTypes, '21')}">
					<td class="view_table_left">新的开户银行：</td>
					<td class="view_table_right">
						<input id="bankCodeNew" class="easyui-combobox" name="bankCode" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=BANK_CODE',panelHeight:150"/>
						<input type="hidden" name="bankName" />
						<!-- <input type="text" 
						name="bankName" class="easyui-validatebox"
						data-options="required:true" /> -->
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '21')}">
					<td class="view_table_left">新的开户银行账号：</td>
					<td class="view_table_right"><input type="text"
						name="bankAccountNo" class="easyui-validatebox"
						data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
				</tr>
				</c:if>
			</table>
		</div>
				
				
		<form id="form-modify-apply-upload" action="eliminatedModify/fileUpload.do" method="post" enctype="multipart/form-data">
		<div id="div-modify-apply-upload" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<c:if test="${fn:contains(modifyTypes, '30') || fn:contains(modifyTypes, '31') || fn:contains(modifyTypes, '32') || fn:contains(modifyTypes, '33') || fn:contains(modifyTypes, '34') || fn:contains(modifyTypes, '35') || fn:contains(modifyTypes, '36') || fn:contains(modifyTypes, '37') || fn:contains(modifyTypes, '38') || fn:contains(modifyTypes, '39')}">
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">证明材料</td>
				</tr>
				<c:if test="${fn:contains(modifyTypes, '31')}">
				<c:if test="${!empty callbackFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原报废汽车回收证明：</td>
					<td class="view_table_right">
						<c:forEach items="${callbackFiles}" var="callbackFile">
							<a href="${callbackFile.filePath}" target="_blank">${callbackFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废汽车回收证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="callbackProofFiles" name="callbackProofFiles" data-options="editable:false,required:false,buttonText:'请选择'"
						class="easyui-filebox" />
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoCallbackProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="callbackProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '32')}">
				<c:if test="${!empty vehicleCancelProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原机动车注销证明：</td>
					<td class="view_table_right">
						<c:forEach items="${vehicleCancelProofFiles}" var="vehicleCancelProofFile">
							<a href="${vehicleCancelProofFile.filePath}" target="_blank">${vehicleCancelProofFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">机动车注销证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleCancelProof" class="easyui-filebox" name="vehicleCancelProof" data-options="editable:false,required:false,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleCancelProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleCancelProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '34')}">
				<c:if test="${!empty bankCardFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原银行卡：</td>
					<td class="view_table_right">
						<c:forEach items="${bankCardFiles}" var="bankCardFile">
							<a href="${bankCardFile.filePath}" target="_blank">${bankCardFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">银行卡：</td>
					<td class="view_table_right" colspan="2">
						<input id="bankCard" class="easyui-filebox" name="bankCard" data-options="editable:false,required:false,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoBankCardUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="bankCardFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '33')}">
				<c:if test="${!empty vehicleOwnerProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原车主身份证明：</td>
					<td class="view_table_right">
						<c:forEach items="${vehicleOwnerProofFiles}" var="vehicleOwnerProofFile">
							<a href="${vehicleOwnerProofFile.filePath}" target="_blank">${vehicleOwnerProofFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">车主身份证明：</td>
					<td class="view_table_right" colspan="2">
						<input type="file" name="vehicleOwnerProof" multiple="multiple" />
						<!-- <input id="vehicleOwnerProof" class="easyui-filebox" name="vehicleOwnerProof" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleOwnerProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleOwnerProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${v.isProxy eq 'N'}">
				<c:if test="${fn:contains(modifyTypes, '36')}">
				<c:if test="${!empty agentProxyFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原代理委托书：</td>
					<td class="view_table_right">
						<c:forEach items="${agentProxyFiles}" var="agentProxyFile">
							<a href="${agentProxyFile.filePath}" target="_blank">${agentProxyFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">代理委托书：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProxy" class="easyui-filebox" name="agentProxy" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProxyProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProxyFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '37')}">
				<c:if test="${!empty agentProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原代理人身份证：</td>
					<td class="view_table_right">
						<c:forEach items="${agentProofFiles}" var="agentProofFile">
							<a href="${agentProofFile.filePath}" target="_blank">${agentProofFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">代理人身份证：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProof" class="easyui-filebox" name="agentProof" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProofFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				</c:if>
				<c:if test="${v.isPersonal eq 'N'}">
				<c:if test="${fn:contains(modifyTypes, '35')}">
				<c:if test="${!empty noFinanceProvideFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原非财政供养单位证明：</td>
					<td class="view_table_right">
						<c:forEach items="${noFinanceProvideFiles}" var="noFinanceProvideFile">
							<a href="${noFinanceProvideFile.filePath}" target="_blank">${noFinanceProvideFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">非财政供养单位证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="noFinanceProvide" class="easyui-filebox" name="noFinanceProvide" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoNoFinanceProvideUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="noFinanceProvideFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '38')}">
				<c:if test="${!empty openAccPromitFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原开户许可证：</td>
					<td class="view_table_right">
						<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile">
							<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}</a>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">开户许可证：</td>
					<td class="view_table_right" colspan="2">
						<input type="file" name="openAccPromit" multiple="multiple" />
						<!-- <input id="openAccPromit" class="easyui-filebox" name="openAccPromit" data-options="editable:false,required:true,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoOpenAccPromitUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="openAccPromitFileImg" href="#" target="_blank"></a>
					</td>
				</tr>
				</c:if>
				</c:if>
				<!-- <tr class="datagrid-row filesInfo">
					<td class="view_table_left vehicleRegFile" style="width:120px">签字确认的受理表：</td>
					<td class="view_table_right vehicleRegFile">
						<input class="easyui-filebox" name="signedApplyFile" data-options="required:true,buttonText:'请选择'"/>
						<font color="red">&nbsp;*&nbsp;</font>
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td align="center" colspan="6">
						<a id="btnUploadUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">上传</a>
					</td>
				</tr>
				</c:if>
				
				<tr class="datagrid-header-row" style="color:red">
					<td class="view_table_left">修正结果：</td>
					<td class="view_table_right">
						<input type="radio" name="modifyResult" value="1"/>结束修正
						<input type="radio" name="modifyResult" value="2"/>继续修正
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<script type="text/javascript">
		
			$(function() {
				//var basePath = $("#basePath").val();
				
				// 默认选择结束修正
				$("input[type='radio'][name='modifyResult'][value='1']").attr("checked", true);
				
				var basePath = "<%=basePath%>";
				
				var id = ${v.id};
				var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					
				// 重新调整页面窗口大小和位置居中
				/* $("#common-dialog").dialog({
					title : "申报受理录入修改"
				}).dialog("resize", {
					width : 1132,
					height : 800 
				}).dialog("center"); */
				
				// 隐藏保存按钮
				$("#common-dialog-save").hide();
				$("#common-dialog-close").hide();
				
				// 设置已经上传的图片路径(后期优化预览图片效果)
				// 选择银行名称
				/* $("#bankCodeNew").combobox({
						editable : false,
						reqiured : true,
						valueField : 'code',
						textField : 'value',
						panelHeight : 150
					});
				
				$("#bankCodeNew").combobox("reload", basePath + '/sysDict/getDictListFromMap.do?dictType=BANK_CODE');
				
				var bankCode = '${v.bankCode}';
				$("#bankCodeNew").combobox("setValue", bankCode); */
				
			   //报废回收证明、机动车注销证明、银行卡、车主身份证明、代理委托书、代理人身份证、开户许可证、非财政供养单位证明
			   $("#btnUploadUpdate").click(function() {
				   
				   var isPersonal = '${v.isPersonal}';
				   var isProxy = '${v.isProxy}';
				   
				   var modifyTypes = '${modifyTypes}';
				   
				   //文件框页面校验，必填
				   var ifValid = $("#form-modify-apply-upload form").form("enableValidation").form("validate");
				   
				   if (ifValid) {
					
					   // 校验文件是否上传
					   if (!checkAttachments(modifyTypes)) {
						   alert("还有证明材料没有上传，请先上传！");
						   return false;
					   }
					   
					   var url =  $("#form-modify-apply-upload").attr("action")+"?isPersonal="+isPersonal+"&isProxy="+isProxy;
					   $("#form-modify-apply-upload").form({
		        			url  : url,
		        		 	success : function(result) {
		        		 		var result = eval('(' + result + ')');
		        		 		if (result.success) {
		        		 			alert("文件上传成功！");
		        					$("#callbackProofFileImg").text("报废汽车回收证明");
		        					$("#callbackProofFileImg").attr("href", basePath+'/'+result.message.callbackProofFiles);
		        					$("input[name='callbackProofFile']").val(result.message.callbackProofFiles);
		        					
		        					$("#vehicleCancelProofFileImg").text("机动车注销证明");
		        					$("#vehicleCancelProofFileImg").attr("href", basePath+'/'+result.message.vehicleCancelProof);
		        					$("input[name='vehicleCancelProofFiles']").val(result.message.vehicleCancelProof);
		        					
		        					$("#bankCardFileImg").text("银行卡");
		        					$("#bankCardFileImg").attr("href", basePath+'/'+result.message.bankCard);
		        					$("input[name='bankCardFiles']").val(result.message.bankCard);
		        					
		        					$("#vehicleOwnerProofFileImg").text("车主身份证明");
		        					$("#vehicleOwnerProofFileImg").attr("href", basePath+'/'+result.message.vehicleOwnerProof);
		        					$("input[name='vehicleOwnerProofFiles']").val(result.message.vehicleOwnerProof);
		        					
		        					// 办理类型为代办
		        					if (isProxy == "N") {
			        					$("#agentProxyFileImg").text("代理委托书");
			        					$("#agentProxyFileImg").attr("href", basePath+'/'+result.message.agentProxy);
			        					$("input[name='agentProxyFiles']").val(result.message.agentProxy);
			        					
			        					$("#agentProofFileImg").text("代理人身份证");
			        					$("#agentProofFileImg").attr("href", basePath+'/'+result.message.agentProof);
			        					$("input[name='agentProofFiles']").val(result.message.agentProof);
		        					}
		        					
		        					// 车主类型为企业
		        					if (isPersonal == "N") {
		        						$("#noFinanceProvideFileImg").text("非财政供养单位证明");
			        					$("#noFinanceProvideFileImg").attr("href", basePath+'/'+result.message.noFinanceProvide);
			        					$("input[name='noFinanceProvideFiles']").val(result.message.noFinanceProvide);
			        					
			        					$("#openAccPromitFileImg").text("开户许可证");
			        					$("#openAccPromitFileImg").attr("href", basePath+'/'+result.message.openAccPromit);
			        					$("input[name='openAccPromitFiles']").val(result.message.openAccPromit);
		        					}
		        					
		        		 		} else {
		        		 			alert("文件上传失败！");
		        		 			//$('#form-apply-upload')[0].reset();
		        		 			
		        		 			$("#callbackProofFile").filebox("clear");
		        		 			$("#vehicleCancelProof").filebox("clear");
		        		 			$("#bankCard").filebox("clear");
		        		 			
		        		 			var jVehicleOwnerProof = $("#vehicleOwnerProof")   
		        		 			jVehicleOwnerProof.after(jVehicleOwnerProof.clone().val(""));     
		        		 			jVehicleOwnerProof.remove();
		        		 			
		        		 			//$("#vehicleOwnerProof").filebox("clear");
		        		 			if (isPersonal == "N") {
			        		 			$("#agentProxy").filebox("clear");
			        		 			$("#agentProof").filebox("clear");
		        		 			}
		        		 			if (isProxy == "N") {
		        		 				$("#noFinanceProvide").filebox("clear");
		        		 				var jOpenAccPromit = $("#openAccPromit")   
			        		 			jOpenAccPromit.after(jOpenAccPromit.clone().val(""));     
		        		 				jOpenAccPromit.remove();
		        		 				//$("#openAccPromit").filebox("clear");
		        		 			} 
		        		 		}
		        			}
		        	 	});
					 
					   $("#form-modify-apply-upload").submit();
				   } else {
					   alert("还有证明材料未选择，请选择后上传！");
					   return false;
				   }
	        	 	
		        });
				
				// 报废回收证明抓拍上传
				$("#btnTakePhotoCallbackProofUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#callbackProofFileImg").text("报废回收证明(1)");
	        			$("#callbackProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#callbackProofFileImg"+i+"' href='"+filepath+"' target='_blank'>报废回收证明("+i+")</a>";
		        				$("#callbackProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='callbackProofFile']").val(parentValue.filepath);
		        	}
				});
				
				// 机动车注销证明
				$("#btnTakePhotoVehicleCancelProofUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#vehicleCancelProofFileImg").text("机动车注销证明(1)");
	        			$("#vehicleCancelProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#vehicleCancelProofFileImg"+i+"' href='"+filepath+"' target='_blank'>机动车注销证明("+i+")</a>";
		        				$("#vehicleCancelProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='vehicleCancelProofFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 车主身份证明
				$("#btnTakePhotoVehicleOwnerProofUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#vehicleOwnerProofFileImg").text("车主身份证明(1)");
	        			$("#vehicleOwnerProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#vehicleOwnerProofFileImg"+i+"' href='"+filepath+"' target='_blank'>车主身份证明("+i+")</a>";
		        				$("#vehicleOwnerProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='vehicleOwnerProofFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 银行卡
				$("#btnTakePhotoBankCardUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#bankCardFileImg").text("银行卡(1)");
	        			$("#bankCardFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#bankCardFileImg"+i+"' href='"+filepath+"' target='_blank'>银行卡("+i+")</a>";
		        				$("#bankCardFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='bankCardFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 非财政供养单位证明
				$("#btnTakePhotoNoFinanceProvideUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#noFinanceProvideFileImg").text("非财政供养单位证明(1)");
	        			$("#noFinanceProvideFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#noFinanceProvideFileImg"+i+"' href='"+filepath+"' target='_blank'>非财政供养单位证明("+i+")</a>";
		        				$("#noFinanceProvideFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='noFinanceProvideFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 开户许可证
				$("#btnTakePhotoOpenAccPromitUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#openAccPromitFileImg").text("开户许可证(1)");
	        			$("#openAccPromitFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#openAccPromitFileImg"+i+"' href='"+filepath+"' target='_blank'>开户许可证("+i+")</a>";
		        				$("#openAccPromitFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='openAccPromitFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 代理委托书
				$("#btnTakePhotoAgentProxyProofUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#agentProxyFileImg").text("代理委托书(1)");
	        			$("#agentProxyFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#agentProxyFileImg"+i+"' href='"+filepath+"' target='_blank'>代理委托书("+i+")</a>";
		        				$("#agentProxyFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='agentProxyFiles']").val(parentValue.filepath);
		        	}
				});
				
				// 代理人身份证
				$("#btnTakePhotoAgentProofUpdate").click(function() {
					var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		var files = parentValue.filepath.split(",");
		        		// 多张图片预览
		        		$("#agentProofFileImg").text("代理人身份证(1)");
	        			$("#agentProofFileImg").attr("href", basePath+'/'+files[0]);
		        		if (files.length > 1) {
		        			for (var i = 2 ; i <= files.length ; i ++) {
		        				var filepath = basePath + '/' + files[i-1];
		        				var _a = "&nbsp<a id='#agentProofFileImg"+i+"' href='"+filepath+"' target='_blank'>代理人身份证("+i+")</a>";
		        				$("#agentProofFileImg").append(_a);
		        			}
		        		} 
		        		
	        			// 设置隐藏字段传递到后台
	    				$("input[name='agentProofFiles']").val(parentValue.filepath);
		        	}
				});
				
			});
				
			function checkAttachments(modifyTypes) {
				var isOk = true;
				var isPersonal = '${v.isPersonal}';
			    var isProxy = '${v.isProxy}';
				
			    //alert(modifyTypes);
			    
		    	if (modifyTypes.includes("31")) {
			    	// 报废回收证明
					if ($("#callbackProofFiles").filebox("getValue") == "") {
						isOk = false;
					}
		    	}
				
		    	if (modifyTypes.includes("32")) {
					// 机动车注销证明
					if ($("#vehicleCancelProof").filebox("getValue").val() == "") {
						isOk = false;
					}
		    	}
		    	
		    	if (modifyTypes.includes("33")) {
					// 车主身份证明
					if ($("input[name='vehicleOwnerProof']").val() == "") {
						isOk = false;
					}
		    	}
		    	
		    	if (modifyTypes.includes("34")) {
					// 银行卡
					if ($("#bankCard").filebox("getValue").val() == "") {
						isOk = false;
					}
		    	}
				// 银行卡
				/* if ($("input[name='bankCardFiles']").val() == "") {
					isOk = false;
				} */
				
				// 企业需要非财政供养单位证明、开户许可证
				if (isPersonal == 'N') {
					if (modifyTypes.includes("35")) {
						if ($("#noFinanceProvide").filebox("getValue").val() == "") {
							isOk = false;
						}
					}
					if (modifyTypes.includes("38")) {
						if ($("input[name='openAccPromitFiles']").val() == "") {
							isOk = false;
						}
					}
				}
				// 代理需要代理委托书、代理人身份证
				if (isProxy == "N") {
					if (modifyTypes.includes("36")) {
						if ($("#agentProxy").filebox("getValue").val() == "") {
							isOk = false;
						}
					}
					if (modifyTypes.includes("37")) {
						if ($("#agentProof").filebox("getValue").val() == "") {
							isOk = false;
						}
					}
				}
				return isOk;
			}
		
		</script>
		
</body>
</html>