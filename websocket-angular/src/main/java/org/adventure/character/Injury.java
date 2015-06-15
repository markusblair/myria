package org.adventure.character;

import javax.jdo.annotations.EmbeddedOnly;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class Injury {
	
	private String description;
	private int healthLossRate;
	private int severityLevel;
	
	public Injury(String description, int healthLossRate, int severityLevel) {
		super();
		this.description = description;
		this.healthLossRate = healthLossRate;
		this.severityLevel = severityLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHealthLossRate() {
		return healthLossRate;
	}

	public void setHealthLossRate(int healthLossRate) {
		this.healthLossRate = healthLossRate;
	}

	public int getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(int severityLevel) {
		this.severityLevel = severityLevel;
	}

}
