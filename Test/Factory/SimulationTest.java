package de.hsrm.mi.swt.Test.Factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryManager;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.DrainNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SourceNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.intf.IFactoryManager;
import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;

public class SimulationTest {

	IFactoryManager fm;
	
	@Before
	public void setup() {
		fm = new FactoryManager();
	}
	
	@Test
	public void test() throws Exception {
		INode n1 = new SourceNode(1);
		INode n2 = new TransportNode(1);
		INode n3 = new TransportNode(1);
		INode n4 = new TransportNode(1);
		INode n5 = new TransportNode(1);
		INode n6 = new TransportNode(1);
		INode n7 = new TransportNode(1);
		INode n8 = new DrainNode(1);

		n1.setPos(0, 0);
		n2.setPos(1, 0);
		n3.setPos(2, 0);
		n4.setPos(3, 0);
		n5.setPos(4, 0);
		n6.setPos(5, 0);
		n7.setPos(6, 0);
		n8.setPos(7, 0);

		try {
			n1.setOutput(n2);
			n2.setOutput(n3);
			n3.setOutput(n4);
			n4.setOutput(n5);
			n5.setOutput(n6);
			n6.setOutput(n7);
			n7.setOutput(n8);
		} catch (Exception e){
			e.printStackTrace();
		}

		n1.addAcceptedProduct("teddy");
		n2.addAcceptedProduct("teddy");
		n3.addAcceptedProduct("teddy");
		n4.addAcceptedProduct("teddy");
		n5.addAcceptedProduct("teddy");
		n6.addAcceptedProduct("teddy");
		n7.addAcceptedProduct("teddy");
		n8.addAcceptedProduct("teddy");
		
		List<INode> nodes = new ArrayList<INode>();
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);
		nodes.add(n5);
		nodes.add(n6);
		nodes.add(n7);
		nodes.add(n8);

		
		IMachineUnit mu = new MachineUnit("testmachine", 3, 1, nodes, null, null);
		Set<String> groups = new HashSet<String>();
		groups.add("users");
		IUser u = new User();//"test", "123", groups);
		
		fm.createFactory(u, "test", 100, 100, "users", "users");
		fm.placeMachineUnit(u, 1, mu, 0, 0);
		
		fm.createSimulator(u, 1);
		fm.startSimulation(u, 1);
	}

}
