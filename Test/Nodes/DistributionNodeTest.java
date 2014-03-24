package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.DistributionNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class DistributionNodeTest {

	DistributionNode dNode;
	TransportNode t1;
	TransportNode t2;
	TransportNode t3;

	@Before
	public void setup() {
		dNode = new DistributionNode(1, 3);
		dNode.setPos(5, 5);
		t1 = new TransportNode(5);
		t2 = new TransportNode(5);
		t3 = new TransportNode(5);
		t1.setPos(5, 4);
		t2.setPos(6, 5);
		t3.setPos(5, 6);
	}

	@Test
	public void functionalityTest() throws NodeException {
		dNode.setOutput(t1);
		dNode.setOutput(t2);
		dNode.setOutput(t3);

		IProduct p1 = new Product("ID1", "AProduct");
		IProduct p2 = new Product("ID2", "AProduct");
		IProduct p3 = new Product("ID3", "AProduct");
		IProduct p4 = new Product("ID4", "AProduct");
		IProduct p5 = new Product("ID5", "AProduct");
		IProduct p6 = new Product("ID6", "AProduct");
		IProduct p7 = new Product("ID7", "AProduct");
		IProduct p8 = new Product("ID8", "AProduct");
		IProduct p9 = new Product("ID9", "AProduct");
		IProduct p10 = new Product("ID10", "AProduct");
		IProduct p11 = new Product("ID11", "AProduct");

		dNode.addAcceptedProduct(p1.getType());
		t1.addAcceptedProduct(p1.getType());
		t2.addAcceptedProduct(p1.getType());
		t3.addAcceptedProduct(p1.getType());

		// first 3 products should be arrived in t1
		dNode.addProduct(p1);
		assertTrue(dNode.isFull());
		assertFalse(t1.isFull());
		dNode.execute();
		assertFalse(dNode.isFull());
		assertFalse(t1.isFull());
		assertEquals(p1, t1.getProduct());
		dNode.addProduct(p2);
		dNode.execute();
		dNode.addProduct(p3);
		dNode.execute();

		// second 3 products should be arrived in t2
		dNode.addProduct(p4);
		dNode.execute();
		dNode.addProduct(p5);
		dNode.execute();
		dNode.addProduct(p6);
		dNode.execute();
		assertEquals(p4, t2.removeProduct());
		assertEquals(p5, t2.removeProduct());
		assertEquals(p6, t2.removeProduct());

		// third 3 products should be arrived in t3
		dNode.addProduct(p7);
		dNode.execute();
		dNode.addProduct(p8);
		dNode.execute();
		dNode.addProduct(p9);
		dNode.execute();
		assertEquals(p7, t3.removeProduct());
		assertEquals(p8, t3.removeProduct());
		assertEquals(p9, t3.removeProduct());

		// remaining products should be arrived in t1
		dNode.addProduct(p10);
		dNode.execute();
		dNode.addProduct(p11);
		dNode.execute();
		assertTrue(t1.isFull());
		assertEquals(p1, t1.getProduct());
		t1.removeProduct();
		assertEquals(p2, t1.removeProduct());
		assertEquals(p3, t1.removeProduct());
		assertEquals(p10, t1.removeProduct());
		assertEquals(p11, t1.removeProduct());

	}

}
