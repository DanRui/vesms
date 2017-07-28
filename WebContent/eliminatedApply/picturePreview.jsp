<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String basePath = request.getContextPath();
	String fileType = request.getParameter("filetype");
	String index = request.getParameter("index");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=5"> -->
        <title>证明材料预览</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/picturePreview.css">
        <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.0.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/script.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/jquery.zoombie.js"></script>
   </head>
<body>
<div id="show_box" class="show_box">
	<div id="title_nav" class="title_nav">
	</div>
</div>
<script type="text/javascript">
	$(function() {
		// 定义函数表达式
		var setPicturePreview = function(filepath, type, name) {
			var basePath = "<%=basePath%>";
			var currentType = "<%=fileType%>";
			
			if (null == filepath || filepath == "") {
				return;
			}
			
			var filesArray = filepath.split(",");
			
			var div_show_box = $("DIV#show_box");
			var ul = div_show_box.find("ul.pic_list");
			if (ul.length <= 0) {
				ul = $('<ul class="pic_list"></ul>');
			}
			
			for (var i = 0 ; i < filesArray.length ; i ++) {
				// 循环增加li到ul
				var imgUrl = basePath + "/" + filesArray[i];
				//alert(imgUrl);
				var imgId = "img_" + (i+1);
				var li = '<li style="display:block"><span style="display:inline-block;width:800px;height:500px;" id="'+ imgId +'"><img style="display:block;width:100%;height:100%;" alt="'+ name + '" src="'+imgUrl +'"/></span></li>';
				ul.append(li);
				
				// 循环增加证明材料名称
				var _a_class = "";
				if (currentType == type) {
					_a_class = 'class="select"';
				}
				var _a_name = '<a id="'+ type + (i+1) +'" href="#"' + _a_class + '><em>'+name+'</em><em>'+(i+1) +'</em></a>';
				$("#title_nav").append(_a_name);
				
			}
			
			if (div_show_box.find("ul.pic_list").length <= 0) {
				div_show_box.append(ul);
			}
		};
		
		<%-- var picturePreview = function(filepath, countFiles) {
			var basePath = "<%=basePath%>";
			var currentType = "<%=fileType%>";
			// 根据所有的证明材料和各类材料数量，构造出预览的结构
			if (filepath == null || filepath == "" || filepath.length <= 0) {
				return;
			}
			
			var filesArray = filepath.split(",");
			
			var div_show_box = $("DIV#show_box");
			var ul = $('<ul class="pic_list"></ul>');
			
			for (var i = 0 ; i < filesArray.length ; i ++) {
				// 循环增加li到ul
				var imgUrl = basePath + "/" + filesArray[i];
				var li = '<li style="display:block"><a href="#" title=""><img alt="'+ name + '" src="'+imgUrl +'"/></a></li>';
				ul.append(li);
				
				// 循环增加证明材料名称
				var _a_class = "";
				var _name = "";
				if (currentType in countFiles) {
					_a_class = 'class="select"';
				}
				
				if (countFiles.callbackProof != 0 && i) {
					
				}
				
				var _a_name = '<a href="#"' + _a_class + '><em>'+name+'</em><em>'+(i+1) +'</em></a>';
				$("#title_nav").append(_a_name);
				
			}
			div_show_box.append(ul);
		}; --%>
		
		// 根据父页面传递来的参数设置路径
		// 统计每类证明材料的数量
		//var countFiles = new Object();
		//var filepath;
		// 报废回收证明
		var callbackProofFile = window.opener.callbackProofFile;
		/* if (null != callbackProofFile && callbackProofFile != "") {
			countFiles.callbackProof = callbackProofFile.split(",").length;
		} */
		
		setPicturePreview(callbackProofFile, 'callbackProof', '报废回收证明');
		
		// 机动车注销证明
		var vehicleCancelProofFiles = window.opener.vehicleCancelProofFiles;
		/* if (null != vehicleCancelProofFiles && vehicleCancelProofFiles != "") {
			countFiles.vehicleCancelProof = vehicleCancelProofFiles.split(",").length;
		} */
		setPicturePreview(vehicleCancelProofFiles, 'vehicleCancelProof', '机动车注销证明');
		
		// 银行卡
		var bankCardFiles = window.opener.bankCardFiles;
		/* if (null != bankCardFiles && bankCardFiles != "") {
			countFiles.bankCard = bankCardFiles.split(",").length;
		} */
		setPicturePreview(bankCardFiles, 'bankCard', '银行卡');
		
		// 车主身份证明
		var vehicleOwnerProofFiles = window.opener.vehicleOwnerProofFiles;
		/* if (null != vehicleOwnerProofFiles && vehicleOwnerProofFiles != "") {
			countFiles.vehicleOwnerProof = vehicleOwnerProofFiles.split(",").length;
		} */
		setPicturePreview(vehicleOwnerProofFiles, 'vehicleOwnerProof', '车主身份证明');
		
		// 代理委托书
		var agentProxyFiles = window.opener.agentProxyFiles;
		/* if (null != agentProxyFiles && agentProxyFiles != "") {
			countFiles.agentProxy = agentProxyFiles.split(",").length;
		} */
		setPicturePreview(agentProxyFiles, 'agentProxy', '代理委托书');
		
		// 代理人身份证明
		var agentProofFiles = window.opener.agentProofFiles;
		/* if (null != agentProofFiles && agentProofFiles != "") {
			countFiles.agentProof = agentProofFiles.split(",").length;
		} */
		setPicturePreview(agentProofFiles, 'agentProof', '代理人身份证明');
		
		// 开户许可证
		var openAccPromitFiles = window.opener.openAccPromitFiles;
		/* if (null != openAccPromitFiles && openAccPromitFiles != "") {
			countFiles.openAccPromit = openAccPromitFiles.split(",").length;
		} */
		setPicturePreview(openAccPromitFiles, 'openAccPromit', '开户许可证');
		
		// 非财政供养单位证明
		var noFinanceProvideFiles = window.opener.noFinanceProvideFiles;
		/* if (null != noFinanceProvideFiles && noFinanceProvideFiles != "") {
			countFiles.noFinanceProvide = noFinanceProvideFiles.split(",").length;
		} */
		setPicturePreview(noFinanceProvideFiles, 'noFinanceProvide', '非财政供养单位证明');
		
		// 签字确认的受理表
		var signedApplyFiles = window.opener.signedApplyFiles;
		/* if (null != noFinanceProvideFiles && noFinanceProvideFiles != "") {
			countFiles.noFinanceProvide = noFinanceProvideFiles.split(",").length;
		} */
		setPicturePreview(signedApplyFiles, 'signedApply', '签字确认的受理表');

		// 补贴对象变更证明材料
		var accountChangeProofFiles = window.opener.accountChangeProofFiles;
		/* if (null != noFinanceProvideFiles && noFinanceProvideFiles != "") {
			countFiles.noFinanceProvide = noFinanceProvideFiles.split(",").length;
		} */
		setPicturePreview(accountChangeProofFiles, 'accountChangeProof', '补贴对象变更证明材料');
		
		/* filepath = callbackProofFile + "," + vehicleCancelProofFiles + "," + bankCardFiles + "," + vehicleOwnerProofFiles + "," 
				 + agentProxyFiles + "," + agentProofFiles + "," + openAccPromitFiles + "," + noFinanceProvideFiles; */
		
		//picturePreview(filepath, countFiles);
		
		$(".show_box").turnPic();
		
		$("span[id^='img_']").each(function() {
			$(this).zoombie();
		});
		
		// 根据前一个页面的参数，选择相关的证明材料展示
		var currentType = "<%=fileType%>";
		var index = "<%=index%>";
		$("#"+ currentType + index).click();
	});
</script>
</body>
</html>