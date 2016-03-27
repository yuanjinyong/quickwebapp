(function(angular) {
    var ErrorController = function($scope, $location) {
        $scope.logger('ErrorController');
    };

    ErrorController.$inject = [ '$scope', '$location' ];
    angular.module('app.controllers').controller('ErrorController', ErrorController);
}(angular));