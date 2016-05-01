(function(angular) {
    var UrlService = function($resource) {
        var uri = 'api/sys/urls';
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

    UrlService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UrlService", UrlService);
}(angular));