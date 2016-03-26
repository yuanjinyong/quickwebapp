(function(angular) {
    var AppController = function($scope, Url) {
        Url.query(function(response) {
            $scope.items = response ? response : [];
        });

        $scope.addItem = function(description) {
            new Url({
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

    AppController.$inject = [ '$scope', 'Url' ];
    angular.module("app.controllers").controller("AppController", AppController);
}(angular));