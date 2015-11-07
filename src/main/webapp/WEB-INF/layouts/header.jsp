<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="brand" href="#"><fmt:message key="project.name"/></a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					<i class="icon-user"></i> <shiro:principal property="name"/>  |
					<a href="${ctx}/logout" class="navbar-link"><fmt:message key="logout"/></a>
				</p>
				<%--<ul class="nav">--%>
				<%--<li class="active"><a href="#">Home</a></li>--%>
				<%--<li><a href="#about">About</a></li>--%>
				<%--<li><a href="#contact">Contact</a></li>--%>
				<%--</ul>--%>
			</div><!--/.nav-collapse -->
		</div>
	</div>
</div>
<%--
<div id="header">
	<div id="title">
	    <h1><a href="${ctx}"><fmt:message key="project.name"/></a><small></small>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/api">APIs</a></li>
					<li><a href="${ctx}/profile">Edit Profile</a></li>
					<li><a href="${ctx}/logout">Logout</a></li>
				</ul>
			</div>
		</shiro:user>
		</h1>
	</div>
</div>--%>
