package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;
import org.adventure.npc.ai.Task;
import org.junit.Assert;
import org.junit.Test;

public class InvertTaskTest {


	@Test
	public void testInvert() {
		InvertTask invertTask = new InvertTask();
		TestTask testTask = new TestTask(BTTaskState.FAILURE);
		invertTask.add(testTask);
		BTContext context = new BTContext();
		invertTask.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getTaskContext(invertTask).getTaskState());
		Assert.assertEquals(2, context.getTaskCount());
		
		testTask.setResult(BTTaskState.SUCESS);
		invertTask.doAction(context);
		Assert.assertEquals(BTTaskState.FAILURE, context.getTaskContext(invertTask).getTaskState());
		
		testTask.setResult(BTTaskState.PROCESSING);
		invertTask.doAction(context);
		Assert.assertEquals(BTTaskState.PROCESSING, context.getTaskContext(invertTask).getTaskState());
	}
	
	@Test
	public void testInvertInSequence() {
		TestTask taskToUpdate = new TestTask(BTTaskState.SUCESS);
		Task inverTask = new InvertTask().add(taskToUpdate);
		ParentTask task = new SequenceTask().add(inverTask).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(3, context.getTaskCount());	
	}
	
	@Test
	public void testInvertInSequenceFailure() {
		TestTask taskToUpdate = new TestTask(BTTaskState.FAILURE);
		Task inverTask = new InvertTask().add(taskToUpdate);
		ParentTask task = new SequenceTask().add(inverTask).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(4, context.getTaskCount());	
	}
}
