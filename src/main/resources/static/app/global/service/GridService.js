(function(angular) {
    var GridService = function() {
        $qw.dev && console.info('GridService');

        var service = {
            buildGridOptionsFn : function($scope, customGridOptions) {
                var gridOptions = {
                    feature : $scope.feature || {},
                    showRowNumber : true, // 自定义属性，默认显示行号
                    beginRowNum : 0, // 自定义属性，后台查询起始行号
                    orderBy : '', // 自定义属性，用于后台排序的
                    queryParams : {}, // 自定义属性，用于后台查询过滤的条件参数
                    errorMsg : null, // 自定义属性，用于显示错误信息
                    exporterCsvFilename : $scope.feature.name + '.csv',
                    loadFn : function(params, isExporterAllData) {
                        var gridOptions = this;

                        var queryParams = {};
                        if (isExporterAllData) {
                            gridOptions.beginRowNum = 0;
                        } else {
                            queryParams.pageSize = gridOptions.paginationPageSize;
                            queryParams.currentPage = gridOptions.paginationCurrentPage || 1;
                            queryParams.totalCount = gridOptions.totalItems;
                            gridOptions.beginRowNum = (queryParams.currentPage - 1) * queryParams.pageSize;
                        }
                        queryParams.beginRowNum = gridOptions.beginRowNum;
                        queryParams.orderBy = gridOptions.orderBy || '';

                        angular.extend(queryParams, gridOptions.queryParams);
                        if (params) {
                            angular.extend(queryParams, params);
                        }

                        return $qw.http.get(gridOptions.feature.service.uri, {
                            params : queryParams
                        }, function(result) {
                            // 更新表格的相关数据
                            if (result.currentPageData && result.currentPageData.length > 0) {
                                gridOptions.totalItems = result.totalCount;
                                gridOptions.data = result.currentPageData;
                                gridOptions.errorMsg = null;
                            } else {
                                gridOptions.totalItems = 0;
                                gridOptions.data = [];
                                gridOptions.errorMsg = "无记录。";
                            }
                            // 调用成功的回调函数
                            if (gridOptions.onLoadedSuccess) {
                                gridOptions.onLoadedSuccess(result);
                            } else {
                                $qw.dev && console.debug('可以通过添加gridOptions.onLoadedSuccess(result)来接收加载数据成功事件');
                            }
                        }, function(result) {
                            gridOptions.totalItems = 0;
                            gridOptions.data = [];
                            gridOptions.errorMsg = '未知异常。';
                            if (result) {
                                if (angular.isString(result)) {
                                    gridOptions.errorMsg = result;
                                } else if (angular.isObject(result)) {
                                    if (result.msg) {
                                        gridOptions.errorMsg = result.msg;
                                    } else if (result.status === 404) {
                                        gridOptions.errorMsg = result.path + ' ' + result.error;
                                    } else {
                                        gridOptions.errorMsg = angular.toJson(result);
                                    }
                                }
                            }
                            // 调用失败的回调函数
                            if (gridOptions.onLoadedError) {
                                gridOptions.onLoadedError(result);
                            } else {
                                $qw.dev && console.debug('可以通过添加gridOptions.onLoadedError(result)来接收加载数据失败事件');
                            }
                        });
                    },
                    toFormFn : function(toolbarItem, entity, size) {
                        var gridOptions = this;
                        gridOptions.formDialogOptions = $qw.dialog.buildDialogOptionsFn({ // formOptions
                            gridOptions : gridOptions,
                            toolbarItem : toolbarItem,
                            entity : entity || {}
                        }, { // dialogOptions
                            size : size || 'lg'
                        }).openFn(function(result) {
                            if (gridOptions.onFormClosed) {
                                gridOptions.onFormClosed(result);
                            } else {
                                $qw.dev && console.debug('可以通过添加gridOptions.onFormClosed(result)来接收表单关闭事件');
                            }
                        }, function(result) {
                            if (gridOptions.onFormDismiss) {
                                gridOptions.onFormDismiss(result);
                            } else {
                                $qw.dev && console.debug('可以通过添加gridOptions.onFormDismiss(result)来接收表单返回事件');
                            }
                        });
                    },
                    toRemoveFn : function(toolbarItem, gridOptions) {
                        var formOptions = {
                            gridOptions : gridOptions,
                            toolbarItem : toolbarItem
                        };

                        $qw.dialog.buildYesNoDialogFn(formOptions, '是否删除所选' + gridOptions.feature.name + '?').openFn(
                                function(result) {
                                    var entity = gridOptions.getSelectedRowFn();
                                    gridOptions.feature.service.remove({
                                        id : entity.f_id
                                    }, null, function(result, putResponseHeaders) {
                                        $qw.dev && console.debug('记录删除result：', {
                                            entity : entity,
                                            result : result,
                                            putResponseHeaders : putResponseHeaders
                                        });

                                        if (result.state) {
                                            // 刷新表单数据
                                            gridOptions.loadFn();
                                            $qw.dialog.buildSuccessDialogFn(formOptions, result.msg).openFn();
                                            return;
                                        } else {
                                            $qw.dialog.buildErrorDialogFn(formOptions, result.msg).openFn();
                                        }
                                    });
                                });
                    },
                    toAuditFn : function(toolbarItem, gridOptions, isApprove) {
                        alert(isApprove ? "还未实现审核通过" : "还未实现审核驳回");
                    },
                    toBackFn : function(toolbarItem, gridOptions) {
                        alert("还未实现退回新建");
                    },
                    toPrintFn : function(toolbarItem, gridOptions, isPrintview) {
                        alert(isPrintview ? "还未实现打印" : "还未实现打印预览");
                    },
                    toExportFn : function(toolbarItem, gridOptions) {
                        alert("还未实现导出");
                    },
                    getSelectedRowFn : function() {
                        var selectedRows = this.gridApi.selection.getSelectedRows();
                        if (selectedRows && selectedRows.length > 0) {
                            return selectedRows[0];
                        }
                        return null;
                    },
                    getSelectedRowsFn : function() {
                        return this.gridApi.selection.getSelectedRows();
                    }
                };

                // 自定义属性，表格工具栏
                var addItem = $qw.buildAddItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                    }
                });
                var copyItem = $qw.buildCopyItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                    }
                });
                var editItem = $qw.buildEditItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toFormFn(item, gridOptions.getSelectedRowFn());
                    }
                });
                var removeItem = $qw.buildRemoveItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toRemoveFn(item, gridOptions);
                    }
                });
                var approveItem = $qw.buildApproveItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toAuditFn(item, gridOptions, true);
                    }
                });
                var rejectItem = $qw.buildRejectItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toAuditFn(item, gridOptions, false);
                    }
                });
                var backItem = $qw.buildBackItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toBackFn(item, gridOptions);
                    }
                });
                var exportItem = $qw.buildExportItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toExportFn(item, gridOptions);
                    }
                });
                var printItem = $qw.buildPrintItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toPrintFn(item, gridOptions, false);
                    }
                });
                var printviewItem = $qw.buildPrintviewItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.toPrintFn(item, gridOptions, true);
                    }
                });
                var refreshItem = $qw.buildRefreshItemFn({
                    click : function(item, gridOptions) {
                        gridOptions.loadFn();
                    }
                });
                gridOptions.toolbar = $qw.buildButtonBarFn('gridToolbar', [
                        $qw.buildGroupFn('addGroup', [ addItem, copyItem, editItem, removeItem ]),
                        $qw.buildGroupFn('approveGroup', [ approveItem, rejectItem, backItem ]),
                        $qw.buildGroupFn('refreshGroup', [ exportItem, printItem, printviewItem, refreshItem ]) ]);

                angular
                        .extend(
                                gridOptions,
                                {
                                    enableRowHeaderSelection : true, // 启用行首的勾选功能功能
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
                                        columns : [ 'Left part', {
                                            text : 'Right part',
                                            alignment : 'right'
                                        } ]
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
                                    //exporterPdfOrientation : 'landscape', //'landscape' or 'portrait', Defaults to landscape 
                                    //exporterPdfPageSize : 'A4', // Defaults to A4
                                    //exporterPdfMaxGridWidth : 720, // Defaults to 720 (for A4 landscape), use 670 for LETTER
                                    exporterCsvLinkElement : angular.element(document
                                            .querySelectorAll(".custom-csv-link-location")),
                                    exporterAllDataFn : function() {
                                        var gridOptions = this;
                                        return gridOptions.loadFn(null, true).then(function() {
                                            // 导出成功后，要重新把表格的当前页记录加载下
                                            gridOptions.loadFn();
                                        });
                                    },
                                    onRegisterApi : function(gridApi) {
                                        this.gridApi = gridApi;

                                        if (gridApi.grid.options.showRowNumber) {
                                            gridApi.core
                                                    .addRowHeaderColumn({
                                                        name : 'f_row_number',
                                                        displayName : '',
                                                        cellClass : 'text-right',
                                                        cellTemplate : '<div class="ui-grid-cell-contents">{{grid.options.beginRowNum + grid.renderContainers.body.visibleRowCache.indexOf(row) + 1}}</div>',
                                                        width : 35,
                                                    });
                                        }

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

                                        if (gridApi.pagination) {
                                            gridApi.pagination.on.paginationChanged($scope,
                                                    function(newPage, pageSize) {
                                                        this.grid.options.loadFn();
                                                    });
                                        }

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
                                        gridApi.selection.on
                                                .rowSelectionChanged(
                                                        $scope,
                                                        function(row) {
                                                            $qw.dev && console.debug('Row data is:', row);
                                                            var gridOptions = row.grid.options;
                                                            if (gridOptions.onRowSelectionChanged) {
                                                                gridOptions.onRowSelectionChanged(row);
                                                            } else {
                                                                $qw.dev
                                                                        && console
                                                                                .debug('可以通过添加gridOptions.onRowSelectionChanged(row)来接收行选择变更事件');
                                                            }
                                                        });
                                    }
                                }, customGridOptions);

                return gridOptions;
            }
        };

        return service;
    };

    // GridService.$inject = [ 'HttpService', 'DialogService' ];
    angular.module("app.services").factory("GridService", GridService);
}(angular));