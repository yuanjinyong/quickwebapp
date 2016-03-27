(function(angular) {
    var LoginController = function($rootScope, $scope, $http, $location, menuService) {
        console.log('LoginController');
        var authenticate = function(credentials, callback) {
            var headers = credentials ? {
                authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
            } : {};

            $http.get('authenticate', {
                headers : headers
            }).success(function(response) {
                alert(JSON.stringify(response, null, 2));
                if (response.name) {
                    //menuService.query(function(response) {
                    //    $rootScope.menu = response ? response : [];
                    //});

                    $scope.setCurrentUser(response);
                    $rootScope.authenticated = true;
                } else {
                    $scope.setCurrentUser(null);
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function(response) {
                $scope.setCurrentUser(null);
                $rootScope.authenticated = false;
                callback && callback();
            });
        }


        $scope.error = false;
        //authenticate();
        $scope.credentials = {username : 'admin', password : 'pkpm'};
        $scope.login = function() {
            authenticate($scope.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.path('/');
                    $scope.error = false;
                } else {
                    $location.path('/login');
                    $scope.error = true;
                }
            });
        };
    };

    LoginController.$inject = [ '$rootScope', '$scope', '$http', '$location', 'MenuService' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));