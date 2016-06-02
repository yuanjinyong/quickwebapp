(function(angular) {
    var HttpService = function($http) {
        $qw.dev && console.info('HttpService');

        var service = {
            unauthorizedCallback : null,
            get : function(url, params, successCallback, failCallback) {
                var service = this;
                return $http.get(url, params).success(
                        function(response) {
                            $qw.dev
                                    && console.debug('Http get url: ', url, '\n   params is: ', params,
                                            '\n response is: ', response);
                            if (response && response.state == false) {
                                failCallback && failCallback(response);
                            } else {
                                successCallback && successCallback(response);
                            }
                        }).error(function(response) {
                    if (response.status === 401 && service.unauthorizedCallback) {
                        return service.unauthorizedCallback(response);
                    }

                    console.error('Http get url: ', url, '\n   params is: ', params, '\n response is: ', response);
                    failCallback && failCallback(response);
                });
            },
            post : function(url, params, successCallback, failCallback) {
                var service = this;
                return $http.post(url, params).success(
                        function(response) {
                            $qw.dev
                                    && console.debug('Http post url: ', url, '\n   params is: ', params,
                                            '\n response is: ', response);
                            successCallback && successCallback(response);
                        }).error(function(response) {
                    if (response.status === 401 && service.unauthorizedCallback) {
                        return service.unauthorizedCallback(response);
                    }

                    console.error('Http post url: ', url, '\n   params is: ', params, '\n response is: ', response);
                    failCallback && failCallback(response);
                });
            }
        };

        return service;
    };

    HttpService.$inject = [ '$http' ];
    angular.module("app.services").factory("HttpService", HttpService);
}(angular));