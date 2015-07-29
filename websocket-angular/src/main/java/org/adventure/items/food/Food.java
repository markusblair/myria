package org.adventure.items.food;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.ICharacter;
import org.adventure.items.IConsumable;
import org.adventure.items.Item;

@EmbeddedOnly
public class Food extends Item implements IConsumable {

	private int energy = 0;
	
	public Food() {
	}

	public Food(String name, String description, int volume, int weight, String longDescription, int energy) {
		super(name, description, volume, weight, longDescription);
		this.energy = energy;
	}

	public Food(String name, String description, int volume, int weight, int energy) {
		super(name, description, volume, weight);
		this.energy = energy;
	}

	
	
	public int getEnergy() {
		return energy;
	}

	@Override
	public void consume(ICharacter character) {
		character.addEnergyReserve(this.energy);
	}

}
