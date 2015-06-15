package org.adventure.monster.ai;

import java.util.Collection;

import org.adventure.character.ICharacter;
import org.adventure.monster.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FleeWhenOverwhelmed implements IAiChainHandler {

	Logger log = LoggerFactory.getLogger(FleeWhenOverwhelmed.class);
	private float monstersPerCharacter;
	public FleeWhenOverwhelmed(float monstersPerCharacter) {
		this.monstersPerCharacter = monstersPerCharacter;
	}

	@Override
	public boolean handle(Monster monster) {
		if (isOverWhelmed(monster)) {
			log.info("Overwhelmed.");
			return new WanderingMonster().handle(monster);
		}
		return false;
	}

	public boolean isOverWhelmed(Monster monster) {
		float monsterCount = 0;
		float characterCount = 0;
		Collection<ICharacter> characters = monster.getCurrentRoom().getCharacters();
		for (ICharacter iCharacter : characters) {
			if (iCharacter instanceof Monster) {
				monsterCount++;
			}
			else {
				characterCount++;
			}
		}
		log.info("Flee when overwhelmed monsters=" + monsterCount + " characterCount=" + characterCount + " monstersPerCharacter=" + monstersPerCharacter);
		return (characterCount * monstersPerCharacter > monsterCount * monstersPerCharacter);
	}
}
