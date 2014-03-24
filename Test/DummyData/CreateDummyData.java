package de.hsrm.mi.swt.Test.DummyData;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Factory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.MachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.TransportNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.UserManagement.User;
import de.hsrm.mi.swt.Business.UserManagement.intf.IUser;
import de.hsrm.mi.swt.Persistence.Persistence;
import de.hsrm.mi.swt.Persistence.Exceptions.PersistenceException;

public class CreateDummyData {
	
	private static Persistence p = new Persistence();

	/***************
	 * 
	 * USER
	 * 
	 ***************/

	private static String createHash(String pw) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(pw.getBytes(), 0, pw.length());
		String ret = new BigInteger(1, m.digest()).toString(16);
		return ret;
	}

	public static void generateDummyUser() throws PersistenceException,
			Exception {
		getPersistanceInstance();

		IUser usr = null;
		String[] namen = { "wasili", "dominic", "paul", "steph", "soeren",
				"justin", "marcel", "marc", "tina", "miri", "mario", "martin" };
		Set<String> groups = new HashSet<String>();
		groups.add("users");

		for (String s : namen) {
			p.saveUser(new User(s, createHash(s), 0, groups));
			usr = p.loadUser(s);
			System.out.println("User Added: " + usr.getName() + " - "
					+ usr.getPassword());
		}

	}

	/***************
	 * 
	 * MACHINE
	 * 
	 ***************/

	private static IMachineUnit createDummyTransport(String name, int length)
			throws PersistenceException {

		List<INode> nodes = new ArrayList<INode>();
		INode node = null;
		INode actNode = null;

		for (int i = 0; i < length; i++) {
			// create Node
			actNode = new TransportNode(1);

			// set Node Position
			actNode.setPos(i, 0);

			// set node follower
			if (node != null) {
				try {
					node.setOutput(actNode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			nodes.add(actNode);

			node = actNode;
		}

		ArrayList<Integer> input = new ArrayList<Integer>();
		input.add(0);
		ArrayList<Integer> output = new ArrayList<Integer>();
		input.add(length - 1);

		IMachineUnit mu = new MachineUnit(name + "_" + length, length, 1,
				nodes, input, output);

		return mu;
	}

	public static void generateDummyTransport() throws PersistenceException {
		getPersistanceInstance();

		IMachineUnit mu = null;
		for (int i = 5; i < 9; i++) {
			mu = createDummyTransport("transport", i);
			p.saveMachineUnit(mu);
			p.loadMachineUnit(mu.getTypeID());
			System.out.println("Added MachineUnit: " + mu.getTypeID());

		}

	}

	/***************
	 * 
	 * FACTORY
	 * 
	 ***************/

	public static void generateDummyFactories() throws PersistenceException {
		getPersistanceInstance();

		String name = "fabrik";
		for (int i = 0; i < 5; i++) {
			IFactory f = new Factory(i, name + "_" + i, i * 10, i * 10, "users", "users");
			p.saveFactory(f);
			f = p.loadFactory(name + "_" + i);
			System.out.println("Added Factory: " + f.getId());
		}

	}

	private static void getPersistanceInstance(){
		if (p == null){
			p = new Persistence();
		}
	}
	
	public static void main(String args[]) {

		getPersistanceInstance();
		
		try {
			generateDummyFactories();
			generateDummyTransport();
			generateDummyUser();
		} catch (PersistenceException p) {
			p.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
