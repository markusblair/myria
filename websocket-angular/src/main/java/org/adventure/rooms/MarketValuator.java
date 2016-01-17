package org.adventure.rooms;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.items.IItem;
import org.adventure.items.Wearable;
import org.adventure.items.armor.Armor;
import org.adventure.items.food.Food;
import org.adventure.items.weapons.LoadableWeapon;
import org.adventure.items.weapons.Weapon;

public class MarketValuator {
	public int determineValue(IItem item) {
		int value = baseValue(item);
		return value;
	}

	
	public int determineSellValue(IItem item) {
		int value = baseValue(item);
		value = value /10;
		return value;
	}
	

	private int baseValue(IItem item) {
		if (item instanceof Weapon) {
			Weapon weapon = (Weapon) item;
			float baseAttackRate = weapon.getBaseAttackRate();
			if (weapon instanceof LoadableWeapon) {
				LoadableWeapon loadableWeapon = (LoadableWeapon) weapon;
				baseAttackRate = baseAttackRate + loadableWeapon.getBaseLoadTime();
			}
			
			float maxMeanDamage = weapon.maxMeanDamageAttack();
			float damagePerSec = 1 + maxMeanDamage/baseAttackRate;
			int value = Math.round(damagePerSec * damagePerSec * damagePerSec * damagePerSec * damagePerSec * damagePerSec * damagePerSec);
			return value;
		}
		if (item instanceof Food) {
			Food food = (Food)item;
			return food.getEnergy()/ 50;
		}
		if (item instanceof Armor) {
			Armor armor = (Armor) item;
			
		}
		if (item instanceof Wearable) {
			Wearable wearable = (Wearable) item;
			
		}
		return ThreadLocalRandom.current().nextInt(100);
	}
}
