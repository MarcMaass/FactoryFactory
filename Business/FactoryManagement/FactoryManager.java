package de.hsrm.mi.swt.Business.FactoryManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.Business.FactoryManagement.Exceptions.FactoryNotFoundException;
import de.hsrm.mi.swt.Business.FactoryManagement.Exceptions.NameInUseException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactoryHeader;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.ISimulator;
import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.intf.IPersistence;

public class FactoryManager implements IFactoryManager {

	private IPersistence persistence;

	private Map<Long, IFactory> factories;
	private Map<Long, ISimulator> simulators;

	public FactoryManager() {
		persistence = new Persistence();

		factories = new HashMap<Long, IFactory>();
		simulators = new HashMap<Long, ISimulator>();
	}

	// TODO Check if Fac exists, Save dummyFac, What if createFac fails?
	@Override
	public IFactory createFactory(IUser user, String name, int width, int height, String readGroup, String writeGroup) throws Exception {

		if (persistence.getFactories().contains(name)) {
			throw new NameInUseException("Name Is Already In Use");
		}
		long id = name.hashCode() + System.currentTimeMillis();
		IFactory f = new Factory(id, name, width, height, readGroup, writeGroup);
		
		factories.put(id, f);
		persistence.saveFactory(f);

		return f;
	}

	@Override
	public IFactory loadFactory(IUser user, long factoryID) throws Exception {
		// if Factory is already loaded, do nothing
		if (factories.containsKey(factoryID)) {
			return factories.get(factoryID);
		}

		IFactory f = persistence.loadFactory(String.valueOf(factoryID));
		factories.put(factoryID, f);
		return f;
	}

	@Override
	public boolean saveFactory(IUser user, long factoryID) throws Exception {
		if (!factories.containsKey(factoryID)) {
			throw new FactoryNotFoundException("Factory Could Not Be Saved");
		}
		return persistence.saveFactory(factories.get(factoryID));
	}

	@Override
	public boolean deleteFactory(IUser user, long factoryID) throws Exception {
		//TODO Shut down whole Factory?!
		
		if (factories.containsKey(factoryID)) {
			factories.remove(factoryID);
		}
		return persistence.deleteFactory(String.valueOf(factoryID));
	}

	@Override
	public List<IFactoryHeader> getFactoryList(IUser user) throws Exception {
		return persistence.getFactories();
	}

	@Override
	public boolean placeMachineUnitByID(IUser user, long factoryID, String typeID, int x, int y) throws Exception {
		IMachineUnit m = persistence.loadMachineUnit(typeID);

		return factories.get(factoryID).placeMachineUnit(m, x, y) != null;
	}

	@Override
	public String placeMachineUnit(IUser user, long factoryID, IMachineUnit m, int x, int y) {
		return factories.get(factoryID).placeMachineUnit(m, x, y);
	}

	@Override
	public boolean machineUnitIsPlaceable(IUser user, long factoryID, String typeID, int x, int y) throws Exception {
		IMachineUnit m = persistence.loadMachineUnit(typeID);

		return factories.get(factoryID).machineUnitIsPlaceable(m, x, y);
	}

	@Override
	public boolean deleteMachineUnit(IUser user, long factoryID, String machineUnitID) {
		if (!factories.containsKey(factoryID)) { // invalid factoryID
			return false;
		}

		return factories.get(factoryID).deleteMachineUnit(machineUnitID);
	}

	@Override
	public boolean createSimulator(IUser user, long factoryID) {
		if (!factories.containsKey(factoryID)) { // invalid factoryID
			return false;
		}

		ISimulator s = factories.get(factoryID).getSimulator();
		if (s != null) {
			simulators.put(factoryID, factories.get(factoryID).getSimulator());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean startSimulation(IUser user, long simulatorID) {
		if (!simulators.containsKey(simulatorID)) { // invalid simulatorID
			return false;
		}

		simulators.get(simulatorID).start();
		return true;
	}

	@Override
	public boolean pauseSimulation(IUser user, long simulatorID) {
		if (!simulators.containsKey(simulatorID)) { // invalid simulatorID
			return false;
		}

		simulators.get(simulatorID).pause();
		return true;
	}

	@Override
	public boolean stopSimulation(IUser user, long simulatorID) {
		if (!simulators.containsKey(simulatorID)) { // invalid simulatorID
			return false;
		}

		simulators.get(simulatorID).stop();
		return true;
	}

	@Override
	public boolean deleteSimulator(IUser user, long simulatorID) {
		if (!simulators.containsKey(simulatorID)) { // simulator already
													// deleted?
			return true;
		}

		stopSimulation(user, simulatorID);
		simulators.remove(simulatorID);
		return false;
	}

	@Override
	public boolean moveMachineUnitTo(IUser user, long factoryID,
			String machineUnitID, int x, int y) {
		if (!factories.containsKey(factoryID)) { // invalid factoryID
			return false;
		}

		return factories.get(factoryID).moveMachineUnitTo(machineUnitID, x, y);
	}

	@Override
	public boolean moveMachineUnit(IUser user, long factoryID, String machineUnitID, int deltax, int deltay) {
		if (!factories.containsKey(factoryID)) { // invalid factoryID
			return false;
		}

		return factories.get(factoryID).moveMachineUnit(machineUnitID, deltax,
				deltay);
	}

}
