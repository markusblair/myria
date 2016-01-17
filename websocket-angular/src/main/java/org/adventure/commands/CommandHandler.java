package org.adventure.commands;
import java.util.List;

import org.adventure.character.PlayerCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommandHandler {
	
	@Autowired
	public PlayerCharacter playerCharacter;
	
	
	private List<Action> validCommands;
	
	@Autowired
	public void setValidCommands(List<Action> actions) {
		this.validCommands = actions;
	}
	
	public void processAction(Action action, Command command) {
		action.performAction(command, playerCharacter);
	}
	
	public CommandHandler() {
		super();
		
	}

	public void handle(String cmd) {
		try {
			Command command = constructCommand(cmd);
			if (playerCharacter.getPlayerPrompt()  != null) {
				playerCharacter.getPlayerPrompt().handleResponse(command, playerCharacter);
				playerCharacter.setPlayerPrompt(null);
			}
			else {
				Action action = getCommand(command);
				if (action != null) {
					processAction(action, command);
				}
				else {
					playerCharacter.sendMessage("Huh? I don't know what you meant by " + cmd);
				}			
			}
		} catch (Exception e) {
			throw new RuntimeException("Error handling command from " + this.playerCharacter.getName() + " : " + cmd, e);
		}
	}
	
	public Action getCommand(Command command) {
		 for (Action action : validCommands) {
			 if (action.matches(command)) {
				 return action;
			 }
		 }
		 List<Action> roomActions = playerCharacter.getCurrentRoom().getValidCommands(playerCharacter);
		 for (Action action : roomActions) {
			 if (action.matches(command)) {
				 return action;
			 }
		 }
		return null;
	}
	
	private Command constructCommand(String cmd) {
		 Command command = new Command(cmd);
		 
	      
	     return command;
	}
}
