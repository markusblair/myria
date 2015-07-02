package org.adventure.npc.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Task {
	Logger log = LoggerFactory.getLogger(Task.class);
	public Task() {
	}

	public boolean checkConditions(BTContext context) {
		log.debug("Check Conditions:" + this.getClass().getName() + " - " + context);
		return true;
	}
	
	public void doAction(BTContext context) {
		log.debug("Do Action:" + this.getClass().getName() + " - " + context);
		context.incrementTaskCount();
		context.setCurrentRunningTask(this);
		context.getTaskContext(this).setTaskState(BTTaskState.PROCESSING);
	};
	
	
	protected BTTaskContext createTaskContext(BTTaskContext taskContext) {
		return new BTTaskContext(taskContext);
	}
	

}
