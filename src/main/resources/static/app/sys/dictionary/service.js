(function(angular) {
    var DictGroupService = function($resource) {
        $qw.dev && console.info('DictGroupService');

        return $qw.buildServiceFn('api/sys/dict/groups');
    };

    DictGroupService.$inject = [ '$resource' ];
    angular.module("app.services").factory("DictGroupService", DictGroupService);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var DictItemService = function($resource) {
        $qw.dev && console.info('DictItemService');

        return $qw.buildServiceFn('api/sys/dict/groups/:groupId/items', {
            groupId : '@groupId'
        });
    };

    DictItemService.$inject = [ '$resource' ];
    angular.module("app.services").factory("DictItemService", DictItemService);
}(angular));