angular.module("myria.command",[])
	.factory('commandService', ['$http',function($http) {
		return {
			command : function(cmd) {
				$http({method: 'POST', url: '/myria/game/command/', data:cmd});
			}
		};
	
	}])
	.directive('command', ['commandService', function (commandService) {
		   return {
			      restrict: "E",
			      replace: true,
			      transclude: false,
			      compile: function (element, attrs) {
			     
			         var html = "<input style='100%' type='text' id='" + attrs.id + "'>" +
			            "</input>";

			         var newElem = angular.element(html);
			         element.replaceWith(newElem);

			         return function ($scope, element, attrs, controller) {
			        	 $scope.commands= [];
			        	 $scope.commandIndex = 0;
			        	
			        	 element.bind('keydown', function(event) {
			     			if (event.keyCode == 38 || event.keyCode == 40) {
			    				if (event.keyCode == 38) {
			    					$scope.commandIndex = $scope.commandIndex+1;
			    					if ($scope.commandIndex >= $scope.commands.length) {
			    						$scope.commandIndex = $scope.commands.length -1;
			    					}
			    				} else if (event.keyCode == 40) {
			    					$scope.commandIndex = $scope.commandIndex-1;
			    					if ($scope.commandIndex == -1) {
			    						$scope.commandIndex = 0;
			    					}
			    				}
			    				 element.attr('placeholder', $scope.commands[$scope.commandIndex]);
			    			}
			     			else if (event.keyCode == 13) {
			     				$scope.commandIndex = 0;
			     				if (element.val() != "") {
			     					pushCommand(element.val());
			     					element.attr('placeholder', element.val());
			     					element.val('');			     					
			     				} else {
			     					element.val(element.attr('placeholder'));
			     				};
			     			};
			    		});
			    		
			        	 var pushCommand = function(cmd) {
			        		 commandService.command(cmd);
			        		 var cmdIndex = $scope.commands.indexOf(cmd);
			        		if (cmdIndex > -1) {
			        			$scope.commands.splice(cmdIndex, 1);
			        		} 
			        		$scope.commands.splice(0, 0, element.val());
			        	 };
			         };

			      }
			   };
			}])
;