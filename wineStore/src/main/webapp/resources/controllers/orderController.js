// Controller com os métodos de controle de pedido
app.controller('OrderController', function($location, $scope, $http) {

	$scope.getCurrentOrder = function() {
		$http({
			method : 'GET',
			async : true,
			cache : false,
			url : '/wineStore/rest/order/current'
		}).then(function(response) {
			$scope.currentOrder = response.data;
		}, function(error) {
			$scope.currentOrder = error;
		});
	};

	$scope.getLastFinishOrder = function() {
		$http({
			method : 'GET',
			async : true,
			cache : false,
			url : '/wineStore/rest/order/last'
		}).then(function(response) {
			$scope.lastFinishOrder = response.data;
		}, function(error) {
			$scope.lastFinishOrder = error;
		});
	};

	$scope.createOrder = function() {

		order = new Object();

		order.state = 'new';
		order.freightValue = 0;
		order.distance = 0;
		order.totalProductsValue = 0;

		$http({
			method : 'POST',
			data : order,
			url : '/wineStore/rest/order/'
		}).then(function(response) {
			$scope.currentOrder = response.data;
		}, function(error) {
			$scope.currentOrder = error;
		});
	};

	$scope.addProductToOrder = function(item) {

		item.orderId = $scope.currentOrder.orderId;

		$http({
			method : 'POST',
			data : item,
			url : '/wineStore/rest/order/item'
		}).then(function(response) {
			$scope.addProduct = response;
		}, function(error) {
			$scope.addProduct = error;
		});
	};

	$scope.calculateFreight = function(freight) {

		freight.orderId = $scope.currentOrder.orderId;

		$http({
			method : 'POST',
			data : freight,
			url : '/wineStore/rest/order/freight'
		}).then(function(response) {
			$scope.freightValue = response.data;
			$scope.getCurrentOrder();
		}, function(error) {
			$scope.freightValue = error;
		});

	};

	$scope.finishOrder = function() {

		orderStatus = new Object();

		orderStatus.orderId = $scope.currentOrder.orderId;
		orderStatus.state = 'finished';

		$http({
			method : 'PUT',
			data : orderStatus,
			url : '/wineStore/rest/order/update'
		}).then(function(response) {
			$scope.createOrder();
			$location.path('/wineStore/confirmacao');
		}, function(error) {
			$scope.finishOrder = error;
		});
	};

	$scope.deleteItemOrder = function(id) {

		console.log(id)

		$http({
			method : 'DELETE',
			url : '/wineStore/rest/order/item/' + id
		}).then(function(response) {
			$scope.removeItem = response;
			$scope.getCurrentOrder();
		}, function(error) {
			$scope.removeItem = error;
		});
	};

});
