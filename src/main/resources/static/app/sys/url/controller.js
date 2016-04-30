(function(angular) {
    var UrlController = function($scope, urlService) {
        console.log('UrlController');

        // urlService.query(function(response) {
        // $scope.items = response ? response : [];
        // });

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

        // ///////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////
        $scope.filterOptions = {
            filterText : "",
            useExternalFilter : true
        };
        $scope.totalServerItems = 0;
        $scope.pagingOptions = {
            pageSizes : [ 250, 500, 1000 ],
            pageSize : 250,
            currentPage : 1
        };
        $scope.setPagingData = function(data, page, pageSize) {
            var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
            $scope.myGridData = pagedData;
            $scope.totalServerItems = data.length;
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        };
        $scope.getPagedDataAsync = function(pageSize, page, searchText) {
            // setTimeout(function() {
            // var data;
            // if (searchText) {
            // var ft = searchText.toLowerCase();
            // $http.get('jsonFiles/largeLoad.json').success(function(largeLoad) {
            // data = largeLoad.filter(function(item) {
            // return JSON.stringify(item).toLowerCase().indexOf(ft) != -1;
            // });
            // $scope.setPagingData(data, page, pageSize);
            // });
            // } else {
            // $http.get('jsonFiles/largeLoad.json').success(function(largeLoad) {
            // $scope.setPagingData(largeLoad, page, pageSize);
            // });
            // }
            // }, 100);

            urlService.query(function(response) {
                // $scope.items = response ? response : [];
                $scope.setPagingData(response, page, pageSize);
            });
        };

        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

        $scope.$watch('pagingOptions', function(newVal, oldVal) {
            if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,
                        $scope.filterOptions.filterText);
            }
        }, true);
        $scope.$watch('filterOptions', function(newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,
                        $scope.filterOptions.filterText);
            }
        }, true);

        $scope.myGridOptions = {
            data : 'myGridData',
            columnDefs : [ {
                field : 'f_url',
                displayName : 'URL',
                width: 300
            }, {
                field : 'f_description',
                displayName : 'URL描述',
                width: 200
            }, {
                field : 'f_methods',
                displayName : '提交方式',
                width: 100
            }, {
                field : 'f_handler_method',
                displayName : '处理方法'
            }, {
                field : 'f_log',
                displayName : '记录日志',
                width: 100
            }, {
                field : 'f_auto',
                displayName : '自动生成',
                width: 100
            } ],
            enablePaging : true,
            showFooter : true,
            totalServerItems : 'totalServerItems',
            pagingOptions : $scope.pagingOptions,
            filterOptions : $scope.filterOptions
        };
    };

    UrlController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);
}(angular));