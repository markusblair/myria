package org.adventure.items.food;

import org.adventure.items.IItem;
import org.adventure.items.IItemFactory;

public class FoodFactory implements IItemFactory {


	@Override
	public IItem createItem() {
		Food food =  new Food("food", "Food", 1, 1, 100);
		return food;
	}

}
