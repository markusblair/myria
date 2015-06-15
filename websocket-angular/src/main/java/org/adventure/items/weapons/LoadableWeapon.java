package org.adventure.items.weapons;

import javax.jdo.annotations.EmbeddedOnly;

@EmbeddedOnly
public class LoadableWeapon extends Weapon {

	private boolean loaded;
	private int baseLoadTime;
	
	public LoadableWeapon() {
	}

	@Override
	public boolean isLoaded() {
		return this.loaded;
	}

	public int getBaseLoadTime() {
		return baseLoadTime;
	}

	public void setBaseLoadTime(int baseLoadTime) {
		this.baseLoadTime = baseLoadTime;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	
	
}
