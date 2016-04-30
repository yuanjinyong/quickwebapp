(function(angular) {
    var ErrorController = function($scope, $location) {
        $scope.debug('ErrorController');
    };

    ErrorController.$inject = [ '$scope', '$location' ];
    angular.module('app.controllers').controller('ErrorController', ErrorController);
}(angular));