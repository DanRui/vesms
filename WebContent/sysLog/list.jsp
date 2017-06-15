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
	<div id = "sys-log-list" class="easyui-panel easyui-panel-style" data-options="title: '日志查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="sysLogList-grid-toolbar">
				<table id="vehicle-recycle-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="sys-log-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#sys-log-list #sys-log-grid").datagrid({
			toolbar : "#sysLogList-grid-toolbar",
			url : basePath+"/data/sysLog.json",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "moduleName",
				title : "模块名称",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "operateName",
				title : "操作名称",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "operatePerson",
				title : "操作人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "operatePersonCode",
				title : "操作人账号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "operateIP",
				title : "操作IP",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "operateTime",
				title : "操作时间",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				 $(this).datagrid("view",{width:900,height:800,url:basePath+"/vehicleRecycle/view.jsp?id="+rowData.id,content:"报废车辆信息查看",param:{close:false}}); 
			}
		}).datagrid("initSearch",{
			columns:[{field:"operateIP",title:"操作IP:",type:"text"},
					{field:"moduleType",title:"操作模块:",type:"combobox", url:basePath+"/data/moduleType.json", text:"name", value:"value"},
					{field:"operateType",title:"操作名称:",type:"combobox", url:basePath+"/data/operateType.json", text:"name", value:"value"},
					{field:"operatePersonCode",title:"操作人账号:",type:"combobox", url:basePath+"/data/operatePersonCode.json", text:"name", value:"value"},
					{startField:"operateStartTime",endField:"operateEndTime",title:"操作时间:",type:"datetime",section:true},
					],
			tools:[
				   {type:"QUERY"},
				   {type:"DELETE",fn:function() {
					   	var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
						
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						} else {
							Messager.confirm({
								title : "提示",
								content : "确认删除当前选中的"+selectedRows.length + "条记录",
								handler : function(r) {
									if (r) {
										//调后台删除操作
										Messager.alert({
											type:"info",
											title :"提示",
											content: "删除成功"
										});
									}
								}
							});
							/* $.messager.confirm("提示", "确认删除当前选中的"+selectedRows.length + "条记录", function(r) {
								if (r) {
									Messager.alert({
										type:"info",
										title :"&nbsp",
										content: "删除成功"
									});
									}
								}); */
						}
				   }}
				  ],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true
		})

	})
	
	</script>
</body>
</html>