<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div id = "allBatchMain-list" class="easyui-panel  easyui-panel-style" data-options="title: '申报批次总查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="allBatchMain-grid-toolbar">
				<table id="allBatchMain-recycle-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="allBatchMain-grid" style = "height:97.5%"></table>
	</div>


	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#allBatchMain-list #allBatchMain-grid").datagrid({
			toolbar : "#allBatchMain-grid-toolbar",
			url : basePath+"/payApply/batchAllList.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "toFinanceNo",
				title : "报送序号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "batchNo",
				title : "内部批次号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "batchType",
				title : "批次类型",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "1") {
						return "正常批次";
					} else if (value == "2") {
						return "重报批次";
					}
				},
				styler : function(value, row, index) {
					if (value == "1") {
						return "color:gray";
					} else if (value == "2") {
						return "color:red";
					}
				}
			},{
				field : "toFinanceStatus",
				title : "报财务状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未报";
					} else if (value == "1") {
						return "已报";
					}
				},
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:gray";
					} else if (value == "1") {
						return "color:green";
					} 
				}
			},{
				field : "payResStatus",
				title : "拨付结果状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未标记";
					} else if (value == "1") {
						return "拨付已完成";
					}else if (value == "2") {
						return "拨付部分完成";
					}else if (value == "3") {
						return "财务全部退回";
					}
				},
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:gray";
					} else if (value == "1") {
						return "color:green";
					} else if (value == "2") {
						return "color:red";
					} else if (value == "3") {
						return "color:blue";
					}
				}
			},{
				field : "batchStatus",
				title : "批次状态",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "1") {
						return "有效";
					} else if (value == "2") {
						return "废弃";
					}
				},
				styler : function(value, row, index) {
					if (value == "1") {
						return "color:green";
					} else if (value == "2") {
						return "color:red";
					}
				}
			},{
				field : "payBussCount",
				title : "业务单数",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payBatchTotalAmount",
				title : "拨付批次总金额(元)",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "createUser",
				title : "批次生成人",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "createDate",
				title : "批次生成时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.createDate) {
						return getNowFormatDate(new Date(row.createDate.time))
					} else {
						return "";
					}
				}
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&type=view",
							content:"批次受理单明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"toFinanceNo",title:"报送序号：",type:"text"},
			         {field:"batchNo",title:"内部批次号：",type:"text"},
			         {field:"toFinanceStatus",title:"批次报财委状态:",type:"combobox", url:basePath+"/data/toFinanceStatus.json", text:"name", value:"value"},
					 {field:"batchType",title:"批次类型：",type:"combobox", url:basePath+"/data/batchType.json", text:"name", value:"value"},
					 {startField:"createStartDate",endField:"createEndDate",title:"批次生成时间:",type:"date",section:true},
					 {field:"batchStatus",title:"批次状态：",type:"combobox", url:basePath+"/data/batchStatus.json", text:"name", value:"value"},
					 {startField:"toFinanceStartTime",endField:"toFinanceEndTime",title:"批次报财务时间:",type:"date",section:true}
			        ],
			tools:[			       
					   {type:"QUERY"}
				  ],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true   
		})

		
	})
	</script>
</body>
</html>