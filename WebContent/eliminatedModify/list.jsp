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
	<div id = "editData-apply-list" class="easyui-panel  easyui-panel-style" data-options="title: '一般资料修正',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="editData-apply-grid-toolbar">
				<table id="editData-apply-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="editData-apply-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#editData-apply-list #editData-apply-grid").datagrid({
			toolbar : "#editData-apply-grid-toolbar",
			url : basePath+"/eliminatedModify/list.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "bussinessStatus",
				title : "业务状态",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "-1") {
						return "color:red";
					} else if (value == "1") {
						return "color:green";
					} 
				},
				formatter : function(value, row, index) {
					if (value == "-1") {
						return "异常";
					} else if (value == "1") {
						return "正常";
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
				field : "vehicleOwner",
				title : "车主",
				width : "15%",
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
				field : "vehicleIdentifyNo",
				title : "车架号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "cancelReason",
				title : "注销类别",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "emissionStandard",
				title : "排放标准",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
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
				width : "15%",
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
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/eliminatedModify/view.do?id="+rowData.id
						+"&type=common",
						content:"受理单修正信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
			         {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
			         {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", panelHeight:false, url:basePath+"/sysDict/getDictListByType.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"},	
					 /* {field:"cancelReason",title:"注销类别:",type:"combobox", url:basePath+"/data/logoutType.json", text:"name", value:"value"}, */	
					 /* {field:"emissionStandard",title:"排放标准:",type:"combobox", url:basePath+"/data/dischargeSta.json", text:"name", value:"value"}, */	
					 /* {field:"modifyType",title:"修正类型:",type:"combobox",url:basePath+"/data/modifyType.json",text:"name", value:"value"}, */
					 {startField:"startTime",endField:"endTime",title:"受理时间:",type:"date",section:true}
			        ],
			tools:[			       
				   { type:"MODIFY", icon:"icon-edit", title:"资料修改", content:"", text_width:120,
			    		param:{
			    			reset : false
			    		}, fn : function() {
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
								   	type : "modify_apply",
									title : "受理单资料修正",
									width : 900,
									height : 600,
									param: {reset:false, save:false,
										buttons:[{
											id : "choose_modify_type",
											text : "提交",
											iconCls : "icon-ok"/* ,
											fn : function() {
												//后台获取选择的修正类型，传到修正页面进行处理。
												Dialog.create("modify_apply", {
													type : "modify_apply",
													title : "受理单资料修正",
													width : 900,
													height : 600,
													param: {
															buttons:[
																	{id:"modify_apply_save",text:"提交",iconCls:"icon-save",handler:function(){
																		alert("资料修改成功");
																		$("#modify_apply").dialog("close");
																		$("#common-dialog").dialog("close");
																	}},
																	{id:"modify_apply_cancel",text:"关闭",iconCls:"icon-cancel",handler:function(){
																		$("#modify_apply").dialog("close");
																	}}
																]
														},
													maximizable : true,
													href : basePath+"/eliminatedModify/modifyApply.jsp"
												});
											} */
										}]
										},
									maximizable : true,
									href : basePath+"/eliminatedModify/chooseModifyType.do?id="+selectedRows[0].id
											+"&faultType=1,2"
							   });
							}
			    		}  
				   },			
				   //{type:"CLEAR",title:"重置",text_width:70},  
				   {type:"QUERY"}
				 
				   ],
			module : 'M_ELIMINATED_MODIFY_NOR',
			shownum : 3 ,
			debug : false   
		})
	})

	</script>
</body>
</html>