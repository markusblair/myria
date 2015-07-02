package org.adventure.npc.ai.tasks.conditionals;

import org.adventure.npc.ai.AlwaysFailTask;
import org.adventure.npc.ai.AlwaysSucceedTask;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTParentTaskContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;
import org.adventure.npc.ai.Task;

public abstract class ConditionalTask extends ParentTask {
	private Task trueTask = new AlwaysSucceedTask();
	private Task falseTask = new AlwaysFailTask();
	
	public abstract boolean determineTaskToRun(BTContext context);
	
	@Override
	public boolean checkConditions(BTContext context) {
		return true;
	}
	
	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		BTParentTaskContext currentTaskContext = (BTParentTaskContext) context.getTaskContext(this);
		int i = 0;
		if (determineTaskToRun(context) == false) {
			i = 1;
		}
		currentTaskContext.setIndex(i);
		if (checkChildCondition(i, context)) {
			BTTaskState state = doChildAction(i, context);
			currentTaskContext.setTaskState(state);
		}
	}
	
	@Override
	public ParentTask add(Task childTask) {
			throw new RuntimeException("Use ifTrue and ifFalse methods to add tasks.");
	}
	
	public ConditionalTask ifTrue(Task task) {
			trueTask = task;
			return this;
	}
	
	public ConditionalTask ifFalse(Task task) {
		falseTask = task;
		return this;
	}



	@Override
	public Task getTaskIndex(int index) {
		if (index == 0) {
			return trueTask;
		}
		else {
			return falseTask;
		}
	}
	
	
	
}
