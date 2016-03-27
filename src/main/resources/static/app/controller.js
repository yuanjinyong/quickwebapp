(function(angular) {
    var ApplicationController = function($rootScope, $scope, $http, $location) {
        $scope.setCurrentUser = function(user) {
            $scope.currentUser = user;
        };
        $scope.logout = function() {
            $http.post('logout', {}).success(function() {
                console.log('logout success');
                $scope.setCurrentUser(null);
                $location.path('/login');
            }).error(function(response) {
                console.log('logout error');
                alert(JSON.stringify(response, null, 2));
            });
        }

        console.log('ApplicationController');
        $scope.currentUser = null;
        if ($scope.currentUser == null) {
            //alert('需要先登录');
            $location.path('/login');
        }
    };
    ApplicationController.$inject = [ '$rootScope', '$scope', '$http', '$location' ];
    angular.module('app.controllers').controller('ApplicationController', ApplicationController);

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    var WorkspaceController = function($rootScope, $scope, $http, $location) {

    };
    angular.module('app.controllers').controller('WorkspaceController', WorkspaceController);
}(angular));