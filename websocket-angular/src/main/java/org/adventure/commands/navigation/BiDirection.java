package org.adventure.commands.navigation;

public enum BiDirection {
	NORTH_SOUTH(Direction.NORTH, Direction.SOUTH),
	SOUTH_NORTH(Direction.SOUTH, Direction.NORTH),
	WEST_EAST(Direction.WEST, Direction.EAST),
	EAST_WEST(Direction.EAST, Direction.WEST),
	NORTHEAST_SOUTHWEST(Direction.NORTHEAST, Direction.SOUTHWEST),
	SOUTHWEST_NORTHEAST(Direction.SOUTHWEST, Direction.NORTHEAST),
	NORTHWEST_SOUTHEAST(Direction.NORTHWEST, Direction.SOUTHEAST),
	SOUTHEAST_NORTHWEST(Direction.SOUTHEAST, Direction.NORTHWEST),
	DOOR_DOOR(Direction.DOOR, Direction.DOOR);
	
	private Direction direction;
	private Direction reverseDireciton;
	private BiDirection(Direction direction, Direction reverseDireciton) {
		this.direction = direction;
		this.reverseDireciton = reverseDireciton;
	}
	public Direction getDirection() {
		return direction;
	}
	public Direction getReverseDireciton() {
		return reverseDireciton;
	}
	
	
}
