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
	
	<div id = "repeatBatchAdjust-list" class="easyui-panel  easyui-panel-style" data-options="title: '重报内部批次调整',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repeatBatchAdjust-grid-toolbar">
				<table id="repeatBatchAdjust-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="repeatBatchAdjust-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repeatBatchAdjust-list #repeatBatchAdjust-grid").datagrid({
			toolbar : "#repeatBatchAdjust-grid-toolbar",
			url : basePath+"/payApply/repBatchAdjust.do",
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
					if (row.expRecentDate) {
						return getNowFormatDate(new Date(row.expRecentDate.time))
					} else {
						return "";
					}
				}
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
				title : "重报拨付批次总金额(元)",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "createUser",
				title : "重报批次生成人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,
					url:basePath+"/payApply/repBatchView.do?id="+rowData.id+"&type=view",content:"重报批次受理单明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"batchNo",title:"重报内部批次号：",type:"text"},
					{startField:"batchCreateStartDate",endField:"batchCreateEndDate",title:"重报批次生成时间:",type:"date",section:true}
			        ],
			tools:[			    
			       {type:"DELETE",icon:"icon-add",title:"作废",text_width:100,
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
								$.messager.confirm('确认框','你确定要将此数据作废吗?',function(r){
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
											content : "选中的批次中含有已报财委  或者 是被作废数据，无法进行作废操作!"
										});
								 } else {
									  $.get(basePath+"/payApply/repBatchCancel.do",{ids:ids},function(data){
										  Messager.alert({
												type : "info",
												title : "&nbsp;",
												content : data.message
											});
										  $("#repeatBatchAdjust-list #repeatBatchAdjust-grid").datagrid('load');
								 	})
								 }
								 }
								})
							}
					 	  }
				       },
				    {type:"BATCH_ADJUST",title:"调整批次",text_width:100,icon:"icon-edit",
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
								if (selectedRows[0].batchToFinanceStatus == "已报财委" || selectedRows[0].batchToFinanceStatus == "再次已报") {
									Messager.alert({
										type : "info",
										title : "警告",
										content : "已报财委的批次数据无法调整!"
									});
								} else {
									openDialog({
									   	type : "batch_adjust",
										title : "批次调整",
										width : 1012,
										height : 550,
										param: {reset:false,save:false,close:false},
										maximizable : true,
										href : basePath+"/payApply/repBatchNoList.do?id="+selectedRows[0].id
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