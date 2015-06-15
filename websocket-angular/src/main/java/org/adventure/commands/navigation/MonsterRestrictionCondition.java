package org.adventure.commands.navigation;

import org.adventure.character.ICharacter;
import org.adventure.commands.CommandCondition;
import org.adventure.monster.Monster;

public class MonsterRestrictionCondition extends CommandCondition {

	@Override
	protected boolean conditional(ICharacter character) {
		return !(character instanceof Monster);
	}

}
