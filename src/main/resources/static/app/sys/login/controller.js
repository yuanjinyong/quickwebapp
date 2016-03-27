(function(angular) {
    var LoginController = function($scope, $location) {
        $scope.logger('LoginController');

        $scope.login = function() {
            var user = $scope.credentials;
            var headers = user ? {
                authorization : 'Basic ' + btoa(user.username + ':' + user.password)
            } : {};

            $scope.httpGet('authenticate', {
                headers : headers
            }, function(response) {
                $scope.setCurrentUser(response);
                $scope.error = false;
                $location.path('/');
            }, function(response) {
                $scope.setCurrentUser(null);
                $scope.error = true;
                $location.path('/login');
            });
        };

        $scope.error = false;
        $scope.credentials = {
            username : ($scope.dev ? 'admin' : ''),
            password : ($scope.dev ? 'pkpm' : '')
        };
    };

    LoginController.$inject = [ '$scope', '$location' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));