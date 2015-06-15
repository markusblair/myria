package org.adventure.commands;
import java.util.ArrayList;
import java.util.List;

import org.adventure.character.ICharacter;


public abstract class Action {
		
		private List<String> commandPatters = new ArrayList<String>();
		public abstract void action(Command command, ICharacter character);

		public final void performAction(Command command, ICharacter character) {
			if (isAllowed(character)) {
				action(command, character);	
				int duration = getExecutionTime(character);
				if (duration > 0) {
					character.setBusyFor(duration);
				}
			}
		}
		
		public Action addCommandPattern(String... commandPattern) {
			for (String string : commandPattern) {
				this.commandPatters.add(string.toLowerCase());				
			}
			return this;
		}
		
		public boolean matches(Command command) {
			for (String commandPattern : this.commandPatters) {
				if (command.matches(commandPattern))
					return true;
			}
			return false;
		}
		
		public boolean isAllowed(ICharacter character) {
			boolean result = true;
			int duration = getExecutionTime(character);
			boolean isBusy = character.isBusy();
			if (duration > 0 && isBusy) {
				result = false;
				character.sendMessage(new StringBuilder("Wait ").append(character.getBusyFor()).append(" seconds.").toString());
			}
			return result;
		};
		
		public int getExecutionTime(ICharacter character) {
			return 0;
		}
		
}
