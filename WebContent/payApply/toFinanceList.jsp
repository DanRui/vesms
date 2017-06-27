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
	
	<div id = "repFinance-list" class="easyui-panel  easyui-panel-style" data-options="title: '正常批次报财务',headerCls:'panel-title-center'" style="height:100%">
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
			url : basePath+"/payApply/batchAdjustList.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "batchNo",
				title : "内部批次号",
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
				field : "toFinanceStatus",
				title : "批次报财务状态",
				width : "10%",
				align : "center",
				halign :"center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未报财务";
					} else if (value == "1") {
						return "已报财务";
					}
				},
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:red";
					} else if (value == "1") {
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
				$(this).datagrid("view",{width:900,height:800,
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&type=view",
							content:"申报受理单明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"内部批次号：",type:"text"},
					{field:"payBatchTotalAmount",title:"拨付总金额（元）:",type:"text"},
					{startField:"batchCreateStartDate",endField:"batchCreateEndDate",title:"批次生成时间:",type:"date",section:true}
			        ],
			tools:[			       
				    {type:"CONFIRM_APPLY",icon:"icon-ok",title:"报财务确认",text_width:100,
						   fn:function() {
								var selectedRows = this.datagrid("getSelections");
								var infoMsg = null;
							//	var ids=[];
								infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
								if (null != infoMsg) {
									Messager.alert({
										type : "info",
										title : "&nbsp;",
										content : infoMsg
									});
								}else {
								//	$.messager.confirm('Confirm','你确定要报财务吗?',function(r){
								//	    if (r){
									  //  	for (var i=0;i<selectedRows.length;i++){					
										//		ids.push(selectedRows[i].id);
										//	}
										//	var ids = ids.join(",")+"";
											if (null != infoMsg) {
												Messager.alert({
													type : "info",
													title : "&nbsp;",
													content : infoMsg
												});
											} else {
										$.messager.confirm('报财务确认','是否确定报送财务',function(r){
											if(r){												
											/*	$.ajaxSetup({  
												    async : false  
												});  
											 	$.post(basePath+"/payApply/confirmBatch.do",{id:selectedRows[0].id},function(data) {
										 			Messager.alert({
														type : "info",
														title : "&nbsp;",
														content : data.message
													});
										 			$("#repFinance-list #repFinance-grid").datagrid("load");
										 		}); */
											 	openDialog({
												   	type : "batch_adjust",
													title : "批次报财务",
													width : 300,
													height : 200,
													param: {reset:false,save:false,close:false},
													maximizable : true,
													href : basePath+"/payApply/toFinanceExcel.do?id="+selectedRows[0].id
											   });
											 	$("#repFinance-list #repFinance-grid").datagrid("load");
											}
										})
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