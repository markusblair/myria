package org.adventure.commands;

import org.adventure.character.ICharacter;
import org.adventure.items.search.ItemSearchResult;
import org.springframework.stereotype.Component;

@Component
public class ExamineCommand extends ItemCommand {

	public ExamineCommand() {
		super();
		this.addCommandPattern("((look)|(examine)) <item>");
		this.addCommandPattern("((look)|(examine))");
	}

	@Override
	public void action(Command command, ICharacter character) {
		super.action(command, character);
		if (command.hasItem("<item>")) {
			ItemSearchResult itemSearchResult = getItem("<item>", character);
			if (itemSearchResult != null) {
				character.sendMessage(itemSearchResult.getItem().getLongDescription());					
			}
		}
		else {
			character.sendMessage(null);
			
		}
	}


	
	
}
