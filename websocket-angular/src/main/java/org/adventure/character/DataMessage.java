package org.adventure.character;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.adventure.items.IItem;
import org.adventure.items.armor.AttackResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataMessage {

	private static Logger log = LoggerFactory.getLogger(DataMessage.class);
	private DataMesageType type;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	public DataMessage() {

	}

	public DataMessage addCharacter(ICharacter character) {
		this.type = DataMesageType.ADD;
		data.put("action", "add");
		data.put("character", jsonify(character));
		
		return this;
	}
	
	public DataMessage removeCharacter(ICharacter character) {
		this.type = DataMesageType.DELETE;
		HashMap<String, Object> props = new HashMap<String, Object>();
		data.put("action", "remove");
		props.put("id", character.getId());
		data.put("character", props);
		return this;
	}
	
	public DataMessage updateCharacter(String id, String property, Object value) {
		this.type = DataMesageType.UPDATE;
		HashMap<String, Object> props = new HashMap<String, Object>();
		data.put("action", "update");
		props.put("id", id);
		props.put(property, value);
		data.put("character", props);
		return this;
	}
	
	public DataMessage addItem(IItem item) {
		this.type = DataMesageType.ADD;
		data.put("action", "add");
		data.put("item", jsonify(item));
		return this;
	}
	
	public DataMessage removeItem(IItem item) {
		this.type = DataMesageType.DELETE;
		data.put("action", "remove");
		data.put("item", jsonify(item));
		return this;
	}
	
	public DataMessage addAttackResult(AttackResult attackResult) {
		
		return this;
	}
	
	public String jsonify(Object object) {
		Writer stringWritter = new StringWriter();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			
			new JsonFactory().createGenerator(stringWritter).setCodec(objectMapper).writeObject(object);

		} catch (Exception e) {
			log.error("Error json serailzing object :" + object.getClass(), e);
		}
		return stringWritter.toString();
	}
	
	public HashMap<String, Object> getData() {
		return data;
	}
}
