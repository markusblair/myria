package org.adventure.npc.ai;

public class AlwaysSucceedTask extends Task {


	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);
	}

}
