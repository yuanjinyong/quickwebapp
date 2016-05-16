(function(angular) {
    var TrueFalseFilter = function(dictService) {
        return function(input) {
            if (!input) {
                return '';
            } else {
                return dictService.dicts.trueFalse[input];
            }
        };
    };

    TrueFalseFilter.$inject = [ 'DictService' ];
    angular.module("app.filters").filter('TrueFalseFilter', TrueFalseFilter);

    angular.module("app.filters").filter('trueFalse', function() {
        return function(input) {
            if (!input) {
                return '';
            } else {
                return $qw.dict.dicts['trueFalse'][input];
            }
        };
    });
    angular.module("app.filters").filter('TrueFalse', function() {
        return function(input) {
            if (input == undefined || input == null) {
                return '';
            } else {
                return $qw.dict.dicts['TrueFalse'][input];
            }
        };
    });

    // /////////////////////////////////////////////
    var MenuType = function() {
        return function(input) {
            if (input == undefined || input == null) {
                return '';
            } else {
                return $qw.dict.dicts['MenuType'][input];
            }
        };
    };

    angular.module("app.filters").filter('MenuType', MenuType);
    // ///////////////////////////////////////////////
}(angular));