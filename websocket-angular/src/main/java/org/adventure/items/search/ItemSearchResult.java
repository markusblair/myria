package org.adventure.items.search;

import org.adventure.IContainer;
import org.adventure.items.IItem;

public class ItemSearchResult {
	private IItem item;
	private IContainer container;
	
	public ItemSearchResult() {
		super();
	}
	public ItemSearchResult(IItem item, IContainer container) {
		super();
		this.item = item;
		this.container = container;
	}
	public IItem getItem() {
		return item;
	}
	public IContainer getContainer() {
		return container;
	}
	public void setItem(IItem item) {
		this.item = item;
	}
	public void setContainer(IContainer container) {
		this.container = container;
	}
	
	
}
