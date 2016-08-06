(function(angular) {
    angular.module('app.interceptors').factory(
        'HttpInterceptor',
        [
            '$q',
            '$location',
            function($q, $location) {
                var getReqInfo = function(config) {
                    return '[' + config.method + '][' + config.url + ']';
                };

                return {
                    'request' : function(config) {// 请求成功的处理
                        // $qw.dev && console.debug('请求' + getReqInfo(config) + '成功，config:', config);
                        config.requestTimestamp = new Date().getTime();
                        return config;
                    },
                    'requestError' : function(rejection) {// 请求失败的处理
                        $qw.dev && console.error('请求' + getReqInfo(rejection.config) + '失败，rejection:', rejection);
                        return $q.reject(rejection);
                    },

                    'response' : function(response) {// 响应成功的处理
                        // $qw.dev && console.debug('响应' + getReqInfo(response.config) + '成功，response:', response);
                        response.config.responseTimestamp = new Date().getTime();
                        if (response.data.state == false) {
                            $qw.dialog.buildErrorDialogFn({}, response.data.msg).openFn();
                        }
                        return response;
                    },
                    'responseError' : function(response) {// 响应失败的处理
                        $qw.dev && console.error('响应' + getReqInfo(response.config) + '失败，response:', response);
                        switch (response.status) {
                        case 401: // 未认证
                            var curPath = $location.path();
                            if (curPath !== '/app/global/login') {
                                $qw.originalPath = curPath;
                                $qw.dev
                                    && console.warn('访问' + getReqInfo(response.config) + '需要先登录认证，备份当前路径['
                                        + $qw.originalPath + ']，跳转到登录页面。');
                                $location.path('/app/global/login');
                            }
                            break;
                        case 403: // 已认证，无访问权限
                            if (response.data && response.data.message) {
                                $qw.dev && console.error(response.data.message);
                                $qw.dialog.buildErrorDialogFn({}, response.data.message).openFn();
                            } else {
                                $qw.dialog
                                    .buildErrorDialogFn({}, '无' + getReqInfo(response.config) + '的访问权限，请联系系统管理员！')
                                    .openFn();
                            }
                            break;
                        case 404: // URL地址不正确，不存在该资源或地址
                            var msg = '未找到[' + response.config.method + '][' + response.config.url + ']URL！';
                            $qw.dev && console.error(msg);
                            $qw.dialog.buildErrorDialogFn({}, msg).openFn();
                            break;
                        default: // 其他
                            if (response.data && response.data.message) {
                                $qw.dialog.buildErrorDialogFn({}, response.data.message).openFn();
                            } else {
                                $qw.dialog.buildErrorDialogFn({}, response.statusText).openFn();
                            }
                        }

                        return $q.reject(response);
                    }
                };
            } ]);
}(angular));