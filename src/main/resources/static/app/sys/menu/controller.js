(function(angular) {
    var MenuController = function($scope, menuService) {
        menuService.query(function(response) {
            $scope.items = response ? response : [];
        });

        $scope.addItem = function(description) {
            new MenuService({
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

    MenuController.$inject = [ '$scope', 'MenuService' ];
    angular.module("app.controllers").controller("MenuController", MenuController);
}(angular));