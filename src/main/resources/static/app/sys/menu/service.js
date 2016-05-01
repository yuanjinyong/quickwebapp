(function(angular) {
    var MenuService = function($resource) {
        var uri = 'api/sys/menus';
        var service = $resource(this.uri + '/:id', {
            id : '@id'
        }, {
            update : {
                method : "PUT"
            },
            remove : {
                method : "DELETE"
            }
        });
        service.uri = uri;
        return service;
    };

    MenuService.$inject = [ '$resource' ];
    angular.module("app.services").factory("MenuService", MenuService);
}(angular));