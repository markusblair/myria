package org.adventure.commands.combat;

import org.adventure.CharacterGroup;
import org.adventure.character.ICharacter;
import org.adventure.commands.Command;

public class EngageCommand extends CharacterCommand {

	public EngageCommand() {
		super();
		this.addCommandPattern("engage <character>");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ICharacter target = getCharacter("<character>", character);
		character.getCurrentRoom().joinCharacterGroup(character, target);
		character.sendMessage("You engage the " + target.getName() + ".");
		character.sendMessageToRoom(character.getName() + " enagages " + target.getName());
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result = super.isAllowed(character);
		CharacterGroup characterGroup = character.getCurrentRoom().getCharacterGroup(character);
		if (characterGroup != null) {
			character.sendMessage("You are all ready engaged in combat.");
			result =  false;
		}
		return result;
		
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		return 6;
	}
 
}
