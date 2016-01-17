package org.adventure.character;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.adventure.items.armor.AttackResult;
import org.adventure.messaging.DataMessage;
import org.adventure.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketDataService implements IWebSocketDataService {
	private static Logger log = LoggerFactory.getLogger(WebSocketDataService.class);

	@Autowired
    private SimpMessagingTemplate session;
	
	public void setSession(SimpMessagingTemplate session) {
		this.session = session;
	}
	public SimpMessagingTemplate getSession() {
		return session;
	}

	public void sendDataToCharacter(String characterId, Map<String, Object> data) {
			String topic = new StringBuilder("/topic/notify/character/").append(characterId).toString();
			log.debug(new StringBuilder("MSG: ").append(characterId).append(" ").append(jsonify(data)).toString());
			getSession().convertAndSend(topic, data);			
	}
	
	public String jsonify(Object object) {
		Writer stringWritter = new StringWriter();
		try {
			new JsonFactory().createGenerator(stringWritter).setCodec(new ObjectMapper()).writeObject(object);
		} catch (Exception e) {
			log.error("Error json serailzing object :" + object.getClass(), e);
		}
		return stringWritter.toString();
	}
	
	@Override
	public void sendAttackResults(String characterId, AttackResult attackResult) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("attackResult", jsonify(attackResult));
		sendDataToCharacter(characterId, data);
		
	}
	
	@Override
	public void sendRoom(String characterId, Room room) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("room", jsonify(room));
		sendDataToCharacter(characterId, data);
	}
	
	@Override
	public void sendMessageToCharacter(String characterId, String message) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("message", message);
		sendDataToCharacter(characterId, data);
    }
	
	@Override
	public void sendCharacter(ICharacterData characterData) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("character", jsonify(characterData));
		sendDataToCharacter(characterData.getId(), data);
	}
	
	@Override
	public void sendCharacterUpdate(ICharacter character,  String property, Object value) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", character.getId());
		p.put(property, value);
		data.put("character", jsonify(p));
		sendDataToCharacter(character.getId(), data);
		if (character.getCurrentRoom() != null) {
			for (ICharacter c : character.getCurrentRoom().getCharacters()) {
				c.sendData(data);
			}			
		}
	}
	
	@Override
	public void sendDataMessageToRoom(DataMessage dataMessage, Room room) {
		for (ICharacter c : room.getCharacters()) {
			c.sendData(dataMessage.getData());
		}
	}

}
