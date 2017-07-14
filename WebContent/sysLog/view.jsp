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
					<td colspan="6">基本信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">系统代码：</td>
					<td class="view_table_right">${v.appCode }</td>
					<td class="view_table_left">模块代码：</td>
					<td class="view_table_right">${v.objType }</td>
					<td class="view_table_left">操作代码：</td>
					<td class="view_table_right">${v.opeType }</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">操作人：</td>
					<td class="view_table_right">${v.opeUserName }</td>
					<td class="view_table_left">操作人代码：</td>
					<td class="view_table_right">${v.opeUserCode }</td>
					<td class="view_table_left">操作总用时：</td>
					<td class="view_table_right">${v.busUserTime } ms</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">操作IP：</td>
					<td class="view_table_right">${v.opeIp }</td>
					<td class="view_table_left">操作Mac地址：</td>
					<td class="view_table_right">${v.mac }</td>
					<td class="view_table_left">操作时间</td>
					<td class="view_table_right"><fmt:formatDate value="${v.opeTime}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">操作开始时间：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.startTime}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="view_table_left">操作结束时间：</td>
					<td class="view_table_right">
						<fmt:formatDate value="${v.endTime}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="view_table_left">业务操作用时：</td>
					<td class="view_table_right">${v.useTime} ms
					</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">调用服务名称：</td>
					<td class="view_table_right" style="word-wrap:break-word">${v.serviceName }</td>
					<td class="view_table_left">调用方法名称：</td>
					<td class="view_table_right">${v.methodName }</td>
					<td class="view_table_left">方法参数</td>
					<td class="view_table_right" style="word-wrap:break-word">${v.serviceArgs}</td>
				</tr>
				<tr class="datagrid-header-row classify-tr">
					<td colspan="6">异常信息</td>
				</tr>
				<tr class="datagrid-row">
					<td class="view_table_left">session信息：</td>
					<td class="view_table_right">${v.webSession }</td>
					<td class="view_table_left">是否异常：</td>
					<td class="view_table_right">
						<c:if test="${v.opearterType eq 0}">
							正常
						</c:if>
						<c:if test="${v.opearterType eq 1}">
							异常
						</c:if>
					</td>
					<td class="view_table_left">异常信息</td>
					<td class="view_table_right" style="word-wrap:break-word">
						${v.errorMsg}
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