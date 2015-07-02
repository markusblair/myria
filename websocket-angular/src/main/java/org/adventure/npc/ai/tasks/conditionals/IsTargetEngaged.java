package org.adventure.npc.ai.tasks.conditionals;

import org.adventure.CharacterGroup;
import org.adventure.character.ICharacter;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;

public class IsTargetEngaged extends ConditionalTask {

	@Override
	public boolean determineTaskToRun(BTContext context) {
		ICharacter target = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		CharacterGroup characterGroup =  monster.getCurrentRoom().getCharacterGroup(monster);
		if (characterGroup != null) {
			return characterGroup.getCharacters().contains(target);
		}
		return false;
	}

}
