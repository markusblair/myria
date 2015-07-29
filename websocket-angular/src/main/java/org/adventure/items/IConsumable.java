package org.adventure.items;

import org.adventure.character.ICharacter;

public interface IConsumable extends IItem {

	public void consume(ICharacter character);
}
