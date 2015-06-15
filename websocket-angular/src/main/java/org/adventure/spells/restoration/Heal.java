package org.adventure.spells.restoration;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.ICharacter;
import org.adventure.random.SkillType;
import org.adventure.spells.Spell;
@EmbeddedOnly
public class Heal extends Spell {
	
	@Override
	public String getDescription() {
		return "Heal";
	}
	@Override
	public void cast(ICharacter castor, ICharacter target) {
		castor.addHealth(castor.getSkill(getSkillType()).getValue() * 2);
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.HEAL_SPELLS;
	}

	@Override
	public int getManaRequried() {
		return 5;
	}

	@Override
	public int castingTime() {
		return 6;
	}
}
