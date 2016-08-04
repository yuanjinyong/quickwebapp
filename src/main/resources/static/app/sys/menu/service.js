(function(angular) {
    var MenuService = function() {
        $qw.dev && console.info('MenuService');

        return $qw.buildServiceFn('api/sys/menus');
    };

    angular.module("app.services").factory("MenuService", MenuService);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var MenuUrlService = function() {
        $qw.dev && console.info('MenuUrlService');

        return $qw.buildServiceFn('api/sys/menus/:menuId/urls');
    };

    angular.module("app.services").factory("MenuUrlService", MenuUrlService);
}(angular));