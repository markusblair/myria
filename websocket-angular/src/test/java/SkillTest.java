import org.adventure.random.Skill;
import org.adventure.random.SkillType;
import org.junit.Test;


public class SkillTest {

	@Test
	public void test() {
		Skill skill = new Skill(SkillType.AXE, 1);
		for (int i = 1; i < 30; i++) {
			System.out.print(getAverageSkillValue(i));
			System.out.print(", ");
		}
	}

	
	private int getAverageSkillValue(int level) {
		int total = 0;
		int tests = 1000;
		Skill skill = new Skill(SkillType.AXE, level);
		skill.setParentSkill(new Skill(SkillType.MELEE, 2));
		for (int i = 0; i < tests; i++) {
			total = total + skill.check(level).getValue1();
		}
		return total/tests;
	}
}
