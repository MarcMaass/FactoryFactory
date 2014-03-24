package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf;

import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;

public interface IMap extends Serializable, Cloneable{
	/**
	 * Places given MachineUnit at Position x, y.
	 * 
	 * @param typeID
	 * @param x
	 * @param y
	 * @return ID of the newly placed machine
	 */
	public String placeMachineUnit(IMachineUnit machine, int x, int y);
	
	/**
	 * Checks if given MachineUnit can be placed at x, y
	 * 
	 * @param typeID
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean machineUnitIsPlaceable(IMachineUnit machine, int x, int y);
	
	/**
	 * Deletes the selected MachineUnit
	 * 
	 * @param MachineUnitID
	 * @return
	 */
	public boolean deleteMachineUnit(String machineUnitID);
	
	/**
	 * Moves the selected MachineUnit according to the deltas
	 * @param machineUnitID
	 * @return
	 */
	public boolean moveMachineUnit(String machineUnitID, int deltax, int deltay);
	
	/**
	 * Moves the selected MachineUnit to the specified position
	 * @param machineUnitID
	 * @param x x-pos of the machine
	 * @param y y-pos of the machine
	 * @return
	 */
	public boolean moveMachineUnitTo(String machineUnitID, int x, int y);
	
	/**
	 * Turns the selected MachineUnit clockwise (if true), else counter-clockwise
	 * @param machineUnitID
	 * @return
	 */
	public boolean turnMachineUnit(String machineUnitID, boolean clockwise);
	
	
	/**
	 * Get all Nodes in which new Products can be placed
	 * (for simulation purposes)
	 * @return
	 */
	public List<INode> getProductSources();
	
	/**
	 * Get a list of all contained MachineUnits
	 * @return
	 */
	public List<IMachineUnit> getMachines();
	
	/**
	 * @return the width of the Factory floor
	 */
	public int getWidth();
	
	/**
	 * @return the height of the Factory floor
	 */
	public int getHeight();
	
}
