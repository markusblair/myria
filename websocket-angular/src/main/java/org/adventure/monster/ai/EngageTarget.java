package org.adventure.monster.ai;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.EngageCommand;
import org.adventure.monster.Monster;
import org.adventure.random.SkillCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngageTarget implements IAiChainHandler {
	Logger log = LoggerFactory.getLogger(EngageTarget.class);
	private ITargetSelector targetSelector;
	
	public EngageTarget(ITargetSelector targetSelector) {
		this.targetSelector = targetSelector;
	}

	@Override
	public boolean handle(Monster monster) {
		log.info("Check melee weapon :" + monster.getWeapon().getWeaponType());
		if (monster.getWeapon().getWeaponType().inSkillCategory(SkillCategory.MELEE_ATTACK)) {
			log.info(monster.getName() + " melee weapon.");
			ICharacter target = this.targetSelector.currentTarget();
			if (target == null) {
				log.info("No current target.");
				target = this.targetSelector.selectCharacterFromList(monster.getCurrentRoom().getCharacters());
				log.info("found target to engage." + target);
				if (target != null) {
					log.info(monster.getName() + " engage");
					Command command = new Command("Engage "+ target.getName());
					EngageCommand engageCommand = new EngageCommand();
					engageCommand.matches(command);
					engageCommand.performAction(command, monster);
					return true;					
				}
			}
			else {
				log.info("current target=" + target);
			}
		}
		return false;
	}

}
