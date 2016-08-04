(function(angular) {
    var MemberInfoController = function($scope, memberInfoService) {
        $qw.dev && console.info('MemberInfoController');

        $scope.feature = {
            id : 'memberInfo',
            name : '会员信息',
            path : 'app/stzj/member/info',
            service : memberInfoService
        }

        $scope.memberGridOptions = $qw.grid.buildGridOptionsFn($scope, {
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
                field : 'f_openid',
                displayName : '微信Open ID',
                width : 240
            }), $qw.buildColumnFn({
                field : 'f_nickname',
                displayName : '用户昵称',
                cellClass : 'text-left',
                pinnedLeft : true,// 靠左固定列
                width : 160
            }), $qw.buildImgColumnFn({
                field : 'f_headimgurl',
                displayName : '用户头像',
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_name',
                displayName : '真实姓名',
                width : 90
            }), $qw.buildColumnFn({
                field : 'f_phone',
                displayName : '手机号码',
                width : 110
            }), $qw.buildDatetimeColumnFn({
                field : 'f_first_login_time',
                displayName : '注册时间',
            }), $qw.buildDatetimeColumnFn({
                field : 'f_latest_login_time',
                displayName : '最近访问时间',
            }) ]
        });

        $scope.memberGridOptions.loadFn();
    };

    MemberInfoController.$inject = [ '$scope', 'MemberInfoService' ];
    angular.module("app.controllers").controller("MemberInfoController", MemberInfoController);

    var MemberInfoFormController = function($scope, memberInfoService) {
        $qw.dev && console.log('MemberInfoFormController');
    };

    MemberInfoFormController.$inject = [ '$scope', 'MemberInfoService' ];
    angular.module("app.controllers").controller("MemberInfoFormController", MemberInfoFormController);
}(angular));