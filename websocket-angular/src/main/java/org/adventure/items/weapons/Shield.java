package org.adventure.items.weapons;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.items.Wearable;
import org.adventure.items.WearableType;

@EmbeddedOnly
public class Shield extends Wearable {

	public Shield() {
		super(WearableType.SHOULDER);
	}
 
}
