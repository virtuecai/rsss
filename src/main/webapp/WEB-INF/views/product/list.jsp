<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>商品管理</title>
</head>

<body>

<div class="text-center">
    <h3>商品一览</h3>
</div>

<div class="clearfix">
    <form class="form-inline" action="${ctx}/product/search" method="post">
        <label class="checkbox">
            <fmt:message key="product.code"/>: <input type="text" class="input-small" name="code" id="code" value="${code}" style="width: 130px;">
        </label>&nbsp;&nbsp;&nbsp;
        <label class="checkbox">
            <fmt:message key="product.name.ja"/>: <input type="text" class="input-small" name="nameJa" id="nameJa" value="${nameJa}" style="width: 130px;">
        </label>&nbsp;&nbsp;&nbsp;
        <label class="checkbox">
            <fmt:message key="product.name.zh"/>: <input type="text" class="input-small" name="nameZh" id="nameZh" value="${nameZh}" style="width: 130px;">
        </label>&nbsp;&nbsp;&nbsp;
        <div class="pull-right">
            <button type="submit" class="btn btn-primary"><fmt:message key="search"/></button>
        </div>
    </form>
</div>
<table id="productListTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="width: 14px;"></th>
        <th style="width: 130px;"><fmt:message key="product.code"/></th>
        <th style="width: 215px;"><fmt:message key="product.name.ja"/></th>
        <th style="width: 215px;"><fmt:message key="product.name.zh"/></th>
        <th style="width: 70px;"><fmt:message key="product.grossWeight"/></th>
        <th style="width: 70px;"><fmt:message key="product.netWeight"/></th>
        <th><fmt:message key="product.updateDate"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${productList}" var="p">
        <tr>
            <td>
                <input type="checkbox" value="${p.id}"/>
                <input type="hidden" name="product2Id" value="${p.product2.id}"/>
            </td>
            <td>${p.product2.code}</></td>
            <td >
                <div class="can-edit" data-name="name">${p.name}</div>
            </td>
            <td>
                <div class="can-edit" data-name="name" data-objectname="product2">${p.product2.name}</div>
            </td>
            <td>
                <div class="can-edit number" data-name="grossWeight" data-objectname="product2">
                    ${p.product2.grossWeight}
                </div>
            </td>
            <td>
                <div class="can-edit number" data-name="netWeight" data-objectname="product2">${p.product2.netWeight}</div>
            </td>
            <td>
                <fmt:formatDate value="${p.updateDate}" pattern="yyyy/MM/dd"/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="clearfix">
    <a class="btn btn pull-left" target="_blank" href="${ctx}/product/export"><fmt:message key="export.excel"/></a>
    <button class="btn btn-primary pull-right products-save">
        <fmt:message key="save"/>
    </button>
</div>

<!-- 菜单定位 -->
<input type="hidden" name="menu-position" value="product">
<script>
    var message = {
        numberError : '<fmt:message key="form.number.error.title"/>',
        saveDataCount : '<fmt:message key="form.save.data.count"/>',
        numberError : '<fmt:message key="form.number.error.title"/>',
        emptyError : '<fmt:message key="form.empty.error"/>'
    };
</script>
<script src="${ctx}/static/js/product.js"></script>
</body>
</html>
