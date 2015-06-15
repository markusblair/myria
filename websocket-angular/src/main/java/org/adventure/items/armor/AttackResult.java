package org.adventure.items.armor;

import org.adventure.random.SkillCheckResult;

public class AttackResult {
	SkillCheckResult hitCheck;
	SkillCheckResult shieldCheck;
	DamageCalculation damageCalculation;
	String attackType;
	String attacker;
	String defender;
	String bodyPart;
	
	public AttackResult(SkillCheckResult hitCheck, String attacker, String defender) {
		super();
		this.hitCheck = hitCheck;
		this.attacker = attacker;
		this.defender = defender;
	}
	public SkillCheckResult getHitCheck() {
		return hitCheck;
	}
	public void setHitCheck(SkillCheckResult hitCheck) {
		this.hitCheck = hitCheck;
	}
	public SkillCheckResult getShieldCheck() {
		return shieldCheck;
	}
	public void setShieldCheck(SkillCheckResult shieldCheck) {
		this.shieldCheck = shieldCheck;
	}
	public DamageCalculation getDamageCalculation() {
		return damageCalculation;
	}
	public void setDamageCalculation(DamageCalculation damageCalculation) {
		this.damageCalculation = damageCalculation;
	}
	public String getAttacker() {
		return attacker;
	}
	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}
	public String getDefender() {
		return defender;
	}
	public void setDefender(String defender) {
		this.defender = defender;
	}
	public String getAttackType() {
		return attackType;
	}
	public void setAttackType(String attackType) {
		this.attackType = attackType;
	}
	public String getBodyPartName() {
		return bodyPart;
	}
	public void setBodyPartName(String bodyPart) {
		this.bodyPart = bodyPart;
	}
	
	
}
