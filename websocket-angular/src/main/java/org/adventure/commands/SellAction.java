package org.adventure.commands;

import org.adventure.IPlayerPrompt;
import org.adventure.character.ICharacter;
import org.adventure.items.QuantityItem;
import org.adventure.items.search.ItemSearchResult;
import org.adventure.rooms.MarketValuator;

public class SellAction extends ItemCommand {
	MarketValuator marketValuator;
	
	public SellAction(MarketValuator marketValuator) {
		this.addCommandPattern("Sell <item>");
		this.marketValuator = marketValuator;
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ItemSearchResult itemSearchResult = getItem("<item>",character);
		if (itemSearchResult != null && itemSearchResult.getItem() != null) {
			int amount = this.marketValuator.determineSellValue(itemSearchResult.getItem());
			character.sendMessage(new StringBuilder("Do you want to sell the ")
					.append(itemSearchResult.getItem().getDescription())
					.append(" for ")
					.append(amount)
					.append(" gold? Y/N").toString());
			character.setPlayerPrompt(new SellResponse(itemSearchResult, amount));		
		}
		
	}
	
	class SellResponse implements IPlayerPrompt {
		private ItemSearchResult itemToBuy;
		private int amount;
		public SellResponse(ItemSearchResult itemToBuy, int amount) {
			super();
			this.itemToBuy = itemToBuy;
			this.amount = amount;
		}

		@Override
		public void handleResponse(Command command, ICharacter character) {
			if (command.getCmd().toLowerCase().startsWith("y")) {
				QuantityItem gold = new QuantityItem(QuantityItem.GOLD, this.amount);
				character.addItem(gold);
				itemToBuy.getContainer().removeItem(itemToBuy.getItem());
				character.sendMessage("You sell the " + itemToBuy.getItem().getDescription() + " for " + this.amount + " gold.");				
			}
		}
	}
}
