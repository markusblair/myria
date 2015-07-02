package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;

public class ClearTargetTask extends Task {

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		context.put(BTContext.TARGET, null);
		context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);
	}


}
