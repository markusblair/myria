package org.adventure.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="characterSession",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PlayerCharacter extends Character {
	
	public PlayerCharacter() {
		super(null, null);
	}
	
	public PlayerCharacter(ICharacterData characterData) {
		super(characterData, null);
	}

	public void setCharacterData(ICharacterData characterData) {
		super.setCharacterData(characterData);
	}

	@Override
	@Autowired
	protected void setWebSockerSession(IWebSocketDataService webSocketDataService) {
		super.setWebSockerSession(webSocketDataService);
	}
	
}
