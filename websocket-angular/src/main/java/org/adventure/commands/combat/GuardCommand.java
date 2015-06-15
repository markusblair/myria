package org.adventure.commands.combat;

public class GuardCommand extends CharacterCommand {

	public GuardCommand() {
		super();
		this.addCommandPattern("guard <character>");
	}

}
