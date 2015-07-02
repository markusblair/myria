package org.adventure.npc.ai.tasks;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.EngageCommand;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngageTargetTask extends Task {
	Logger log = LoggerFactory.getLogger(EngageTargetTask.class);
	@Override
	public boolean checkConditions(BTContext context) {
		return true;
	}

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		ICharacter target = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		log.info(monster.getName() + " engages " + target.getName());
		Command command = new Command("Engage "+ target.getName());
		EngageCommand engageCommand = new EngageCommand();
		engageCommand.matches(command);
		engageCommand.performAction(command, monster);
		context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);
	}


}
