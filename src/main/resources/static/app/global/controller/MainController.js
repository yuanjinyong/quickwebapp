(function(angular) {
    var MainController = function($rootScope, $scope, $location, $route, $cookies) {
        $qw.dev && console.info('MainController');

        // $scope.langs = i18n.getAllLangs();
        $scope.langs = [ {
            code : 'en',
            name : 'English'
        }, {
            code : 'zh-cn',
            name : '中文'
        } ];
        $scope.currentLang = angular.fromJson($cookies.get('currentLang') || '{"code" : "zh-cn", "name" : "中文"}');

        $qw.i18n.setCurrentLang($scope.currentLang.code);
        $scope.changeLang = function(lang) {
            $scope.currentLang = lang;
            $qw.i18n.setCurrentLang(lang.code);
            $route.reload();
            $cookies.put('currentLang', angular.toJson($scope.currentLang));
        };

        $scope.menuHideWidth = 28;
        $scope.menuItemWidth = 200;
        $scope.currentMenu = {};
        $scope.openMenu = function(menu) {
            if (menu.f_type === 2) {
                $scope.currentMenu.css = null;
                $scope.currentMenu = menu;
                $scope.currentMenu.css = 'list-group-item-active';
                $qw.route(menu.f_url_id);
            } else {
                if (menu.isOpen) {
                    menu.isOpen = false;
                } else {
                    menu.isOpen = true;
                }

                $qw.timeout($scope.resizeMenuItemWidth, 10);
            }
        };

        $scope.resizeMenuItemWidth = function() {
            $qw.timeout(function() {
                var maxWidth = $scope.menuItemWidth - 16;
                var itemEs = document.querySelectorAll('.list-group-item');
                angular.forEach(itemEs, function(itemE) {
                    var item = angular.element(itemE);
                    item.css('width', 'initial'); // 这里要先设置回原始宽度
                    var clientWidth = item.prop('clientWidth'); // 获取计算后的原始宽度
                    if (maxWidth < clientWidth) {
                        maxWidth = clientWidth;
                    }
                });

                // 把所有的宽度都设置为最大的那个宽度
                maxWidth += 16;
                angular.forEach(itemEs, function(itemE) {
                    var item = angular.element(itemE);
                    item.css('width', maxWidth + 'px');
                });
            }, 10);
        }
        $qw.timeout($scope.resizeMenuItemWidth, 100);

        $scope.hideMenu = $cookies.get('hideMenu') == 'true';
        $scope.hideMenuFn = function() {
            $scope.hideMenu = !$scope.hideMenu;
            $cookies.put('hideMenu', $scope.hideMenu);
        };
        $scope.getMenuStyle = function() {
            return {
                'width' : ($scope.hideMenu ? $scope.menuHideWidth : $scope.menuItemWidth) + 'px'
            };
        };

        $scope.getMenuItemStyle = function(menu) {
            return {
                'padding-left' : (menu.f_parent_ids.split('/').length * 16) + 'px'
            };
        };
        $scope.getPageStyle = function() {
            return {
                'margin-left' : ($scope.hideMenu ? $scope.menuHideWidth : $scope.menuItemWidth) + 'px'
            };
        };

        $scope.setCurrentUser = function(user) {
            $scope.currentUser = user;
        };

        $scope.toLogin = function() {
            $scope.setCurrentUser(null);
            console.info('跳转到登录页面');
            $location.path('/app/login');
        };

        $scope.logout = function() {
            $qw.http.post('logout', {}, function(response) {
                $scope.toLogin();
            }, function(response) {
                console.error('退出失败！', response);
            });
        };

        $scope.currentUser = null; // 先初始成null，然后从服务器上获取当前用户信息，如果没有获取到则跳转到登录页面
        $qw.http.get('user', {}, function(response) {
            if (response != "") {
                $scope.setCurrentUser(response);
            } else {
                console.info('未获取到用户信息。', response);
                $scope.toLogin();
            }
        }, function(response) {
            console.error('未获取到用户信息。', response);
            $scope.toLogin();
        });
    };

    MainController.$inject = [ '$rootScope', '$scope', '$location', '$route', '$cookies' ];
    angular.module('app.controllers').controller('MainController', MainController);
}(angular));