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
	
	<div id = "batchMain-list" class="easyui-panel  easyui-panel-style" data-options="title: '正常内部批次查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="batchMain-grid-toolbar">
				<table id="batchMain-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="batchMain-grid" style = "height:97.5%"></table>
	</div>


	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#batchMain-list #batchMain-grid").datagrid({
			toolbar : "#batchMain-grid-toolbar",
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
				title : "内部批次号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "toFinanceStatus",
				title : "批次报财委状态",
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
			columns:[{field:"batchNo",title:"内部批次号：",type:"text"},
			         {field:"batchStatus",title:"批次状态：",type:"combobox", panelHeight:true, url:basePath+"/data/batchStatus.json", text:"name", value:"value"},
					 {startField:"createStartDate",endField:"createEndDate",title:"批次生成时间:",type:"date",section:true}
			        ],
			tools:[		
			       {type:"BATCH_PREVIEW",icon:"icon-add",title:"审批表预览",text_width:100,
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
							}else if(selectedRows[0].batchStatus!="1"){
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : "已作废的批次不能进行预览"
								})
							}else{
								//文件预览 
								openDialog({
									   	type : "batch_List",
										title : "审批表预览",
										width : 300,
										height : 200,
										param: {reset:false,save:false,close:false},
										maximizable : true,
										href : basePath+"/payApply/toExcelPreview.do?id="+selectedRows[0].id
								   });
							}
					 	  }
				       },
					   {type:"QUERY"},
					   {type:"CLEAR"}
				  ],
			module:"M_NOR_BATCH_LIST",
			shownum:3
		})

		
	})
	</script>
</body>
</html>