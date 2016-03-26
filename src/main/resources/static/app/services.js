(function(angular) {
    var UrlFactory = function($resource) {
        return $resource('/webapp/api/sys/urls/:id', {
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

    UrlFactory.$inject = [ '$resource' ];
    angular.module("app.services").factory("Url", UrlFactory);
}(angular));