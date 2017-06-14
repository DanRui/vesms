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
	<div id = "update-acc-check-list" class="easyui-panel easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="updateAccCheckList-grid-toolbar">
				<table id="update-acc-check-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="update-acc-check-grid" style = "width:97.5%;height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#update-acc-check-list #update-acc-check-grid").datagrid({
			toolbar : "#updateAccCheckList-grid-toolbar",
			url : basePath+"/data/updateAccCheck.json",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			}, {
				field : "vehiclePlateNum",
				title : "号牌号码",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "vehiclePlateType",
				title : "号牌种类",
				width : "5%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "vehicleType",
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
				sortable : true
			},{
				field : "checkResult",
				title : "审批结果",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "通过") {
						return "color:green";
					} else if (value == "未通过") {
						return "color:red";
					}
				}
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "10%",
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
				sortable : true
			},{
				field : "lastUpdateTime",
				title : "上一节点处理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "deadline",
				title : "报废期止",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}/*,{
				field : "lastUpdatePerson",
				title : "受理人",
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
				$(this).datagrid("view",{width:900,height:450,url:basePath+"/eliminatedModify/viewUpdateAccCheck.jsp?id="+rowData.id,content:"车辆补贴信息查看"});
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox",url:basePath+"/data/vehiclePlateType.json", text:"name", value:"value"},
					{field:"vehicleType",title:"车辆类型:",type:"combobox",url:basePath+"/data/carType.json", text:"name", value:"value"},
					{field:"vehicleOwner",title:"车主:",type:"text"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					{field:"bussinessStatus",title:"审批结果:",type:"combobox",url:basePath+"/data/checkResult.json", text:"name", value:"value"},
					{startField:"RecycleStartDate",endField:"checkDate",title:"审批时间:",type:"date",section:true},
					],
			tools:[
				   {type:"CHECK",title:"审核",icon:"icon-check",fn:function() {
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
							var i = 0;
							for (; i < selectedRows.length ; i ++) {
								if (selectedRows[i].checkResult == "通过") {
									break;
								}
							}
							 if (i != selectedRows.length) {
								 Messager.alert({
										type : "info",
										title : "警告",
										content : "选中的记录审批已通过，无法再次审批！"
									});
							 } else {
								 openDialog({
										type : "check",
										title : "补贴账户修改审批",
										width : 1012,
										height : 400,
										param: {reset:false},
										maximizable : true,
										href : basePath+"/eliminatedModify/updateAccCheck.jsp"
									});
							 }
						}
				   }}, 
				   {type:"QUERY"}],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true
		})

	})
	
	</script>
</body>
</html>