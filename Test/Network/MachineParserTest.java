package de.hsrm.mi.swt.Test.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Node;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Network.Utilities.JSONParser;

/**
 * This Test checks, if the Parser deserialize correctly
 * TODO: make it generic
 * @author jalbe001
 *
 */
public class MachineParserTest {

	JSONParser p;
	MachineUnit machine;
	String serial;
	String JSONString;
	

	public void buildMachine() throws Exception {

			// Nodes
			INode n1 = new TransportNode(1);
			INode n2 = new TransportNode(1);
			INode n3 = new TransportNode(1);
			INode n4 = new TransportNode(1);
			INode n5 = new TransportNode(1);
			INode n6 = new TransportNode(1);
			INode n7 = new TransportNode(1);

			// Position in Machine
			n1.setPos(0, 0);
			n2.setPos(1, 0);
			n3.setPos(2, 0);
			n4.setPos(3, 0);
			n5.setPos(4, 0);
			n6.setPos(5, 0);
			n7.setPos(6, 0);

			// Connect Nodes
			try {
				n1.setOutput(n2);
				n2.setOutput(n3);
				n3.setOutput(n4);
				n4.setOutput(n5);
				n5.setOutput(n6);
				n6.setOutput(n7);
			} catch (Exception e){
				e.printStackTrace();
			}

//			// 
//			n2.addAcceptedProduct();
//			n3.addAcceptedProduct("teddy");
//			n4.addAcceptedProduct("teddy");
//			n5.addAcceptedProduct("teddy");
//			n6.addAcceptedProduct("teddy");
//			n7.addAcceptedProduct("teddy");
			
			List<INode> nodes = new ArrayList<INode>();
			nodes.add(n1);
			nodes.add(n2);
			nodes.add(n3);
			nodes.add(n4);
			nodes.add(n5);
			nodes.add(n6);
			nodes.add(n7);

			ArrayList<Integer> input = new ArrayList<Integer>();
			input.add(0);
			ArrayList<Integer> output = new ArrayList<Integer>();
			input.add(6);
			
			machine = new MachineUnit("transport", 7, 1, nodes, input, output);
	}
	
	@Before
	public void setup() throws Exception{
		JSONString = "{\"name\":\"user\", \"password\":\"ee11cbb19052e40b07aac0ca060c23ee\", \"sessionId\":\"0\"}";
		
		buildMachine();

		p = new JSONParser();
	}

	@Test
	public void serialize() throws JMSException, IOException{
		INode n = new TransportNode(1);
		String serial = p.serialize(machine);
		System.out.println(machine.getTypeID() + " : " + serial);
	}
	
	@Test
	public void deserialize() throws JMSException, IOException, com.google.gson.JsonParseException, ClassNotFoundException {
		INode receivedNode = (Node) p.deserializeUser(JSONString, INode.class.getName());
		System.out.println(receivedNode.toString());
//		assertTrue(expectedUser.equals(receivedUser)); // sind Objekte identisch?
//		assertEquals(expectedUser, receivedUser); // sagt das auch JUnit?
//		assertNotSame(receivedUser, expectedUser); // haben die Objekte verschiedene Referenzen?
	}
	


}
