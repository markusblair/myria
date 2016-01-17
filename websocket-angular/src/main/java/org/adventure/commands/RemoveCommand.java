package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IWearable;
import org.adventure.items.armor.Armor;
import org.adventure.items.search.ItemSearchResult;
import org.springframework.stereotype.Component;

@Component
public class RemoveCommand extends ItemCommand {
	public RemoveCommand() {
		super();
		this.addCommandPattern("remove <item>");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ItemSearchResult itemSearchResult = getItem("<item>", character, SearchPriority.CHARACTER);
		if (itemSearchResult != null && itemSearchResult.getItem() != null) {
			if (character.getFreeHands() > 0) {
				if (character.isWearing(itemSearchResult.getItem())) {
					if (itemSearchResult.getItem() instanceof IWearable) {
						IWearable wearable = (IWearable) itemSearchResult.getItem();
						character.unWear(wearable);
					}
					else if (itemSearchResult.getItem() instanceof Armor) {
						Armor armor = (Armor) itemSearchResult.getItem();
						character.unWear(armor);
					}
					character.sendMessage(new StringBuilder("You remove ").append(command.getItem("<item>")).toString());
				}
				else {
					character.sendMessage(new StringBuilder("You are not wearing the ").append(command.getItem("<item>")).toString());
				}
			}
			else {
				character.sendMessage("Your hands are full.");
			}
		}
		else {
			character.sendMessage("What are you wanting to remove?");
		}
	}
}
