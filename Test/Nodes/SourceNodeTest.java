package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SourceNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class SourceNodeTest {

	SourceNode srcNode;

	@Test
	public void srcTest() throws NodeException {
		srcNode = new SourceNode(3);

		IProduct p1 = new Product("MyId1", "SomeProcessInput");
		IProduct p2 = new Product("MyId2", "SomeProcessInput");
		IProduct p3 = new Product("MyId3", "SomeProcessInput");
		IProduct p4 = new Product("MyId4", "SomeProcessInput");

		TransportNode node = new TransportNode(1);

		srcNode.addAcceptedProduct(p1.getType());
		srcNode.setPos(0, 0);
		node.setPos(1, 0);
		srcNode.setOutput(node);

		node.addAcceptedProduct(p1.getType());

		assertTrue(srcNode.addProduct(p1));
		assertTrue(srcNode.addProduct(p2));
		assertTrue(srcNode.addProduct(p3));

		assertTrue(srcNode.isFull());
		assertFalse(node.isFull());

		srcNode.execute();

		assertEquals(p1, node.getProduct());

		assertFalse(srcNode.isFull());
		assertTrue(node.isFull());

		assertTrue(srcNode.addProduct(p4));
	}

}
