angular.module("myria.message",[])
	.directive('message', function () {
		return {
			restrict: "E",
		    replace: true,
		    transclude: false,
		    scope: {
		    	value:'=value'
		    },
			compile: function (element, attrs) {
				  var html = "<message-" + attrs.value.type + "  id=" + attrs.id + " value='value'></message-" + attrs.value.type + ">";
				  
		         var newElem = angular.element(html);
		         element.replaceWith(newElem);
		         return function ($scope, element, attrs, controller) {
		        	 
		         }
			}
		}
	})
	.directive('message-string', function () {
		return {
			restrict: "E",
		    replace: true,
		    transclude: false,
		    scope: {
		    	value:'=value'
		    }
		}
	})