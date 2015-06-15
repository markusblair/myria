package org.adventure.commands;

import org.adventure.IContainer;
import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public class EatCommand extends ItemCommand {

	public EatCommand() {
		super();
		this.addCommandPattern("eat <item>");
		this.addCommandPattern("drink <item>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		// Is the item in your hand.
		ItemSearchResult itemSearchResult = getItem("<item>", character, SearchPriority.CHARACTER);
//		IContainer container = (IContainer)getItem("<container>", character);
//		if (container != null && itemSearchResult != null) {
//			container.addItem(itemSearchResult.getItem());
//			character.sendMessage(new StringBuilder("You put the ").append(command.getItem("<item>"))
//					.append(" in the ").append(command.getItem("<container>")).toString());
//		} 

	}
}
