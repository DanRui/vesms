<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
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
			<td class="view_table_left">批次号：</td>
			<td class="view_table_right">${ v.batchNo }</td>
			<td class="view_table_left">业务单数：</td>
			<td class="view_table_right">${v.payBussCount }</td>
			<td class="view_table_left">拨付总金额（元）：</td>
			<td class="view_table_right">${v.payBatchTotalAmount }</td>
		</tr>
		<tr class="datagrid-row">
			<td class="view_table_left">批次导出状态：</td>
			<td class="view_table_right">
				<c:if test="${v.isExported == '0'}">
						未导出
				</c:if>
				<c:if test="${v.isExported == '1'}">
						已导出
				</c:if>
				<c:if test="${v.isExported == '2'}">
						待重新导出
				</c:if>
			</td>
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
			<td class="view_table_right">
			<fmt:formatDate value="${v.createDate}" type="time" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
			<tr class="datagrid-row">
			<td class="view_table_left">拨付结果详情：</td>
			<td class="view_table_right">${v.payResRemark }</td>
		</tr>
	</table>
	</br>
	<div id = "repBatch-adjust-list" class="easyui-panel easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repBatchAdjustList-grid-toolbar">
				<table id="repBatch-adjust-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="repBatch-adjust-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		//var batchNo=${v.batchNo};
		var basePath = $("#basePath").val();
		$("#repBatch-adjust-list #repBatch-adjust-grid").datagrid({
			toolbar : "#repBatchAdjustList-grid-toolbar",
			url : basePath+"/payApply/repAdjustList.do?repeatedBatchNo="+'${v.batchNo}',
			method : "post",
			sortName : "lastUpdateTimeDate",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "applyNo",
				title : "受理单号",
				width : "15%",
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
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "subsidiesMoney",
				title : "补贴金额（元）",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "toFinanceStatus",
				title : "业务报财委状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var num = parseInt (value);
					if (num < -1) {
						return "待报财务";
					} else if (num > 1) {
						return "已报财务";
					}
				},
				styler : function(value, row, index) {
					var num = parseInt (value);
					if (num < -1) {
						return "color:gray";
					} else if (num > 1) {
						return "color:red";
					} 
				}
			},{
				field : "applyConfirmTime",
				title : "受理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.applyConfirmTime) {
						return getNowFormatDate(new Date(row.applyConfirmTime.time))
					} else {
						return "";
					}
				}
			}/*,{
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
			},{
				field : "payResStatus",
				title : "拨付结果",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "未拨付") {
						return "color:gray";
					} else if (value == "拨付成功") {
						return "color:green";
					} else if (value == "拨付失败") {
						return "color:red";
					} else if (value == "财委退回") {
						return "color:blue";
					}
				}
			} */
			] ],
		/*	onDblClickRow : function(rowIndex, rowData) {
				 $(this).datagrid("view",{width:900,height:450,url:basePath+"/payResult/viewApply.jsp?id="+rowData.id,content:"申报受理清单查看"}); 
			}  */
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox",url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					{field:"applyNo",title:"受理单号:",type:"text"}
				//	{field:"batchToFinanceStatus",title:"报财委状态:",type:"combobox", panelHeight:true,url:basePath+"/data/toFinanceStatus.json", text:"name", value:"value"}
					/* {field:"payBatchResStatus",title:"拨付导出状态:",type:"combobox", url:basePath+"/data/batchExpStatus.json", text:"name", value:"value"} */
					],
			tools:[
			       
			      {type:"DELETE",title:"移出", fn:function() {
			    	  	var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
						var ids=[];
						for (var i=0;i<selectedRows.length;i++){					
							ids.push(selectedRows[i].id);
						} 
						var ids = ids.join(",")+"";
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						} else {
							/* $.messager.confirm("确认框",'你确定移出吗',function(r){
							if(r){		 */						
						 	$.get(basePath+"/payApply/repApplyDelete.do",{ids:ids,batchId:'${v.id}'},function(data) {
					 			Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : data.message
								});
					 		//	$("#repBatch-adjust-list #repBatch-adjust-grid").datagrid("load");
						 		$("#common-dialog").dialog('refresh');
					 			$("#repeatBatchAdjust-list #repeatBatchAdjust-grid").datagrid('load');
						 	});
							}
						//	})
						//}
			      }},
			      {type:"ADD_APPLY",title:"增加业务单",text_width:120,icon:"icon-add",
			    	fn:function() {
					Dialog.create("repBatch_add_apply", {
							type : "add_apply",
							title : "受理单查询",
							width : 1012,
							height : 500,
							param: {
									reset:false,
									buttons:[
									{id:"repBatch_add_apply_save",text:"确认",iconCls:"icon-save",handler:function(){
										var ids=[];
										var selectedRows = $("#repBatch-add-list #repBatch-add-grid").datagrid("getSelections");
										for (var i=0;i<selectedRows.length;i++){					
											ids.push(selectedRows[i].id);
										} 
										var ids = ids.join(",")+"";
										$.get(basePath+"/payApply/addApplyToRepBatch.do",{ids:ids,batchId:'${v.id}'},function(data){
										 		Messager.alert({
													type : "info",
													title : "&nbsp;",
													content : data.message
												});
										 		$("#repBatch_add_apply").dialog("close");
										 		$("#common-dialog").dialog('refresh');
											 	$("#repeatBatchAdjust-list #repeatBatchAdjust-grid").datagrid('load'); 	
										})
									}},
							/*		{id:"repBatch_add_apply_cancel",text:"取消",iconCls:"icon-cancel",handler:function(){
										$("#repBatch_add_apply").dialog("close");
									}}*/
								]
							},
							maximizable : true,
							href : basePath+"/payApply/repBatchAddApplyView.do?batchId="+'${v.id}'
						});
					}
			      },
				  {type:"QUERY"},
				  {type:"CLEAR"}
			     ],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true
		})
 
	})
	
	</script>
</body>
</html>