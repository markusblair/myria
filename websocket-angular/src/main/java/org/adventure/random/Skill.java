package org.adventure.random;

import java.util.concurrent.ThreadLocalRandom;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class Skill implements IRandom {
	SkillType skillType;
	int level = 1;
	private Skill parentSkill;
	public Skill() {
		super();
	}

	public Skill(SkillType skillType, int level) {
		this.level = level;
		this.skillType = skillType;
	}
	
	public void setParentSkill(Skill parentSkill) {
		this.parentSkill = parentSkill;
	}
	
	public SkillCheckResult check(IRandom random) {
		int testValue = random.getValue();
		SkillCheckResult result = new SkillCheckResult();
		result.setValue1(getValue());
		result.setValue2(testValue);
		return result;
	}
	
	public SkillCheckResult check(int level) {
		int testValue = getValue(level);
		SkillCheckResult result = new SkillCheckResult();
		result.setValue1(getValue());
		result.setValue2(testValue);
		return result;
	}
	
	public SkillCheckResult check(Skill skill) {
		int testValue = skill.getValue();
		SkillCheckResult result = new SkillCheckResult();
		result.setValue1(getValue());
		result.setValue2(testValue);
		return result;
	}
	
	@Override
	public int getValue() {
		int value = 0;
		if (this.parentSkill != null) {
			value = this.parentSkill.getValue();
		}
		value = value + getValue(getLevel());
		return value;
	}
	
	public int getValue(int level) {
		Double adjustedLevel = Math.log(level);
		if (level == 0) {
			adjustedLevel = 0d;
		}
//		Double adjustedLevel = Double.valueOf(level);
		Double result = 10 + (ThreadLocalRandom.current().nextGaussian() * 2) + (adjustedLevel * 20);
		return Math.abs(result.intValue());
	}

	public int getLevel() {
		return level;
	}

	public SkillType getSkillType() {
		return skillType;
	}
	 
	public void addLevel() {
		 this.level = this.level +1;
	 }
	
	
}
