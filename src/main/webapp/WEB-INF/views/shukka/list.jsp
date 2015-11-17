<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>出库情报</title>
</head>

<body>

<div class="text-center">
    <h3>出库情报</h3>
</div>

<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">
        <button data-dismiss="alert" class="close">×</button>
            ${message}
    </div>
</c:if>

<c:if test="${not empty error}">
    <div id="message" class="alert alert-error">
        <button data-dismiss="alert" class="close">×</button>
            ${error}
    </div>
</c:if>

<div class="clearfix">
    <form id="importDataForm" class="form-inline" action="${ctx}/shukka/loadHeadData" method="post"
          enctype="multipart/form-data">
        <span class="btn btn-success fileinput-button">
            <span>选择数据导入文件</span>
            <input id="fileupload" type="file" name="file"/>
        </span>
        <span class="selected-file-path"></span>

        <div class="pull-right">
            <button type="submit import-btn" class="btn btn-primary"> 导入</button>
        </div>
    </form>
</div>
<table id="shukkaListTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="width: 150px;">仓库编号</th>
        <th style="width: 130px;">出货日</th>
        <th style="width: 215px;">用户编号</th>
        <th style="width: 215px;">集装箱编号</th>
        <th style="width: 215px;">得意先id</th>
        <th style="width: 215px;">订单编号</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${shukkaHList}" var="item">
        <tr>
            <td>
                <input type="hidden" name="id" value="${item.id}"/>
                    ${item.warehouseId}
            </td>
            <td><fmt:formatDate value="${item.shukkaDate}" pattern="yyyy/MM/dd"/></td>
            <td>${item.userId}</td>
            <td>
                <div class="can-edit" contenteditable="true" name="contenerId">${item.contenerId}</div>
            </td>
            <td>
                <div class="can-edit" contenteditable="true" name="customerId">${item.customerId}</div>
            </td>
            <td>
                <div class="can-edit" contenteditable="true" name="saleCd">${item.saleCd}</div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="clearfix">
    <button class="btn btn-primary pull-right shukka-save">
        <fmt:message key="save"/>
    </button>
</div>

<!-- 菜单定位 -->
<input type="hidden" name="menu-position" value="shukka">


<script src="${ctx}/static/js/shukka.js"></script>
</body>
</html>
