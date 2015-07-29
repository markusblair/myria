package org.adventure.items.weapons;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.items.IItem;
import org.adventure.items.IItemFactory;
import org.adventure.random.SkillType;

public class WeaponFactory implements IItemFactory {
	
	@Override
	public IItem createItem() {
		IItem item;
		switch (ThreadLocalRandom.current().nextInt(10)) {
		case 0:
			item = newLongSword();
			break;
		case 1:
			item = newShortSword();
			break;
		case 2:
			item = newGreatSword();
			break;
		case 3:
			item = newHandAxe();
			break;
		case 4:
			item = newWarAxe();
			break;
		case 5:
			item = newMaceLight();
			break;
		case 6:
			item = newMaceHeavy();
			break;
		case 7:
			item = newDagger();
			break;
		default:
			item = newShortBow();
			break;
		}

		return item;
	}

	public Weapon newDagger() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.KNIFE);
		w.setName("dagger");
		w.addDamage("slashes", DamageType.SLASH, 4, 2);
		w.addDamage("stabs", DamageType.PIERCE, 4, 2);
		w.setVolume(2);
		w.setWeight(2);
		w.setBaseAttackRate(4);
		return w;
	}
	
	public Weapon newLongSword() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.SWORD);
		w.setName("long sword");
		w.addDamage("slashes", DamageType.SLASH, 10, 5);
		w.addDamage("slashes", DamageType.BLUNT, 10, 5);
		w.addDamage("stabs", DamageType.PIERCE, 10, 8);
		w.setVolume(15);
		w.setWeight(15);
		w.setBaseAttackRate(8);
		return w;
	}
	
	public Weapon newShortSword() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.SWORD);
		w.setName("short sword");
		w.addDamage("slashes",DamageType.SLASH, 8, 4);
		w.addDamage("slashes",DamageType.BLUNT, 8, 3);
		w.addDamage("stabs", DamageType.PIERCE, 8, 5);
		w.setVolume(10);
		w.setWeight(10);
		w.setBaseAttackRate(6);
		return w;
		
	}
		
	public Weapon newGreatSword() {
		Weapon w = new Weapon();
		w.setName("great sword");
		w.setWeaponType(SkillType.TWO_HANDDED_SWORD);
		w.addDamage("slashes",DamageType.SLASH, 16, 8);
		w.addDamage("slashes",DamageType.BLUNT, 16, 8);
		w.addDamage("stabs", DamageType.PIERCE, 12, 6);
		w.setVolume(20);
		w.setWeight(20);
		w.setBaseAttackRate(10);
		return w;
	}
		
	public Weapon newHandAxe() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.AXE);
		w.setName("hand axe");
		w.addDamage("slashes",DamageType.SLASH, 8, 2);
		w.addDamage("slashes",DamageType.BLUNT, 8, 3);
		w.setVolume(10);
		w.setWeight(10);
		w.setBaseAttackRate(5);
		return w;
		
	}
		
	public Weapon newWarAxe() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.TWO_HANDED_AXE);
		w.setName("war axe");
		w.addDamage("slashes",DamageType.SLASH, 25, 10);
		w.addDamage("slashes",DamageType.BLUNT, 25, 10);
		w.setVolume(20);
		w.setWeight(20);
		w.setBaseAttackRate(12);
		return w;
	}
		
	public Weapon newMaceLight() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.MACE);
		w.setName("light mace");
		w.addDamage("hits",DamageType.BLUNT, 10, 7);
		w.setVolume(10);
		w.setWeight(10);
		w.setBaseAttackRate(6);
		return w;
	}
	
	public Weapon newShortBow() {
		LoadableWeapon w  = new LoadableWeapon();
		w.setWeaponType(SkillType.BOW);
		w.setName("Short Bow");
		w.addDamage("hits", DamageType.PIERCE, 20, 3);
		w.setVolume(5);
		w.setWeight(5);
		w.setBaseAttackRate(1);
		w.setBaseLoadTime(5);
		return w;
	}
	
	public Weapon newMaceHeavy() {
		Weapon w = new Weapon();
		w.setWeaponType(SkillType.MACE);
		w.setName("heavy mace");
		w.addDamage("hits",DamageType.BLUNT, 14, 10);
		w.setVolume(15);
		w.setWeight(15);
		w.setBaseAttackRate(8);
		return w;
	}
		
}
