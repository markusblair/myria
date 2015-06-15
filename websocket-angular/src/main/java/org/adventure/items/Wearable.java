package org.adventure.items;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class Wearable extends Item implements IWearable {
	private WearableType wearableType;
	private String color;
	private String material;
	private String design; 
	private String adjective1;
	private String adjective2;
	
	
	public Wearable(WearableType wearableType) {
		this.wearableType = wearableType;
	}

	@Override
	public WearableType getWearableType() {
		return this.wearableType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDesign() {
		return design;
	}

	public void setDesign(String design) {
		this.design = design;
	}

	public String getAdjective1() {
		return adjective1;
	}

	public void setAdjective1(String adjective1) {
		this.adjective1 = adjective1;
	}

	public String getAdjective2() {
		return adjective2;
	}

	public void setAdjective2(String adjective2) {
		this.adjective2 = adjective2;
	}

	public void setWearableType(WearableType wearableType) {
		this.wearableType = wearableType;
	}

	@Override
	public String getName() {
		StringBuilder sb = new StringBuilder();
		if (this.getColor() != null) {
			sb.append(this.getColor());
			sb.append(" ");			
		}
		if (this.getAdjective1() != null) {
			sb.append(this.getAdjective1());
			sb.append(" ");			
		}
		if (this.getAdjective2() != null) {
			sb.append(this.getAdjective2());
			sb.append(" ");			
		}
		sb.append(this.getMaterial());
		sb.append(" ");
		sb.append(super.getName());
		return sb.toString();
	}


	
	
}
