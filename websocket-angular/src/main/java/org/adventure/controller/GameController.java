package org.adventure.controller;

import javax.servlet.http.HttpServletRequest;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.character.ICharacterData;
import org.adventure.character.IEffectableCharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.commands.CommandHandler;
import org.adventure.persistance.CharacterDao;
import org.adventure.rooms.CityOfMyria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/game")
public class GameController {
	private static Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private CharacterDao service;
	
    @Autowired
    private CommandHandler commandHandler;
    
	@Autowired
	PlayerCharacter playerCharacter;
	

    @Autowired
	private RoomManager roomManager;
    
    @RequestMapping(value = "/character/{id}", method = RequestMethod.GET)
    public @ResponseBody IEffectableCharacterData load(@PathVariable String id, HttpServletRequest request) {
    	request.getSession().setAttribute("cId", id);
		ICharacterData characterData = service.load(id);
		playerCharacter.setCharacterData(characterData);
		ICharacterData out = service.load(id);
		Room room;
		if (roomManager.hasRoom(out.getRoomId())) {
			room = roomManager.getRoom(out.getRoomId());
		}else {
			room  = roomManager.getRoom(CityOfMyria.CENTRAL_PLAZA);
		}
		playerCharacter.gotoRoom(room);
        return characterData;
    }
    

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public @ResponseBody Room loadRoom(HttpServletRequest request) {
        return playerCharacter.getCurrentRoom();
    }
    
    @RequestMapping(value = "/command", method = RequestMethod.POST)
    public @ResponseBody void command(@RequestBody String cmd, HttpServletRequest request) {
//    	String id = (String)request.getSession().getAttribute("cId");
//    	org.adventure.character.CharacterData characterData = service.load(id);
//		playerCharacter.setCharacterData(characterData);
        commandHandler.handle(cmd);
    }
}
