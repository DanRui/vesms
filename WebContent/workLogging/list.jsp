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
	<div id = "work-logging-list" class="easyui-panel easyui-panel-style" data-options="title: '工作记录查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="workLoggingList-grid-toolbar">
				<table id="work-logging-list-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="work-logging-list-grid" style = "width:97.5%;height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function() {
		var basePath = $("#basePath").val();
		$("#work-logging-list #work-logging-list-grid").datagrid({
			toolbar : "#workLoggingList-grid-toolbar",
			url : basePath+"/workLogging/list.do",
			method : "post",
			sortName : "actionTime",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			}, {
				field : "post",
				title : "操作岗位",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var postName = "";
					switch (row.post) {
						case "CKSLG" :
							postName = "窗口受理岗";
							break;
						case "YBSLXXXZG" :
							postName = "一般受理信息修正岗";
							break;
						case "BTDXXZG" :
							postName = "补贴对象修正岗";
							break;
						case "KJCSG" :
							postName = "会计初审岗";
							break;
						case "CKSHG" :
							postName = "窗口审核岗";
							break;
						case "KZSHG" :
							postName = "科长审核岗";
							break;
						case "CZSHG" :
							postName = "处长审核岗";
							break;
						case "KJFSG" : 
							postName = "会计复审岗"; 
							break;
						case "BFSBG" :
							postName = "拨付申报岗";
							break;
						case "BFJGBJG" :
							postName = "拨付结果标记岗";
							break;
						case "YWBJG" :
							postName = "业务办结岗";
							break;
						default:
							postName = "";
					}
					return postName;
				}
			}, {
				field : "actionUserName",
				title : "操作人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "action",
				title : "操作动作",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			}, {
				field : "actionResult",
				title : "操作结果",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "actionTime",
				title : "操作时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.actionTime) {
						return getNowFormatDate(new Date(row.actionTime.time))
					} else {
						return "";
					}
				}
			}, {
				field : "applyNo",
				title : "业务受理单号",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "vehiclePlateNum",
				title : "号牌号码",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "vehiclePlateTypeName",
				title : "号牌种类",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "emissionStandard",
				title : "排放标准",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "iolTypeName",
				title : "燃油种类",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "subsidiesMoney",
				title : "补贴金额(元)",
				width : "9%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "currentPost",
				title : "业务当前岗位",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var postName = "";
					switch (row.currentPost) {
						case "CKSLG" :
							postName = "窗口受理岗";
							break;
						case "YBSLXXXZG" :
							postName = "一般受理信息修正岗";
							break;
						case "BTDXXZG" :
							postName = "补贴对象修正岗";
							break;
						case "KJCSG" :
							postName = "会计初审岗";
							break;
						case "CKSHG" :
							postName = "窗口审核岗";
							break;
						case "KZSHG" :
							postName = "科长审核岗";
							break;
						case "CZSHG" :
							postName = "处长审核岗";
							break;
						case "KJFSG" : 
							postName = "会计复审岗"; 
							break;
						case "BFSBG" :
							postName = "拨付申报岗";
							break;
						case "BFJGBJG" :
							postName = "拨付结果标记岗";
							break;
						case "YWBJG" :
							postName = "业务办结岗";
							break;
						default:
							postName = "";
					}
					return postName;
				}
			}, {
				field : "bussinessStatus",
				title : "业务当前状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "1") {
						return "color:green";
					} else if (value == "2") {
						return "color:gray";
					} else if (value == "-1") {
						return "color:red";
					}
				},
				formatter : function(value, row, index) {
					if (value == "1") {
						return "正常";
					} else if (value == "2") {
						return "结束";
					} else {
						return "异常";
					}
				} 
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:1050,height:800,url:basePath+"/workLogging/workLogView.do?id="+rowData.applyId+"&type=applyLog",
					content:"受理单详细信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"post",title:"操作岗位:",type:"combobox",panelHeight:true, url:basePath+"/workLogging/getPostList.do", text:"value", value:"code"},
					{field:"actionUser",title:"操作人:",type:"combobox",panelHeight:true, url:basePath+"/workLogging/getActionUserList.do", text:"value", value:"code"},
					{field:"action",title:"操作动作:",type:"combobox",panelHeight:true, url:basePath+"/workLogging/getActionNameList.do", text:"value", value:"code"},
					{field:"actionResult",title:"操作结果:",type:"combobox",panelHeight:true, url:basePath+"/workLogging/getActionResultList.do", text:"value", value:"code"},
					/* {field:"applyNo",title:"受理单号:",type:"text"},
					{field:"vehicleOwner",title:"车主:",type:"text"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					{field:"batchNo",title:"批次号:",type:"text"},
					{field:"archiveBoxNo",title:"档案盒号:",type:"text"},
					{field:"subsidiesMoney",title:"补贴金额（元）:",type:"text"},
					{field:"concludeStatus",title:"办结类型:",type:"combobox",panelHeight:true,url:basePath+"/data/finishApplyStatus.json", text:"name", value:"value"},
					{field:"businessStatus",title:"业务状态:",type:"combobox",panelHeight:true,url:basePath+"/data/bussStatus.json", text:"name", value:"value"}, */
					{startField:"startTime",endField:"endTime",title:"操作时间:",type:"date",section:true}
					],
			tools:[
			       {type:"QUERY"}/* ,
			       {type:"PRINT_RECEIPT",icon:"icon-print",title:"打印受理回执单",text_width:150,
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
								if (selectedRows[0].applyConfirmTime != null) {
									openDialog({
									   	type : "PRINT_RECEIPT",
										title : "受理回执单打印预览",
										width : 1030,
										height : 500,
										param: {reset:false,save:false,close:false},	
										maximizable : true,
										href : basePath+"/eliminatedApply/receiptPreview.do?id="+selectedRows[0].id
								   });
								} else {
									Messager.alert({
										type : "info",
										title : "警告",
										content : "未确认的业务记录，无法打印受理回执单"
									});
								}
							}
					   }
				   } */
			      ],
			module:"M_COMMON_QUERY_WORK",
			shownum:3,
			debug:false
		});
		
		// 设置岗位信息联动
		$("#post").combobox({
			onSelect : function() {
				var postCode = $(this).combobox("getValue");
				// 根据岗位查询操作过的人员
				var actionUserUrl = basePath + "/workLogging/getActionUserList.do?postCode="+postCode;
				$("#actionUser").combobox("reload", actionUserUrl);
				
				// 根据岗位查询操作过的动作
				var actionNameUrl = basePath + "/workLogging/getActionNameList.do?postCode="+postCode;
				$("#action").combobox("reload", actionNameUrl);
				
				// 根据岗位查询操作过的结果
				var actionResultUrl = basePath + "/workLogging/getActionResultList.do?postCode="+postCode;
				$("#actionResult").combobox("reload", actionResultUrl);
			}
		});
	});
	
	</script>
</body>
</html>