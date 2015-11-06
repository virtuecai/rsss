<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
	<title>登录页</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />

	<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
	<link href="${ctx}/static/js/lib/bootstrap/2.3.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/js/lib/bootstrap/2.3.2/css/bootstrap-responsive.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/js/lib/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/css/default.css" type="text/css" rel="stylesheet" />

</head>

<body>
<style>
	body {
		padding-top: 40px;
		padding-bottom: 40px;
		background-color: #f5f5f5;
	}

	.form-signin {
		max-width: 300px;
		padding: 19px 29px 29px;
		margin: 0 auto 20px;
		background-color: #fff;
		border: 1px solid #e5e5e5;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		-moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
		box-shadow: 0 1px 2px rgba(0,0,0,.05);
	}
	.form-signin .form-signin-heading,
	.form-signin .checkbox {
		margin-bottom: 10px;
	}
	.form-signin input[type="text"],
	.form-signin input[type="password"] {
		font-size: 16px;
		height: auto;
		margin-bottom: 15px;
		padding: 7px 9px;
	}
</style>

<div class="container" id="loginPage">
	<%
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
	%>
	<div class="alert alert-error controls text-center">
		<button class="close" data-dismiss="alert">×</button>登录信息有误，请重试.
	</div>
	<%
		}
	%>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post" style="<shiro:user>display: none;</shiro:user>">
		<img class="logo" src="${ctx}/static/images/logo.png" alt=""/>
		<input class="input-block-level" placeholder="请输入用户名"  type="text" id="username" name="username"  value="${username}" required minlength="1" maxlength="20">
		<input class="input-block-level" placeholder="请输入密码"  type="password" id="password" name="password" required minlength="1" maxlength="20">
		<label class="checkbox">
			<input type="checkbox" value="remember-me"  id="rememberMe" name="rememberMe"> 记住我
		</label>

		<button class="btn btn-large btn-primary" type="submit">登录</button>
		<hr/>
		<span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>
	</form>

	<shiro:user>
	<form class="form-signin text-center" style="margin-top: 40px;">
		<a class="btn btn-large btn-success" href="${ctx}/welcome" style="margin-top: 10px;">已登录, 点击进入系统</a>
	</form>
	</shiro:user>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>

</div>

<script src="${ctx}/static/js/lib/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/lib/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/lib/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/js/lib/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
<script>
	$(document).ready(function() {
		$("#loginForm").validate();
	});
</script>
</body>
</html>
