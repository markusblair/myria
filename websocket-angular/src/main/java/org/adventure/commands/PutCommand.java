package org.adventure.commands;

import org.adventure.IContainer;
import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public class PutCommand extends ItemCommand {

	
	public PutCommand() {
		super();
		this.addCommandPattern("put <item> in <container>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		// Is the item in your hand.
		ItemSearchResult itemSearchResult = getItem("<item>", character);
		IContainer container = (IContainer)getItem("<container>", character).getItem();
		if (container != null && itemSearchResult != null) {
			container.addItem(itemSearchResult.getItem());
			itemSearchResult.getContainer().removeItem(itemSearchResult.getItem());
			character.sendMessage(new StringBuilder("You put the ").append(command.getItem("<item>"))
					.append(" in the ").append(command.getItem("<container>")).toString());
		} 

	}

}
