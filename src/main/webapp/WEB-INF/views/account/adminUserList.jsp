<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>用户管理</title>
</head>

<body>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">
        <button data-dismiss="alert" class="close">×</button>
            ${message}</div>
</c:if>

<div class="clearfix">
    <div class="pull-left">
        <h3>用户列表</h3>
    </div>

    <div class="pull-right">
        <a class="btn btn-primary" style="margin-top: 13px;" href="${ctx}/admin/user/add">新增用户</a>
    </div>
</div>

<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>用户编号</th>
        <th>用户名</th>
        <th>所属</th>
        <th>注册时间</th>
        <th>更新时间</th>
        <th>更新管理者</th>
        <%--<th>状态</th>--%>
        <th style="width: 88px;">管理</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.loginName}</td>
            <td>${user.location.name}</td>
            <td>
                <fmt:formatDate value="${user.registerDate}" pattern="yyyy/MM/dd"/>
            </td>
            <td>
                <fmt:formatDate value="${user.updateDate}" pattern="yyyy/MM/dd"/>
            </td>
            <td>${user.updateUser.loginName}</td>
            <%--<td>${user.updateUser.status.text}</td>--%>
            <td>
                <a class="btn btn-mini" href="${ctx}/admin/user/update/${user.id}">修改</a>
                <a class="btn btn-mini" href="${ctx}/admin/user/delete/${user.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- 菜单定位 -->
<input type="hidden" name="menu-position" value="adminUser">

</body>
</html>
