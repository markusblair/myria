package org.adventure.persistance;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.adventure.character.CharacterData;
import org.adventure.character.ICharacterData;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CharacterDao {
	private static Logger log = LoggerFactory.getLogger(CharacterDao.class);
	protected static Timer timer = new Timer();
	
	public CharacterDao() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Collection<CharacterData> characters =  CharacterDao.this.loadedCharacters.values();
				for (CharacterData playerCharacter :characters) {
					CharacterDao.this.save(playerCharacter);					
				}
				log.trace("Saving Chartacters.");
			}
		}, 60000, 5000);
	}
	public class CharacterDescriptor {

	}
	private Map<String, CharacterData> loadedCharacters = new HashMap<String, CharacterData>();

	@Autowired
	private PersistenceManager pm;
	
	public ICharacterData load(String id) {
		if (loadedCharacters.containsKey(id)) {
			return loadedCharacters.get(id);
		}
		CharacterData loaded =  pm.getObjectById(CharacterData.class, id);
		loadedCharacters.put(id, loaded);
		return loaded;
	}

	public boolean delete(String id) {
		Transaction t = pm.currentTransaction();
		try {
			if (t.isActive() == false) {
				t.begin();
			}
			ICharacterData loaded = null;
			if (loadedCharacters.containsKey(id)) {
				loaded = loadedCharacters.get(id);
			}
			else {
				loaded =  pm.getObjectById(CharacterData.class, id);			
			}
			
			pm.deletePersistent(loaded); 
			System.out.println("Deleteing character:" + ToStringBuilder.reflectionToString(loaded));
			t.commit();
			return true;
		} catch (Exception e) {
			log.error("Error Saving player",e);
			return false;
		}
		
	}
	
	public void logout(String id) {
		loadedCharacters.remove(id);
	}
	
	public void save(CharacterData playerBean) {
		Transaction t = pm.currentTransaction();
		try {
			t.begin();
			pm.makePersistent(playerBean);
			log.debug("Saving character:" + ToStringBuilder.reflectionToString(playerBean));
			t.commit();
		} catch (Exception e) {
			log.error("Error Saving player",e);
			t.rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CharacterData> listCharacters(String userName) {
		Query query = pm.newQuery(CharacterData.class);
		query.setFilter("userId = userParam");
		query.declareParameters("String userParam");
		Object result = query.execute(userName);
		
		return (List<CharacterData>)result;
	}

	
}
