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
			<td colspan="6">批次信息</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">内部批次号：</td>
			<td class="view_table_right">${v.batchNo }</td>
			<td class="view_table_left">业务单数：</td>
			<td class="view_table_right">${v.payBussCount }</td>
			<td class="view_table_left">拨付总金额（元）：</td>
			<td class="view_table_right">${v.payBatchTotalAmount }</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">批次报财委状态：</td>
			<td class="view_table_right">
				<c:if test="${v.toFinanceStatus == '0'}">
					未报
				</c:if>
				<c:if test="${v.toFinanceStatus == '1'}">
					已报
				</c:if>
			</td>
			<td class="view_table_left">批次生成时间：</td>
			<td class="view_table_right">${v.createDate }</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">拨付结果详情：</td>
			<td class="view_table_right">${v.payResRemark }</td>
		</tr>
	</table>
	</br>
	<div id = "batchManage-list" class="easyui-panel  easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="batchManage-grid-toolbar">
				<table id="batchManage-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="batchManage-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#batchManage-list #batchManage-grid").datagrid({
			//toolbar : "#batchCreate-grid-toolbar",
			url : basePath+"/payApply/batchApplyList.do?batchNo="+'${v.batchNo }',
			method : "post",
			rownumbers : true,
			sortName : "lastUpdateTimeDate",
			sortOrder : "desc",
			columns : [ [{
				field : "batchNo",
				title : "内部批次号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyNo",
				title : "受理单号",
				width : "18%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "subsidiesMoney",
				title : "补贴金额（元）",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true 
			},{
				field : "vehiclePlateNum",
				title : "号牌号码",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehiclePlateTypeName",
				title : "号牌种类",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "toFinanceStatus",
				title : "批次报财委状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var num = parseInt (value);
					if (num < 0) {
						return "待报财务";
					} else if (num > "0") {
						return "已报财务";
					}
				},
				styler : function(value, row, index) {
					var num = parseInt (value);
					if (num < 0) {
						return "color:gray";
					} else if (num > 0) {
						return "color:green";
					} 
				}
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},
			{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}/*,{
				field : "applyTime",
				title : "受理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.expRecentDate) {
						return getNowFormatDate(new Date(row.expRecentDate.time))
					} else {
						return "";
					}
				}
			} ,{
				field : "applyPerson",
				title : "受理人",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyPersonDept",
				title : "部门",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			} */
			] ],
		});
	});

	</script>
</body>
</html>