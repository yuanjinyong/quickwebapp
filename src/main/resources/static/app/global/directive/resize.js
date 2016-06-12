(function(angular) {
    angular.module("app.directives").directive('qwResize', function($window) {
        $qw.dev && console.info('qwResize');

        return function($scope, $element) {
            $scope.getWindowDimensions = function() {
                return {
                    innerHeight : $window.innerHeight,
                    innerWidth : $window.innerWidth
                };
            };
            $scope.$watch($scope.getWindowDimensions, function(newValue, oldValue) {
                $qw.window.innerWidth = newValue.innerWidth;
                $qw.window.innerHeight = newValue.innerHeight;

                $scope.getMiddleContainerStyle = function() {
                    var header = document.querySelector('.header-container');
                    var footer = document.querySelector('.footer-container');
                    // console.info('header', header.clientHeight, 'footer', footer.clientHeight);
                    return {
                        'position' : 'absolute',
                        'top' : header.clientHeight + 'px',
                        'bottom' : footer.clientHeight + 'px',
                        'height' : 'auto'
                    };
                };
                $scope.getFormBodyStyle = function() {
                    return {
                        'overflow-y' : 'auto',
                        'max-height' : (newValue.innerHeight - 180) + 'px'
                    };
                };

                $qw.timeout(function() {
                    var modal = $element.parent().parent().parent();
                    var top = modal.css('top');
                    top = top == '' ? 0 : parseInt(top.substring(0, top.length - 2));
                    top = (newValue.innerHeight - modal.prop('clientHeight') - 60) / 2;
                    modal.css('top', top + 'px');
                });
            }, true);

            var w = angular.element($window);
            w.bind('resize', function() {
                $scope.$apply();
            });
        }
    });
}(angular));