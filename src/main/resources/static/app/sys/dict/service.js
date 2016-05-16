(function(angular) {
    var DictService = function($resource) {
        $qw.dev && console.info('DictService');

        var service = $resource('api/station/stations/:id', {}, {
            update : {
                method : "PUT"
            },
            remove : {
                method : "DELETE"
            }
        });
        service.uri = 'api/station/stations';

        service.dicts = {
            TrueFalse : {
                1 : '是',
                2 : '否',
                selectOptions : [ {
                    value : 1,
                    label : '是'
                }, {
                    value : 2,
                    label : '否'
                } ]
            },
            MenuType : {
                0 : '根',
                1 : '目录',
                2 : '页面',
                3 : '按钮',
                selectOptions : [ {
                    value : 0,
                    label : '根'
                }, {
                    value : 1,
                    label : '目录'
                }, {
                    value : 2,
                    label : '页面'
                }, {
                    value : 3,
                    label : '按钮'
                } ]
            }
        };

        return service;
    };

    DictService.$inject = [ '$resource' ];
    angular.module("app.services").factory("DictService", DictService);
}(angular));