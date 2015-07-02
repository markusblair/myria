package org.adventure.npc.ai;

import org.adventure.npc.ai.tasks.AttackTask;
import org.adventure.npc.ai.tasks.ClearTargetTask;
import org.adventure.npc.ai.tasks.EngageTargetTask;
import org.adventure.npc.ai.tasks.SelectorTask;
import org.adventure.npc.ai.tasks.conditionals.IsTargetEngaged;
import org.adventure.npc.ai.tasks.conditionals.IsUsingMeleeWeapon;

public class BTLibrary {

	public static Task DEFAULT_ATTACK_TREE = new IsUsingMeleeWeapon()
		.ifTrue(new IsTargetEngaged()
			.ifTrue(new AttackTask())
			.ifFalse(new SelectorTask()
				.add(new EngageTargetTask())
				.add(new ClearTargetTask())
				)
			)
		.ifFalse(new AttackTask());
	
}
