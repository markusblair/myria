package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;
import org.adventure.npc.ai.Task;
import org.junit.Assert;
import org.junit.Test;

public class BehaviorTreeTest {

	@Test
	public void SequenceTest1() {
		ParentTask task = new SequenceTask().add(new TestTask(BTTaskState.SUCESS)).add(new TestTask(BTTaskState.SUCESS)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(4, context.getTaskCount());
	}

	@Test
	public void SequenceTest2() {
		ParentTask task = new SequenceTask().add(new TestTask(BTTaskState.SUCESS)).add(new TestTask(BTTaskState.FAILURE)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.FAILURE, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(3, context.getTaskCount());
	}
	
	@Test
	public void SequenceTest3() {
		TestTask taskToUpdate = new TestTask(BTTaskState.PROCESSING);
		ParentTask task = new SequenceTask().add(new TestTask(BTTaskState.SUCESS)).add(taskToUpdate).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.PROCESSING, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(3, context.getTaskCount());	
	}
	
	@Test
	public void SequenceTest4() {
		ParentTask task = new SequenceTask().add(new TestTask(BTTaskState.SUCESS));
		TestTask taskToUpdate = new TestTask(BTTaskState.PROCESSING);
		task.add(taskToUpdate).add(new TestTask(BTTaskState.SUCESS)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		
		taskToUpdate.setResult(BTTaskState.SUCESS);
		task.doAction(context);
		Assert.assertEquals(7, context.getTaskCount());
		taskToUpdate.setResult(BTTaskState.SUCESS);		
	}
	
	@Test
	public void selectorTest1() {
		ParentTask task = new SelectorTask().add(new TestTask(BTTaskState.FAILURE)).add(new TestTask(BTTaskState.FAILURE)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(4, context.getTaskCount());
	}
	
	@Test
	public void selectorTest2() {
		Task task = new SelectorTask().add(new TestTask(BTTaskState.SUCESS)).add(new TestTask(BTTaskState.FAILURE)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount());
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(2, context.getTaskCount());
	}
	
	@Test
	public void selectorTest3() {
		Task task = new SelectorTask().add(new TestTask(BTTaskState.FAILURE)).add(new TestTask(BTTaskState.PROCESSING)).add(new TestTask(BTTaskState.SUCESS));
		BTContext context = new BTContext();
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount()); 
		Assert.assertEquals(BTTaskState.PROCESSING, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(3, context.getTaskCount());
	}
	
	@Test
	public void multiParentTest1() {
		TestTask longRunningTask = new TestTask(BTTaskState.PROCESSING);
		TestTask longRunningTask2 = new TestTask(BTTaskState.PROCESSING);
		Task sequenceTask = new SequenceTask()
				.add(new TestTask(BTTaskState.SUCESS))
				.add(longRunningTask2);
		Task task = new SelectorTask()
						.add(new TestTask(BTTaskState.FAILURE))
						.add(longRunningTask)
						.add(sequenceTask);
		BTContext context = new BTContext();
		
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount()); 
		Assert.assertEquals(BTTaskState.PROCESSING, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(3, context.getTaskCount());
		
		longRunningTask.setResult(BTTaskState.FAILURE);
		task.doAction(context);
		context.getCurrentRunningTask().doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount()); 
		Assert.assertEquals(BTTaskState.PROCESSING, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(9, context.getTaskCount());
		
		longRunningTask2.setResult(BTTaskState.SUCESS);
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount()); 
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(12, context.getTaskCount());
		
		task.doAction(context);
		System.out.println(context.getCurrentTaskContext().getTaskState());
		System.out.println(context.getTaskCount()); 
		Assert.assertEquals(BTTaskState.SUCESS, context.getCurrentTaskContext().getTaskState());
		Assert.assertEquals(18, context.getTaskCount());
	}
}
