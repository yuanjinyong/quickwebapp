(function(angular) {
    var DictService = function() {
        $qw.dev && console.info('DictService');

        return $qw.buildServiceFn('api/sys/dicts');
    };

    angular.module("app.services").factory("DictService", DictService);
}(angular));