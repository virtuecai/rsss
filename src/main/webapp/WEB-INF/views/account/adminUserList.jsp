<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
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
        <th><fmt:message key="user.id"/></th>
        <th><fmt:message key="user.loginName"/></th>
        <th><fmt:message key="user.location"/></th>
        <th><fmt:message key="user.registerDate"/></th>
        <th><fmt:message key="user.updateDate"/></th>
        <th><fmt:message key="user.updateAdmin"/></th>
        <th><fmt:message key="user.status"/></th>
        <th style="width: 88px;"><fmt:message key="user.list.table.thead.opreation"/></th>
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
            <td>
                <c:choose>
                    <c:when test="${zhangfn:status(user) == 0}"><fmt:message key="user.online"/></c:when>
                    <c:when test="${zhangfn:status(user) == 1}"><fmt:message key="user.offline"/></c:when>
                    <c:otherwise><fmt:message key="user.disabled"/></c:otherwise>
                </c:choose>
            </td>
            <td>
                <a class="btn btn-mini" href="${ctx}/admin/user/update/${user.id}">
                    <fmt:message key="operation.update"/>
                </a>
                <a class="btn btn-mini" href="${ctx}/admin/user/delete/${user.id}">
                    <fmt:message key="operation.delete"/>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- 菜单定位 -->
<input type="hidden" name="menu-position" value="adminUser">

</body>
</html>
