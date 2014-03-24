package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.MaxNrOfIOReached;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NextNodeFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoChildNodeAllowedException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueEmptyException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

/**
 * Lowest machine element. Nodes are connected between each other to achieve a
 * process-tree-structure
 * 
 */
public interface INode extends Serializable {

	/**
	 * Getter for outputs
	 * 
	 * @return
	 */
	public Map<Direction, INode> getOutputs();

	/**
	 * Getter for inputs
	 * 
	 * @return
	 */
	public Map<Direction, INode> getInputs();

	/**
	 * Checks node for possible input directions and connects the nodes if there
	 * is enough place
	 * 
	 * @param n
	 * @return
	 * @throws MaxNrOfIOReached
	 */
	public boolean setInput(INode n) throws MaxNrOfIOReached,
			NoChildNodeAllowedException;

	/**
	 * Checks node for possible output directions and connects the nodes if
	 * there is enough place
	 * 
	 * @param n
	 * @return
	 * @throws MaxNrOfIOReached
	 */
	public boolean setOutput(INode n) throws MaxNrOfIOReached,
			NoChildNodeAllowedException;

	/**
	 * Removes the node as output
	 * 
	 * @param n
	 */
	public void deleteOutput(INode n);

	/**
	 * Removes the node as input
	 * 
	 * @param n
	 */
	public void deleteInput(INode n);

	/**
	 * Getter for all accepted product types
	 * 
	 * @return
	 */
	public List<String> getAcceptedProducts();

	/**
	 * Add another product type to the nodes' accepted products
	 * 
	 * @param type
	 */
	public void addAcceptedProduct(String type);

	/**
	 * Returns true if the node accept the assigned product type
	 * 
	 * @param prod
	 * @return
	 */
	public boolean accept(IProduct prod);

	/**
	 * Sets the assigned node as a child node, so that several process steps can
	 * be done on just one nodepoint
	 * 
	 * @param n
	 * @return
	 * @throws NoChildNodeAllowedException
	 */
	public boolean setChildNode(INode n) throws NoChildNodeAllowedException;

	/**
	 * Deletes the given node as a child. Existing child nodes of the assigned
	 * node will be reconnected.
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeChildNode(INode n);

	/**
	 * Collects the product in the node-intern product queue if enough capacity
	 * is left
	 * 
	 * @param prod
	 * @return
	 */
	public boolean addProduct(IProduct prod) throws ProductQueueFullException,
			NoAcceptedProductException;

	/**
	 * Removes the next Product
	 * 
	 * @return
	 */
	public IProduct removeProduct() throws ProductQueueEmptyException;

	/**
	 * Returns the actual Product for modifications, but not removes the product
	 * from node
	 * 
	 * @return
	 */
	public IProduct getProduct() throws ProductQueueEmptyException;

	/**
	 * Executes the nodes' logic
	 */
	public void execute() throws NodeException;

	/**
	 * Passes the product to the next Node.
	 */
	public void send(IProduct prod) throws NextNodeFullException,
			NoAcceptedProductException;

	/**
	 * Returns TRUE if the node cannot accept more products at time
	 * 
	 * @return
	 */
	public boolean isFull();

	/**
	 * Set the position of the Node
	 * 
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y);

	/**
	 * Get the position of the Node
	 * 
	 * @param x
	 * @param y
	 */
	public int[] getPos();

	/**
	 * Turns the Node's IO-Ports according to param
	 * 
	 * @param clockwise
	 *            whether to turn clockwise or not
	 */
	public void turn(boolean clockwise);

}
