package org.adventure.commands;

import java.util.Collection;
import java.util.List;

import org.adventure.character.ICharacter;
import org.adventure.items.IWearable;
import org.springframework.stereotype.Component;

@Component
public class MeCommand extends Action {

	public MeCommand() {
		super();
		this.addCommandPattern("me");
	}

	@Override
	public void action(Command command, ICharacter character) {
		String me = getMeString(character);
		character.sendMessage(me);
		character.sendCharacter();
	}

	private String getMeString(ICharacter character) {
		StringBuilder result = new StringBuilder();
		result.append("Exp:");
		result.append(character.getExperience());
		result.append(" You are wearing: ");
		Collection<List<IWearable>> clothes = character.getClothing().values();
		int clothingItemsWorn =0;
		for (List<IWearable> list : clothes) {
			for (IWearable iWearable : list) {
				result.append(iWearable.getDescription());
				clothingItemsWorn++;
			}
		}
		if (clothingItemsWorn ==0) {
			result.append(" Nothing At All! ");
		}
		if (character.getRightHand() != null) {
			result.append("You are carring " + character.getRightHand().getDescription() + " in your right hand.");
		}
		if (character.getLeftHand() != null) {
			result.append("You are carring " + character.getLeftHand().getDescription() + " in your left hand.");
		}
		return result.toString();
	}
	
}
