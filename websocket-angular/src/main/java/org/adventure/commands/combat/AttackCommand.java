package org.adventure.commands.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.adventure.CharacterGroup;
import org.adventure.PlayerState;
import org.adventure.character.BodyPart;
import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.items.armor.AttackResult;
import org.adventure.items.armor.DamageCalculation;
import org.adventure.items.weapons.DamageType;
import org.adventure.items.weapons.LoadableWeapon;
import org.adventure.items.weapons.Shield;
import org.adventure.items.weapons.Weapon;
import org.adventure.monster.Monster;
import org.adventure.random.IRandom;
import org.adventure.random.SkillCategory;
import org.adventure.random.SkillCheckResult;
import org.adventure.random.SkillType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttackCommand extends CharacterCommand {
	Logger log = LoggerFactory.getLogger(AttackCommand.class);
	public AttackCommand() {
		super();
		this.addCommandPattern("attack the <bodypart> of <character>");
		this.addCommandPattern("attack <character>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ICharacter defender = null;
		Weapon weapon = character.getWeapon();
		if (weapon.getWeaponType().inSkillCategory(SkillCategory.MELEE_ATTACK)) { //(false == true) {//
			CharacterGroup characterGroup = character.getCurrentRoom().getCharacterGroup(character);
			if (characterGroup != null) {
				defender = getCharacterFromGroup("<character>", character, characterGroup);				
			}
			if (defender == null) {
				character.sendMessage(new StringBuilder("Could not find target.  Do you need to engage them?").toString());
			}
		}
		else {
			defender = getCharacter("<character>", character);
		}
		if (character != null && defender != null) {
			attack(command, character, defender);
		}
	}
	
	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result =  super.isAllowed(character);
		
		if (character.getPlayerState().equals(PlayerState.UNCONSCIOUS) || character.getPlayerState().equals(PlayerState.STUNNED)) {
			character.sendMessage("You can't attack in your state.");
			result = false;
		}
		else {
			if (character.getWeapon() instanceof LoadableWeapon) {
				LoadableWeapon loadableWeapon = (LoadableWeapon) character.getWeapon();
				result = loadableWeapon.isLoaded();
				if (result == false) {
					character.sendMessage("You need to load your " + loadableWeapon.getName());
				}
			}
		}
		return result;
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		return character.getWeapon().getBaseAttackRate();
	}

	public void attack(Command command, ICharacter character, ICharacter defender) {
		if (defender.getHealth() > 0) {
			Weapon weapon = character.getWeapon();
			// Skill check to see if attacker hits defender.
			IRandom rndDefense = getDefLevel(defender, weapon.getWeaponType());
			SkillCheckResult hit = character.skillCheck(rndDefense, weapon.getWeaponType());
			AttackResult attackResult = new AttackResult(hit, character.getName(), defender.getName());
			attackResult.setAttackType(weapon.getName());
			log.debug(new StringBuilder(character.getName()).append(" attacks with ").append(weapon.getName()).toString());
			String attackType = weapon.getAttackType();
			if (hit.success()) {
				// If the defender has a shield give them a chance to block the
				// attack.
				if (defender.getLeftHand() != null && defender.getLeftHand() instanceof Shield) {
					IRandom shieldDefense = getDefLevel(defender, SkillType.SHIELD);
					hit = character.skillCheck(shieldDefense, weapon.getWeaponType());
					attackResult.setShieldCheck(hit);
				}
			}
			if (hit.success()) {
				
				BodyPart bodyPart = getBodyPartToAttack(command, defender);
				Map<DamageType, Integer> damages = null;
				
				if(hit.getValue1() > hit.getValue2() * 2) {
					//Critical hit.
					damages = weapon.getCriticalDamages(attackType);
				} else {
					damages = weapon.getDamages(attackType);				
				}
				DamageCalculation damage = defender.calculateDamage(damages, bodyPart);
				attackResult.setDamageCalculation(damage);
				attackResult.setBodyPartName(bodyPart.getName());
				int experienceModifier = (20 + defender.getTotalSkillLevels() - character.getTotalSkillLevels());
				if (experienceModifier > 0)
					character.addExperience(damage.getTotalDamage() * experienceModifier);
			} 
			if (character instanceof Monster) {
				defender.sendAttackResult(attackResult);			
			}
			else {
				character.sendAttackResult(attackResult);			
			}
			if (character.getWeapon() instanceof LoadableWeapon) {
				LoadableWeapon loadableWeapon = (LoadableWeapon) character.getWeapon();
				loadableWeapon.setLoaded(false);
			}
		}
		else {
			character.sendMessage("That is already dead.");
		}
	}

	private BodyPart getBodyPartToAttack(Command command, ICharacter defender) {
		BodyPart bodyPart;
		if (command != null && command.hasItem("<bodypart>")) {
			bodyPart = getBodyPart(command.getItem("<bodypart>"), defender);
		} else {
			List<BodyPart> bodyParts = defender.getBodyParts();
			List<BodyPart> bodyPartsToAttack = new ArrayList<BodyPart>();
			for (BodyPart bodyPart2 : bodyParts) {
				if (bodyPart2.getHealth() > 0 ) {
					bodyPartsToAttack.add(bodyPart2);
				}
			}
			if (bodyPartsToAttack.size() > 1) {
				int bodyIndex = ThreadLocalRandom.current().nextInt(bodyPartsToAttack.size() - 1);
				bodyPart = bodyPartsToAttack.get(bodyIndex);				
			}
			else if (bodyPartsToAttack.size() == 1) {
				int bodyIndex = 0;
				bodyPart = bodyPartsToAttack.get(bodyIndex);				
			}
			else {
				bodyPart = null;
			}
		}
		return bodyPart;
	}

	private IRandom getDefLevel(ICharacter character, SkillType attackType) {
		SkillType defenseSkillType = null;
		if (attackType.inSkillCategory(SkillCategory.MELEE_ATTACK)) {
			defenseSkillType = SkillType.PARRY_MELEE;
		} else if (attackType.inSkillCategory(SkillCategory.RANGED_ATTACK)) {
			defenseSkillType = SkillType.DODGE;
		} else {
			defenseSkillType = attackType;
		}
			
		return character.getSkill(defenseSkillType);
	}

	public BodyPart getBodyPart(String bodyPartName, ICharacter character) {
		for (BodyPart bodyPart : character.getBodyParts()) {
			if (bodyPart.getName().toLowerCase().equals(bodyPartName)) {
				return bodyPart;
			}
		}
		return null;

	}

}
