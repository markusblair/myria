package org.adventure.character;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CopyOnWriteArrayList;

public class CharacterDataEffectsProxy implements InvocationHandler {

	public static ICharacterData newInstance(ICharacterData characterData) {
		return (ICharacterData)Proxy.newProxyInstance(characterData.getClass().getClassLoader(), characterData.getClass().getInterfaces(), new CharacterDataEffectsProxy(characterData));
	}

	private ICharacterData characterData;
	private CopyOnWriteArrayList<CharacterDataEffect> characterDataEffects = new CopyOnWriteArrayList<CharacterDataEffect>();
	public CharacterDataEffectsProxy(ICharacterData characterData) {
		super();
		this.characterData = characterData;
	}

	public void addEffect(CharacterDataEffect characterDataEffect) {
		this.characterDataEffects.add(characterDataEffect);
	}

	public void expireEffects() {
		
		for (CharacterDataEffect characterDataEffect : characterDataEffects) {
			if (characterDataEffect.expire()) {
				// We can do this since this is the CopyOnWriteArrayList
				characterDataEffects.remove(characterDataEffect);
				characterDataEffect.onExpire();
			}
		}
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// Now I just need to pass it through each of the current effects.
		IEffectableCharacterData previousCharacterData = this.characterData;
		if (method.getDeclaringClass().isAssignableFrom(IEffectableCharacterData.class)) {
			for (CharacterDataEffect effect : this.characterDataEffects) {
				effect.setCharacterData(previousCharacterData);
				previousCharacterData = effect;
			}			
		}
		Object object =  method.invoke(previousCharacterData, args);
		return object;
	}

}
