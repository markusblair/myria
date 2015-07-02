package org.adventure.npc.ai;

import java.util.HashMap;
import java.util.Map;

public class BTTaskContext {
	private BTTaskState taskState;
	private BTTaskContext parentContext;
	private BTContext rootContext;
	
	private Map<String, Object> variables = new HashMap<String, Object>();
	
	public BTTaskContext(BTTaskContext parentContext) {
		super();
		this.parentContext = parentContext;
	}

	public void put(String name, Object value) {
		variables.put(name, value);
	}
	
	public Object get(String name) {
		if (variables.containsKey(name)) {
			return variables.get(name);			
		} 
		else if (this.parentContext != null){
			return this.parentContext.get(name);
		}
		return null;
	}
	public BTTaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(BTTaskState taskState) {
		this.taskState = taskState;
	}

	public BTContext getRootContext() {
		return rootContext;
	}
	
	
}
