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
	<div id = "eliminated-check-list-all" class="easyui-panel easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="elimiCheckListAll-grid-toolbar">
				<table id="eliminated-check-all-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="eliminated-check-all-grid" style = "width:97.5%;height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#eliminated-check-list-all #eliminated-check-all-grid").datagrid({
			toolbar : "#elimiCheckListAll-grid-toolbar",
			url : basePath+"/data/eliminatedCheckLog.json",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			}, {
				field : "applyNo",
				title : "受理单号",
				width : "10%",
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
				field : "finishStatus",
				title : "办结状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "正常办结") {
						return "color:green";
					} else if (value == "未办结") {
						return "color:red";
					} else if (value == "异常办结") {
						return "color:orange";
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
				field : "curPost",
				title : "当前岗位",
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
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:450,url:basePath+"/eliminatedCheck/checkLogView.jsp?id="+rowData.id
								+"&isPersonal="+rowData.isPersonal+"&isProxy="+rowData.isProxy,
								content:"审批详细信息查看"});
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox",url:basePath+"/data/vehiclePlateType.json", text:"name", value:"value"},
					{field:"vehicleType",title:"车辆类型:",type:"combobox",url:basePath+"/data/carType.json", text:"name", value:"value"},
					{field:"applyNo",title:"受理单号:",type:"text"},
					{field:"vehicleOwner",title:"车主:",type:"text"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					{startField:"checkStartDate",endField:"checkEndDate",title:"审批时间:",type:"date",section:true}
					],
			tools:[
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