angular.module("myria.items",['myria.command'])
	.directive('itemDisplay', ['commandService','$interpolate', function (commandService,  $interpolate) {
	   return {
		      restrict: "E",
		      scope:{
		    	  item: '='
		      },
		      templateUrl: './app/items/item.html',
		      link: function(scope, element, attrs) {
		    	  scope.isContainer = function() {
		    		  return (scope.item && scope.item.items.length > 0);
		    	  }
		    	  scope.command = function() {
		    		  var cmd = scope.action.replace('<', '{{');
		    		  cmd = cmd.replace('>', '}}');
		    		  var parsedExp = $interpolate(cmd);
                      var actionStr = scope.$eval(parsedExp)
		    		  commandService.command(actionStr);
		    	  }
		    	  
		      }
		   };
		}])
;