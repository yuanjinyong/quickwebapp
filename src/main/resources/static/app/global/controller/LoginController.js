(function(angular) {
    var LoginController = function($scope) {
        $qw.dev && console.info('LoginController');

        $scope.credentials = {
            username : ($qw.dev ? $qw.superAdmin : ''),
            password : ($qw.dev ? 'pkpm' : '')
        };

        $scope.getFormStyle = function() {
            var padding = (document.getElementsByClassName('view-container')[0].clientHeight - 40 - document
                .getElementById('loginForm').clientHeight) / 2;
            return {
                'padding-top' : padding + 'px',
                'padding-bottom' : padding + 'px',
            };
        };
    };

    LoginController.$inject = [ '$scope' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));