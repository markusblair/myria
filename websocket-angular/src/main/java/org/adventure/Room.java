package org.adventure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adventure.character.DataMessage;
import org.adventure.character.ICharacter;
import org.adventure.commands.Action;
import org.adventure.commands.CommandCondition;
import org.adventure.commands.navigation.Direction;
import org.adventure.commands.navigation.MoveCommand;
import org.adventure.items.IItem;
import org.adventure.items.search.ItemSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Room extends CharacterGroup implements IContainer {
	private String id;
	private static Logger log = LoggerFactory.getLogger(CharacterGroup.class);
	
	@JsonIgnore
	private Map<Action, CommandCondition> validCommands = new HashMap<Action, CommandCondition>();
	private List<Direction> exits = new ArrayList<Direction>();
	private CharacterGroups meleeBattles = new CharacterGroups();
	String name;
	String description;
	List<IItem> items = new ArrayList<IItem>();
	private int travelSpeed = 1;
	
	protected Room() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.adventure.IPercievedRoom#getCharacters()
	 */
//	@JsonIgnore
//	public Collection<ICharacter> getCharacters() {
//		return characters.values();
//	}
//
//	public Map<String, ICharacter> getCharacterMap() {
//		return characters;
//	}
	
	
	
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.adventure.IPercievedRoom#getItems()
	 */
	public List<IItem> getItems() {
		return items;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getLongDescription() {
		return getDescription();
	}

	public Room setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getTravelSpeed() {
		return travelSpeed;
	}

	public void setTravelSpeed(int travelSpeed) {
		this.travelSpeed = travelSpeed;
	}

	public List<Direction> getExits() {
		return exits;
	}

	public IContainer addItems(IItem... itemsToAdd) {
		for (IItem item : itemsToAdd) {
			items.add(item);
		}
		return this;
	}

	@Override
	public void removeItem(IItem item) {
		items.remove(item);
		this.sendDataMessageToRoom(new DataMessage().removeItem(item));
	}
	
	public ItemSearchResult getItem(String itemName) {
		int index = 0;
		if (itemName.toLowerCase().startsWith("other ")) {
			itemName = itemName.substring(6, itemName.length());
			index =  1;
		} 
		else if (itemName.toLowerCase().startsWith("2nd ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  2;
		} 
		else if (itemName.toLowerCase().startsWith("3rd ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  2;
		} 
		else if (itemName.toLowerCase().startsWith("4th ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  3;
		} 		
		for (IItem item : items) {
			if (item.is(itemName)) {
				if (--index < 0 ) {
					return new ItemSearchResult(item, this);
				}
			} 
			else if (item instanceof IContainer) {
				IContainer container = (IContainer) item;
				if (container.isContentsVisible()) {
					ItemSearchResult itemInContainer = container.getItem(itemName);
					if (itemInContainer != null) {
						if (--index < 0 ) {
							return itemInContainer;							
						}
					}
				}
			}
		}
		return null;
	}
	
	public IContainer addCommand(Action command) {
		return addCommand(command, null);
	}
	
	public IContainer addCommand(Action command, CommandCondition commandCondition) {
		validCommands.put(command, commandCondition);
		if (command instanceof MoveCommand) {
			MoveCommand moveCommand = (MoveCommand) command;
			this.exits.add(moveCommand.getDirection());
		}
		return this;
	}

	public List<Action> getValidCommands(ICharacter playerCharacter) {
		Set<Action> actions = validCommands.keySet();
		List<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (validCommands.get(action) == null || validCommands.get(action).testCondition(playerCharacter)) {
				validActions.add(action);
			}
		}
		return validActions;
	}

	@Override
	public void addItem(IItem item) {
		this.items.add(item);	
		this.sendDataMessageToRoom(new DataMessage().addItem(item));
	}

	@Override
	public boolean isContentsVisible() {
		return false;
	}

	@Override
	public boolean canAddItem(IItem item) {
		return true;
	}
//
//	public void addCharacter(ICharacter character) {
//		if (this.characters.containsKey(character.getId()) == false) {
//			this.characters.put(character.getId(), character);
//			DataMessage dataMessage = new DataMessage();
//			dataMessage.addCharacter(character);
//			character.sendDataMessageToRoom(dataMessage, false);
//		}
//		else {
//			this.removeCharacter(character);
//		}
//	}
//	
//	
	public void removeCharacter(ICharacter character) {
		super.removeCharacter(character);
		character.sendDataMessageToRoom(new DataMessage().removeCharacter(character), false);
		this.meleeBattles.removeFromGroup(character);
	}
//	
//	public ICharacter getCharacter(String name) {
//		Collection<ICharacter> chrts  = this.characters.values();
//		for (ICharacter character : chrts) {
//			if (character.getName().toLowerCase().startsWith(name)) {
//				return character;
//			}
//		}
//		return null;
//	}
	
	public void joinCharacterGroup(ICharacter character, ICharacter charactersGroupToJoin) {
		this.meleeBattles.joinCharacterGroup(character, charactersGroupToJoin);
	}

	public CharacterGroup getCharacterGroup(ICharacter character) {
		return this.meleeBattles.getCharacterGroup(character);
	}
		
	private void sendDataMessageToRoom(DataMessage dataMessage) {
		for (ICharacter character : getCharacters()) {
			character.sendDataMessageToRoom(dataMessage, false);
		}
	}
	
}
