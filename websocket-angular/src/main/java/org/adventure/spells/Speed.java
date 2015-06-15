package org.adventure.spells;

import javax.jdo.annotations.EmbeddedOnly;

import org.adventure.character.CharacterDataEffect;
import org.adventure.character.DataMessage;
import org.adventure.character.ICharacter;
import org.adventure.random.SkillType;

@EmbeddedOnly
public class Speed extends Spell {
	
	@Override
	public String getDescription() {
		return "Speed";
	}

	@Override
	public void cast(final ICharacter castor, final ICharacter target) {
		final int speedBoost = castor.getSkill(getSkillType()).getValue() + 10;
		castor.sendMessage(target.getName() + " begins to move faster.");
		castor.addCharacterDataEffect(new CharacterDataEffect() {
			int duration = 30;
			@Override
			public void onExpire() {
				target.sendDataMessageToRoom(new DataMessage().updateCharacter(target.getId(), "playerState", target.getPlayerState()), true);
				castor.sendMessage(target.getName() + " returns to normal speed.");
			}
			
			@Override
			public boolean expire() {
				boolean expired = (duration-- <= 0);
				return expired;
			}

			@Override
			public int getSpeed() {
				return super.getSpeed() + speedBoost;
			}
		});
	}

	@Override
	public SkillType getSkillType() {
		return SkillType.ILLUSION_SPELLS;
	}

	@Override
	public int getManaRequried() {
		return 5;
	}

	@Override
	public int castingTime() {
		return 6;
	}

}
