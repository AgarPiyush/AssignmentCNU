'use strict';
var app = angular.module('myApp', ['ngRoute',
    'ngStorage'
]);

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
    when('/products/:param', {
        controller: 'singleProduct',
        templateUrl: 'templates/product.html'
        }).
    when('/products',
        {
            controller: 'allProduct',
            templateUrl: 'templates/allproducts.html'
        }).
    when('/category_products/:param',
        {
            controller: 'categoryContoller',
            templateUrl: 'templates/categoryProducts.html'
        }).

      when('/viewcart/',
        {
            controller: 'cartController',
            templateUrl: 'templates/cart.html'
        }).
    when('/checkout/',
        {
            controller: 'checkoutController',
            templateUrl: 'templates/formUser.html'
        }).
    when('/orderSummary/',
         {
             controller: 'summaryController',
             templateUrl: 'templates/orderSummary.html'

         }).
      when('/contactus/',
          {
              controller: 'contactus',
              templateUrl: 'templates/contactus.html',

          })
      .otherwise({ redirectTo: '/products'});
}]);

// For display all product
app.controller('MainController', function($scope, $http, $localStorage) {
    $scope.cartCount=function () {
       //TODO
        if($localStorage == undefined || $localStorage.countCart == undefined) {
            $localStorage.countCart = 0;
            console.log("Count cart zero");

            return $localStorage.countCart;
        }
        else {
            console.log("Count cart");
            return $localStorage.countCart;
        }
    };
});

app.controller('allProduct', function($scope, $http, $localStorage) {
  $http.get("http://localhost:8000/api/products/")
      .then(function(response) {
          console.log("All products");

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
        $scope.product_name = response.data.data;
          console.log($scope.product_name);

      });
        $scope.addToCart = function(product_name) {
            if($localStorage.orderId == undefined) {
                console.log("New order created");
                $http.post("http://localhost:8000/api/orders/")
                    .then(function (response) {
                        $scope.newOrder = response.data.data;
                        $localStorage.orderId = $scope.newOrder;
                        console.log($localStorage.orderId);
                        var k = '{ "product_id":'+product_name.id+', "price":'+ product_name.price + ',"order_id":' + $localStorage.orderId.id +',"qty":'+ $scope.qty +'}';
                        $http.post("http://localhost:8000/api/orders/"+$localStorage.orderId.id+"/orderLineItem/",k)
                            .then(function (response) {
                                console.log("Product added to order");
                        });
                    });
                $localStorage.countCart = 1;
            }
            else {
                var k = '{ "product_id":' + product_name.id + ', "price":' + product_name.price + ',"order_id":' + $localStorage.orderId.id + ',"qty":' + $scope.qty + '}';
                $http.post("http://localhost:8000/api/orders/" + $localStorage.orderId.id + "/orderLineItem/", k)
                    .then(function (response) {
                        console.log("Product added to order hey ");
                        $localStorage.countCart += 1;
                    });
            }
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
    if($localStorage.orderId != undefined) {
        $http.get("http://127.0.0.1:8000/api/orders/" + $localStorage.orderId.id + "/orderLineItem")
            .then(function (response) {
                console.log(("http://127.0.0.1:8000/api/orders/" + $localStorage.orderId.id + "/orderLineItem"));
                $scope.cartProducts = response.data.data;
                var total = 0;
                for (var i = 0; i < $scope.cartProducts.length; i++) {
                    var product = $scope.cartProducts[i];
                    total += (product.price * product.qty);
                }
                $localStorage.countCart = $localStorage.cartProducts.length;
                $scope.total = total;
            });

        $http.get("http://localhost:8000/api/products/")
            .then(function (responseProduct) {
                $scope.products = responseProduct.data.data;
                $scope.verify_product();
            });
        $scope.verify_product = function () {
            $scope.linktoroute = 'checkout';
            $scope.valid = 1;

            console.log("Inside verify products");
            for (var i = 0; i < $scope.products.length; i++) {
                var proDatabase = $scope.products[i];
                var total_quantity = 0;
                for (var j = 0; j < $scope.cartProducts.length; j++) {
                    var productCheckout = $scope.cartProducts[j];
                    if (productCheckout.product_id == proDatabase.id) {
                        total_quantity = total_quantity + productCheckout.qty;
                    }
                }
                if (total_quantity > proDatabase.qty) {
                    console.log("Not valid");
                    $scope.valid = 0;
                    $scope.linktoroute = 'viewcart';
                }
            }

        };
        $scope.deleteproduct = function (productInCart) {
            var i = $scope.cartProducts.indexOf(productInCart);
            $scope.cartProducts.splice(i, 1);
            $localStorage.countCart = $localStorage.countCart - 1;
            $scope.total = $scope.total - productInCart.price * productInCart.qty;
            $http.delete("http://127.0.0.1:8000/api/orders/" + productInCart.order_id + "/orderLineItem/" + productInCart.id + "/");
            $scope.verify_product();
        };
    }
});




app.controller("checkoutController", function($scope, $http,$localStorage) {
        $scope.master = {};
        $scope.update = function(user) {
            console.log("Inside checkout")
            $scope.master = angular.copy(user);
            var k = '{ "pk":'+$localStorage.orderId.id+', "username":"'+ $scope.master.username + '","address":"' + $scope.master.address +'","status":"Completed"}';
            $http.patch("http://localhost:8000/api/orders/"+$localStorage.orderId.id+"/",k);
            // window.alert("Order Placed");
            // window.location = "http://localhost:9000#orderSummary";
        };
});



app.controller("contactus", function($scope, $http,$localStorage) {
    $scope.master = {};
    $scope.contact = function() {
        window.alert("We will get back to you as soon as possible");
        window.location = "http://localhost:9000#products";
    };
    $(document).ready(function(){
        $('button').click(function(){
            $('.alert').show()
        })
    });
});




app.controller("summaryController", function($scope, $http,$localStorage) {
    console.log("Inside");
    if($localStorage.orderId != undefined) {
        console.log("Final");
        console.log($localStorage);
        console.log($localStorage.orderId);
        $http.get("http://127.0.0.1:8000/api/orders/" + $localStorage.orderId.id + "/orderLineItem")
             .then(function (response) {
                 console.log(("http://127.0.0.1:8000/api/orders/" + $localStorage.orderId.id + "/orderLineItem"));
                 $scope.cartProducts = response.data.data;
                 var total = 0;
                 for (var i = 0; i < $scope.cartProducts.length; i++) {
                     var product = $scope.cartProducts[i];
                     total += (product.price * product.qty);
                 }
                 $localStorage.countCart = $scope.cartProducts.length;
                 $scope.total = total;
                 $localStorage.$reset();
             });
        }
});

