(function(angular) {
    var UrlService = function($resource) {
        $qw.dev && console.info('UrlService');

        return $qw.buildServiceFn('api/sys/urls');
    };

    UrlService.$inject = [ '$resource' ];
    angular.module("app.services").factory("UrlService", UrlService);
}(angular));