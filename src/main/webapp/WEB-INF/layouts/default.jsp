<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="project.name"/>-<sitemesh:title/></title>
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
    <script src="${ctx}/static/js/lib/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/lib/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/lib/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
    <script src="${ctx}/static/js/lib/bootstrap-notify.js" type="text/javascript"></script>
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
    </style>
    <sitemesh:head/>
</head>

<body>
<div class="container">
    <%@ include file="/WEB-INF/layouts/header.jsp"%>
    <div class="row">
        <div class="span3">
            <%@ include file="/WEB-INF/layouts/left.menu.jsp"%>
        </div><!--/span-->
        <div class="span9">
            <sitemesh:body/>
        </div>
    </div>
    <%@ include file="/WEB-INF/layouts/footer.jsp"%>
</div>
<script src="${ctx}/static/js/lib/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
<script>
    window['ctx'] = '${ctx}';
    $(function () {
        //菜单定位
        var menuPosition = $('input[name=menu-position]').val();
        $('.nav.nav-list li').removeClass('active').filter(function () {
           return $(this).data('menu_position') == menuPosition;
        }).addClass('active');
    });
</script>
</body>
</html>