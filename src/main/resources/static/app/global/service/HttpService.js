(function(angular) {
    var HttpService = function($http) {
        $qw.dev && console.info('HttpService');

        var service = {
            unauthorizedCallback : null,
            get : function(url, config, successCallback, failCallback, unauthorizedCallback) {
                var service = this;
                return $http.get(url, config).success(
                        function(response) {
                            $qw.dev
                                    && console.debug(' Http get url:', url, '\n    config is:', config,
                                            '\n  response is:', response);
                            if (response && response.state == false) {
                                failCallback && failCallback(response);
                            } else {
                                successCallback && successCallback(response);
                            }
                        }).error(function(response) {
                    if (response.status === 401) {
                        if (unauthorizedCallback) {
                            return unauthorizedCallback(response);
                        } else if (service.unauthorizedCallback) {
                            return service.unauthorizedCallback(response);
                        }
                    }

                    console.error(' Http get url:', url, '\n    config is:', config, '\n  response is:', response);
                    failCallback && failCallback(response);
                });
            },
            post : function(url, data, config, successCallback, failCallback) {
                var service = this;
                return $http.post(url, data, config).success(
                        function(response) {
                            $qw.dev
                                    && console.debug('Http post url:', url, '\n      data is:', data,
                                            '\n    config is:', config, '\n  response is:', response);
                            successCallback && successCallback(response);
                        }).error(
                        function(response) {
                            if (response.status === 401 && service.unauthorizedCallback) {
                                return service.unauthorizedCallback(response);
                            }

                            console.error('Http post url:', url, '\n      data is:', data, '\n    config is:', config,
                                    '\n  response is:', response);
                            failCallback && failCallback(response);
                        });
            }
        };

        return service;
    };

    HttpService.$inject = [ '$http' ];
    angular.module("app.services").factory("HttpService", HttpService);
}(angular));