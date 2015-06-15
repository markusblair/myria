package org.adventure;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.adventure.character.DataMessage;
import org.adventure.character.ICharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CharacterGroup  {

	private static Logger log = LoggerFactory.getLogger(CharacterGroup.class);
	private ConcurrentHashMap<String, ICharacter> characters = new ConcurrentHashMap<String, ICharacter>();

	@JsonIgnore
	public Collection<ICharacter> getCharacters() {
		return characters.values();
	}

	public Map<String, ICharacter> getCharacterMap() {
		return characters;
	}
	
	public void addCharacter(ICharacter character) {
		if (this.characters.containsKey(character.getId()) == false) {
			this.characters.put(character.getId(), character);
		}
		else {
			this.removeCharacter(character);
		}
	}
	
	
	public void removeCharacter(ICharacter character) {
		ICharacter removed = this.characters.remove(character.getId());
		if (removed != null) {
			DataMessage dataMessage = new DataMessage();
			dataMessage.removeCharacter(character);
		}
		else {
			log.debug("Unable to find character in group to remove. cId=%1", character.getId());
		}
	}
	
	public ICharacter getCharacter(String name) {
		Collection<ICharacter> chrts  = this.characters.values();
		for (ICharacter character : chrts) {
			if (character.getName().toLowerCase().startsWith(name)) {
				return character;
			}
		}
		return null;
	}
}
