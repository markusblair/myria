package org.adventure.commands;

import org.adventure.character.ICharacter;

public class MeCommand extends Action {

	public MeCommand() {
		super();
		this.addCommandPattern("me");
	}

	@Override
	public void action(Command command, ICharacter character) {
		String me = character.describeMe();
		character.sendMessage(me);
		character.sendCharacter();
	}

}
