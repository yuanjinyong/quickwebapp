(function(angular) {
    var ApplicationController = function($rootScope, $scope, $http, $location, $route, i18nService) {
        console.log('ApplicationController');

        $scope.dev = true; // 开发模式

        // $scope.langs = i18nService.getAllLangs();
        $scope.langs = [ {
            code : 'en',
            name : 'English'
        }, {
            code : 'zh-cn',
            name : '中文'
        } ];
        $scope.lang = 'zh-cn';

        i18nService.setCurrentLang($scope.lang);
        $scope.changeLang = function(newVal) {
            i18nService.setCurrentLang(newVal);
            $route.reload();
        };

        $scope.httpGet = function(url, params, successCallback, failCallback) {
            $scope.dev && console.debug(params);
            return $http.get(url, params).success(function(response) {
                $scope.dev && console.debug(response);
                successCallback && successCallback(response);
            }).error(function(response) {
                console.error(response);
                failCallback && failCallback(response);
            });
        };
        $scope.httpPost = function(url, params, successCallback, failCallback) {
            $scope.dev && console.debug(params);
            return $http.post(url, params).success(function(response) {
                $scope.dev && console.debug(response);
                successCallback && successCallback(response);
            }).error(function(response) {
                console.error(response);
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
                console.info('路由到' + path);
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
            console.info('跳转到登录页面');
            $location.path('/login');
        };

        $scope.logout = function() {
            $scope.httpPost('logout', {}, function(response) {
                $scope.toLogin();
            }, function(response) {
                console.error(response);
            });
        };

        $scope.showMode = "list";
        $scope.setCurrentUser(null); // 先初始成null，然后从服务器上获取当前用户信息，如果没有获取到则跳转到登录页面
        $scope.httpGet('user', {}, function(response) {
            if (response != "") {
                $scope.setCurrentUser(response);
            } else {
                $scope.toLogin();
            }
        }, function(response) {
            console.error(response);
            $scope.toLogin();
        });
    };
    ApplicationController.$inject = [ '$rootScope', '$scope', '$http', '$location', '$route', 'i18nService' ];
    angular.module('app.controllers').controller('ApplicationController', ApplicationController);
}(angular));