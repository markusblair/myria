package org.adventure.commands;

import org.adventure.MarketValuator;
import org.adventure.items.weapons.WeaponFactory;
import org.junit.Test;

public class MarketValuationTest {

	@Test
	public void testWeaponValues() {
		MarketValuator marketValuator = new MarketValuator();
		WeaponFactory weaponFactory = new WeaponFactory();
		int value = marketValuator.determineValue(weaponFactory.newDagger());
		System.out.println(value);
		
		value = marketValuator.determineValue(weaponFactory.newLongSword());
		System.out.println(value);
		
		value = marketValuator.determineValue(weaponFactory.newGreatSword());
		System.out.println(value);
	}

}
