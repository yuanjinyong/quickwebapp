(function(angular) {
    var UrlController = function($scope, urlService) {
        console.log('UrlController');

        $scope.addItem = function(description) {
            new UrlService({
                description : description,
                checked : false
            }).$save(function(item) {
                $scope.items.push(item);
            });
            $scope.newItem = "";
        };
        $scope.updateItem = function(item) {
            item.$update();
        };
        $scope.deleteItem = function(item) {
            item.$remove(function() {
                $scope.items.splice($scope.items.indexOf(item), 1);
            });
        };

        // /////////////////////////////////////////////////////////////////////////
        // /////////////////////////////////////////////////////////////////////////
        var queryParams = {
            pageSize : 10,
            currentPage : 1,
            orderBy : null
        };

        $scope.urlGridOptions = {
            enableFiltering : true, // 启用过滤器
            enableColumnResizing : true, // 启用调整列宽
            useExternalSorting : true, // 启用后台排序
            useExternalPagination : true, // 启用后台分页
            paginationPageSizes : [ 10, 20, 30, 50, 100 ], // 可选每页记录数
            paginationPageSize : 10, // 每页记录数
            totalItems : 0,// 总记录数
            columnDefs : [
                    {
                        field : 'f_url',
                        displayName : 'URL',
                        width : 300
                    },
                    {
                        field : 'f_description',
                        displayName : 'URL描述',
                        width : 200
                    },
                    {
                        field : 'f_methods',
                        displayName : '提交方式',
                        width : 100
                    },
                    {
                        field : 'f_handler_method',
                        displayName : '处理方法',
                        minWidth : 300
                    },
                    {
                        field : 'f_log',
                        displayName : '记录日志',
                        filterHeaderTemplate : '<div class="ui-grid-filter-container" ng-repeat="colFilter in col.filters"><div my-custom-dropdown></div></div>',
                        filter : {
                            term : '',
                            options : [ {
                                id : '',
                                value : ''
                            }, {
                                id : 1,
                                value : 'true'
                            }, {
                                id : 2,
                                value : 'false'
                            } ]
                        },
                        cellFilter : 'trueFalseFilter',
                        enableColumnResizing : false,
                        width : 100
                    }, {
                        field : 'f_auto',
                        displayName : '自动生成',
                        enableColumnResizing : false,
                        width : 100
                    } ],
            onRegisterApi : function(gridApi) {
                $scope.gridApi = gridApi;

                $scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
                    var orderBy = "";
                    if (sortColumns.length > 0) {
                        angular.forEach(sortColumns, function(data, index, array) {
                            // data等价于array[index]
                            orderBy += "," + data.field + " " + data.sort.direction;
                        });
                        orderBy = orderBy.substring(1);
                    }

                    queryParams.orderBy = orderBy;
                    getPage();
                });

                gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
                    queryParams.pageSize = pageSize;
                    queryParams.currentPage = newPage;
                    getPage();
                });

                $scope.gridApi.core.on.filterChanged($scope, function() {
                    $scope.urlGridOptions.totalItems = 0;

                    var grid = this.grid;
                    angular.forEach(grid.columns, function(data, index, array) {
                        // data等价于array[index]
                        if (data.enableFiltering) {
                            queryParams[data.field] = data.filters[0].term;
                        }
                    });

                    getPage();
                });
            }
        };

        var getPage = function() {
            queryParams.totalCount = $scope.urlGridOptions.totalItems < 0 ? 0 : $scope.urlGridOptions.totalItems;

            urlService.get(queryParams, function(page) {
                $scope.urlGridOptions.totalItems = page.totalCount;
                $scope.urlGridOptions.data = page.currentPageData;
            });
        };

        getPage();
    };

    UrlController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);

    angular.module("app.filters").filter('trueFalseFilter', function() {
        var trueFalseHash = {
            '' : '',
            1 : 'true',
            2 : 'false'
        };

        return function(input) {
            if (!input) {
                return '';
            } else {
                return trueFalseHash[input];
            }
        };
    });

    angular
            .module("app.directives")
            .directive(
                    'myCustomDropdown',
                    function() {
                        return {
                            template : '<select ng-model="colFilter.term" ng-options="option.id as option.value for option in colFilter.options"></select>'
                        };
                    });
}(angular));