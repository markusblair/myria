package org.adventure.commands.navigation;

import org.adventure.PlayerState;
import org.adventure.character.ICharacter;
import org.adventure.commands.CommandCondition;

public class PlayerMovementCondition extends CommandCondition {

	@Override
	public boolean conditional(ICharacter playerCharacter) {
		return (playerCharacter.getPlayerState().equals(PlayerState.DEAD) == false &&
				 playerCharacter.getPlayerState().equals(PlayerState.UNCONSCIOUS) == false &&
				 playerCharacter.getPlayerState().equals(PlayerState.STUNNED) == false);
	}

}
