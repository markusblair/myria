package org.adventure.commands;

import org.adventure.character.CharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.rooms.Room;
import org.adventure.rooms.areas.RoomManager;
import org.junit.Before;

public class MyIdentifierTests {
	private CommandHandler commandHandler;
	private PlayerCharacter playerCharacter;
	private RoomManager roomManager;
	
	@Before
	public void setup() {
		this.commandHandler = new CommandHandler();
		playerCharacter = new PlayerCharacter(new CharacterData());
		this.commandHandler.playerCharacter = this.playerCharacter;
		roomManager = new RoomManager();
		Room room = roomManager.getRoom("TestRoom");
		playerCharacter.gotoRoom(room);
		
	}

	
	
}
