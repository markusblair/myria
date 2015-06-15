package org.adventure.items.search;

import org.adventure.character.ICharacter;
import org.adventure.commands.ItemCommand.SearchPriority;

public class ItemService {
	
	public ItemSearchResult getItem(String itemName, ICharacter character, SearchPriority... priority) {
		ItemSearchResult itemSearchResult = null;
		if (itemName.toLowerCase().startsWith("my ")) {
			itemName = itemName.substring(3, itemName.length());
			priority = new SearchPriority[] {SearchPriority.CHARACTER};
		} else if (priority == null || priority.length == 0) {
			priority = new SearchPriority[] {SearchPriority.CHARACTER, SearchPriority.ROOM};
		}
		for (SearchPriority searchPriority : priority) {
			switch (searchPriority) {
			case ROOM:
				if (itemSearchResult == null) {
					itemSearchResult = character.getCurrentRoom().getItem(itemName);
				}
				break;
			default:
				if (itemSearchResult == null) {
					itemSearchResult = character.getItem(itemName);
				}
				break;
			}
		}
		
		if (itemSearchResult == null) {
			character.sendMessage(new StringBuilder("Could not find the ").append(itemName).toString());
		}
		return itemSearchResult;			
	}
}
