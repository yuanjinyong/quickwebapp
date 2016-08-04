(function(angular) {
    var StationInfoService = function() {
        $qw.dev && console.info('StationInfoService');

        return $qw.buildServiceFn('api/station/stations');
    };

    angular.module("app.services").factory("StationInfoService", StationInfoService);
}(angular));