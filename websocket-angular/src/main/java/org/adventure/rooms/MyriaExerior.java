package org.adventure.rooms;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.commands.navigation.BiDirection;
import org.adventure.commands.navigation.Direction;
import org.adventure.commands.navigation.MonsterRestrictionCondition;
import org.adventure.monster.IMonsterFactory;
import org.adventure.monster.MonsterSpawner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyriaExerior {

	@Autowired
	public RoomGridFactory roomGridFactory;
	
	@Autowired 
	RoomPathFactory roomPathFactor;
	
	@Autowired
	public RoomManager roomManager;
	
	@Autowired
	public IMonsterFactory monsterFactory;
	
	@Autowired
	public IMonsterFactory lumberForestMonsterFactory;
	
	@Bean
	public Room forest() {
		roomGridFactory.createRoomGrid("northRoad", 1, 7, "A road leading through a dense forest.", 0);
		
		/*
		 * Outside the North Gate
		 */
		String roomID = roomGridFactory.createId("northRoad", 0, 0);
		roomManager.getRoom(roomID).setDescription("A wide cobbelstone road.  The Northern gate of Myria stands to your south.  The walls of the city stretch East towards the bay and west towards a large guard tower.  There are stables to your east.");
		
		roomManager.addBiDirectionalNavigation(roomID, BiDirection.NORTH_SOUTH, CityOfMyria.N_HIGH_3,
				new MonsterRestrictionCondition());		
		
		
		/*
		 * 
		 */
		roomID = roomGridFactory.createId("northRoad", 1, 0);
		roomManager.getRoom(roomID).setDescription("A wide cobbelstone road. A sparse forest with lines each side of the road.  There are many side trails leading off towards small non discript wooden homes. ");
		
		roomID = roomGridFactory.createId("northRoad", 2, 0);
		roomManager.getRoom(roomID).setDescription("A wide cobbelstone road. The forest that lines each side of the road seems to become denser to the north. ");
	
		/*
		 * Fork in the road.
		 */
		String forkInTheRoad = roomGridFactory.createId("northRoad", 3, 0);
		String roomID2 = roomGridFactory.createId("northRoad", 6, 0);
		roomManager.getRoom(forkInTheRoad).setDescription("A stone column that is capped with an wrote iron emblem stands in the center of fork in the road.  At it's base it is surrounded by a low stone wall an flower bed.");
		//TODO:   Need to be able to examine signs/column to see distances.
		Room room =  roomManager.getRoom(roomID);
		Room room2 = roomManager.getRoom(roomID2);
		
		MonsterSpawner spawner = new MonsterSpawner(monsterFactory, 60000, 5);
		spawner.setMaxMonsterCount(2);
		spawner.addRoom(room2);
		
		roomPathFactor.setName("The Myrian Way");
		roomPathFactor.setCurrentRoom(forkInTheRoad);
		roomPathFactor.setTravelSpeed(0);
		roomPathFactor.setDefaultDescription("A wide cobbelstone road. Underbrush of the forest lines both sides of the road.");
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTH);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTH);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		room = roomPathFactor.createNewRoom(Direction.EAST);
		room.setDescription("Here a well traveled dirt road leads north into the woods. A well mainted stone bridge fords the stream to the east.");
		RoomPathFactory loggingRoad = roomPathFactor.createNewRoomPathFactory();
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTH);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTH).setDescription("A wide cobbelstone road. To the east you hear the squaking of seaguls.");
		roomPathFactor.createNewRoom(Direction.NORTHEAST).setDescription("A wide cobbelstone road. To the east you hear the squaking of seaguls.");
		roomPathFactor.createNewRoom(Direction.EAST).setDescription("Here the forest to the south gives way the cobbelstone road continues along the edge of the forest. ");
		roomPathFactor.createNewRoom(Direction.NORTHEAST).setDescription("A wide cobbelstone road follows the edge of the forest. To the east over the cliffs of Veron you can see the Myrain Bay ");
		roomPathFactor.setDefaultDescription("A wide cobbelstone road follows the edge of the forest. ");
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.NORTH);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.NORTHEAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		roomPathFactor.createNewRoom(Direction.EAST);
		
		loggingRoad.setDefaultDescription("A well traveled dirt road winds it's way along a wide shallow stream.");
		loggingRoad.createNewRoom(Direction.NORTH, new MonsterRestrictionCondition());
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.NORTHWEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.NORTHWEST);
		loggingRoad.createNewRoom(Direction.WEST);
		room = loggingRoad.createNewRoom(Direction.NORTHWEST);
		room.setDescription("To the north is a clearing in the woods.  The slight smell of smoke waifs through the air.  Several wooden guard towers stand around the perimter of the clearing.  A hand full of building litter the clearing.");
		Room lumberCamp = loggingRoad.createNewRoom(Direction.NORTH);
		lumberCamp.setDescription("In the Northeast corner of the clearing a large building is nestled next to a stream.   The sound of running watter and the sawing of lumber echos from behind building.  Several wagons sit outside the building with varying degrees of lumber loaded into them. ");
		loggingRoad.setAutojoinAdjacentRooms(true);
		loggingRoad.setDefaultDescription("A forest");
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.SOUTH);
		loggingRoad.createNewRoom(Direction.SOUTH);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.WEST);
		loggingRoad.createNewRoom(Direction.NORTH);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		loggingRoad.createNewRoom(Direction.EAST);
		spawner = new MonsterSpawner(lumberForestMonsterFactory, 60000, 5);
		spawner.setMaxMonsterCount(8);
		spawner.addRoom(loggingRoad.createNewRoom(Direction.EAST));
		loggingRoad.createNewRoom(Direction.EAST);
		return room;
	}
}
