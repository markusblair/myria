import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.adventure.character.BodyPart;
import org.adventure.character.BodyPartType;
import org.adventure.character.CharacterData;
import org.adventure.character.WebSocketDataService;
import org.adventure.character.stats.IStatReference;
import org.adventure.character.stats.StatReference;
import org.adventure.items.armor.AttackResult;
import org.adventure.items.armor.DamageCalculation;
import org.adventure.items.weapons.DamageType;
import org.adventure.random.Skill;
import org.adventure.random.SkillCheckResult;
import org.adventure.random.SkillDelegate;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;


public class QuickTest {

	@Test
	public void test() {
		AttackResult attackResult = new AttackResult(new SkillCheckResult(), "A", "B");
		IStatReference maxCharacterHealth = new StatReference(100);
		DamageCalculation damageCalculation = new DamageCalculation(new BodyPart(BodyPartType.ARM, "Rigth Arm", 0.50f, maxCharacterHealth));
		attackResult.setDamageCalculation(damageCalculation);
		damageCalculation.addDamage(DamageType.BLUNT, 5, 4, 5);
		
		Map<String, Object> data = new HashMap<String, Object>();
		Writer stringWritter = new StringWriter();
		try {
			new JsonFactory().createGenerator(stringWritter).setCodec(new ObjectMapper()).writeObject(attackResult);
			System.out.println(stringWritter.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test1() {
		AttackResult attackResult = new AttackResult(new SkillCheckResult(), "A", "B");
		StatReference maxCharacterHealth = new StatReference(100);
		BodyPart bodyPart = new BodyPart(BodyPartType.ARM, "Rigth Arm", 0.50f, maxCharacterHealth);
		Assert.assertEquals(50, bodyPart.getMaxHealth());
		
		maxCharacterHealth.setStat(200);
		Assert.assertEquals(100, bodyPart.getMaxHealth());
	}
	
	@Test
	public void test2() {
		WebSocketDataService wsDataService = new WebSocketDataService();
		
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", 6);
		p.put("prop", 6);
		data.put("character", wsDataService.jsonify(p));
		
		System.out.println(wsDataService.jsonify(data));
		
		TestData testData = new TestData();
		System.out.println(wsDataService.jsonify(testData));
		
	}
	
	@Test
	public void test3() {
		Skill skill = new Skill();
		SkillDelegate skillDelegate = new SkillDelegate(skill);
		int samples = 100000;
		for (int lvl = 0; lvl < 60; lvl++) {
			int max =0;
			int tot = 0;
			int min = Integer.MAX_VALUE;
			skillDelegate.setMultiplier(1f);
			for (int i = 0; i < samples; i++) {
				int val = skillDelegate.getValue(lvl);
				tot = tot + val;
				if (val > max) max = val;
				if (val < min ) min = val;
			}
			System.out.println(lvl + "= " +tot/samples + " max:" + max + "  min:" + min);
			tot =0;
		}
	}
}
