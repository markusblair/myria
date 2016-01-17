package org.adventure.character;

import java.util.Map;

import org.adventure.character.stats.StatReference;
import org.adventure.random.Skill;
import org.adventure.random.SkillType;

public abstract class CharacterDataEffect implements IEffectableCharacterData {
	private IEffectableCharacterData characterData;
	
	public abstract boolean expire();
	
	public abstract void onExpire();
	
	public void setCharacterData(IEffectableCharacterData characterData) {
		this.characterData = characterData;
	}
	
	/*
	 *	Delegate Methods 
	 * 
	 */
	public PlayerState getPlayerState() {
		return this.characterData.getPlayerState();
	}

	public StatReference getMaxHealth() {
		return characterData.getMaxHealth();
	}

	public int getHealth() {
		return characterData.getHealth();
	}

	public int getMind() {
		return characterData.getMind();
	}

	public int getMaxMind() {
		return characterData.getMaxMind();
	}

	public int getMana() {
		return characterData.getMana();
	}

	public int getMaxMana() {
		return characterData.getMaxMana();
	}

	public int getStrength() {
		return characterData.getStrength();
	}

	public int getAgility() {
		return characterData.getAgility();
	}

	public int getSpeed() {
		return characterData.getSpeed();
	}

	public int getInteligence() {
		return characterData.getInteligence();
	}

	public int getConstitution() {
		return characterData.getConstitution();
	}

	public Map<SkillType, Skill> getSkills() {
		return characterData.getSkills();
	}
	

	
	
}
