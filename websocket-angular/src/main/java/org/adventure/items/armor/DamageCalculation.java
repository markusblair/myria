package org.adventure.items.armor;

import java.util.LinkedList;
import java.util.List;

import org.adventure.character.BodyPart;
import org.adventure.items.weapons.DamageType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DamageCalculation {
	@JsonProperty
	List<DamageAmount> damageAmounts = new LinkedList<DamageCalculation.DamageAmount>();
	int bodyPartHealth = 0;
	int totalDamage = 0;
	BodyPart bodyPart;
	String description;
	
	public DamageCalculation(BodyPart bodyPart) {
		super();
		this.bodyPart = bodyPart;
		this.bodyPartHealth = bodyPart.getHealth();
	}
	
	public int getTotalDamage() {
		int damage = this.totalDamage;
		if (damage > this.bodyPartHealth) {
			damage = this.bodyPartHealth;
		}
		return damage;
	}
	
	public void addDamage(DamageType damageType, int amount, int damageReduction, int reamainingArmorHealth) {
		damageAmounts.add(new DamageAmount(damageType, amount, damageReduction, reamainingArmorHealth));
		this.totalDamage = this.totalDamage + amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	class DamageAmount {
		DamageType damageType;
		int amount;
		int damageReduction;
		int reamainingArmorHealth;
		
		public DamageAmount(DamageType damageType, int amount, int damageReduction, int reamainingArmorHealth) {
			super();
			this.damageType = damageType;
			this.amount = amount;
			this.damageReduction = damageReduction;
			this.reamainingArmorHealth = reamainingArmorHealth;
		}

		public DamageType getDamageType() {
			return damageType;
		}

		public int getAmount() {
			return amount;
		}

		public int getDamageReduction() {
			return damageReduction;
		}

		public int getReamainingArmorHealth() {
			return reamainingArmorHealth;
		}
		
	}
}
