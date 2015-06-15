package org.adventure.commands;

import org.adventure.PlayerState;
import org.adventure.character.ICharacter;
import org.adventure.commands.combat.CharacterCommand;

public class DragCommand extends CharacterCommand {

	public DragCommand() {
		super();
		this.addCommandPattern("drag <character>");
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ICharacter dragCharacter = getCharacter("<character>", character);
		if (character != null) {
			if (PlayerState.DEAD.equals(dragCharacter.getPlayerState())) {
				character.addItem(dragCharacter);				
				character.sendMessage("You begin to drag " + dragCharacter.getName());
			}
		}
	}

}
