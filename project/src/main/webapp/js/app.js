var adminApp = angular.module('adminApp', [
    'ngRoute',
    'adminControllers'
]);

adminApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
                when('/recettes', {
                    templateUrl: '/admin/partial/receipe/list.html',
                    controller: 'RecetteListCtrl'
                }).
                when('/recettes/:phoneId', {
                    templateUrl: '/admin/partial/receipe/form.html',
                    controller: 'RecetteCtrl'
                }).
                otherwise({
                    redirectTo: '/admin'
                });
    }]);
