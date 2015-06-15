package org.adventure.monster.ai;

import java.util.ArrayList;
import java.util.List;

import org.adventure.monster.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonsterAiManager implements IAiChainManager {
	Logger log = LoggerFactory.getLogger(MonsterAiManager.class);
	private List<IAiChainHandler> handlers = new ArrayList<IAiChainHandler>();
	
	@Override
	public void processChain(Monster monster) {
		for (IAiChainHandler iAiChainHandler : handlers) {
			try {
				if (iAiChainHandler.handle(monster)) {
					log.info("AI Cycle complete: " + iAiChainHandler.getClass());
					break;
				}
			} catch (Exception e) {
				log.error("Error processing AiChainHandler.", e);
			}
		}
	}

	@Override
	public void addChainHandler(IAiChainHandler chainHandler) {
		this.handlers.add(chainHandler);
	}

}
