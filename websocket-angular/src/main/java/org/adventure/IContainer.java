package org.adventure;

import org.adventure.items.IItem;
import org.adventure.items.search.ItemSearchResult;

public interface IContainer {


	public abstract void addItem(IItem item);
	
	public abstract boolean canAddItem(IItem item);

	public abstract void removeItem(IItem item);
	
	public abstract ItemSearchResult getItem(String itemName);

	public abstract boolean isContentsVisible();
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract String getLongDescription();
}