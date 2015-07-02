package org.adventure.npc.ai.tasks.conditionals;

import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.adventure.random.SkillCategory;

public class IsUsingMeleeWeapon extends ConditionalTask {

	@Override
	public boolean determineTaskToRun(BTContext context) {
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		return monster.getWeapon().getWeaponType().inSkillCategory(SkillCategory.MELEE_ATTACK);
	}

}
