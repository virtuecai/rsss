<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page import="com.xyxy.platform.service.ServiceException" %>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(200);
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
%>

<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误 </title>
</head>

<body>
	<div class="alert alert-error controls text-center">
		<h3>500 - 系统发生内部错误. <%= exception.getMessage()%></h3>
		<input id="cancel_btn" class="btn btn-danger" type="button" value="返回" onclick="history.back()"/>
	</div>
</body>
</html>
