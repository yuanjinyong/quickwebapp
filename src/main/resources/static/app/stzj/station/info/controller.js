(function(angular) {
    // *****************************************************************************************************************
    // StationInfoController begin
    var StationInfoController = function($scope, stationInfoService) {
        $qw.dev && console.info('StationInfoController');

        $scope.feature = {
            id : 'stationInfo',
            name : '站点信息',
            path : 'app/stzj/station/info',
            service : stationInfoService
        }

        $scope.stationGridOptions = $qw.grid.buildGridOptionsFn($scope, {
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
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_link_phone',
                displayName : '联系电话',
                width : 110
            }), $qw.buildColumnFn({
                field : 'f_secret_key',
                displayName : '加密秘钥',
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

        $scope.stationGridOptions.toolbar.showItems([ 'add', 'copy', 'edit', 'remove', 'export', 'refresh' ]);

        $scope.stationGridOptions.loadFn();
    };

    StationInfoController.$inject = [ '$scope', 'StationInfoService' ];
    angular.module("app.controllers").controller("StationInfoController", StationInfoController);
    // StationInfoController end
    // *****************************************************************************************************************

    // *****************************************************************************************************************
    // StationInfoFormController begin
    var StationInfoFormController = function($scope, stationInfoService) {
        $qw.dev && console.log('StationInfoFormController');

        if ($scope.formOptions.toolbarItem.id == 'copy') {
            $scope.entity.f_id = null;
            $scope.entity.f_station_no = null;
        }
    };

    StationInfoFormController.$inject = [ '$scope', 'StationInfoService' ];
    angular.module("app.controllers").controller("StationInfoFormController", StationInfoFormController);
    // StationInfoFormController end
    // *****************************************************************************************************************
}(angular));