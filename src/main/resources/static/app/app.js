var contextPath='/webapp';

(function(angular) {
    console.log('angular init');
    angular.module('app.controllers', []);
    angular.module('app.services', []);
    angular.module('app', [ 'ngRoute', 'ngResource', 'app.controllers', 'app.services' ]);

    angular.module('app').config(function($routeProvider, $httpProvider) {
        // Spring Security responds to it by not sending a “WWW-Authenticate” header in a 401 response, and thus the
        // browser will not pop up an authentication dialog (which is desirable in our app since we want to control the
        // authentication).
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

/*        $http.get('api/sys/routes', {}).success(function(response) {
            $scope.logger(response);
            successCallback && successCallback(response);
        }).error(function(response) {
            $scope.logger(response);
            failCallback && failCallback(response);
        });*/

        $routeProvider.when('/workspace', {
            templateUrl : 'workspace.html',
            controller : 'WorkspaceController'
        }).when('/login', {
            templateUrl : 'app/sys/login/form.html',
            controller : 'LoginController'
        }).when('/sys/url', {
            templateUrl : 'app/sys/url/list.html',
            controller : 'UrlController'
        }).when('/sys/menu', {
            templateUrl : 'app/sys/menu/list.html',
            controller : 'MenuController'
        }).otherwise('/sys/error', {
            templateUrl : 'app/sys/error/error.html',
            controller : 'ErrorController'
        });
    });
}(angular));