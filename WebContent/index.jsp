<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String basePath = request.getContextPath();
%>
    
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深圳市老旧机动车提前淘汰补贴系统</title>
<link rel="shortcut icon" href="<%=basePath%>/images/logo_small.png" type="image/x-icon"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/index.css">

<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/easyui/themes/extension.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/plugins/date/skin/WdatePicker.css">
<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/themes/default/default.css" />
<link rel="stylesheet" href="<%=basePath%>/js/plugins/editor/plugins/code/prettify.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/table.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/take_photo.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/printPreview.css">
 
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/jquery.easyui.extension.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/date/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/kindeditor.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/lang/zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/plugins/editor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/main.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/encoder.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/dictParse.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/util/printUtil.js"></script>

<style media="print">
	.Noprint{display:none;}
	.PageNext{page-break-after: always;}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:113px;">
		<div class="title">
			<div class="title-common-layer">
				<div class="title-info-box">
					<span>所属部门：警视通</span>
					<span>用户名称：admin</span>
				</div>
			</div>

			<div class="title-common-layer">
				<div class="title-time-box">
					<span>
						<input id="styleBox" class="easyui-combobox">
					</span>
					<span></span>
				</div>
		
				<div class="title-btn-box">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" style="margin-top: 1px; width: 80px;" id="index-update">修改密码</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-logout'" style="margin-top: 1px; width: 60px;" id="index-logout">注销</a>
				</div>
			</div>
		</div>
	</div>
		
	<div title="功能菜单" data-options="region:'west',split:true" style="width:245px;">
		<div id="index-accordion" class="easyui-accordion" data-options="border:false,fit:false,animate:false"></div>
	</div>
	
	<div data-options="region:'center'" style="width:100%;">
		<div id="index-tab" class="easyui-tabs" data-options="border:false,fit:true">
			<div title="首页">
				<!--企业信息状态-->
			</div>
		</div>
	</div>
	
	<div id="index-menu" class="easyui-menu" data-options="onShow:menuOnShowHandler" style="width:160px;">
		<div id="index-menu-closeSelf">关闭标签页</div>
		<div id="index-menu-closeOthers">关闭其他标签页</div>
		<div id="index-menu-closeLeft">关闭左侧标签页</div>
		<div id="index-menu-closeRight">关闭右侧标签页</div>
		<div id="index-menu-closeAll">关闭所有标签页</div>
	</div>
	
	<div id="common-dialog" class="easyui-dialog"></div>
	<div id="common-dialog-buttons" style="text-align: center;">
		<a href="javascript:void(0)" id="common-dialog-save" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a href="javascript:void(0)" id="common-dialog-reset" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
		<a href="javascript:void(0)" id="common-dialog-close" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
		<!-- <a href="javascript:void(0)" id="common-dialog-print" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印</a> -->
	</div>
	
	<div class = "shrink-panel"  o = "false" id = "open-div">
		<div class = "open-button" >
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-open'" style="margin-top: 1px; width: 80px;" id="index-open-all">全屏</a>
		<%-- 
		<img width = "20" title = "全频" alt="全频" src="<%=basePath %>/skin/images/scale.png"/> --%></div>
	</div>
</body>
</html>