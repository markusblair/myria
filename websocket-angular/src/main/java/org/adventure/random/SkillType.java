package org.adventure.random;


public enum SkillType {
	RANGED(null, SkillCategory.RANGED_ATTACK, SkillCategory.AGILITY),
	BOW(RANGED, SkillCategory.RANGED_ATTACK, SkillCategory.AGILITY), 
	XBOW(null, SkillCategory.RANGED_ATTACK, SkillCategory.AGILITY), 
	SLING(null, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY), 
	MELEE(null, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY),
	SWORD(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY), 
	KNIFE(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY), 
	AXE(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY), 
	MACE(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.STRENGTH),
	SPEAR(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.AGILITY), 
	TWO_HANDDED_SWORD(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.STRENGTH, SkillCategory.AGILITY), 
	TWO_HANDED_AXE(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.STRENGTH, SkillCategory.AGILITY), 
	HAND_TO_HAND(MELEE, SkillCategory.MELEE_ATTACK, SkillCategory.STRENGTH, SkillCategory.AGILITY),
	SHIELD(MELEE, SkillCategory.DEFENSE),
	PARRY_MELEE(null, SkillCategory.DEFENSE, SkillCategory.AGILITY), 
	DODGE(null, SkillCategory.DEFENSE, SkillCategory.AGILITY),
	MAGIC(null, SkillCategory.INTELECT),
	DESTRUCTION_SPELLS(MAGIC, SkillCategory.INTELECT),
	HEAL_SPELLS(MAGIC, SkillCategory.INTELECT),
	ILLUSION_SPELLS(MAGIC, SkillCategory.INTELECT),
	PROTECTIN_SPELLS(MAGIC, SkillCategory.INTELECT);
	
	private SkillCategory[] skillCategories;
	private SkillType parentSkill;
	private SkillType(SkillType parentSkill, SkillCategory... skillCategories) {
		this.parentSkill = parentSkill;
		this.skillCategories = skillCategories;
	}

	public boolean inSkillCategory(SkillCategory skillCategory) {
		for (SkillCategory category : skillCategories) {
			if (category.equals(skillCategory)) {
				return true;
			}
		}
		return false;
	}
	
	public SkillType getParentSkillType() {
		return this.parentSkill;
	}
}
