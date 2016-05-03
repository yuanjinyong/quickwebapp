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
}(angular));