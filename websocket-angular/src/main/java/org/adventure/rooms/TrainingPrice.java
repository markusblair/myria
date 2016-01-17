package org.adventure.rooms;

import org.adventure.IPlayerPrompt;
import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.CommandCondition;
import org.adventure.items.search.ItemSearchResult;
import org.adventure.random.SkillType;

public class TrainingPrice extends CommandCondition implements ISalePrice{
	private int amount; 
	private SkillType skillType;
	
	public TrainingPrice(int amount, SkillType skillType) {
		super();
		this.skillType = skillType;
		this.amount = amount;
	}

	@Override
	public boolean conditional(ICharacter character) {
		StringBuilder msg = new StringBuilder();
		msg.append("Your current ");
		msg.append(this.skillType);
		msg.append(" skill level is ");
		msg.append(character.getSkill(skillType).getLevel());
		msg.append(".  You can increase your skill level for ");
		msg.append(getTrainingCost(character));
		msg.append(" gold and ");
		msg.append(getExperienceCost(character));
		msg.append(" experience points.");
		character.sendMessage(msg.toString());
		return false;
	}

	private int getTrainingCost(ICharacter character) {
		return character.getSkill(this.skillType).getLevel() * this.amount;
	}
	
	private int getExperienceCost(ICharacter character) {
		return character.getSkill(this.skillType).getLevel() * 5000;
	}
	
	public void buyItem(ICharacter character, ItemSearchResult itemSearchResult) {
		IPlayerPrompt result;
		int experienceCost = getExperienceCost(character);
		if (character.getGold().getAmount() > getTrainingCost(character) && character.getExperience() > experienceCost) {
			character.sendMessage(new StringBuilder("Do you want to buy the ")
				.append(itemSearchResult.getItem().getDescription())
				.append(" for ").append(getTrainingCost(character)).append(" gold and ")
				.append(experienceCost).append(" experience ")
				.append("? Y/N").toString());
			result = new BuyResponse(this.skillType, getTrainingCost(character));	
			character.setPlayerPrompt(result);
		}
		else {
			character.sendMessage("You don't have enough gold to purchase that.");
		}
	}

	class BuyResponse implements IPlayerPrompt {
		private SkillType skillType;
		private int amount;
		public BuyResponse(SkillType skillType, int amount) {
			super();
			this.skillType = skillType;
			this.amount = amount;
		}

		@Override
		public void handleResponse(Command command, ICharacter character) {
			if (command.getCmd().toLowerCase().startsWith("y")) {
				character.removeGold(amount);
				character.setExperience(character.getExperience() - getExperienceCost(character));
				character.addSkillLevel(skillType);
				character.sendMessage("You train in the " + skillType);				
			}
		}
	}

}
