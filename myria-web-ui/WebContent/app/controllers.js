angular.module("myria.controllers", [])
.controller("mainCtrl", function($scope, $http,  $route) {
	$scope.name = "mainCtrl";
})
.controller("gameCtrl", function($scope, $http,  $route, commandService, messageService) {
	$scope.model = messageService.model;

	messageService.onMessage = function() {
		$scope.$apply(messageService.model);
		$scope.$apply(messageService.model.room.characterMap);
	};

	$scope.healthBarType = function() {
		var percent = $scope.model.character.health/$scope.model.character.maxHealth.stat;
		if (percent > 0.75) {
			return 'success';
		}
		if (percent > 0.50) {
			return 'info';
		}
		if (percent > 0.25) {
			return 'warning';
		}
		return 'danger';
	}

	$scope.mindBarType = function() {
		var percent = $scope.model.character.mind/$scope.model.character.maxMind;

		if (percent > 0.95) {
			return 'danger';
		}
		if (percent > 0.33) {
			return 'info';
		}
		return 'success';
	}

	$scope.manaBarType = function() {
		var percent = $scope.model.character.mana/$scope.model.character.maxMana;

		if (percent > 0.95) {
			return 'success';
		}
		if (percent > 0.33) {
			return 'info';
		}
		return 'danger';
	}


	$scope.energyBarType = function() {
		var percent = $scope.model.character.energy/$scope.model.character.maxEnergy;

		if (percent > 0.95) {
			return 'success';
		}
		if (percent > 0.33) {
			return 'info';
		}
		return 'danger';
	}

	$scope.energyReserveBarType = function() {
		var percent = $scope.model.character.energyReserve/$scope.model.character.maxEnergyReserve;

		if (percent > 0.95) {
			return 'success';
		}
		if (percent > 0.33) {
			return 'info';
		}
		return 'danger';
	}

	$scope.command = function(cmd) {
		commandService.command(cmd);
	};

	$http({method: 'GET', url: '/myria/game/character/' + $route.current.params.cid})
		.success(function(data, status, headers, config) {
			$scope.model.character = data;
			$http({method: 'GET', url: '/myria/game/room'})
			.success(function(data, status, headers, config) {
				$scope.model.room = data;
			});
			
		});

	messageService.initSockets();

})
.controller("accountCtrl", function($scope, $http, $route, $location) {
	$scope.model = {
			characters:[],
			character:{
				name:"",
				race:"",
				str:0,
				agi:0,
				spe:0,
				int:0,
				sta:0
			}
	}
	
	$scope.load = function() {
		$http({method: 'GET', url: '/myria/character/list'})
		.success(function(data) {
			$scope.model.characters = data;
		})
		.error(function(data) {
			$scope.model.characters = ["Houston we have a problem."];
		});
	}
	
	$scope.setCharacter = function(cid) {
		$scope.model.cid = cid;
		$location.path('/game/' + cid)
		
	};
	
	$scope.deleteCharacter = function(cid) {
		$http({method: 'GET', url: '/myria/character/delete/' + cid}).
		success(function(data, status, headers, config) {
			$scope.load();
		});
	};
	
	$scope.createCharacter = function() {
		$http({method: "post", url:'/myria/character/create/', data:$scope.model.character}).
		success(function(data, status, headers, config) {
			$scope.load();
		});
	};
	
	$scope.characterEditor = function() {
		$location.path('/characterEdit');
	};
	
	$scope.load();
})
;