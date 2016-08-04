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
                $qw.route('/' + menu.f_url_id.substring(0, menu.f_url_id.lastIndexOf('.')));
            } else {
                if (menu.isOpen) {
                    menu.isOpen = false;
                } else {
                    menu.isOpen = true;
                }

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
                }, 200);
            }
        };

        $scope.hideMenu = $cookies.get('hideMenu') == 'true';
        $scope.hideMenuFn = function() {
            $scope.hideMenu = !$scope.hideMenu;
            $cookies.put('hideMenu', $scope.hideMenu);
        };
        $scope.getMenuStyle = function() {
            return {
                'width' : ($location.path() == '/app/global/login' ? 0 : ($scope.hideMenu ? $scope.menuHideWidth
                        : $scope.menuItemWidth))
                        + 'px'
            };
        };

        $scope.getMenuItemStyle = function(menu) {
            return {
                'width' : $scope.menuItemWidth + 'px',
                'padding-left' : (menu.f_parent_ids.split('/').length * 16) + 'px'
            };
        };
        $scope.getPageStyle = function() {
            return {
                'margin-left' : ($location.path() == '/app/global/login' ? 0 : ($scope.hideMenu ? $scope.menuHideWidth
                        : $scope.menuItemWidth))
                        + 'px'
            };
        };

        $scope.toOriginalPath = function(user, originalPath) {
            $scope.currentUser = user;
            $qw.currentUser = user;

            $qw.http.get('api/sys/dict/groups/items', {
                params : {
                    orderBy : 'f_code,f_item_order'
                }
            }, function(response) {
                $qw.dict.buildDictFn(response);

                var path = '/app/global/welcome';
                $qw.dev && console.info('originalPath', originalPath, 'currentUser', $scope.currentUser);
                if (originalPath && originalPath != '/app/global/login') {
                    path = originalPath;
                }

                $location.path(path);
            }, function(response) {
                console.error('获取字典失败！', response);
                $qw.dict.dicts = [];
            });
        };

        $scope.toLogin = function(unauthorized, response) {
            $scope.currentUser = null;
            $qw.currentUser = null;

            if (unauthorized) {
                $qw.dev && console.info('还未登录，跳转到登录页面。', $location.path(), response);
                if ($location.path() != '/app/global/login') {
                    // 备份原来的path
                    $qw.originalPath = $location.path();
                }
            } else {
                $qw.dev && console.info('退出成功，跳转到登录页面。', response);
            }

            $location.path('/app/global/login');
        };
        $qw.http.unauthorizedCallback = function(response) {
            $qw.dev && console.info('unauthorizedCallback', response);
            $scope.toLogin(true, response);
        }

        $scope.login = function(user) {
            if (!user) {
                return;
            }

            var headers = {};
            if (user.openId) {
                headers['Open-ID'] = btoa(user.openId);
            } else {
                headers['Authorization'] = 'Basic ' + btoa(user.username + ':' + user.password);
            }

            $qw.http.get('api/sys/user', {
                headers : headers
            }, function(response) {
                $scope.toOriginalPath(response, $qw.originalPath);
                $qw.originalPath = null;
                $scope.loginErrorMsg = null;
            }, function(response) {
                console.error('登录失败！', response);
                if (response.status === 500) {
                    $scope.loginErrorMsg = response.message;
                } else {
                    $scope.loginErrorMsg = response || '账号密码不正确！';
                }
            });
        };

        $scope.logout = function() {
            $qw.http.post('logout', {}, {}, function(response) {
                $scope.toLogin(false, response);
            }, function(response) {
                console.error('退出失败！', response);
            });
        };

        $scope.isLoading = true;
        $location.path('/app/global/login');
        var code = $qw.location.params['code'];
        // 获取用户信息，如果用户还未登录，则会通过调用$qw.http.unauthorizedCallback跳转到登录页面。
        $qw.http.get('api/sys/user', {
            headers : code ? {
                'Weixin-Code' : btoa(code)
            } : {}
        }, function(response) {
            if (response) {
                $scope.toOriginalPath(response, $qw.location.path);
            } else {
                console.error('未获取到用户信息。', response);
            }
            $scope.isLoading = false;
        }, function(response) {
            console.error('获取用户信息失败。', response);
            $scope.isLoading = false;
        }, function(response) {
            console.error('还未登录。', response);
            $scope.isLoading = false;
        });
    };

    MainController.$inject = [ '$rootScope', '$scope', '$location', '$route', '$cookies' ];
    angular.module('app.controllers').controller('MainController', MainController);
}(angular));