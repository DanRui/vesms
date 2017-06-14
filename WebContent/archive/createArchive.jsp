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
	<div id = "archiveCreate-list" class="easyui-panel  easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="archiveCreate-grid-toolbar">
				<table id="archiveCreate-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="archiveCreate-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#archiveCreate-list #archiveCreate-grid").datagrid({
			toolbar : "#archiveCreate-grid-toolbar",
			url : basePath+"/data/archiveApply.json",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "batchNo",
				title : "批次号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyNo",
				title : "受理单号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehiclePlateNum",
				title : "号牌号码",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehiclePlateType",
				title : "号牌种类",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "vehicleType",
				title : "车辆类型",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},
			{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "subsidiesMoney",
				title : "补贴金额（元）",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true 
			},{
				field : "payBatchResStatus",
				title : "拨付结果",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true ,
				styler : function(value, row, index) {
					if (value == "拨付成功") {
						return "color:green";
					} else if (value == "拨付失败") {
						return "color:red";
					} else if (value == "财委退回") {
						return "color:blue";
					}
				}
			},{
				field : "applyTime",
				title : "受理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}/*,{
				field : "applyPerson",
				title : "受理人",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "applyPersonDept",
				title : "部门",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}*/
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:800,height:450,url:basePath+"/eliminatedApply/view.jsp?id="+rowData.id,content:"受理单查看"});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					 {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", url:basePath+"/data/vehiclePlateType.json", text:"name", value:"value"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"},
					 {field:"batchNo",title:"批次号:",type:"text"},
					 {field:"payBatchResStatus",title:"拨付结果:",type:"combobox", url:basePath+"/data/payResStatus.json", text:"name", value:"value"}
			        ],
			tools:[			       
			       {type:"ADD_ARCHIVE_BOX",icon:"icon-add",title:"生成档案盒号",text_width:120,
			     	  fn:function() {
						var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						infoMsg = selectedRows.length < 1 ? "至少选择一条记录" :  null;
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
							content : "档案盒号 已生成"
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