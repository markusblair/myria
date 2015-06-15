package org.adventure.random;

import java.util.concurrent.ThreadLocalRandom;

import javax.jdo.annotations.EmbeddedOnly;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class Dice implements IRandom {
	int deviation;
	int mean;


	public Dice() {
		super();
	}

	public Dice(int deviation, int mean) {
		super();
		this.deviation = deviation;
		this.mean = mean;
	}

	@JsonIgnore
	@Override
	public int getValue() {
		int result   = (int) (ThreadLocalRandom.current().nextGaussian() * deviation + mean);
		result = Math.abs(result);
		return result;
	}

	public int getDeviation() {
		return deviation;
	}

	public void setDeviation(int deviation) {
		this.deviation = deviation;
	}

	public int getMean() {
		return mean;
	}

	public void setMean(int mean) {
		this.mean = mean;
	}

}
