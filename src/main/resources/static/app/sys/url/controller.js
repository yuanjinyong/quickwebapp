(function(angular) {
    var UrlController = function($scope, urlService) {
        $qw.dev && console.info('UrlController');

        $scope.feature = {
            id : 'url',
            name : 'URL',
            path : 'app/sys/url',
            service : urlService
        }

        $scope.urlGridOptions = $qw.grid.buildGridOptionsFn($scope, {
            onLoadedSuccess : function(result) {
                this.toolbar.enableItems([ 'add', 'refresh' ]);
            },
            onRowSelectionChanged : function(row) {
                if (!row.isSelected) {
                    this.toolbar.enableItems([ 'add', 'refresh' ]);
                } else {
                    this.toolbar.enableAllItems();
                }
            },
            columnDefs : [ $qw.buildLinkColumnFn({
                field : 'f_url',
                displayName : 'URL',
                cellClass : 'text-left',
                pinnedLeft : true,// 靠左固定列
                width : 300
            }), $qw.buildColumnFn({
                field : 'f_description',
                displayName : 'URL描述',
                cellClass : 'text-left',
                width : 200
            }), $qw.buildColumnFn({
                field : 'f_methods',
                displayName : '提交方式',
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_handler_method',
                displayName : '处理方法',
                cellClass : 'text-left',
                minWidth : 300
            }), $qw.buildDictColumnFn({
                field : 'f_log',
                displayName : '记录日志',
                dictCode : 'TrueFalse',
                enableColumnResizing : false,
                enableSorting : false,
                width : 90
            }), $qw.buildDictColumnFn({
                field : 'f_auto',
                displayName : '自动生成',
                dictCode : 'TrueFalse',
                enableColumnResizing : false,
                enableSorting : false,
                width : 90
            }) ]
        });

        // /////////////////////////////////////////////////////////////////
        // 在已有的组内新增一个按钮
        $scope.urlGridOptions.toolbar.getGroup('refreshGroup').addItem($qw.buildGroupItemFn({
            id : "info",
            ico : "info-sign",
            css : "default",
            text : "查看",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }));
        // 在指定组前插入一组新的按钮
        $scope.urlGridOptions.toolbar.addGroup($qw.buildGroupFn('questionGroup', [ $qw.buildGroupItemFn({
            id : "question1",
            ico : "question-sign",
            css : 'info',
            text : "咨询1",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }), $qw.buildGroupItemFn({
            id : "question2",
            ico : "question-sign",
            css : 'success',
            text : "咨询2",
            click : function(item, gridOptions) {
                alert(item.text);
            }
        }) ]), 0);
        // 显示默认隐藏起来的按钮
        $scope.urlGridOptions.toolbar.getGroup('addGroup').getItem('copy').hide = false;
        // 修改某个按钮的点击响应函数
        $scope.urlGridOptions.toolbar.getGroup('addGroup').getItem('copy').click = function(gridOptions, item) {
            var entity = gridOptions.getSelectedRowFn();
            $qw.dev && console.log(entity);
            gridOptions.toFormFn(item, entity, 'lg');
        };
        $scope.urlGridOptions.toolbar.getGroup('approveGroup').getItem('approve').hide = false;
        $scope.urlGridOptions.toolbar.getGroup('approveGroup').getItem('reject').hide = false;
        $scope.urlGridOptions.toolbar.getGroup('approveGroup').getItem('back').hide = false;
        $scope.urlGridOptions.toolbar.getGroup('refreshGroup').getItem('print').hide = false;
        $scope.urlGridOptions.toolbar.getGroup('refreshGroup').getItem('printview').hide = false;
        // 去掉整个工具栏
        // $scope.urlGridOptions.toolbar = null;
        // /////////////////////////////////////////////////////////////////

        // 打开时首次加载表格数据
        $scope.urlGridOptions.loadFn();
    };

    UrlController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlController", UrlController);

    var UrlFormController = function($scope, urlService) {
        $qw.dev && console.log('UrlFormController');
    };

    UrlFormController.$inject = [ '$scope', 'UrlService' ];
    angular.module("app.controllers").controller("UrlFormController", UrlFormController);
}(angular));