package de.hsrm.mi.swt.Test.Persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

@RunWith(MockitoJUnitRunner.class)
public class PersistenceMachineUnitTest {

	IMachineUnit mac;
	String macID;
	
	IMachineUnit mac2;
	String macID2;
	
	List<Integer> io;
	List<INode> nodes;

	Persistence pers;

	@Before
	public void start(){
		pers = new Persistence();
		
		macID = "a2345";
		macID2 = "b2345";
		
		io = new ArrayList<Integer>();
		nodes = new ArrayList<INode>();

		
		mac = new MachineUnit(macID, 100, 100, nodes, io, io);
		mac2 = new MachineUnit(macID2, 100, 100, nodes, io, io);

	}
	
	@Test
	public void saveLoadMachineUnit() {
		try {
			pers.saveMachineUnit(mac);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MachineUnit mac3 = null;
		try {
			mac3 = (MachineUnit)pers.loadMachineUnit(macID);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotSame(mac, mac3);
		assertEquals(mac.getTypeID(), mac3.getTypeID());
	}
	
	
	@Test
	public void getMachineUnitList() {
		
		try {
			pers.saveMachineUnit(mac);
			pers.saveMachineUnit(mac2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<String> macList = null;
		try {
			macList = pers.getMachineUnits();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		assertTrue(macList.contains(macID));
		assertTrue(macList.contains(macID2));
		
	}
	
	@Test
	public void deleteMachineUnit() {
		
		// save MachineUnit and compare ID in FactopryList
		try {
			pers.saveMachineUnit(mac);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> macList = null;
		try {
			macList = pers.getMachineUnits();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(macList.contains(macID));
		
		// delete MachineUnit now, ID shouldn't be in MachineUnitList
		try {
			pers.deleteMachineUnit(macID);
			macList = pers.getMachineUnits();
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertFalse(macList.contains(macID));
		
	}

	@After
	public void stop(){
		try {
			pers.deleteMachineUnit(macID);
			pers.deleteMachineUnit(macID2);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
