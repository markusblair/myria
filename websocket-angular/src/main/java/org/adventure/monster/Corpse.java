package org.adventure.monster;

import org.adventure.IContainer;
import org.adventure.character.ICharacter;
import org.adventure.items.Container;
import org.adventure.items.IItem;
import org.adventure.items.armor.Armor;

public class Corpse extends Container {

	public Corpse(ICharacter monster) {
		super();
		setVolumeCapacity(Integer.MAX_VALUE);
		setVolume(monster.getVolume());
		setWeight(monster.getBodyWeight());
		setName("A dead " + monster.getName());
		setContentsVisible(true);
		if (monster.getLeftHand() != null) {
			addItem(monster.getLeftHand());
			monster.setLeftHand(null);
		}
		else if (monster.getRightHand() != null) {
			addItem(monster.getRightHand());
			monster.setRightHand(null);
		}
		for (IContainer container : monster.getContainers()) {
			if (container instanceof IItem) {
				IItem item = (IItem) container;
				addItem(item);	
			}
		}
		for (Armor armor : monster.getWornArmor()) {
			addItem(armor);
		}
		addItem(monster.getGold());
		monster.getGold().remove(monster.getGold().getAmount());
	}

	
	
}
