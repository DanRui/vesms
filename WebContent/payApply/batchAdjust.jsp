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
	
	<div id = "batchAdjust-list" class="easyui-panel  easyui-panel-style" data-options="title: '正常内部批次调整',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="batchAdjust-grid-toolbar">
				<table id="batchAdjust-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="batchAdjust-grid" style = "height:97.5%"></table>
	</div>


	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#batchAdjust-list #batchAdjust-grid").datagrid({
			toolbar : "#batchAdjust-grid-toolbar",
			url : basePath+"/payApply/batchAdjustList.do",
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
				title : "批次报财务状态",
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
				$(this).datagrid("view",{width:970,height:800,
					url:basePath+"/payApply/batchView.do?id="+rowData.id+"&batchType="+rowData.batchType+"&type=view",
							content:"批次受理单明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"内部批次号：",type:"text"},
					{startField:"createStartDate",endField:"createEndDate",title:"批次生成时间:",type:"date",section:true}
			        ],
			tools:[		
			       {type:"CANCEL",icon:"icon-remove",title:"作废",text_width:100,
				     	  fn:function() {
							var selectedRows = this.datagrid("getSelections");
							var ids=[];
							var infoMsg = null;
							infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							}else {
								$.messager.confirm('确认框','你确定要作废该批次吗?',function(r){
								if(r){
									$.ajaxSetup({  
									    async : false  
									});  
								var i = 0;
								for (; i < selectedRows.length ; i ++) {
									if (selectedRows[i].toFinanceStatus != "0" || selectedRows[i].batchStatus == "2") {
										break;
									}
									ids.push(selectedRows[i].id);
								}
								ids = ids.join(",")+"";
								 if (i != selectedRows.length) {
									 Messager.alert({
											type : "info",
											title : "警告",
											content : data.message
										});
								 } else {
									  $.get(basePath+"/payApply/batchCancel.do",{ids:ids},function(data){
										  Messager.alert({
												type : "info",
												title : "&nbsp;",
												content : data.message
											});
										  $("#batchAdjust-list #batchAdjust-grid").datagrid('load');
								 	})
								 }
								}
								})
							}
					 	  }
				     },
				            				       
				    {type:"ADJUST",title:"调整批次",text_width:100,icon:"icon-edit",
				    	fn:function() {
				    		var selectedRows = this.datagrid("getSelections");
							var infoMsg = null;
							infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							} else {
								if (selectedRows[0].batchToFinanceStatus == "已报财务" || selectedRows[0].batchToFinanceStatus == "再次已报") {
									Messager.alert({
										type : "info",
										title : "警告",
										content : "已报财务的批次数据无法调整!"
									});
								} else {
									openDialog({
									   	type : "batch_adjust",
										title : "批次调整",
										width : 1012,
										height : 550,
										param: {reset:false,save:false,close:false},
										maximizable : true,
										href : basePath+"/payApply/batchNoList.do?id="+selectedRows[0].id
								   });
								}
							}
				    	}	   
				    },
					   {type:"QUERY"}, 
					   {type:"CLEAR"} 
				    
				  ],
			module:"M_NOR_BATCH_ADJUST",
			shownum:3
		})

		
	})
	</script>
</body>
</html>