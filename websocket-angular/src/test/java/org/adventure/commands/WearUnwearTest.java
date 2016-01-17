package org.adventure.commands;

import java.util.List;

import org.adventure.IContainer;
import org.adventure.character.BodyPartType;
import org.adventure.character.CharacterData;
import org.adventure.character.ICharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.items.IWearable;
import org.adventure.items.WearableContainer;
import org.adventure.items.WearableType;
import org.adventure.items.armor.Armor;
import org.adventure.items.armor.ArmorFactory;
import org.adventure.rooms.Room;
import org.adventure.rooms.areas.RoomManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WearUnwearTest {

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
	public void wearBackpack() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("backpack");
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		this.commandHandler.handle("get sword from backpack");
		
		List<IWearable> wornBackpacks = playerCharacter.getClothing().get(WearableType.BACK_PACK);
		Assert.assertEquals(1, wornBackpacks.size());
		Assert.assertEquals(backpack, wornBackpacks.get(0));
		
	}
	
	
	@Test
	public void unwearBackpack() {
		WearableContainer backpack = new WearableContainer(WearableType.BACK_PACK, 1000);
		backpack.setName("backpack");
		playerCharacter.addItem(backpack);
		playerCharacter.wear(backpack);
		
		List<IWearable> wornBackpacks = playerCharacter.getClothing().get(WearableType.BACK_PACK);
		Assert.assertEquals("Unable to wear the backpack.",1, wornBackpacks.size());
		Assert.assertEquals("Unable to wear the backpack.",backpack, wornBackpacks.get(0));
		
		playerCharacter.unWear(backpack);
		
		wornBackpacks = playerCharacter.getClothing().get(WearableType.BACK_PACK);
		Assert.assertEquals("Unable to remove the backpack.",0, wornBackpacks.size());
		
		List<IContainer> containers = playerCharacter.getContainers();
		Assert.assertEquals("Unable to remove the backpack from the list of containers.",0, containers.size());
		
	}
	
	@Test
	public void wearUnWearArmor() {
		Armor armor = new ArmorFactory().getPaddedChain();
		playerCharacter.addItem(armor);
		playerCharacter.wear(armor);
		
		Assert.assertEquals("Armor is not worn.", armor, playerCharacter.getWornArmor().get(0));
		
		playerCharacter.unWear(armor);
		
		Assert.assertEquals(armor, playerCharacter.getRightHand());
		Assert.assertEquals("Armor is not worn.", 0, playerCharacter.getWornArmor().size());
		Armor a  = playerCharacter.getArmorMap().get(BodyPartType.BACK);
		
		System.out.println(a);
	}
}
