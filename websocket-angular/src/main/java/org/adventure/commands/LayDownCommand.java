package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.springframework.stereotype.Component;

@Component
public class LayDownCommand extends Action {

	public LayDownCommand() {
		super();
		this.addCommandPattern("lay");
		this.addCommandPattern("lay down");	
	}

	@Override
	public void action(Command command, ICharacter character) {
		character.setState(PlayerState.LAYING);
	}
	
	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result = super.isAllowed(character);
		if (PlayerState.LAYING.equals(character.getPlayerState())) {
			character.sendMessage("You are all ready laying down.");
			result = false;
		}
		return result;
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		return 2;
	}

	
}
