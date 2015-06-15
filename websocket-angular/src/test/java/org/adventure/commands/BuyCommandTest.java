package org.adventure.commands;

import org.adventure.RoomManager;
import org.adventure.SalePrice;
import org.adventure.Store;
import org.adventure.TrainingPrice;
import org.adventure.character.CharacterData;
import org.adventure.character.ICharacterData;
import org.adventure.character.PlayerCharacter;
import org.adventure.items.Item;
import org.adventure.random.SkillType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuyCommandTest {


	private CommandHandler commandHandler;
	private PlayerCharacter playerCharacter;
	private RoomManager roomManager;
	private Store room;
	
	@Before
	public void setup() {
		this.commandHandler = new CommandHandler();
		ICharacterData characterData = new CharacterData();
		characterData.setId("Test");
		playerCharacter = new PlayerCharacter(characterData);
		this.commandHandler.playerCharacter = playerCharacter;
		roomManager = new RoomManager();
		room = roomManager.createStore("TestRoom", null);
		playerCharacter.gotoRoom(room);
		
	}

	@Test
	public void buySwordInitialPromotTest() {

		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new SalePrice(1, item.getDescription()));
		room.addItem(item);
		this.commandHandler.handle("buy sword");
		
		Assert.assertNull("Should have to confirm purchase.",playerCharacter.getRightHand());
	}

	@Test
	public void buySwordTest() {

		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new SalePrice(1, item.getDescription()));
		room.addItem(item);
		this.commandHandler.handle("buy sword");
		this.commandHandler.handle("y");
		Assert.assertEquals(item,playerCharacter.getRightHand());
	}
	
	@Test
	public void rejectPurchaseTest() {

		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new SalePrice(1, item.getDescription()));
		room.addItem(item);
		this.commandHandler.handle("buy sword");
		this.commandHandler.handle("n");
		
		Assert.assertNull("Rejecting purchase should cancel.",playerCharacter.getRightHand());
	}
	
	
	@Test
	public void testTakeRestriction() {

		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new SalePrice(1, item.getDescription()));
		room.addItem(item);
		this.commandHandler.handle("get sword");
		
		Assert.assertNull("The player should not be able to take the item.",playerCharacter.getRightHand());
	}
	
	@Test
	public void insufecientFunds() {

		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new SalePrice(playerCharacter.getGold().getAmount() + 1, item.getDescription()));
		room.addItem(item);
		this.commandHandler.handle("buy sword");
		this.commandHandler.handle("y");
		Assert.assertNull("Not enought funds.",playerCharacter.getRightHand());
	}
	
	@Test
	public void traininInSword() {
		playerCharacter.setExperience(30000);
		playerCharacter.getGold().add(2000);
		Item item = new Item("sword", "", 1, 1);
		item.addCommandCondition(TakeCommand.class, new TrainingPrice(1000, SkillType.SWORD));
		room.addItem(item);
		Assert.assertEquals(1, playerCharacter.getSkill(SkillType.SWORD).getLevel());
		this.commandHandler.handle("buy sword");
		this.commandHandler.handle("y");
		Assert.assertEquals(2, playerCharacter.getSkill(SkillType.SWORD).getLevel());
		
	}
	
}
