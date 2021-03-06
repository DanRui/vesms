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
	<div id = "conclude-exce-list" class="easyui-panel easyui-panel-style" data-options="title: '异常办结',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="concludeExce-grid-toolbar">
				<table id="conclude-exce-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="conclude-exce-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#conclude-exce-list #conclude-exce-grid").datagrid({
			toolbar : "#concludeExce-grid-toolbar",
			url : basePath+"/conclude/faultList.do",
			method : "post",
			sortName : "lastUpdateTimeDate",
			sortOrder : "desc",
			rownumbers : true,
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "applyNo",
				title : "受理单号",
				width : "12%",
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
				field : "vehiclePlateTypeName",
				title : "号牌种类",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
			},{
				field : "vehicleTypeName",
				title : "车辆类型",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payResStatus",
				title : "拨付结果状态",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					if (value == "1") {
						return "标记成功";
					} else if (value == "2") {
						return "标记失败";
					}
				},
				styler : function(value, row, index) {
					if (value == "1") {
						return "color:green";
					} else if (value == "2") {
						return "color:red";
					}
				}
			},
			{
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleOwner",
				title : "车主",
				width : "15%",
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
				field : "applyConfirmTime",
				title : "受理时间",
				width : "10%",
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
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/eliminatedApply/view.do?id="+rowData.id+"&type=applyLog",content:"受理单查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					 {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					 {field:"vehicleType",title:"车辆类型:",type:"combobox",url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_TYPE", text:"value", value:"code"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"}
					 //{field:"batchCreateStatus",title:"批次生成状态:",type:"combobox",url:basePath+"/data/batchCreateStatus.json",text:"name", value:"value"}
			        ],
			tools:[
					{type:"CONCLUDE_MARK",icon:"icon-add",title:"办结",text_width:100,
						  fn:function() {
							var ids=[];
							var selectedRows = this.datagrid("getSelections");
							for (var i=0;i<selectedRows.length;i++){					
								ids.push(selectedRows[i].id);
							}
							var ids = ids.join(",")+"";
							//console.info(ids);
							var infoMsg = null;
							infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条" : null);
							if (null != infoMsg) {
								Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : infoMsg
								});
							}else{
							//ajax请求，返回成功后再处理
						 		$.post(basePath+"/conclude/faultConclude.do",{ids:ids,concludeStatus:"2"},function(data) {
						 			 Messager.alert({
										type : "info",
										title : "&nbsp;",
										content : data.message
									}); 
						 			$("#conclude-exce-list #conclude-exce-grid").datagrid('load');
						 		});
						 		}
							}
							
						  },
				   {type:"QUERY"},
				   {type:"CLEAR"}],
			module:"M_FAULT_APPLY_CONCLUDE",
			shownum:3
		})

	})
	
	</script>
</body>
</html>