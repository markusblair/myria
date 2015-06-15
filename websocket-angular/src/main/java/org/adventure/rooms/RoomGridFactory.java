package org.adventure.rooms;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.commands.navigation.BiDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomGridFactory {
	@Autowired
	private RoomManager roomManager;
	
	public void createRoomGrid(String prefix, int columns, int rows, String description, int travelSpeed) {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				String roomId = createId(prefix, x, y);
				Room room = roomManager.getRoom(roomId);
				room.setName(roomId);
				room.setDescription(description);
				room.setTravelSpeed(travelSpeed);
				if (x + 1 != rows) {
					roomManager.addBiDirectionalNavigation(roomId, BiDirection.SOUTH_NORTH, createId(prefix, x+1, y));
				}
				if (y + 1 != columns) {
					if (x + 1 != rows) {
						roomManager.addBiDirectionalNavigation(roomId, BiDirection.SOUTHWEST_NORTHEAST, createId(prefix, x+1, y+1));
					}					
					roomManager.addBiDirectionalNavigation(roomId, BiDirection.WEST_EAST, createId(prefix, x, y+1));
					if (x != 0) {
						roomManager.addBiDirectionalNavigation(roomId, BiDirection.NORTHWEST_SOUTHEAST, createId(prefix, x-1, y+1));
					}
				}
				if (x != 0) {
					roomManager.addBiDirectionalNavigation(roomId, BiDirection.NORTH_SOUTH, createId(prefix, x-1, y));
				}
			}
		}
	}

	protected String createId(String prefix, int x, int y) {
		return new StringBuilder(prefix).append("_").append(x).append("_").append(y).toString();
	}
}
