package org.adventure.character;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BodyPartType {
	ARM("Arm"),
	HEAD("Head"),
	NECK("Neck"),
	LEG("Leg"),
	HAND("Hand"),
	FOOT("Foot"),
	TORSO("Torso"),
	BACK("Back");
	
	private String value;

	private BodyPartType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
