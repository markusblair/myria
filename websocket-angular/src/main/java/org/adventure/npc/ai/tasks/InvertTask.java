package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTParentTaskContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;
import org.adventure.npc.ai.Task;

public class InvertTask extends ParentTask {

	public InvertTask() {
	}

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		BTParentTaskContext currentTaskContext = (BTParentTaskContext) context.getTaskContext(this);
		int i = 0;
		currentTaskContext.setIndex(i);
		if (checkChildCondition(i, context)) {
			BTTaskState state = doChildAction(i, context);
			if (state.equals(BTTaskState.FAILURE)) {
				currentTaskContext.setTaskState(BTTaskState.SUCESS);
			} else if (state.equals(BTTaskState.SUCESS)) {
				currentTaskContext.setTaskState(BTTaskState.FAILURE);
			} else {
			}
		}
	}

	@Override
	public ParentTask add(Task childTask) {
		if (this.getChildCount() > 0) {
			throw new RuntimeException("Only one child task is allowed for invert task.");
		}
		return super.add(childTask);
	}
	
	
}
