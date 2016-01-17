package org.adventure.character;

import java.util.Map;

import org.adventure.items.armor.AttackResult;
import org.adventure.messaging.DataMessage;
import org.adventure.rooms.Room;

public interface IWebSocketDataService {

	public abstract void sendAttackResults(String characterId, AttackResult attackResult);

	public abstract void sendMessageToCharacter(String characterId, String message);

	public abstract void sendCharacter(ICharacterData characterData);
	
	public abstract void sendCharacterUpdate(ICharacter room, String property, Object value);
	
	public abstract void sendRoom(String characterId, Room room);

	public abstract void sendDataToCharacter(String characterId, Map<String, Object> data);
	
	public abstract void sendDataMessageToRoom(DataMessage dataMessage, Room room);
	
}