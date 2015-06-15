package org.adventure.commands.navigation;

public enum Direction {
	NORTH("N", "North", "Go North"), 
	SOUTH("S","South","Go South"), 
	EAST("E","East","Go East"), 
	WEST("W","West","Go West"),
	NORTHWEST("NW","Northwest","Go Northwest"),
	NORTHEAST("NE","Northeast","Go Northeast"),
	SOUTHWEST("SW","Southwest","Go Southwest"),
	SOUTHEAST("SE","Southeast","Go Southeast"),
	DOOR("Door", "Go Door");
	
	private String[] validValues;
	private Direction(String... validValues) {
		this.validValues = validValues;
	}
	public String[] getValidValues() {
		return validValues;
	}
	public String getLabel() {
		return this.validValues[1];
	}
	
}
