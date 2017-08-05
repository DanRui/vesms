<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String basePath = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深圳市老旧车提前淘汰补贴管理系统</title>
<link rel="shortcut icon" href="<%=basePath%>/images/webSite/login/logo.png" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/login.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/encoder.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/login.js"></script>
</head>
<body>

	<!--头部内容-->
	<div class="header">
		<img src="images/logo.jpg" alt="logo" />
	</div>

	<!--登录内容-->
	<div class="container">
		<!--左边图片内容-->
		<div class="left">
			<img src="images/back_login.jpg" alt="banner图片" />
		</div>

		<!--右边登录内容-->
		<div class="right">
			<h1>用户登录</h1>
			<!--登录内容-->
			<div class="loginCon">
				<form action="login/login.do" method="post">
					<ul>
						<li>
							<span>用户名:</span>
							<input type="text" name="username" autocomplete="off" class = "user"/>
						</li>
						<li>
							<span>密码:</span>
							<input type="password" name="password" autocomplete="off" class = "pass"/>
						</li>
						<%-- <li>
							<span>验证码:</span>
							<input type="text" name="verifycode" autocomplete="off"  class="checkBox"/>
							<img src="<%=basePath %>/login/getCode.do" class="code">
							<!-- <img src="images/icon_code.jpg" alt="验证码" class="code"> -->
						</li> --%>
					</ul>
					<div class="button">
						<a href="javascript:void(0)" class="logining">登录</a>
						<a href="javascript:void(0)" class="look_for">找回密码</a>
					</div>
				</form>
			</div>
			<div class="login-error">
				<label style = "color:red;">${sessionScope['org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS'][0]['errormessage']}  </label>
			</div>
		</div>
	</div>
</body>
</html>