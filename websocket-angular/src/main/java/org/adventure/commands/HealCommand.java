package org.adventure.commands;

import java.util.List;

import org.adventure.character.BodyPart;
import org.adventure.character.ICharacter;

public class HealCommand extends Action {

	public HealCommand() {
		super();
		this.addCommandPattern("heal");
	}

	@Override
	public void action(Command command, ICharacter character) {
		int maxHealth = character.getMaxHealth();
		character.addHealth(maxHealth);
		List<BodyPart> bodyParts = character.getBodyParts();
		for (BodyPart bodyPart : bodyParts) {
			bodyPart.setHealth(bodyPart.getMaxHealth());
			bodyPart.setInjury(null);
		}
		character.setMind(0);
		character.sendMessage("Ahh you feel much better?");
		character.sendMessageToRoom( character.getName() + " is healed.");
		character.sendCharacter();
	}

}
