(function(angular) {
    var LoginController = function($scope) {
        $qw.dev && console.info('LoginController');

        $scope.credentials = {
            username : ($qw.dev ? 'admin' : ''),
            password : ($qw.dev ? 'pkpm' : '')
        };
    };

    LoginController.$inject = [ '$scope' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));