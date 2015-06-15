package org.adventure.items;

import org.adventure.character.ICharacter;
import org.adventure.commands.Action;
import org.adventure.commands.CommandCondition;

public interface IItem {

	/**
	 * The name of the item should return a descriptive name that will be used in selecting an item.  Red Cotton Tunic or Iron Handaxe.
	 * @return
	 */
	public abstract String getName();

	/**
	 * The description is what will be shown to the user
	 * @return
	 */
	public abstract String getDescription();

	public abstract String getLongDescription();

	public abstract int getVolume();

	public abstract int getWeight();
	
	public abstract boolean is(String name);
	
	public boolean commandAllowed(Action command, ICharacter character);
	
	public IItem addCommandCondition(Class command, CommandCondition commandCondition);
	
	public CommandCondition getCommandCondition(Class command);
	
	public void removeCommandCondition(Class command);
}