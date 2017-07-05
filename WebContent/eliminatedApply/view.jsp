<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
	String basePath = request.getContextPath();
	//String isPersonal = request.getParamter("isPersonal");
	//String isProxy = request.getParamter("isProxy");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test-update</title>
</head>
<body>
	<div id="eliminated-apply-info" class="datagrid-header">
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
				</td>
				<td class="view_table_left">办理类型：</td>
				<td class="view_table_right" colspan="2">
					<c:if test="${v.isProxy == 'Y'}">
						自办
					</c:if>
					<c:if test="${v.isProxy == 'N'}">
						代理
					</c:if>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">车辆基本信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">号牌号码：</td>
				<td class="view_table_right">
					${v.vehiclePlateNum}
				</td>
				<td class="view_table_left">号牌种类：</td>
				<td class="view_table_right">
					${v.vehiclePlateTypeName}
				</td>
				<td class="view_table_left">厂牌型号：</td>
				<td class="view_table_right">
					${v.vehicleModelNo}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车辆类型：</td>
				<td class="view_table_right">
					${v.vehicleTypeName}
				</td>
				<td class="view_table_left">车架号：</td>
				<td class="view_table_right">
					${v.vehicleIdentifyNo}
				</td>
				<td class="view_table_left">发动机号：</td>
				<td class="view_table_right">
					${v.engineNo}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">使用性质：</td>
				<td class="view_table_right">
					${v.useOfPropertyName}
				</td>
				<td class="view_table_left">燃油类型：</td>
				<td class="view_table_right">
					${v.iolTypeName}
				</td>
				<td class="view_table_left">总质量（千克）</td>
				<td class="view_table_right">
					${v.totalWeight}
				</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">核定载客数（人）：</td>
				<td class="view_table_right">
					${v.vehicleNumPeople}
				</td>
				<td class="view_table_left">初次登记日期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.registerDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td class="view_table_left">强制报废期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.deadline}" type="date" pattern="yyyy-MM-dd"/>
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
				</td>
				<td class="view_table_left">车辆状态：</td>
				<td class="view_table_right">
					${v.vehicleStatusName}
				</td>
				<td class="view_table_left">提前报废时长：</td>
				<td class="view_table_right">
					${v.advancedScrapDays}
				</td>
			</tr>
			<tr class="datagrid-header-row">
				<td class="view_table_left">排放标准：</td>
				<td class="view_table_right">
					${v.emissionStandard}
				</td>
				<td class="view_table_left">注销类别：</td>
				<td class="view_table_right">
					${v.cancelReason}
				</td>
				<td class="view_table_left">是否财政供养：</td>
				<td class="view_table_right">
					<c:if test="${v.isFinancialSupport eq '1'}">
						个人
					</c:if>
					<c:if test="${v.isFinancialSupport eq '2'}">
						非财政供养单位
					</c:if>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">报废信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left" style="width: 110px">报废回收证明编号：</td>
				<td class="view_table_right">
					${v.callbackProofNo}
				</td>
				<td class="view_table_left">交售日期：</td>
				<td class="view_table_right">
					<fmt:formatDate value="${v.recycleDate}" type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">补贴对象信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">车主：</td>
				<td class="view_table_right"><textarea name="vehicleOwner"
						class="easyui-validatebox" readonly="readonly">${v.vehicleOwner}</textarea>
				</td>
				<td class="view_table_left">车主身份证明号码：</td>
				<td class="view_table_right">
					${v.vehicleOwnerIdentity}
				</td>
				<td class="view_table_left">车主联系号码：</td>
				<td class="view_table_right">
					${v.mobile}
				</td>
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
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">补贴信息</td>
			</tr>
			<tr class="datagrid-row">
				<td class="view_table_left">补贴金额：</td>
				<td class="view_table_right">
					${v.subsidiesMoney}
				</td>
				<td class="view_table_left">补贴标准说明：</td>
				<td class="view_table_right">
					${v.subsidiesStandard}
				</td>
			</tr>
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">证明材料</td>
			</tr>
			<c:if test="${!empty callbackFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">报废汽车回收证明：</td>
				<td class="view_table_right">
					<c:forEach items="${callbackFiles}" var="callbackFile">
						<a href="${callbackFile.filePath}" target="_blank">${callbackFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty vehicleCancelProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">机动车注销证明：</td>
				<td class="view_table_right">
					<c:forEach items="${vehicleCancelProofFiles}" var="vehicleCancelProofFile">
						<a href="${vehicleCancelProofFile.filePath}" target="_blank">${vehicleCancelProofFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty vehicleOwnerProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">车主身份证明：</td>
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
			<c:if test="${!empty bankCardFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">银行卡：</td>
				<td class="view_table_right">
					<c:forEach items="${bankCardFiles}" var="bankCardFile">
						<a href="${bankCardFile.filePath}" target="_blank">${bankCardFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${v.isPersonal == 'N'}">
			<c:if test="${!empty noFinanceProvideFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">非财政供养单位证明：</td>
				<td class="view_table_right">
					<c:forEach items="${noFinanceProvideFiles}" var="noFinanceProvideFile">
						<a href="${noFinanceProvideFile.filePath}" target="_blank">${noFinanceProvideFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty openAccPromitFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">开户许可证：</td>
				<td class="view_table_right">
					<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile">
						<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</c:if>
			<c:if test="${v.isProxy == 'N'}">
			<c:if test="${!empty agentProxyFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">代理委托书：</td>
				<td class="view_table_right">
					<c:forEach items="${agentProxyFiles}" var="agentProxyFile">
						<a href="${agentProxyFile.filePath}" target="_blank">${agentProxyFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty agentProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">代理人身份证：</td>
				<td class="view_table_right">
					<c:forEach items="${agentProofFiles}" var="agentProofFile">
						<a href="${agentProofFile.filePath}" target="_blank">${agentProofFile.name}</a>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			</c:if>
		</table>
	</div>
</body>
</html>