package org.adventure.commands;

import org.adventure.MarketValuator;
import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;

public class AppraiseAction extends ItemCommand {
	MarketValuator marketValuator;
	
	public AppraiseAction(MarketValuator marketValuator) {
		this.addCommandPattern("Appraise <item>");
		this.marketValuator = marketValuator;
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ItemSearchResult itemSearch = getItem("<item>",character);
		int amount = marketValuator.determineSellValue(itemSearch.getItem());
		character.sendMessage(new StringBuilder().append("The ").append(itemSearch.getItem().getDescription())
				.append(" is worth ")
				.append(amount)
				.append(" gold.").toString());
		
	}

}
