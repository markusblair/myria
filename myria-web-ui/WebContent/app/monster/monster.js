angular.module("myria.monster",['myria.command'])
	.directive('monsterDisplay', ['commandService', function (commandService) {
	   return {
		      restrict: "E",
		      scope:{
		    	  monster: '=monster'
		      },
		      templateUrl: './app/monster/monster.html',
		      controller: function($scope) {
				  $scope.healthBarType = function() {
					  var percent = $scope.monster.health/$scope.monster.maxHealth;
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
		      }
		   };
		}])
;