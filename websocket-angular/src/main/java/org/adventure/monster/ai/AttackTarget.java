package org.adventure.monster.ai;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.AttackCommand;
import org.adventure.monster.Monster;

public class AttackTarget implements IAiChainHandler {
	private ITargetSelector targetSelector;
	
	public AttackTarget(ITargetSelector targetSelector) {
		this.targetSelector = targetSelector;
	}

	@Override
	public boolean handle(Monster monster) {
		ICharacter target = targetSelector.currentTarget();
		if (target != null) {
			Command command = new Command("Attack "+ target.getName());
			AttackCommand attackCommand = new AttackCommand();
			attackCommand.matches(command);
			attackCommand.performAction(command, monster);
			
			return true;
		}
		return false;
	}


}
