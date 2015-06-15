package org.adventure.monster;

import java.util.concurrent.ThreadLocalRandom;

import org.adventure.character.WebSocketDataService;
import org.adventure.items.armor.ArmorFactory;
import org.adventure.items.weapons.WeaponFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LumberForestMonsterFactory implements IMonsterFactory {
	Logger log = LoggerFactory.getLogger(LumberForestMonsterFactory.class);
	ArmorFactory armorFactory = new ArmorFactory();
	WeaponFactory weaponFactory = new WeaponFactory();
	/* (non-Javadoc)
	 * @see org.adventure.monster.IMonsterFactory#createMonster()
	 */
	
	@Autowired
	private WebSocketDataService webSocketDataService;
	
		@Override
		public Monster createMonster() {
			Monster monster;
			int level = getLevel();
			switch (level) {
			case 5:
				monster = new Monster("Hobgoblin", 3, 40, 4, webSocketDataService);
				break;
			case 3: case 4:
				monster = new Monster("Warg", 2, 25, 4, webSocketDataService);
				break;
			default:
				monster = new Monster("Goblin", 1, 15, 4, webSocketDataService);
				break;
			} 
			if (level > 0)
				addItems(monster);
			return monster;
		}
		
		public int getLevel() {
			return ThreadLocalRandom.current().nextInt(6);
		}


		private void addItems(Monster monster) {
			log.debug("Adding armor and weapons.");
			monster.addItem(weaponFactory.createItem());
			monster.wear(armorFactory.createItem());
		}
}
