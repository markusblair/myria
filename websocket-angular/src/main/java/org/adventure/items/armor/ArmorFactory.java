package org.adventure.items.armor;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.character.BodyPartType;
import org.adventure.items.IItem;
import org.adventure.items.IItemFactory;
import org.adventure.items.weapons.DamageType;
import org.adventure.items.weapons.Shield;

public class ArmorFactory implements IItemFactory {
	boolean includeShields = false;
	
	public ArmorFactory() {
		super();
	}

	public ArmorFactory(boolean includeShields) {
		super();
		this.includeShields = includeShields;
	}

	@Override
	public IItem createItem() {
		IItem item;
		int types = 3;
		if (includeShields) {
			types = types + 1;
		}
		switch (ThreadLocalRandom.current().nextInt(types)) {
		case 0:
			item = getLeather();
			break;
		case 1:
			item = getIron();
			break;
		case 2:
			item = getPaddedChain();
			break;
		case 3:
			item = getShield();
			break;
		default:
			item = getLeather();
			break;
		}

		return item;
	}

	public Armor getLeather() {
		Armor armor = new Armor();
		armor.setVolume(5);
		armor.setWeight(10);
		armor.setName("leather armor");
		armor.setArmorHealth(BodyPartType.ARM, 30);
		armor.setArmorHealth(BodyPartType.HEAD, 30);
		armor.setArmorHealth(BodyPartType.NECK, 30);
		armor.setArmorHealth(BodyPartType.LEG, 30);
		armor.setArmorHealth(BodyPartType.HAND, 30);
		armor.setArmorHealth(BodyPartType.FOOT, 30);
		armor.setArmorHealth(BodyPartType.TORSO, 30);
		armor.setArmorHealth(BodyPartType.BACK, 30);
		armor.addProtection(DamageType.SLASH, 2,8);
		armor.addProtection(DamageType.BLUNT, 1,5);
		armor.addProtection(DamageType.FIRE, 2,5);
		armor.addProtection(DamageType.COLD, 2,5);
		armor.setDeteriorationRate(DamageType.SLASH, 0.25d);
		armor.setDeteriorationRate(DamageType.FIRE, 0.25d);
		armor.setDeteriorationRate(DamageType.COLD, 0.25d);
		return armor;
	}
	
	public Armor getIron() {
		Armor armor = new Armor();
		armor.setVolume(15);
		armor.setWeight(150);
		armor.setName("iron armor");
		armor.setArmorHealth(BodyPartType.ARM, 40);
		armor.setArmorHealth(BodyPartType.HEAD, 40);
		armor.setArmorHealth(BodyPartType.NECK, 40);
		armor.setArmorHealth(BodyPartType.LEG, 40);
		armor.setArmorHealth(BodyPartType.HAND, 40);
		armor.setArmorHealth(BodyPartType.FOOT, 40);
		armor.setArmorHealth(BodyPartType.TORSO, 40);
		armor.setArmorHealth(BodyPartType.BACK, 40);
		armor.addProtection(DamageType.SLASH, 4,10);
		armor.addProtection(DamageType.BLUNT, 2,8);
		armor.addProtection(DamageType.PIERCE, 2,6);
		armor.addProtection(DamageType.FIRE, 2,5);
		armor.addProtection(DamageType.COLD, 2,5);
		armor.setDeteriorationRate(DamageType.SLASH, 0.10d);
		armor.setDeteriorationRate(DamageType.BLUNT, 0.30d);
		armor.setDeteriorationRate(DamageType.FIRE, 0.25d);
		armor.setDeteriorationRate(DamageType.COLD, 0.25d);
		return armor;
	}
	
	public Armor getPaddedChain() {
		Armor armor = new Armor();
		armor.setWeight(100);
		armor.setName("padded chainmail");
		armor.setArmorHealth(BodyPartType.ARM, 35);
		armor.setArmorHealth(BodyPartType.HEAD, 35);
		armor.setArmorHealth(BodyPartType.NECK, 35);
		armor.setArmorHealth(BodyPartType.LEG, 35);
		armor.setArmorHealth(BodyPartType.HAND, 35);
		armor.setArmorHealth(BodyPartType.FOOT, 35);
		armor.setArmorHealth(BodyPartType.TORSO, 35);
		armor.setArmorHealth(BodyPartType.BACK, 35);
		armor.addProtection(DamageType.SLASH, 2,8);
		armor.addProtection(DamageType.BLUNT, 2,10);
		armor.addProtection(DamageType.PIERCE, 1,5);
		armor.addProtection(DamageType.FIRE, 2,5);
		armor.addProtection(DamageType.COLD, 2,5);
		armor.setDeteriorationRate(DamageType.SLASH, 0.20d);
		armor.setDeteriorationRate(DamageType.BLUNT, 0.10d);
		armor.setDeteriorationRate(DamageType.FIRE, 0.25d);
		armor.setDeteriorationRate(DamageType.COLD, 0.25d);
		return armor;
		
	}
	
	public Shield getShield() {
		Shield shield = new Shield();
		shield.setName("Wooden Shield");
		shield.setDescription("A wooden shield.");
		return shield;
	}
}
