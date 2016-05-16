(function(angular) {
    var ErrorController = function($scope, $location) {
        $qw.dev && console.info('ErrorController');
    };

    ErrorController.$inject = [ '$scope', '$location' ];
    angular.module('app.controllers').controller('ErrorController', ErrorController);
}(angular));