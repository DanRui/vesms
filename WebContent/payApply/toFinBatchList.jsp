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
	
	<div id = "toFinance-list" class="easyui-panel  easyui-panel-style" data-options="title: '正常报财务批次查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="toFinance-grid-toolbar">
				<table id="toFinance-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="toFinance-grid" style = "height:97.5%"></table>
	</div>
	<div>
		<iframe id="iframeLoad" style="display:none;"/>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#toFinance-list #toFinance-grid").datagrid({
			toolbar : "#toFinance-grid-toolbar",
			url : basePath+"/payApply/toFinanceList.do",
			method : "post",
			rownumbers : true,
			sortName : "toFinanceTime",
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
			},
			{
				field : "batchNo",
				title : "内部批次号",
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
				field : "toFinanceTime",
				title : "批次报财务时间",
				width : "10%",
				align : "center",
				halign :"center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.toFinanceTime) {
						return getNowFormatDate(new Date(row.toFinanceTime.time))
					} else {
						return "";
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
			columns:[{field:"toFinanceNo",title:"报送序号：",type:"text"},
			         {field:"batchNo",title:"内部批次号：",type:"text"},
					{field:"payBatchTotalAmount",title:"拨付总金额（元）:",type:"text"},
					{startField:"createStartDate",endField:"createEndDate",title:"批次生成时间:",type:"date",section:true},
					{startField:"toFinanceStartTime",endField:"toFinanceEndTime",title:"报财务时间:",type:"date",section:true}
			        ],
			tools:[
					{type:"FILE_QUERY",icon:"icon-add",title:"报财务文件查看",text_width:150,
						  fn:function() {
							var selectedRows = this.datagrid("getSelections");
							//var ids=[];
							var infoMsg = null;
							infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							}else {
								//文件查看
								
								$.get(basePath+"/payApply/confirmBatchLook.do",{batchNo:selectedRows[0].batchNo},function(data) {
									//document.frames[0].location.href = basePath+"/payApply/fileDownload.do?filePath="+data.message+"&batchNo="+selectedRows[0].batchNo;
									$("#iframeLoad").attr("src", basePath+"/payApply/fileDownload.do?filepath="+data.message+"&batchNo="+selectedRows[0].batchNo);
									$("#iframeLoad").show();
								});
								
							/*	openDialog({
								   	type : "batch_List",
									title : "文件查看",
									width : 300,
									height : 200,
									param: {reset:false,save:false},
									maximizable : true,
									href : basePath+"/payApply/financeExcel.do?batchNo="+selectedRows[0].batchNo
								});*/
						  	}
							}},
							{type:"PDF_QUERY",icon:"icon-add",title:"图片文件查看",text_width:150,
								  fn:function() {
									var selectedRows = this.datagrid("getSelections");
									//var ids=[];
									var infoMsg = null;
									infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
									if (null != infoMsg) {
										Messager.alert({
											type : "info",
											title : "&nbsp;",
											content : infoMsg
										});
									}else {
										//文件查看
										$.get(basePath+"/payApply/confirmBatchPdf.do",{batchNo:selectedRows[0].batchNo},function(data) {
											$("#iframeLoad").attr("src", basePath+"/payApply/fileDownload.do?filepath="+data.message+"&batchNo="+selectedRows[0].batchNo);
											$("#iframeLoad").show();
										});
								  	}
							}},
							{type:"PREVIEW_QUERY",icon:"icon-add",title:"审批表查看",text_width:150,
								  fn:function() {
									var selectedRows = this.datagrid("getSelections");
									//var ids=[];
									var infoMsg = null;
									infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
									if (null != infoMsg) {
										Messager.alert({
											type : "info",
											title : "&nbsp;",
											content : infoMsg
										});
									}else {
										//文件查看
										$.get(basePath+"/payApply/confirmBatchPreview.do",{batchNo:selectedRows[0].batchNo},function(data) {
											$("#iframeLoad").attr("src", basePath+"/payApply/fileDownload.do?filepath="+data.message+"&batchNo="+selectedRows[0].batchNo);
											$("#iframeLoad").show();
										});
								  	}
							}},
							{type:"QUERY"},
							{type:"CLEAR"}
				  ],
				 
			module:"M_TO_FIN_LIST",
			shownum:3
		})

		
	})
	</script>
</body>
</html>