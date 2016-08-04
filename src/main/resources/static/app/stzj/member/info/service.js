(function(angular) {
    var MemberInfoService = function($resource) {
        $qw.dev && console.info('MemberInfoService');

        return $qw.buildServiceFn('api/member/members');
    };

    MemberInfoService.$inject = [ '$resource' ];
    angular.module("app.services").factory("MemberInfoService", MemberInfoService);
}(angular));