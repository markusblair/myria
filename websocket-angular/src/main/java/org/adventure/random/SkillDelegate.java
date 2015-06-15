package org.adventure.random;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class SkillDelegate extends Skill {
	Skill skill;
	private float multiplier;
	private int adjustment;
	
	public SkillDelegate(Skill skill) {
		super();
		this.skill = skill;
	}

	public SkillDelegate(Skill skill, int adjustment) {
		super();
		this.skill = skill;
		this.adjustment = adjustment;
	}

	public SkillDelegate(Skill skill, float multiplier) {
		super();
		this.skill = skill;
		this.multiplier = multiplier;
	}

	public SkillDelegate setMultiplier(float multiplier) {
		this.multiplier = multiplier;
		return this;
	}

	public SkillDelegate setAdjustment(int adjustment) {
		this.adjustment = adjustment;
		return this;
	}

	@Override
	public SkillCheckResult check(IRandom random) {
		return this.skill.check(random);
	}

	@Override
	public SkillCheckResult check(int level) {
		return this.skill.check(level);
	}

	@Override
	public int getValue() {
		return this.getValue(this.level);
	}

	@Override
	public int getValue(int level) {
		int resultingValue = this.skill.getValue(level);
		resultingValue = (int)(resultingValue * multiplier);
		resultingValue = resultingValue + adjustment;
		return resultingValue;
	}

	@Override
	public int getLevel() {
		return this.skill.getLevel();
	}
	
	
}
