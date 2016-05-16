(function(angular) {
    var LoginController = function($scope, $location, http) {
        $qw.dev && console.info('LoginController');

        $scope.login = function() {
            var user = $scope.credentials;
            var headers = user ? {
                authorization : 'Basic ' + btoa(user.username + ':' + user.password)
            } : {};

            http.get('authenticate', {
                headers : headers
            }, function(response) {
                $scope.setCurrentUser(response);
                $location.path('/');
            }, function(response) {
                console.error('登录失败！', response);
                $scope.setCurrentUser(null);
                $location.path('/app/login');
            });
        };

        $scope.credentials = {
            username : ($qw.dev ? 'admin' : ''),
            password : ($qw.dev ? 'pkpm' : '')
        };
    };

    LoginController.$inject = [ '$scope', '$location', 'HttpService' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));