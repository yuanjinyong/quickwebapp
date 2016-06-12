<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1" />
<title>Quick Webapp - Web应用快速开发平台</title>

<script type="text/javascript">
//全局变量、函数和服务，避免很多地方都需要去写依赖注入
var $qw = {
    dev : ${dev},
    buildTimestamp : ${timestamp}
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
    <script type="text/javascript" src="app/global/filter/dict.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/DialogService.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/GridService.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/global/service/HttpService.js?__t=${timestamp}"></script>

    <script type="text/javascript" src="app/sys/url/controller.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/sys/url/service.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/sys/dict/controller.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/sys/dict/service.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/sys/menu/controller.js?__t=${timestamp}"></script>
    <script type="text/javascript" src="app/sys/menu/service.js?__t=${timestamp}"></script>
</body>
</html>