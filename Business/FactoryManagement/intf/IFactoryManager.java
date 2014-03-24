package de.hsrm.mi.swt.Business.FactoryManagement.intf;

import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;

public interface IFactoryManager {


	/**
	 * 	
	 * @param width
	 * @param height
	 * @return
	 */
	public IFactory createFactory(IUser user, String name, int width, int height, String readGroup, String writeGroup)  throws Exception;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public IFactory loadFactory(IUser user, long factoryID) throws Exception;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public boolean saveFactory(IUser user, long factoryID) throws Exception;
	
	/**
	 * 
	 * @param factoryID
	 * @return
	 */
	public boolean deleteFactory(IUser user, long factoryID) throws Exception;

	/**
	 * Get the IDs of all Factories
	 * @return a list of the IDs
	 */
	public List<IFactoryHeader> getFactoryList(IUser user) throws Exception ;
	
	
	/************
	 *  EDITOR  *
	 ************/
	
	/**
	 * Places MachineUnit with the given ID at Position x, y.
	 * 
	 * @param factoryID The Factory in which to place the MachineUnit
	 * @param typeID The TypeID of the MachineUnit to place
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean placeMachineUnitByID(IUser user, long factoryID, String typeID, int x, int y) throws Exception;
	
	/**
	 * Places the given MachineUnit at Position x, y.
	 * 
	 * @param factoryID The Factory in which to place the MachineUnit
	 * @param m The MachineUnit to place
	 * @param x
	 * @param y
	 * @return ID of the newly placed machine
	 */
	public String placeMachineUnit(IUser user, long factoryID, IMachineUnit m, int x, int y);
	
	/**
	 * Checks if selected MachineUnitType can be placed at x, y
	 * 
	 * @param factoryID
	 * @param typeID
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean machineUnitIsPlaceable(IUser user, long factoryID, String typeID, int x, int y) throws Exception;
	
	
	/**
	 * Moves the selected MachineUnit to the specified position
	 * @param factoryID the ID of the target factory
	 * @param machineUnitID
	 * @param x x-pos of the machine
	 * @param y y-pos of the machine
	 * @return
	 */
	public boolean moveMachineUnitTo(IUser user, long factoryID, String machineUnitID, int x, int y);
	
	/**
	 * Moves the selected MachineUnit to the specified position
	 * @param factoryID the ID of the target factory
	 * @param machineUnitID
	 * @param deltax x-delta to move the machine
	 * @param deltay y-delta to move the machine
	 * @return
	 */
	public boolean moveMachineUnit(IUser user, long factoryID, String machineUnitID, int deltax, int deltay);
	
	/**
	 * Deletes the selected MachineUnit
	 * 
	 * @param factoryID
	 * @param MachineUnitID
	 * @return
	 */
	public boolean deleteMachineUnit(IUser user, long factoryID, String machineUnitID);
	
	
	

	/***************
	 *  SIMULATOR  *
	 ***************/
	
	/**
	 * creates a new Simulator in his own Thread 
	 * 
	 * @param factoryID
	 * @return
	 */
	public boolean createSimulator(IUser user, long factoryID);
	
	/**
	 * Starts the selected Simulator
	 * 
	 * @param simulatorID
	 * @return
	 */
	public boolean startSimulation(IUser user, long simulatorID);
	
	/**
	 * Pauses the selected Simulator
	 * 
	 * @param simulatorID
	 * @return
	 */
	public boolean pauseSimulation(IUser user, long simulatorID);

	/**
	 * Stops the selected Simulator Thread, Simulator still available
	 * 
	 * @param simulatorID
	 * @return
	 */
	public boolean stopSimulation(IUser user, long simulatorID);
	
	/**
	 * Deletes the selected Simulator
	 * 
	 * @param simulatorID
	 * @return
	 */
	public boolean deleteSimulator(IUser user, long simulatorID);
	
	
	
}
