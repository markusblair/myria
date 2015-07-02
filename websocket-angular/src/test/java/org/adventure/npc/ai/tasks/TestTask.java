package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;

public class TestTask extends Task {
	private static int nextId = 0;
	private int id;
	private BTTaskState result;
	
	public TestTask(BTTaskState result) {
		super();
		this.result = result;
		id = nextId++;
	}

	@Override
	public boolean checkConditions(BTContext context) {
		return true;
	}

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		context.getCurrentTaskContext().setTaskState(result);
	}

	public void setResult(BTTaskState result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TestTask [id=" + id + ", result=" + result + "]";
	}

	
	
}
