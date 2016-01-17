package org.adventure.rooms;

import org.adventure.commands.BuyAction;
import org.adventure.commands.TakeCommand;
import org.adventure.items.Container;
import org.adventure.items.Item;
import org.adventure.random.SkillType;

public class TrainingRoom extends Room {

	public TrainingRoom() {
		this.addCommand(new BuyAction());
		
		Container sign = new Container("Sign", "Sign with a list of offered services.",0 ,0, "Something about no refunds allowed!");
		sign.setVolumeCapacity(1);
		sign.setItemCountCapacity(1000);
		sign.setContentsVisible(true);
		this.addItem(sign);
		createSkillTraining(sign, "melee", SkillType.MELEE);
		createSkillTraining(sign, "sword", SkillType.SWORD);
		createSkillTraining(sign, "two handed sword", SkillType.TWO_HANDDED_SWORD);
		createSkillTraining(sign, "parry", SkillType.PARRY_MELEE);
		createSkillTraining(sign, "shield", SkillType.SHIELD);
		createSkillTraining(sign, "dodge", SkillType.DODGE);
		createSkillTraining(sign, "axe", SkillType.AXE);
		createSkillTraining(sign, "two handed axe", SkillType.TWO_HANDED_AXE);
		
		createSkillTraining(sign, "Protection", SkillType.PROTECTIN_SPELLS);
		createSkillTraining(sign, "Destruction", SkillType.DESTRUCTION_SPELLS);
		createSkillTraining(sign, "Heal", SkillType.HEAL_SPELLS);
		createSkillTraining(sign, "Charm", SkillType.ILLUSION_SPELLS);
	}

	protected void createSkillTraining(Container sign, String name, SkillType skillType) {
		Item item3 = new Item(name, name, 0, 0);
		item3.addCommandCondition(TakeCommand.class, new TrainingPrice(10, skillType));
		sign.addItem(item3);
	}

}
