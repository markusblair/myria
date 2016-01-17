package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IItem;
import org.springframework.stereotype.Component;

@Component
public class SwapCommand extends Action {

	public SwapCommand() {
		this.addCommandPattern("swap");
	}

	@Override
	public void action(Command command, ICharacter character) {
		if (character.getFreeHands() == 1) {
			IItem rightHandItem = character.getRightHand();
			IItem leftHandItem = character.getLeftHand();
			character.setLeftHand(rightHandItem);
			character.setRightHand(leftHandItem);
			character.sendCharacter();
		}
	}

}
