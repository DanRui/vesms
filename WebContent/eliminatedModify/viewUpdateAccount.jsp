<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- <form action="" method="post" enctype="multipart/form-data"> -->
		<div id="eliminated-update-account" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
				<!-- <tr class="datagrid-header-row classify-tr">
					<td colspan="6">车辆基本信息</td>
				</tr> -->
				<tr class="datagrid-header-row">
				<td class="view_table_left">车主类型：</td>
				<td class="view_table_right">
					<c:if test="${v.isPersonal eq 'Y'}">
						自然人
					</c:if>
					<c:if test="${v.isPersonal eq 'N'}">
						企业
					</c:if>
				</td>
				<td class="view_table_left">办理类型：</td>
				<td class="view_table_right">
					<c:if test="${v.isProxy == 'Y'}">
						自办
					</c:if>
					<c:if test="${v.isProxy == 'N'}">
						代理
					</c:if>
				</td>
				<c:if test="${v.isFault eq '1'}">
					<td class="view_table_left">
						<font color="red">数据异常：</font>
					</td>
					<td class="view_table_right">
						<font color="red">${v.faultDesc}</font>
					</td>
				</c:if>
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
				<td class="view_table_left">提前报废时长（天）：</td>
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
				<td class="view_table_left" style="width: 120px">报废回收证明编号：</td>
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
				<td class="view_table_right">
					${v.vehicleOwner}
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
			<c:if test="${!empty callbackFiles && !empty vehicleCancelProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">报废汽车回收证明：</td>
				<c:if test="${!empty callbackFiles}">
				<td class="view_table_right">
					<c:forEach items="${callbackFiles}" var="callbackFile">
						<a href="${callbackFile.filePath}" target="_blank">${callbackFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">机动车注销证明：</td>
				<c:if test="${!empty vehicleCancelProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${vehicleCancelProofFiles}" var="vehicleCancelProofFile">
						<a href="${vehicleCancelProofFile.filePath}" target="_blank">${vehicleCancelProofFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${!empty vehicleOwnerProofFiles && !empty bankCardFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">车主身份证明：</td>
				<c:if test="${!empty vehicleOwnerProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${vehicleOwnerProofFiles}" var="vehicleOwnerProofFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="${vehicleOwnerProofFile.filePath}" target="_blank">${vehicleOwnerProofFile.name}(${status.count})</a></br>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="${vehicleOwnerProofFile.filePath}" target="_blank">${vehicleOwnerProofFile.name}(${status.count})</a>
						</c:if>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">银行卡：</td>
				<c:if test="${!empty bankCardFiles}">
				<td class="view_table_right">
					<c:forEach items="${bankCardFiles}" var="bankCardFile">
						<a href="${bankCardFile.filePath}" target="_blank">${bankCardFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${v.isPersonal == 'N'}">
			<tr class="datagrid-row">
				<td class="view_table_left">非财政供养单位证明：</td>
				<c:if test="${!empty noFinanceProvideFiles}">
				<td class="view_table_right">
					<c:forEach items="${noFinanceProvideFiles}" var="noFinanceProvideFile">
						<a href="${noFinanceProvideFile.filePath}" target="_blank">${noFinanceProvideFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">开户许可证：</td>
				<c:if test="${!empty callbackFiles}">
					<td class="view_table_right">
						<c:forEach items="${openAccPromitFiles}" var="openAccPromitFile" varStatus="status">
							<c:if test="${status.index % 2 eq 1}">
								<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}(${status.count})</a></br>
							</c:if>
							<c:if test="${status.index % 2 eq 0}">
								<a href="${openAccPromitFile.filePath}" target="_blank">${openAccPromitFile.name}(${status.count})</a>
							</c:if>
						</c:forEach>
					</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${v.isProxy == 'N'}">
			<tr class="datagrid-row">
				<td class="view_table_left">代理委托书：</td>
				<c:if test="${!empty agentProxyFiles}">
				<td class="view_table_right">
					<c:forEach items="${agentProxyFiles}" var="agentProxyFile">
						<a href="${agentProxyFile.filePath}" target="_blank">${agentProxyFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
				<td class="view_table_left">代理人身份证：</td>
				<c:if test="${!empty agentProofFiles}">
				<td class="view_table_right">
					<c:forEach items="${agentProofFiles}" var="agentProofFile">
						<a href="${agentProofFile.filePath}" target="_blank">${agentProofFile.name}</a>
					</c:forEach>
				</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${!empty signedApplyFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left">签字确认的受理表：</td>
				<td class="view_table_right">
					<c:forEach items="${signedApplyFiles}" var="signedApplyFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="${signedApplyFile.filePath}" target="_blank">${signedApplyFile.name}(${status.count})</a></br>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="${signedApplyFile.filePath}" target="_blank">${signedApplyFile.name}(${status.count})</a>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty accountChangeProofFiles}">
			<tr class="datagrid-row">
				<td class="view_table_left" style="width: 120px">补贴对象变更证明材料：</td>
				<td class="view_table_right">
					<c:forEach items="${accountChangeProofFiles}" var="accountChangeProofFile" varStatus="status">
						<c:if test="${status.index % 2 eq 1}">
							<a href="${accountChangeProofFile.filePath}" target="_blank">${accountChangeProofFile.name}(${status.count})</a></br>
						</c:if>
						<c:if test="${status.index % 2 eq 0}">
							<a href="${accountChangeProofFile.filePath}" target="_blank">${accountChangeProofFile.name}(${status.count})</a>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			</c:if>	
			<tr>
			<td colspan="6" style="vertical-align:text-top;">
			<table id="table-action-log" width="100%;">	
			<tr class="datagrid-header-row classify-tr">
				<td colspan="6">业务流水记录</td>
			</tr>	
			<tr class="datagrid-row" bgcolor="#d7ebf9" style="text-align:center;">
				<th>操作岗位</th>
				<th>操作动作</th>
				<th>发生时间</th>
				<th>经办人</th>
				<th>操作结果</th>
				<th width="25%">详情说明</th>
			</tr>
			<c:forEach items="${actionLogs}" var="log">
				<tr align="center">
					<td>
						<c:choose>
							<c:when test="${log.currentPost eq 'CKSLG'}">
								窗口受理岗
							</c:when>
							<c:when test="${log.currentPost eq 'YBSLXXXZG'}">
								一般信息修正岗
							</c:when>
							<c:when test="${log.currentPost eq 'BTDXXZG'}">
								补贴对象修正岗
							</c:when>
							<c:when test="${log.currentPost eq 'KJCSG'}">
								会计初审岗
							</c:when>
							<c:when test="${log.currentPost eq 'CKSHG'}">
								窗口审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'KZSHG'}">
								科长审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'CZSHG'}">
								处长审核岗
							</c:when>
							<c:when test="${log.currentPost eq 'KJFSG'}">
								会计复审岗
							</c:when>
							<c:when test="${log.currentPost eq 'BFSBG'}">
								拨付申报岗
							</c:when>
							<c:when test="${log.currentPost eq 'BFJGBJG'}">
								拨付结果标记岗
							</c:when>
							<c:when test="${log.currentPost eq 'YWBJG'}">
								业务办结岗
							</c:when>
							<c:otherwise>
								系统管理员
							</c:otherwise>
						</c:choose>
					</td>
					<td>${log.actionName}</td>
					<td><fmt:formatDate value="${log.actionTime}" type="time" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${log.actionUser}</td>
					<td>${log.actionResult}</td>
					<td>${log.actionDetail}</td>
				</tr>
			</c:forEach>
			</table>
			</td>
			</tr>
		</table>
	</div>
</body>
</html>