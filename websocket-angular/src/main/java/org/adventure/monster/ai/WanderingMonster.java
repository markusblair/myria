package org.adventure.monster.ai;

import java.util.List;

import org.adventure.commands.Action;
import org.adventure.commands.navigation.Direction;
import org.adventure.commands.navigation.MoveCommand;
import org.adventure.monster.Monster;
import org.adventure.random.RandomCollection;
import org.adventure.rooms.Room;

public class WanderingMonster implements IAiChainHandler {
	public WanderingMonster() {
	}

	@Override
	public boolean handle(Monster monster) {
		Room currentRoom = monster.getCurrentRoom();
		Direction direction = new RandomCollection<>(currentRoom.getExits()).next();
		List<Action> actions = currentRoom.getValidCommands(monster);
		for (Action action : actions) {
			if (action instanceof MoveCommand) {
				MoveCommand move = (MoveCommand) action;
				if (move.getDirection().equals(direction)) {
					move.move(monster);
				}
			}
		}
		return true;
	}

}
