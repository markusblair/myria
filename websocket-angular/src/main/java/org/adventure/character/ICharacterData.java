package org.adventure.character;

import java.util.List;
import java.util.Map;

import org.adventure.IContainer;
import org.adventure.PlayerState;
import org.adventure.character.stats.StatReference;
import org.adventure.items.IItem;
import org.adventure.items.IWearable;
import org.adventure.items.QuantityItem;
import org.adventure.items.WearableType;
import org.adventure.items.armor.Armor;
import org.adventure.random.Skill;
import org.adventure.random.SkillType;

public interface ICharacterData extends IEffectableCharacterData {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getRoomId();

	public abstract void setRoomId(String roomId);

	public abstract String getUserId();

	public abstract void setUserId(String userId);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract void setMaxHealth(StatReference maxHealth);

	public abstract void setHealth(int health);

	public abstract void setMind(int mind);

	public abstract void setMana(int mana);

	public abstract int getExperience();

	public abstract void setExperience(int experience);

	public abstract void setStrength(int strength);

	public abstract void setAgility(int agility);

	public abstract void setSpeed(int speed);

	public abstract void setInteligence(int inteligence);

	public abstract void setConstitution(int constitution);

	public abstract IItem getLeftHand();

	public abstract void setLeftHand(IItem leftHand);

	public abstract IItem getRightHand();

	public abstract void setRightHand(IItem rightHand);

	public abstract QuantityItem getGold();

	public abstract void setGold(QuantityItem gold);

	public abstract List<BodyPart> getBodyParts();

	public abstract void setBodyParts(List<BodyPart> bodyParts);

	public abstract Map<BodyPartType, Armor> getArmorMap();

	public abstract void setArmorMap(Map<BodyPartType, Armor> armorMap);

	public abstract List<Armor> getWornArmor();

	public abstract void setWornArmor(List<Armor> wornArmor);

	public abstract List<IContainer> getContainers();

	public abstract void setContainers(List<IContainer> containers);

	public abstract Map<WearableType, List<IWearable>> getClothing();

	public abstract void setClothing(Map<WearableType, List<IWearable>> clothing);

	public abstract void setPlayerState(PlayerState playerState);

	public abstract void setSkills(Map<SkillType, Skill> skills);

	public abstract void setDeathCredits(int deathCredits);

	public abstract int getDeathCredits();

	public abstract void setWill(int will);

	public abstract int getWill();

	public abstract void setReflex(int reflex);

	public abstract int getReflex();

	public abstract int getEnergy();
	
	public abstract int getEnergyReserve();
	
	public abstract void setEnergy(int energy);

	public abstract int getMaxEnergyReserve();

	public abstract int getMaxEnergy();

	public abstract void setEnergyReserve(int energyReserve);

	public abstract int getStamina();
}