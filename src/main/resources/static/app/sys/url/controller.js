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
            enableGridMenu : true, // 启用表格右上角菜单
            enableFiltering : true, // 启用过滤器
            enableColumnResizing : true, // 启用调整列宽
            useExternalSorting : true, // 启用后台排序
            useExternalPagination : true, // 启用后台分页
            paginationPageSizes : [ 10, 20, 30, 50, 100 ], // 可选每页记录数
            paginationPageSize : 10, // 每页记录数
            totalItems : 0, // 总记录数
            orderBy : "", // 自定义属性，用于后台排序的
            queryParams : {}, // 自定义属性，用于后台查询过滤的条件参数
            errorMsg : null, // 自定义属性，用于显示错误信息
            title : "URL列表", // 自定义属性，表格标题
            toolbar : {
                groups : [ {
                    items : [ {
                        id : "add",
                        ico : "plus",
                        text : "增加",
                        click : function(gridOptions, item) {
                            alert("增加");
                        }
                    }, {
                        id : "edit",
                        ico : "pencil",
                        text : "修改",
                        click : function(gridOptions, item) {
                            alert("修改");
                        }
                    }, {
                        id : "remove",
                        ico : "minus",
                        text : "删除",
                        click : function(gridOptions, item) {
                            alert("删除");
                        }
                    } ]
                }, {
                    items : [ {
                        id : "approve",
                        ico : "ok",
                        text : "审核通过",
                        click : function(gridOptions, item) {
                            alert("审核通过");
                        }
                    }, {
                        id : "reject",
                        ico : "remove",
                        text : "审核驳回",
                        click : function(gridOptions, item) {
                            alert("审核驳回");
                        }
                    }, {
                        id : "back",
                        ico : "share-alt",
                        text : "退回新建",
                        click : function(gridOptions, item) {
                            alert("退回新建");
                        }
                    } ]
                }, {
                    items : [ {
                        id : "refresh",
                        ico : "refresh",
                        text : "刷新",
                        click : function(gridOptions, item) {
                            alert("刷新");
                        }
                    }, {
                        id : "export",
                        ico : "floppy-disk",
                        text : "导出",
                        click : function(gridOptions, item) {
                            alert("导出");
                        }
                    }, {
                        id : "print",
                        ico : "print",
                        text : "打印",
                        click : function(gridOptions, item) {
                            alert("打印");
                        }
                    }, {
                        id : "printView",
                        ico : "picture",
                        text : "预览",
                        click : function(gridOptions, item) {
                            alert("预览");
                        }
                    } ]
                } ]
            }, // 自定义属性，表格工具栏
            columnDefs : [
                    {
                        field : 'index',
                        displayName : ' ',
                        cellClass : 'text-right',
                        headerCellClass : 'text-right',
                        enableColumnMenu : false, // 不启用列头菜单
                        enableFiltering : false, // 不启用过滤
                        pinnedLeft : true, // 靠左固定列
                        enableSorting : false, // 不启用排序
                        width : 35
                    },
                    {
                        field : 'f_url',
                        displayName : 'URL',
                        headerCellClass : 'text-center',
                        pinnedLeft : true,// 靠左固定列
                        width : 300
                    },
                    {
                        field : 'f_description',
                        displayName : 'URL描述',
                        headerCellClass : 'text-center',
                        width : 200
                    },
                    {
                        field : 'f_methods',
                        displayName : '提交方式',
                        headerCellClass : 'text-center',
                        width : 100
                    },
                    {
                        field : 'f_handler_method',
                        displayName : '处理方法',
                        headerCellClass : 'text-center',
                        minWidth : 300
                    },
                    {
                        field : 'f_log',
                        displayName : '记录日志',
                        headerCellClass : 'text-center',
                        filterHeaderTemplate : '<div class="ui-grid-filter-container" ng-repeat="colFilter in col.filters"><div my-custom-dropdown></div></div>',
                        filter : {
                            options : [ {
                                id : '0',
                                value : '否'
                            }, {
                                id : '1',
                                value : '是'
                            } ]
                        },
                        cellFilter : 'trueFalseFilter',
                        enableColumnResizing : false,
                        pinnedRight : true, // 靠右固定列
                        enableSorting : false,
                        width : 100
                    }, {
                        field : 'f_auto',
                        displayName : '自动生成',
                        headerCellClass : 'text-center',
                        enableColumnResizing : false,
                        enableSorting : false,
                        width : 100
                    } ],
            exporterCsvFilename : 'URL.csv',
            exporterPdfDefaultStyle : {
                fontSize : 9
            },
            exporterPdfTableStyle : {
                margin : [ 30, 30, 30, 30 ]
            },
            exporterPdfTableHeaderStyle : {
                fontSize : 10,
                bold : true,
                italics : true,
                color : 'red'
            },
            exporterPdfHeader : {
                text : "My Header",
                style : 'headerStyle'
            },
            exporterPdfFooter : function(currentPage, pageCount) {
                return {
                    text : currentPage.toString() + ' of ' + pageCount.toString(),
                    style : 'footerStyle'
                };
            },
            exporterPdfCustomFormatter : function(docDefinition) {
                docDefinition.styles.headerStyle = {
                    fontSize : 22,
                    bold : true
                };
                docDefinition.styles.footerStyle = {
                    fontSize : 10,
                    bold : true
                };
                return docDefinition;
            },
            exporterPdfOrientation : 'portrait',
            exporterPdfPageSize : 'LETTER',
            exporterPdfMaxGridWidth : 500,
            exporterCsvLinkElement : angular.element(document.querySelectorAll(".custom-csv-link-location")),
            exporterAllDataFn : function() {
                return loadGrid($scope.urlGridOptions, true).then(function() {
                    // 导出成功后，要重新把表格的当前页记录加载下
                    loadGrid($scope.urlGridOptions);
                });
            },
            onRegisterApi : function(gridApi) {
                $scope.gridApi = gridApi;

                $scope.gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
                    if (loadGrid) {
                        var orderBy = "";
                        if (sortColumns.length > 0) {
                            angular.forEach(sortColumns, function(data, index, array) {
                                // data等价于array[index]
                                orderBy += "," + data.field + " " + data.sort.direction;
                            });
                            orderBy = orderBy.substring(1);
                        }
                        $scope.urlGridOptions.orderBy = orderBy;

                        loadGrid($scope.urlGridOptions);
                    }
                });

                gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
                    if (loadGrid) {
                        loadGrid($scope.urlGridOptions);
                    }
                });

                $scope.gridApi.core.on.filterChanged($scope, function() {
                    if (loadGrid) {
                        // 过滤条件变了，需要重置记录总数和查询参数
                        $scope.urlGridOptions.totalItems = 0;
                        $scope.urlGridOptions.queryParams = {};

                        // 获取过滤条件到查询参数中
                        angular.forEach(this.grid.columns, function(data, index, array) {
                            if (data.enableFiltering && data.filters[0].term) {
                                $scope.urlGridOptions.queryParams[data.field] = data.filters[0].term;
                            }
                        });

                        loadGrid($scope.urlGridOptions);
                    }
                });
            }
        };

        var loadGrid = function(gridOptions, isExporterAllData) {
            var beginRowNum;
            var params = {};
            if (isExporterAllData) {
                beginRowNum = 0;
            } else {
                params.pageSize = gridOptions.paginationPageSize;
                params.currentPage = gridOptions.paginationCurrentPage || 1;
                params.totalCount = gridOptions.totalItems;
                beginRowNum = (params.currentPage - 1) * params.pageSize;
            }
            params.orderBy = gridOptions.orderBy || '';

            angular.extend(params, gridOptions.queryParams);

            // return urlService.get(params, function(page) {
            return $scope.httpGet(urlService.uri, {
                params : params
            }, function(page) {
                // 追加行号到每条记录中
                angular.forEach(page.currentPageData, function(data, index, array) {
                    data.index = beginRowNum + index + 1;
                });
                // 更新表格的记录总数
                gridOptions.totalItems = page.totalCount;
                // 更新表格当前页的记录
                gridOptions.data = page.currentPageData;
                if (gridOptions.data && gridOptions.data.length) {
                    gridOptions.errorMsg = null;
                } else {
                    gridOptions.errorMsg = "无记录。";
                }
            }, function(page) {
                gridOptions.errorMsg = "后台异常！";
            });
        };

        // 打开时首次加载表格数据
        loadGrid($scope.urlGridOptions);
    };

    UrlController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);

    angular.module("app.filters").filter('trueFalseFilter', function() {
        var trueFalseHash = {
            // '' : '',
            '0' : '否',
            '1' : '是'
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
                            template : '<select ng-model="colFilter.term" ng-options="option.id as option.value for option in colFilter.options"><option value=""></option></select>'
                        };
                    });
}(angular));