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
	<div id = "vehicle-recycle-list" class="easyui-panel easyui-panel-style" data-options="title: '报废车辆查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="vehicleRecyList-grid-toolbar">
				<table id="vehicle-recycle-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="vehicle-recycle-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function() {
		var basePath = $("#basePath").val();
		//加载字典数据
		//号牌种类
		$("#vehicle-recycle-list #vehicle-recycle-grid").datagrid({
			toolbar : "#vehicleRecyList-grid-toolbar",
			url : basePath+"/vehicleRecycle/list.do",
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
				field : "vehiclePlateTypeName",
				title : "号牌种类",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
				/* formatter : function (value, row, index) {
					if (row.vehiclePlateType) {
						return getDictNameByCode("VEHICLE_PLATE_TYPE", row.vehiclePlateType);
					} else {
						return "";
					}
				} */
			},{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "useOfPropertyName",
				title : "使用性质",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "inputTime",
				title : "录入时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.inputTime) {
						return getNowFormatDate(new Date(row.inputTime.time))
					} else {
						return "";
					}
				}
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:950,height:680,url:basePath+"/vehicleRecycle/view.do?id="+rowData.id+"&type=view",
						content:"报废车辆信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					{field:"vehiclePlateType",title:"号牌种类:",type:"combobox", url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					{field:"vehicleIdentifyNo",title:"车架号:",type:"text"},					
					{startField:"recycleStartDate",endField:"recycleEndDate",title:"交售时间:",type:"date",section:true},
					{startField:"inputStartTime",endField:"inputEndTime",title:"录入时间:",type:"date",section:true}
					],
			tools:[
				   {type:"UPDATE",title:"编辑",content:"报废车辆信息更新",width:1012,height:600,param:{
 					   reset:false},url:basePath+"/vehicleRecycle/view.do",
					   confim:function(c) {
				    		var selectedRows = $(this).datagrid("getSelections");
							if (selectedRows[0].status == "1") {
								Messager.alert({
									type : "info",
									title : "警告",
									content : "已受理的数据不能再进行编辑！"	
								});
								return false;
							} else {
								return true;
							}
						}
				   },
				   {type:"DELETE",title:"删除",url:basePath+"/vehicleRecycle/delete.do",param:{
					   reset:false},
					   confim:function(e) {
				    		var selectedRows = $(this).datagrid("getSelections");
							if (selectedRows[0].status == "1") {
								Messager.alert({
									type : "info",
									title : "警告",
									content : "已受理的数据不能再进行删除！"
								});
								return false;
							} else {
								return true;
							}
						}
				   },
				   {type:"QUERY"}],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true
		})

	})
	
	</script>
</body>
</html>