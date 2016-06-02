// 全局变量、函数和服务，避免很多地方都需要去写依赖注入
var $qw = {};

// 开发模式，生产环境上需要注释掉这行
$qw.dev = true;
$qw.dev && console.info('初始化$qw');

$qw.isMobile = false;
(function(a) {
    if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a)
            || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0, 4))) {
        $qw.isMobile = true;
    }
})(navigator.userAgent || navigator.vendor || window.opera);

// 这里把toJson包装下，然后加入到$rootScope中，这样就可以在所有的html页面中使用了
$qw.toJson = function(obj, pretty) {
    return angular.toJson(obj, pretty);
};

$qw.getDocHeight = function() {
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight,
            document.documentElement.offsetHeight, document.body.clientHeight, document.documentElement.clientHeight);
};

// 手动初始化
angular.element(document).ready(function() {
    $qw.dev && console.info('启动angular app');
    angular.bootstrap(document, [ 'app' ]);
});
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

(function(angular) {

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
    'ui.grid.expandable', // Grid表格折叠控件
    'ui.grid.edit', // Grid表格字段可编辑控件
    'ui.grid.cellNav', // Grid表格定位功能
    'ui.grid.rowEdit', // Grid表格行编辑控件
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
        $qw.dev && console.info('配置angular app');

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