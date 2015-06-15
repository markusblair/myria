package org.adventure.monster;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.character.WebSocketDataService;
import org.adventure.items.weapons.WeaponFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ThiefFactory implements IMonsterFactory {
	@Autowired
	private WebSocketDataService webSocketDataService;
	
	@Override
	public Monster createMonster() {
		WeaponFactory weaponFactory = new WeaponFactory();
		
		int type = ThreadLocalRandom.current().nextInt(2);
		Monster thief = null;
		
		if (type == 0) {
			thief = new Monster("thief", 4, 150, 7, webSocketDataService);
			thief.addItem(weaponFactory.newLongSword());
		}
		else if (type == 1) {
			thief = new Monster("thief", 4, 150, 7, webSocketDataService);
			thief.addItem(weaponFactory.newDagger());
		}
		else {
			thief = new Monster("thief", 4, 150, 7, webSocketDataService);
			thief.addItem(weaponFactory.newHandAxe());
		}
		
		return thief;
	}

}
