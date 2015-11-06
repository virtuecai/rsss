<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<ul class="nav nav-list bs-docs-sidenav affix">
    <li data-menu_position="profile" class="active">
        <a href="${ctx}/profile"><i class="icon-chevron-right"></i> 个人信息管理</a>
    </li>
    <shiro:hasRole name="admin">
        <li data-menu_position="adminUser" class="active">
            <a href="${ctx}/admin/user"><i class="icon-chevron-right"></i> 用户管理</a>
        </li>
    </shiro:hasRole>
    <li data-menu_position="product" class="active">
        <a href="${ctx}/product"><i class="icon-chevron-right"></i> 商品管理</a>
    </li>
    <li data-menu_position="task" class="active">
        <a href="${ctx}/task"><i class="icon-chevron-right"></i> 任务管理</a>
    </li>
    <li data-menu_position="api" class="active">
        <a href="${ctx}/api"><i class="icon-chevron-right"></i> api地址</a>
    </li>
</ul>