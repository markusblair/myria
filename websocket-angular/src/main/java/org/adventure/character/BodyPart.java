package org.adventure.character;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.stats.IStatReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class BodyPart {
	@JsonIgnore
	private BodyPartType bodyPartType;
	private String name;
	private float percentHealth;
	private int health = 100;
	private IStatReference maxCharacterHealth;
	// Some way to store injuries.
	@JsonIgnore
	private Injury injury;
	
	
	public BodyPart() {
		super();
	}

	public BodyPart(BodyPartType bodyPartType, String name, float percentHealth, IStatReference maxCharacterHealth) {
		super();
		this.bodyPartType = bodyPartType;
		this.name = name;
		this.percentHealth = percentHealth;
		this.maxCharacterHealth = maxCharacterHealth;
		this.health = getMaxHealth();
	}

	@JsonIgnore
	public BodyPartType getBodyPartType() {
		return bodyPartType;
	}

	public void setBodyPartType(BodyPartType bodyPartType) {
		this.bodyPartType = bodyPartType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void addDamage(int damage) {
		this.health = health - damage;
	}

	public int getMaxHealth() {
		return Math.round(this.maxCharacterHealth.getStat() * this.percentHealth);
	}
	
	public void setHealth(int health) {
		if (health > getMaxHealth()) {
			this.health = getMaxHealth();
		}
		this.health = health;
	}

	public Injury getInjury() {
		return injury;
	}

	public void setInjury(Injury injury) {
		this.injury = injury;
	}
	
	public int getPerecentHealth(int amount) {
		return amount * 100 / this.getMaxHealth();
	}
	
	public interface ProcessInjury {
		public abstract void processInjury(Injury injury);
	}
}
