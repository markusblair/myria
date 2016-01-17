package org.adventure.rooms.areas;

import org.adventure.commands.CommandCondition;
import org.adventure.commands.navigation.BiDirection;
import org.adventure.commands.navigation.Direction;
import org.adventure.rooms.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomPathFactory {

	@Autowired
	private RoomManager roomManager;

	private Coordinates coordinates = new Coordinates(0, 0);
	private Room room;
	private int forks;
	private String prefix = "path";
	private String defaultDescription;
	private int travelSpeed = 10;
	private String name;
	private boolean autojoinAdjacentRooms = false;
	
	
	
	public void setCurrentRoom(String roomId) {
		this.room = roomManager.getRoom(roomId);
	}
	
	public Room createNewRoom(Direction direction) {
		return createNewRoom(direction, null);
	}
	
	public Room createNewRoom(Direction direction,  CommandCondition toRoomCondition) {
		Coordinates newRoomCoordinates = getCoordinates(direction);
		String newRoomId = createId(prefix, newRoomCoordinates);
		if (roomManager.hasRoom(newRoomId)) {
			throw new RuntimeException("Room already exists:" + newRoomId);
		}
		else {
			Room room = roomManager.getRoom(newRoomId);
			roomManager.addBiDirectionalNavigation(newRoomId, getBiDirection(direction), this.room.getId(), toRoomCondition);
			
			this.coordinates = newRoomCoordinates;
			this.room = room;
			this.room.setName(this.getName());
			this.room.setDescription(this.getDefaultDescription());
			this.room.setTravelSpeed(this.getTravelSpeed());	
			
			if (isAutojoinAdjacentRooms()) {
//				System.out.println("Adding room id: " + newRoomId);
				for (Direction d : Direction.values()) {
					String adjacentRoomId = createId(prefix, getCoordinates(d));
//					System.out.println("    adj room id exists? :" + adjacentRoomId);
					if (roomManager.hasRoom(adjacentRoomId)) {
						roomManager.addBiDirectionalNavigation(adjacentRoomId, getBiDirection(d), this.room.getId());
//						System.out.println("         Adding bi directional " + getBiDirection(d) + " navigaiton " + adjacentRoomId + "  to " +this.room.getId());
					}
				}
			}
		}
		return room;
	}
	
	public RoomPathFactory createNewRoomPathFactory() {
		RoomPathFactory roomPathFactory = new RoomPathFactory();
		roomPathFactory.prefix = this.prefix + "_" + forks++;
		roomPathFactory.roomManager = this.roomManager;
		roomPathFactory.room = this.room;
		return roomPathFactory;
	}
	
	public String getDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

	public int getTravelSpeed() {
		return travelSpeed;
	}

	public void setTravelSpeed(int travelSpeed) {
		this.travelSpeed = travelSpeed;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAutojoinAdjacentRooms() {
		return autojoinAdjacentRooms;
	}

	public void setAutojoinAdjacentRooms(boolean autojoinAdjacentRooms) {
		this.autojoinAdjacentRooms = autojoinAdjacentRooms;
	}

	protected BiDirection getBiDirection(Direction direction) {
		if (direction.equals(Direction.NORTH)) {
			return BiDirection.NORTH_SOUTH;
		} else if (direction.equals(Direction.SOUTH)) {
			return BiDirection.SOUTH_NORTH;
		} else if (direction.equals(Direction.EAST)) {
			return BiDirection.EAST_WEST;
		} else if (direction.equals(Direction.WEST)) {
			return BiDirection.WEST_EAST;
		} else if (direction.equals(Direction.NORTHEAST)) {
			return BiDirection.NORTHEAST_SOUTHWEST;
		} else if (direction.equals(Direction.NORTHWEST)) {
			return BiDirection.NORTHWEST_SOUTHEAST;
		} else if (direction.equals(Direction.SOUTHEAST)) {
			return BiDirection.SOUTHEAST_NORTHWEST;
		} else {
			return BiDirection.SOUTHWEST_NORTHEAST;
		}
	}
	
	protected Coordinates getCoordinates(Direction direction) {
		if (direction.equals(Direction.NORTH)) {
			return new Coordinates(this.coordinates.getX(), this.coordinates.getY() + 1);
		} else if (direction.equals(Direction.SOUTH)) {
			return new Coordinates(this.coordinates.getX(), this.coordinates.getY() - 1);
		} else if (direction.equals(Direction.EAST)) {
			return new Coordinates(this.coordinates.getX() + 1, this.coordinates.getY());
		} else if (direction.equals(Direction.WEST)) {
			return new Coordinates(this.coordinates.getX() - 1, this.coordinates.getY());
		} else if (direction.equals(Direction.NORTHEAST)) {
			return new Coordinates(this.coordinates.getX() + 1, this.coordinates.getY() + 1);
		} else if (direction.equals(Direction.NORTHWEST)) {
			return new Coordinates(this.coordinates.getX() - 1, this.coordinates.getY() + 1);
		} else if (direction.equals(Direction.SOUTHEAST)) {
			return new Coordinates(this.coordinates.getX() + 1, this.coordinates.getY() - 1);
		} else { // SOUTHEST
			return new Coordinates(this.coordinates.getX() - 1, this.coordinates.getY() - 1);
		}
	}
	
	protected String createId(String prefix, Coordinates coord) {
		return new StringBuilder(prefix).append("_").append(coord.getX()).append("_").append(coord.getY()).toString();
	}
	
	class Coordinates {
		private int x;
		private int y;
		
		
		public Coordinates(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		
		
	}
}
