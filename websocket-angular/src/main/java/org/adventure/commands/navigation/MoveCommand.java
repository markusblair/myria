package org.adventure.commands.navigation;
import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.adventure.commands.Action;
import org.adventure.commands.Command;
import org.adventure.rooms.Room;

public class MoveCommand extends Action {
	private Room roomToMoveTo;
	private Direction direction;
	
	public MoveCommand(Room roomToMoveTo, Direction direction) {
		super();
		this.roomToMoveTo = roomToMoveTo;
		this.direction = direction;
		this.addCommandPattern(direction.getValidValues());
	}
	
	@Override
	public void action(Command command, ICharacter character) {
		move(character);
	}

	@Override
	public boolean isAllowed(ICharacter character) {
		if ( character.getPlayerState().equals(PlayerState.STANDING) == false) {
			character.sendMessage("You must stand up to move.");
			return false;
		}
		boolean result = super.isAllowed(character);
		return result;
	}
	
	public void move(ICharacter character) {
		//character.setBusyFor(character.getCurrentRoom().getTravelSpeed());
		character.gotoRoom(roomToMoveTo);			
	}

	@Override
	public int getExecutionTime(ICharacter character) {
		return character.getCurrentRoom().getTravelSpeed();
	}

	public Direction getDirection() {
		return direction;
	}

	
	
	
}
