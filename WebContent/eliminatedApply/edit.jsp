<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test-add</title>
</head>
<body>
	<form id="common-apply-edit" action="eliminatedApply/edit.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${v.id}"/>
		<div id="eliminated-apply-edit" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<tr class="datagrid-header-row">
					<td class="view_table_left">车主类型：</td>
					<td class="view_table_right" colspan="2">
						<c:if test="${v.isPersonal eq 'Y'}">
							自然人
						</c:if>
						<c:if test="${v.isPersonal eq 'N'}">
							企业
						</c:if>
						<%-- <input id="isPersonal" class="easyui-combobox" value="${v.isPersonal}" name="isPersonal" 
						data-options="editable:false,required:true,valueField:'id',textField:'text',url:'data/isPersonal.json',panelHeight:'auto'"/> --%>
						<!-- <select id="isPersonal" name="isPersonal" class="easyui-combobox" data-options="editable:false,required:true,width:160,panelHeight:'auto'">
							<option>请选择是自然人或企业</option> 
							<option value="Y">自然人</option>
							<option value="N">企业</option>
						</select> -->
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">办理类型：</td>
					<td class="view_table_right" colspan="2" >
						<c:if test="${v.isProxy == 'Y'}">
							自办
						</c:if>
						<c:if test="${v.isProxy == 'N'}">
							代办
						</c:if>
						<%-- <input id="isProxy" class="easyui-combobox" value="${v.isProxy}" name="isProxy" 
						data-options="editable:false,required:true,valueField:'id',textField:'text',url:'data/isProxy.json',panelHeight:'auto'"/> --%>			
						<!-- <select id="isProxy" name="isProxy" class="easyui-combobox" data-options="editable:false,required:true,width:75,panelHeight:'auto'">
							<option>请选择</option>
							<option value="Y">自办</option>
							<option value="N">代办</option>
						</select> -->
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">号牌号码：</td>
					<td class="view_table_right">
						<input type="text" name="vehiclePlateNum" value="${v.vehiclePlateNum}" class="easyui-validatebox" readonly="readonly"/>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">号牌种类：</td>
					<td class="view_table_right">
						<input type="text" name="vehiclePlateTypeName" value="${v.vehiclePlateTypeName}" class="easyui-validatebox" readonly="readonly"/>
						<%-- <input id="vehiclePlateType" class="easyui-combobox" value="${v.vehiclePlateType}" name="vehiclePlateType" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListByType.do?dictType=VEHICLE_PLATE_TYPE',panelHeight:300"/> 
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
						<a id="btnApplyVerify" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-baofei-message'">取报废数据</a> --%>
					</td>
					<td class="view_table_left">厂牌型号：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleModelNo" value="${v.vehicleModelNo}" readonly="readonly"/>
					</td>

				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车辆类型：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleTypeName" value="${v.vehicleTypeName}" readonly="readonly" /></td>
					<td class="view_table_left">车架号：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleIdentifyNo" value="${v.vehicleIdentifyNo}" readonly="readonly" /></td>
					<td class="view_table_left">发动机号：</td>
					<td class="view_table_right">
						<input type="text" name="engineNo" value="${v.engineNo}" readonly="readonly" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">使用性质：</td>

					<td class="view_table_right">
						<input type="text" name="useOfPropertyName" value="${v.useOfPropertyName}" readonly="readonly" /></td>

					<td class="view_table_left">燃油类型：</td>
					<td class="view_table_right">
						<input type="text" name="iolTypeName" value="${v.iolTypeName}" readonly="readonly" />
					</td>
					<td class="view_table_left">总质量（千克）</td>

					<td class="view_table_right">
						<input type="text" name="totalWeight" value="${v.totalWeight}" readonly="readonly" />
					</td>

				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">核定载客数（人）：</td>

					<td class="view_table_right">
						<input type="text" name="vehicleNumPeople" value="${v.vehicleNumPeople}" readonly="readonly" /></td>
					<td class="view_table_left">初次登记日期：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.recycleDate }" type="date" pattern="yyyy-MM-dd"/>
						<%-- <input  type="text" name="registerDate" value="${v.registerDate}" readonly="readonly" /> --%>
					</td>
					<td class="view_table_left">强制报废期：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>
						<%-- <input type="text" name="deadline" value="${v.deadline}" readonly="readonly" /> --%>
					</td>
				</tr>
				<!-- <tr class="datagrid-row applyType">
					<td class="view_table_left">机构类型：</td>
					<td class="view_table_right">
						<input type="text" name="orgType"/>
					</td>
					<td class="view_table_left">组织机构代码：</td>
					<td class="view_table_right applyType">
						<input type="text" name="orgCode" />
					</td>
				</tr> -->
				<tr class="datagrid-row">
					<td class="view_table_left">注销日期：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.destroyDate}" type="date" pattern="yyyy-MM-dd"/>
						<%-- <input type="text" name="destroyDate" value="${v.destroyDate}" readonly="readonly"/> --%>
					</td>
					<td class="view_table_left">车辆状态：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleStatusName" value="${v.vehicleStatusName}" readonly="readonly"/>
					</td>
						<td class="view_table_left">提前报废时长(天)：</td>
					<td class="view_table_right">
						<input type="text" name="advancedScrapDays" value="${v.advancedScrapDays}" readonly="readonly"/>
					</td>	
				</tr>
				<tr class="datagrid-header-row">
					<td class="view_table_left">排放标准：</td>
					<td class="view_table_right">
						<input type="text" name="emissionStandard" value="${v.emissionStandard}" readonly="readonly"/>
					</td>
					<td class="view_table_left">注销类别：</td>
					<td class="view_table_right">
						<input type="text" name="cancelReason" value="${v.cancelReason}" readonly="readonly"/>
					</td>	
					<td class="view_table_left">是否财政供养：</td>
					<td class="view_table_right">
						<c:if test="${v.isFinancialSupport eq '1'}">
							个人
						</c:if>
						<c:if test="${v.isFinancialSupport eq '2'}">
							车主自证非财政供养
						</c:if>
						<%-- <input id="isFinancialSupport" class="easyui-combobox" value="${v.isFinancialSupport}" name="isFinancialSupport" 
						data-options="editable:false,required:true,valueField:'id',textField:'text',url:'data/isFinancialSupport.json',panelHeight:'auto'"/> --%>
						<%-- <select id="isFinancialSupport" name="isFinancialSupport" value="${v.isFinancialSupport}" class="easyui-combobox" 
						data-options="editable:false,required:true,width:118,panelHeight:'auto'">
							<option selected>请选择</option>
							<option value="1">个人</option>
							<option value="2">非财政供养单位</option>
						</select> --%>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>		
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">报废信息</td>
				<tr class="datagrid-row">
					<td class="view_table_left" style="width:120px">报废回收证明编号：</td>
					<td class="view_table_right">
						<input type="text" name="callbackProofNo" value="${v.callbackProofNo}" readonly="readonly"  />
					</td>
					<td class="view_table_left">交售日期：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.recycleDate}" type="date" pattern="yyyy-MM-dd"/>
						<%-- <input type="text" class="easyui-datebox" data-options="required:true" value="${v.recycleDate}"/> --%>
						<%-- <input type="text" name="recycleDate" value="${v.recycleDate}" readonly="readonly" /> --%>
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴对象信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">车主：</td>
					<td class="view_table_right">
						<textarea  name="vehicleOwner" class="easyui-validatebox" readonly="readonly">${v.vehicleOwner}</textarea>
					</td>
					<td class="view_table_left">车主身份证号码或其它证明：</td>
					<td class="view_table_right">
						<input type="text" name="vehicleOwnerIdentity" value="${v.vehicleOwnerIdentity}" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">车主联系号码：</td>
					<td class="view_table_right">
						<input type="text" name="mobile" value="${v.mobile}" class="easyui-numberbox" />
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">经办人：</td>
					<td class="view_table_right">
						<input type="text" name="agent" value="${v.agent}" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人手机号：</td>
					<td class="view_table_right">
						<input type="text" name="agentMobileNo" value="${v.agentMobileNo}" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">经办人身份证号：</td>
					<td class="view_table_right">
						<input type="text" name="agentIdentity" value="${v.agentIdentity}" class="easyui-validatebox" data-options="required:true"/>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">开户户名：</td>
					<td class="view_table_right">
						<textarea rows="3" name="bankAccountName" class="easyui-validatebox" readonly="readonly">${v.bankAccountName}</textarea>
						<%-- <input type="text" name="bankAccountName" value="${v.bankAccountName}" class="easyui-validatebox" readonly="readonly"/> --%>
						<!-- <span style="color:red;text-align:center">&nbsp;*&nbsp;</span> -->
					</td>
					<td class="view_table_left">开户银行：</td>
					<td class="view_table_right">
						<input id="bankCodeUpdate" name="bankCode" />
						<input type="hidden" name="bankName" value="${v.bankName}" />
						<!-- <input id="bankCodeUpdate" class="easyui-combobox" name="bankCode" 
						data-options="editable:false,required:true,valueField:'code',textField:'value',url:'sysDict/getDictListFromMap.do?dictType=BANK_CODE',panelHeight:150"/> -->
						<%-- <input type="text" name="bankName" value="${v.bankName}" class="easyui-validatebox" data-options="required:true" /> --%>
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
					<td class="view_table_left">开户银行账号：</td>
					<td class="view_table_right">
						<input type="text" name="bankAccountNo" value="${v.bankAccountNo}" class="easyui-validatebox" data-options="required:true" />
						<span style="color:red;text-align:center">&nbsp;*&nbsp;</span>
					</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">补贴信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">补贴金额：</td>
					<td class="view_table_right">
						<input type="text" name="subsidiesMoney" value="${v.subsidiesMoney}" readonly="readonly"/>
					</td>
					<td class="view_table_left">补贴标准说明：</td>
					<td class="view_table_right">
						<input type="text" name="subsidiesStandard" value="${v.subsidiesStandard}" readonly="readonly"/>
					</td>
				</tr>
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
			</table>
		</div>
	</form>
	<form id="form-apply-upload" action="eliminatedApply/fileUpload.do" method="post" enctype="multipart/form-data">
	<div id="div-apply-upload" class="datagrid-header">
		<table class="datagrid-table-s datagrid-htable">
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">证明材料</td>
			</tr>
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoCallbackProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="callbackProofFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoVehicleCancelProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="vehicleCancelProofFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoBankCardUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="bankCardFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
			<c:if test="${!empty vehicleOwnerProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">原车主身份证明：</td>
				<td class="view_table_right">
					<c:forEach items="${vehicleOwnerProofFiles}" var="vehicleOwnerProofFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="${vehicleOwnerProofFile.filePath}" target="_blank">${vehicleOwnerProofFile.name}</a></br>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="${vehicleOwnerProofFile.filePath}" target="_blank">${vehicleOwnerProofFile.name}</a>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<tr class="datagrid-row">
				<td class="view_table_left" style="width:110px">车主身份证明：</td>
				<td class="view_table_right" colspan="2">
					<input type="file" name="vehicleOwnerProof" multiple="multiple" />
					<!-- <input id="vehicleOwnerProof" class="easyui-filebox" name="vehicleOwnerProof" data-options="editable:false,required:true,buttonText:'请选择'"/> -->
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoVehicleOwnerProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="vehicleOwnerProofFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
			<c:if test="${v.isProxy eq 'N'}">
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoAgentProxyProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="agentProxyFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoAgentProofUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="agentProofFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
			</c:if>
			<c:if test="${v.isPersonal eq 'N'}">
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
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoNoFinanceProvideUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="noFinanceProvideFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
			<c:if test="${!empty openAccPromitFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">原开户许可证：</td>
				<td class="view_table_right">
					<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}</a></br>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}</a>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<tr class="datagrid-row">
				<td class="view_table_left" style="width:110px">开户许可证：</td>
				<td class="view_table_right" colspan="2">
					<input type="file" name="openAccPromit" multiple="multiple" />
					<!-- <input id="openAccPromit" class="easyui-filebox" name="openAccPromit" data-options="editable:false,required:true,width:141,buttonText:'请选择'"/> -->
					<!-- <font color="red">&nbsp;*&nbsp;</font> -->
					<a id="btnTakePhotoOpenAccPromitUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-photo'">拍照</a>
				</td>
				<td class="view_table_right" colspan="3">
					<a id="openAccPromitFileImg" href="#" target="_blank"></a>
				</td>
			</tr>
			</c:if>
			<tr class="datagrid-row">
				<td align="center" colspan="6">
					<a id="btnUploadUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-shangchuan'">上传</a>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<br>
	<br>
	<div id="applyEdit" align="center">
		<a id="btnEditNextStepUpdate" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-nextstep'">下一步</a>
	</div>
	
	<script type="text/javascript">
		$(function() {
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
			$("#bankCodeUpdate").combobox({
					editable : false,
					reqiured : true,
					valueField : 'code',
					textField : 'value',
					panelHeight : 150
				});
			
			$("#bankCodeUpdate").combobox("reload", basePath + '/sysDict/getDictListFromMap.do?dictType=BANK_CODE');
			
			$("#bankCodeUpdate").combobox({
				onSelect : function() {
					var bankName = $(this).combobox("getText");
					$("input[name='bankName']").val(bankName);
				}
			});
			
			var bankCode = '${v.bankCode}';
			$("#bankCodeUpdate").combobox("setValue", bankCode);
			
			// 选中车主类型和办理类型
			/*
			$("#isPersonal").combobox({
				onChange : function() {
					var isPersonal = $(this).combobox("getValue");
					$("tr[class*='applyType']").each(function() {
						if (isPersonal == "N") {
							$("#isProxy").combobox("setValue", "N");
							$(this).show();
							$("#noFinanceProvide").attr("required","true");
							$("#openAccPromit").attr("required","true");
						} else if (isPersonal == "Y") {
							$(this).hide();
							// 车主类型是个人,隐藏开户许可证和非财政供养单位证明
							$("#noFinanceProvide").attr("required","false");
							$("#openAccPromit").attr("required","false");
						}
					});
				}
			});
			
			$("#isProxy").combobox({
				onChange : function() {
					var isProxy = $(this).combobox("getValue");
					$("tr[class*='proxyType']").each(function() {
						if (isProxy == "N") {
							$(this).show();
							$("#agentProxy").attr("required","true");
							$("#agentProof").attr("required","true");
						} else if (isProxy == "Y") {
							$("#isPersonal").combobox("setValue", "Y");
							$(this).hide();
							// 自办隐藏代理委托书和代理人身份证明
							$("#agentProxy").attr("required","false");
							$("#agentProof").attr("required","false");
						}
					});
				}
			});
			*/
			
		   //报废回收证明、机动车注销证明、银行卡、车主身份证明、代理委托书、代理人身份证、开户许可证、非财政供养单位证明
		   $("#btnUploadUpdate").click(function() {
			   
			   var isPersonal = '${v.isPersonal}';
			   var isProxy = '${v.isProxy}';
			   
			   if (isPersonal != "Y" && isPersonal != "N") {
				   alert("请选择车主类型！");
				   return false;
			   }
			   
			   if (isProxy != "Y" && isProxy != "N") {
				   alert("请选择办理类型！");
				   return false;
			   }
			   
			   //文件框页面校验，必填
			   var ifValid = $("#form-apply-upload").form("enableValidation").form("validate");
			   
			   if (ifValid) {
				
				   var url =  $("#form-apply-upload").attr("action")+"?isPersonal="+isPersonal+"&isProxy="+isProxy;
				   $("#form-apply-upload").form({
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
				 
				   $("#form-apply-upload").submit();
			   } else {
				   alert("还有证明材料未选择，请选择后上传！");
				   return false;
			   }
        	 	
	        });
			
			// 报废回收证明抓拍上传
			$("#btnTakePhotoCallbackProofUpdate").click(function() {
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == null || vehiclePlateNum == "" || typeof(vehiclePlateNum) == "undefined") {
					Messager.alert({
						type : "error",
						title : "&nbsp;",
						content : "请先输入号牌号码！"
					});
					return false;
				}
				// 弹出高拍仪抓拍图片界面
				var parentValue = window.showModalDialog("eliminatedApply/capture.jsp?vehiclePlateNum="+vehiclePlateNum, "图片抓拍上传", "toolbar=yes,width=1300,height=600,status=no,scrollbars=yes,resize=yes,menubar=no");
        	
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
			
			
			/* // 取报废录入数据和交警接口数据
			$("#btnApplyVerify").bind("click", function() {
				//校验输入的号牌号码和号牌种类，判断是否在系统中录入的车辆，过滤不符合资格或者补贴金额为0的车辆。
				var vehiclePlateNum = $("input[name='vehiclePlateNum']").val();
				if (vehiclePlateNum == "") {
					alert("号牌号码为空！");
					return false;
				}
				var vehiclePlateType = $("#vehiclePlateType").combobox("getValue");
				if (vehiclePlateType == "") {
					alert("号牌种类为空！");
					return false;
				}
				
		        $.ajax({
		        	url : basePath + "/eliminatedApply/getVehicleInfo.do?vehiclePlateNum=" + vehiclePlateNum + "&vehiclePlateType=" + vehiclePlateType,
		        	async : true,
		        	type : "POST",
		        	dataType : "json",
		        	beforeSend : function() {
		        		if (null == vehiclePlateNum || null == vehiclePlateType) {
		        			return false;
		        		}
		        	},
		        	success : function(data) {
		        		if (data.success) {
			        		//将获取到的数据填充到页面字段，并显示。
			        		// 厂牌型号
			        		$("input[name='vehicleModelNo']").val(data.message.vehicleModelNo);
			        		// 车辆类型
			        		$("input[name='vehicleType']").val(data.message.vehicleType);
			        		$("input[name='vehicleTypeName']").val(data.message.vehicleTypeName);
			        		// 发动机型号
			        		$("input[name='engineNo']").val(data.message.engineNo);
			        		// 车辆状态
			        		$("input[name='vehicleStatus']").val(data.message.vehicleStatus);
			        		$("input[name='vehicleStatusName']").val(data.message.vehicleStatusName);
			        		// 车架号
			        		$("input[name='vehicleIdentifyNo']").val(data.message.vehicleIdentifyNo);
			        		// 使用性质
			        		$("input[name='useOfProperty']").val(data.message.useOfProperty);
			        		$("input[name='useOfPropertyName']").val(data.message.useOfPropertyName);
			        		// 燃油种类
			        		$("input[name='iolType']").val(data.message.iolType);
			        		$("input[name='iolTypeName']").val(data.message.iolTypeName);
			        		// 总质量
			        		$("input[name='totalWeight']").val(data.message.totalWeight);
			        		// 核定载客数
			        		$("input[name='vehicleNumPeople']").val(data.message.vehicleNumPeople);
			        		// 初次登记日期
			        		$("input[name='registerDate']").val(getNowFormatDate(new Date(data.message.registerDate.time)));
			        		// 强制报废期止
			        		$("input[name='deadline']").val(getNowFormatDate(new Date(data.message.deadline.time)));
			        		// 车主
			        		$("textarea[name='vehicleOwner']").val(data.message.vehicleOwner);
			        		// 交售日期
			        		$("input[name='recycleDate']").val(getNowFormatDate(new Date(data.message.recycleDate.time)));
			        		// 排放标准
			        		$("input[name='emissionStandard']").val(data.message.emissionStandard);
			        		// 提前报废时长（天）
			        		$("input[name='advancedScrapDays']").val(data.message.advancedScrapDays);
			        		// 注销类别
			        		$("input[name='cancelReason']").val(data.message.cancelReason);
			        		// 车主身份号或者其它证明号码
			        		$("input[name='vehicleOwnerIdentity']").val(data.message.vehicleOwnerIdentity);
			        		// 车主手机号码
			        		$("input[name='mobile']").val(data.message.mobile);
			        		// 补贴账户名称
			        		$("input[name='bankAccountName']").val(data.message.bankAccountName);
			        		// 补贴金额
			        		$("input[name='subsidiesMoney']").val(data.message.subsidiesMoney);
			        		// 补贴标准
			        		$("input[name='subsidiesStandard']").val(data.message.subsidiesStandard);
			        		// 注销日期
			        		$("input[name='destroyDate']").val(getNowFormatDate(new Date(data.message.destroyDate.time)));
			        		// 报废回收证明编号
			        		$("input[name='callbackProofNo']").val(data.message.callbackProofNo);
			        		
		        		} else {
		        			alert("报废数据获取失败!");
		        		}
		        	}
		        });
				//alert("机动车未注销，不得受理！");
				//$("#common-dialog").dialog("close");
			}); */
			
			Callback.onsubmit = function() {
				// 点击下一步，页面提交前的特殊处理
				return true;
				
			}
			
			// 下一步按钮点击事件处理函数
			$("#btnEditNextStepUpdate").click(function() {
				var isValid = $("#common-apply-edit").form("enableValidation").form("validate");
				if (isValid) {
					$.ajax({
		                //cache: true,
		                type: "POST",
		                url:$("#common-apply-edit").attr("action"),
		                data:$("#common-apply-edit").serialize(),
		                async: true,
		                dataType : 'json',
		                success: function(data) {
		                	
		                	/* if(Object.prototype.toString.call(data) === "[object String]") {
								data = eval("(" + data + ")");
							} */
							
							if(data.success) {
								
								Messager.alert({
									type:"info",
									title:"&nbsp;",
									content:data.message.msg
								});
								// 设置受理单号、受理表Id、档案盒编号
								$("input[name='id']").val(data.message.id);
								
								// 资格校验成功，受理表信息保存，页面跳转到受理表打印预览页面
								var url = basePath+"/eliminatedApply/applyPreview.do?id="+data.message.id;
								$("#common-dialog").dialog("close");
								
								openDialog({
								   	type : "PRINT_APPLY_TABLE",
									title : "补贴受理表打印预览",
									width : 1040,
									height : 400,
									param: {reset:false,save:false},	
									maximizable : true,
									href : url
							   });
								
		                	} else {
		                		Messager.alert({
									type:"error",
									title:"&nbsp;",
									content:data.message.msg
								});
		                	}
		            	}
					});
				} else {
					alert("请先填写受理表信息");
					return false;
				}
			
			});
			
		});	
	
	
	
	</script>
	
</body>
</html>