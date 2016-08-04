(function(angular) {
    var UserService = function($resource) {
        $qw.dev && console.info('UserService');

        return $qw.buildServiceFn('api/sys/users');
    };

    UserService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UserService", UserService);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var UserRoleService = function($resource) {
        $qw.dev && console.info('UserRoleService');

        return $qw.buildServiceFn('api/sys/users/:userId/roles', {
            userId : '@userId'
        });
    };

    UserRoleService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UserRoleService", UserRoleService);

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var UserMenuService = function($resource) {
        $qw.dev && console.info('UserMenuService');

        return $qw.buildServiceFn('api/sys/users/:userId/menus', {
            userId : '@userId'
        });
    };

    UserMenuService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UserMenuService", UserMenuService);
}(angular));