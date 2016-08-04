<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="com.quickwebapp.framework.core.utils.JsonUtil"%>
<%@page import="com.quickwebapp.usm.sys.entity.MenuEntity"%>
<%
    //
			Boolean dev = (Boolean) request.getAttribute("dev");
			Long timestamp = (Long) request.getAttribute("timestamp");
			List<MenuEntity> pages = (List<MenuEntity>) request.getAttribute("pages");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1" />
<title>Quick Webapp - Web应用快速开发平台</title>

<script type="text/javascript">
//全局变量、函数和服务，避免很多地方都需要去写依赖注入
var $qw = {
    dev : ${dev},
    superAdmin : '${superAdmin}',
    buildTimestamp : ${timestamp},
    pages : <%=JsonUtil.toJson(pages)%>
};
</script>

<link rel="icon" type="image/x-icon" href="weblibs/favicon.ico">
<link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" href="webjars/ui-grid/3.1.1/ui-grid.min.css" />
<link rel="stylesheet" href="weblibs/css/app.css?__t=${timestamp}" />

</head>

<body ng-controller="ApplicationController">

    <div class="main-container" ng-controller="MainController" ng-if="$qw.isMobile" ng-include="'app/global/index-mobile.html?__t=${timestamp}'"></div>
    <div class="main-container" ng-controller="MainController" ng-if="!$qw.isMobile" ng-include="'app/global/index-desktop.html?__t=${timestamp}'"></div>

    <script type="text/javascript" src="webjars/angularjs/1.4.8/angular.min.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.4.8/angular-animate.min.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.4.8/angular-route.min.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.4.8/angular-resource.min.js"></script>
    <script type="text/javascript" src="webjars/angularjs/1.4.8/angular-cookies.min.js"></script>
    <script type="text/javascript" src="webjars/angular-ui-bootstrap/1.0.3/ui-bootstrap-tpls.min.js"></script>
    <script type="text/javascript" src="webjars/ui-grid/3.1.1/ui-grid.min.js"></script>
    <script type="text/javascript" src="webjars/lodash/4.0.0/lodash.min.js"></script>
    
    <script type="text/javascript" src="app/app.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/controller/ApplicationController.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/controller/DialogController.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/controller/MainController.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/controller/LoginController.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/directive/grid.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/directive/resize.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/directive/tree.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/filter/dict.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/DialogService.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/GridService.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/HttpService.js?__t=${timestamp}"></script>

    <%
        //开发态分散加载每个菜单页面的js脚本
        out.println("<!--开发态分散加载每个菜单页面的js脚本 START-->");
        for (MenuEntity menu : pages) {
            if (menu.getF_url_id() != null) {
                String path = menu.getF_url_id().substring(0, menu.getF_url_id().lastIndexOf("/"));
                String staticDirectory = getServletContext().getRealPath("WEB-INF/classes/static");
                if (new File(staticDirectory, path + "/controller.js").exists()) {
                    out.println("    <script type=\"text/javascript\" src=\"" + path + "/controller.js?__t="
                            + timestamp + "\"></script><!--" + menu.getF_menu_name() + "-->");
                } else {
                    out.println("    <!--script type=\"text/javascript\" src=\"" + path + "/controller.js?__t="
                            + timestamp + "\"></script--><!--" + menu.getF_menu_name() + "-->");
                }
                if (new File(staticDirectory, path + "/controller.js").exists()) {
                    out.println("    <script type=\"text/javascript\" src=\"" + path + "/service.js?__t="
                            + timestamp + "\"></script><!--" + menu.getF_menu_name() + "-->");
                } else {
                    out.println("    <!--script type=\"text/javascript\" src=\"" + path + "/service.js?__t="
                            + timestamp + "\"></script--><!--" + menu.getF_menu_name() + "-->");
                }
            }
        }
        out.println("    <!--开发态分散加载每个菜单页面的js脚本 END-->");
    %>
</body>
</html>