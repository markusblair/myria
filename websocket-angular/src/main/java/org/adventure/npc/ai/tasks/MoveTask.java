package org.adventure.npc.ai.tasks;

import java.util.List;

import org.adventure.commands.Action;
import org.adventure.commands.navigation.Direction;
import org.adventure.commands.navigation.MoveCommand;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;
import org.adventure.random.RandomCollection;
import org.adventure.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveTask extends Task {

	Logger log = LoggerFactory.getLogger(MoveTask.class);
	
	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		Room currentRoom = monster.getCurrentRoom();
		Direction direction = new RandomCollection<>(currentRoom.getExits()).next();
		List<Action> actions = currentRoom.getValidCommands(monster);
		for (Action action : actions) {
			if (action instanceof MoveCommand) {
				MoveCommand move = (MoveCommand) action;
				if (move.getDirection().equals(direction)) {
					move.move(monster);
					log.debug("Move :" + direction + " - " + context);
				}
			}
		}

		context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);
	}


}
