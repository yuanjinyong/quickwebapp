(function(angular) {
    var ApplicationController = function($rootScope, $scope, $http, $location) {
        console.log('ApplicationController');

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

        $scope.toLogin = function() {
            $scope.setCurrentUser(null);
            $scope.logger('跳转到登录页面');
            $location.path('/login');
        };

        $scope.logout = function() {
            $scope.httpPost('logout', {}, function(response) {
                $scope.toLogin();
            }, function(response) {
                $scope.logger(response);
            });
        };

        $scope.showMode = "list";
        $scope.setCurrentUser(null); //先初始成null，然后从服务器上获取当前用户信息，如果没有获取到则跳转到登录页面
        $scope.httpGet('user', {}, function(response) {
            if (response != "") {
                $scope.setCurrentUser(response);
            } else {
                $scope.toLogin();
            }
        }, function(response) {
            $scope.logger(response);
            $scope.toLogin();
        });
    };
    ApplicationController.$inject = [ '$rootScope', '$scope', '$http', '$location' ];
    angular.module('app.controllers').controller('ApplicationController', ApplicationController);
}(angular));