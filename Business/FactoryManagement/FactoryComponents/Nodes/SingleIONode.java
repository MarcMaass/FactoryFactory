package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NextNodeFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.ISingleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

public class SingleIONode extends Node implements ISingleIONode {

	public SingleIONode(int capacity) {
		super(capacity);
	}

	@Override
	public void send(IProduct prod) throws NextNodeFullException,
			NoAcceptedProductException {
		try {
			for(Direction d : getOutputs().keySet()){
				getOutputs().get(d).addProduct(prod);
			}
		} catch (ProductQueueFullException e) {
			throw new NextNodeFullException();
		} catch (NoAcceptedProductException e) {
			throw new NoAcceptedProductException();
		}
	}
}
