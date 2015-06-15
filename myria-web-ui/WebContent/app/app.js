var app = angular.module("myria", [ "myria.services", "myria.controllers", 'ngRoute', 'ui.bootstrap','myria.command', 'ngPrettyJson', 'myria.items', 'myria.monster'])
.config(function($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('!');
	$routeProvider
		.when('/game/:cid', {
			templateUrl: './assets/view/game.html',
			controller: 'gameCtrl'
		})
		.when('/account', {
			templateUrl: './assets/view/account.html',
			controller: 'accountCtrl'
		})
		.when('/characterEdit', {
			templateUrl: './assets/view/characterEdit.html',
			controller: 'accountCtrl'
		});

});

app.directive('ngContextMenu', function ($parse) {
    var renderContextMenu = function ($scope, event, options) {
        if (!$) { var $ = angular.element; }
        $(event.currentTarget).addClass('context');
        var $contextMenu = $('<div>');
        $contextMenu.addClass('dropdown clearfix');
        var $ul = $('<ul>');
        $ul.addClass('dropdown-menu');
        $ul.attr({ 'role': 'menu' });
        $ul.css({
            display: 'block',
            position: 'absolute',
            left: event.pageX + 'px',
            top: event.pageY + 'px'
        });
        angular.forEach(options, function (item, i) {
            var $li = $('<li>');
            if (item === null) {
                $li.addClass('divider');
            } else {
                $a = $('<a>');
                $a.attr({ tabindex: '-1', href: '#' });
                $a.text(item[0]);
                $li.append($a);
                $li.on('click', function () {
                    $scope.$apply(function() {
                        item[1].call($scope, $scope);
                    });
                });
            }
            $ul.append($li);
        });
        $contextMenu.append($ul);
        $contextMenu.css({
            width: '100%',
            height: '100%',
            position: 'absolute',
            top: 0,
            left: 0,
            zIndex: 9999
        });
        $(document).find('body').append($contextMenu);
        $contextMenu.on("click", function (e) {
            $(event.currentTarget).removeClass('context');
            $contextMenu.remove();
        }).on('contextmenu', function (event) {
            $(event.currentTarget).removeClass('context');
            event.preventDefault();
            $contextMenu.remove();
        });
    };
    return function ($scope, element, attrs) {
        element.on('contextmenu', function (event) {
            $scope.$apply(function () {
                event.preventDefault();
                var options = $scope.$eval(attrs.ngContextMenu);
                if (options instanceof Array) {
                    renderContextMenu($scope, event, options);
                } else {
                    throw '"' + attrs.ngContextMenu + '" not an array';                    
                }
            });
        });
    };
});

app.factory('myriaServerInterceptor',[function() {
    var requestInterceptor = {
        request: function(config) {
            if (config.url.match(/myria/) || config.url.match(/notify/)) {
                config.url = 'http://localhost:8080'+config.url;
            }
            return config;
        }
    };
    return requestInterceptor;
}]);

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('myriaServerInterceptor');
    $httpProvider.defaults.withCredentials = true;
}]);

app.filter('reverse', function() {
    return function(items) {
        return items.slice().reverse();
    };
});
