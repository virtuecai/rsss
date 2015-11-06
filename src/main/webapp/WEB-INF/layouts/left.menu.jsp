<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<div class="well sidebar-nav" style="min-height: 400px;">
	<ul class="nav nav-list">

		<li class="nav-header">个人</li>
		<li data-menu_position="profile" <%--class="active"--%>><a href="${ctx}/profile">个人信息管理</a></li>

		<shiro:hasRole name="admin">
		<li class="nav-header">用户</li>
		<li data-menu_position="adminUser"><a href="${ctx}/admin/user">用户管理</a></li>
		</shiro:hasRole>

		<li class="nav-header">商品</li>
		<li data-menu_position="productManager"><a href="#">商品管理</a></li>

		<li class="nav-header">任务</li>
		<li data-menu_position="taskManager"><a href="#">任务管理</a></li>

		<li class="nav-header">Api</li>
		<li data-menu_position="apiView"><a href="${ctx}/api">api地址</a></li>
	</ul>
</div>