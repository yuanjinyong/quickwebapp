(function(angular) {
    var MenuService = function() {
        $qw.dev && console.info('MenuService');

        return $qw.buildServiceFn('api/sys/menus');
    };

    angular.module("app.services").factory("MenuService", MenuService);
}(angular));