package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IItem;
import org.adventure.items.search.ItemSearchResult;
import org.springframework.stereotype.Component;

@Component
public class DropCommand extends ItemCommand {

	
	public DropCommand() {
		super();
		this.addCommandPattern("drop <item>");
	}



	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		// Is the item in your hand.
		ItemSearchResult itemSearchResult = getItem("<item>", character);
		dropItem(character, itemSearchResult.getItem());
	}

	public void dropItem(ICharacter character, IItem item) {
		if (character.isHolding(item)) {
			character.removeItem(item);			
			character.getCurrentRoom().addItem(item);
			character.sendMessageToRoom("drops " + item.getName());
		}		
		else {
			character.sendMessage("Drop the what?");
		}
	}
}
