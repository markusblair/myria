package org.adventure;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;

public interface IPlayerPrompt {
	public void handleResponse(Command command, ICharacter character);
}
