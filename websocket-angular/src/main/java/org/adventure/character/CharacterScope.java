package org.adventure.character;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class CharacterScope implements Scope {

	private Map<String, Object> characterMap = Collections.synchronizedMap(new HashMap<String, Object>());

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if (!characterMap.containsKey(name)) {
			characterMap.put(name, objectFactory.getObject());
		}
		return characterMap.get(name);

	}
	/** (non-Javadoc)
     * @see org.springframework.beans.factory.config.Scope#remove(java.lang.String)
     */
    public Object remove(String name) {
        return characterMap.remove(name);
    }
 
    /** (non-Javadoc)
     * @see org.springframework.beans.factory.config.Scope#registerDestructionCallback(java.lang.String, java.lang.Runnable)
     */
    public void registerDestructionCallback(String name, Runnable callback) {
        // do nothing
    }
 
    /** (non-Javadoc)
     * @see org.springframework.beans.factory.config.Scope#resolveContextualObject(java.lang.String)
     */
    public Object resolveContextualObject(String key) {
        return null;
    }
 
    /** (non-Javadoc)
     * @see org.springframework.beans.factory.config.Scope#getConversationId()
     */
    public String getConversationId() {
        return "CharacterScope";
    }

}
