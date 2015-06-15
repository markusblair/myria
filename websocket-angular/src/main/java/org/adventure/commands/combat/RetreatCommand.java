package org.adventure.commands.combat;

import org.adventure.CharacterGroup;
import org.adventure.character.ICharacter;
import org.adventure.commands.Command;

public class RetreatCommand  extends CharacterCommand {

	public RetreatCommand() {
		super();
		this.addCommandPattern("retreat");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ICharacter target = getCharacter("<character>", character);
		CharacterGroup characterGroup = character.getCurrentRoom().getCharacterGroup(character);
		if (characterGroup != null) {
			characterGroup.removeCharacter(character);
			character.sendMessage("You retreat.");
		}
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result = super.isAllowed(character);
		if (character.getCurrentRoom().getCharacterGroup(character) == null) {
			character.sendMessage("You are not engaged in combat.");
			result =  false;
		}
		return result;
		
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		return 6;
	}
 
}
