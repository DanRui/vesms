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
	<div>
		<iframe id="iframeRead" style="display:none;"/>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#payImport-list #payImport-grid").datagrid({
			toolbar : "#payImport-grid-toolbar",
			url : basePath+"/payImport/list.do",
			method : "post",
			rownumbers : true,
			sortName : "importTime",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "makeTime",
				title : "制表时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.makeTime) {
						return getNowFormatDate(new Date(row.makeTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "importTime",
				title : "导入时间",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.importTime) {
						return new Date(row.importTime.time).Format("yyyy-MM-dd hh:mm:ss");
					} else {
						return "";
					}
				}
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
				width : "6%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyCountWaitforSign",
				title : "尚未标记的单数",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "fileName",
				title : "文件名称",
				width : "25%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,
					url:basePath+"/payImport/importView.do?id="+rowData.id,
							content:"国库文件明细",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[{startField:"makeStartTime",endField:"makeEndTime",title:"制表时间:",type:"date",section:true},
					 {startField:"importStartTime",endField:"importEndTime",title:"导入时间:",type:"date",section:true}
			        ],
			tools:[		
			       {type:"FILE_IMPORT",icon:"icon-add",title:"文件导入标记",text_width:150,
			     	  fn:function() {
						openDialog({
							   	type : "FILE_IMPORT",
								title : "国库文件导入标记",
								width : 500,
								height : 300,
								param: {reset:false,save:false,close:false},
								maximizable : true,
								href : basePath+"/payImport/toFileImport.do"
						   });
				 	  	}
				    },
				    {type:"RESULT_MARK",title:"手工补充标记",text_width:150,icon:"icon-biaoji",
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
								openDialog({
								   	type : "file_mark",
									title : "文件手工标记",
									width : 1012,
									height : 550,
									param: {reset:false,save:false,close:false},
									maximizable : true,
									href : basePath+"/payImport/importMarkView.do?id="+selectedRows[0].id
							   });
							}
				    	}	   
				    },
				    {type:"TREASURY_QUERY",icon:"icon-add",title:"国库文件查看",text_width:150,
						  fn:function() {
								var selectedRows = this.datagrid("getSelections");
								//var ids=[];
								var infoMsg = null;
								infoMsg =selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
								if (null != infoMsg) {
									Messager.alert({
										type : "info",
										title : "&nbsp;",
										content : infoMsg
									});
								}else {
									//文件查看
									$.ajax({
										url:basePath+"/payImport/getImportPath.do",
										type:"POST",
										data:{id:selectedRows[0].id},
										contentType:"application/x-www-form-urlencoded; charset=UTF-8",
										success:function(data) {
											$("#iframeRead").attr("src", basePath+"/payImport/fileDownload.do?filepath="+encodeURI(encodeURI(data.message)));
											$("#iframeRead").show(); 
										}
									});
									/* $.post(basePath+"/payImport/getImportPath.do",{id:selectedRows[0].id
										},
										function(data) {
										$("#iframeRead").attr("src", basePath+"/payImport/fileDownload.do?filepath="+data.message);
										$("#iframeRead").show(); 
									}); */
							  	}
						}},
					   {type:"QUERY"},
					   {type:"CLEAR"}
				  ],
			module:"M_MARK_IMPORT",
			shownum:3
		})

		
	})
	</script>
</body>
</html>