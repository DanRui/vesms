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
	<div id = "apply-auth-list" class="easyui-panel  easyui-panel-style" data-options="title: '变更补贴对象业务授权',headerCls:'panel-title-center'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="apply-auth-grid-toolbar">
				<table id="apply-auth-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="apply-auth-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#apply-auth-list #apply-auth-grid").datagrid({
			toolbar : "#apply-auth-grid-toolbar",
			url : basePath+"/applySpecialAuthority/list.do",
			method : "post",
			rownumbers : true,
			nowrap: false,
			sortName : "askTime",
			sortOrder : "desc",
			columns : [ [ {
				field : "id",
				checkbox : true,
				width : "2%"
			},{
				field : "applyNo",
				title : "受理单号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "askUserName",
				title : "申请人",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "askTime",
				title : "申请时间",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.askTime) {
						return getNowFormatDate(new Date(row.askTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "checkStatus",
				title : "审核状态",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				styler : function(value, row, index) {
					if (value == "0") {
						return "color:red";
					} else if (value == "1") {
						return "color:green";
					} else if (value == "2") {
						return "color:gray";
					} 
				},
				formatter : function(value, row, index) {
					if (value == "0") {
						return "未审核";
					} else if (value == "1") {
						return "审核通过";
					} else if (value == "2") {
						return "审核不通过";
					}
				}
			},{
				field : "remark",
				title : "备注",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "checkUserName",
				title : "审核人",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "checkTime",
				title : "审核时间",
				width : "8%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.checkTime) {
						return getNowFormatDate(new Date(row.checkTime.time))
					} else {
						return "";
					}
				}
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				$(this).datagrid("view",{width:900,height:800,url:basePath+"/applySpecialAuthority/view.do?applyNo="+rowData.applyNo,
						content:"受理单详细信息查看",param:{close:false}});
			}
		}).datagrid("initSearch",{
			columns:[
					 {field:"applyNo",title:"受理单号:",type:"text"},
					 {startField:"askStartTime",endField:"askEndTime",title:"申请时间:",type:"date",section:true},
					 {startField:"checkStartTime",endField:"checkEndTime",title:"审核时间:",type:"date",section:true}
			        ],
			tools:[			       
				   {type:"APPLY", icon:"icon-edit", title:"新增授权申请", text_width:120,
			    		param:{
			    			reset : false
			    		}, fn : function() {
			    			
						openDialog({
						   	type : "apply_special_authority",
							title : "变更补贴对象业务授权申请查询",
							width : 1012,
							height : 550,
							param: {reset:false,save:false,close:false,
								buttons : [{
							           id:"apply-authority-confirm",
							           text:"提交授权申请", iconCls:"icon-save"}]
							},
							maximizable : true,
							href : basePath+"/applySpecialAuthority/listApplyView.do"
					   });
					}
				   },			
				   {type:"CLEAR",title:"重置",text_width:70},  
				   {type:"QUERY",title:"业务授权记录查询", text_width:130},
				   {type:"CHECK",title:"业务授权审核",text_width:120,icon:"icon-check-apply",fn:function() {
					   	var selectedRows = this.datagrid("getSelections");
						var infoMsg = null;
						infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
						//infoMsg = selectedRows.length < 1 ? "请至少选择一条记录" : null;
						if (null != infoMsg) {
							Messager.alert({
								type : "info",
								title : "&nbsp;",
								content : infoMsg
							});
						} else {
							if (selectedRows[0].checkStatus == "0") {
								openDialog({
									type : "check",
									title : "受理单业务授权审批",
									width : 600,
									height : 300,
									param: {reset:false, save:false,close:false,
											buttons : [{
											           id:"apply-special-check-submit",
											           text:"提交",iconCls:"icon-save"}
											          ]
										   },
									maximizable : true,
									href : basePath+"/applySpecialAuthority/applyAuthorityView.do?ids="+selectedRows[0].id
								});
							} else {
								Messager.alert({
									type : "info",
									title : "警告",
									content : "已审核过的业务授权，无法再次审核！"
								});
							}
							
						}
				   }}
				   ],
			module : 'M_ELIMINATED_MODIFY_AUTH',
			shownum : 3 ,
			debug : true   
		})
	})

	</script>
</body>
</html>