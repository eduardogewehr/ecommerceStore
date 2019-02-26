// Controller com os métodos de inserir e consultar produtos
app.controller('ProductsController', function($scope, $http) {

	$scope.listAllProducts = function() {
		$http({
			method : 'GET',
			url : '/store_wine/rest/product'
		}).then(function(response) {
			$scope.response = response;
		}, function(error) {
			$scope.response = error;
		});
	};

	$scope.listProductById = function(product) {
		$http({
			method : 'GET',
			url : '/store_wine/rest/product/' + product.id
		}).then(function(response) {
			$scope.response = response;
		}, function(error) {
			$scope.response = error;
		});
	};

	$scope.insertProduct = function(product) {

		$http({
			method : 'POST',
			data : product,
			url : '/store_wine/rest/product'
		}).then(function(response) {
			$scope.response = response;
		}, function(error) {
			$scope.response = error;
		});
	};
});