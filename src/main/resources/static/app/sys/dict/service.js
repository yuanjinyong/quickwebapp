(function(angular) {
    var DictService = function($resource) {
        var uri = 'api/sys/dicts';
        var service = $resource(this.uri + '/:id', {
            id : '@id'
        }, {
            update : {
                method : "PUT"
            },
            remove : {
                method : "DELETE"
            }
        });

        service.uri = uri;
        service.dicts = {
            trueFalse : {
                '0' : '否',
                '1' : '是',
                options : [ {
                    id : '0',
                    value : '否'
                }, {
                    id : '1',
                    value : '是'
                } ]
            }
        };
        service.getValueFn = function(dictId, itemId) {
            return this.dicts[dictId][itemId];
        };

        return service;
    };

    DictService.$inject = [ '$resource' ];
    angular.module("app.services").factory("DictService", DictService);
}(angular));