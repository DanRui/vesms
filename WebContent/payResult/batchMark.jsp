<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
			<td class="view_table_right">${v.isExported }</td>
			<td class="view_table_left">批次报财委状态：</td>
			<td class="view_table_right">${v.toFinanceStatus}</td>
			<td class="view_table_left">批次生成时间：</td>
			<td class="view_table_right">${v.createDate }</td>
		</tr>
	</table>
	</br>
	<div id = "batch-mark-list" class="easyui-panel easyui-panel-style" data-options="title: '受理单明细列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="batchMarkList-grid-toolbar">
				<table id="batch-mark-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="batch-mark-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#batch-mark-list #batch-mark-grid").datagrid({
			toolbar : "#batchMarkList-grid-toolbar",
			url : basePath+"/payResult/markResultList.do?batchNo="+'${v.batchNo }',
			//url : basePath+"/data/batchDetail.json",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "payResStatus",
				title : "拨付结果",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未标记";
					} else if (value == "1") {
						return "拨付成功";
					}else if (value == "2") {
						return "拨付异常";
					}else if (value == "3") {
						return "财务退回";
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
				field : "applyNo",
				title : "受理单号",
				width : "12%",
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
				field : "applyTime",
				title : "受理时间",
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
			}*/
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				/* $(this).datagrid("view",{width:900,height:450,url:basePath+"/eliminatedCheck/view.jsp?id="+rowData.id,content:"申报受理单信息查看"}); */
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox",url:basePath+"/data/vehiclePlateType.json", text:"name", value:"value"},
					{field:"vehicleOwner",title:"受理单号:",type:"text"},
					{field:"payBatchResStatus",title:"拨付结果:",type:"combobox", url:basePath+"/data/payResStatus.json", text:"name", value:"value"}
					],
			tools:[
			       {type:"SINGLE_MARK",title:"标记拨付结果",text_width:120,icon:"icon-biaoji",
			    	   fn:function() {
							var selectedRows = this.datagrid("getSelections");
							var infoMsg = null;
							var ids=[];
							for (var i=0;i<selectedRows.length;i++){					
								ids.push(selectedRows[i].id);
							}
							var ids = ids.join(",")+"";
							/* infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null); */
							infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							} else {
								Dialog.create("pay_res_mark", {
									type : "single_mark",
									title : "批次拨付结果标记",
									width : 700,
									height : 200,
									param: {
											reset:false,
											buttons:[
													{id:"pay_res_mark_save",text:"保存",iconCls:"icon-save",handler:function(){
														var payResult = $("#checkType").combobox('getValue');
														var remark = $("#checkOpinion").val();
														//alert(payResult);
														var faultType =$('#faultType').combobox('getValue');
														var ifValid = $("form #pay-res-check-form").form("enableValidation").form("validate");
														if (ifValid) {
															$.get(basePath+"/payResult/payMark.do",{ids:ids,payResStatus:payResult,faultType:faultType,faultDesc:remark},function(data) {
													 			Messager.alert({
																	type : "info",
																	title : "&nbsp;",
																	content : data.message
																});
													 		});
															$("#pay_res_mark").dialog("close");
														}
													}},
													{id:"pay_res_mark_cancel",text:"取消",iconCls:"icon-cancel",handler:function(){
														$("#pay_res_mark").dialog("close");
													}}
												]
										},
									maximizable : true,
									href : basePath+"/payResult/batchMarkRes.jsp"
								});
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