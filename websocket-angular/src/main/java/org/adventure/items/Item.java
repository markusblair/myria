package org.adventure.items;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.jdo.annotations.EmbeddedOnly;
import javax.persistence.Transient;

import org.adventure.character.ICharacter;
import org.adventure.commands.Action;
import org.adventure.commands.CommandCondition;

import com.fasterxml.jackson.annotation.JsonIgnore;


@EmbeddedOnly
public class Item implements IItem {
	@JsonIgnore
	private String name;
	private String description;
	@JsonIgnore
	private String longDescription;
	@JsonIgnore
	private int volume;
	@JsonIgnore
	private int weight;
	@Transient
	private Map<Class, CommandCondition> commandConditionMap = new HashMap<Class, CommandCondition>();
	
	public Item() {
		super();
	}

	public Item(String name, String description,
			int volume, int weight, String longDescription) {
		super();
		this.name = name;
		this.description = description;
		this.longDescription = longDescription;
		this.volume = volume;
		this.weight = weight;
	}

	public Item(String name, String description, int volume, int weight) {
		super();
		this.name = name;
		this.description = description;
		this.volume = volume;
		this.weight = weight;
	}

	/* (non-Javadoc)
	 * @see org.adventure.IItem#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public Item setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getDescription() {
		if (this.description == null) {
			return getName();
		}
		return description;
	}

	public Item setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String getLongDescription() {
		return longDescription;
	}

	public Item setLongDescription(String longDescription) {
		this.longDescription = longDescription;
		return this;
	}

	@Override
	public int getVolume() {
		return volume;
	}

	public Item setVolume(int volume) {
		this.volume = volume;
		return this;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	public Item setWeight(int weight) {
		this.weight = weight;
		return this;
	}
	
	public boolean commandAllowed(Action command, ICharacter character) {
		CommandCondition commandCondition = this.commandConditionMap.get(command.getClass());
		if (commandCondition != null) {
			return commandCondition.testCondition(character);			
		}
		return true;
	}

	public IItem addCommandCondition(Class command, CommandCondition commandCondition) {
		this.commandConditionMap.put(command, commandCondition);
		return this;
	}

	@Override
	public CommandCondition getCommandCondition(Class command) {
		return this.commandConditionMap.get(command);
	}

	@Override
	public void removeCommandCondition(Class command) {
		this.commandConditionMap.remove(command);
	}

	@Override
	public boolean is(String name) {
		String description = this.getName();
		StringTokenizer nameTokens = new StringTokenizer(name);
		String nameToken = "";
		if (description == null) {
			description = this.name;
		}
		while (nameTokens.hasMoreElements()) {
			nameToken = (String) nameTokens.nextElement();
			if (description == null || description.toLowerCase().contains(nameToken) == false) {
				return false;
			}
		}
		return description.toLowerCase().endsWith(nameToken);
	}

	
	
}
