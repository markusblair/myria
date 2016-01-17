package org.adventure.monster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

import javax.persistence.Transient;

import org.adventure.character.BodyPart;
import org.adventure.character.BodyPartType;
import org.adventure.character.Character;
import org.adventure.character.CharacterData;
import org.adventure.character.CharacterDataEffectsProxy;
import org.adventure.character.ICharacter;
import org.adventure.character.IWebSocketDataService;
import org.adventure.character.PlayerState;
import org.adventure.character.stats.StatReference;
import org.adventure.messaging.DataMessage;
import org.adventure.monster.ai.IAiChainManager;
import org.adventure.monster.ai.MonsterAiManager;
import org.adventure.npc.ai.BTContext;
import org.adventure.npc.ai.Task;
import org.adventure.random.RandomCollection;
import org.adventure.rooms.Room;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Monster extends Character {
	private TimerTask aiTask;
	private int baseSkill;
	private IAiChainManager iAiChainManager = new MonsterAiManager();
	
	public Monster(String name, int baseSkill, int maxHealth, int baseAttackRate, IWebSocketDataService webSocketDataService) {
		super(CharacterDataEffectsProxy.newInstance(new CharacterData()), null);

		setId(UUID.randomUUID().toString());
		setName(name);
		setMaxHealth(new StatReference(maxHealth));
		setHealth(maxHealth);
		getWeapon().setBaseAttackRate(baseAttackRate);
		
		List<BodyPart> bodyParts = new ArrayList<BodyPart>();
		bodyParts.add(new BodyPart(BodyPartType.ARM,"Left Arm",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.ARM,"Right Arm",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.LEG,"Left Leg",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.LEG,"Right Leg",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.BACK,"Back",1f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.TORSO,"Torso",1f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HEAD,"Head",0.75f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.NECK,"Neck",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HAND,"Left Hand",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.HAND,"Right Hand",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.FOOT,"Left Foot",0.50f, characterData.getMaxHealth()));
		bodyParts.add(new BodyPart(BodyPartType.FOOT,"Right Foot",0.50f, characterData.getMaxHealth()));
		characterData.setBodyParts(bodyParts);
		
		this.baseSkill = baseSkill;
	}

	public void gotoRoom(Room room) {
		if (room.equals(this.getCurrentRoom()) == false){
			if (getCurrentRoom() != null) {
				this.getCurrentRoom().addCharacter(this);				
			}
			setCurrentRoom(room);
			this.getCurrentRoom().addCharacter(this);
			this.sendDataMessageToRoom(new DataMessage().addCharacter(this), false);
			getAiTask();
		}
	}
	
	@Override
	protected void playerDies() {
		super.playerDies();
		final Corpse corpse = new Corpse(this);
		getCurrentRoom().addItem(corpse);
		final Room corpseRoom = getCurrentRoom();
		corpseRoom.removeCharacter(this);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				corpseRoom.removeItem(corpse);
			}
		}, 120000);
		
	}

	@Override
	protected int getBaseSkill() {
		return this.baseSkill;
	}
	@Transient
	@JsonIgnore
	public IAiChainManager getAiManager() {
		return this.iAiChainManager;
	}
	
	private Task task;
	private BTContext context;
	
	public void setMonsterBehaviorTree(Task task) {
		this.task = task;
	}
	protected void monsterAi() {
		if (this.context == null) {
			this.context = new BTContext();
			this.context.put(BTContext.MONSTER, this);
		}
		if (this.task != null) {
			this.task.doAction(this.context);			
		}
	}

	protected ICharacter selectTarget() {
		Collection<ICharacter> characters = this.getCurrentRoom().getCharacters();
		Collection<ICharacter> targets = new LinkedList<ICharacter>();
		for (ICharacter iCharacter : characters) {
			if (iCharacter instanceof Monster) {
			}
			else {
				targets.add(iCharacter);
			}
		}
		if (targets.size() > 0) {
			
			ICharacter character = new RandomCollection<>(targets).next();
			return character;
		}
		return null;
	}
	
	private TimerTask getAiTask() {
		if (aiTask == null) {
			aiTask = new TimerTask() {
				@Override
				public void run() {
					if (PlayerState.DEAD.equals(getPlayerState())) {
						if (getCurrentRoom() == null || getCurrentRoom().getCharacters().size() == 0) {
							getAiTask().cancel();
						}
					}
					else if (Monster.this.isBusy() == false) {
						Monster.this.monsterAi();						
					}
				}
			};
			timer.scheduleAtFixedRate(this.aiTask, 0, 1000);
		}
		return aiTask;
	}
	
	
//	@Override
//	public void sendRoom() {
//	}
//	
//	@Override
//	public void sendMessage(String message) {
//	}
//	
//	@Override
//	public void sendCharacter() {
//	}
//	
//	@Override
//	public void sendData(Map<String, Object> data) {
//	}
//	
//	public void sendCharacterUpdate(String property, Object value) {
//	}
}
