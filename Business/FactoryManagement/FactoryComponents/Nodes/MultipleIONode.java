package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoOutputNodeAvailableException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.IMultipleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

public class MultipleIONode extends Node implements IMultipleIONode {

	public MultipleIONode(int capacity) {
		super(capacity);
	}

	@Override
	public void send(IProduct prod, Direction dir)
			throws ProductQueueFullException, NoAcceptedProductException,
			NoOutputNodeAvailableException {
		if (getOutputs().containsKey(dir)) {
			getOutputs().get(dir).addProduct(prod);
		} else {
			throw new NoOutputNodeAvailableException();
		}
	}

}
