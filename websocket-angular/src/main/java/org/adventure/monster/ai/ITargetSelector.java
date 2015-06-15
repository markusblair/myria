package org.adventure.monster.ai;

import java.util.Collection;
import java.util.LinkedList;

import org.adventure.PlayerState;
import org.adventure.character.ICharacter;
import org.adventure.monster.Monster;
import org.adventure.random.RandomCollection;

public interface ITargetSelector {
	public ICharacter currentTarget();

	public abstract ICharacter selectCharacterFromList(Collection<ICharacter> characters);
}
