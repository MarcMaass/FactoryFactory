package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.AssemblerNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.ModifyNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SortNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SourceNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoChildNodeAllowedException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.IMultipleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.ISingleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class SingleIONodeTest {

	ISingleIONode sion;
	ISingleIONode n1;
	ISingleIONode n2;
	IProduct p;
	IMultipleIONode mion;

	@Before
	public void setup() {
		sion = new SourceNode(1);
		n1 = new AssemblerNode(1);
		n2 = new ModifyNode(1);

		sion.setPos(0, 0);
		n1.setPos(0, 0);
		n2.setPos(0, 0);

		p = new Product("123", "TeddyType");

		sion.addAcceptedProduct(p.getType());
		n1.addAcceptedProduct(p.getType());
		n2.addAcceptedProduct("AssembledTeddyType");
	}

	@Test
	public void multiNodeTest() throws NodeException {
		assertTrue(sion.setOutput(n1));
		assertTrue(n1.setOutput(n2));

		sion.addProduct(p);
		assertTrue(sion.isFull());
		assertFalse(n1.isFull());
		assertFalse(n2.isFull());

		sion.execute();
		n1.execute();

		assertFalse(sion.isFull());
		assertFalse(n1.isFull());
		assertTrue(n2.isFull());

		assertEquals(p, n2.getProduct());
	}

	@Test
	public void removeMiddleChildNodeTest() throws NodeException {
		sion.setOutput(n1);
		n1.setOutput(n2);

		sion.removeChildNode(n1);
		n2.addAcceptedProduct(p.getType());

		sion.addProduct(p);
		sion.execute();
		assertTrue(n2.isFull());
		assertFalse(sion.isFull());
		assertFalse(n1.isFull());
		assertEquals(p, n2.getProduct());

	}

	@Test(expected = NoChildNodeAllowedException.class)
	public void childNodeWithMultiIONode() throws NodeException {
		mion = new SortNode(1);
		mion.setPos(0, 0);

		mion.setOutput(n1);
	}

	@Test(expected = NoChildNodeAllowedException.class)
	public void childNodeWithMultiIONode2() throws NodeException {
		mion = new SortNode(1);
		mion.setPos(0, 0);

		mion.setChildNode(n1);
	}

	@Test(expected = NoChildNodeAllowedException.class)
	public void childNodeWithMultiIONode3() throws NodeException {
		mion = new SortNode(1);
		mion.setPos(0, 0);

		sion.setChildNode(mion);
	}

	@Test(expected = NoChildNodeAllowedException.class)
	public void childNodeWithMultiIONode4() throws NodeException {
		mion = new SortNode(1);
		mion.setPos(0, 0);

		sion.setOutput(mion);
	}

}
