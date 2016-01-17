package org.adventure.rooms;

import java.util.Timer;
import java.util.TimerTask;

import org.adventure.character.ICharacter;
import org.adventure.commands.AppraiseAction;
import org.adventure.commands.BuyAction;
import org.adventure.commands.CommandCondition;
import org.adventure.commands.SellAction;
import org.adventure.commands.TakeCommand;
import org.adventure.items.Container;
import org.adventure.items.IItem;
import org.adventure.items.IItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Store extends Room {
	Logger log = LoggerFactory.getLogger(Store.class);
	protected static Timer timer = new Timer();
	private final IItemFactory itemFactory;
	Container[] containers;
	MarketValuator marketValuator = new MarketValuator();
	public Store(String id, IItemFactory itemFactory, Container... containers) {
		setId(id);
		this.addCommand(new BuyAction());
		this.addCommand(new AppraiseAction(marketValuator));
		this.addCommand(new SellAction(marketValuator));
		this.itemFactory = itemFactory;
		this.containers = containers;
		for (Container container : containers) {
			container.setContentsVisible(true);
			container.addCommandCondition(TakeCommand.class, new CommandCondition() {
				@Override
				public boolean conditional(ICharacter character) {
					return false;
				}
			});
			this.addItem(container);
		}
		
		for (Container container : Store.this.containers) {
			addItemToStore(container);
		}
		if (this.itemFactory != null) {
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					for (Container container : Store.this.containers) {
						boolean adddedItem = addItemToStore(container);
						int cnt = 5;
						while (adddedItem && cnt-- > 0) {
							adddedItem = addItemToStore(container);
						}
					}
				}
			};
			timer.schedule(timerTask, 2000, 600000); //Every ten minutes try and add an item to each container.			
		}
	}
	private boolean addItemToStore(Container container) {
		IItem item = Store.this.itemFactory.createItem();
		if (container.canAddItem(item)) {
			container.addItem(item);
			item.addCommandCondition(TakeCommand.class, new SalePrice(marketValuator.determineValue(item), item.getDescription()));
			return true;
		}
		log.debug("Unable to add  to container.");
		return false;
	}
	
	
	
}
