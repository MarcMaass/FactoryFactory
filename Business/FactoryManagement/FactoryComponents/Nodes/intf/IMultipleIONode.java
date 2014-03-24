package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoOutputNodeAvailableException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

/**
 * Lowest machine element with several in- and outputs
 * 
 */
public interface IMultipleIONode extends INode {

	public static final int MAX_NR_INPUT = 3;
	public static final int MAX_NR_OUTPUT = 3;


	/**
	 * Sends the given product to the next node in the assigned direction
	 * 
	 * @param prod
	 * @param dir
	 * @throws ProductQueueFullException
	 * @throws NoAcceptedProductException
	 */
	public void send(IProduct prod, Direction dir)
			throws ProductQueueFullException, NoAcceptedProductException,
			NoOutputNodeAvailableException;

}
