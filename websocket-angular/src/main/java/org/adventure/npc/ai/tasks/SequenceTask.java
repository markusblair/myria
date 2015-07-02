package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;


/**
 * The Sequence Task will run the tasks in sequence until they all complete successfully or one fails.
 * @author blairma
 *
 */
public class SequenceTask extends ParentTask {
	private int currentTaskIndex = 0;

	@Override
	public boolean checkConditions(BTContext context) {
		boolean result = super.checkConditions(context);
		if (result) {
			for (int i = 0; i < getChildCount(); i++) {
				if (checkChildCondition(i, context)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		BTTaskContext taskContext = context.getTaskContext(this);
		taskContext.setTaskState(BTTaskState.FAILURE);
		int startIndex = currentTaskIndex;
		for (int i = startIndex; i < getChildCount(); i++) {
			currentTaskIndex = i;
			if (checkChildCondition(i, context)) {
				BTTaskState state = doChildAction(i, context);
				if (state.equals(BTTaskState.FAILURE)) {
					break;
				}				
				else if (state.equals(BTTaskState.SUCESS)){
				}
				else {
					break;
				}
			}
		}
		if (taskContext.getTaskState().equals(BTTaskState.SUCESS)) {
			currentTaskIndex = 0;			
		}
	}

 
}
