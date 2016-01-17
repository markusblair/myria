package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IConsumable;
import org.adventure.items.search.ItemSearchResult;
import org.springframework.stereotype.Component;

@Component
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
		if (itemSearchResult != null && itemSearchResult.getItem() instanceof IConsumable) {
			IConsumable consumable = (IConsumable)itemSearchResult.getItem();
			consumable.consume(character);
			character.removeItem(consumable);
		}

	}
}
