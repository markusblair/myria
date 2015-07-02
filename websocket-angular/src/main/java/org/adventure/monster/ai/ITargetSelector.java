package org.adventure.monster.ai;

import java.util.Collection;

import org.adventure.character.ICharacter;

public interface ITargetSelector {
	public ICharacter currentTarget();

	public abstract ICharacter selectCharacterFromList(Collection<ICharacter> characters);
}
