'use strict';
var app = angular.module('myApp', ['ngRoute',
    'ngStorage'
]);

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/products/:param', {
        controller: 'singleProduct',
        templateUrl: 'templates/product.html',
    }).
    when('/products',
          {
            controller: 'allProduct',
            templateUrl: 'templates/allproducts.html',
          }).
    when('/category_products/:param',
        {
            controller: 'categoryContoller',
            templateUrl: 'templates/categoryProducts.html',
        }).

      when('/viewcart/',
           {
               controller: 'cartController',
               templateUrl: 'templates/cart.html',
           }).
        when('/checkout/',
        {
                controller: 'checkoutController',
                 templateUrl: 'templates/formUser.html'
        })
         .otherwise({ redirectTo: '/products'});
}]);

// For display all product

app.controller('allProduct', function($scope, $http) {
  $http.get("http://localhost:8000/api/products/")
      .then(function(response) {
        $scope.myWelcome = response.data.data;
      });
  $http.get("http://localhost:8000/api/category/")
      .then(function(response) {
        $scope.myCategory = response.data.data;
      });
});

// Dispaly Single Product
app.controller("singleProduct", function($scope, $http, $routeParams, $localStorage) {

    $http.get("http://127.0.0.1:8000/api/products/"+$routeParams.param)
      .then(function (response) {
        $scope.product_name = response.data.data;});

        $scope.addToCart = function(product_name) {
            if($localStorage.orderId == undefined) {
                console.log("New order created");
                $http.post("http://localhost:8000/api/orders/")
                    .then(function (response) {
                        $scope.newOrder = response.data.data;
                        $localStorage.orderId = $scope.newOrder;
                        console.log($localStorage.orderId);
                    });
                 }
            console.log($scope);
            var k = '{ "product_id":'+product_name.id+', "price":'+ product_name.price + ',"order_id":' + $localStorage.orderId.id +',"qty":'+ $scope.qty +'}';
            $http.post("http://localhost:8000/api/orders/"+$localStorage.orderId.id+"/orderLineItem/",k)
            .then(function (response) {
                    console.log("Product added to order");
                    window.alert("Added to cart")
            });
        };
});

// filter by category
app.controller("categoryContoller", function($scope, $http, $routeParams) {
  $http.get("http://127.0.0.1:8000/api/category_products/"+$routeParams.param)
      .then(function (response) {
        $scope.category_products = response.data.data;});

  $http.get("http://localhost:8000/api/category/")
      .then(function(response) {
          $scope.myCategory = response.data.data;
      });
});




// order filter
app.controller("cartController", function($scope, $http,$localStorage) {
    $http.get("http://127.0.0.1:8000/api/orders/"+$localStorage.orderId.id+"/orderLineItem")
        .then(function (response) {
            $scope.cartProducts = response.data.data;
            var total = 0;
            for(var i = 0; i < $scope.cartProducts.length; i++){
                var product = $scope.cartProducts[i];
                total += (product.price * product.qty);
            }
            console.log(total);
            $scope.total = total;
        });
        $scope.valid = 1;
        $scope.linktoroute = 'checkout';

        $http.get("http://localhost:8000/api/products/")
            .then(function (responseProduct) {
                $scope.products = responseProduct.data.data;

                for(var i=0; i<$scope.products.length; i++)
                {
                    var proDatabase = $scope.products[i];
                    var total_quantity = 0;
                    for(var j =0; j<$scope.cartProducts.length; j++)
                    {
                        var productCheckout = $scope.cartProducts[j];
                        if(productCheckout.product_id == proDatabase.id)
                        {
                            total_quantity = total_quantity + productCheckout.qty;
                        }
                    }
                    if(total_quantity > proDatabase.qty)
                    {
                        console.log("Not valid");
                        $scope.valid = 0;
                        // TODO add link
                        // $scope.linktoroute = 'viewcart';
                    }
                }
            });
        console.log("valid is "+$scope.valid);



        $scope.deleteproduct = function (productInCart) {
            console.log(productInCart);
            console.log("http://127.0.0.1:8000/api/orders/"+productInCart.order_id+"/orderLineItem/"+productInCart.id+"/");
            $http.delete("http://127.0.0.1:8000/api/orders/"+productInCart.order_id+"/orderLineItem/"+productInCart.id+"/");
        };
});
        // $scope.verify = function() {
        //     console.log("HeyMe");
        //     $http.get("http://localhost:8000/api/products/")
        //     .then(function (responseProduct) {
        //         $scope.products = responseProduct.data.data;
        //
        //         for(var i=0; i<$scope.products.length; i++)
        //         {
        //             var proDatabase = $scope.products[i];
        //             var total_quantity = 0;
        //             for(var j =0; j<$scope.cartProducts.length; j++)
        //             {
        //                 var productCheckout = $scope.cartProducts[j];
        //                 if(productCheckout.product_id == proDatabase.id)
        //                 {
        //                     total_quantity = total_quantity + productCheckout.qty;
        //                 }
        //             }
        //             if(total_quantity > proDatabase.qty)
        //             {
        //                 console.log("Not valid");
        //                 $scope.valid = 0;
        //                 // TODO add link
        //                 $scope.linktoroute = 'viewcart';
        //             }
        //         }
        //     });
        //     console.log("valid is "+$scope.valid);
        // };
// });



app.controller("checkoutController", function($scope, $http,$localStorage) {


        // $scope.master = {};
        // $scope.update = function(user) {
        //     $scope.master = angular.copy(user);
        //     var k = '{ "pk":'+$localStorage.orderId.id+', "username":"'+ $scope.master.username + '","address":"' + $scope.master.address +'","status":"Completed"}';
        //     $http.patch("http://localhost:8000/api/orders/"+$localStorage.orderId.id+"/",k).

});


