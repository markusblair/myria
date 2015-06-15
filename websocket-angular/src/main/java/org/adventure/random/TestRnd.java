package org.adventure.random;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestRnd {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(TestRnd.class);

		Random rnd = new Random();
		int i =10000;
		int x = 0;
		while (i-- > 0) {
			int attack   = (int) new Skill(null, 2).getValue();
			int defense   = (int) new Skill(null, 1).getValue();
			if (attack > defense) {
				x++;
			}
		}
		logger.debug("Hit Percent " + x/100);
	}

}
