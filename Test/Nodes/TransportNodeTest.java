package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class TransportNodeTest {
	TransportNode t1;
	TransportNode t2;
	TransportNode t3;

	@Test
	public void transporterQueueTest() throws NodeException{
		t1 = new TransportNode(1);
		t2 = new TransportNode(1);
		t3 = new TransportNode(1);

		IProduct p1 = new Product("1234ID", "Cookies");

		t1.addAcceptedProduct(p1.getType());
		t2.addAcceptedProduct(p1.getType());
		t3.addAcceptedProduct(p1.getType());
		
		t1.setPos(0, 0);
		t2.setPos(1, 0);
		t3.setPos(2, 0);

		t1.setOutput(t2);
		t2.setOutput(t3);

		assertTrue(t1.addProduct(p1));
		t1.execute();
		assertEquals(p1, t2.getProduct());
		assertFalse(t1.isFull());
		assertTrue(t2.isFull());
		t2.execute();
		assertEquals(p1, t3.getProduct());
		assertFalse(t2.isFull());
		assertTrue(t3.isFull());
	}

}
