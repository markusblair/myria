package org.adventure.commands;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.character.CharacterData;
import org.adventure.character.ICharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.items.Item;
import org.adventure.items.WearableContainer;
import org.adventure.items.WearableType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TakeCommandTest {

	private CommandHandler commandHandler;
	private PlayerCharacter playerCharacter;
	private RoomManager roomManager;
	
	@Before
	public void setup() {
		this.commandHandler = new CommandHandler();
		ICharacterData characterData = new CharacterData();
		characterData.setId("Test");
		playerCharacter = new PlayerCharacter(characterData);
		this.commandHandler.playerCharacter = this.playerCharacter;
		roomManager = new RoomManager();
		Room room = roomManager.getRoom("TestRoom");
		playerCharacter.gotoRoom(room);
		
	}

	@Test
	public void swordInBackPackTest() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("backpack");
		Item item = new Item("sword", "", 1, 1);
		backpack.addItem(item);
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		this.commandHandler.handle("get sword from backpack");
		
		Assert.assertEquals(item, playerCharacter.getRightHand());
	}
	
	@Test
	public void swordInBackPackOnFloorTest() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("backpack");
		Item item = new Item("sword", "", 1, 1);
		backpack.addItem(item);
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		
		WearableContainer backpackInRoom = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpackInRoom.setName("backpack");
		playerCharacter.getCurrentRoom().addItem(backpackInRoom);
		Item item2 = new Item("sword", "", 1, 1);
		backpackInRoom.addItem(item2);
		

		
		this.commandHandler.handle("get sword from backpack");
		Assert.assertEquals(item2, playerCharacter.getRightHand());
		Assert.assertEquals(backpackInRoom.getItems().size(), 0);
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		this.commandHandler.handle("put sword in backpack");
		
		Assert.assertNull(playerCharacter.getRightHand());
		Assert.assertEquals(backpackInRoom.getItems().size(), 1);
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		this.commandHandler.handle("get sword from my backpack");
		Assert.assertEquals(playerCharacter.getRightHand(), item);
		Assert.assertEquals(backpackInRoom.getItems().size(), 1);
		Assert.assertEquals(backpack.getItems().size(), 0);
		
		this.commandHandler.handle("put sword in my backpack");
		
		Assert.assertNull(playerCharacter.getRightHand());
		Assert.assertEquals(backpackInRoom.getItems().size(), 1);
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		WearableContainer secondBackpackInRoom = new WearableContainer(WearableType.BACK_PACK, 1000);
		secondBackpackInRoom.setName("backpack");
		playerCharacter.getCurrentRoom().addItem(secondBackpackInRoom);
		Item item3 = new Item("sword", "", 1, 1);
		secondBackpackInRoom.addItem(item3);
		
		this.commandHandler.handle("get sword from other backpack");
		Assert.assertEquals(playerCharacter.getRightHand(), item3);
		Assert.assertEquals(secondBackpackInRoom.getItems().size(), 0);
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		this.commandHandler.handle("put sword in other backpack");
		
		Assert.assertNull(playerCharacter.getRightHand());
		Assert.assertEquals(secondBackpackInRoom.getItems().size(), 1);
		Assert.assertEquals(backpack.getItems().size(), 1);
	}
	
	@Test
	public void testSpecificNames() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("leather backpack");
		Item item = new Item("great sword", "", 1, 1);
		backpack.addItem(item);
		Item item2 = new Item("long sword", "", 1, 1);
		backpack.addItem(item2);
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		
		this.commandHandler.handle("get sword from backpack");
		Assert.assertEquals(item, playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 1);
	
		this.commandHandler.handle("put sword in my backpack");
		Assert.assertNull( playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 2);
		
		this.commandHandler.handle("get long sword from backpack");
		Assert.assertEquals(item2, playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		this.commandHandler.handle("put sword in my backpack");
		Assert.assertNull( playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 2);
		
	}
	
	
	@Test
	public void testSpecificContainersNames() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("leather backpack");
		Item item = new Item("leather sword", "", 1, 1);
		backpack.addItem(item);
		Item item2 = new Item("long sword", "", 1, 1);
		backpack.addItem(item2);
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		
		this.commandHandler.handle("get leather sword from backpack");
		Assert.assertEquals(item, playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 1);
	
		this.commandHandler.handle("put sword in my backpack");
		Assert.assertNull( playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 2);
		
		this.commandHandler.handle("get long sword from backpack");
		Assert.assertEquals(item2, playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 1);
		
		this.commandHandler.handle("put sword in my backpack");
		Assert.assertNull( playerCharacter.getRightHand());
		Assert.assertEquals(backpack.getItems().size(), 2);
		
	}
	
	

	@Test
	public void testDropContainersNames() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("leather backpack");
		playerCharacter.addItem(backpack);
		
		Assert.assertNotNull(playerCharacter.getRightHand());
		Assert.assertEquals(0, playerCharacter.getCurrentRoom().getItems().size());
		this.commandHandler.handle("drop backpack");
		Assert.assertNull(playerCharacter.getRightHand());
		Assert.assertEquals(1, playerCharacter.getCurrentRoom().getItems().size());
		
	}

	@Test
	public void testSwap() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("leather backpack");
		playerCharacter.addItem(backpack);
		
		Assert.assertEquals(backpack,playerCharacter.getRightHand());
		Assert.assertEquals(0, playerCharacter.getCurrentRoom().getItems().size());
		this.commandHandler.handle("swap");
		Assert.assertNull(playerCharacter.getRightHand());
		Assert.assertEquals(backpack, playerCharacter.getLeftHand());
	}
	
}
