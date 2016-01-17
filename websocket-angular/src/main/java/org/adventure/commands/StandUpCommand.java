package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.springframework.stereotype.Component;

@Component
public class StandUpCommand extends Action {

	public StandUpCommand() {
		super();
		this.addCommandPattern("stand");
		this.addCommandPattern("stand up");	
	}

	@Override
	public void action(Command command, ICharacter character) {
		if (character.getPlayerState().equals(PlayerState.STANDING) == false) {
			if (character.getPlayerState().equals(PlayerState.LAYING)) {
				character.addBusyFor(8);
			}
			else if (character.getPlayerState().equals(PlayerState.KNELLING)) {
				character.addBusyFor(3);
			}
			else if (character.getPlayerState().equals(PlayerState.SITTING)) {
				character.addBusyFor(5);
			}
			character.setState(PlayerState.STANDING);
		}
		else {
			character.sendMessage("You are all ready standing.");
		}
	}

}
