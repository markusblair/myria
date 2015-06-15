package org.adventure.commands.combat;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.items.weapons.LoadableWeapon;
import org.adventure.items.weapons.Weapon;

public class LoadCommand extends CharacterCommand {

	public LoadCommand() {
			super();
			this.addCommandPattern("load");
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result = super.isAllowed(character);
		Weapon weapon = character.getWeapon();
		if (weapon instanceof LoadableWeapon) {
			LoadableWeapon loadableWeapon = (LoadableWeapon) weapon;
			if (loadableWeapon.isLoaded()) {
				character.sendMessage("Your " + weapon.getName() + " is already loaded.");
				result =  false;
			}
		}
		else {
			character.sendMessage("Your " + weapon.getName() + " is not loadable.");
			result = false;
		}
		return result;
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		int time = 6;
		Weapon weapon = character.getWeapon();
		if (weapon instanceof LoadableWeapon) {
			LoadableWeapon loadableWeapon = (LoadableWeapon) weapon;
			time = loadableWeapon.getBaseLoadTime();
		}
		
		return time;
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		Weapon weapon = character.getWeapon();
		if (weapon instanceof LoadableWeapon) {
			LoadableWeapon loadableWeapon = (LoadableWeapon) weapon;
			loadableWeapon.setLoaded(true);
			character.sendMessage("You load your " + loadableWeapon.getName());
			character.sendMessageToRoom(" loads their " + loadableWeapon.getName());
		}
	}

	
	
}
