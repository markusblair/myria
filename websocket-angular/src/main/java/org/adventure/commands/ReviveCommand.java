package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.adventure.monster.Corpse;
import org.adventure.rooms.Room;
import org.adventure.rooms.areas.CityOfMyria;
import org.adventure.rooms.areas.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviveCommand extends Action {
	@Autowired
	private RoomManager roomManager;
	
	public ReviveCommand() {
		this.addCommandPattern("revive");
	}

	@Override
	public void action(Command command, ICharacter character) {
		final Corpse corpse = new Corpse(character);
		character.getCurrentRoom().addItem(corpse);
		//Reset all item lists.  
		character.getContainers().clear();
		character.getWornArmor().clear();
		character.getClothing().clear();
		character.setExperience(character.getExperience() - 10000);
		character.setMind(0);
		Room room = roomManager.getRoom(CityOfMyria.TEMPLE_OF_ELANDOR);
		new HealCommand().action(command, character);
		character.gotoRoom(room);
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		boolean result =  super.isAllowed(character);
		if (character.getPlayerState().equals(PlayerState.DEAD) == false) {
			result = false;
			character.sendMessage("Huh? Are you wishing you were dead?");
		}
		if (character.getDeathCredits() <=0) {
			character.sendMessage("Sorry, you have lost favor with the gods.");
			result = false;
		}
		return result;
	}
	
	

}
