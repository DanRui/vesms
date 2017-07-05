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
	<div id = "repeatPay-result-list" class="easyui-panel easyui-panel-style" data-options="title: '重报拨付结果标记',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repeatPayResultList-grid-toolbar">
				<table id="repeatPay-result-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="repeatPay-result-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repeatPay-result-list #repeatPay-result-grid").datagrid({
			toolbar : "#repeatPayResultList-grid-toolbar",
			url : basePath+"/payResult/repList.do",
			method : "post",
			sortName : "last_Update_Time",
			sortOrder : "desc",
			rownumbers : true,
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
			},{
				field : "repeatedBatchNo",
				title : "重报批次号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
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
				sortable : true,
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},
			{
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
				sortable : true,
			},{
				field : "subsidiesMoney",
				title : "补贴金额（元）",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true 
			},{
				field : "applyConfirmTime",
				title : "受理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.applyTime) {
						return getNowFormatDate(new Date(row.applyTime.time))
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
			/* {
				field : "batchCreateStatus",
				title : "批次生成状态",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "未生成") {
						return "color:red";
					} else if (value == "已生成") {
						return "color:green";
					}
				}
			} */
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/eliminatedApply/view.do?id="+rowData.id+"&type=applyLog",content:"受理单查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
			         {field:"toFinanceNo",title:"报送序号:",type:"text"},
					 {field:"repeatedBatchNo",title:"批次号:",type:"text"},
					 {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					 {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					 {field:"vehicleType",title:"车辆类型:",type:"combobox",url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_TYPE", text:"value", value:"code"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"}
			        ],
			tools:[
				   {type:"RESULT_MARK",title:"拨付结果标记",text_width:150,icon:"icon-biaoji",fn:function(){
						var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						var ids=[];
						for (var i=0;i<selectedRows.length;i++){					
							ids.push(selectedRows[i].id);
						}
						var ids = ids.join(",")+"";
						infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条" : null);
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						} else {
							Dialog.create("pay_res_mark", {
								type : "RESULT_MARK",
								title : "拨付结果标记",
								width : 700,
								height : 300,
								param: {
								reset:false,
								buttons:[
										{id:"pay_res_mark_save",text:"保存",iconCls:"icon-save",handler:function(){
											var payResult = $("#checkType").combobox('getValue');
											var remark = $("#checkOpinion").val();
											var faultType =$('#faultType').combobox('getValue');
											var ifValid = $("form #pay-res-check-form").form("enableValidation").form("validate");
											if (ifValid) {
												$.get(basePath+"/payResult/payRepMark.do",{ids:ids,payResStatus:payResult,faultType:faultType,faultDesc:remark},function(data) {
										 			Messager.alert({
														type : "info",
														title : "&nbsp;",
														content : data.message
													});
										 		});
												$("#pay_res_mark").dialog("close");
												$("#repeatPay-result-list #repeatPay-result-grid").datagrid('load');
											}
										}}
									]
								},
									maximizable : true,
									href : basePath+"/payResult/batchMarkRes.jsp"
							});
						} 
				 	}},
				   {type:"QUERY"},
				   {type:"CLEAR"}
				 	],
			module:"M_MARK_REP_APPLY",
			shownum:3
		})

	})
	
	</script>
</body>
</html>