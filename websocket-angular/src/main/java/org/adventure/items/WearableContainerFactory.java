package org.adventure.items;

import org.adventure.random.RandomCollection;

public class WearableContainerFactory implements IItemFactory {
	private static RandomCollection<WearableType> wearableType =  new RandomCollection<WearableType>()
			.add(1, WearableType.BACK_PACK);
//			.add(1, WearableType.SHOULDER)
//			.add(1, WearableType.BELT);

	@Override
	public IItem createItem() {
		WearableType type = wearableType.next();
		
		WearableContainer wearableContainer = new WearableContainer(type, 100);
		wearableContainer.setName("Backpack");
		wearableContainer.setDescription("A leather backpack.");
		wearableContainer.setVolume(2);
		return wearableContainer;
	}

}
