package org.adventure.commands;

import org.adventure.IContainer;
import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;
import org.adventure.rooms.ISalePrice;
import org.springframework.stereotype.Component;

@Component
public class BuyAction extends ItemCommand {

	public BuyAction() {
		this.addCommandPattern("Buy <item> from <container>", 
				   "Buy <item>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		this.command = command;
		ItemSearchResult itemSearchResult = null;
		if (command.hasItem("<container>")) {
			IContainer container = (IContainer)getItem("<container>", character, SearchPriority.ROOM);
			if (container != null) {
				itemSearchResult = container.getItem(command.getItem("<item>"));
			}
		}
		else {
			itemSearchResult = getItem("<item>",character, SearchPriority.ROOM);
		}
		if (itemSearchResult != null) {
			CommandCondition commandCondition = itemSearchResult.getItem().getCommandCondition(TakeCommand.class);
			if (commandCondition instanceof ISalePrice) {
				((ISalePrice)commandCondition).buyItem(character, itemSearchResult);
			}
			else {
				character.sendMessage("The " + itemSearchResult.getItem().getDescription() + " is not for sale.");
			}
		}
	}

	
	
	
	 
}
