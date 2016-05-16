(function(angular) {
    // *****************************************************************************************************************
    // DictController begin
    var DictController = function($scope, dictService) {
        $qw.dev && console.info('DictController');

        $scope.feature = {
            id : 'dict',
            name : '字典',
            path : 'app/sys/dict',
            service : dictService
        }

        $scope.dictGridOptions = $qw.grid.buildGridOptionsFn($scope, {
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
            onFormClosed : function(result) {
                $qw.dev && console.debug('可以到这里添加刷新页面上其他表格。result:', result);
            },
            columnDefs : [ $qw.buildLinkColumnFn({
                field : 'f_station_no',
                displayName : '搅拌站编号',
                pinnedLeft : true,// 靠左固定列
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_station_name',
                displayName : '搅拌站名称',
                cellClass : 'text-left',
                pinnedLeft : true,// 靠左固定列
                width : 160
            }), $qw.buildColumnFn({
                field : 'f_visit_url',
                displayName : '外网访问地址',
                cellClass : 'text-left',
                enableSorting : false, // 不启用排序
                width : 260
            }), $qw.buildColumnFn({
                field : 'f_province',
                displayName : '所属省份',
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_city',
                displayName : '所属城市',
                width : 100
            }), $qw.buildColumnFn({
                field : 'f_address',
                displayName : '地址',
                cellClass : 'text-left',
                width : 200
            }), $qw.buildColumnFn({
                field : 'f_linker',
                displayName : '联系人',
                filter : {
                    term : '二'
                },
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_link_phone',
                displayName : '联系电话',
                width : 110
            }), $qw.buildColumnFn({
                field : 'f_secret_key',
                displayName : '加密秘钥2',
                width : 150
            }), $qw.buildDictColumnFn({
                field : 'f_status',
                displayName : '启用',
                dictCode : 'TrueFalse',
                width : 70
            }), $qw.buildColumnFn({
                field : 'f_remark',
                displayName : '备注',
                cellClass : 'text-left',
                width : 300
            }) ]
        });

        $scope.dictGridOptions.toolbar.showItems([ 'add', 'copy', 'edit', 'remove', 'export', 'refresh' ]);

        $scope.dictGridOptions.loadFn();
    };

    DictController.$inject = [ '$scope', 'DictService' ];
    angular.module("app.controllers").controller("DictController", DictController);
    // DictController end
    // *****************************************************************************************************************

    // *****************************************************************************************************************
    // DictFormController begin
    var DictFormController = function($scope, dictService) {
        $qw.dev && console.log('DictFormController');

        if ($scope.formOptions.toolbarItem.id == 'copy') {
            $scope.entity.f_id = null;
            $scope.entity.f_station_no = null;
        }
    };

    DictFormController.$inject = [ '$scope', 'DictService' ];
    angular.module("app.controllers").controller("DictFormController", DictFormController);
    // DictFormController end
    // *****************************************************************************************************************
}(angular));