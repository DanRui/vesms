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
	<div id = "repeatBatchCreate-list" class="easyui-panel  easyui-panel-style" data-options="title: '重报内部批次生成',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="repeatBatchCreate-grid-toolbar">
				<table id="repeatBatchCreate-recycle-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="repeatBatchCreate-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#repeatBatchCreate-list #repeatBatchCreate-grid").datagrid({
			toolbar : "#repeatBatchCreate-grid-toolbar",
			url : basePath+"/payApply/repList.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "repeatedBatchNo",
				title : "重报内部批次号",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "toFinanceStatus",
				title : "业务报财务状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function(value, row, index) {
					var num = parseInt (value);
						if (num < 0) {
							return "待报财务";
						} else if (num > "0") {
							return "已报财务";
						}
					},
				styler : function(value, row, index) {
					var num = parseInt (value);
					if (num < 0) {
						return "color:gray";
					} else if (num > 0) {
						return "color:red";
					} 
				}
			},{
				field : "applyNo",
				title : "受理单号",
				width : "15%",
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
				field : "batchNo",
				title : "原批次号",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "vehicleTypeName",
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
				field : "applyTime",
				title : "受理时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.expRecentDate) {
						return getNowFormatDate(new Date(row.expRecentDate.time))
					} else {
						return "";
					}
				}
			}
			/*,{
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
			/* {
				field : "batchCreateStatus",
				title : "批次生成状态",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "未生成") {
						return "color:red";
					} else if (value == "已生成") {
						return "color:green";
					}
				}
			} */
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/eliminatedApply/view.do?id="+rowData.id+"&type=applyLog",
					content:"受理单查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"},
					 {field:"toFinanceStatus",title:"业务报财务状态:",type:"combobox",url:basePath+"/data/toFinanceStatus.json",text:"name",value:"value"},
					 {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", url:basePath+"/data/vehiclePlateType.json", text:"name", value:"value"},
					 {field:"vehicleType",title:"车辆类型:",type:"combobox",url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_TYPE",text:"name", value:"value"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"}
					 
					 //{field:"batchCreateStatus",title:"批次生成状态:",type:"combobox",url:basePath+"/data/batchCreateStatus.json",text:"name", value:"value"}
			        ],
			tools:[			       
			       {type:"ADD_BATCH",icon:"icon-add",title:"生成重报批次",text_width:120,
			     	  fn:function() {
			     		var ids=[];
						var selectedRows = this.datagrid("getSelections");
						for (var i=0;i<selectedRows.length;i++){					
							ids.push(selectedRows[i].id);
						}
						var ids = ids.join(",")+"";
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
							$.get(basePath+"/payApply/repCreate.do",{ids:ids},function(data) {
					 			Messager.alert({
									type : "info",
									title : "&nbsp;",
									content : data.message
								});
					 			$("#repeatBatchCreate-list #repeatBatchCreate-grid").datagrid("load");
					 		});
							
				 	  	}
			     	  }
			    	   
			       },
			       {type:"QUERY"},
			       {type:"CLEAR"}
				   ],
			module:"M_TEST_MANAGER",
			shownum:3,
			debug:true   
		})

		
	})

	</script>
</body>
</html>