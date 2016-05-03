(function(angular) {
    var UrlController = function($scope, $log, urlService, dictService) {
        console.log('UrlController');

        $scope.dictService = dictService;
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
            enableFullRowSelection : true, // 启用单击行选中功能
            multiSelect : false, // 禁用多选
            enableGridMenu : true, // 启用表格右上角菜单
            enableFiltering : true, // 启用过滤器
            enableColumnResizing : true, // 启用调整列宽
            useExternalSorting : true, // 启用后台排序
            useExternalPagination : true, // 启用后台分页
            paginationPageSizes : [ 10, 20, 30, 50, 100 ], // 可选每页记录数
            paginationPageSize : 10, // 每页记录数
            totalItems : 0, // 总记录数
            orderBy : '', // 自定义属性，用于后台排序的
            queryParams : {}, // 自定义属性，用于后台查询过滤的条件参数
            errorMsg : null, // 自定义属性，用于显示错误信息
            id : 'urlGrid', // 自定义属性，表格ID
            title : 'URL列表', // 自定义属性，表格标题
            url : urlService.uri, // 自定义属性，从后台加载表格数据记录时使用的URL地址
            loadFn : function(params, isExporterAllData) {
                console.log(this);
                var gridOptions = this;
                var beginRowNum;

                var queryParams = {};
                if (isExporterAllData) {
                    beginRowNum = 0;
                } else {
                    queryParams.pageSize = gridOptions.paginationPageSize;
                    queryParams.currentPage = gridOptions.paginationCurrentPage || 1;
                    queryParams.totalCount = gridOptions.totalItems;
                    beginRowNum = (queryParams.currentPage - 1) * queryParams.pageSize;
                }
                queryParams.orderBy = gridOptions.orderBy || '';

                angular.extend(queryParams, gridOptions.queryParams);
                if (params) {
                    angular.extend(queryParams, params);
                }

                return $scope.httpGet(gridOptions.url, {
                    params : queryParams
                }, function(page) {
                    // 追加行号到每条记录中
                    angular.forEach(page.currentPageData, function(data, index, array) {
                        data.index = beginRowNum + index + 1;
                    });
                    // 更新表格的记录总数
                    gridOptions.totalItems = page.totalCount;
                    // 更新表格当前页的记录
                    gridOptions.data = page.currentPageData;
                    // 刷新工具栏
                    if (gridOptions.toolbar && gridOptions.toolbar.updateFn) {
                        gridOptions.toolbar.updateFn(null);
                    }

                    if (gridOptions.data && gridOptions.data.length) {
                        gridOptions.errorMsg = null;
                    } else {
                        gridOptions.errorMsg = "无记录。";
                    }
                }, function(page) {
                    gridOptions.errorMsg = "后台异常！";
                });
            }, // 自定义函数，从后台加载表格数据记录
            toolbar : {
                updateFn : function(row) {
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            if (item.id == 'edit') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'remove') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'approve') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'reject') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'back') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'print') {
                                item.disabled = !row || !row.isSelected;
                            } else if (item.id == 'printview') {
                                item.disabled = !row || !row.isSelected;
                            }
                        });
                    });

                    try {
                        $scope.$apply(); // this triggers a $digest
                    } catch (e) {
                        console.error(e);
                    }
                },
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
                            gridOptions.loadFn();
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
                        id : "printview",
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
                        cellTemplate : '<div class="ui-grid-cell-contents"><a href="" ng-click="showDetail()">{{COL_FIELD}}</a></div>',
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
                        cellClass : 'text-center',
                        headerCellClass : 'text-center',
                        cellTemplate : '<div class="ui-grid-cell-contents">{{grid.appScope.dictService.dicts.trueFalse[row.entity.f_log]}}</div>',
                        cellFilter : 'TrueFalseFilter',
                        filter : {
                            options : dictService.dicts.trueFalse.options
                        },
                        filterHeaderTemplate : '<div grid-filter-dropdown></div>',
                        enableColumnResizing : false,
                        enableSorting : false,
                        width : 100
                    },
                    {
                        field : 'f_auto',
                        displayName : '自动生成',
                        cellClass : 'text-center',
                        headerCellClass : 'text-center',
                        cellTemplate : '<div class="ui-grid-cell-contents">{{grid.appScope.dictService.dicts.trueFalse[row.entity.f_auto]}}</div>',
                        cellFilter : 'TrueFalseFilter',
                        filter : {
                            term : '', // 默认选中值
                            options : dictService.dicts.trueFalse.options
                        },
                        filterHeaderTemplate : '<div grid-filter-dropdown></div>',
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
                var gridOptions = this;
                return gridOptions.loadFn(null, true).then(function() {
                    // 导出成功后，要重新把表格的当前页记录加载下
                    gridOptions.loadFn();
                });
            },
            onRegisterApi : function(gridApi) {
                gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
                    var orderBy = "";
                    if (sortColumns.length > 0) {
                        angular.forEach(sortColumns, function(data, index, array) {
                            orderBy += "," + data.field + " " + data.sort.direction;
                        });
                        orderBy = orderBy.substring(1);
                    }
                    this.grid.options.orderBy = orderBy;

                    this.grid.options.loadFn();
                });

                gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
                    this.grid.options.loadFn();
                });

                gridApi.core.on.filterChanged($scope, function() {
                    var grid = this.grid;
                    var gridOptions = grid.options;

                    // 过滤条件变了，需要重置记录总数和查询参数
                    gridOptions.totalItems = 0;
                    gridOptions.queryParams = {};

                    // 获取过滤条件到查询参数中
                    angular.forEach(grid.columns, function(data, index, array) {
                        if (data.enableFiltering && data.filters[0].term) {
                            gridOptions.queryParams[data.field] = data.filters[0].term;
                        }
                    });

                    gridOptions.loadFn();
                });

                // /////
                gridApi.selection.on.rowSelectionChanged($scope, function(row) {
                    $log.log(row);
                    // $log.log('Row data: ' + JSON.stringify(row.entity));
                    row.grid.options.toolbar.updateFn(row);
                });
            }
        };

        // 打开时首次加载表格数据
        $scope.urlGridOptions.loadFn();
    };

    UrlController.$inject = [ '$scope', '$log', 'UrlService', 'DictService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);
}(angular));