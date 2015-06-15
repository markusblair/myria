angular.module("myria.services", [])
    .service('messageService',["$route",
    function($route){
        var service = {};
        var roomSubscribe;

        service.model = {
            id: null,
            character: {},
            room: {},
            messages:[],
            rawMessage: []
        };
        
        service.socket = {
            client: null,
            stomp: null
        };
        service.addMessage = function(type, message) {
            var msg = {'type':type,
                'message':message};
            if (service.model.messages.length == 0) {
                service.model.messages.push(msg);
            }
            else if (service.model.messages.length >= 0) {
                service.model.messages.splice(0, 0, msg);
            }
            if (service.model.messages.length >= 50) {
                service.model.messages.pop();
            }
        };

        service.clearMessages = function() {
            service.model.messages=[];
        };

        service.notifyMessage = function(/** Message */ message) {
            var msg = angular.fromJson(message.body);

            if (msg.message) {
                service.addMessage('./app/message/string.html',msg.message);
            }
            if (msg.attackResult) {
                service.addMessage('./app/message/attack.html', angular.fromJson(msg.attackResult));
            }
            if (msg.room) {
                service.clearMessages();
                service.model.rawMessage = [];
                service.model.rawMessage.push(msg);
                var newRoom = angular.fromJson(msg.room);
                if (service.model.room.id != newRoom.id) {
                    delete newRoom.characterMap[service.model.character.id];
                    service.model.room = newRoom;
                }
                else {
                    angular.extend(service.model.room, newRoom);
                }

            }
            if (msg.action) {
                if (msg.action ==  'add') {
                    if (msg.character) {
                        var c = angular.fromJson(msg.character);
                        service.model.rawMessage.push(c);
                        service.model.room.characterMap[c.id] = c;
                    }
                    if (msg.item) {
                        var i = angular.fromJson(msg.item);
                        service.model.rawMessage.push(i);
                        service.model.room.items.push(i);
                    }
                }
                else if (msg.action ==  'remove') {
                    if (msg.character) {
                        var c = angular.fromJson(msg.character);
                        service.model.rawMessage.push(c);
                        delete service.model.room.characterMap[c.id];
                    }
                    if (msg.item) {
                        var i = angular.fromJson(msg.item);
                    }
                }
                else if (msg.action ==  'update') {
                    service.updateCharacter(msg);
                }
            }
            else if (msg.character) {
                service.updateCharacter(msg);
            }

            service.onMessage();
        };

        service.updateCharacter = function(msg) {
            var c = angular.fromJson(msg.character);

            if (c.id === service.model.character.id) {
                if (!c.mind && !c.mana) {
                    service.model.rawMessage.push(c);
                }
                angular.extend(service.model.character,c);
            }
            else if (service.model.room.characterMap[c.id]) {
                angular.extend(service.model.room.characterMap[c.id],c);
            }
            else {
                service.model.room.characterMap[c.id] = c;
            }
        }

        service.onMessage = function() {};

        service.reconnect = function() {
            setTimeout(service.initSockets, 10000);
        };

        service.initSockets = function() {
            service.socket.client = new SockJS('http://localhost:8080/myria/notify/');
            service.socket.stomp = Stomp.over(service.socket.client);
            service.socket.stomp.connect({}, function() {
                service.socket.stomp.subscribe("/topic/notify/character/"+$route.current.params.cid, service.notifyMessage);
            });
            service.socket.client.onclose = service.reconnect;
        };


        return service;
    }]);