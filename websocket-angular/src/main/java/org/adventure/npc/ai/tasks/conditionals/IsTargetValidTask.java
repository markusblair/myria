package org.adventure.npc.ai.tasks.conditionals;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsTargetValidTask extends ConditionalTask {

	Logger log = LoggerFactory.getLogger(IsTargetValidTask.class);
	
	@Override
	public boolean determineTaskToRun(BTContext context) {
		boolean validTarget = false;
		ICharacter currentTarget = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		if (currentTarget != null 
				&& monster.getCurrentRoom().equals(currentTarget.getCurrentRoom())
				&& PlayerState.DEAD.equals(currentTarget.getPlayerState()) == false
				) {
			validTarget = true;
			log.debug("Target is valid:" + currentTarget.getName());
		}
		if (currentTarget == null) {
			log.debug("Current Target is null");
		} else if (validTarget == false) {
			log.debug("Target is not valid: monsterRoom=" + monster.getCurrentRoom().getId() +
					" characterRoom=" +currentTarget.getCurrentRoom().getId() + 
					" targetState=" + currentTarget.getPlayerState());
		}
		return validTarget;
	}


}
