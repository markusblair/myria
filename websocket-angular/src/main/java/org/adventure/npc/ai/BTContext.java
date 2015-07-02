package org.adventure.npc.ai;

import java.util.HashMap;
import java.util.Map;

import org.adventure.character.CharacterData;
import org.adventure.character.ICharacter;
import org.adventure.character.PlayerCharacter;
import org.adventure.monster.Monster;


public class BTContext {
	public static String MONSTER = "monster";
	public static String TARGET = "target";
	BTTaskContext rootTaskContext = new BTTaskContext(null);
	int taskCount = 0;
	Task currentRunningTask;
	Map<Task, BTTaskContext> contextMap = new HashMap<Task, BTTaskContext>();
	
	public BTContext() {
		super();
	}

	public void incrementTaskCount() {
		this.taskCount++;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public Task getCurrentRunningTask() {
		return currentRunningTask;
	}

	public void setCurrentRunningTask(Task newTask) {
		if (this.contextMap.containsKey(newTask) == false) {
			BTTaskContext currentTaskContext = this.contextMap.get(this.currentRunningTask);
			if (this.currentRunningTask == null) {
				currentTaskContext = rootTaskContext;
			}
			this.contextMap.put(newTask, newTask.createTaskContext(currentTaskContext));
		}
		this.currentRunningTask = newTask;
	}
	
	public BTTaskContext getTaskContext(Task task) {
		if (this.contextMap.containsKey(task) == false) {
			BTTaskContext currentTaskContext = this.contextMap.get(this.currentRunningTask);
			if (this.currentRunningTask == null) {
				currentTaskContext = rootTaskContext;
			}
			this.contextMap.put(task, task.createTaskContext(currentTaskContext));
		}
		return this.contextMap.get(task);
	}
	
	public BTTaskContext getCurrentTaskContext() {
		return this.contextMap.get(this.currentRunningTask);
	}
	
	public void put(String variable, Object value) {
		this.rootTaskContext.put(variable, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getCurrentRunningTask() != null) {
			if (getCurrentTaskContext().get(MONSTER) != null) {
				sb.append("Monster:" + ((ICharacter)getCurrentTaskContext().get(MONSTER)).getName());				
			}
			if (getCurrentTaskContext().get(TARGET) != null) {
				sb.append(", Target:" + ((ICharacter)getCurrentTaskContext().get(TARGET)).getName());							
			}
		}
		
		return sb.toString();
	}
	
	
	
}


