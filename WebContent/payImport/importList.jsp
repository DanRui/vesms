<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div id = "payImport-list" class="easyui-panel  easyui-panel-style" data-options="title: '国库文件导入标记',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="payImport-grid-toolbar">
				<table id="payImport-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="payImport-grid" style = "height:97.5%"></table>
	</div>


	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#payImport-list #payImport-grid").datagrid({
			toolbar : "#payImport-grid-toolbar",
			url : basePath+"/payImport/list.do",
			method : "post",
			rownumbers : true,
			sortName : "makeTime",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "importTime",
				title : "制表时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "makeTime",
				title : "导入时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "recordCountTotal",
				title : "记录数",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountValid",
				title : "有效业务单数",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountInvalid",
				title : "无效业务单数",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountPayOk",
				title : "正常支付单数",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountPayNotok",
				title : "退款单数",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountWaitforSign",
				title : "尚未标记的单数",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "remark",
				title : "备注",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
		/*	onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,
					url:basePath+"/payImport/batchView.do?id="+rowData.id+"&batchType="+rowData.batchType+"&type=view",
							content:"批次受理单明细",param:{close:false}});
			}*/
		}).datagrid("initSearch",{
			columns:[{startField:"makeStartTime",endField:"makeEndTime",title:"制表时间:",type:"date",section:true},
					 {startField:"importStartTime",endField:"importEndTime",title:"导入时间:",type:"date",section:true}
			        ],
			tools:[		
			       {type:"FILE_IMPORT",icon:"icon-add",title:"文件导入 ",text_width:100,
			     	  fn:function() {
						openDialog({
							   	type : "FILE_IMPORT",
								title : "文件导入",
								width : 500,
								height : 300,
								param: {reset:false,save:false,close:false},
								maximizable : true,
								href : basePath+"/payImport/toFileImport.do"
						   });
				 	  	}
				    },
					   {type:"QUERY"},
					   {type:"CLEAR"}
				  ],
			module:"M_MARK_IMPORT",
			shownum:3,
			debug:true
		})

		
	})
	</script>
</body>
</html>