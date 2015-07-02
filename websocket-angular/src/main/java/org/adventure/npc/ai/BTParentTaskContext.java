package org.adventure.npc.ai;

public class BTParentTaskContext  extends BTTaskContext {

	int index;
	
	public BTParentTaskContext(BTTaskContext parentContext) {
		super(parentContext);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
	
}
