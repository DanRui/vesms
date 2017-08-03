<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
String basePath = request.getContextPath();
/* String batchNo = request.getParameter("batchNo");
String payBussCount = request.getParameter("payBussCount");
String payBatchTotalAmount = request.getParameter("payBatchTotalAmount");
String batchExpStatus = new String(request.getParameter("batchExpStatus").getBytes("ISO-8859-1"), "UTF-8");
String batchToFinanceStatus = new String(request.getParameter("batchToFinanceStatus").getBytes("ISO-8859-1"), "UTF-8");
String createDate = request.getParameter("createDate"); */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table class="datagrid-table-s datagrid-htable">
		<tr class="datagrid-header-row classify-tr">
			<td colspan="6">国库信息</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">ID：</td>
			<td class="view_table_right">${v.id }</td>
			<td class="view_table_left">有效记录数：</td>
			<td class="view_table_right">${v.applyCountValid }</td>
			<td class="view_table_left">无效记录数：</td>
			<td class="view_table_right">${v.applyCountInvalid}</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">总记录数：</td>
			<td class="view_table_right">${v.recordCountTotal }</td>
			<td class="view_table_left">正常支付单数：</td>
			<td class="view_table_right">${v.applyCountPayOk }</td>
			<td class="view_table_left">退款单数：</td>
			<td class="view_table_right">${v.applyCountPayNotok}</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">制表时间：</td>
			<td class="view_table_right">
				<fmt:formatDate value="${v.makeTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="view_table_left">导入时间：</td>
			<td class="view_table_right">
				<fmt:formatDate value="${v.importTime}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
	</table>
	</br>
	<div id = "importDetail-list" class="easyui-panel  easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="batchManage-grid-toolbar">
				<table id="importDetail-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="importDetail-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#importDetail-list #importDetail-grid").datagrid({
			//toolbar : "#batchCreate-grid-toolbar",
			url : basePath+"/importDetail/list.do?payImportId="+'${v.id }',
			method : "post",
			rownumbers : true,
			sortName : "id",
			pageSize:200,
			columns : [ [{
				field : "applyNo",
				title : "业务单号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "accountName",
				title : "开户户名",
				width : "22%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "bankName",
				title : "开户银行",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payAmount",
				title : "支付金额",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payResult",
				title : "支付状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true 
			},{
				field : "payResStatus",
				title : "支付结果标记状态",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true ,
				formatter : function(value, row, index) {
					if (value =="-1") {
						return "无效";
					}else if (value =="0") {
						return "正常待手工标记";
					}else if (value =="1") {
						return "拨付成功";
					}else if (value =="2") {
						return "拨付不成功";
					}else if (value =="3") {
						return "退款待手工标记";
					}
				},
				styler : function(value, row, index) {
					if (value =="-1") {
						return "color:gray";
					}else if (value =="0") {
						return "color:red";
					}else if (value =="1") {
						return "color:green";
					}else if (value =="2") {
						return "color:red";
					}else if (value =="3") {
						return "color:red";
					}
				}
			},{
				field : "remark",
				title : "说明",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payTime",
				title : "付款时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.payTime) {
						return getNowFormatDate(new Date(row.payTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "confirmTime",
				title : "清算时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.confirmTime) {
						return getNowFormatDate(new Date(row.confirmTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "updateTime",
				title : "更新时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.updateTime) {
						return getNowFormatDate(new Date(row.updateTime.time))
					} else {
						return "";
					}
				}
			}
			] ],
		});
	});

	</script>
</body>
</html>