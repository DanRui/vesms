<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div id = "repFinance-list" class="easyui-panel  easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repFinance-grid-toolbar">
				<table id="repFinance-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="repFinance-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repFinance-list #repFinance-grid").datagrid({
			toolbar : "#repFinance-grid-toolbar",
			url : basePath+"/payApply/batchList.do",
			method : "post",
			rownumbers : true,
			sortName : "createDate",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "batchNo",
				title : "批次号",
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
			},{
				field : "createUser",
				title : "批次生成人",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "isExported",
				title : "导出状态",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "待导出") {
						return "color:red";
					} else if (value == "已导出") {
						return "color:green";
					} else if (value == "待重新导出") {
						return "color:blue";
					}
				}
			},{
				field : "expRecentDate",
				title : "最近导出时间",
				width : "8%",
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
			},{
				field : "exportTimes",
				title : "批次导出次数",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "toFinanceStatus",
				title : "批次报财务状态",
				width : "10%",
				align : "center",
				halign :"center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "待报财务") {
						return "color:red";
					} else if (value == "已报财务") {
						return "color:green";
					} else if (value == "再次未报") {
						return "color:orange";
					} else if (value == "再次已报") {
						return "color:green";
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
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:800,height:450,
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&type=view",
							content:"申报受理单明细"});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"批次号：",type:"text"},
					{field:"batchExpStatus",title:"批次导出状态:",type:"combobox", url:basePath+"/data/batchExpStatus.json", text:"name", value:"value"},
					{field:"batchToFinanceStatus",title:"批次报财务状态:",type:"combobox", url:basePath+"/data/toFinanceStatus.json", text:"name", value:"value"},
					{field:"payBatchTotalAmount",title:"拨付总金额（元）:",type:"text"},
					{startField:"batchCreateStartDate",endField:"batchCreateEndDate",title:"批次生成时间:",type:"date",section:true},
					{startField:"batchExpStartDate",endField:"batchExpEndDate",title:"拨付导出时间:",type:"date",section:true}
			        ],
			tools:[			       
				    {type:"CONFIRM_APPLY",icon:"icon-ok",title:"报财务确认",text_width:100,
						   fn:function() {
								var selectedRows = this.datagrid("getSelections");
								var infoMsg = null;
								infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
								if (null != infoMsg) {
									Messager.alert({
										type : "info",
										title : "&nbsp;",
										content : infoMsg
									});
								}else {
								    var i = 0;
									for (; i < selectedRows.length ; i ++) {
										if (selectedRows[i].batchExpStatus == "待导出" || selectedRows[i].batchExpStatus == "待重新导出" || selectedRows[i].batchToFinanceStatus == "已报财务" || selectedRows[i].batchToFinanceStatus == "再次已报") {
											break;
										}
									}
									
									if (i != selectedRows.length) {
										Messager.alert({
											type : "info",
											title : "警告",
											content : "选中的批次中含有"+"<font color=red> 待导出  </font>"+"或"+"<font color=red> 已报财务  </font>"+"的数据,无法再次报财务!"
										});
									} else {
										Messager.confirm({
											   title : "提示",
											   content :"你选择了  "+"1"+"  号批次号"+"<br>"+"单数:"+100+"单"+"<br>"+"该批次总金额:"+800000+"元"+
											   			"<br><br>"+"<center>是否确认报财务？</center>",
											   handler :function(r){
												   if(r){
													   Messager.alert({
															type : "info",
															title : "提示 ",
															content : "提交成功"
														});
												   }
											   }
										   });
									}
								}
						   }
					   },
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