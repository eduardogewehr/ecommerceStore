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
   .when('/wineStore/produtos', {
      templateUrl : 'resources/views/listProducts.html',
      controller  : 'ListProductsViewController',
   })
   
    // para a rota '/produtos', carregaremos o template insertProduct.html e o controller 'InsertProductViewController'
   .when('/wineStore/insereProduto', {
      templateUrl : 'resources/views/insertProduct.html',
      controller  : 'InsertProductViewController',
   })
   
   // para a rota '/produtos', carregaremos o template insertProduct.html e o controller 'InsertProductViewController'
   .when('/wineStore/carrinho', {
      templateUrl : 'resources/views/cart.html',
      controller  : 'CartViewController',
   })
   
    // para a rota '/confirmacao', carregaremos o template confirmation.html e o controller 'ConfirmationViewController'
   .when('/wineStore/confirmacao', {
      templateUrl : 'resources/views/confirmation.html',
      controller  : 'ConfirmationViewController',
   })
   
   // caso não seja nenhum desses, redirecione para a rota '/wineStore/produtos'
   .otherwise ({ redirectTo: '/wineStore/produtos' });
});