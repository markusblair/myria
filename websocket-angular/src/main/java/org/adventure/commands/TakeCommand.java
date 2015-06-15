package org.adventure.commands;

import org.adventure.IContainer;
import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public class TakeCommand extends ItemCommand {
	public TakeCommand() {
		super();
		this.addCommandPattern("((take)|(get)) <item> from <container>", 
							   "((take)|(get)) <item>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ItemSearchResult itemSearchResult = null;
		StringBuilder youString = new StringBuilder();
		StringBuilder othersString = new StringBuilder();
		if (command.hasItem("<container>")) {
			itemSearchResult = getItem("<container>", character, ItemCommand.SearchPriority.ROOM, ItemCommand.SearchPriority.CHARACTER);
			if (itemSearchResult != null) {
				IContainer container = (IContainer)itemSearchResult.getItem();
				if (container != null) {
					String itemName = command.getItem("<item>");
					itemSearchResult = container.getItem(itemName);
					if (itemSearchResult != null) {
						itemSearchResult.getContainer().removeItem(itemSearchResult.getItem());
						youString.append("You take the ").append(itemSearchResult.getItem().getDescription())
						.append(" from the ").append(container.getName());
						othersString.append(character.getName()).append(" takes the ").append(itemSearchResult.getItem().getName())
						.append(" from the ").append(container.getName());
					}
				}				
			}
		}
		else {
			itemSearchResult = getItem("<item>",character, ItemCommand.SearchPriority.ROOM, ItemCommand.SearchPriority.CHARACTER);
			if (itemSearchResult != null) {
				youString.append("You pick up the ").append(itemSearchResult.getItem().getName());
				othersString.append(character.getName()).append(" takes the ").append(itemSearchResult.getItem().getName());				
			}
		}
		if (itemSearchResult != null) {
			if (itemSearchResult.getItem().commandAllowed(this, character)) {
				if (character.canAddItem(itemSearchResult.getItem())) {
					character.addItem(itemSearchResult.getItem());
					itemSearchResult.getContainer().removeItem(itemSearchResult.getItem());
					character.sendMessage(youString.toString());
					character.sendMessageToRoom(othersString.toString());					
				}
				else {
					if (character.isHolding(itemSearchResult.getItem())) {
						character.sendMessage(new StringBuilder().append("You already have the ").append(itemSearchResult.getItem().getName()).toString());						
					}
					else if (character.getFreeHands() == 0){
						character.sendMessage(new StringBuilder().append("You need a free hand to do that.").toString());						
					}
					else {
						character.sendMessage(new StringBuilder().append("You can't do that do that.").toString());	
					}
				}
			}
		}
	}

}
