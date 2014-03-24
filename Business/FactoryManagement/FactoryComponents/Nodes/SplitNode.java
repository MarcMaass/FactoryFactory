package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

/**
 * Represents a cutter. Exactly one input and one output are expected. The
 * SplitNode accepts one product and divides it into several new product-pieces.
 * 
 */
public class SplitNode extends SingleIONode {
	private int nrSplittings;

	public SplitNode(int capacity, int nrSplittings) {
		super(capacity);
		this.nrSplittings = nrSplittings;
	}

	public void execute() throws NodeException {
		IProduct currentProd = getProduct();

		for (int i = 0; i < nrSplittings; i++) {
			IProduct newProdPart = new Product(currentProd.getID(), "Split"
					+ nrSplittings + currentProd.getType(), "SplittedIn"
					+ nrSplittings + currentProd.getDescription(),
					currentProd.getTexture());
			this.send(newProdPart); // TODO catch Products which can't be sent
								// because of a full NextNode
		}

		removeProduct();

	}
}
