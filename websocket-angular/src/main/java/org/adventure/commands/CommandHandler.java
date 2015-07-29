package org.adventure.commands;
import java.util.ArrayList;
import java.util.List;

import org.adventure.character.ICharacter;
import org.adventure.character.PlayerCharacter;
import org.adventure.commands.combat.AttackCommand;
import org.adventure.commands.combat.EngageCommand;
import org.adventure.commands.combat.LoadCommand;
import org.adventure.commands.combat.RetreatCommand;
import org.adventure.spells.CastSpellAction;
import org.adventure.spells.PrepareSpellAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommandHandler {
	
	@Autowired
	public PlayerCharacter playerCharacter;
	
	@Autowired
	private ReviveCommand reviveCommand; 
	
	@Autowired
	private CastSpellAction castSpellAction;
	
	@Autowired
	private PrepareSpellAction prepareSpellAction;
	
	public TakeCommand takeCommand = new TakeCommand();
	public DropCommand dropCommand = new DropCommand();
	ExamineCommand examineCommand= new ExamineCommand();
	MeCommand meCommand = new MeCommand();
	DropCommand dropCommand2 = new DropCommand();
	WearCommand wearCommand = new WearCommand();
	PutCommand putCommand = new PutCommand();
	RemoveCommand removeCommand = new RemoveCommand();
	SayCommand sayCommand = new SayCommand();
	DragCommand dragCommand = new DragCommand();
	SwapCommand swapCommand = new SwapCommand();
	
	LayDownCommand sitCommand = new LayDownCommand();
	StandUpCommand standUpCommand = new StandUpCommand();
	SitDownCommand layDownCommand = new SitDownCommand();
	KnellCommand knellCommand = new KnellCommand();
	
	private List<Action> validCommands;
	
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
		validCommands = new ArrayList<Action>();
		ICharacter playerCharacter = this.playerCharacter;
		validCommands.add(sayCommand);
		validCommands.add(examineCommand);
		validCommands.add(examineCommand);
		validCommands.add(takeCommand);
		validCommands.add(meCommand);
		validCommands.add(dropCommand);
		validCommands.add(wearCommand);
		validCommands.add(putCommand);
		validCommands.add(removeCommand);
		validCommands.add(sayCommand);			
		validCommands.add(new AttackCommand());
		validCommands.add(new LoadCommand());
		validCommands.add(new EatCommand());
		validCommands.add(castSpellAction);
		validCommands.add(dragCommand);
		validCommands.add(swapCommand);
		validCommands.add(sitCommand);
		validCommands.add(standUpCommand);
		validCommands.add(knellCommand);
		validCommands.add(layDownCommand);
		validCommands.add(reviveCommand);
		validCommands.add(prepareSpellAction);
		validCommands.add(new EngageCommand());
		validCommands.add(new RetreatCommand());
		validCommands.addAll(playerCharacter.getCurrentRoom().getValidCommands(playerCharacter));
		 for (Action action : validCommands) {
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
