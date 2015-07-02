package org.adventure.npc.ai.tasks;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.AttackCommand;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;

public class AttackTask extends Task {

	
	
	@Override
	public boolean checkConditions(BTContext context) {
		ICharacter target = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		return target != null;
	}

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		ICharacter target = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		
			Command command = new Command("Attack "+ target.getName());
			AttackCommand attackCommand = new AttackCommand();
			attackCommand.matches(command);
			attackCommand.performAction(command, monster);
			
		context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);
	}


}
