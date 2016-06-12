(function(angular) {
    var LoginController = function($scope) {
        $qw.dev && console.info('LoginController');

        $scope.credentials = {
            username : ($qw.dev ? 'admin' : ''),
            password : ($qw.dev ? 'pkpm' : '')
        };

        $scope.getFormStyle = function() {
            return {
                'float' : 'right',
                'padding-top' : (document.body.clientHeight / 2 - document.querySelectorAll('#loginForm')[0].clientHeight)
                        + 'px',
                'width' : '400px'
            };
        };
    };

    LoginController.$inject = [ '$scope' ];
    angular.module('app.controllers').controller('LoginController', LoginController);
}(angular));