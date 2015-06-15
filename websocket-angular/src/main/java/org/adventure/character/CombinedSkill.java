package org.adventure.character;

import org.adventure.random.Skill;
import org.adventure.random.SkillType;

public class CombinedSkill extends Skill {

	Skill[] skills;
	
	public CombinedSkill(Skill...skills) {
		this.skills = skills;
	}

	@Override
	public int getLevel() {
		int level = 0;
		for (Skill skill : skills) {
			level = level + skill.getLevel();
		}
		return level;
	}

	@Override
	public SkillType getSkillType() {
		throw new RuntimeException("Method Not Supported.");
	}

	@Override
	public void addLevel() {
		throw new RuntimeException("Method Not Supported.");
	}


	
}
