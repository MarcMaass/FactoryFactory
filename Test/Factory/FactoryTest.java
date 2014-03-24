package de.hsrm.mi.swt.Test.Factory;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;

public class FactoryTest {

	Factory fac;
	
	@Before
	public void start(){
		fac = new Factory(1, "test", 100, 100, "users", "users");

	}
	
	@Test
	public void placeMachineOverMachine() {
		
		IMachineUnit machine = new MachineUnit("test", 3, 3, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		IMachineUnit machine2 = new MachineUnit("test2", 3, 3, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		
		assertTrue(fac.placeMachineUnit(machine, 1, 1) != null);
		assertFalse(fac.machineUnitIsPlaceable(machine2, 2, 2));
		assertFalse(fac.placeMachineUnit(machine2, 2, 2) != null);
		
		assertTrue(fac.placeMachineUnit(machine2, 4, 4) != null);
	}
	
	
	@Test
	public void turnMachine() {
	try {
		IMachineUnit machine = new MachineUnit("test", 5, 1, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		IMachineUnit machine2 = new MachineUnit("test2", 3, 3, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		
		assertTrue(fac.placeMachineUnit(machine, 1, 1) != null);
		assertFalse(fac.machineUnitIsPlaceable(machine2, 2, 1));
		
		fac.turnMachineUnit("0", true);
		assertTrue(fac.machineUnitIsPlaceable(machine2, 2, 1));
		
		fac.turnMachineUnit("0", false);
		assertFalse(fac.machineUnitIsPlaceable(machine2, 2, 1));
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	@Test
	public void moveMachine() {

	
		IMachineUnit machine = new MachineUnit("test", 5, 1, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		IMachineUnit machine2 = new MachineUnit("test2", 3, 3, new ArrayList<INode>(), new ArrayList<Integer>(), new ArrayList<Integer>());
		
		assertTrue(fac.placeMachineUnit(machine, 1, 1) != null);
		assertFalse(fac.machineUnitIsPlaceable(machine2, 2, 1));
		
		fac.moveMachineUnit("0", 0, 3);
		assertTrue(fac.machineUnitIsPlaceable(machine2, 2, 1));
		
		fac.moveMachineUnit("0", 0, -3);
		assertFalse(fac.machineUnitIsPlaceable(machine2, 2, 1));
	}

}
