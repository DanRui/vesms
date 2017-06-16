<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>

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
	<%
		//登录信息
		Map<String, Object> loginInfo = new Hashtable<String, Object>();
		if(!SecurityUtils.getSubject().isAuthenticated()){
		//System.out.println("未登陆！"+basePath);
	%>
		<script type="text/javascript">
		
			//console.log("<%=basePath%>/login.jsp");
			window.location.href = "<%=basePath%>/login.jsp";
		</script>
	<%
		} else {
			loginInfo = (Hashtable<String, Object>)SecurityUtils.getSubject().getSession().getAttribute("LOGIN_INFO");
		}
	%>
	<div data-options="region:'north'" style="height:113px;">
		<div class="title">
			<div class="title-common-layer">
				<div class="title-info-box">
					<span>部门：<%=loginInfo.containsKey("DEPT_NAME") ? loginInfo.get("DEPT_NAME") : "" %></span>
					<span>用户：<%=loginInfo.containsKey("USER_NAME") ? loginInfo.get("USER_NAME") : "" %></span>
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
	
	<%
	List<Map<String, Object>> list = ((List<Map<String, Object>>)SecurityUtils.getSubject().getSession().getAttribute("WORKDATA"));
	if(list != null) {
	%>
	<shiro:authenticated>
	<div data-options="region:'center'" style="width:100%;">
		<div id="index-tab" class="easyui-tabs" data-options="border:false,fit:true">
			<div title="首页" style="background-color: #FFFFFF">
				<!-- <img src="images/welcome.jpg"> -->
				<div id="console" style="margin: 30px 20px;height: 400px;">
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">受理业务</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">受理未确认：<a href="javascript:void(0)" src="eliminatedApply/list.jsp" parent="补贴业务受理"><%=list.get(0).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待资料修正：<a href="javascript:void(0)" src="eliminatedApply/listAll.jsp" parent="业务综合查询"><%=list.get(0).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">会计初审</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">正常待审核：<a href="javascript:void(0)" src="eliminatedCheck/list.jsp?currentPost=KJCSG" parent="会计初审管理"><%=list.get(1).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">修正待重审核：<a href="javascript:void(0)" src="eliminatedCheck/backList.jsp?currentPost=KJCSG" parent="会计初审管理"><%=list.get(1).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">窗口审核</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">正常待审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(2).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">修正待重审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(2).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">科长审核</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">正常待审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(3).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">修正待重审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(3).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">处长审核</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">正常待审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(4).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">修正待重审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(4).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">会计复审</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">正常待审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(5).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">修正待重审核：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(5).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 8%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">拨付申报</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待首次报财务：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(6).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待重新报财务：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(6).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 10%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">拨付结果标记</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待首次拨付结果标记：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(7).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待重报拨付结果标记：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(7).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 10%; height: 200px;background:#f1f5f8;margin-right:5px;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">办结业务</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待正常办结：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(8).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">待终止办结：<a href="javascript:void(0)" src="order_initList.action" parent="预约管理"><%=list.get(8).get("COL3") %></a>条</p>
					</div>
					<div style="float: left; width: 7%; height: 200px;background:#ff0000;border-radius:5px 5px 0 0;">
						<p style="float: left; width: 100%; font-size: 16px;font-weight:bold;text-align:center;border-bottom:1px solid #66b5ff;">超期业务</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">即将超期总量：<a href="javascript:void(0)" src="eliminatedApply/listAll.jsp" parent="业务综合查询"><%=list.get(9).get("COL2") %></a>条</p>
						<p style="float: left; width: 100%; font-size: 14px;text-indent: 20px;">已经超期数量：<a href="javascript:void(0)" src="eliminatedApply/listAll.jsp" parent="业务综合查询"><%=list.get(9).get("COL3") %></a>条</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	</shiro:authenticated>
	<%
	}
	%>
	
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
<script type="text/javascript">
$(function(){
	$("#console a").click(function(e) {
		e.preventDefault();
		
		var which = $(this).attr("parent"); 
		var src = $(this).attr("src");
		var parent = $("#index-accordion").accordion("getPanel", which);
		parent.find("a").each(function() {
			if($(this).data("menuUrl") == src) {
				$(this).click();
			}
		});
	});
	
	
});
</script>
</html>