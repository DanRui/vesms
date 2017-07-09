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
	<div id = "eliminated-apply-list-all" class="easyui-panel easyui-panel-style" data-options="title: '申报受理综合查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="elimiApplyListAll-grid-toolbar">
				<table id="eliminated-apply-all-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="eliminated-apply-all-grid" style = "width:97.5%;height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function() {
		var basePath = $("#basePath").val();
		$("#eliminated-apply-list-all #eliminated-apply-all-grid").datagrid({
			toolbar : "#elimiApplyListAll-grid-toolbar",
			url : basePath+"/workLogging/listApply.do",
			method : "post",
			sortName : "lastUpdateTimeDate",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			}, {
				field : "applyNo",
				title : "受理单号",
				width : "15%",
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
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			}, {
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "recycleDate",
				title : "交售日期",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.recycleDate) {
						return getNowFormatDate(new Date(row.recycleDate.time))
					} else {
						return "";
					}
				}
			}, {
				field : "subsidiesMoney",
				title : "补贴金额(元)",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "vehicleOwner",
				title : "车主",
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
				field : "batchNo",
				title : "批次号",
				width : "5%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "archiveBoxNo",
				title : "档案盒号",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "concludeStatus",
				title : "办结类型",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "1") {
						return "color:green";
					} else if (value == "0") {
						return "color:red";
					} else if (value == "-1") {
						return "color:orange";
					}
				},
				formatter : function(value, row, index) {
					if (value == "1") {
						return "正常办结";
					} else if (value == "0") {
						return "未办结";
					} else {
						return "终止办结";
					}
				}
			}, {
				field : "bussinessStatus",
				title : "业务状态",
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
			}, {
				field : "currentPost",
				title : "当前岗位",
				width : "10%",
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
			},{
				field : "prePost",
				title : "上一岗位",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var postName = "";
					switch (row.prePost) {
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
			},{
				field : "lastUpdateTimeDate",
				title : "最近处理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.lastUpdateTimeDate) {
						return getNowFormatDate(new Date(row.lastUpdateTimeDate.time))
					} else {
						return "";
					}
				}
			},{
				field : "lastUpdateUser",
				title : "处理人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
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
			}, {
				field : "EndTime",
				title : "结束时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.EndTime) {
						return getNowFormatDate(new Date(row.EndTime.time))
					} else {
						return "";
					}
				}
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:1050,height:632,url:basePath+"/workLogging/view.do?id="+rowData.id+"&type=applyLog",
					content:"受理单详细信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox",panelHeight:true, url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					{field:"vehicleType",title:"车辆类型:",type:"combobox",panelHeight:false, url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_TYPE", text:"value", value:"code"},
					{field:"applyNo",title:"受理单号:",type:"text"},
					{field:"vehicleOwner",title:"车主:",type:"text"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					{field:"batchNo",title:"批次号:",type:"text"},
					{field:"archiveBoxNo",title:"档案盒号:",type:"text"},
					{field:"subsidiesMoney",title:"补贴金额（元）:",type:"text"},
					{field:"concludeStatus",title:"办结类型:",type:"combobox",panelHeight:true,url:basePath+"/data/finishApplyStatus.json", text:"name", value:"value"},
					{field:"businessStatus",title:"业务状态:",type:"combobox",panelHeight:true,url:basePath+"/data/bussStatus.json", text:"name", value:"value"},
					{field:"currentPost",title:"业务当前岗位:",type:"combobox",panelHeight:false, url:basePath+"/workLogging/getPostList.do", text:"value", value:"code"},
					{startField:"startTime",endField:"endTime",title:"受理时间:",type:"date",section:true}
					],
			tools:[
			       {type:"QUERY"},
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
										param: {reset:false,save:false,close:false/* ,
											buttons:[{
												id : "print_apply_table",
												text : "打印",
												iconCls : "icon-print",
												fn : function() {
													window.print();
												}
											}]	 */
										},	
										maximizable : true,
										href : basePath+"/workLogging/receiptPreview.do?id="+selectedRows[0].id
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
				   },
				   {type:"CLEAR"}
			      ],
			module:"M_COMMON_QUERY_ALL",
			shownum:3,
			debug:false
		})
	})
	
	</script>
</body>
</html>