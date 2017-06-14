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
	
	<div id = "repBatchExport-list" class="easyui-panel  easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repBatchExport-grid-toolbar">
				<table id="repBatchExport-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="repBatchExport-grid" style = "height:97.5%"></table>
	</div>


	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repBatchExport-list #repBatchExport-grid").datagrid({
			toolbar : "#repBatchExport-grid-toolbar",
			url : basePath+"/payApply/repExpBatchList.do",
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
				title : "报财务序号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
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
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未导出";
					} else if (value == "1") {
						return "已导出";
					} else if (value == "2") {
						return "待重新导出";
					}
				},
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:red";
					} else if (value == "1") {
						return "color:green";
					} else if (value == "2") {
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
			} ,{
				field : "toFinanceStatus",
				title : "批次报财委状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
						if (value == "0") {
							return "待报财务";
						} else if (value == "1") {
							return "已报财务";
						}
					},
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:gray";
					} else if (value == "1") {
						return "color:red";
					} 
				}
			} ,{
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
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&type=view",content:"重报批次受理单明细"});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"批次号：",type:"text"},
					{field:"isExport",title:"批次导出状态:",type:"combobox", url:basePath+"/data/batchExpStatus.json", text:"name", value:"value"},
					{field:"toFinanceStatus",title:"批次报财委状态:",type:"combobox", url:basePath+"/data/toFinanceStatus.json", text:"name", value:"value"},
					{field:"payBatchTotalAmount",title:"拨付总金额（元）:",type:"text"},
					{startField:"createStartDate",endField:"createEndDate",title:"批次生成时间:",type:"date",section:true},
					{startField:"expStartDate",endField:"expEndDate",title:"拨付导出时间:",type:"date",section:true}
			        ],
			tools:[			       
			       {type:"BATCH_Export",icon:"icon-add",title:"批次导出",text_width:100,
				     	  fn:function() {
							var selectedRows = this.datagrid("getSelections");
							var ids=[];
							var infoMsg = null;
							infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							}else {
								$.messager.confirm('Confirm','你确定要导出?',function(r){
									if(r){
								var i = 0;
								for (; i < selectedRows.length ; i ++) {
									if (selectedRows[i].batchToFinanceStatus == "已报财委" || selectedRows[i].batchToFinanceStatus == "再次已报") {
										break;
									}
									ids.push(selectedRows[i].id);
								}
								ids = ids.join(",")+"";
								 if (i != selectedRows.length) {
									 Messager.alert({
											type : "info",
											title : "警告",
											content : "选中的批次中含有已报财委的数据，无法导出!"
										});
								 } else {
									/*  Messager.confirm({
										   title : "提示",
										   content :"你选择了  <font color=red>"+selectedRows.length+"</font>  条批次<br>"+"是否导出",
										   handler :function(r){
											   if(r){
												   Messager.alert({
														type : "info",
														title : "提示 ",
														content : "批次导出成功"
													});
											   }
										   }
									   }); */
									  $.get(basePath+"/payApply/repBatchExport.do",{id:selectedRows[0].id},function(data){
										  Messager.alert({
												type : "info",
												title : "提示 ",
												content : data.message
											});
										  $("#repBatchExport-list #repBatchExport-grid").datagrid("load");
								 	})
								 	
								 }
							}
								})
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