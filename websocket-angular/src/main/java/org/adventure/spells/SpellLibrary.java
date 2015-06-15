package org.adventure.spells;

import java.util.HashMap;

import org.adventure.spells.illusion.Daze;
import org.adventure.spells.restoration.Heal;
import org.springframework.stereotype.Component;

@Component
public class SpellLibrary {

	private HashMap<String, Spell> spells = new HashMap<String, Spell>();
	
	public SpellLibrary() {
		super();
		spells.put("daze", new Daze());
		spells.put("heal", new Heal());
		spells.put("speed", new Speed());
	}



	public Spell getSpell(String spell) {
		return spells.get(spell);
	}
}
