(function(angular) {
    angular.module("app.directives").directive('qwGridList', function() {
        $qw.dev && console.info('qwGridList');

        return {
            restrict : 'A',
            replace : true,
            transclude : true,
            scope : {
                qwGridOptions : '=qwGridList'
            },
            templateUrl : 'template/grid/list.html',
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

    angular.module("app.directives").directive('qwGridTree', function() {
        $qw.dev && console.info('qwGridTree');

        return {
            restrict : 'A',
            replace : true,
            transclude : true,
            scope : {
                qwGridOptions : '=qwGridTree'
            },
            templateUrl : 'template/grid/tree.html',
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