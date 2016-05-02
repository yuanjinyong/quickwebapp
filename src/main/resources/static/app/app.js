var contextPath = '/webapp';

(function(angular) {
    console.log('angular init');
    angular.module('app.directives', []);
    angular.module('app.filters', []);
    angular.module('app.controllers', []);
    angular.module('app.services', []);
    angular.module('app', [ //
    'ngRoute', // 多视图路由
    'ngResource', // RESTful API
    'ui.bootstrap', //Bootstrap components
    'ui.grid', // Grid表格控件
    'ui.grid.pagination', // Grid表格翻页控件
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

/*        $http.get('api/sys/routes', {}).success(function(response) {
            $scope.logger(response);
            successCallback && successCallback(response);
        }).error(function(response) {
            $scope.logger(response);
            failCallback && failCallback(response);
        });*/

        $routeProvider.when('/workspace', {
            templateUrl : 'workspace.html',
            controller : 'WorkspaceController'
        }).when('/login', {
            templateUrl : 'app/sys/login/form.html',
            controller : 'LoginController'
        }).when('/sys/url', {
            templateUrl : 'app/sys/url/list.html',
            controller : 'UrlController'
        }).when('/sys/menu', {
            templateUrl : 'app/sys/menu/list.html',
            controller : 'MenuController'
        }).otherwise('/sys/error', {
            templateUrl : 'app/sys/error/error.html',
            controller : 'ErrorController'
        });
    });
}(angular));