<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
	<div id = "import-mark-list" class="easyui-panel easyui-panel-style" data-options="title: '查询列表'" style="height:100%">
		<input type = "hidden" id = "basePath" value = "<%=basePath %>"/>
			<div class="datagrid-header" id="importMarkList-grid-toolbar">
				<table id="import-mark-tool-table" style = "width:100%;">
				</table>
			</div>
		<table id="import-mark-grid" style = "height:97.5%"></table>
	</div>

	<script type="text/javascript">
	$(function(){
		var basePath = $("#basePath").val();
		$("#import-mark-list #import-mark-grid").datagrid({
			toolbar : "#importMarkList-grid-toolbar",
			url : basePath+"/importDetail/importMarkList.do?payImportId="+'${v.id }',
			method : "post",
			sortName : "id",
			rownumbers : true,
			columns : [[{
				field : "id",
				width : "2%",
				checkbox : true
			},{
				field : "applyNo",
				title : "业务单号",
				width : "15%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "accountName",
				title : "开户户名",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "bankName",
				title : "开户银行",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payAmount",
				title : "支付金额",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payResult",
				title : "支付状态",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true 
			},{
				field : "payResStatus",
				title : "支付结果标记状态",
				width : "12%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true ,
				formatter : function(value, row, index) {
					if (value =="-1") {
						return "无效";
					}else if (value =="0") {
						return "正常待手工标记";
					}else if (value =="1") {
						return "拨付成功";
					}else if (value =="2") {
						return "拨付不成功";
					}else if (value =="3") {
						return "退款待手工标记";
					}
				},
				styler : function(value, row, index) {
					if (value =="-1") {
						return "color:gray";
					}else if (value =="0") {
						return "color:red";
					}else if (value =="1") {
						return "color:green";
					}else if (value =="2") {
						return "color:red";
					}else if (value =="3") {
						return "color:red";
					}
				}
			},{
				field : "remark",
				title : "说明",
				width : "20%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			},{
				field : "payTime",
				title : "付款时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.payTime) {
						return getNowFormatDate(new Date(row.payTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "confirmTime",
				title : "清算时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.confirmTime) {
						return getNowFormatDate(new Date(row.confirmTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "updateTime",
				title : "更新时间",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true,
				formatter : function (value, row, index) {
					if (row.updateTime) {
						return getNowFormatDate(new Date(row.updateTime.time))
					} else {
						return "";
					}
				}
			},{
				field : "applyId",
				title : "业务id",
				width : "10%",
				align : "center",
				halign : "center",
				resizable : true,
				sortable : true
			}
			] ],
			onDblClickRow : function(rowIndex, rowData) {
				Dialog.create("pay_import_mark", {
					type : "RESULT_MARK",
					title : "拨付结果标记",
					width : 880,
					height : 800,
					param: {
							reset:false
					},
			maximizable : true,
			href :basePath+"/eliminatedApply/view.do?id="+rowData.applyId+"&type=applyLog"
				});
			}
		}).datagrid("initSearch",{
			columns:[
					{field:"payResStatus",title:"支付结果标记状态:",type:"combobox",panelHeight:true,url:basePath+"/data/payResStatus.json", text:"name", value:"value"},
					{field:"payResult",title:"支付结果:",type:"combobox",panelHeight:true,url:basePath+"/data/payResult.json", text:"name", value:"value"},
					{field:"accountName",title:"开户户名:",type:"text"},
					{field:"applyNo",title:"业务单号:",type:"text"},
					{startField:"payStartTime",endField:"payEndTime",title:"付款时间:",type:"date",section:true}
					],
			tools:[
		 {type:"RESULT_MARK",title:"拨付结果标记",text_width:150,icon:"icon-biaoji",fn:function(){
			var selectedRows = this.datagrid("getSelections");
			var infoMsg = null;
			var ids=[];
			for (var i=0;i<selectedRows.length;i++){	
				if(selectedRows[i].payResStatus=="0"){
					infoMsg = selectedRows.length < 1 ? "至少选择一条记录" :  null;
					break;
				}else if(selectedRows[i].payResStatus=="3"){
					infoMsg = selectedRows.length < 1 ? "请选择一条记录" : (selectedRows.length > 1 ? "最多只能选择一条记录" : null);
					break;
				}
			}
			var sta1=0;
			var sta2=0;
			for (var i=0;i<selectedRows.length;i++){					
				if(selectedRows[i].payResStatus=="0"){
					sta1++;
				}else if(selectedRows[i].payResStatus=="3"){
					sta2++;
				}
			}
			if(sta1>0 && sta2>0){
				infoMsg="你选择的数据中存在两种状态,请先进行拨付结果状态筛选";
			}
			
			for (var i=0;i<selectedRows.length;i++){					
				ids.push(selectedRows[i].applyId);
			}
			var ids = ids.join(",")+"";
			
			if (null != infoMsg) {
				Messager.alert({
					type : "info",
					title : "&nbsp;",
					content : infoMsg
				});
			}else{
 				if("0"!=selectedRows[0].payResStatus && "3"!=selectedRows[0].payResStatus){
					Messager.alert({
						type : "error",
						title : "提示",
						content : "选择的数据中存在不属于 待手工标记   的业务数据 ,请联系管理员"
					});
				}else{
					var checkType = null;
					if(selectedRows[0].payResStatus=="0"){
						checkType="1";
					}else if(selectedRows[0].payResStatus=="3"){
						checkType="2";
					}
					
					if(checkType=="2"){
						Dialog.create("import_res_mark", {
							type : "RESULT_MARK",
							title : "拨付结果标记",
							width : 700,
							height : 300,
							param: {
								reset:false,
								buttons:[
									{id:"pay_res_mark_save",text:"保存",iconCls:"icon-save",handler:function(){
									//	var checkType = $("#resultType").combobox('getValue');
										var remark = $("#resultOpinion").val();
										var fauType =$('#fauType').val();
										
										/* if (checkType == "" || (checkType != "1" && checkType != "2")) {
											alert("请选择审批操作！");
											return false;
										} */
										
										if (checkType == "2") {
											if (fauType == "" || (fauType != "1" && fauType != "2" && fauType != "3")) {
												alert("请选择退回类型！");
												return false;
											}
											
											if (remark == "") {
												alert("请填写具体原因！");
												return false;
											}
										}
										
										var ifValid = $("form #pay-res-check-form").form("enableValidation").form("validate");
										if (ifValid) {
											$.post(basePath+"/payResult/payMark.do",{ids:ids,payResStatus:checkType,faultType:fauType,faultDesc:remark},function(data) {
									 			Messager.alert({
													type : "info",
													title : "&nbsp;",
													content : data.message
												});
									 		});
											$("#import_res_mark").dialog("close");
											$("#import-mark-list #import-mark-grid").datagrid('load');
											$("#payImport-list #payImport-grid").datagrid('load');
										}
									}},
									]
								},
							maximizable : true,
							href : basePath+"/payImport/payResView.do"
						});
					}else if(checkType=="1"){
						Dialog.create("import_nor_mark", {
							type : "RESULT_MARK",
							title : "拨付结果标记",
							width : 700,
							height : 300,
							param: {
								reset:false,
								buttons:[
									{id:"pay_res_mark_save",text:"保存",iconCls:"icon-save",handler:function(){
									//	var checkType = $("#resultType").combobox('getValue');
										var remark = $("#resultOpinion").val();
										var fauType =$('#fauType').val();
										var ifValid = $("form #pay-res-check-form").form("enableValidation").form("validate");
										if (ifValid) {
											$.post(basePath+"/payResult/payMark.do",{ids:ids,payResStatus:checkType,faultType:fauType,faultDesc:remark},function(data) {
									 			Messager.alert({
													type : "info",
													title : "&nbsp;",
													content : data.message
												});
									 		});
											$("#import_nor_mark").dialog("close");
											$("#import-mark-list #import-mark-grid").datagrid('load');
											$("#payImport-list #payImport-grid").datagrid('load');
										}
									}},
									]
								},
							maximizable : true,
							href : basePath+"/payImport/payNorView.do"
						});
					}
			}
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