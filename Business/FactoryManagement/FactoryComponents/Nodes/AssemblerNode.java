package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

/**
 * Represents a product-builder. Exactly one input and one output are expected.
 * The AssemblerNode waits for a product heap and passes them as a new complete
 * product to the next node.
 * 
 */
public class AssemblerNode extends SingleIONode {

	public AssemblerNode(int capacity) {
		super(capacity);
	}

	public void execute() throws NodeException{
		IProduct currentProd = getProduct();
		// current product is a heap of different product-parts, now it's marked
		// as a completely new product
		currentProd.setType("Assembled" + currentProd.getType());
		send(removeProduct());

	}
}
