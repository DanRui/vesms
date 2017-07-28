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
						<a id="btnVerifyAgentCard" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">识别</a>
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
						<input type="text" name="OtherBankName"/>
						<input type="hidden" name="bankName" />
						<!-- <input type="text" 
						name="bankName" class="easyui-validatebox"
						data-options="required:true" /> -->
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					</c:if>
					<c:if test="${fn:contains(modifyTypes, '22')}">
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
						<c:forEach items="${callbackFiles}" var="callbackFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('callbackProof', '${status.count}')">${callbackFile.name}</a>
							<input type="hidden" name="callbackProofFileImgOri" value="${callbackFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">报废汽车回收证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="callbackProofFiles" type="file" name="callbackProofFiles" required="required" multiple="multiple" />
						<!-- <input id="callbackProofFiles" name="callbackProofFiles" data-options="editable:false,required:false,buttonText:'请选择'"
						class="easyui-filebox" /> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoCallbackProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="callbackProofFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '32')}">
				<c:if test="${!empty vehicleCancelProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原机动车注销证明：</td>
					<td class="view_table_right">
						<c:forEach items="${vehicleCancelProofFiles}" var="vehicleCancelProofFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('vehicleCancelProof', '${status.count}')">${vehicleCancelProofFile.name}</a>
							<input type="hidden" name="vehicleCancelProofFileImgOri" value="${vehicleCancelProofFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">机动车注销证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleCancelProof" type="file" name="vehicleCancelProof" required="required" multiple="multiple" />
						<!-- <input id="vehicleCancelProof" class="easyui-filebox" name="vehicleCancelProof" data-options="editable:false,required:false,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleCancelProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleCancelProofFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '34')}">
				<c:if test="${!empty bankCardFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原银行卡：</td>
					<td class="view_table_right">
						<c:forEach items="${bankCardFiles}" var="bankCardFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('bankCard', '${status.count}')">${bankCardFile.name}</a>
							<input type="hidden" name="bankCardFileImgOri" value="${bankCardFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">银行卡：</td>
					<td class="view_table_right" colspan="2">
						<input id="bankCard" type="file" name="bankCard" required="required" multiple="multiple" />
						<!-- <input id="bankCard" class="easyui-filebox" name="bankCard" data-options="editable:false,required:false,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoBankCardUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="bankCardFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '33')}">
				<c:if test="${!empty vehicleOwnerProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原车主身份证明：</td>
					<td class="view_table_right">
						<c:forEach items="${vehicleOwnerProofFiles}" var="vehicleOwnerProofFile" varStatus="status">
							<c:if test="${status.index % 2 eq 1}">
								<a href="javascript:void(0)" onclick="preview('vehicleOwnerProof', '${status.count}')">${vehicleOwnerProofFile.name}</a></br>
								<input type="hidden" name="vehicleOwnerProofFileImgOri" value="${vehicleOwnerProofFile.filePath}"/>
							</c:if>
							<c:if test="${status.index % 2 eq 0}">
								<a href="javascript:void(0)" onclick="preview('vehicleOwnerProof', '${status.count}')">${vehicleOwnerProofFile.name}</a>
								<input type="hidden" name="vehicleOwnerProofFileImgOri" value="${vehicleOwnerProofFile.filePath}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">车主身份证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="vehicleOwnerProof" type="file" name="vehicleOwnerProof" required="required" multiple="multiple" />
						<!-- <input id="vehicleOwnerProof" class="easyui-filebox" name="vehicleOwnerProof" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoVehicleOwnerProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="vehicleOwnerProofFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${v.isProxy eq 'N'}">
				<c:if test="${fn:contains(modifyTypes, '36')}">
				<c:if test="${!empty agentProxyFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原代理委托书：</td>
					<td class="view_table_right">
						<c:forEach items="${agentProxyFiles}" var="agentProxyFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('agentProxy', '${status.count}')">${agentProxyFile.name}</a>
							<input type="hidden" name="agentProxyFileImgOri" value="${agentProxyFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">代理委托书：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProxy" type="file" name="agentProxy" required="required" multiple="multiple" />
						<!-- <input id="agentProxy" class="easyui-filebox" name="agentProxy" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProxyProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProxyFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '37')}">
				<c:if test="${!empty agentProofFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原代理人身份证：</td>
					<td class="view_table_right">
						<c:forEach items="${agentProofFiles}" var="agentProofFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('agentProof', '${status.count}')">${agentProofFile.name}</a>
							<input type="hidden" name="agentProofFileImgOri" value="${agentProofFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">代理人身份证：</td>
					<td class="view_table_right" colspan="2">
						<input id="agentProof" type="file" name="agentProof" required="required" multiple="multiple" />
						<!-- <input id="agentProof" class="easyui-filebox" name="agentProof" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoAgentProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="agentProofFileImg" href="#"></a>
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
						<c:forEach items="${noFinanceProvideFiles}" var="noFinanceProvideFile" varStatus="status">
							<a href="javascript:void(0)" onclick="preview('noFinanceProvide', '${status.count}')">${noFinanceProvideFile.name}</a>
							<input type="hidden" name="noFinanceProvideFileImgOri" value="${noFinanceProvideFile.filePath}"/>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">非财政供养单位证明：</td>
					<td class="view_table_right" colspan="2">
						<input id="noFinanceProvide" type="file" name="noFinanceProvide" required="required" multiple="multiple" />
						<!-- <input id="noFinanceProvide" class="easyui-filebox" name="noFinanceProvide" data-options="editable:false,required:false,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoNoFinanceProvideUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="noFinanceProvideFileImg" href="#"></a>
					</td>
				</tr>
				</c:if>
				<c:if test="${fn:contains(modifyTypes, '38')}">
				<c:if test="${!empty openAccPromitFiles}">
				<tr class="datagrid-row">
					<td class="view_table_left">原开户许可证：</td>
					<td class="view_table_right">
						<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile" varStatus="status">
							<c:if test="${status.index % 2 eq 1}">
								<a href="javascript:void(0)" onclick="preview('openAccPromit', '${status.count}')">${openAccPromitFile.name}</a></br>
								<input type="hidden" name="openAccPromitFileImgOri" value="${openAccPromitFile.filePath}"/>
							</c:if>
							<c:if test="${status.index % 2 eq 0}">
								<a href="javascript:void(0)" onclick="preview('openAccPromit', '${status.count}')">${openAccPromitFile.name}</a>
								<input type="hidden" name="openAccPromitFileImgOri" value="${openAccPromitFile.filePath}"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:110px">开户许可证：</td>
					<td class="view_table_right" colspan="2">
						<input id="openAccPromit" type="file" name="openAccPromit" required="required" multiple="multiple" />
						<!-- <input id="openAccPromit" class="easyui-filebox" name="openAccPromit" data-options="editable:false,required:true,width:141,buttonText:'请选择'"/> -->
						<font color="red">&nbsp;*&nbsp;</font>
						<a id="btnTakePhotoOpenAccPromitUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
					</td>
					<td class="view_table_right" colspan="3">
						<a id="openAccPromitFileImg" href="#"></a>
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
						<a id="btnUploadUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">本地文件上传</a>
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
					<td colspan="4"></td>
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
				
				$("input[name='OtherBankName']").hide();
				
				$("#bankCodeNew").combobox({
					onSelect : function(rec) {
						if (rec.code == "999") {
							$("input[name='OtherBankName']").attr("required", true);
							$("input[name='OtherBankName']").show();
						} else {
							$("input[name='OtherBankName']").attr("required", false);
							$("input[name='OtherBankName']").hide();
						}
					}
				});
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
				
				// 设置银行选择“其它银行”时，显示输入框，并且必填
				
				// 经办人身份证识别
				$("#btnVerifyAgentCard").click(function() {
					var idCard = ReadIDCard();
					
					$("input[name='agentIdentity']").val(idCard);
					
					var isProxy = "${v.isProxy}";
					
					var vehicleOwnerIdentity = "${v.vehicleOwnerIdentity}";
					
					var agentIdentity = $("input[name='agentIdentity']").val();
					
					if (vehicleOwnerIdentity != "" && agentIdentity != "") {
						if (isProxy == "Y" && (idCard != vehicleOwnerIdentity || agentIdentity != idCard)) {
							alert("办理类型为自办，经办人身份证号必须与车主一致！");
							$("input[name='agentIdentity']").val("");
						} else if (isProxy == "N" && (vehicleOwnerIdentity == agentIdentity || idCard == vehicleOwnerIdentity)) {
							alert("办理类型为代理，经办人身份证号必须与车主身份证明号不同！");
							$("input[name='agentIdentity']").val("");
						}
					}
					
					//alert(idCard);
				});
				
			   //报废回收证明、机动车注销证明、银行卡、车主身份证明、代理委托书、代理人身份证、开户许可证、非财政供养单位证明
			   $("#btnUploadUpdate").click(function() {
				   
				   var isPersonal = '${v.isPersonal}';
				   var isProxy = '${v.isProxy}';
				   
				   var modifyTypes = '${modifyTypes}';
				   var modifyTypesArray = modifyTypes.split(",");
				   
				   //文件框页面校验，必填
				   var ifValid = $("#form-modify-apply-upload form").form("enableValidation").form("validate");
				   
				   if (ifValid) {
					
					   // 校验文件是否上传
					   if (!checkAttachmentsForUpload(modifyTypesArray)) {
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
		        		 			
		        		 			// 设置文件回显、页面隐藏路径、点击事件等
		        		 			setFilePreview("callbackProof", result.message.callbackProofFiles, "报废回收证明");
		        		 			
		        		 			setFilePreview("vehicleCancelProof", result.message.vehicleCancelProof, "机动车注销证明");
		        		 			
		        		 			setFilePreview("bankCard", result.message.bankCard, "银行卡");
		        		 			
		        		 			setFilePreview("vehicleOwnerProof", result.message.vehicleOwnerProof, "车主身份证明");
		        		 			
		        					/* $("#callbackProofFileImg").text("报废汽车回收证明");
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
		        					 */
		        					// 办理类型为代办
		        					if (isProxy == "N") {
										setFilePreview("agentProxy", result.message.agentProxy, "代理委托书");
		        						
		        						setFilePreview("agentProof", result.message.agentProof, "代理人身份证");
		        						
			        					/* $("#agentProxyFileImg").text("代理委托书");
			        					$("#agentProxyFileImg").attr("href", basePath+'/'+result.message.agentProxy);
			        					$("input[name='agentProxyFiles']").val(result.message.agentProxy);
			        					
			        					$("#agentProofFileImg").text("代理人身份证");
			        					$("#agentProofFileImg").attr("href", basePath+'/'+result.message.agentProof);
			        					$("input[name='agentProofFiles']").val(result.message.agentProof); */
		        					}
		        					
		        					// 车主类型为企业
		        					if (isPersonal == "N") {
										setFilePreview("noFinanceProvide", result.message.noFinanceProvide, "非财政供养单位证明");		        						
		        						setFilePreview("openAccPromit", result.message.openAccPromit, "开户许可证");		        						
		        						
		        						/* $("#noFinanceProvideFileImg").text("非财政供养单位证明");
			        					$("#noFinanceProvideFileImg").attr("href", basePath+'/'+result.message.noFinanceProvide);
			        					$("input[name='noFinanceProvideFiles']").val(result.message.noFinanceProvide);
			        					
			        					$("#openAccPromitFileImg").text("开户许可证");
			        					$("#openAccPromitFileImg").attr("href", basePath+'/'+result.message.openAccPromit);
			        					$("input[name='openAccPromitFiles']").val(result.message.openAccPromit); */
		        					}
		        					
		        		 		} else {
		        		 			alert(result.message.msg);
		        		 			//$('#form-apply-upload')[0].reset();
		        		 			
		        		 			clearUploadFiles(isPersonal, isProxy);
		        		 			
		        		 			/* $("#callbackProofFiles").filebox("clear");
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
		        		 			} */ 
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
					captureImg();
					/*var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		setFilePreview("callbackProof", parentValue.filepath, "报废回收证明");
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
		        	}*/
				});
				
				// 机动车注销证明
				$("#btnTakePhotoVehicleCancelProofUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
		        	//alert(parentValue.filepath);
		        	
		        	// 抓拍返回，设置图片路径到页面字段
		        	if (typeof(parentValue) != "undefined" && parentValue.filepath != "") {
		        		setFilePreview("vehicleCancelProof", parentValue.JDCZXZM, "机动车注销证明");
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
		        	} */
				});
				
				// 车主身份证明
				$("#btnTakePhotoVehicleOwnerProofUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
				// 银行卡
				$("#btnTakePhotoBankCardUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
				// 非财政供养单位证明
				$("#btnTakePhotoNoFinanceProvideUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
				// 开户许可证
				$("#btnTakePhotoOpenAccPromitUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
				// 代理委托书
				$("#btnTakePhotoAgentProxyProofUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
				// 代理人身份证
				$("#btnTakePhotoAgentProofUpdate").click(function() {
					captureImg();
					/* var vehiclePlateNum = '${v.vehiclePlateNum}'; 
					// 弹出高拍仪抓拍图片界面
					var parentValue = window.showModalDialog("eliminatedModify/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "dialogWidth=700px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
	        	
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
		        	} */
				});
				
			});
				
			function checkAttachmentsForUpload(modifyTypeArray) {
				var isOk = true;
				var isPersonal = '${v.isPersonal}';
				var isProxy = '${v.isProxy}';
				
				for (var i = 0 ; i < modifyTypeArray.length; i ++) {
					if (modifyTypeArray[i] == "31") {
						// 报废回收证明
						if ($("input[name='callbackProofFiles']").val() == "") {
							isOk = false;
						}
						/* if ($("#callbackProofFiles").filebox("getValue") == "") {
							isOk = false;
						} */
					}
					
					if (modifyTypeArray[i] == "32") {
						// 机动车注销证明
						if ($("input[name='vehicleCancelProof']").val() == "") {
							isOk = false;
						}
						/* if ($("#vehicleCancelProof").filebox("getValue") == "") {
							isOk = false;
						} */
					}
					
					if (modifyTypeArray[i] == "33") {
						// 车主身份证明
						if ($("input[name='vehicleOwnerProof']").val() == "") {
							isOk = false;
						}
					}
					
					if (modifyTypeArray[i] == "34") {
						// 银行卡
						if ($("input[name='bankCard']").val() == "") {
							isOk = false;
						}
						/* if ($("#bankCard").filebox("getValue") == "") {
							isOk = false;
						} */
					}
					
					if (modifyTypeArray[i] == "35") {
						// 车主类型为企业
						if (isPersonal == 'N') {
							if ($("input[name='noFinanceProvide']").val() == "") {
								isOk = false;
							}
							/* if ($("#noFinanceProvide").filebox("getValue") == "") {
								isOk = false;
							} */
						}
					}
					
					if (modifyTypeArray[i] == "36") {
						// 办理类型为代理
						if (isProxy == "N") {
							if ($("input[name='agentProxy']").val() == "") {
								isOk = false;
							}
							/* if ($("#agentProxy").filebox("getValue") == "") {
								isOk = false;
							} */
						}
					}
					
					if (modifyTypeArray[i] == "37") {
						// 办理类型为代理
						if (isProxy == "N") {
							if ($("input[name='agentProof']").val() == "") {
								isOk = false;
							}
							/* if ($("#agentProof").filebox("getValue") == "") {
								isOk = false;
							} */
						}
					}
					
					if (modifyTypeArray[i] == "38") {
						// 车主类型为企业
						if (isPersonal == 'N') {
							if ($("input[name='openAccPromit']").val() == "") {
								isOk = false;
							}
						}
					}
				}
				return isOk;
			}
		
			// 清空文件上传框的值，使得可以重新选择文件进行上传
			function clearUploadFiles(isPersonal, isProxy) {
				$('#form-modify-apply-upload')[0].reset();
				
				if (isPersonal == 'N') {
					// 如果是企业，则清空页面开户许可证和非财政供养单位证明的文件路径
					$("input[name='noFinanceProvideFiles']").val("");

					$("input[name='openAccPromitFiles']").val("");
				}
				
				if (isProxy == 'N') {
					// 如果是代理，则清空页面代理人身份证明和代理委托书的文件路径
					$("input[name='agentProofFiles']").val("");

					$("input[name='agentProxyFiles']").val("");
				}
				
				$("input[name='callbackProofFile']").val("");

				$("input[name='vehicleCancelProofFiles']").val("");
				
				$("input[name='bankCardFiles']").val("");

				$("input[name='vehicleOwnerProofFiles']").val("");
				
				// 清空页面超链接
				
				/* if (isPersonal == 'N') {
					// 如果是企业，则清空开户许可证和非财政供养单位证明
					//$("#openAccPromit").filebox("setValue", "");
					//$("#noFinanceProvide").filebox("setValue", "");
					
					$("#openAccPromitFileImg").text("");
	    			$("#openAccPromitFileImg").attr("href", "#");
	    			
	    			$("#noFinanceProvideFileImg").text("");
	    			$("#noFinanceProvideFileImg").attr("href", "#");
				}
				if (isProxy == 'N') {
					// 如果是代办，则清空代理人身份证明和代理委托书
					//$("#agentProof").filebox("setValue", "");
					//$("#agentProxy").filebox("setValue", "");
					
					$("#agentProofFileImg").text("");
	    			$("#agentProofFileImg").attr("href", "#");
	    			
	    			$("#agentProxyFileImg").text("");
	    			$("#agentProxyFileImg").attr("href", "#");
				}
				//$("#callbackProofFiles").filebox("setValue", "");
				//$("#vehicleCancelProof").filebox("setValue", "");
				//$("#bankCard").filebox("setValue", "");
				//$("#vehicleOwnerProof").filebox("setValue", "");
				
				// 清空文件预览后的链接
				$("#callbackProofFileImg").text("");
    			$("#callbackProofFileImg").attr("href", "#");
    			
    			$("#vehicleCancelProofFileImg").text("");
    			$("#vehicleCancelProofFileImg").attr("href", "#");
    			
    			$("#bankCardFileImg").text("");
    			$("#bankCardFileImg").attr("href", "#");
    			
    			$("#vehicleOwnerProofFileImg").text("");
    			$("#vehicleOwnerProofFileImg").attr("href", "#"); */
			}
			
			// 图片集中预览
			function picturePreview(currentType, index) {
				var basePath = "<%=basePath%>";
				// 获取页面所有图片路径，传递到新窗口
				// 先获取页面已经上传的文件路径
				var proof_files = window;
				
				// 报废回收证明
				proof_files.callbackProofFile = $("input[name='callbackProofFile']").val();
				// 机动车注销证明
				proof_files.vehicleCancelProofFiles = $("input[name='vehicleCancelProofFiles']").val();
				// 银行卡
				proof_files.bankCardFiles = $("input[name='bankCardFiles']").val();
				// 车主身份证明
				proof_files.vehicleOwnerProofFiles = $("input[name='vehicleOwnerProofFiles']").val();
				// 开户许可证
				proof_files.openAccPromitFiles = $("input[name='openAccPromitFiles']").val();
				// 非财政供养单位证明
				proof_files.noFinanceProvideFiles = $("input[name='noFinanceProvideFiles']").val();
				// 代理人身份证
				proof_files.agentProofFiles = $("input[name='agentProofFiles']").val();
				// 代理委托书
				proof_files.agentProxyFiles = $("input[name='agentProxyFiles']").val();
				
				//alert("adadad");
				window.open(basePath + '/eliminatedApply/picturePreview.jsp?filetype='+currentType+'&index='+index,'新证明材料预览','height=300,width=400,top=200,left=400,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
			}
			
			// 根据文件路径设置回显和传递到后台的隐藏值
			function setFilePreview(type, files, name) {
				var basePath = "<%=basePath%>";
				
				if (files == null && files == "") {
					return;
				}
				
				var filesArray = files.split(",");
				var id = "#" + type + "FileImg";
				var filename;
				
				if (type == "callbackProof") {
					filename = type + "File";
				} else {
					filename = type + "Files";
				}
	    		// 多张图片预览
	    		$(id).text(name + "(1)");
	    		
	    		// 添加图片点击事件，到新窗口预览
	    		$(id).attr('href', 'javascript:void(0)');
	    		//$(id).attr('href', "javascript:picturePreview('"+type+"');");
	    		
	    		$(id).attr('onclick', "picturePreview('"+type+"', '1');");
	    		
				//$(id).attr("href", basePath+'/'+filesArray[0]);
				
	    		if (filesArray.length > 1) {
	    			for (var i = 2 ; i <= filesArray.length ; i ++) {
	    				//var filepath = basePath + '/' + filesArray[i-1];
	    				var filepath = "picturePreview('"+type+"', '"+ i +"');";
	    				var _a = "&nbsp<a id='"+id+i+"' href='javascript:void(0)' onclick='"+filepath+"'>"+name+"("+i+")</a>";
	    				$(id).append(_a);
	    			}
	    		} 
	    		
				// 设置隐藏字段传递到后台
				$("input[name='"+filename+"']").val(files);
			}
			
			// 根据原证明材料种类获取页面保存的路径
			function getFilepath(type) {
				var filepath = "";
				$("input[name='"+type+"FileImgOri']").each(function() {
					filepath = filepath + $(this).val() + ",";
					
					// 设置当前<a>标签的路径
					//$(this).attr("href", "javascript:void(0)");
				});
				
				if (filepath != "" && filepath.indexOf(",") != -1) {
					filepath = filepath.substring(0, filepath.length - 1);
				}
				
				return filepath;
			}
			
			// 根据图片种类，预览图片
			function preview(type, index) {
				var basePath = "<%=basePath%>";
				// 获取页面所有图片路径，传递到新窗口
				var proof_files = window;
				
				// 报废回收证明
				proof_files.callbackProofFile = getFilepath("callbackProof");
				// 机动车注销证明
				proof_files.vehicleCancelProofFiles = getFilepath("vehicleCancelProof");
				// 银行卡
				proof_files.bankCardFiles = getFilepath("bankCard");
				// 车主身份证明
				proof_files.vehicleOwnerProofFiles = getFilepath("vehicleOwnerProof");
				// 开户许可证
				proof_files.openAccPromitFiles = getFilepath("openAccPromit");
				// 非财政供养单位证明
				proof_files.noFinanceProvideFiles = getFilepath("noFinanceProvide");
				// 代理人身份证
				proof_files.agentProofFiles = getFilepath("agentProof");
				// 代理委托书
				proof_files.agentProxyFiles = getFilepath("agentProxy");
				// 签字确认的受理表
				proof_files.signedApplyFiles = getFilepath("signedApply");
				// 补贴对象变更证明材料
				proof_files.accountChangeProofFiles = getFilepath("accountChangeProof");
				
				//alert("adadad");
				window.open(basePath + '/eliminatedApply/picturePreview.jsp?filetype='+type+"&index="+index,'原证明材料预览','height=300,width=400,top=200,left=400,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
			}
			
			function captureImg() {
				// 先获取页面已经上传的文件路径
				var proof_files = new Object();
				
				// 报废回收证明
				proof_files.callbackProofFile = $("input[name='callbackProofFile']").val();
				// 机动车注销证明
				proof_files.vehicleCancelProofFiles = $("input[name='vehicleCancelProofFiles']").val();
				// 银行卡
				proof_files.bankCardFiles = $("input[name='bankCardFiles']").val();
				// 车主身份证明
				proof_files.vehicleOwnerProofFiles = $("input[name='vehicleOwnerProofFiles']").val();
				// 开户许可证
				proof_files.openAccPromitFiles = $("input[name='openAccPromitFiles']").val();
				// 非财政供养单位证明
				proof_files.noFinanceProvideFiles = $("input[name='noFinanceProvideFiles']").val();
				// 代理人身份证
				proof_files.agentProofFiles = $("input[name='agentProofFiles']").val();
				// 代理委托书
				proof_files.agentProxyFiles = $("input[name='agentProxyFiles']").val();
				
				// 号牌号码
				var vehiclePlateNum = "${v.vehiclePlateNum}";
				
				// 车主类型
				var isPersonal = "${v.isPersonal}";
				
				// 办理类型
				var isProxy = "${v.isProxy}";
				
				// 修正类型
				var modifyTypes = "${modifyTypes}";
				
				// 释放高拍仪资源
				Destroy();
				
				// alert(JSON.stringify(proof_files));
				
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog('eliminatedModify/captureNew.jsp?vehiclePlateNum='+vehiclePlateNum + '&isPersonal=' + isPersonal + '&isProxy=' + isProxy + '&modifyTypes=' + modifyTypes, proof_files, 
						"dialogWidth=800px,dialogHeight=600px,resizable=yes,status=no,scrollbars=yes,menubar=no");
				
				// 抓拍返回，设置图片路径到页面字段, 且设置后台隐藏字段
	        	if (typeof(parentValue) != "undefined" && parentValue != null) {
	        		// 分别获取对应的证明材料
	        		// 报废回收证明
	        		if (parentValue.JDCHSZM != null && parentValue.JDCHSZM != "") {
	        			setFilePreview("callbackProof", parentValue.JDCHSZM, "报废回收证明");
	        		}
	        		
	        		// 机动车注销证明
	        		if (parentValue.JDCZXZM != null && parentValue.JDCZXZM != "") {
	        			setFilePreview("vehicleCancelProof", parentValue.JDCZXZM, "机动车注销证明");
	        		}
	        		
	        		// 银行卡
	        		if (parentValue.YHK != null && parentValue.YHK != "") {
	        			setFilePreview("bankCard", parentValue.YHK, "银行卡");
	        		}
	        		
	        		// 车主身份证明
	        		if (parentValue.CZSFZM != null && parentValue.CZSFZM != "") {
	        			setFilePreview("vehicleOwnerProof", parentValue.CZSFZM, "车主身份证明");
	        		}
	        		
	        		// 代理人身份证明
	        		if (parentValue.DLRSFZ != null && parentValue.DLRSFZ != "") {
	        			setFilePreview("agentProof", parentValue.DLRSFZ, "代理人身份证明");
	        		}
	        		
	        		// 代理委托书
	        		if (parentValue.DLWTS != null && parentValue.DLWTS != "") {
	        			setFilePreview("agentProxy", parentValue.DLWTS, "代理委托书");
	        		}
	        		
	        		// 开户许可证
	        		if (parentValue.KHXKZ != null && parentValue.KHXKZ != "") {
	        			setFilePreview("openAccPromit", parentValue.KHXKZ, "开户许可证");
	        		}
	        		
	        		// 非财政供养单位证明
	        		if (parentValue.FCZGYZM != null && parentValue.FCZGYZM != "") {
	        			setFilePreview("noFinanceProvide", parentValue.FCZGYZM, "非财政供养单位证明");
	        		}
	        	}
			}
		</script>
		
</body>
</html>