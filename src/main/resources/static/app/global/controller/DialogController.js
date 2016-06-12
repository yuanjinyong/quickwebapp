(function(angular) {
    var DialogController = function($scope, $uibModalInstance, formOptions) {
        $qw.dev && console.info('DialogController');

        // 在html中使用FormController的entity，formOptions下的entity作为备份的旧数据
        $scope.formTemplateUrl = formOptions.getFormTemplateUrl();
        $scope.formOptions = formOptions;
        $scope.entity = angular.copy(formOptions.entity);

        if ($scope.formOptions.toolbarItem.id == 'add') {
            $scope.entity = {};
        } else if ($scope.formOptions.toolbarItem.id == 'copy') {
            $scope.entity.f_id = null;
        } else if ($scope.formOptions.toolbarItem.id == 'view') {
            $scope.formOptions.footbar.enableItems([ 'close' ]);
        }

        $qw.dev
                && console.debug($scope.formOptions.toolbarItem.text + $scope.formOptions.gridOptions.feature.name
                        + '， 原始数据为：', formOptions.entity);

        // 右上角的X按钮点击事件响应函数
        $scope.dismiss = function() {
            var footbarItem = $qw.buildGroupItemFn({
                id : "dismiss",
                text : "返回关闭"
            });
            $scope.formOptions.dismissFn(footbarItem, $scope.formOptions, $scope.entity);
        };

        // 点击对话框标题栏，拖动。
        $scope.startToMove = function(mouseDownEvent) {
            var modalHeader = angular.element(mouseDownEvent.target);
            modalHeader.css('cursor', 'move');
            modalHeader.addClass('qw-unselectable');
            modalHeader.attr('unselectable', 'on');

            var modal = modalHeader.parent().parent().parent().parent();
            var top = modal.css('top');
            var left = modal.css('left');
            top = top == '' ? 0 : parseInt(top.substring(0, top.length - 2));
            left = left == '' ? 0 : parseInt(left.substring(0, left.length - 2));

            modal.bind('mousemove', function(mouseMoveEvent) {
                modal.css({
                    top : (top + mouseMoveEvent.clientY - mouseDownEvent.clientY) + 'px',
                    left : (left + mouseMoveEvent.clientX - mouseDownEvent.clientX) + 'px'
                });
            });
            modal.bind('mouseup', function(mouseUpEvent) {
                modal.unbind('mousemove');
                modal.unbind('mouseup');
                modalHeader.css('cursor', 'default');
                modalHeader.addClass('qw-unselectable');
                modalHeader.removeAttr('unselectable');
            });
            modal.bind('mouseout', function(mouseOutEvent) {
                modal.unbind('mousemove');
                modal.unbind('mouseup');
                modalHeader.css('cursor', 'default');
                modalHeader.removeClass('qw-unselectable');
                modalHeader.removeAttr('unselectable');
            });
        };
    };

    angular.module("app.controllers").controller("DialogController", DialogController);
}(angular));