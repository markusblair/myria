package org.adventure.character;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.adventure.IContainer;
import org.adventure.IPlayerPrompt;
import org.adventure.PlayerState;
import org.adventure.Room;
import org.adventure.character.stats.StatReference;
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
import org.adventure.random.SkillCategory;
import org.adventure.random.SkillCheckResult;
import org.adventure.random.SkillDelegate;
import org.adventure.random.SkillType;
import org.adventure.rooms.CityOfMyria;
import org.adventure.spells.Spell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Character implements ICharacter {
	@JsonIgnore
	private static Logger log = LoggerFactory.getLogger(Character.class);
	
	@JsonIgnore
	private IWebSocketDataService characterSession;
	
	protected ICharacterData characterData;
	@JsonIgnore
	private Weapon defaultWeapon = new Weapon().setWeaponType(SkillType.HAND_TO_HAND).addDamage("fist", DamageType.BLUNT, 1,1);
	@JsonIgnore
	private Room currentRoom;
	@JsonIgnore
	private IPlayerPrompt playerPrompt;
	
	protected static Timer timer = new Timer();
	private TimerTask timerTask;
	private final AtomicInteger busyFor = new AtomicInteger();
	

	public Character(ICharacterData characterData, IWebSocketDataService characterSession) {
		this.characterSession = characterSession;
		setCharacterData(characterData);
	}

	protected void setCharacterData(ICharacterData characterData) {
		if (characterData != null) {
			ICharacterData characterDataEffectProxy = CharacterDataEffectsProxy.newInstance(characterData);			
			this.characterData = characterDataEffectProxy;
		}
		else {
			this.characterData = null;
		}
	}
	
	@Override
	public void addCharacterDataEffect(CharacterDataEffect characterDataEffect) {
		if (Proxy.getInvocationHandler(this.characterData) instanceof CharacterDataEffectsProxy) {
			((CharacterDataEffectsProxy)Proxy.getInvocationHandler(this.characterData)).addEffect(characterDataEffect);
		}
		else {
			log.error("Non effectable character! name={}", this.getName());
		}
	}
	
	private void expireEffects() {
		if (Proxy.getInvocationHandler(this.characterData) instanceof CharacterDataEffectsProxy) {
			CharacterDataEffectsProxy effectsProxy = (CharacterDataEffectsProxy)Proxy.getInvocationHandler(this.characterData);
			effectsProxy.expireEffects();
		}
	}
	
	protected void setWebSockerSession(IWebSocketDataService webSocketDataService) {
		this.characterSession = webSocketDataService;
	}
	
	public ICharacterData getCharacterData() {
		return this.characterData;
	}

	
	public IPlayerPrompt getPlayerPrompt() {
		return playerPrompt;
	}

	public void setPlayerPrompt(IPlayerPrompt playerPrompt) {
		this.playerPrompt = playerPrompt;
	}

	
	public void gotoRoom(Room room) {
		if (room.equals(this.currentRoom) == false){
			if (currentRoom != null) {
				this.currentRoom.removeCharacter(this);				
			}
			this.currentRoom = room;
			this.currentRoom.addCharacter(this);
			this.sendRoom();
			setRoomId(room.getId());
			if ((getRightHand() instanceof ICharacter)) {
				ICharacter draging = (ICharacter)getRightHand();
				draging.gotoRoom(this.currentRoom);
			}
			if ((getLeftHand() instanceof ICharacter)) {
				ICharacter draging = (ICharacter)getLeftHand();
				draging.gotoRoom(this.currentRoom);
			}
		}
	}
	
	private float expAbsorbed = 0f;
	protected void absorbExperience() {
		float floatExpToAbsorb = getInteligence() * getExperienceModifier() / 50f;
		
		expAbsorbed = expAbsorbed + floatExpToAbsorb;
		int expToAbsorb = (int) expAbsorbed;
		expAbsorbed = expAbsorbed - expToAbsorb;
		if (expToAbsorb > getMind()) {
			expToAbsorb = getMind();
		}
		setMind(getMind() - expToAbsorb);
		this.setExperience(this.getExperience() + expToAbsorb);
	}
	
	
	@Override
	public int getEnergy() {
		return characterData.getEnergy();
	}

	@Override
	public void addEnergyReserve(int energyReserve) {
		if (energyReserve > 0) {
			int newEnergyReserve = characterData.getEnergyReserve() + energyReserve;
			if (newEnergyReserve > characterData.getMaxEnergyReserve()) {
				newEnergyReserve = characterData.getMaxEnergyReserve();
			}
			if (newEnergyReserve != characterData.getEnergyReserve()) {
				characterData.setEnergyReserve(newEnergyReserve);			
				sendCharacterUpdate("energyReserve", newEnergyReserve);
			}
		}
	}

	public void setEnergy(int energy) {
		if (energy < 0) {
			energy = 0;
		}
		if (energy > characterData.getMaxEnergy()) {
			energy = characterData.getMaxEnergy();
		}
		if (energy != characterData.getEnergy()) {
			characterData.setEnergy(energy);			
			sendCharacterUpdate("energy", energy);
		}
	}
	
	private float energyAbsorbed = 0f;
	protected void recoverEnergy() {
		boolean fullEnergy = characterData.getEnergy() == characterData.getMaxEnergy();
		boolean hasEnergyReserve = characterData.getEnergyReserve() > 0;
		if (hasEnergyReserve && !fullEnergy && !isBusy()) {	
			//Move Energy from reserve to refill energy.
			float e = (getEnergyRecoveryModifier() / 500f);
			energyAbsorbed = energyAbsorbed + e;
			int energyToAbsorb = (int) energyAbsorbed;
			if (characterData.getEnergyReserve() < energyToAbsorb) {
				energyToAbsorb = characterData.getEnergyReserve();
			}
			if (energyToAbsorb > 0 && energyToAbsorb <= characterData.getEnergyReserve()) {
				energyAbsorbed = energyAbsorbed - energyToAbsorb;
				int newEnergyReserve = characterData.getEnergyReserve() - energyToAbsorb;
				characterData.setEnergyReserve(newEnergyReserve);
				sendCharacterUpdate("energyReserve", newEnergyReserve);
				setEnergy(this.characterData.getEnergy() + energyToAbsorb);
			}
		}			
	}
	
	private float energyLoss = 0f;
	protected void loseEnergy() {
		energyLoss = energyLoss + 0.1f;
		if (energyLoss >= 1) {
//			boolean fullEnergy = characterData.getEnergy() == characterData.getMaxEnergy();
			boolean hasEnergyReserve = characterData.getEnergyReserve() > 0;
			if (hasEnergyReserve) {
				// Reduce energy reserve.
				int newEnergyReserve = characterData.getEnergyReserve() - (int)energyLoss;
				energyLoss = 0;
				characterData.setEnergyReserve(newEnergyReserve);
				sendCharacterUpdate("energyReserve", newEnergyReserve);
			}
			else { //No Energy Reserve
				boolean hasEnergy = characterData.getEnergy() > 0;
				if (hasEnergy) {
					int newEnergyReserve = characterData.getEnergy() - (int)energyLoss;
					energyLoss = 0;
					setEnergy(newEnergyReserve);
				}
				else { // Has no energy.. something bad should start happening..like health should be reduced.
					
				}
			}
		}
	}
	
	
	private float getEnergyRecoveryModifier() {
		return getExperienceModifier();
	}
	
	public void setMana(int mana) {
		if (mana < 0) {
			mana = 0;
		}
		if (mana > characterData.getMaxMana()) {
			mana = characterData.getMaxMana();
		}
		if (mana != characterData.getMana()) {
			this.characterData.setMana(mana);
			sendCharacterUpdate("mana", mana);
		}
	}
	
	public boolean useMana(int mana) {
		if (characterData.getMana() >= mana) {
			setMana(this.characterData.getMana() - mana);
			return true;
		}
		return false;
	}
	
	private float manaAbsorbed = 0f;
	protected void recoverMana() {
		boolean spellPrepared =  (this.getLeftHand() instanceof Spell || this.getRightHand() instanceof Spell);
		if (!spellPrepared) {
			float m = (this.characterData.getMaxMana() * getManaRecoveryModifier() / 2000f);
			manaAbsorbed = manaAbsorbed + m;
			int manaToAbsorb = (int) manaAbsorbed;
			if (manaToAbsorb > 0) {
				manaAbsorbed = manaAbsorbed - manaToAbsorb;
				setMana(this.characterData.getMana() + manaToAbsorb);
			}			
		}
	}
	
	private float getManaRecoveryModifier() {
		return getExperienceModifier();
	}
	
	private float getExperienceModifier() {
		float modifier = 1;
		
		if (getCurrentRoom() != null && getCurrentRoom().getId().equals(CityOfMyria.CENTRAL_PLAZA)) {
			modifier = modifier * 50;
		}
		
		if (getPlayerState().equals(PlayerState.LAYING)) {
			modifier = modifier * 2.5f;
		}
		if (getPlayerState().equals(PlayerState.SITTING)) {
			modifier = modifier * 1.25f;
		}
		if (getWornArmor().size() == 0) {
			modifier = modifier * 20;
		}
		return modifier;
	}
	
	
	@Override
	@JsonIgnore
	public Weapon getWeapon() {
		IItem item = getRightHand();
		if (item instanceof Weapon) {
			Weapon weapon = (Weapon) item;
			return weapon;
		}
		return defaultWeapon;
	}

	@Override
	public void setDefaultWeapon(Weapon defaultWeapon) {
		this.defaultWeapon = defaultWeapon;
	}

	@Override
	public DamageCalculation calculateDamage(Map<DamageType, Integer> damages, BodyPart bodyPart) {
		Armor armor = new Armor();
		if (bodyPart != null) {
			armor = getArmor(bodyPart);
			if (armor == null) {
				armor = new Armor();
			}
		}

		DamageCalculation damageCalculation = armor.calculateDamage(damages, bodyPart);
		log.debug("Damage calculation for name=%1, total=%2", getName(), damageCalculation.getTotalDamage());
		determineBodyPartInjury(bodyPart, damageCalculation);
		
		removeHealth(damageCalculation.getTotalDamage());
		return damageCalculation;
	}

	protected void determineBodyPartInjury(BodyPart bodyPart, DamageCalculation damageCalculation) {
		int totalDamage = damageCalculation.getTotalDamage();
		bodyPart.addDamage(totalDamage);
		if (BodyPartType.ARM.equals(bodyPart.getBodyPartType()) || BodyPartType.HAND.equals(bodyPart.getBodyPartType())) {
			if (bodyPart.getPerecentHealth(totalDamage) > 25) {
				Injury injury = new Injury("Minors cut and bruises.", 0, 0);
				bodyPart.setInjury(injury);
			}
			else if (bodyPart.getPerecentHealth(totalDamage) > 50) {
				Injury injury = new Injury("Deep cuts and borken bones.", 0, 1);
				bodyPart.setInjury(injury);
			}
			else if (bodyPart.getHealth() <= 0) {
				Injury injury = new Injury("Completly Severed.", 0, 2);
				bodyPart.setInjury(injury);
			}
		}
		else if (BodyPartType.LEG.equals(bodyPart.getBodyPartType()) || BodyPartType.FOOT.equals(bodyPart.getBodyPartType())) {
			if (bodyPart.getPerecentHealth(totalDamage) > 50) {
				Injury injury = new Injury("Minors cut and bruises.", 0, 0);
				bodyPart.setInjury(injury);
				damageCalculation.setDescription("and falls to the ground.");
				setState(PlayerState.LAYING);
			}
			else if (bodyPart.getHealth() <= 0) {
				Injury injury = new Injury("Completly Severed.", 0, 2);
				bodyPart.setInjury(injury);
				setState(PlayerState.LAYING);
			}
		}
		else if (BodyPartType.HEAD.equals(bodyPart.getBodyPartType())) {
			if (bodyPart.getPerecentHealth(totalDamage) > 50) {
				damageCalculation.setDescription("and is stunned.");
				setState(PlayerState.STUNNED);
			}
			else if (bodyPart.getHealth() <= 0) {
				Injury injury = new Injury("Completly Severed.", 0, 2);
				bodyPart.setInjury(injury);
				setState(PlayerState.DEAD);
			}
		}
		else if (BodyPartType.NECK.equals(bodyPart.getBodyPartType())) {
			if (bodyPart.getPerecentHealth(totalDamage) > 50) {
				Injury injury = new Injury("Minors cut and bruises.", 0, 1);
				bodyPart.setInjury(injury);
			}
			else if (bodyPart.getHealth() <= 0) {
				Injury injury = new Injury("Completly Severed.", 0, 2);
				bodyPart.setInjury(injury);
				setState(PlayerState.DEAD);
			}
		}
		else if (BodyPartType.TORSO.equals(bodyPart.getBodyPartType()) || BodyPartType.BACK.equals(bodyPart.getBodyPartType())) {
			if (bodyPart.getPerecentHealth(totalDamage) > 50) {
				Injury injury = new Injury("Minors cut and bruises.", 0, 1);
				bodyPart.setInjury(injury);
			}
			else if (bodyPart.getHealth() <= 0) {
				Injury injury = new Injury("Critical wounds.",0, 2);
				bodyPart.setInjury(injury);
				setState(PlayerState.DEAD);
			}
		}
	}

	@Override
	public SkillCheckResult skillCheck(IRandom random, SkillType type) {
		return getSkill(type).check(random);
	}
	
	@Override
	public Skill getSkill(SkillType skillType) {
		Skill skill = getSkills().get(skillType);
		if (skill == null) {
			skill = new Skill(skillType, getBaseSkill());
			if (skillType.getParentSkillType() != null) {
				skill.setParentSkill(getSkill(skillType.getParentSkillType()));				
			}
			getSkills().put(skillType, skill);
		}
		if (PlayerState.DEAD.equals(this.getPlayerState())) {
			skill = new SkillDelegate(skill, 0.0f);
		} 
		else if (PlayerState.LAYING.equals(getPlayerState())) {
			if (SkillType.PARRY_MELEE.equals(skillType) ||
				SkillType.DODGE.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.3f);				
			}
			if (SkillType.XBOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.8f);	
			}
			if (SkillType.BOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.6f);	
			}
			if(SkillType.SHIELD.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.6f);		
			}
		}
		else if (PlayerState.KNELLING.equals(getPlayerState())) {
			if (SkillType.PARRY_MELEE.equals(skillType) ||
				SkillType.DODGE.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.6f);				
			} 
			if (SkillType.XBOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.4f);	
			}
			if (SkillType.BOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.2f);	
			}
			if(SkillType.SHIELD.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.4f);		
			}
		}
		else if (PlayerState.SITTING.equals(getPlayerState())) {
			if (SkillType.PARRY_MELEE.equals(skillType) ||
				SkillType.DODGE.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.4f);				
			}
			if (SkillType.XBOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.4f);	
			}
			if (SkillType.BOW.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.2f);	
			}
			if(SkillType.SHIELD.equals(skillType)) {
				skill = new SkillDelegate(skill, 1.2f);		
			}
		}
		else if (PlayerState.UNCONSCIOUS.equals(getPlayerState())) {
			if (SkillType.PARRY_MELEE.equals(skillType) ||
				SkillType.DODGE.equals(skillType) ||
				SkillType.SHIELD.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.0f);				
			}
		}
		else if (PlayerState.STUNNED.equals(getPlayerState())) {
			if (SkillType.PARRY_MELEE.equals(skillType) ||
				SkillType.DODGE.equals(skillType) ||
				SkillType.SHIELD.equals(skillType)) {
				skill = new SkillDelegate(skill, 0.2f);				
			}
		}
		if (skillType.inSkillCategory(SkillCategory.STRENGTH)) {
			skill = new SkillDelegate(skill, 1 + (0.1f * this.getStrength()));
		}
		if (skillType.inSkillCategory(SkillCategory.AGILITY)) {
			skill = new SkillDelegate(skill, 1 + (0.1f * this.getAgility()));
		}
		return skill;
	}

	protected int getBaseSkill() {
		return 1;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getLongDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuantityItem removeGold(int amount) {
		if (getGold().getAmount() >= amount) {
			QuantityItem g = new QuantityItem();
			g.setName(QuantityItem.GOLD);
			g.add(amount);
			getGold().remove(amount);
			return getGold();
		}
		return null;
	}

	@Override
	public boolean isHolding(IItem item) {
		if (getRightHand() != null && getRightHand().equals(item)) {
			return true;
		}
		else if (getLeftHand() != null && getLeftHand().equals(item)) {
			return true;
		}
		return false;
	}


	@Override
	public int getFreeHands() {
		int cnt = 2;
		if (getRightHand() != null) cnt--;
		if (getLeftHand() != null) cnt--;
		return cnt;
	}

	@Override
	public boolean unWear(IWearable wearable) {
		boolean result = false;
		if (getFreeHands() > 0) {
			List<IWearable> warnClothingOfType = getClothing().get(wearable.getWearableType());
			if (warnClothingOfType != null && warnClothingOfType.contains(wearable)) {
				warnClothingOfType.remove(warnClothingOfType.indexOf(wearable));
			}
			List<IContainer> containers = getContainers();
			if (containers != null && containers.contains(wearable)) {
				containers.remove(containers.indexOf(wearable));
			}
			addItem(wearable);
			result = true;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacter#unWear(org.adventure.items.armor.Armor)
	 */
	@Override
	public boolean unWear(Armor armor) {
		boolean result = false;
		if (getFreeHands() > 0) {
			if (getArmorMap().containsValue(armor)) {
				getWornArmor().remove(armor);
				for (BodyPartType bodyPartType : getArmorMap().keySet()) {
					if (armor.equals(getArmorMap().get(bodyPartType))) {
						getArmorMap().put(bodyPartType, null);
					}
				}
			}
			addItem(armor);
			result = true;
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacter#removeItem(org.adventure.items.IItem)
	 */
	@Override
	public void removeItem(IItem item) {
		if (isHolding(item)) {
			if (getRightHand() != null && getRightHand().equals(item)) {
				setRightHand(null);
			}
			else if (getLeftHand() != null && getLeftHand().equals(item)) {
				setLeftHand(null);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacter#addItem(org.adventure.items.IItem)
	 */
	@Override
	public void addItem(IItem item) {
		if (item instanceof QuantityItem) {
			QuantityItem quantityItem = (QuantityItem) item;
			if (QuantityItem.GOLD.equals(quantityItem.getName())) {
				getGold().add(quantityItem.getAmount());
			}
		}
		else if (getRightHand() == null) {
			setRightHand(item);
		}
		else {
			setLeftHand(item);
		}
	}

	
	
	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacter#canAddItem(org.adventure.items.IItem)
	 */
	@Override
	public boolean canAddItem(IItem item) {
		boolean result = false;
		if (item != null) {
			result = getFreeHands() > 0;
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see org.adventure.character.ICharacter#wear(org.adventure.items.IItem)
	 */
	@Override
	public boolean wear(IItem item) {
		boolean added = false;
		this.removeItem(item);
		if (item instanceof IWearable) {
			IWearable wearable = (IWearable) item;
			WearableType wearableType = wearable.getWearableType();
			
			List<IWearable> clothingType = getClothing().get(wearableType);
			if (clothingType == null) {
				clothingType = new ArrayList<IWearable>();
				getClothing().put(wearableType, clothingType);
			}
			if (clothingType.size() < determineAllowableNumberOfClothingType(wearableType)) {
				addWearableContainer(item);
				clothingType.add(wearable);
				added = true;
			}
		}
		else if (item instanceof Armor) {
			Armor armor = (Armor) item;
			for (BodyPartType bodyPartType : armor.getBodyPartTypes()) {
				if (getArmorMap().containsKey(bodyPartType)) {
					return false;
				}
			}
			for (BodyPartType bodyPartType : armor.getBodyPartTypes()) {
				getArmorMap().put(bodyPartType, armor);
				added = true;
			}
			getWornArmor().add(armor);
		}

		return added;
	}

	private int determineAllowableNumberOfClothingType(WearableType wearableType) {
		if (WearableType.SHOULDER.equals(wearableType)) {
			return 2;
		}
		return 1;
	}
	
	private void addWearableContainer(IItem item) {
		if (item instanceof IContainer) {
			IContainer container = (IContainer) item;
			getContainers().add(container);
		}
	}
	
	@Override
	public IWearable getWorn(WearableType type) {
		return getWorn(type, 0);
	}

	@Override
	public IWearable getWorn(WearableType type, int index) {
		List<IWearable> warnClothingOfType = getClothing().get(type);
		if (warnClothingOfType.size() > index) {
			return warnClothingOfType.get(index);			
		}
		return null;
	}

	@Override
	public boolean isWearing(IItem item) {
		if (item instanceof IWearable) {
			IWearable wearable = (IWearable) item;
			List<IWearable> warnClothingOfType = getClothing().get(wearable.getWearableType());
			if (warnClothingOfType != null && warnClothingOfType.contains(wearable)) {
				return true;
			}
		}
		else if (item instanceof Armor) {
			for (Armor armor : getArmorMap().values()) {
				if (armor.equals(item)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public ItemSearchResult getItem(String itemName) {
		int index = 0;
		if (itemName.toLowerCase().startsWith("other ")) {
			itemName = itemName.substring(6, itemName.length());
			index =  1;
		} 
		else if (itemName.toLowerCase().startsWith("3rd ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  2;
		} 
		else if (itemName.toLowerCase().startsWith("4th ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  3;
		} 
		if (getLeftHand() != null && getLeftHand().is(itemName)) {
			if (--index < 0) {
				return new ItemSearchResult(getLeftHand(), this);				
			}
		}
		if (getRightHand() != null && getRightHand().is(itemName)) {
			if (--index < 0) {
				return new ItemSearchResult(getRightHand(), this);
			}
		}
		for (List<IWearable> wearables : getClothing().values()) {
			for (IWearable iWearable : wearables) {
				if (iWearable.is(itemName)) {
					if (--index < 0) {
						return new ItemSearchResult(iWearable, this);
					}
				}
			}
		}
		for (Armor armor : getWornArmor()) {
			if (armor.is(itemName)) {
				if (index-- < 0) {
					return new ItemSearchResult(armor, this);
				}
			}
		}
		return null;
	}


	@Override
	public boolean isContentsVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setCurrentRoom(Room room) {
		this.currentRoom = room;
	}
	
	@Override
	public Room getCurrentRoom() {
		return this.currentRoom;
	}
	
	@Override
	public void addHealth(int amount) {
		if (amount < 0) {
			removeHealth(-amount);
		}
		else {
			if (getHealth() + amount >getMaxHealth()){
				setHealth(getMaxHealth());
			}
			else {
				setHealth(getHealth() + amount);			
			}	
			if (getPlayerState().equals(PlayerState.DEAD)) {
				this.setState(PlayerState.LAYING);
			}
		}
	}

	@Override
	public void removeHealth(int amount) {
		setHealth(getHealth() - amount);
		if (getHealth() <= 0) {
			playerDies();
		}
	}

	protected void playerDies() {
		this.setState(PlayerState.DEAD);
	}
	
	@Override
	public void setState(PlayerState playerState) {
		if (getPlayerState().equals(playerState) == false) {
			setPlayerState(playerState);
		}
	}

	@Override
	public void setMaxHealth(int health) {
		characterData.getMaxHealth().setStat(health);;
		setHealth(health);
	}

	@Override
	public void addSkillLevel(SkillType skillType) {
		getSkill(skillType).addLevel();
//		if (getSkills().containsKey(skillType) == false) {
//			getSkills().put(skillType, new Skill(skillType, 1));
//		}
//		else {
//			getSkills().get(skillType).addLevel();
//		}
	}

	protected TimerTask getTimerTask() {
		if (timerTask == null) {
			timerTask = new TimerTask() {
				@Override
				public void run() {
					try {
						Character.this.decrementBusyFor();
						Character.this.absorbExperience();
						Character.this.recoverMana();
						Character.this.expireEffects();
						Character.this.recoverEnergy();
						Character.this.loseEnergy();
					} catch (Exception e) {
						log.error(new StringBuilder("Error running timer task for character ").append(getName()).toString(), e);
					}
				}
			};
		}
		return timerTask;
	}
	
	
	public void addBusyFor(int busyFor) {
		busyFor =  (int)(busyFor + (1 - (0.1f * this.getSpeed())));
		if (busyFor < 0 ) {
			busyFor = 0;
		}
		sendDataMessage(new DataMessage().updateCharacter(getId(), "busy", busyFor));
		int initalValue = this.busyFor.getAndAdd(busyFor);
		if (initalValue <= 0 && timerTask == null) {
			try {
				getTimer().scheduleAtFixedRate(getTimerTask(), 0, 1000);
			} catch (Exception e) {
				timer = new Timer();
				getTimer().scheduleAtFixedRate(getTimerTask(), 0, 1000);
			}
		}
	}

	protected Timer getTimer() {
		return timer;
	}
	
	public int getBusyFor() {
		return busyFor.get();
	}

	public int decrementBusyFor() {
		int result = 0;
		if (this.busyFor.get() > 0) {
			result =  this.busyFor.decrementAndGet();
			sendDataMessage(new DataMessage().updateCharacter(getId(), "busy", result));
		}
		
		return result;
	}
	
	public void setBusyFor(int busyFor) {
		this.busyFor.set(busyFor);
	}
	
	@JsonIgnore
	public boolean isBusy() {
		return this.busyFor.get() > 0;
	}

	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getWeight() {
		return 0;
	}


	@Override
	public int getBodyWeight() {
		return 0;
	}


	@Override
	public boolean is(String name) {
		return false;
	}


	@Override
	public boolean commandAllowed(Action command, ICharacter character) {
		return false;
	}

	@Override
	public IItem addCommandCondition(Class command, CommandCondition commandCondition) {
		return null;
	}


	@Override
	public CommandCondition getCommandCondition(Class command) {
		return null;
	}


	@Override
	public void removeCommandCondition(Class command) {
		
	}

	@Override
	public void addExperience(int exp) {
		if (getMind() + exp <= getMaxMind()) {
			setMind(getMind() + exp);
		}
		else {
			setMind(getMaxMind());
		}
	}

	@Override
	public int getTotalSkillLevels() {
		int totalSkillLevel = 0;
		for (Skill skill : getSkills().values()) {
			totalSkillLevel = totalSkillLevel + skill.getLevel();
		}
		
		return totalSkillLevel;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (characterData == null) {
			if (other.characterData != null)
				return false;
		} else if (!characterData.equals(other.characterData))
			return false;
		return true;
	}


	public String getId() {
		return characterData.getId();
	}


	public String getRoomId() {
		return characterData.getRoomId();
	}


	public String getName() {
		return characterData.getName();
	}


	public int getMaxHealth() {
		return characterData.getMaxHealth().getStat();
	}


	public int getHealth() {
		return characterData.getHealth();
	}


	public int getMind() {
		return characterData.getMind();
	}


	public int getExperience() {
		return characterData.getExperience();
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


	public void setWill(int will) {
		characterData.setWill(will);
	}

	@Override
	public int getWill() {
		return characterData.getWill();
	}

	public void setReflex(int reflex) {
		characterData.setReflex(reflex);
	}

	@Override
	public int getReflex() {
		return characterData.getReflex();
	}

	@Override
	public int getStamina() {
		return characterData.getStamina();
	}

	public IItem getLeftHand() {
		return characterData.getLeftHand();
	}


	public IItem getRightHand() {
		return characterData.getRightHand();
	}


	public QuantityItem getGold() {
		return characterData.getGold();
	}

	public List<BodyPart> getBodyParts() {
		return characterData.getBodyParts();
	}

	public Map<BodyPartType, Armor> getArmorMap() {
		return characterData.getArmorMap();
	}

	public Armor getArmor(BodyPart bodyPart) {
		return characterData.getArmorMap().get(bodyPart.getBodyPartType());
	}
	
	public List<Armor> getWornArmor() {
		return characterData.getWornArmor();
	}

	public List<IContainer> getContainers() {
		return characterData.getContainers();
	}

	@Override
	public Map<WearableType, List<IWearable>> getClothing() {
		return characterData.getClothing();
	}


	public PlayerState getPlayerState() {
		return characterData.getPlayerState();
	}


	public Map<SkillType, Skill> getSkills() {
		return characterData.getSkills();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((characterData == null) ? 0 : characterData.hashCode());
		return result;
	}


	public void setId(String id) {
		characterData.setId(id);
	}


	public void setRoomId(String roomId) {
		characterData.setRoomId(roomId);
	}


	public void setName(String name) {
		characterData.setName(name);
	}

	public void setMaxHealth(StatReference maxHealth) {
		characterData.setMaxHealth(maxHealth);
	}

	public void setHealth(int health) {
		if (health < 0) {
			health = 0;
		}
		if (health != characterData.getHealth()) {
			characterData.setHealth(health);			
			sendDataMessageToRoom(new DataMessage().updateCharacter(getId(), "health", health), true);
		}
	}

	public void setMind(int mind) {
		if (mind < 0) {
			mind = 0;
		}
		if (mind != characterData.getMind()) {
			characterData.setMind(mind);			
			sendCharacterUpdate("mind", mind);
		}
	}

	public void setExperience(int experience) {
		characterData.setExperience(experience);
	}


	public void setStrength(int strength) {
		characterData.setStrength(strength);
	}


	public void setAgility(int agility) {
		characterData.setAgility(agility);
	}


	public void setSpeed(int speed) {
		characterData.setSpeed(speed);
	}


	public void setInteligence(int inteligence) {
		characterData.setInteligence(inteligence);
	}


	public void setConstitution(int constitution) {
		characterData.setConstitution(constitution);
	}


	public void setLeftHand(IItem leftHand) {
		characterData.setLeftHand(leftHand);
		sendDataMessageToRoom(new DataMessage().updateCharacter(getId(), "leftHand", leftHand), true);
	}


	public void setRightHand(IItem rightHand) {
		characterData.setRightHand(rightHand);
		sendDataMessageToRoom(new DataMessage().updateCharacter(getId(), "rightHand", rightHand), true);
	}


	public void setGold(QuantityItem gold) {
		characterData.setGold(gold);
		sendDataMessage(new DataMessage().updateCharacter(getId(), "gold", gold));
	}

	@Override
	public int getDeathCredits() {
		return characterData.getDeathCredits();
	}

	public void setBodyParts(List<BodyPart> bodyParts) {
		characterData.setBodyParts(bodyParts);
	}


	public void setArmorMap(Map<BodyPartType, Armor> armorMap) {
		characterData.setArmorMap(armorMap);
	}


	public void setWornArmor(List<Armor> wornArmor) {
		characterData.setWornArmor(wornArmor);
	}


	public void setContainers(List<IContainer> containers) {
		characterData.setContainers(containers);
	}


	public void setClothing(Map<WearableType, List<IWearable>> clothing) {
		characterData.setClothing(clothing);
	}


	public void setPlayerState(PlayerState playerState) {
		characterData.setPlayerState(playerState);
		sendDataMessageToRoom(new DataMessage().updateCharacter(getId(), "playerState", playerState.toString()), true);
	}


	public void setSkills(Map<SkillType, Skill> skills) {
		characterData.setSkills(skills);
	}

	public String toString() {
		return characterData.toString();
	}
	
	@Override
	public int getMaxMind() {
		return characterData.getMaxMind();
	}

	public int getCarriedWeight() {
		int total =0;
		for (List<IWearable> wearables : getClothing().values()) {
			for (IWearable iWearable : wearables) {
				if (iWearable instanceof IContainer) {
					IItem item = (IItem) iWearable;
					System.out.println(item.getName() + ":" + item.getWeight());
					total = total + item.getWeight();
				}
			}
		}
		for (Armor armor : getWornArmor()) {
			total = total + armor.getWeight();
		}
		return total;
	}
	
	@Override
	public void sendRoom() {
		if (this.characterSession != null) {
			this.characterSession.sendRoom(getId(), this.currentRoom);
		}
	}
	
	@Override
	public void sendMessage(String message) {
		if (this.characterSession != null) {
			this.characterSession.sendMessageToCharacter(this.getId(), message);			
		}
	}

	@Override
	public void sendMessageToRoom(String message) {
		if (currentRoom != null) {
			for (ICharacter character : currentRoom.getCharacters()) {
				if (character.getId().equals(this.getId()) == false) {
					character.sendMessage(message);
				}
			} 			
		}
	}

	@Override
	public void sendAttackResult(AttackResult attackResult) {
		if (this.characterSession != null) {
			this.characterSession.sendAttackResults(getId(), attackResult);
		}
	}
	
	@Override
	public void sendCharacter() {
		if (this.characterSession != null) {
			characterSession.sendCharacter(Character.this.getCharacterData());	
		}
	}
	
	@Override
	public void sendData(Map<String, Object> data) {
		if (this.characterSession != null) {
			characterSession.sendDataToCharacter(getId(), data);	
		}
	}
	
	public void sendCharacterUpdate(String property, Object value) {
		if (this.characterSession != null) {
			this.characterSession.sendCharacterUpdate(this, property, value);			
		}
	}
	
	public void sendDataMessageToRoom(DataMessage dataMessage, boolean includeCharacter) {
		if (currentRoom != null) {
			for (ICharacter character : currentRoom.getCharacters()) {
				if (character.getId().equals(this.getId()) == false || includeCharacter) {
					character.sendData(dataMessage.getData());
				}
			} 			
		}
	}
	
	public void sendDataMessage(DataMessage dataMessage) {
		sendData(dataMessage.getData());
	}


	
}
