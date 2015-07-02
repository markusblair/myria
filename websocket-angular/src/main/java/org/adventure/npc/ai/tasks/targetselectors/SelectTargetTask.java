package org.adventure.npc.ai.tasks.targetselectors;

import java.util.Collection;
import java.util.LinkedList;

import org.adventure.CharacterGroup;
import org.adventure.PlayerState;
import org.adventure.character.ICharacter;
import org.adventure.monster.Monster;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.BTTaskState;
import org.adventure.npc.ai.Task;
import org.adventure.random.RandomCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectTargetTask extends Task {
	Logger log = LoggerFactory.getLogger(SelectTargetTask.class);

	
	
	@Override
	public boolean checkConditions(BTContext context) {
		ICharacter target = ((ICharacter)context.getTaskContext(this).get(BTContext.TARGET));
		return target == null;
	}




	@Override
	public void doAction(BTContext context) {
		super.doAction(context);
		Monster monster = (Monster)context.getTaskContext(this).get(BTContext.MONSTER);
		
		log.debug(monster.getName() + " Selecting target.");
		Collection<ICharacter> characters = null;
		CharacterGroup characterGroup =  monster.getCurrentRoom().getCharacterGroup(monster);
		if (characterGroup != null) {
			characters = characterGroup.getCharacters();			
		} else {
			characters = monster.getCurrentRoom().getCharacters();			
		}
		ICharacter character = selectCharacterFromList(characters);
		if (character != null) {
			context.put(BTContext.TARGET, character);
			context.getTaskContext(this).setTaskState(BTTaskState.SUCESS);			
		}
		else {
			context.getTaskContext(this).setTaskState(BTTaskState.FAILURE);		
		}
	}
	
	public ICharacter selectCharacterFromList(Collection<ICharacter> characters) {
		
		if (characters != null) {
			Collection<ICharacter> targets = new LinkedList<ICharacter>();
			for (ICharacter iCharacter : characters) {
				if (iCharacter instanceof Monster) {
				}
				else if (PlayerState.DEAD.equals(iCharacter.getPlayerState()) == false) {
					targets.add(iCharacter);
				}
			}
			if (targets.size() > 0) {
				
				ICharacter character = new RandomCollection<>(targets).next();
				return character;
			}			
		}
		return null;
	}
}
