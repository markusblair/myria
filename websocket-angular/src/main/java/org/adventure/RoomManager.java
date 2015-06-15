package org.adventure;

import java.util.concurrent.ConcurrentHashMap;

import org.adventure.commands.CommandCondition;
import org.adventure.commands.navigation.BiDirection;
import org.adventure.commands.navigation.Direction;
import org.adventure.commands.navigation.MoveCommand;
import org.adventure.commands.navigation.PlayerMovementCondition;
import org.adventure.items.Container;
import org.adventure.items.IItemFactory;
import org.springframework.stereotype.Component;

@Component
public class RoomManager {
	private ConcurrentHashMap<String, Room> roomMap = new ConcurrentHashMap<String, Room>();
	
	public boolean hasRoom(String id) {
		return roomMap.containsKey(id);
	}
	
	public Room getRoom(String id) {
		return getRoom(id, true);
	}
	
	public Room getRoom(String id, boolean createIfMissing) {
		Room result = roomMap.get(id);
		if (result == null && createIfMissing) {
			result = createRoom(id);
		}
		return result;
	}
	
	public Store createStore(String id, IItemFactory itemFactory, Container... containers) {
		if (roomMap.containsKey(id)) {
			throw new RuntimeException("Duplicate Room Id :" + id);
		}
		Store room = new Store(id, itemFactory, containers);
		roomMap.put(id, room);
		return room;
	}
	
	public Room createRoom(String id) {
		if (roomMap.contains(id)) {
			throw new RuntimeException("Duplicate Room Id :" + id);
		}
		Room room = new Room();
		room.setId(id);
		roomMap.put(id, room);
		return room;
	}
	
	public void addRoom(Room room) {
		roomMap.put(room.getId(), room);
	}
	
	public void addBiDirectionalNavigation(String fromRoomId, BiDirection direction, String toRoomId, CommandCondition commandCondition) {
		Room toRoom = getRoom(toRoomId);
		Room fromRoom = getRoom(fromRoomId);
		toRoom.addCommand(new MoveCommand(fromRoom, direction.getDirection()), new PlayerMovementCondition());			
		if (commandCondition == null) {
			fromRoom.addCommand(new MoveCommand(toRoom, direction.getReverseDireciton()), new PlayerMovementCondition());
		}
		else {
			fromRoom.addCommand(new MoveCommand(toRoom, direction.getReverseDireciton()), commandCondition.setCommandConditionChain(new PlayerMovementCondition()));	
		}
	
	}
	
	public void addBiDirectionalNavigation(String fromRoomId, BiDirection direction, String toRoomId) {
		addBiDirectionalNavigation(fromRoomId, direction, toRoomId, null);
	}
	
	public void addDirectionalNavigation(Room fromRoom, Direction direction, Room toRoom) {
		fromRoom.addCommand(new MoveCommand(toRoom, direction));
	}
	
	public void addDirectionalNavigation(Room fromRoom, Direction direction, Room toRoom, CommandCondition toRoomCondition) {
		fromRoom.addCommand(new MoveCommand(toRoom, direction), toRoomCondition);
	}
}
