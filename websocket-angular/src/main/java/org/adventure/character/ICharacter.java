package org.adventure.character;

import java.util.List;
import java.util.Map;

import org.adventure.IContainer;
import org.adventure.IPlayerPrompt;
import org.adventure.PlayerState;
import org.adventure.Room;
import org.adventure.commands.Action;
import org.adventure.commands.CommandCondition;
import org.adventure.items.IItem;
import org.adventure.items.IWearable;
import org.adventure.items.QuantityItem;
import org.adventure.items.WearableType;
import org.adventure.items.armor.Armor;
import org.adventure.items.armor.AttackResult;
import org.adventure.items.armor.DamageCalculation;
import org.adventure.items.search.ItemSearchResult;
import org.adventure.items.weapons.DamageType;
import org.adventure.items.weapons.Weapon;
import org.adventure.random.IRandom;
import org.adventure.random.Skill;
import org.adventure.random.SkillCheckResult;
import org.adventure.random.SkillType;

public interface ICharacter extends IContainer, IItem {

	public abstract void setStrength(int strength);

	public abstract void setAgility(int agility);

	public abstract void setSpeed(int speed);

	public abstract void setInteligence(int inteligence);
	
	public abstract Weapon getWeapon();

	public abstract void setDefaultWeapon(Weapon defaultWeapon);

	public abstract List<BodyPart> getBodyParts();

	public abstract void setBodyParts(List<BodyPart> bodyParts);

	public abstract DamageCalculation calculateDamage(Map<DamageType, Integer> damages, BodyPart bodyPart);

	public abstract SkillCheckResult skillCheck(IRandom random, SkillType type);

	public abstract Skill getSkill(SkillType skillType);

	public abstract String getId();

	public abstract void setId(String id);
	
	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract String getLongDescription();

	public abstract IItem getLeftHand();

	public abstract void setLeftHand(IItem leftHand);

	public abstract IItem getRightHand();

	public abstract void setRightHand(IItem rightHand);

	public abstract QuantityItem getGold();

	public abstract QuantityItem removeGold(int amount);

	public abstract boolean isHolding(IItem item);

	public abstract int getFreeHands();

	public abstract boolean unWear(IWearable wearable);

	public abstract boolean unWear(Armor armor);

	public abstract void removeItem(IItem item);

	/**
	 * Adding an item to a character is the same thing as pick up an item.. It will go into the characters hands if they have 
	 * a free hand.  
	 */
	public abstract void addItem(IItem item);

	public abstract boolean canAddItem(IItem item);

	public abstract boolean wear(IItem item);

	public abstract List<Armor> getWornArmor();

	public abstract IWearable getWorn(WearableType type);

	public abstract IWearable getWorn(WearableType type, int index);

	public abstract boolean isWearing(IItem item);

	public abstract List<IContainer> getContainers();

	public abstract ItemSearchResult getItem(String itemName);

	public abstract boolean isContentsVisible();

	public abstract Room getCurrentRoom();

	public abstract String getRoomId();

	public void gotoRoom(Room room);
	
	public abstract void addHealth(int amount);

	public abstract void removeHealth(int amount);

	public boolean useMana(int mana);
	
	public abstract PlayerState getPlayerState();

	public abstract void setState(PlayerState playerState);

	public abstract int getMaxHealth();

	public abstract int getHealth();

	public abstract void setMaxHealth(int health);

	public abstract int getExperience();

	public abstract void setExperience(int experience);

	public abstract void addSkillLevel(SkillType skillType);

	public abstract int getVolume();

	public abstract int getWeight();

	public abstract int getBodyWeight();

	public abstract boolean is(String name);

	public abstract boolean commandAllowed(Action command, ICharacter character);

	public abstract IItem addCommandCondition(Class command, CommandCondition commandCondition);

	public abstract CommandCondition getCommandCondition(Class command);

	public abstract void removeCommandCondition(Class command);

	public abstract void addExperience(int exp);

	public abstract int getMind();

	public abstract void setMind(int mind);

	public abstract int getMaxMind();

	public abstract int getTotalSkillLevels();
	
	public abstract void setBusyFor(int time);
	
	public abstract int getBusyFor();
	
	public boolean isBusy();
	
	/**
	 *  Messaging
	 */
	
	public abstract void sendRoom();
	
	public abstract void sendMessage(String message);
	
	public abstract void setPlayerPrompt(IPlayerPrompt playerPrompt);
	
	public abstract void sendMessageToRoom(String message);
	
	public abstract void sendAttackResult(AttackResult attackResult);

	public abstract void sendCharacter();
	
	public void sendData(Map<String, Object> data);
	
	public void sendDataMessageToRoom(DataMessage dataMessage, boolean includeCharacter);

	public abstract int getDeathCredits();

	public abstract Map<WearableType, List<IWearable>> getClothing();

	public abstract void addCharacterDataEffect(CharacterDataEffect characterDataEffect);

	public abstract int getReflex();

	public abstract int getWill();
}