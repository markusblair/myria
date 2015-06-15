package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.IItem;

public class DragHandle implements IItem {
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLongDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean is(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commandAllowed(Action command, ICharacter character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IItem addCommandCondition(Class command, CommandCondition commandCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandCondition getCommandCondition(Class command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCommandCondition(Class command) {
		// TODO Auto-generated method stub
		
	}

}
