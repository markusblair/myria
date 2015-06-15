package org.adventure.items.armor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.BodyPart;
import org.adventure.character.BodyPartType;
import org.adventure.items.Item;
import org.adventure.items.weapons.DamageType;
import org.adventure.random.Dice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class Armor extends Item {
	private static Logger log = LoggerFactory.getLogger(Armor.class);
	@JsonIgnore
	Map<BodyPartType, Integer> healthMap = new HashMap<BodyPartType, Integer>();
	@JsonIgnore
	Map<DamageType, Dice> protection = new HashMap<DamageType, Dice>();
	@JsonIgnore
	Map<DamageType, Double> deteriorationRate = new HashMap<DamageType, Double>();
	
	@JsonIgnore
	public Set<BodyPartType> getBodyPartTypes() {
		return this.healthMap.keySet();
	}
	public Integer getArmorHealth(BodyPartType bodyPartType) {
		return healthMap.get(bodyPartType);
	}

	public void setArmorHealth(BodyPartType bodyPartType, Integer health) {
		this.healthMap.put(bodyPartType, health);
	}

	public Map<DamageType, Dice> getProtection() {
		return protection;
	}

	public void addProtection(DamageType damageType, Dice protection) {
		this.protection.put(damageType, protection);
	}

	public void addProtection(DamageType damageType, int deviation, int mean) {
		this.protection.put(damageType, new Dice(deviation, mean));
	}
	
	public Map<DamageType, Double> getDeteriorationRate() {
		return deteriorationRate;
	}

	public void setDeteriorationRate(DamageType damageType, Double deteriorationRate) {
		this.deteriorationRate.put(damageType, deteriorationRate);
	}

	public DamageCalculation calculateDamage(Map<DamageType, Integer> damages, BodyPart bodyPart) {
		DamageCalculation damageCalculation = new DamageCalculation(bodyPart);
		Set<DamageType> dTypes = damages.keySet();
		for (DamageType dType : dTypes) {
			int rawDamage = damages.get(dType);
			if (protection.containsKey(dType)) {
				BodyPartType bodyPartType = bodyPart.getBodyPartType();
				int damageReduction = protection.get(dType).getValue();
				Integer armorHealth = this.getArmorHealth(bodyPartType);
				if (rawDamage < damageReduction) {
					damageReduction = rawDamage;
				}
				if (damageReduction > armorHealth) {
					damageReduction = armorHealth;
				}
				int armorDamageAmount = damageArmor(dType, damageReduction);
				int reamainingArmorHealth = armorHealth - armorDamageAmount;
				setArmorHealth(bodyPartType, reamainingArmorHealth);
				int adjDamage = rawDamage - damageReduction;
				if (adjDamage > bodyPart.getHealth()) {
					adjDamage = bodyPart.getHealth();
				}
				log.debug(" DamageType=" + dType + ", Body Part=" + bodyPart.getName() + ", Max="+ bodyPart.getMaxHealth() + ", Health="+ bodyPart.getHealth()+ " Damage Raw= "+ rawDamage  + ", Adjusted=" + adjDamage + ", Armor Absorbs=" + damageReduction + ", Remaining Armor=" + reamainingArmorHealth);
				damageCalculation.addDamage(dType, rawDamage, damageReduction, reamainingArmorHealth);
			}
			else {
				int adjDamage = rawDamage;
				if (rawDamage > bodyPart.getHealth()) {
					adjDamage = bodyPart.getHealth();
				}
				log.debug(" DamageType=" + dType + ", Body Part=" + bodyPart.getName() + ", Max="+ bodyPart.getMaxHealth() + ", Health="+ bodyPart.getHealth()+ " Damage Raw= "+ rawDamage  + ", Adjusted=" + adjDamage);
				damageCalculation.addDamage(dType, rawDamage, 0, 0);
			}
		}

		// Need to set the injury based on  the amount of health left.
		return damageCalculation;
	}
	
	private int damageArmor(DamageType dType, int amount) {
		Double rate = 0d;
		if (this.deteriorationRate.containsKey(dType)) {
			rate = this.deteriorationRate.get(dType);
		}
		return (int)(amount * rate);
	}
}
