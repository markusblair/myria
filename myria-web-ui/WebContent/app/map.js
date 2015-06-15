new function() {
	var map = angular.module('wow.map',[]);
	map.controller('mapController', function($scope, tileService) {
		$scope.character = {name:"Joe Guy of a long name player", 
							percentHealth:67, 
							postuer:'standing', 
							x:2, y:2};
		$scope.monsters = [{name:"Orc", percentHealth:34, postuer:'standing', x:2, y:4}];
		$scope.tiles = [
				{x:0,y:0,terrain:"grass"},{x:1,y:0,terrain:"grass"},{x:2,y:0,terrain:"hills"},{x:3,y:0,terrain:"mtn"},
				{x:0,y:1,terrain:"hills"},{x:1,y:1,terrain:"hills"},{x:2,y:1,terrain:"mtn"},{x:3,y:1,terrain:"mtn"},
				{x:0,y:2,terrain:"grass"},{x:1,y:2,terrain:"grass"},{x:2,y:2,terrain:"hills"},{x:3,y:2,terrain:"mtn"},
				{x:1,y:3,terrain:"grass"},{x:2,y:3,terrain:"grass"},{x:1,y:4,terrain:"hills"},{x:2,y:4,terrain:"mtn"},
				];
		$scope.terrainClick = function($event) {
			var location = tileService.calculateTileCoord($event.clientX, $event.clientY);
			$scope.character.x = location.x;
			$scope.character.y = location.y;
		};
	});
	
	map.factory('tileService', function($log) {
		var tileService = {};
		
		tileService.selectedLocation = {x:0,y:0};
		tileService.tileHeight = 50;
		tileService.tileWidth = 50;
		tileService.calculateTileLocation = function(x, y) {
			var location = {};
			location.x = x * this.tileHeight;
			location.y = y * this.tileWidth;
			
			return location;
		}
		
		tileService.calculateTileCoord = function(clientX, clientY) {
			var newX,newY = 0;
			newX = (clientX - (clientX % this.tileHeight)) / this.tileHeight;
			newY = (clientY - (clientY % this.tileWidth)) / this.tileWidth;
			
			return {x:newX,y:newY};
		}
		
		tileService.selectTile = function(clientX, clientY) {
			var tileCoord = tileService.calculateTileCoord(clientX, clientY);
			if (tileCoord.x != tileService.selectedLocation.x || tileCoord.y != tileService.selectedLocation.y) {
				tileService.selectedLocation = tileCoord
				$log.log("Clicked coordinates (" + tileService.selectedLocation.x + ", " + tileService.selectedLocation.y + ") ");
			}
			return tileCoord;
		};
				
		tileService.tileMap = {};	
		tileService.getTileKey = function(tile) {
			var x = tile.x >= 0 ? tile.x : tile.data('x');
			var y = tile.y >= 0 ? tile.y : tile.data('y');
			var tileKey = x + '-' + y;
			return tileKey;
		};		
		tileService.addTile = function(tile) {
			var tileKey = tileService.getTileKey(tile);
			if (tileService.tileMap[tileKey] == undefined) {
				tileService.tileMap[tileKey]  = [];
			}
			tileService.tileMap[tileKey].push(tile);
		};
		tileService.getSelectedTiles = function() {
			var location = tileService.selectedLocation;
			var tileKey = tileService.getTileKey(location);
			var tiles = tileService.tileMap[tileKey];
			return tiles;
		};
		tileService.getTiles = function(location) {
			var tileKey = tileService.getTileKey(location);
			var tiles = tileService.tileMap[tileKey];
			return tiles;
		};
		tileService.getTiles = function(clientX, clientY) {
			var location = tileService.calculateTileCoord(clientX, clientY);
			var tileKey = tileService.getTileKey(location);
			var tiles = tileService.tileMap[tileKey];
			return tiles;
		};
		return tileService;
	});
	
	map.directive('selectedTile', function($log, tileService) {
		return {
			restrict:"C",
			link: function($scope, $elem, $attrs) {
					var x = $attrs.mapX;
					var y = $attrs.mapY;
					var location = tileService.calculateTileLocation(x, y);
					location.y = location.y - 4;
					location.x = location.x - 4;
					var height = tileService.tileHeight + 2;
					var width = tileService.tileHeight + 2;
					$elem.attr("style","left:" + location.x + "px;" +
					"top:" + location.y + "px;" +
					"height:" + height + "px;" +
					"width:" + width + "px;" +
					"position:absolute;border-color: yellow;border-width: 4px;border-style: solid; border-radius: 4px;");
				}
			}
	});
	
	map.directive('tile', function($log, tileService) {
		return {
			restrict:"C",
			link: function($scope, $elem, $attrs) {
				var x = $attrs.mapX;
				var y = $attrs.mapY;
				var location = tileService.calculateTileLocation(x, y);
				$elem.addClass("tile-layout");
				$elem.css("left", location.x +"px");
				$elem.css("top", location.y + "px");
				$elem.css("height", tileService.tileHeight + "px");
				$elem.css("width", tileService.tileWidth + "px");
				$elem.css("position", "absolute");
				$elem.css("border-color","gray");
				$elem.css("border-width","1px 1px 1px 1px");
				$elem.css("border-style", "solid");
			}
		}
	});
	
	map.directive('terrain', function($log, tileService) {
		return {
			restrict:"A",
			scope: {
				t:'=terrain'
			},
			link: function($scope, $elem, $attrs) {
				tileService.addTile($scope.t);
				$elem.on('click', function(e) {
					
				});
			},
		}
	});
	
	map.directive('character', function($log, tileService) {
		return {
			restrict:"CA",
			scope: {
				c:'=character'
			},
			link: function($scope, $elem, $attrs) {
				tileService.addTile($scope.c);
				$elem.on('click', function(e) {
						alert('character');
					});
			},
			template:"<div class='characterTile'>" +
				"<div class='statusBar'><div class='bar' style='height:{{c.percentHealth}}%'></div></div>" +
				"<div class='postuer-{{c.postuer}}'></div>" +
				"<div>{{c.name}}</div>" +
				"</div>"
		}
	});
}();