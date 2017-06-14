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
	<div id = "test-list" class="easyui-panel  easyui-panel-style" data-options="title: '测试'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="testList-grid-toolbar">
				<table id="test-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="test-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#test-list #test-grid").datagrid({
			toolbar : "#testList-grid-toolbar",
			url : basePath+"/test/list.do",
			method : "post",
			sortName : "id",
			sortOrder : "desc",
			pageList : [ 10, 100, 150, 200 ],
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			}, {
				field : "name",
				title : "名称",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}, {
				field : "scription",
				title : "脚本",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:800,content:"asd",height:450,url:basePath+"/test/view.do?id="+rowData.id});
			}
		}).datagrid("initSearch",{
			columns:[
				{field:"name",title:"姓名",type:"text"},
					{field:"scription",url:basePath+"/data/corp.json",text:"insname",value:"inscode",title:"脚本",type:"combobox"},
					{field:"scription",title:"脚本",type:"date"},
					{field:"scription",title:"脚本",type:"date"},
					{startField:"start",endField:"end",title:"培训开始时间",type:"date",section:true},
					{field:"name",title:"名称",type:"text"},
					{field:"name",title:"姓名",type:"text"},
					{field:"scription",url:basePath+"/data/corp.json",text:"insname",value:"inscode",title:"脚本",type:"combobox"},
					{field:"name",title:"姓名",type:"text"},
					{startField:"start",endField:"end",title:"培训开始时间",type:"date",section:true},
					{field:"scription",url:basePath+"/data/corp.json",text:"insname",value:"inscode",title:"脚本",type:"combobox"},
					{field:"name",title:"姓名",type:"text"},
					{field:"scription",url:basePath+"/data/corp.json",text:"insname",value:"inscode",title:"脚本",type:"combobox"},
					{field:"name",title:"姓名",type:"text"},
					{field:"scription",url:basePath+"/data/corp.json",text:"insname",value:"inscode",title:"脚本",type:"combobox"}],
			tools:[
				   {type:"ADD",text_wdith:100,content:"新增咯",title:"录入",width:2000,height:1200,url:basePath+"/test/add.jsp"},
				   {type:"UPDATE",width:600,height:400,param:{iconCls:"icon-check",text:"我晕",reset:false},url:basePath+"/test/view.do"},
				   {type:"DELETE",url:basePath+"/test/delete.do"},
				   {type:"CHECK",text_width:100,title:"审核标志",icon:"icon-check",fn:function(){
					   alert("这是自定义审核的事件");
				   }},
				   {type:"CLEAR"},
				   {type:"QUERY"}],
			module:"M_TEST_MANAGER",
			shownum:4,
			debug:true
		})
	})
	
	</script>
</body>
</html>