(function(angular) {
    var UrlController = function($scope, urlService) {
        $scope.logger('UrlController');

        urlService.query(function(response) {
            $scope.items = response ? response : [];
        });

        $scope.addItem = function(description) {
            new UrlService({
                description : description,
                checked : false
            }).$save(function(item) {
                $scope.items.push(item);
            });
            $scope.newItem = "";
        };

        $scope.updateItem = function(item) {
            item.$update();
        };

        $scope.deleteItem = function(item) {
            item.$remove(function() {
                $scope.items.splice($scope.items.indexOf(item), 1);
            });
        };
    };

    UrlController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);
}(angular));