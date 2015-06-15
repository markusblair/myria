package org.adventure.character.stats;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class StatReference implements IStatReference {

	private int stat;

	public StatReference() {
		super();
	}

	public StatReference(int stat) {
		super();
		this.stat = stat;
	}

	@Override
	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}
	
	
}
