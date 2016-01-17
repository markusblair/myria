package org.adventure.rooms;

import org.adventure.IPlayerPrompt;
import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.CommandCondition;
import org.adventure.commands.TakeCommand;
import org.adventure.items.search.ItemSearchResult;

public class SalePrice extends CommandCondition implements ISalePrice {
	private int amount; 
	private String itemName;
	
	public SalePrice(int amount, String itemName) {
		super();
		this.amount = amount;
		this.itemName = itemName;
	}

	@Override
	public boolean conditional(ICharacter character) {
		StringBuilder msg = new StringBuilder();
		msg.append("You can buy the ");
		msg.append(itemName);
		msg.append(" for ");
		msg.append(this.amount);
		msg.append(" gold.");

		character.sendMessage(msg.toString());
		return false;
	}

	/* (non-Javadoc)
	 * @see org.adventure.ISalePrice#buyItem(org.adventure.PlayerCharacter, org.adventure.items.search.ItemSearchResult)
	 */
	@Override
	public void buyItem(ICharacter character, ItemSearchResult itemSearchResult) {
		IPlayerPrompt result;
		if (character.getGold().getAmount() > amount) {
			character.sendMessage(new StringBuilder("Do you want to buy the ").append(itemSearchResult.getItem().getDescription())
					.append(" for ").append(amount).append(" gold? Y/N").toString());
			result = new BuyResponse(itemSearchResult, amount);	
			character.setPlayerPrompt(result);
		}
		else {
			character.sendMessage("You don't have enough gold to purchase that.");
		}
	}

	class BuyResponse implements IPlayerPrompt {
		private ItemSearchResult itemToBuy;
		private int amount;
		public BuyResponse(ItemSearchResult itemToBuy, int amount) {
			super();
			this.itemToBuy = itemToBuy;
			this.amount = amount;
		}

		@Override
		public void handleResponse(Command command, ICharacter character) {
			if (command.getCmd().toLowerCase().startsWith("y")) {
				character.removeGold(amount);
				itemToBuy.getContainer().removeItem(itemToBuy.getItem());
				itemToBuy.getItem().removeCommandCondition(TakeCommand.class);
				character.addItem(itemToBuy.getItem());
				character.sendMessage("You purchase the " + itemToBuy.getItem().getDescription());				
			}
		}
	}
}
