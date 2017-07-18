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
	
	<div id = "repeatToFinance-list" class="easyui-panel  easyui-panel-style" data-options="title: '重报批次报财务',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repeatToFinance-grid-toolbar">
				<table id="repeatToFinance-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="repeatToFinance-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repeatToFinance-list #repeatToFinance-grid").datagrid({
			toolbar : "#repeatToFinance-grid-toolbar",
			url : basePath+"/payApply/repBatchToFinList.do",
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
				title : "重报内部批次号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "createDate",
				title : "重报批次生成时间",
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
				title : "重报批次生成人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "toFinanceStatus",
				title : "重报批次报财委状态",
				width : "12%",
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
				title : "重报批次总金额(元)",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "batchType",
				title : "批次类型",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
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
						return "color:green";
					} else if (value == "2") {
						return "color:red";
					}
				}
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:500,
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&batchType="+rowData.batchType+"&type=view",
					content:"批次受理单明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"重报内部批次号：",type:"text"},
					{startField:"createStartDate",endField:"createEndDate",title:"重报批次生成时间:",type:"date",section:true}
			        ],
			tools:[			       
				    {type:"CONFIRM_APPLY",icon:"icon-ok",title:"报财委确认",text_width:100,
						   fn:function() {
								var selectedRows = this.datagrid("getSelections");
								var infoMsg = null;
								infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
								if (null != infoMsg) {
									Messager.alert({
										type : "info",
										title : "&nbsp;",
										content : infoMsg
									});
								}else {
									$.messager.confirm('报财务确认','你确定将选中的批次进行报财务吗?',function(r){
										if(r){							
											 	openDialog({
												   	type : "batch_adjust",
													title : "批次报财务",
													width : 300,
													height : 200,
													param: {reset:false,save:false,close:false},
													maximizable : true,
													href : basePath+"/payApply/confirmRepExportExcel.do?id="+selectedRows[0].id
											   });
											}
										
										
										/* 	if(r){
									if (null != infoMsg) {
										Messager.alert({
											type : "info",
											title : "&nbsp;",
											content : infoMsg
										});
									} else {
										 $.ajaxSetup({  
											async : false  
										 });  
									 	 $.post(basePath+"/payApply/confirmRepBatch.do",{id:selectedRows[0].id},function(data) {
								 			Messager.alert({
												type : "info",
												title : "&nbsp;",
												content : data.message
											});
								 			$("#repeatToFinance-list #repeatToFinance-grid").datagrid("load");
								 		}); 
										openDialog({
										   	type : "batch_toFinance",
											title : "批次报财务",
											width : 300,
											height : 200,
											param: {reset:false,save:false},
											maximizable : true,
											href : basePath+"/payApply/confirmRepExportExcel.do?id="+selectedRows[0].id
									   });
									}
								} */
									
									
								})
								}
						   }
					   },
					   {type:"QUERY"},
					   {type:"CLEAR"}
				  ],
			module:"M_REP_TO_FIN_CONFIRM",
			shownum:3
		})

		
	})
	</script>
</body>
</html>