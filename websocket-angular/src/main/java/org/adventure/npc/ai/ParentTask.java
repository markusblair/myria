package org.adventure.npc.ai;

import java.util.ArrayList;

public abstract class ParentTask extends Task{
	protected ArrayList<Task> childTasks = new ArrayList<Task>();
	
	@Override
	public boolean checkConditions(BTContext context) {
		return (childTasks.size() > 0);
	}
	
	public ParentTask add(Task childTask) {
		childTasks.add(childTask);
		return this;
	}

	public int getChildCount() {
		return this.childTasks.size();
	}
	
	public Task getTaskIndex(int index) {
		return this.childTasks.get(index);
	}
	
	public BTTaskState doChildAction(int i, BTContext context) {
		Task task = this.getTaskIndex(i);
		task.doAction(context);
		return context.getTaskContext(task).getTaskState();
	}
	
	public boolean checkChildCondition(int i, BTContext context) {
		return this.getTaskIndex(i).checkConditions(context);
	}

	@Override
	public void doAction(BTContext context) {
		
		super.doAction(context);
	}
	
	
	@Override
	protected BTTaskContext createTaskContext(BTTaskContext taskContext) {
		BTParentTaskContext parentTaskContext = new BTParentTaskContext(taskContext);
		return parentTaskContext;
	}
	
}
