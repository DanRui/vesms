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
	<div id = "archiveMain-list" class="easyui-panel  easyui-panel-style" data-options="title: '档案管理',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="archiveMain-grid-toolbar">
				<table id="archiveMain-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="archiveMain-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#archiveMain-list #archiveMain-grid").datagrid({
			toolbar : "#archiveMain-grid-toolbar",
			url : basePath+"/archives/list.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "archiveBoxNo",
				title : "档案盒号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "documentNum",
				title : "文档数量",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "archiveDate",
				title : "档案生成年月",
				width : "8%",
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
			},{
				field : "filingDate",
				title : "档案立卷时间",
				width : "8%",
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
			},{
				field : "createUser",
				title : "档案生成人",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/archives/view.do?id="+rowData.id+"&type=view",content:"档案盒内业务查询",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"archiveBoxNo",title:"档案盒编号:",type:"text"},
					 {startField:"archiveStartDate",endField:"archiveEndDate",title:"档案年月:",type:"date",section:true}
			        ],
			tools:[			       
			       {type:"PRINT_ARCHIVES",icon:"icon-print",title:"打印档案盒号",text_width:120,
			     	  fn:function() {
						var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						infoMsg = selectedRows.length < 1 ? "至少选择一条记录" :null;
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						}else{
						//ajax请求，返回成功后再处理
						Messager.alert({
							type : "info",
							title : "提示;",
							content : "档案盒号已打印"
						})};
				 	  }
			       },
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