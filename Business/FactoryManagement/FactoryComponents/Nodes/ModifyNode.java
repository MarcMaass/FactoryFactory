package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

/**
 * Represents a machine-component which changes the product-properties. Exactly
 * one input and one output are expected.
 * 
 * 
 */
public class ModifyNode extends SingleIONode {
	public ModifyNode(int capacity) {
		super(capacity);
	}

	public void execute() throws NodeException {
		IProduct currentProd = getProduct();
		// TODO scripting modifies
		currentProd.setData(null);

		send(removeProduct());
	}
}
