package org.adventure.spells.illusion;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.CharacterDataEffect;
import org.adventure.character.ICharacter;
import org.adventure.character.PlayerState;
import org.adventure.messaging.DataMessage;
import org.adventure.random.Skill;
import org.adventure.random.SkillCheckResult;
import org.adventure.random.SkillType;
import org.adventure.spells.Spell;

@EmbeddedOnly
public class Daze extends Spell {

	@Override
	public void cast(ICharacter castor, final ICharacter target) {
		if (target != null) {
				Skill charmSkill = castor.getSkill(SkillType.ILLUSION_SPELLS);
				SkillCheckResult skillCheckResult  = charmSkill.check(target.getWill());
				if (skillCheckResult.success()) {					
					int d= (skillCheckResult.getValue1() - skillCheckResult.getValue2()) * charmSkill.getLevel();
					if (d > 30) { 
						d = 30;
					}
					final int  finalD = d;
					target.sendMessageToRoom(target.getName() + " is stunned for " + d + " seconds.");
					target.sendDataMessageToRoom(new DataMessage().updateCharacter(target.getId(), "playerState", PlayerState.STUNNED), true);
					target.addCharacterDataEffect(new CharacterDataEffect() {
						private int duration = finalD;
						
						@Override
						public boolean expire() {
							boolean expired = (duration-- <= 0);
							return expired;
						}
						
						
						@Override
						public void onExpire() {
							target.sendDataMessageToRoom(new DataMessage().updateCharacter(target.getId(), "playerState", target.getPlayerState()), true);
						}
						
						@Override
						public PlayerState getPlayerState() {
							return PlayerState.STUNNED;
						}
					});
				}
				else {
					target.sendMessageToRoom("Resists the stun spell.");
				}
		}
		else {
			castor.sendMessage("You should cast the spell 'at' something.");
		}
	}

	@Override
	public int getManaRequried() {
		return 5;
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.ILLUSION_SPELLS;
	}

	@Override
	public int castingTime() {
		return 6;
	}
	
	@Override
	public String getDescription() {
		return "Daze";
	}
}
