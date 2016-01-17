package org.adventure.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.adventure.IContainer;
import org.adventure.character.stats.StatReference;
import org.adventure.items.IItem;
import org.adventure.items.IWearable;
import org.adventure.items.QuantityItem;
import org.adventure.items.WearableType;
import org.adventure.items.armor.Armor;
import org.adventure.random.Skill;
import org.adventure.random.SkillType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CharacterData implements ICharacterData  {
	@Id
	private String id;
	private String name;
	private String roomId;
	private String userId;
	private StatReference maxHealth = new StatReference(100);
	private int health = 100;
	
	@JsonIgnore
	private int deathCredits = 100;
	private int mana = 0;	
	private int mind = 0;
	private int experience = 10000;
	private int strength = 0;
	private int agility = 0;
	private int speed = 0;
	private int stamina = 0;
	private int inteligence = 0;
	private int constitution = 0;
	private int reflex = 0;
	private int will = 0;
	
	private int energy = 100;
	private int maxEnergy = 100;
	private int energyReserve = 100;
	private int maxEnergyReserve = 100;
	
	private PlayerState playerState = PlayerState.STANDING;
	private List<String> knownSpells = new ArrayList<String>();
	private Map<SkillType, Skill> skills = new HashMap<SkillType, Skill>();
	
	private IItem leftHand;
	private IItem rightHand;
	
	private QuantityItem gold = new QuantityItem(QuantityItem.GOLD, 200);

	private List<BodyPart> bodyParts = new ArrayList<BodyPart>();
	@JsonIgnore
	private Map<BodyPartType, Armor> armorMap = new HashMap<BodyPartType, Armor>();
	private List<Armor> wornArmor = new ArrayList<Armor>();
	private List<IContainer> containers = new ArrayList<IContainer>();
	private Map<WearableType, List<IWearable>> clothing = new HashMap<WearableType, List<IWearable>>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int getDeathCredits() {
		return deathCredits;
	}
	@Override
	public void setDeathCredits(int deathCredits) {
		this.deathCredits = deathCredits;
	}
	@Override
	public StatReference getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(StatReference maxHealth) {
		this.maxHealth = maxHealth;
	}
	@Override
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	@Override
	public int getMind() {
		return mind;
	}
	public void setMind(int mind) {
		this.mind = mind;
	}
	@Override
	public int getEnergy() {
		return energy;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	@Override
	public int getEnergyReserve() {
		return energyReserve;
	}
	@Override
	public void setEnergyReserve(int energyReserve) {
		this.energyReserve = energyReserve;
	}
	
	@Override
	public int getMaxEnergy() {
		return maxEnergy;
	}
	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}
	@Override
	public int getMaxEnergyReserve() {
		return maxEnergyReserve;
	}
	public void setMaxEnergyReserve(int maxEnergyReserve) {
		this.maxEnergyReserve = maxEnergyReserve;
	}
	@Override
	@Transient
	public int getMaxMind() {
		return getInteligence() * 500;
	}
	@Override
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	@Override
	public int getMaxMana() {
		int spellSkillLevel = ( 
				getSkillLevel(SkillType.ILLUSION_SPELLS) + 
				getSkillLevel(SkillType.PROTECTIN_SPELLS) + 
				getSkillLevel(SkillType.DESTRUCTION_SPELLS) +
				getSkillLevel(SkillType.HEAL_SPELLS));
		return getInteligence() * spellSkillLevel;
	}
	
	private int getSkillLevel(SkillType skillType) {
		int result = 0;
		if (getSkills().get(skillType) != null) {
			result = getSkills().get(skillType).getLevel();
		} 
		return result;
	}
	
	@Override
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	@Override
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	@Override
	public int getAgility() {
		return agility;
	}
	public void setAgility(int agility) {
		this.agility = agility;
	}
	@Override
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	@Override
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	@Override
	public int getInteligence() {
		return inteligence;
	}
	public void setInteligence(int inteligence) {
		this.inteligence = inteligence;
	}
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacterData#getConstitution()
	 */
	@Override
	public int getConstitution() {
		return constitution;
	}
	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}
	@Override
	public int getReflex() {
		return reflex;
	}
	@Override
	public void setReflex(int reflex) {
		this.reflex = reflex;
	}
	@Override
	public int getWill() {
		return will;
	}
	@Override
	public void setWill(int will) {
		this.will = will;
	}
	public IItem getLeftHand() {
		return leftHand;
	}
	public void setLeftHand(IItem leftHand) {
		this.leftHand = leftHand;
	}
	public IItem getRightHand() {
		return rightHand;
	}
	public void setRightHand(IItem rightHand) {
		this.rightHand = rightHand;
	}
	public QuantityItem getGold() {
		return gold;
	}
	public void setGold(QuantityItem gold) {
		this.gold = gold;
	}
	public List<String> getKnownSpells() {
		return knownSpells;
	}
	public List<BodyPart> getBodyParts() {
		return bodyParts;
	}
	public void setBodyParts(List<BodyPart> bodyParts) {
		this.bodyParts = bodyParts;
	}
	public Map<BodyPartType, Armor> getArmorMap() {
		return armorMap;
	}
	public void setArmorMap(Map<BodyPartType, Armor> armorMap) {
		this.armorMap = armorMap;
	}
	public List<Armor> getWornArmor() {
		return wornArmor;
	}
	public void setWornArmor(List<Armor> wornArmor) {
		this.wornArmor = wornArmor;
	}
	public List<IContainer> getContainers() {
		return containers;
	}
	public void setContainers(List<IContainer> containers) {
		this.containers = containers;
	}
	public Map<WearableType, List<IWearable>> getClothing() {
		return clothing;
	}
	public void setClothing(Map<WearableType, List<IWearable>> clothing) {
		this.clothing = clothing;
	}
	
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacterData#getPlayerState()
	 */
	@Override
	public PlayerState getPlayerState() {
		return playerState;
	}
	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	@Override
	public Map<SkillType, Skill> getSkills() {
		return skills;
	}
	public void setSkills(Map<SkillType, Skill> skills) {
		this.skills = skills;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterData other = (CharacterData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
