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
					character.addBusyFor(duration);
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
				int busyFor = character.getBusyFor();
				float energyLoss = (float)busyFor * 10f / (float)character.getStamina();
				int intEnergyLoss = 1 + (int)energyLoss;
				if (intEnergyLoss > character.getEnergy()) {
					result = false;
					character.sendMessage(new StringBuilder("Wait ").append(character.getBusyFor()).append(" seconds.").toString());
				}
				else {
					System.out.println("busyFor:" + busyFor + " energyLoss:" + intEnergyLoss);
					character.setEnergy(character.getEnergy() - intEnergyLoss);
					character.setBusyFor(busyFor/2);
				}
			}
			return result;
		};
		
		public int getExecutionTime(ICharacter character) {
			return 0;
		}
		
}
