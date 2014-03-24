package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SplitNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NextNodeFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class SplitNodeTest {
	SplitNode sNode;
	TransportNode tNode;

	@Test
	public void splittingTest() throws NodeException {
		sNode = new SplitNode(1, 2);
		tNode = new TransportNode(2);

		IProduct cake = new Product("12345ID", "SuperChocolateCake");
		sNode.addAcceptedProduct(cake.getType());

		tNode.addAcceptedProduct("Split2" + cake.getType());

		sNode.setPos(7, 7);
		tNode.setPos(7, 8);

		sNode.setOutput(tNode);

		assertTrue(sNode.addProduct(cake));
		sNode.execute();
		assertFalse(sNode.isFull());
		assertTrue(tNode.isFull());
		assertEquals("Split2" + cake.getType(), tNode.getProduct().getType());
	}

	@Test(expected = NextNodeFullException.class)
	public void jamTest() throws NodeException {
		sNode = new SplitNode(1, 4);
		tNode = new TransportNode(2);

		IProduct cake = new Product("12345ID", "SuperChocolateCake");
		sNode.addAcceptedProduct(cake.getType());

		tNode.addAcceptedProduct("Split4" + cake.getType());

		sNode.setPos(7, 7);
		tNode.setPos(7, 8);
		
		sNode.setOutput(tNode);

		assertTrue(sNode.addProduct(cake));
		
		assertTrue(sNode.isFull());
		assertFalse(tNode.isFull());
		
		sNode.execute();
		
		assertTrue(tNode.isFull());
	}

}
