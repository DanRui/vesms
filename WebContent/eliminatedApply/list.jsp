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
	<div id = "eliminated-apply-list" class="easyui-panel  easyui-panel-style" data-options="title: '申报未受理查询',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="eliminated-apply-grid-toolbar">
				<table id="eliminated-apply-tool-table" style = "width:100%;">
				</table>
		</div>
		<table id="eliminated-apply-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		
		$("#eliminated-apply-list #eliminated-apply-grid").datagrid({
			toolbar : "#eliminated-apply-grid-toolbar",
			url : basePath+"/eliminatedApply/list.do",
			method : "post",
			rownumbers : true,
			sortName : "id",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "applyConfirmTime",
				title : "受理状态",
				width : "7%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (row.applyConfirmTime) {
						return "color:green";
					} else {
						return "color:red";
					} 
				},
				formatter : function(value, row, index) {
					if (row.applyConfirmTime) {
						return "已受理";
					} else {
						return "未受理";
					}
				}
			},{
				field : "applyNo",
				title : "受理单号",
				width : "14%",
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
				$(this).datagrid("view",{width:1050,height:800,url:basePath+"/eliminatedApply/view.do?id="+rowData.id+"&type=view",
						content:"受理单详细查看",param:{close:false}});
			}
		}).datagrid("initSearch",{ columns: [
			         {field:"vehiclePlateNum",title:"号牌号码:",type:"text"},
					 {field:"vehiclePlateType",title:"号牌种类:",type:"combobox", panelHeight:false, url:basePath+"/sysDict/getDictListFromMap.do?dictType=VEHICLE_PLATE_TYPE", text:"value", value:"code"},
					 {field:"vehicleOwner",title:"车主:",type:"text"},
					 {field:"applyNo",title:"受理单号:",type:"text"},
					 {field:"vehicleIdentifyNo",title:"车架号:",type:"text"},
					 {startField:"startTime",endField:"endTime",title:"受理录入时间:",type:"date",section:true}
			        ],
			tools:[			       
			       //{type:"ADD",title:"新增录入",content:"补贴受理信息录入",text_width:100,width:1012,height:400,url:basePath+"/eliminatedApply/add.jsp",param:{reset:false}},				 
			       /* {type:"DELETE",color:"red",confim:function(d) {
			    	   return true;
			       }}, */
				   {type:"UPDATE",content:"补贴受理信息修改",width:1132,height:800,url:basePath+"/eliminatedApply/view.do",
			    	param:{
			    		reset : false
			    	}   
				   },			
				   //{type:"CLEAR",title:"重置",text_width:70},  
				   {type:"PRINT_APPLY",icon:"icon-print",title:"打印受理表",text_width:100,
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
								if (selectedRows[0].applyConfirmTime == null) {
									openDialog({
									   	type : "PRINT_APPLY_TABLE",
										title : "补贴受理表打印预览",
										width : 1040,
										height : 500,
										param: {reset:false,save:false/* ,
											buttons:[{
												id : "print_apply_table",
												text : "打印",
												iconCls : "icon-print",
												fn : function() {
													window.print();
												}
											}]	 */
										},	
										maximizable : true,
										href : basePath+"/eliminatedApply/applyPreview.do?id="+selectedRows[0].id
								   });
								} else {
									Messager.alert({
										type : "info",
										title : "警告",
										content : "已确认的受理表，无法重新打印受理表"
									});
								}
							}
					   }
				   },
				   {type:"CONFIRM",icon:"icon-ok",title:"受理单确认",text_width:100,
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
							}else {
								openDialog({
								   	type : "CONFIRM_APPLY",
									title : "受理表确认",
									width : 1032,
									height : 400,
									param: {reset:false,save:false/* ,
										buttons:[{
											id : "apply_table_confirm",
											text : "提交",
											iconCls : "icon-ok",
											fn : function() {
												//后台上传文件，保存数据。成功则继续处理，失败提示。
												alert("受理表上传成功，请打印受理回执单。");
												$("#common-dialog").dialog("close");
												Dialog.create("receipt_print_view", {
													type : "receipt_print",
													title : "受理单回执打印预览",
													width : 900,
													height : 600,
													param: {
															reset:false,
															buttons:[
																	{id:"receipt_print",text:"打印",iconCls:"icon-print",handler:function(){
																		window.print();
																	}},
																	{id:"receipt_print_cancel",text:"关闭",iconCls:"icon-cancel",handler:function(){
																		$("#receipt_print_view").dialog("close");
																	}}
																]
														},
													maximizable : true,
													href : basePath+"/eliminatedApply/receiptPrint.jsp"
												});
											}
										}] */
										},
									maximizable : true,
									href : basePath+"/eliminatedApply/confirmPreview.do?id="+selectedRows[0].id
							   });
							}
					   }
				   },
				   {type:"QUERY"},
				   ],
			module : "M_ELIMINATED_APPLY_NO_LIST",
			shownum:3/* ,
			debug:true */   
		})

		
	})

	</script>
</body>
</html>