package org.adventure.commands;

import org.adventure.PlayerState;
import org.adventure.character.ICharacter;

public class KnellCommand extends Action {

	public KnellCommand() {
		super();
		this.addCommandPattern("knell");
		this.addCommandPattern("knell down");	
	}

	@Override
	public void action(Command command, ICharacter character) {
		if (character.getPlayerState().equals(PlayerState.KNELLING) == false) {
			if (character.getPlayerState().equals(PlayerState.LAYING)) {
				character.addBusyFor(5);
			}
			else if (character.getPlayerState().equals(PlayerState.STANDING)) {
				character.addBusyFor(2);
			}
			else if (character.getPlayerState().equals(PlayerState.SITTING)) {
				character.addBusyFor(2);
			}
			character.setState(PlayerState.KNELLING);
		}
		else {
			character.sendMessage("You are all ready knelling.");
		}
	}

}
