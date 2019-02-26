var app = angular.module('app',['ngRoute']);

app.config(function($routeProvider, $locationProvider)
{
   // remove o # da url
	$locationProvider.html5Mode({
	  enabled: true,
	  requireBase: false
	});

   $routeProvider

    // para a rota '/produtos', carregaremos o template listAllProducts.html e o controller 'ProductsController'
   .when('/store_wine/produtos', {
      templateUrl : 'resources/views/listProducts.html',
      controller  : 'ListProductsViewController',
   })
   
    // para a rota '/produtos', carregaremos o template insertProduct.html e o controller 'InsertProductViewController'
   .when('/store_wine/insereProduto', {
      templateUrl : 'resources/views/insertProduct.html',
      controller  : 'InsertProductViewController',
   })
   
   // para a rota '/produtos', carregaremos o template insertProduct.html e o controller 'InsertProductViewController'
   .when('/store_wine/carrinho', {
      templateUrl : 'resources/views/cart.html',
      controller  : 'CartViewController',
   })
   
    // para a rota '/confirmacao', carregaremos o template confirmation.html e o controller 'ConfirmationViewController'
   .when('/store_wine/confirmacao', {
      templateUrl : 'resources/views/confirmation.html',
      controller  : 'ConfirmationViewController',
   })
   
   // caso não seja nenhum desses, redirecione para a rota '/store_wine/produtos'
   .otherwise ({ redirectTo: '/store_wine/produtos' });
});