(function(angular) {
    angular.module("app.directives").directive('qwTree', function($window) {
        $qw.dev && console.info('qwTree');

        return {
            restrict : 'A',
            templateUrl : $qw.getTemplateUrl('template/tree/tree.html'),
            scope : {
                treeOptions : '=qwTree'
            },
            controller : [ '$scope', function($scope) {
                $scope.treeOptions.getCheckedNodes = function() {
                    var checkedNodes = [];
                    $qw.treeNodeForEach($scope.treeOptions.data, function(node) {
                        if (node.checked) {
                            checkedNodes.push(node);
                        }
                    });
                    return checkedNodes;
                };
                $scope.treeOptions.getCheckedNodeValues = function() {
                    var nodeValues = [];
                    $qw.treeNodeForEach($scope.treeOptions.data, function(node) {
                        if (node.checked) {
                            nodeValues.push(node[$scope.treeOptions.valueField]);
                        }
                    });
                    return nodeValues;
                };

                $scope.nodeExpanded = function(node, $event) {
                    node.expanded = !node.expanded;
                    // $event.stopPropagation();
                    ($scope.treeOptions['nodeExpandedChanged'] || angular.noop)($scope.treeOptions, node, $event);
                };

                $scope.getNodeIcon = function(node) {
                    if ($scope.isLeaf(node)) {
                        return 'glyphicon glyphicon-leaf';
                    }

                    return node.expanded ? 'glyphicon glyphicon-minus' : 'glyphicon glyphicon-plus';
                };

                $scope.isLeaf = function(node) {
                    return !node.children || !node.children.length;
                };
                $scope.hasChildren = function(node) {
                    return node.children && node.children.length;
                };

                $scope.setParentChecked = function(node) {
                    if (node.checked) {
                        if (node.$$parent) {
                            node.$$parent.checked = true;
                            $scope.setParentChecked(node.$$parent);
                        }
                    } else {
//                        if (node.$$parent) {
//                            for (var i = 0; i < node.$$parent.children.length; i++) {
//                                if (node.$$parent.children[i].checked) {
//                                    node.$$parent.checked = true;
//                                    return;
//                                }
//                            }
//                            node.$$parent.checked = false;
//                            $scope.setParentChecked(node.$$parent);
//                        }
                    }
                };

                $scope.warpCallback = function(callback, node, $event) {
                    if (callback === 'nodeCheckedChanged') {
                        if ($scope.treeOptions.autoCheckChildren) {
                            $qw.treeNodeForEach(node.children, function(child) {
                                child.checked = node.checked;
                            });
                        }
                        if ($scope.treeOptions.autoCheckParent) {
                            $scope.setParentChecked(node);
                        }
                    }

                    ($scope.treeOptions[callback] || angular.noop)($scope.treeOptions, node, $event);
                };

                // 初始化数据
                if ($scope.treeOptions.checkable === undefined) {
                    $scope.treeOptions.checkable = true;
                }
                if ($scope.treeOptions.autoCheckParent === undefined) {
                    $scope.treeOptions.autoCheckParent = true;
                }
                if ($scope.treeOptions.autoCheckChildren === undefined) {
                    $scope.treeOptions.autoCheckChildren = true;
                }
                if ($scope.treeOptions.nodeTemplateUrl === undefined) {
                    $scope.treeOptions.nodeTemplateUrl = $qw.getTemplateUrl('template/tree/node.html')();
                }
            } ]
        };
    });
}(angular));