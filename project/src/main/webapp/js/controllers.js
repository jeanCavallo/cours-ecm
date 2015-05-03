var adminControllers = angular.module('adminControllers', []);

adminControllers.controller('RecetteListCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.currentPage = 0;
    $scope.items = [];

    $http.get('phones/phones.json').success(function(data) {
        $scope.items = data;
    });
}]);

adminControllers.controller('RecetteCtrl', ['$scope', '$routeParams', function ($scope, $routeParams) {

}]);