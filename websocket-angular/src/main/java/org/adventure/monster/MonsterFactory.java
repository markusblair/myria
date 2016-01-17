package org.adventure.monster;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.character.WebSocketDataService;
import org.adventure.items.armor.ArmorFactory;
import org.adventure.items.weapons.DamageType;
import org.adventure.items.weapons.Weapon;
import org.adventure.items.weapons.WeaponFactory;
import org.adventure.monster.ai.ITargetSelector;
import org.adventure.npc.ai.BTLibrary;
import org.adventure.npc.ai.Task;
import org.adventure.npc.ai.tasks.AttackTask;
import org.adventure.npc.ai.tasks.ClearTargetTask;
import org.adventure.npc.ai.tasks.EngageTargetTask;
import org.adventure.npc.ai.tasks.MoveTask;
import org.adventure.npc.ai.tasks.SelectorTask;
import org.adventure.npc.ai.tasks.SequenceTask;
import org.adventure.npc.ai.tasks.conditionals.IsTargetValidTask;
import org.adventure.npc.ai.tasks.targetselectors.SelectTargetTask;
import org.adventure.random.SkillType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MonsterFactory implements IMonsterFactory {
	Logger log = LoggerFactory.getLogger(MonsterFactory.class);
	ArmorFactory armorFactory = new ArmorFactory();
	WeaponFactory weaponFactory = new WeaponFactory();
	/* (non-Javadoc)
	 * @see org.adventure.monster.IMonsterFactory#createMonster()
	 */
	
	@Autowired
	private WebSocketDataService webSocketDataService;
	
	@Override
	public Monster createMonster() {
		Monster monster;
		int level = getLevel();
		ITargetSelector targetSelector;
		switch (level) {
		case 1:
			monster = new Monster("Goblin", 0, 15, 4, webSocketDataService);
			monster.setSpeed(10);
			monster.setStrength(4);
			monster.setAgility(8);
			monster.setWill(2);
			monster.addSkillLevel(SkillType.MELEE);
//			targetSelector = new DefaultTargetSelector(monster);
			

//			monster.getAiManager().addChainHandler(new BattleReadyTargetIsAvailable(targetSelector));
//			monster.getAiManager().addChainHandler(new FleeWhenOverwhelmed(2));
//			monster.getAiManager().addChainHandler(new EngageTarget(targetSelector));
//			monster.getAiManager().addChainHandler(new AttackTarget(targetSelector));
			break;
		case 2:
			monster = new Monster("Kobold", 0, 25, 4, webSocketDataService);
			monster.setSpeed(8);
			monster.setStrength(6);
			monster.setAgility(10);
			monster.setWill(23);
			monster.addSkillLevel(SkillType.MELEE);
			Task parentTask = new SelectorTask().add(new SelectTargetTask())
					.add(new SequenceTask()
							.add(new EngageTargetTask())
							.add(new AttackTask()));
				monster.setMonsterBehaviorTree(parentTask);	
			
//			targetSelector = new DefaultTargetSelector(monster);
//			monster.getAiManager().addChainHandler(new BattleReadyTargetIsAvailable(targetSelector));
//			monster.getAiManager().addChainHandler(new FleeWhenOverwhelmed(2));
//			monster.getAiManager().addChainHandler(new EngageTarget(targetSelector));
//			monster.getAiManager().addChainHandler(new AttackTarget(targetSelector));
			break;
		case 3:
			monster = new Monster("Orc", 0, 40, 4, webSocketDataService);
			monster.setSpeed(6);
			monster.setStrength(15);
			monster.setAgility(8);
			monster.setWill(5);
			monster.addSkillLevel(SkillType.MELEE);
			monster.addSkillLevel(SkillType.MELEE);
//			targetSelector = new DefaultTargetSelector(monster);
//			monster.getAiManager().addChainHandler(new BattleReadyTargetIsAvailable(targetSelector));
//			monster.getAiManager().addChainHandler(new FleeWhenOverwhelmed(0.5f));
//			monster.getAiManager().addChainHandler(new EngageTarget(targetSelector));
//			monster.getAiManager().addChainHandler(new AttackTarget(targetSelector));
			break;
		default:
			monster = new Monster("Rat", 0, 5, 6, webSocketDataService);
			Weapon w = new Weapon().setWeaponType(SkillType.HAND_TO_HAND).addDamage("Bite", DamageType.PIERCE,  2, 1);
			monster.setDefaultWeapon(w);
			monster.setWill(1);
//			targetSelector = new DefaultTargetSelector(monster);
//			monster.getAiManager().addChainHandler(new BattleReadyTargetIsAvailable(targetSelector));
//			monster.getAiManager().addChainHandler(new FleeWhenOverwhelmed(4f));
//			monster.getAiManager().addChainHandler(new AttackTarget(targetSelector));
			break;
		} 
		monster.setMonsterBehaviorTree(
				new SelectorTask()
					.add(new IsTargetValidTask()
						.ifTrue(BTLibrary.DEFAULT_ATTACK_TREE)
						.ifFalse(new SequenceTask()
							.add(new ClearTargetTask())
							.add(new SelectTargetTask())
							)
						)
					.add(new MoveTask())
					);
		if (level > 0)
			addItems(monster);
		return monster;
	}
	
	public int getLevel() {
		return ThreadLocalRandom.current().nextInt(4);
	}

	private void addItems(Monster monster) {
		log.debug("Adding armor and weapons.");
		monster.addItem(weaponFactory.createItem());
		monster.wear(armorFactory.createItem());
	}
}
