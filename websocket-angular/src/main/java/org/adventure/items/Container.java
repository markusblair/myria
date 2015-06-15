package org.adventure.items;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.IContainer;
import org.adventure.items.search.ItemSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EmbeddedOnly
public class Container extends Item implements IContainer {
	private static Logger log = LoggerFactory.getLogger(Container.class);
	@JsonIgnore
	private int volumeCapacity;
	@JsonIgnore
	private int itemCountCapacity = -1;
	@JsonIgnore
	private WearableType containerType;
	private List<IItem> items = new ArrayList<IItem>();
	@JsonIgnore
	private boolean contentsVisible = false;
	@JsonIgnore
	private String contentsPrefix;
	
	public Container() {
		super();
	}

	public Container(String name, String description, int volume, int weight,
			String longDescription) {
		super(name, description, volume, weight, longDescription);
	}

	public Container(String name, String description, int volume, int weight, int volumeCapacity, WearableType contrainerType) {
		super(name, description, volume, weight);
	}

	@Override
	public String getDescription() {
		return super.getDescription();
	}


	@Override
	public String getLongDescription() {
			StringBuilder sb = new StringBuilder();
			if (super.getDescription() != null) {
				sb.append(super.getDescription());
			}
			if (getContentsPrefix() != null) {
				sb.append(getContentsPrefix());
			}
			
			for (IItem item : items) {
				sb.append(item.getDescription());
				sb.append(",");
			}
			return sb.toString();
	}

	public int getVolumeCapacity() {
		return volumeCapacity;
	}

	public IContainer setVolumeCapacity(int volumeCapacity) {
		this.volumeCapacity = volumeCapacity;
		return this;
	}

	public int getItemCountCapacity() {
		return itemCountCapacity;
	}

	public IContainer setItemCountCapacity(int itemCountCapacity) {
		this.itemCountCapacity = itemCountCapacity;
		return this;
	}

	public WearableType getContainerType() {
		return containerType;
	}

	public IContainer setContainerType(WearableType containerType) {
		this.containerType = containerType;
		return this;
	}
	
	@Override
	public boolean canAddItem(IItem item) {
		log.debug(new StringBuilder("Item volume:").append(item.getVolume())
				.append(" container capacity:").append(this.getVolumeCapacity())
				.append(" volume:").append(this.getVolume()).toString());
		
		boolean capacityConstraintMet = item.getVolume() <= this.getVolumeCapacity() - this.getVolume();
		boolean countCapacityMet = (this.getItemCountCapacity() == -1 || this.getItemCountCapacity() > this.items.size());
		return capacityConstraintMet && countCapacityMet;
	}

	/* (non-Javadoc)
	 * @see org.adventure.ICOntainer#addItem(org.adventure.Item)
	 */
	@Override
	public void addItem(IItem item) {
		this.items.add(item);	
	}

	@Override
	public void removeItem(IItem item) {
		this.items.remove(item);
	}

	@Override
	public int getVolume() {
		int theContainersEmptyVolume=  super.getVolume();
		int volume = theContainersEmptyVolume;
		for (IItem item : this.items) {
			volume = volume + item.getVolume();
		}
		
		return volume;
	}

	@Override
	public int getWeight() {
		int theContainersweight =  super.getWeight();
		int weight = theContainersweight;
		for (IItem item : this.items) {
			weight = weight + item.getWeight();
		}
		
		return weight;
	}
	
	public boolean isContentsVisible() {
		return contentsVisible;
	}

	public IContainer setContentsVisible(boolean contentsVisible) {
		this.contentsVisible = contentsVisible;
		return this;
	}

	public String getContentsPrefix() {
		return contentsPrefix;
	}

	public IContainer setContentsPrefix(String contentsPrefix) {
		this.contentsPrefix = contentsPrefix;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.adventure.ICOntainer#getItem(java.lang.String)
	 */
	@Override
	public ItemSearchResult getItem(String itemName) {
		int index = 0;
		if (itemName.toLowerCase().startsWith("other ")) {
			itemName = itemName.substring(3, itemName.length());
			index =  1;
		} 
		else if (itemName.toLowerCase().startsWith("3rd ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  2;
		} 
		else if (itemName.toLowerCase().startsWith("4th ")) {
			itemName = itemName.substring(4, itemName.length());
			index =  3;
		} 
		for (IItem item : this.items) {
			if (item.is(itemName)) {
				if (--index < 0) {
					return new ItemSearchResult(item, this);
				}
			}
		}
		return null;
	}
	
	public List<IItem> getItems() {
		return this.items;
	}
}
