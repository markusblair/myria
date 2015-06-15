package org.adventure.items;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.IContainer;

@EmbeddedOnly
public class WearableContainer extends Container implements IContainer, IWearable {
	private WearableType wearableType;
	
	public WearableContainer() {
		super();
	}

	public WearableContainer(WearableType wearableType, int volumeCapacity) {
		super();
		setWearableType(wearableType);
		setVolumeCapacity(volumeCapacity);
	}

	public WearableType getWearableType() {
		return wearableType;
	}

	public void setWearableType(WearableType wearableType) {
		this.wearableType = wearableType;
	}

}
