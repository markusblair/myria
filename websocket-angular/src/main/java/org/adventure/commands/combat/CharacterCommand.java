package org.adventure.commands.combat;

import org.adventure.CharacterGroup;
import org.adventure.character.ICharacter;
import org.adventure.character.IWebSocketDataService;
import org.adventure.commands.Action;
import org.adventure.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class CharacterCommand extends Action {

	@Autowired
	public IWebSocketDataService characterSession;
	
	Command command;
	
	@Override
	public void action(Command command, ICharacter character) {
		this.command = command;
	}

	public ICharacter getCharacter(String characterToken, ICharacter character) {
		String characterName = this.command.getItem(characterToken);
		ICharacter target = character.getCurrentRoom().getCharacter(characterName);
		if (target == null) {
			character.sendMessage(new StringBuilder("Could not find ").append(characterName).toString());
		}
		
		return target;
	}
	
	public ICharacter getCharacterFromGroup(String characterToken, ICharacter character, CharacterGroup characterGroup) {
		String characterName = this.command.getItem(characterToken);
		ICharacter target = characterGroup.getCharacter(characterName);
		return target;
	}
}
