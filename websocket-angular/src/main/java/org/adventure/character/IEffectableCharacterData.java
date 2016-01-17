package org.adventure.character;

import java.util.Map;

import org.adventure.character.stats.StatReference;
import org.adventure.random.Skill;
import org.adventure.random.SkillType;

public interface IEffectableCharacterData {

	public abstract StatReference getMaxHealth();

	public abstract int getHealth();

	public abstract int getMind();

	public abstract int getMaxMind();

	public abstract int getMana();

	public abstract int getMaxMana();

	public abstract int getStrength();

	public abstract int getAgility();

	public abstract int getSpeed();

	public abstract int getInteligence();

	public abstract int getConstitution();

	public abstract PlayerState getPlayerState();

	public abstract Map<SkillType, Skill> getSkills();

}