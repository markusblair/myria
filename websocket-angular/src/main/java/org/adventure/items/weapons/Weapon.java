package org.adventure.items.weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.items.Item;
import org.adventure.random.Dice;
import org.adventure.random.SkillType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class Weapon extends Item {
	@JsonIgnore
	SkillType weaponType;
	@JsonIgnore
	Map<String, Map<DamageType, Dice>> attacks = new HashMap<String, Map<DamageType, Dice>>();
	@JsonIgnore
	List<String> attackTypes = new ArrayList<String>();
	@JsonIgnore
	int baseAttackRate = 10;
	int criticalModifier = 5;
	
	public SkillType getWeaponType() {
		return weaponType;
	}
	public Weapon setWeaponType(SkillType weaponType) {
		this.weaponType = weaponType;
		return this;
	}
	public int getBaseAttackRate() {
		return baseAttackRate;
	}
	public void setBaseAttackRate(int baseAttackRate) {
		this.baseAttackRate = baseAttackRate;
	}
	@JsonIgnore
	public String getAttackType() {
		int index = 0;
		if (getAttackTypes().size() > 1) {
			index = ThreadLocalRandom.current().nextInt(getAttackTypes().size());
		}
		String attackType = getAttackTypes().get(index);
		return attackType;
	}
	
	
	public Map<DamageType, Integer> getDamages(String attackType) {
		Map<DamageType, Integer> damages = new HashMap<DamageType, Integer>();
		Set<DamageType> dTypes = getDamageMatrix(attackType).keySet();
		for (DamageType key : dTypes) {
			damages.put(key, getDamageMatrix(attackType).get(key).getValue());
		}
		return damages;
	}
	
	public Map<DamageType, Integer> getCriticalDamages(String attackType) {
		Map<DamageType, Integer> damages = new HashMap<DamageType, Integer>();
		Set<DamageType> dTypes = getDamageMatrix(attackType).keySet();
		for (DamageType key : dTypes) {
			Integer damage = getDamageMatrix(attackType).get(key).getValue() * this.criticalModifier;
			damages.put(key, damage);
		}
		return damages;
	}
	
	public Weapon addDamage(String attackType, DamageType damageType, Dice random) {
		getDamageMatrix(attackType).put(damageType, random);
		return this;
	}
	
	public Weapon addDamage(String attackType, DamageType damageType, int deviation, int mean) {
		getDamageMatrix(attackType).put(damageType, new Dice(deviation, mean));
		return this;
	}
	private Map<DamageType, Dice> getDamageMatrix(String attackType) {
		Map<DamageType, Dice> matrix = attacks.get(attackType);
		if (matrix == null) {
			matrix = new HashMap<DamageType, Dice>();
			attacks.put(attackType, matrix);
			attackTypes.add(attackType);
		}
		return matrix;
	}
	
	public List<String> getAttackTypes() {
		return attackTypes;
	}
	
	/**
	 * The average damage per minute of the strongest attack.
	 * @return
	 */
	public int maxMeanDamageAttack() {
		int maxTotalMean = 0;
		for (String attackType : attackTypes) {
			int totalMean = 0;
			for (Dice dice : attacks.get(attackType).values()) {
				totalMean = totalMean + dice.getMean();
			}
			if (maxTotalMean < totalMean) {
				maxTotalMean = totalMean;
			}
		}
		
		return maxTotalMean;
	}
	
	public boolean isLoaded() {
		return true;
	}
}
