<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>System-Log-View</title>
</head>
<body>
		<div id="system-log-info" class="datagrid-header">
			<table class="datagrid-table-s datagrid-htable">
					<tr class="datagrid-header-row classify-tr">
						<td colspan="6">系统操作日志</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">号牌号码：</td>
						<td class="view_table_right">${v.vehiclePlateNum }</td>
						<td class="view_table_left">号牌种类：</td>
						<td class="view_table_right">${v.vehiclePlateType }-${v.vehiclePlateTypeName }</td>
						<td class="view_table_left">厂牌型号：</td>
						<td class="view_table_right">${v.vehicleModelNo }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">车辆类型：</td>
						<td class="view_table_right">${v.vehicleTypeName }</td>
						<td class="view_table_left">车架号：</td>
						<td class="view_table_right">${v.vehicleIdentifyNo }</td>
						<td class="view_table_left">发动机号：</td>
						<td class="view_table_right">${v.engineNo }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">使用性质：</td>
						<td class="view_table_right">${v.useOfPropertyName }</td>
						<td class="view_table_left">燃油类型：</td>
						<td class="view_table_right">${v.iolTypeName }</td>
						<td class="view_table_left">总质量（千克）</td>
						<td class="view_table_right">${v.totalWeight }</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">核定载客数（人）：</td>
						<td class="view_table_right">${v.vehicleNumPeople }</td>
						<td class="view_table_left">初次登记日期：</td>
						<td class="view_table_right">
							<fmt:formatDate value="${v.registerDate }" type="date" pattern="yyyy-MM-dd"/>
						</td>
						<td class="view_table_left">强制报废期：</td>
						<td class="view_table_right">
							<fmt:formatDate value="${v.deadline }" type="date" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">车辆状态：</td>
						<td class="view_table_right">${v.vehicleStatusName }</td>
					</tr>
					<tr class="datagrid-header-row classify-tr">
						<td colspan="6">车主信息</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">车主：</td>
						<td class="view_table_right">${v.vehicleOwner }</td>
					 	<%-- <td class="view_table_left">车主联系电话：</td>
						<td class="view_table_right">${v.mobile }</td>  --%>
					</tr>
					<tr class="datagrid-header-row classify-tr">
						<td colspan="6">报废信息</td>
					</tr>
					<tr class="datagrid-row">
						<td class="view_table_left">报废回收证明编号：</td>
						<td class="view_table_right">${v.callbackProofNo }</td>
						<td class="view_table_left">交售日期：</td>
						<td class="view_table_right">
							<fmt:formatDate value="${v.recycleDate }" type="date" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr class="datagri-row">
						<td class="view_table_left">录入人：</td>
						<td class="view_table_right">${v.inputUserName }</td>
						<td class="view_table_left">录入时间：</td>
						<td class="view_table_right">
							<fmt:formatDate value="${v.inputTime }" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" />
						</td>
					</tr>
				</table>
			</div>

	</div>
	<script type="text/javascript">
			$(function() {
			});
		</script>
</body>
</html>