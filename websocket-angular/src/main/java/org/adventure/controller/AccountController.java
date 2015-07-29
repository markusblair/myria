package org.adventure.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.character.BodyPart;
import org.adventure.character.BodyPartType;
import org.adventure.character.ICharacterData;
import org.adventure.character.IEffectableCharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.character.stats.StatReference;
import org.adventure.persistance.CharacterDao;
import org.adventure.rooms.CityOfMyria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/character")
public class AccountController {

    @Autowired
    private CharacterDao service;

    @Autowired
	private RoomManager roomManager;
    
    
	@Autowired
	PlayerCharacter playerCharacter;
    
    @RequestMapping(value = "/character/{id}", method = RequestMethod.GET)
    public @ResponseBody IEffectableCharacterData load(@PathVariable String id, HttpServletRequest request) {
		request.getSession().setAttribute("cId", id);
		ICharacterData out = service.load(id);
		Room room;
		if (roomManager.hasRoom(out.getRoomId())) {
			room = roomManager.getRoom(out.getRoomId());
		}else {
			room  = roomManager.getRoom(CityOfMyria.CENTRAL_PLAZA);
		}
		playerCharacter.gotoRoom(room);
        return out;
    }
	
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody boolean delete(@PathVariable String id, HttpServletRequest request) {
        service.delete(id);
    	return true;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<org.adventure.character.CharacterData> loadCharacters(HttpServletRequest request) {
		 List<org.adventure.character.CharacterData> out = service.listCharacters(request.getUserPrincipal().getName());
        return out;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/create", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void createCharacter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStreamReader isr = new InputStreamReader(request.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		String line = in.readLine();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> pc = mapper.readValue(line, Map.class);
		
		int str = Integer.parseInt(pc.get("str").toString());
		int agi =Integer.parseInt(pc.get("agi").toString());
		int spe = Integer.parseInt(pc.get("spe").toString());
		int intel = Integer.parseInt(pc.get("int").toString());
		int sta = Integer.parseInt(pc.get("sta").toString());
		
		int totPoints = (str + agi + spe + intel + sta);
		
		if (totPoints != 50) {
			throw new RuntimeException("Not a valid number of points");
		}
		
		org.adventure.character.CharacterData characterData = new org.adventure.character.CharacterData();
		characterData.setId(UUID.randomUUID().toString());
	    characterData.setUserId(request.getUserPrincipal().getName());
	    characterData.setName((String)pc.get("name"));
	    characterData.setStrength(str);
	    characterData.setAgility(agi);
		characterData.setSpeed(spe);
		characterData.setInteligence(intel);
		characterData.setStamina(sta);
	    characterData.setRoomId(CityOfMyria.TRAINER);
	    characterData.setMaxHealth(new StatReference(100));

		List<BodyPart> bodyParts = new ArrayList<BodyPart>();
		bodyParts.add(new BodyPart(BodyPartType.ARM,"Left Arm",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.ARM,"Right Arm",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.LEG,"Left Leg",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.LEG,"Right Leg",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.BACK,"Back",1f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.TORSO,"Torso",1f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HEAD,"Head",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.NECK,"Neck",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HAND,"Left Hand",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HAND,"Right Hand",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.FOOT,"Left Foot",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.FOOT,"Right Foot",0.50f, characterData.getMaxHealth()));
		characterData.setBodyParts(bodyParts);
	    
	    service.save(characterData);
	    
	    response.sendRedirect("../?cId=" + characterData.getId());;
	}
}
