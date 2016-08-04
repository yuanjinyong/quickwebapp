(function(angular) {
    var RoleService = function($resource) {
        $qw.dev && console.info('RoleService');

        return $qw.buildServiceFn('api/sys/roles');
    };

    RoleService.$inject = [ '$resource' ];
    angular.module("app.services").factory("RoleService", RoleService);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var RoleMenuService = function($resource) {
        $qw.dev && console.info('RoleMenuService');

        return $qw.buildServiceFn('api/sys/roles/:roleId/menus', {
            roleId : '@roleId'
        });
    };

    RoleMenuService.$inject = [ '$resource' ];
    angular.module("app.services").factory("RoleMenuService", RoleMenuService);
}(angular));