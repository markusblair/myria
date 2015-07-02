package org.adventure.npc.ai;

public class AlwaysFailTask extends Task {

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		context.getTaskContext(this).setTaskState(BTTaskState.FAILURE);
	}


}
