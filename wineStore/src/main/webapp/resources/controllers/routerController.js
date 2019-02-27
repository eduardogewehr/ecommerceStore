app.controller('ListProductsViewController', function($rootScope, $location) {
	$rootScope.active = $location.path();

});

app.controller('InsertProductViewController', function($rootScope, $location) {
	$rootScope.active = $location.path();
});

app.controller('CartViewController', function($rootScope, $location) {
	$rootScope.active = $location.path();
});

app.controller('ConfirmationViewController', function($rootScope, $location) {
	$rootScope.active = $location.path();
});
