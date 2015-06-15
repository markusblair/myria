package org.adventure.spells;

import org.adventure.character.ICharacter;
import org.adventure.items.Item;
import org.adventure.random.SkillType;

public abstract class Spell extends Item {

	public abstract void cast(ICharacter castor, ICharacter target);
	public abstract SkillType getSkillType();
	public abstract int getManaRequried();
	public abstract int castingTime();
}