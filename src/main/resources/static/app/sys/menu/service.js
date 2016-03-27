(function(angular) {
    var MenuService = function($resource) {
        return $resource('/webapp/api/sys/menus/:id', {
            id : '@id'
        }, {
            update : {
                method : "PUT"
            },
            remove : {
                method : "DELETE"
            }
        });
    };

    MenuService.$inject = [ '$resource' ];
    angular.module("app.services").factory("MenuService", MenuService);
}(angular));