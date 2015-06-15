package org.adventure.commands;

import org.adventure.items.IItem;

public interface IItemLocator {
	IItem getItem(String ItemName);
}
