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
	<div id = "apply-special-list" class="easyui-panel easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="applySpecialList-grid-toolbar">
				<table id="apply-special-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="apply-special-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function() {
		var basePath = $("#basePath").val();
		$("#apply-special-list #apply-special-grid").datagrid({
			toolbar : "#applySpecialList-grid-toolbar",
			url : basePath+"/applySpecialAuthority/listSpecialApply.do",
			method : "post",
			sortName : "lastUpdateTimeDate",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "bussinessStatus",
				title : "业务状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.applyConfirmTime && row.currentPost == "CKSLG" && row.bussinessStatus == "-1") {
						return "退回待资料修正";
					} else if(!row.appConfirmTime) {
						return "受理未确认";
					}
				}
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
				width : "18%",
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
				title : "受理录入时间",
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
			}
			] ],
		/*	onDblClickRow : function(rowIndex, rowData) {
				/* $(this).datagrid("view",{width:900,height:450,url:basePath+"/payResult/viewApply.jsp?id="+rowData.id,content:"申报受理清单查看"});
			}   */
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox", panelHeight:true, url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					{field:"applyNo",title:"受理单号:",type:"text"}
					],
			tools:[
				  {type:"QUERY"},
				  {type:"CLEAR"}
			     ],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true
		})
		
		$("#common-dialog-apply-authority-confirm").click(function() {
			var selectedRows = $("#apply-special-list #apply-special-grid").datagrid("getSelections");
			var infoMsg = null;
			infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
			if (null != infoMsg) {
				Messager.alert({
					type : "info",
					title : "&nbsp;",
					content : infoMsg
				});
			} else {
				$.get(basePath+"/applySpecialAuthority/checkApplySpecial.do",{applyNo:selectedRows[0].applyNo},function(data) {
		 			
					if (data.success) {
						// 受理单可以申请授权，则弹出框提示填写申请原因
						Dialog.create("apply_reason", {
							type : "apply_reason",
							title : "填写申请原因",
							width : 500,
							height : 200,
							param: {
									reset:false,
									buttons:[
											{id:"apply_reason_confirm",text:"确认",iconCls:"icon-save"}
										]
								},
							maximizable : true,
							href : basePath+"/applySpecialAuthority/addApplyReason.do?id="+selectedRows[0].id
						});
					} else {
						// 无法授权，提示具体原因
						Messager.alert({
							type : "info",
							title : "&nbsp;",
							content : data.message.msg
						});
						
						$("#apply-auth-list #apply-auth-grid").datagrid('load');
			 			$("#common-dialog").dialog('close');
					}
		 		});
			}
		});
	});
	
	</script>
</body>
</html>