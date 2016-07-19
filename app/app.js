'use strict';
var app = angular.module('myApp', ['ngRoute']);

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/products/:param', {
        controller: 'RouteController',
        templateUrl: 'product.html',
    }).
      when('/products',
          {
            controller: 'myCtrl',
            templateUrl: 'allproducts.html',
          }).
      when('/category_products/:param',
        {
          controller: 'categoryContoller',
          templateUrl: 'categoryProducts.html',
        })
        .otherwise({ redirectTo: '/products'});
}]);


app.controller('myCtrl', function($scope, $http) {
  $http.get("http://localhost:8000/api/products/")
      .then(function(response) {
        $scope.myWelcome = response.data.data;
      });
  $http.get("http://localhost:8000/api/category/")
      .then(function(response) {
        $scope.myCategory = response.data.data;
      });
});

app.controller("RouteController", function($scope, $http, $routeParams) {
  $http.get("http://127.0.0.1:8000/api/products/"+$routeParams.param)
      .then(function (response) {
        $scope.product_name = response.data.data;});
});


app.controller("categoryContoller", function($scope, $http, $routeParams) {
  $http.get("http://127.0.0.1:8000/api/category_products/"+$routeParams.param)
      .then(function (response) {
        $scope.category_products = response.data.data;});

  $http.get("http://localhost:8000/api/category/")
      .then(function(response) {
        $scope.myCategory = response.data.data;
      });
});