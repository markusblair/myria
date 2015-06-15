package org.adventure.spells;

import org.adventure.character.ICharacter;
import org.adventure.commands.Command;
import org.adventure.commands.combat.CharacterCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PrepareSpellAction extends CharacterCommand {
	
	@Autowired
	private SpellLibrary spellLibrary;

	public PrepareSpellAction() {
		this.addCommandPattern("prepare <spell>");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		
		if (character.getFreeHands() > 0) {
			
			String spellName = command.getItem("<spell>");
			Spell spell = spellLibrary.getSpell(spellName);
			if (character.useMana(spell.getManaRequried())) {
				character.addItem(spell);
				character.sendMessage("You prepare a spell.");
			}
			else {
				character.sendMessage("You dont' have enough mana for that.");
			}
		}
		else {
			character.sendMessage("You need a free hand for that.");
		}
	}

	
}
