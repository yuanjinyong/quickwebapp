(function(angular) {
    var UrlService = function($resource) {
        return $resource(contextPath + '/api/sys/urls/:id', {
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

    UrlService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UrlService", UrlService);
}(angular));