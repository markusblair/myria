package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.springframework.stereotype.Component;

@Component
public class SitDownCommand extends Action {

	public SitDownCommand() {
		super();
		this.addCommandPattern("sit");
		this.addCommandPattern("sit down");	
	}

	@Override
	public void action(Command command, ICharacter character) {
		character.setState(PlayerState.SITTING);
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result =  super.isAllowed(character);
		if (character.getPlayerState().equals(PlayerState.SITTING)) {
			character.sendMessage("You are all ready sitting.");
			result = false;
		}
		return result;
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		int time = 0;
		if (character.getPlayerState().equals(PlayerState.LAYING)) {
			time = 3;
		}
		else if (character.getPlayerState().equals(PlayerState.STANDING)) {
			time = 2;
		}
		else if (character.getPlayerState().equals(PlayerState.KNELLING)) {
			time = 2;
		}
		return time;
	}

	
	
}
