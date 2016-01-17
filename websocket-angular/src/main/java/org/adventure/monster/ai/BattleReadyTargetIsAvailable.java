package org.adventure.monster.ai;

import org.adventure.character.PlayerState;
import org.adventure.monster.Monster;
import org.adventure.monster.MonsterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleReadyTargetIsAvailable implements IAiChainHandler {
	Logger log = LoggerFactory.getLogger(BattleReadyTargetIsAvailable.class);
	private ITargetSelector targetSelector;
	public BattleReadyTargetIsAvailable(ITargetSelector targetSelector) {
		this.targetSelector = targetSelector;
	}

	@Override
	public boolean handle(Monster monster) {
		if (targetSelector.currentTarget() != null) {
			log.info(monster.getName() + "Found target check state..get ready.");
			if (PlayerState.STANDING.equals(monster.getPlayerState()) == false) {
				monster.setPlayerState(PlayerState.STANDING);
				return true;
			}
		}
		return false;
	}

}
