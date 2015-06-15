package org.adventure.items;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class QuantityItem extends Item {
	public static final String GOLD = "gold";
	private int amount;
	private float weightPerUnit;
	private float volumePerUnit;
	
	public QuantityItem() {
		super();
	}

	public QuantityItem(String name, int amount) {
		super();
		this.setName(name);
		this.amount = amount;
	}

	public boolean add(QuantityItem quantityItem) {
		if (getName().equals(quantityItem.getName())) {
		
		}
		return false;
	}
	
	public void add(int amount) {
		this.amount = this.amount + amount;
	}
	
	public int getAmount() {
		return amount;
	}

	public QuantityItem remove(int amount) {
		if (amount < this.amount) {
			QuantityItem q = new QuantityItem();
			q.setName(this.getName());
			q.amount = amount;
			this.amount = this.amount - amount;
			return q;		
		}
		return null;
	}
	
	@Override
	public int getVolume() {
		return (int)volumePerUnit * amount;
	}
	@Override
	public int getWeight() {
		return (int)weightPerUnit * amount;
	}
	
	
}
