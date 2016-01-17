package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.springframework.stereotype.Component;

@Component
public class SayCommand extends Action {

	public SayCommand() {
		super();
		this.addCommandPattern("say <message>");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		String message = command.getItem("<message>");
		if (PlayerState.DEAD.equals(character.getPlayerState())) {
			character.sendMessageToRoom("The ghostly voice of " + character.getName() + " says '" + message + "'");
			character.sendMessage("You say in a ghostly voice, '" + message + "'");	
		}
		else {
			character.sendMessageToRoom(character.getName() + " says '" + message + "'");
			character.sendMessage("You say, '" + message + "'");			
		}
	}

}
