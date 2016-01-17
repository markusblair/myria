package org.adventure;

import java.util.ArrayList;
import java.util.Collection;

import org.adventure.character.ICharacter;

public class CharacterGroups {
//	private static Logger log = LoggerFactory.getLogger(CharacterGroup.class);
	private Collection<CharacterGroup> characterGroups = new ArrayList<CharacterGroup>();
	
	public CharacterGroup getCharacterGroup(ICharacter character) {
		for (CharacterGroup characterGroup : characterGroups) {
			if (characterGroup.getCharacterMap().containsKey(character.getId())) {
				return characterGroup;
			}
		}
		return null;
	}
	
	/**
	 * Join an existing character group or create a new one.
	 * 
	 * @param character
	 * @param charactersGroupToJoin
	 */
	public void joinCharacterGroup(ICharacter character, ICharacter charactersGroupToJoin) {
		CharacterGroup characterGroup = getCharacterGroup(charactersGroupToJoin);
		if (characterGroup == null) {
			characterGroup = new CharacterGroup();
			characterGroup.addCharacter(charactersGroupToJoin);
			characterGroups.add(characterGroup);
		}
		characterGroup.addCharacter(character);
	}
	
	public boolean isInSameGroup(ICharacter character1, ICharacter character2) {
		CharacterGroup characterGroup = getCharacterGroup(character1);
		if (characterGroup != null) {
			return characterGroup.getCharacterMap().containsKey(character2.getId());
		}
		return false;
	}
	
	public void removeFromGroup(ICharacter character) {
		CharacterGroup characterGroup = getCharacterGroup(character);
		if (characterGroup != null) {
			characterGroup.removeCharacter(character);
			if (characterGroup.getCharacters().size() == 1) {
				characterGroups.remove(characterGroup);
			}
		}
	}
	
}
