package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf;

import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.FactoryHeader;


public interface IFactory extends Serializable, Cloneable {
	/**
	 * Places selected MachineUnitType at Position x, y.
	 * 
	 * @param typeID
	 * @param x
	 * @param y
	 * @return ID of the newly placed machine
	 */
	public String placeMachineUnit(IMachineUnit machine, int x, int y);
	
	/**
	 * Checks if selected MachineUnitType can be placed at x, y
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
	 * Moves the selected MachineUnit to the speciefied position
	 * @param machineUnitID
	 * @return true if possible, false if not. 
	 */
	public boolean moveMachineUnitTo(String machineUnitID, int x, int y);
	
	/**
	 * Turns the selected MachineUnit clockwise (if true), else counter-clockwise
	 * @param machineUnitID
	 * @return
	 */
	public boolean turnMachineUnit(String machineUnitID, boolean clockwise);
	
	/**
	 * 
	 * @return Name of the Factory
	 */
	public String getName();
	
	/**
	 * Get a list of all the contained MachineUnits 
	 * @return
	 */
	public List<IMachineUnit> getMachines();
	
	/**
	 * Returns simulatable Snapshot of the Factory
	 */
	public ISimulator getSimulator();
	
	/**
	 * Returns our ID
	 */
	public long getId();
	
	/**
	 * Returns the current revision of our Factory
	 */
	public int getRevision();
	
	/**
	 * Returns the groupname of the group with read permissions
	 * @return
	 */
	public String getReadGroup();
	
	/**
	 * Returns the groupname of the group with write permissions
	 * @return
	 */
	public String getWriteGroup();
	
	/**
	 * @return the width of the Factory floor
	 */
	public int getWidth();
	
	/**
	 * @return the height of the Factory floor
	 */
	public int getHeight();
	
	/**
	 * 
	 * @return the factory's headerdata
	 */
	public FactoryHeader getHeader();
}
