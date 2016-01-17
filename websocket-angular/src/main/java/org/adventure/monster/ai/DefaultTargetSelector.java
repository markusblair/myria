package org.adventure.monster.ai;

import java.util.Collection;
import java.util.LinkedList;

import org.adventure.CharacterGroup;
import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.adventure.monster.Monster;
import org.adventure.random.RandomCollection;
import org.adventure.random.SkillCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTargetSelector implements ITargetSelector {

	Logger log = LoggerFactory.getLogger(DefaultTargetSelector.class);
	private Monster monster;
	private ICharacter currentTarget;
	
	public DefaultTargetSelector(Monster monster) {
		super();
		this.monster = monster;
	}

	protected boolean validTarget() {
		boolean validTarget = false;
		if (currentTarget != null 
				&& this.monster.getCurrentRoom().equals(currentTarget.getCurrentRoom())
				&& PlayerState.DEAD.equals(currentTarget.getPlayerState()) == false
				) {
			validTarget = true;
		}
		if (currentTarget == null) {
			log.info("Current Target is null");
		} else if (validTarget == false) {
			log.info("Target is not valid: monsterRoom=" + this.monster.getCurrentRoom().getId() +
					" characterRoom=" +currentTarget.getCurrentRoom().getId() + 
					" targetState=" + currentTarget.getPlayerState());
		}
		return validTarget;
	}
	
	protected ICharacter selectTarget() {
		log.info(this.monster.getName() + " Selecting target.");
		Collection<ICharacter> characters = null;
		
		if (this.monster.getWeapon().getWeaponType().inSkillCategory(SkillCategory.MELEE_ATTACK)) {
			CharacterGroup characterGroup =  this.monster.getCurrentRoom().getCharacterGroup(this.monster);
			if (characterGroup != null) {
				characters = characterGroup.getCharacters();			
			}
		}
		else {
			characters = this.monster.getCurrentRoom().getCharacters();			
		}
		return selectCharacterFromList(characters);
	}

	@Override
	public ICharacter selectCharacterFromList(Collection<ICharacter> characters) {
		
		if (characters != null) {
			Collection<ICharacter> targets = new LinkedList<ICharacter>();
			for (ICharacter iCharacter : characters) {
				if (iCharacter instanceof Monster) {
				}
				else if (PlayerState.DEAD.equals(iCharacter.getPlayerState()) == false) {
					targets.add(iCharacter);
				}
			}
			if (targets.size() > 0) {
				
				ICharacter character = new RandomCollection<>(targets).next();
				return character;
			}			
		}
		return null;
	}
	
	@Override
	public  ICharacter currentTarget() {
		if (validTarget() == false) {
			this.currentTarget = selectTarget();
			if (this.currentTarget != null) {
				log.info("Current Target :" + this.currentTarget.getName());				
			}
		}
		return currentTarget;
	}

}
