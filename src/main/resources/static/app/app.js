// 这里把toJson包装下，然后加入到$rootScope中，这样就可以在所有的html页面中使用了
$qw.toJson = function(obj, pretty) {
    return angular.toJson(obj, pretty);
};

$qw.getDocHeight = function() {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight,
            document.documentElement.offsetHeight, document.body.clientHeight, document.documentElement.clientHeight);
};
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

(function(angular) {
    $qw.dev && console.info('初始化angular');

    angular.module('app.directives', []);
    angular.module('app.filters', []);
    angular.module('app.controllers', []);
    angular.module('app.services', []);
    angular.module('app', [ //
    'ngRoute', // 多视图路由
    'ngResource', // RESTful API
    'ngCookies', //
    'ngAnimate', // 弹出alert对话框的动画效果
    'ui.bootstrap', // Bootstrap components
    'ui.grid', // Grid表格控件
    'ui.grid.autoResize', // 父级容器大小变化时，自动调整表格的大小
    'ui.grid.pagination', // Grid表格翻页控件
    'ui.grid.treeView', // Grid表格树形显示
    'ui.grid.selection', // Grid表格启用选择行
    'ui.grid.resizeColumns', // Grid表格启用调整列宽
    'ui.grid.moveColumns', // Grid表格拖动调整列顺序
    'ui.grid.pinning', // Grid表格固定列
    'ui.grid.exporter', // Grid表格导出记录
    'app.directives', // 自定义指令
    'app.filters', // 自定义过滤器
    'app.controllers', // 控制器
    'app.services'// 服务
    ]);

    angular.module('app').config(function($routeProvider, $httpProvider) {
        // Spring Security responds to it by not sending a “WWW-Authenticate” header in a 401 response, and thus the
        // browser will not pop up an authentication dialog (which is desirable in our app since we want to control the
        // authentication).
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

        /*
         * $http.get('api/sys/routes', {}).success(function(response) { $scope.logger(response); successCallback && successCallback(response); }).error(function(response) { $scope.logger(response); failCallback && failCallback(response); });
         */

        $routeProvider.when('/workspace', {
            templateUrl : 'workspace.html',
            controller : 'WorkspaceController'
        }).when('/app/login', {
            templateUrl : 'app/sys/login/form.html',
            controller : 'LoginController'
        }).when('/app/sys/url', {
            templateUrl : 'app/sys/url/list.html',
            controller : 'UrlController'
        }).when('/app/sys/dict', {
            templateUrl : 'app/sys/dict/list.html',
            controller : 'DictController'
        }).when('/app/sys/menu', {
            templateUrl : 'app/sys/menu/list.html',
            controller : 'MenuController'
        }).when('/app/stzj/member/info', {
            templateUrl : 'app/stzj/member/info/list.html',
            controller : 'MemberInfoController'
        }).when('/app/stzj/station/info', {
            templateUrl : 'app/stzj/station/info/list.html',
            controller : 'StationInfoController'
        }).otherwise('/app/sys/error', {
            templateUrl : 'app/sys/error/error.html',
            controller : 'ErrorController'
        });
    });
}(angular));