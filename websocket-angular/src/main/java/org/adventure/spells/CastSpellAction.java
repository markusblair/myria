package org.adventure.spells;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.CharacterCommand;
import org.adventure.items.IItem;
import org.springframework.stereotype.Component;

@Component
public class CastSpellAction extends CharacterCommand {

	
	public CastSpellAction() {
		super();
		this.addCommandPattern("cast at <target>");
		this.addCommandPattern("cast");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		ICharacter target = null;
		if (command.hasItem("<target>")) {
			target = getCharacter("<target>", character);			
		}
		IItem item = character.getLeftHand();
		if (!(item instanceof Spell)) {
			item = character.getRightHand();
		}
		if (item != null && item instanceof Spell) {
			Spell spell = (Spell)item;
			if (target == null) {
				spell.cast(character, character);
			} else {
				spell.cast(character, target);					
			}
			character.removeItem(spell);
		}
		else {
			character.sendMessage("What spell?");
		}
	}

}
