package de.hsrm.mi.swt.Test.Nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.AssemblerNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

public class AssemblerNodeTest {

	AssemblerNode aNode;
	TransportNode tNode;

	@Test
	public void assembleProductsTest() throws NodeException {
		aNode = new AssemblerNode(1);
		tNode = new TransportNode(1);
		IProduct prod = new Product("1234ID", "MergedTeddyArmTeddyLegTeddyBody");

		aNode.addAcceptedProduct(prod.getType());
		tNode.addAcceptedProduct("Assembled" + prod.getType());

		aNode.setPos(3, 3);
		tNode.setPos(4, 3);
		
		aNode.setOutput(tNode);
		aNode.addProduct(prod);

		aNode.execute();
		assertEquals("AssembledMergedTeddyArmTeddyLegTeddyBody", tNode
				.getProduct().getType());
		assertFalse(aNode.isFull());
	}
}
