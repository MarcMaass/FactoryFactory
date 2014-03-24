package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NextNodeFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;

/**
 * Lowest machine element with just one Input and one Output
 * 
 */
public interface ISingleIONode extends INode {

	public static final int MAX_NR_INPUT = 1;
	public static final int MAX_NR_OUTPUT = 1;

	public void send(IProduct prod) throws NextNodeFullException,
			NoAcceptedProductException;

}
