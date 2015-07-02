package org.adventure.npc.ai.tasks;

import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTParentTaskContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.ParentTask;

/**
 * The SelectorTask will go through a list of task executing each task in order until one is successful.
 * @author blairma
 *
 */
public class SelectorTask extends ParentTask {
	

	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		BTParentTaskContext currentTaskContext = (BTParentTaskContext)context.getTaskContext(this);
		int startIndex = currentTaskContext.getIndex();
		for (int i = startIndex; i < getChildCount(); i++) {
			currentTaskContext.setIndex(i);
			if (checkChildCondition(i, context)) {
				BTTaskState state = doChildAction(i, context);
				if (state.equals(BTTaskState.FAILURE)) {
				} else if (state.equals(BTTaskState.SUCESS)) {
					currentTaskContext.setIndex(0);
					break;
				}
				else {
					break;
				}
			}
		}
	}

	
}
