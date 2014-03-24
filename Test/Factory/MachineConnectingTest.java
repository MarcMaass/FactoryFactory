package de.hsrm.mi.swt.Test.Factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.intf.IPersistence;

public class MachineConnectingTest {

	IPersistence ps;
	IFactory f;
	IMachineUnit mu1;
	IMachineUnit mu2;
	List<INode> l1;
	List<INode> l2;
	IProduct p;

	@Before
	public void setup() {
		ps = new Persistence();
		f = new Factory(1, "test", 100, 100, "users", "users");
		l1 = new ArrayList<INode>();
		l2 = new ArrayList<INode>();
		p = new Product("2347678236", "teddybaer");
	}

	@Test
	public void machineConnection() throws NodeException {
		INode n1 = new TransportNode(1);
		INode n2 = new TransportNode(1);
		INode n3 = new TransportNode(1);

		n1.setPos(0, 0);
		n2.setPos(1, 0);
		n3.setPos(2, 0);

		((TransportNode) n1).setOutput(n2);
		((TransportNode) n2).setOutput(n3);

		((TransportNode) n1).addAcceptedProduct(p.getType());
		((TransportNode) n2).addAcceptedProduct(p.getType());
		((TransportNode) n3).addAcceptedProduct(p.getType());

		l1.add(n1);
		l1.add(n2);
		l1.add(n3);

		List<Integer> inputs = new ArrayList<Integer>();
		inputs.add(0);

		List<Integer> outputs = new ArrayList<Integer>();
		outputs.add(2);

		mu1 = new MachineUnit("transportmachine1", 3, 1, l1, inputs, outputs);

		// mu2
		INode n4 = new TransportNode(1);
		INode n5 = new TransportNode(1);
		INode n6 = new TransportNode(1);

		n4.setPos(0, 0);
		n5.setPos(1, 0);
		n6.setPos(2, 0);

		((TransportNode) n4).setOutput(n5);
		((TransportNode) n5).setOutput(n6);


		((TransportNode) n4).addAcceptedProduct(p.getType());
		((TransportNode) n5).addAcceptedProduct(p.getType());
		((TransportNode) n6).addAcceptedProduct(p.getType());

		l2.add(n4);
		l2.add(n5);
		l2.add(n6);

		mu2 = new MachineUnit("transportmachine2", 3, 1, l2, inputs, outputs);

		assertTrue(f.placeMachineUnit(mu1, 0, 0) != null);
		assertTrue(f.placeMachineUnit(mu2, 3, 0) != null);
		
		assertFalse(mu1.equals(mu2));
		assertFalse(mu1.getPosX() == mu2.getPosX());

		// with connected machines:
		mu1.getInputNodes().get(0).addProduct(p);
		p.getNode().execute();
		p.getNode().execute();
		p.getNode().execute();
		p.getNode().execute();
		p.getNode().execute();
		assertEquals(p, n6.getProduct());
		
		// with disconnected machines:
		f.moveMachineUnit(mu2.getID(), 0, 1);
		mu1.getInputNodes().get(0).addProduct(p);
		p.getNode().execute();
		p.getNode().execute();
		assertNull(p.getNode().getOutputs().get(Direction.EAST));
	}

}
