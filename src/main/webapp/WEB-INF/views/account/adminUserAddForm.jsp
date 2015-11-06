<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>新增用户</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/admin/user/add" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>新增用户</small></legend>
			<div class="control-group">
				<label class="control-label">用户名:</label>
				<div class="controls">
					<input type="text" id="loginName" name="loginName" class="input-large required" placeholder="请输入用户名" minlength="4" maxlength="20"/>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword" class="input-large" placeholder="请输入密码" minlength="4" maxlength="20"/>
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">确认密码:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword" class="input-large" equalTo="#plainPassword" placeholder="请确认密码"/>
				</div>
			</div>
			<div class="control-group">
				<label for="location.id" class="control-label">所属:</label>
				<div class="controls">
					<select id="location.id" name="location.id">
					<c:forEach items="${locationList}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">是否是管理员:</label>
				<div class="controls">
					<label class="radio inline">
						<input type="radio" name="roles" value="admin"> 是
					</label>
					<label class="radio inline">
						<input type="radio" name="roles" value="user" checked> 否
					</label>
				</div>
			</div>
			<%--<div class="control-group">
				<label class="control-label">禁用:</label>
				<div class="controls">
					<label class="checkbox inline">
						<input type="checkbox" name="roles" value="admin"> 是
					</label>
					<label class="checkbox inline">
						<input type="checkbox" name="roles" value="user"> 否
					</label>
				</div>
			</div>--%>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
