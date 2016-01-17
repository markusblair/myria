package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IItem;
import org.springframework.stereotype.Component;

@Component
public class WearCommand extends ItemCommand {
	public WearCommand() {
		super();
		this.addCommandPattern("wear <item>");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		IItem leftHandItem = character.getLeftHand();
		IItem rightHandItem = character.getRightHand();
		String itemName = this.command.getItem("<item>");
		IItem itemToWear = null;
		if ((leftHandItem != null && leftHandItem.is(itemName)) || (rightHandItem != null && rightHandItem.is(itemName))) {
			if (leftHandItem != null && leftHandItem.is(itemName)) {
				itemToWear = leftHandItem;
			} else if (rightHandItem != null && rightHandItem.is(itemName)) {
				itemToWear = rightHandItem;
			}
			if (character.wear(itemToWear)) {
				if (leftHandItem != null && leftHandItem.is(itemName)) {
					character.setLeftHand(null);
				} else if (rightHandItem != null && rightHandItem.is(itemName)) {
					character.setRightHand(null);
				}
				character.sendMessage(new StringBuilder("You put on the ").append(itemToWear.getDescription()).toString());
				character.sendMessageToRoom(new StringBuilder(character.getName()).append(" puts on a ").append(itemToWear.getDescription()).toString());
			}
		}
	}
}
