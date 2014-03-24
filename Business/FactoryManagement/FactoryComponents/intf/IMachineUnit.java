package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf;

import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;

public interface IMachineUnit extends Serializable, Cloneable {

	
	/**
	 * Turns us clockwise (if clockwise == true), else counter-clockwise.
	 * All positions of our Nodes have to be recalculated!
	 * 
	 * @param clockwise
	 */
	public void turn(boolean clockwise);
	
	/**
	 * Move us according to the deltas.
	 * All positions of our Nodes have to be recalculated!
	 * 
	 * @param deltax
	 * @param deltay
	 */
	public void move(int deltax, int deltay);
	
	/**
	 * Get the Y-component of our position
	 * @return
	 */
	public int getPosY();
	
	/**
	 * Set our Position to x/y
	 * All positions of our Nodes have to be recalculated!
	 * 
	 * @param deltax
	 * @param deltay
	 */
	public void setPos(int x, int y);
	
	/**
	 * Get the x-component of our position
	 * @return
	 */
	public int getPosX();
	
	/**
	 * Get our current width, dependent on our orientation
	 * @return
	 */
	public int getWidth();
	
	/**
	 * Get our current height, dependent on our orientation
	 * @return
	 */
	public int getHeight();
	
	
	/**
	 * Get our own individual ID
	 * @return
	 */
	public String getID();
	
	
	/**
	 * Set our individual ID
	 */
	public void setID(String id);
	
	/**
	 * Get our TypeID
	 * @return
	 */
	public String getTypeID();
	
	/**
	 * Get all Inputs
	 * 
	 * @return
	 */
	public List<INode> getInputNodes();
	
	/**
	 * Get all Outputs
	 * 
	 * @return
	 */
	public List<INode> getOutputNodes();
	
	/**
	 * Get all nodes
	 * @return
	 */
	public List<INode> getNodes();
	
	/**
	 * Are all Inputs/Outputs of the machine connected?
	 * @return
	 */
	public boolean isConnected();
}
