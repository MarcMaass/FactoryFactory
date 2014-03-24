package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Node;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SingleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueEmptyException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

public class NodeTest {

	INode node;

	@Test
	public void setInput() throws NodeException {
		node = new Node(2);
		INode newNode = new Node(3);
		INode newNode2 = new Node(2);

		node.setPos(3, 4);

		newNode.setPos(4, 4);
		assertTrue(node.setInput(newNode));

		newNode2.setPos(4, 4);
		assertTrue(node.setInput(newNode2));
		assertTrue(newNode.getOutputs().size() == 0);

		newNode.setPos(5, 5);
		assertFalse(node.setInput(newNode));
	}

	@Test
	public void setOutput() throws NodeException {
		node = new Node(2);
		INode on1 = new Node(3);
		INode on2 = new Node(4);

		node.setPos(0, 0);

		on1.setPos(0, 1);
		assertTrue(node.setOutput(on1));

		on2.setPos(0, 1);
		assertTrue(node.setOutput(on2));
		assertTrue(on1.getOutputs().size() == 0);

		on1.setPos(9, 9);
		assertFalse(node.setOutput(on1));
	}

	@Test
	public void childTest() throws NodeException {
		node = new SingleIONode(2);

		INode c1 = new SingleIONode(1);
		INode c2 = new SingleIONode(1);
		INode c3 = new SingleIONode(1);

		node.setChildNode(c1);
		c1.setChildNode(c2);
		c2.setChildNode(c3);

		assertEquals(c1, node.getOutputs().get(Direction.BOTTOM));
		assertEquals(c2, c1.getOutputs().get(Direction.BOTTOM));
		assertEquals(c3, c2.getOutputs().get(Direction.BOTTOM));
	}

	@Test
	public void childIOTest() throws NodeException {
		node = new SingleIONode(2);

		INode c1 = new SingleIONode(1);
		INode c2 = new SingleIONode(1);
		INode c3 = new SingleIONode(1);

		INode output = new Node(1);
		INode input = new Node(1);

		input.setPos(3, 4);
		node.setPos(4, 4);
		output.setPos(5, 4);
		c1.setPos(4, 4);
		c2.setPos(4, 4);
		c3.setPos(4, 4);
		

		node.setInput(input);
		
		assertTrue(node.setChildNode(c1));
		assertTrue(c1.setChildNode(c2));
		assertTrue(c2.setChildNode(c3));
		
		//set output to node "node", but connect with last childNode c3
		node.setOutput(output);

		assertEquals(node, input.getOutputs().get(Direction.EAST));
		assertEquals(c1, node.getOutputs().get(Direction.BOTTOM));
		assertEquals(c2, c1.getOutputs().get(Direction.BOTTOM));
		assertEquals(c3, c2.getOutputs().get(Direction.BOTTOM));
		assertEquals(c3, output.getInputs().get(Direction.WEST));
	}

	@Test
	public void addAndGetProducts() throws NodeException {
		node = new Node(5);

		IProduct p1 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p2 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p3 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p4 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p5 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p6 = new Product("testID", "TestType", "Unusual", "metal");
		
		node.addAcceptedProduct(p1.getType());

		assertTrue(node.addProduct(p1));
		assertTrue(node.addProduct(p2));
		assertTrue(node.addProduct(p3));
		assertTrue(node.addProduct(p4));
		assertTrue(node.addProduct(p5));

		assertEquals(p1, node.removeProduct());

		assertTrue(node.addProduct(p6));
	}

	@Test(expected = ProductQueueFullException.class)
	public void addProductsExceptionTest() throws NodeException {
		node = new Node(2);

		IProduct p1 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p2 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p3 = new Product("testID", "TestType", "Unusual", "metal");
		
		node.addAcceptedProduct(p1.getType());

		assertTrue(node.addProduct(p1));
		assertTrue(node.addProduct(p2));
		assertTrue(node.addProduct(p3));
	}

	@Test(expected = ProductQueueEmptyException.class)
	public void getProductsExceptionTest() throws NodeException {
		node = new Node(1);
		node.getProduct();
	}

	@Test
	public void nodeCapacityTest() throws NodeException {
		node = new Node(2);

		IProduct p1 = new Product("testID", "TestType", "Unusual", "metal");
		IProduct p2 = new Product("testID", "TestType", "Unusual", "metal");
		
		node.addAcceptedProduct(p1.getType());

		assertFalse(node.isFull());
		node.addProduct(p1);
		assertFalse(node.isFull());
		node.addProduct(p2);
		assertTrue(node.isFull());
		node.removeProduct();
		assertFalse(node.isFull());

	}
}
