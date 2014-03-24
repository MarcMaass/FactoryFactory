package de.hsrm.mi.swt.Network.Utilities;

import java.io.IOException;

import javax.jms.JMSException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;



/**
 * JSON-Parser, this utility-class is responsible for parsing JSON-Strigs from Objects
 * and create Object from JSON-Strings
 * a JSON-String is build in the form obj = {"key":"value"}
 * and represents the object:
 * string key = value;
 *  
 * @author Justin Albert, Mario Wandpflug
 * 
 */
public class JSONParser {

	/**
	 * instance from googles gson parserbuilder
	 */
	private static Gson gson = null;

	/**
	 * ParserFactory Constructor
	 */
	public JSONParser() {
		this.gson = getInstance();
	}

	/**
	 * singleton-Pattern
	 * 
	 * @return the single-instance from parser
	 */
	private static Gson getInstance() {
		if (gson == null) {
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.create();
		}
		return gson;
	}

	/**
	 * This class deserializes jsonStrings into the object declared in String
	 * object
	 * 
	 * @param jsonData
	 * @param object
	 * @return The deserialized Object, caller must cast into the right Object
	 * @throws JMSException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws com.google.gson.JsonParseException
	 * @throws ClassNotFoundException
	 */
	public Object deserializeUser(String jsonData, String object)
			throws JsonParseException, ClassNotFoundException {
//		System.out.println("User deserializeUser():\n" + jsonData);
		Object obj2 = gson.fromJson(jsonData, Class.forName(object));
		return obj2;
	}

	/**
	 * 
	 * @param data 
	 * @return JSON-String of the serialized Data
	 */
	public String serialize(Object data) {
		return gson.toJson(data);
	}

}