(function(angular) {
    var TenantService = function($resource) {
        $qw.dev && console.info('TenantService');

        return $qw.buildServiceFn('api/sys/tenants');
    };

    TenantService.$inject = [ '$resource' ];
    angular.module("app.services").factory("TenantService", TenantService);
}(angular));