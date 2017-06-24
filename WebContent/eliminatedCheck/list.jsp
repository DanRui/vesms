<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String basePath = request.getContextPath();
String currentPost = request.getParameter("currentPost");
String mdlCode = request.getParameter("mdlCode");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id = "eliminated-check-list" class="easyui-panel easyui-panel-style" data-options="title:'正常业务审核',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="elimiCheckList-grid-toolbar">
				<table id="eliminated-check-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="eliminated-check-grid" style = "width:97.5%;height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		var currentPost = "<%=currentPost%>";
		var mdlCode = "<%=mdlCode%>";
		
		$("#eliminated-check-list #eliminated-check-grid").datagrid({
			toolbar : "#elimiCheckList-grid-toolbar",
			url : basePath+"/eliminatedCheck/list.do?currentPost="+currentPost,
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			rownumbers : true,
			/* pageSize : 100, */
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "applyNo",
				title : "受理单号",
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
				width : "5%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "subsidiesMoney",
				title : "补贴金额(元)",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
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
			},{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "15%",
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
					if (row.applyConfirmTime) {
						return getNowFormatDate(new Date(row.applyConfirmTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
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
				title : "上一节点处理时间",
				width : "12%",
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
				field : "deadline",
				title : "报废期止",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.deadline) {
						return getNowFormatDate(new Date(row.deadline.time))
					} else {
						return "";
					}
				}
			}/*,{
				field : "lastUpdatePerson",
				title : "处理人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "checkDept",
				title : "部门",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}*/
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:1132,height:800,url:basePath+"/eliminatedCheck/view.do?id="+rowData.id
						+"&type=normal",
						content:"受理单详细信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox", panelHeight:false, url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					{field:"vehicleType",title:"车辆类型:",type:"combobox", panelHeight:false, url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_TYPE", text:"value", value:"code"},
					{field:"applyNo",title:"受理单号:",type:"text"},
					{field:"vehicleOwner",title:"车主:",type:"text"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					{startField:"applyStartDate",endField:"applyEndDate",title:"受理时间:",type:"date",section:true}
					],
			tools:[
				   {type:"CHECK",title:"批量审核",text_width:100,icon:"icon-check-apply",fn:function() {
					   	var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						//infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
						infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
						var ids = "";
						for(i = 0 ; i < selectedRows.length ; i ++) {
							ids += selectedRows[i].id + ",";
						}
						ids = ids.substring(0, ids.length - 1);
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						} else {
							openDialog({
								type : "check",
								title : "受理单审批",
								width : 600,
								height : 300,
								param: {reset:false, save:false,
										buttons : [{
										           id:"check-submit",
										           text:"提交",iconCls:"icon-save"}
										          ]
									   },
								maximizable : true,
								href : basePath+"/eliminatedCheck/checkView.do?ids="+ids+"&currentPost="+currentPost+"&mdlCode="+'${param.mdlCode}'
							});
						}
				   }}, 
				   {type:"QUERY"}],
			module : '${param.mdlCode}',
			shownum:3 ,
			debug:false
		})

	})
	
	</script>
</body>
</html>