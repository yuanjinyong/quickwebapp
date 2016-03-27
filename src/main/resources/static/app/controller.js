(function(angular) {
    var ApplicationController = function($rootScope, $scope, $http, $location) {
        $scope.dev = true; // 开发模式
        $scope.logger = function(message) {
            if (typeof (message) == 'object') {
                $scope.logger(JSON.stringify(message, null, 2));
            } else if (typeof (message) == 'function') {
                console.log(message);
            } else {
                console.log(message);
            }
        };

        $scope.httpGet = function(url, params, successCallback, failCallback) {
            $http.get(url, params).success(function(response) {
                $scope.logger(response);
                successCallback && successCallback(response);
            }).error(function(response) {
                $scope.logger(response);
                failCallback && failCallback(response);
            });
        };
        $scope.httpPost = function(url, params, successCallback, failCallback) {
            $http.post(url, params).success(function(response) {
                $scope.logger(response);
                successCallback && successCallback(response);
            }).error(function(response) {
                $scope.logger(response);
                failCallback && failCallback(response);
            });
        };
        $scope.openMenu = function(menu) {
            if (menu.f_type === 2) {
                $scope.route(menu.f_url_id);
            } else {
                if (menu.isOpen) {
                    menu.isOpen = false;
                } else {
                    menu.isOpen = true;
                }
            }
        };
        $scope.route = function(path) {
            if (path != '') {
                $scope.logger('路由到' + path);
                $location.path(path);
            }
        };
        $scope.changeShow = function(showMode) {
            $scope.showMode = showMode;
        }

        $scope.setCurrentUser = function(user) {
            $scope.currentUser = user;
        };

        $scope.logout = function() {
            $scope.httpPost('logout', {}, function(response) {
                $scope.setCurrentUser(null);
                $location.path('/login');
            }, function(response) {
            });
        };

        $scope.logger('ApplicationController');
        $scope.showMode = "list";
        $scope.currentUser = null;
        if ($scope.currentUser == null) {
            $scope.logger('需要先登录');
            $location.path('/login');
        }
    };
    ApplicationController.$inject = [ '$rootScope', '$scope', '$http', '$location' ];
    angular.module('app.controllers').controller('ApplicationController', ApplicationController);
}(angular));