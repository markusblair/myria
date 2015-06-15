package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public abstract class ItemCommand extends Action {
	Command command;
	public enum SearchPriority {CHARACTER, ROOM};
	
	@Override
	public void action(Command command, ICharacter character) {
		this.command = command;
	}

	

	public ItemSearchResult getItem(String itemToken, ICharacter character, SearchPriority... priority) {
		String itemName = this.command.getItem(itemToken);
		ItemSearchResult item = null;
		if (itemName.toLowerCase().startsWith("my ")) {
			itemName = itemName.substring(3, itemName.length());
			priority = new SearchPriority[] {SearchPriority.CHARACTER};
		} 
		else if (priority == null || priority.length == 0) {
			priority = new SearchPriority[] {SearchPriority.ROOM, SearchPriority.CHARACTER};
		}
		for (SearchPriority searchPriority : priority) {
			switch (searchPriority) {
			case ROOM:
				if (item == null) {
					item = character.getCurrentRoom().getItem(itemName);
				}
				break;
			default:
				if (item == null) {
					item = character.getItem(itemName);
				}
				break;
			}
		}
		
		if (item == null) {
			character.sendMessage(new StringBuilder("Could not find the ").append(itemName).toString());
		}
		return item;			
	}


	

	
}
