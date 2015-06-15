package org.adventure.monster.ai;

import org.adventure.monster.Monster;

public interface IAiChainManager {
	public void processChain(Monster monster);
	public void addChainHandler(IAiChainHandler chainHandler);
	
}
