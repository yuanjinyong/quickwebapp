(function(angular) {
    var ApplicationController = function($rootScope, $scope, $location, $route, $cookies, $resource, $timeout,
            i18nService, HttpService, dialogService, GridService, uiGridConstants) {
        $qw.dev && console.info('ApplicationController');

        $qw.route = function(path) {
            if (path != '') {
                $qw.dev && console.info('路由到' + path);
                $location.path(path);
            }
        };

        // =============================================================================================================
        // buildServiceFn begin
        $qw.buildServiceFn = function(uri) {
            var service = $resource(uri + '/:id', {}, {
                update : {
                    method : "PUT"
                },
                remove : {
                    method : "DELETE"
                }
            });
            service.uri = uri;

            return service;
        };
        // buildServiceFn end
        // =============================================================================================================

        // =============================================================================================================
        // GroupItem begin
        $qw.buildGroupItemFn = function(itemOptions, clickFn) {
            return angular.extend({
                id : "_itemId", // 按钮ID
                text : "按钮文本", // 按钮文本
                // ico : null, // 按钮图标。默认是null，search、plus、plus-sign、pencil、minus等字体图标，详细的请查看http://www.w3schools.com/bootstrap/bootstrap_ref_comp_glyphs.asp
                size : "sm", // 按钮大小。lg、md、sm、xs
                css : "primary", // 按钮类别。default、primary、success、info、warning、danger、link
                block : false, // 是否为块级显示。主要为微信界面使用
                // view : "/form.html", // 按钮弹出表单页面path，需要弹出表单的按钮使用该属性。默认是/form.html
                hide : false, // 是否隐藏按钮
                disabled : false, // 是否禁用按钮
                click : clickFn || function(item) {
                    console.error('未添加按钮点击事件的click处理函数！', item);
                }
            }, itemOptions);
        };

        $qw.buildAddItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "add",
                ico : "plus",
                text : "增加",
            }), itemOptions);
        };
        $qw.buildCopyItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "copy",
                ico : "plus-sign",
                text : "复制",
                hide : true,
            }), itemOptions);
        };
        $qw.buildEditItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "edit",
                ico : "pencil",
                text : "修改",
            }), itemOptions);
        };
        $qw.buildRemoveItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "remove",
                ico : "minus",
                css : 'danger',
                text : "删除",
            }), itemOptions);
        };
        $qw.buildApproveItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "approve",
                ico : "ok",
                text : "审核通过",
                hide : true,
            }), itemOptions);
        };
        $qw.buildRejectItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "reject",
                ico : "remove",
                css : 'warning',
                text : "审核驳回",
                hide : true,
            }), itemOptions);
        };
        $qw.buildBackItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "back",
                ico : "share-alt",
                css : 'danger',
                text : "退回新建",
                hide : true,
            }), itemOptions);
        };
        $qw.buildRefreshItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "refresh",
                ico : "refresh",
                text : "刷新",
            }), itemOptions);
        };
        $qw.buildPrintItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "print",
                ico : "print",
                text : "打印",
                hide : true,
            }), itemOptions);
        };
        $qw.buildPrintviewItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "printview",
                ico : "picture",
                text : "预览",
                hide : true,
            }), itemOptions);
        };
        $qw.buildExportItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "export",
                ico : "floppy-disk",
                text : "导出",
            }), itemOptions);
        };

        $qw.buildSubmitItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "submit",
                text : "提交保存"
            }), itemOptions);
        };
        $qw.buildCloseItemFn = function(itemOptions) {
            return angular.extend($qw.buildGroupItemFn({
                id : "close",
                css : 'warning',
                text : "取消关闭"
            }), itemOptions);
        };
        // GroupItem end
        // =============================================================================================================

        // =============================================================================================================
        // Group begin
        $qw.buildGroupFn = function(groupId, items) {
            return {
                id : groupId,
                items : items,
                addItem : function(item, index) {
                    if (index && index < this.items.length) {
                        this.items.splice(index, 0, item);
                    } else {
                        this.items.push(item);
                    }
                    return this;
                },
                getItem : function(itemId) {
                    for ( var i in this.items) {
                        if (this.items[i].id == itemId) {
                            return this.items[i];
                        }
                    }
                    return null;
                },
                disableItem : function(itemId) {
                    for ( var i in this.items) {
                        if (this.items[i].id == itemId) {
                            this.items[i].disabled = true;
                            break;
                        }
                    }
                    return this;
                }
            }
        };
        // Group end
        // =============================================================================================================

        // =============================================================================================================
        // buttonBar begin
        $qw.buildButtonBarFn = function(buttonBarId, groups, align) {
            return {
                id : buttonBarId,
                align : align || 'left',
                groups : groups,
                addGroup : function(group, index) {
                    if (index && index < this.groups.length) {
                        this.groups.splice(index, 0, group);
                    } else {
                        this.groups.push(group);
                    }
                    return this;
                },
                getGroup : function(groupId) {
                    for ( var i in this.groups) {
                        if (this.groups[i].id == groupId) {
                            return this.groups[i];
                        }
                    }
                    return null;
                },
                showItems : function(itemIds) { // 把指定的按钮全部显示出来，指定外的全部隐藏
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            var hide = true;
                            for ( var i in itemIds) {
                                if (itemIds[i] == item.id) {
                                    hide = false;
                                    break;
                                }
                            }
                            item.hide = hide;
                        });
                    });
                },
                enableItems : function(itemIds) { // 把指定的按钮全部启用，指定外的全部禁用
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            var disabled = true;
                            for ( var i in itemIds) {
                                if (itemIds[i] == item.id) {
                                    disabled = false;
                                    break;
                                }
                            }
                            item.disabled = disabled;
                        });
                    });
                },
                enableAllItems : function() { // 把按钮全部启用
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            item.disabled = false;
                        });
                    });
                },
                disableItems : function(itemIds) { // 把指定的按钮全部禁用，指定外的全部启用
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            var disabled = false;
                            for ( var i in itemIds) {
                                if (itemIds[i] == item.id) {
                                    disabled = true;
                                    break;
                                }
                            }
                            item.disabled = disabled;
                        });
                    });
                },
                disableAllItems : function() { // 把按钮全部启用
                    angular.forEach(this.groups, function(group) {
                        angular.forEach(group.items, function(item) {
                            item.disabled = true;
                        });
                    });
                }
            }
        };
        // buttonBar end
        // =============================================================================================================

        // =============================================================================================================
        // Grid begin
        $qw.buildColumnFn = function(columnDef) {
            return angular.extend({
                headerCellClass : 'text-center',
                cellClass : 'text-center'
            }, columnDef);
        };
        $qw.buildLinkColumnFn = function(columnDef) {
            return angular.extend($qw.buildColumnFn({
                cellTemplate : '<qw-grid-link-cell></qw-grid-link-cell>'
            }), columnDef);
        };
        $qw.buildImgColumnFn = function(columnDef) {
            return angular.extend($qw.buildColumnFn({
                cellTemplate : '<qw-grid-img-cell></qw-grid-img-cell>',
                enableFiltering : false, // 启用过滤器
                enableSorting : false, // 不启用排序
            }), columnDef);
        };
        $qw.buildDatetimeColumnFn = function(columnDef) {
            return angular.extend($qw.buildColumnFn({
                cellTemplate : '<qw-grid-datetime-cell></qw-grid-datetime-cell>',
                width : 150
            }), columnDef);
        };
        $qw.buildDictColumnFn = function(columnDef, dictCode) {
            columnDef.dictCode || console.error('请在columnDef指明dictCode属性的值');
            return angular.extend($qw.buildColumnFn({
                // cellTemplate : '<div class="ui-grid-cell-contents">{{grid.appScope.$qw.dict.dicts["'+columnDef.dictCode+'"][grid.getCellValue(row, col)]}}</div>',
                cellFilter : columnDef.dictCode,
                filter : {
                    type : uiGridConstants.filter.SELECT,
                    selectOptions : $qw.dict.dicts[columnDef.dictCode].selectOptions
                },
            // filterHeaderTemplate : '<div class="ui-grid-filter-container" ng-repeat="colFilter in col.filters"><select ng-model="colFilter.term" ng-options="option.value as option.label for option in colFilter.selectOptions"><option value=""></option></select></div>'
            }), columnDef);
        };
        // Grid end
        // =============================================================================================================

        $qw.timeout = $timeout;
        $qw.i18n = i18nService;
        $qw.http = HttpService;
        $qw.dialog = dialogService;
        // $qw.dict = DictService;
        $qw.dict = {
            dicts : {
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
            }
        };
        $qw.grid = GridService;

        angular.$qw = $qw;
        $rootScope.$qw = angular.$qw;

        // 这行打印语句放到最后面
        //$qw.dev && console.info('$rootScope.$qw:', $rootScope.$qw);
        //$qw.dev && console.info('angular.$qw:', angular.$qw);
        $qw.dev && console.info('$qw:', $qw);
    };

    ApplicationController.$inject = [ '$rootScope', '$scope', '$location', '$route', '$cookies', '$resource',
            '$timeout', 'i18nService', 'HttpService', 'DialogService', 'GridService', 'uiGridConstants' ];
    angular.module('app.controllers').controller('ApplicationController', ApplicationController);

}(angular));
