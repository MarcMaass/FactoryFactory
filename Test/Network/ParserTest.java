package de.hsrm.mi.swt.Test.Network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonParseException;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Node;
import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Network.Utilities.JSONParser;

/**
 * This Test checks, if the Parser deserialize correctly first only for Users
 * TODO: make it generic
 * 
 * @author jalbe001
 * 
 */
public class ParserTest {

	JSONParser p;
	User expectedUser;
	String serial;
	String JSONString;

	public String createHash(String pw) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(pw.getBytes(), 0, pw.length());
		String ret = new BigInteger(1, m.digest()).toString(16);
		return ret;
	}

	@Before
	public void setup() throws Exception {
		JSONString = "{\"name\":\"user\", \"password\":\"ee11cbb19052e40b07aac0ca060c23ee\", \"sessionId\":\"0\"}";
		String name = "user";
		String password = createHash(name);
		expectedUser = new User(name, password, 0, null);
		System.out.println(expectedUser.getPassword());
		p = new JSONParser();
	}

	@Test
	public void serialize() throws JMSException, IOException {
		String serial = p.serialize(expectedUser);
		System.out.println("SERIAL:" + serial);
	}

	@Test
	public void deserialize() throws
			IOException, com.google.gson.JsonParseException,
			ClassNotFoundException {
		User receivedUser = (User) p.deserializeUser(JSONString,
				User.class.getName());
		System.out.println(receivedUser.getName());
		System.out.println(expectedUser.equals(receivedUser));
		assertTrue(expectedUser.equals(receivedUser)); // sind Objekte
														// identisch?
		assertEquals(expectedUser, receivedUser); // sagt das auch JUnit?
		assertNotSame(receivedUser, expectedUser); // haben die Objekte
													// verschiedene Referenzen?
	}

	@Test
	public void deserializeObject() throws JsonParseException, ClassNotFoundException {
		Node testNode = new Node(88);
		String json = p.serialize(testNode);
		System.out.println(json);
		Node b = (Node) p.deserializeUser(json, Node.class.getName());
		System.out.println(b.isFull());
	}

}
