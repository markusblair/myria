package org.adventure.commands;

import org.adventure.character.ICharacter;


public abstract class CommandCondition {
	private CommandCondition commandCondition;
	
	protected abstract boolean conditional(ICharacter character);
	
	public boolean testCondition(ICharacter playerCharacter) {
		if (conditional(playerCharacter)) {
			if (commandCondition != null) {
				return commandCondition.testCondition(playerCharacter);
			}
			return true;
		}
		return false;
	}
	
	public CommandCondition setCommandConditionChain(CommandCondition commandCondition) {
		this.commandCondition = commandCondition;
		return this;
	}
}
