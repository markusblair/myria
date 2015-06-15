package org.adventure.rooms;

import org.adventure.Room;
import org.adventure.RoomManager;
import org.adventure.TrainingRoom;
import org.adventure.commands.HealCommand;
import org.adventure.commands.navigation.BiDirection;
import org.adventure.commands.navigation.Direction;
import org.adventure.items.Container;
import org.adventure.items.IItemFactory;
import org.adventure.items.WearableContainerFactory;
import org.adventure.items.WearableFactory;
import org.adventure.items.armor.ArmorFactory;
import org.adventure.items.weapons.WeaponFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CityOfMyria {
	
	private static final String MYRIAMS_ALE_HOUSE = "myriamsAleHouse";
	public static final String CENTRAL_PLAZA = "centralPlaza";
	private static final String CLOES_CLOTHING = "cloesClothing";
	private static final String CLOE_ENTRANCE = "cloe-entrance";
	private static final String WAS_WEAPONS = "wasWeapons";
	private static final String WAS_WEAPONS_ENTRANCE = "wasWeapons-entrance";
	private static final String WAS_ARMOR = "wasArmor";
	private static final String WAS_ARMOR_ENTRANCE = "wasArmor-entrance";
	private static final String IF_THE_SHOE_FITS = "ifTheShoeFits";
	private static final String N_HIGH_1 = "myrian-n-high-1";
	private static final String N_HIGH_2 = "myrian-n-high-2";
	public static final String N_HIGH_3 = "myrian-n-high-3";
	public static final String TRAVELERS_TRINKETS_ENTRANCE = "travelers-trinkets-entrance";
	public static final String TRAVELERS_TRINKETS = "travelers-trinkets";
	public static final String WINDING_ROAD_TO_TEMPLE_1 = "WINDING_ROAD_TO_TEMPLE_1";
	public static final String WINDING_ROAD_TO_TEMPLE_2 = "WINDING_ROAD_TO_TEMPLE_2";
	public static final String WINDING_ROAD_TO_TEMPLE_3 = "WINDING_ROAD_TO_TEMPLE_3";
	public static final String WINDING_ROAD_TO_TEMPLE_4 = "WINDING_ROAD_TO_TEMPLE_4";
	public static final String TEMPLE_OF_ELANDOR = "TEMPLE_OF_ELANDOR";
	
	public static final String TRAINER = "TRAINER";
	
	@Autowired 
	RoomPathFactory roomPathFactor;
	
	@Autowired
	private RoomManager roomManager;
	
	@Bean
	public Room start() {
		
		Room aleHouse = roomManager.createRoom(MYRIAMS_ALE_HOUSE);
		aleHouse.setDescription("dark wood bar righ in front of you and restrant in a room behind the bar");
		
		
		Room nHigh1 = roomManager.createRoom(N_HIGH_1);
		nHigh1.setDescription("The cobbelstone road runs north to south and is lined with dwellings.  On the east side of the street is a small shop with a sign over the door that reads, 'If the shoe fits.'");
		
		Container shelf = new Container("shelves", "A set of shelves.", 0, 0, "A crudely built set of shelves.");
		shelf.setVolumeCapacity(20);
		shelf.setContentsPrefix(" On the shelves you see ");
		Room shoeStore = roomManager.createStore(IF_THE_SHOE_FITS, new WearableFactory(), shelf);
		shoeStore.setDescription("A quiant store that has a variatey of shoes for sale.");
		
		Room nHigh2 = roomManager.createRoom(N_HIGH_2);
		nHigh2.setDescription("The cobbelstone road runs north to south and is lined with dwellings. A large wooden building with an inviting front poarch stands on the east side of the road. A placard hangs off one of the poarches post that reads 'Myrain's Ale House'.");
		
		
		Room nHigh3 = roomManager.createRoom(N_HIGH_3);
		nHigh3.setDescription("The cobbelstone road leads through the north gate.  It is crossed by a gravel road.  Off a post of the building to the south east hangs several signs.  North High Street points to the south.  Wall Street poitns to the east. Circle Way points to the west. ");
		
		
		Room cloesEntrance = roomManager.createRoom(CLOE_ENTRANCE);
		cloesEntrance.setDescription("There is an entrance to 'Cloe's Clothing'. North and west there are more shops.");
		
		
		WearableFactory wearableFactory = new WearableFactory();
		Container clothingRack = new Container("rack", "A clothing rack.", 0, 0, "A wooden clothing rack.");
		clothingRack.setVolumeCapacity(100);
		Room cloesClothing =   roomManager.createStore(CLOES_CLOTHING, wearableFactory, clothingRack).setDescription("You are in 'Cloe's Clothing'.");
		
		

		shelf = new Container("shelves", "A set of shelves.", 0, 0, "A crudely built set of shelves.");
		shelf.setVolumeCapacity(100);
		Container rack = new Container("rack", "A weapon rack.", 0, 0, "A woode weapon rack.");
		rack.setVolumeCapacity(100);
		Room wasWeaponandArmor =  roomManager.createStore(WAS_WEAPONS, new WeaponFactory(), shelf, rack).setDescription("You are in 'W & A's weapons and armor' an open door to the south leads to the armor store.");
		
		Room wasWeaponAndArmorEntrance = roomManager.createRoom(WAS_WEAPONS_ENTRANCE);
		wasWeaponAndArmorEntrance.setDescription("There is an entrance to 'W & A's Weapons and Armor'. South there are more shops.");
		
		Room wasArmorEntrance = roomManager.createRoom(WAS_ARMOR_ENTRANCE);
		wasArmorEntrance.setDescription("There is an entrance to 'W & A's Weapons and Armor'. ");
		
		Container table = new Container("wooden table", "A wooden table.", 0, 0, "A wooden table.");
		table.setVolumeCapacity(100);
		Room wasArmor = roomManager.createStore(WAS_ARMOR, new ArmorFactory(true), table);
		wasArmor.setDescription("An armor store.  An open door to the north leads to the weapon store.");
		
		Room plaza = roomManager.createRoom(CENTRAL_PLAZA);
		plaza.setDescription("You are in the city plaza. There are roads in all directions.");
		
		Room theTravelersTrinketsEntrance = roomManager.createRoom(TRAVELERS_TRINKETS_ENTRANCE);
		theTravelersTrinketsEntrance.setDescription("On this endge of the plaza stands a large two story");
		
		IItemFactory wearableContainerFactory = new WearableContainerFactory();
		Container threeLargeBins = new Container("bins", "Three large bins.", 0, 0, "Three large wooden bins.");
		threeLargeBins.setVolumeCapacity(300);
		threeLargeBins.setItemCountCapacity(4);
		Room theTravelersTrinkets = roomManager.createStore(TRAVELERS_TRINKETS, wearableContainerFactory, threeLargeBins);
		
		roomPathFactor.setCurrentRoom(theTravelersTrinketsEntrance.getId());
		roomPathFactor.setName("Broad Street");
		roomPathFactor.createNewRoom(Direction.EAST).setDescription("Large fancy homes line the street to the north and south.  To the west your hear the bustle of the central plaza.  ");
		roomPathFactor.createNewRoom(Direction.EAST).setDescription("Big huts lining the north and south of the streets, trees and bushes in between them.");
		roomPathFactor.createNewRoom(Direction.EAST).setDescription("Big huts lining the north and south of the streets, trees and bushes in between them. To the east your see the eastern market.");
		Room easternMarket = roomPathFactor.createNewRoom(Direction.EAST).setDescription("In the center of a large square stands a statue of the Great King Hyran.  All about the sqaure are small booths and make shift tents with people selling all kinds of wares.");
		
	
		TrainingRoom trainingRoom = new TrainingRoom();
		trainingRoom.setId(TRAINER);
		roomManager.addRoom(trainingRoom);
		
		roomManager.createRoom(WINDING_ROAD_TO_TEMPLE_1).setDescription("A winding road leads up the hill to the temple.");
		roomManager.createRoom(WINDING_ROAD_TO_TEMPLE_2).setDescription("A winding road leads up the hill to the temple.");
		roomManager.createRoom(WINDING_ROAD_TO_TEMPLE_3).setDescription("A winding road leads up the hill to the temple.");
		roomManager.createRoom(WINDING_ROAD_TO_TEMPLE_4).setDescription("You stand an the entrance to the temple of Elandor the great arches stand to the south.");
		Room templeElandor = roomManager.createRoom(TEMPLE_OF_ELANDOR).setDescription("The great temple of Elandor has a great arching roof, made of white marble.");
		templeElandor.addCommand(new HealCommand());
		
		roomManager.addBiDirectionalNavigation(N_HIGH_2, BiDirection.DOOR_DOOR, MYRIAMS_ALE_HOUSE);
		roomManager.addBiDirectionalNavigation(N_HIGH_2, BiDirection.NORTH_SOUTH, N_HIGH_1);
		roomManager.addBiDirectionalNavigation(N_HIGH_1, BiDirection.NORTH_SOUTH, CENTRAL_PLAZA);
		roomManager.addBiDirectionalNavigation(N_HIGH_1, BiDirection.DOOR_DOOR, IF_THE_SHOE_FITS);
		roomManager.addBiDirectionalNavigation(N_HIGH_3, BiDirection.NORTH_SOUTH, N_HIGH_2);
		roomManager.addBiDirectionalNavigation(CLOE_ENTRANCE, BiDirection.WEST_EAST, CENTRAL_PLAZA);
		roomManager.addBiDirectionalNavigation(CLOES_CLOTHING, BiDirection.DOOR_DOOR, CLOE_ENTRANCE);
		roomManager.addBiDirectionalNavigation(WAS_WEAPONS_ENTRANCE, BiDirection.SOUTHWEST_NORTHEAST, CENTRAL_PLAZA);
		roomManager.addBiDirectionalNavigation(WAS_WEAPONS, BiDirection.DOOR_DOOR, WAS_WEAPONS_ENTRANCE);
		roomManager.addBiDirectionalNavigation(WAS_WEAPONS_ENTRANCE, BiDirection.NORTH_SOUTH, WAS_ARMOR_ENTRANCE);
		roomManager.addBiDirectionalNavigation(WAS_WEAPONS_ENTRANCE, BiDirection.DOOR_DOOR, WAS_WEAPONS);
		roomManager.addBiDirectionalNavigation(WAS_ARMOR_ENTRANCE, BiDirection.DOOR_DOOR, WAS_ARMOR);
		roomManager.addBiDirectionalNavigation(WAS_WEAPONS, BiDirection.NORTH_SOUTH, WAS_ARMOR);
		roomManager.addBiDirectionalNavigation(TRAVELERS_TRINKETS_ENTRANCE, BiDirection.EAST_WEST, CENTRAL_PLAZA);
		roomManager.addBiDirectionalNavigation(TRAVELERS_TRINKETS, BiDirection.DOOR_DOOR, TRAVELERS_TRINKETS_ENTRANCE);
		roomManager.addBiDirectionalNavigation(CENTRAL_PLAZA, BiDirection.NORTH_SOUTH, WINDING_ROAD_TO_TEMPLE_1);
		roomManager.addBiDirectionalNavigation(WINDING_ROAD_TO_TEMPLE_1, BiDirection.DOOR_DOOR, TRAINER);
		roomManager.addBiDirectionalNavigation(WINDING_ROAD_TO_TEMPLE_1, BiDirection.NORTHEAST_SOUTHWEST, WINDING_ROAD_TO_TEMPLE_2);
		roomManager.addBiDirectionalNavigation(WINDING_ROAD_TO_TEMPLE_2, BiDirection.NORTHWEST_SOUTHEAST, WINDING_ROAD_TO_TEMPLE_3);
		roomManager.addBiDirectionalNavigation(WINDING_ROAD_TO_TEMPLE_3, BiDirection.NORTHEAST_SOUTHWEST, WINDING_ROAD_TO_TEMPLE_4);
		roomManager.addBiDirectionalNavigation(WINDING_ROAD_TO_TEMPLE_4, BiDirection.NORTH_SOUTH, TEMPLE_OF_ELANDOR);
		
		

		
		return plaza;
	}
}
