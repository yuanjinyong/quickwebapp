(function(angular) {
    angular.module("app.directives").directive(
            'qwGridList',
            function() {
                $qw.dev && console.info('qwGridList');

                return {
                    restrict : 'A',
                    replace : true,
                    transclude : true,
                    templateUrl : $qw.getTemplateUrl('template/grid/list.html'),
                    scope : {
                        qwGridOptions : '=qwGridList'
                    },
                    controller : [
                            '$scope',
                            function($scope) {
                                $scope.gridId = $scope.qwGridOptions.feature.id + 'Grid';

                                $scope.getGridBodyHeight = function() {
                                    $scope.qwGridOptions.height = ($scope.qwGridOptions.height || '100%') + '';

                                    if ($scope.qwGridOptions.height.endsWith('px')) {
                                        return {
                                            'height' : $scope.qwGridOptions.height
                                        };
                                    }

                                    if ($scope.qwGridOptions.height.endsWith('%')) {
                                        var height = parseInt($scope.qwGridOptions.height.slice(0, -1));
                                        var heightDiff = $scope.qwGridOptions.heightDiff || 15;

                                        var viewContainerHeight = angular.element(
                                                document.getElementsByClassName('view-container')[0]).prop(
                                                'offsetHeight');
                                        var gridOffsetTop = angular.element(document.getElementById($scope.gridId))
                                                .prop('offsetTop');
                                        var gridHeadHeight = angular.element(
                                                document.getElementById($scope.gridId + 'Head')).prop('offsetHeight');
                                        var gridToolbarHeight = angular.element(
                                                document.getElementById($scope.gridId + 'Toolbar'))
                                                .prop('offsetHeight');

                                        var gridHeight = viewContainerHeight * height / 100 - gridOffsetTop
                                                - heightDiff - 1 - 1; // 一个像素是上下边框

                                        var gridBodyHeight = gridHeight;
                                        if (gridHeadHeight !== undefined) {
                                            gridBodyHeight = gridBodyHeight - gridHeadHeight;
                                        }
                                        if (gridToolbarHeight !== undefined) {
                                            gridBodyHeight = gridBodyHeight - gridToolbarHeight;
                                        }

                                        // $qw.dev
                                        // && console.debug('viewContainerHeight', viewContainerHeight,
                                        // 'gridOffsetTop', gridOffsetTop, 'height', height, 'heightDiff',
                                        // heightDiff, 'gridHeight', gridHeight, 'gridHeadHeight',
                                        // gridHeadHeight, 'gridToolbarHeight', gridToolbarHeight,
                                        // 'gridBodyHeight', gridBodyHeight);

                                        return {
                                            'height' : gridBodyHeight + 'px'
                                        };
                                    }

                                    if ($scope.qwGridOptions.height.endsWith('line')) {
                                        var height = parseInt($scope.qwGridOptions.height.slice(0, -4));
                                        var gridBodyHeight = height * $scope.qwGridOptions.rowHeight + 19; // 19是水平滚动条的高度
                                        if ($scope.qwGridOptions.showHeader) {
                                            gridBodyHeight += 32 - 1;
                                        }
                                        if ($scope.qwGridOptions.enableFiltering) {
                                            gridBodyHeight += 31 - 1;
                                        }
                                        if ($scope.qwGridOptions.enablePagination) {
                                            gridBodyHeight += 32 - 1;
                                        }

                                        return {
                                            'height' : gridBodyHeight + 'px'
                                        };
                                    }

                                    return {
                                        'height' : $scope.qwGridOptions.height + 'px'
                                    };
                                };
                            } ],
                    link : function($scope, $element, $attrs, $ctrl, $transclude) {
                        // $qw.dev && console.debug('qwGridList link');
                        // var qwGridBody = $element.find('.qw-grid-body');
                        // qwGridBody.attr('');
                        // $qw.dev && console.debug('$attrs', $attrs);
                        $scope.$qw = $qw;
                        $transclude(function(clone) {
                            // 这里可以对ng-transclude的内容进行加工后插入到指令的模板元素中。
                            // $qw.dev && console.debug('Clone is:', clone);
                            angular.forEach(clone, function(data, index, array) {
                                var e = array[array.length - 1 - index];
                                var pTag = angular.element(e);
                                // $qw.dev && console.debug('Tag:', pTag);
                                if (pTag.hasClass('qw-grid-insert-before')) {
                                    $element.prepend(pTag);
                                } else if (pTag.hasClass('qw-grid-insert-after')) {
                                    $element.after(pTag);
                                }
                            });
                        });
                    }
                };
            });

    angular.module("app.directives").directive(
            'qwGridTree',
            function() {
                $qw.dev && console.info('qwGridTree');

                return {
                    restrict : 'A',
                    replace : true,
                    transclude : true,
                    templateUrl : $qw.getTemplateUrl('template/grid/tree.html'),
                    scope : {
                        qwGridOptions : '=qwGridTree'
                    },
                    controller : [
                            '$scope',
                            function($scope) {
                                $scope.gridId = $scope.qwGridOptions.feature.id + 'Grid';

                                $scope.getGridBodyHeight = function() {
                                    $scope.qwGridOptions.height = ($scope.qwGridOptions.height || '100%') + '';

                                    if ($scope.qwGridOptions.height.endsWith('px')) {
                                        return {
                                            'height' : $scope.qwGridOptions.height
                                        };
                                    }

                                    if ($scope.qwGridOptions.height.endsWith('%')) {
                                        var height = parseInt($scope.qwGridOptions.height.slice(0, -1));
                                        var heightDiff = $scope.qwGridOptions.heightDiff || 15;

                                        var viewContainerHeight = angular.element(
                                                document.getElementsByClassName('view-container')[0]).prop(
                                                'offsetHeight');
                                        var gridOffsetTop = angular.element(document.getElementById($scope.gridId))
                                                .prop('offsetTop');
                                        var gridHeadHeight = angular.element(
                                                document.getElementById($scope.gridId + 'Head')).prop('offsetHeight');
                                        var gridToolbarHeight = angular.element(
                                                document.getElementById($scope.gridId + 'Toolbar'))
                                                .prop('offsetHeight');

                                        var gridHeight = viewContainerHeight * height / 100 - gridOffsetTop
                                                - heightDiff - 1 - 1; // 一个像素是上下边框

                                        var gridBodyHeight = gridHeight;
                                        if (gridHeadHeight !== undefined) {
                                            gridBodyHeight = gridBodyHeight - gridHeadHeight;
                                        }
                                        if (gridToolbarHeight !== undefined) {
                                            gridBodyHeight = gridBodyHeight - gridToolbarHeight;
                                        }

                                        // $qw.dev
                                        // && console.debug('viewContainerHeight', viewContainerHeight,
                                        // 'gridOffsetTop', gridOffsetTop, 'height', height, 'heightDiff',
                                        // heightDiff, 'gridHeight', gridHeight, 'gridHeadHeight',
                                        // gridHeadHeight, 'gridToolbarHeight', gridToolbarHeight,
                                        // 'gridBodyHeight', gridBodyHeight);

                                        return {
                                            'height' : gridBodyHeight + 'px'
                                        };
                                    }

                                    if ($scope.qwGridOptions.height.endsWith('line')) {
                                        var height = parseInt($scope.qwGridOptions.height.slice(0, -4));
                                        var gridBodyHeight = height * $scope.qwGridOptions.rowHeight + 19; // 19是水平滚动条的高度
                                        if ($scope.qwGridOptions.showHeader) {
                                            gridBodyHeight += 32 - 1;
                                        }
                                        if ($scope.qwGridOptions.enableFiltering) {
                                            gridBodyHeight += 31 - 1;
                                        }
                                        if ($scope.qwGridOptions.enablePagination) {
                                            gridBodyHeight += 32 - 1;
                                        }

                                        return {
                                            'height' : gridBodyHeight + 'px'
                                        };
                                    }

                                    return {
                                        'height' : $scope.qwGridOptions.height + 'px'
                                    };
                                };
                            } ],
                    link : function($scope, $element, $attrs, $ctrl, $transclude) {
                        $scope.$qw = $qw;
                        $transclude(function(clone) {
                            // 这里可以对ng-transclude的内容进行加工后插入到指令的模板元素中。
                            // $qw.dev && console.debug('Clone is:', clone);
                            angular.forEach(clone, function(data, index, array) {
                                var e = array[array.length - 1 - index];
                                var pTag = angular.element(e);
                                // $qw.dev && console.debug('Tag:', pTag);
                                if (pTag.hasClass('qw-grid-insert-before')) {
                                    $element.prepend(pTag);
                                } else if (pTag.hasClass('qw-grid-insert-after')) {
                                    $element.after(pTag);
                                }
                            });
                        });
                    }
                };
            });

    angular.module("app.directives").directive('qwGridEdit', function() {
        $qw.dev && console.info('qwGridEdit');

        return {
            restrict : 'A',
            replace : true,
            transclude : true,
            templateUrl : $qw.getTemplateUrl('template/grid/edit.html'),
            scope : {
                qwGridOptions : '=qwGridEdit'
            },
            controller : [ '$scope', function($scope) {
                $scope.getGridBodyHeight = function() {
                    var rowCount = $scope.qwGridOptions.height || 10;
                    var height = rowCount * $scope.qwGridOptions.rowHeight + 19;
                    if ($scope.qwGridOptions.showHeader) {
                        height += 32;
                    }
                    if ($scope.qwGridOptions.enableFiltering) {
                        height += 31;
                    }
                    if ($scope.qwGridOptions.enablePagination) {
                        height += 32;
                    }

                    return {
                        'height' : height + 'px'
                    };
                };
            } ],
            link : function($scope, $element, $attrs, $ctrl, $transclude) {
                $scope.$qw = $qw;
                $transclude(function(clone) {
                    // 这里可以对ng-transclude的内容进行加工后插入到指令的模板元素中。
                    // $qw.dev && console.debug('Clone is:', clone);
                    angular.forEach(clone, function(data, index, array) {
                        var e = array[array.length - 1 - index];
                        var pTag = angular.element(e);
                        // $qw.dev && console.debug('Tag:', pTag);
                        if (pTag.hasClass('qw-grid-insert-before')) {
                            $element.prepend(pTag);
                        } else if (pTag.hasClass('qw-grid-insert-after')) {
                            $element.after(pTag);
                        }
                    });
                });
            }
        };
    });

    angular.module("app.directives").directive('qwGridDatetimeCell', function() {
        var html = '';
        html += '<div class="ui-grid-cell-contents">';
        html += '    <span>{{grid.getCellValue(row, col) | date:"yyyy-MM-dd HH:mm:ss"}}</span>';
        html += '</div>';
        return {
            restrict : 'E',
            replace : true,
            template : html
        };
    });

    angular
            .module("app.directives")
            .directive(
                    'qwGridLinkCell',
                    function() {
                        var html = '';
                        html += '<div class="ui-grid-cell-contents">';
                                html += '    <a href="" ng-click="grid.options.toFormFn({id:\'view\',ico:\'info-sign\',text:\'查看\'}, row.entity)">{{grid.getCellValue(row, col)}}</a>',
                                html += '</div>';
                        return {
                            restrict : 'E',
                            replace : true,
                            template : html
                        };
                    });

    angular.module("app.directives").directive('qwGridImgCell', function() {
        var html = '';
        html += '<div class="ui-grid-cell-contents">';
        html += '    <img class="qw-grid-cell-img" ng-src="{{grid.getCellValue(row, col)}}" lazy-src></img>';
        html += '</div>';
        return {
            restrict : 'E',
            replace : true,
            template : html
        };
    });

    angular.module("app.directives").directive('qwGridDropdownFilter', function() {
        var html = '';
        html += '<div class="ui-grid-filter-container" ng-repeat="colFilter in col.filters">';
        html += '    <select ng-model="colFilter.term" ';
        html += '        ng-options="option.id as option.text for option in colFilter.options">';
        html += '        <option value=""></option>';
        html += '    </select>';
        html += '</div>';
        return {
            restrict : 'E',
            replace : true,
            template : html
        };
    });
}(angular));