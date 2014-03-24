package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.SourceNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.MaxNrOfIOReached;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoChildNodeAllowedException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMap;

public class ArrayMap implements IMap {

	private static final long serialVersionUID = -9131751857276580580L;

	private int sizex;
	private int sizey;

	private IMachineUnit[][] map;
	private Map<String, IMachineUnit> machines = new HashMap<String, IMachineUnit>();

	private int uniqueID = 0;

	private List<INode> packageSources = new ArrayList<INode>();

	public ArrayMap(int sizex, int sizey) {
		this.sizex = sizex;
		this.sizey = sizey;

		map = new IMachineUnit[this.sizex][this.sizey];
	}

	@Override
	public String placeMachineUnit(IMachineUnit machine, int posx, int posy) {
		if (machineUnitIsPlaceable(machine, posx, posy)) {

			machine.setID("m" + uniqueID++);

			machine.setPos(posx, posy);
			machines.put(machine.getID(), machine);
			placeOnMap(machine);

			// check for packagesources:
			for (INode i : machine.getNodes()) {
				if (i instanceof SourceNode) {
					packageSources.add(i);
				}
			}

			connect(machine);

			return machine.getID();
		}
		return null;
	}

	@Override
	public boolean machineUnitIsPlaceable(IMachineUnit machine, int posx,
			int posy) {
		for (int x = posx; x < machine.getWidth(); x++) {
			for (int y = posy; y < machine.getHeight(); y++) {
				if (map[x][y] != null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean deleteMachineUnit(String machineUnitID) {
		IMachineUnit m = machines.get(machineUnitID);

		if (m != null) {
			disconnect(m);
			removeFromMap(m);

			// check for packagesources:
			for (INode i : m.getNodes()) {
				if (i instanceof SourceNode) {
					packageSources.remove(i);
				}
			}

			machines.remove(machineUnitID);

			return true;
		}
		return false;
	}

	@Override
	public boolean moveMachineUnit(String machineUnitID, int deltax, int deltay) {
		IMachineUnit m = machines.get(machineUnitID);

		if (machineUnitIsPlaceable(m, m.getPosX() + deltax, m.getPosY()
				+ deltay)) {
			disconnect(m);

			removeFromMap(m);
			m.move(deltax, deltay);
			placeOnMap(m);

			connect(m);

			return true;
		}
		return false;
	}

	@Override
	public boolean moveMachineUnitTo(String machineUnitID, int x, int y) {
		IMachineUnit m = machines.get(machineUnitID);
		return moveMachineUnit(machineUnitID, x - m.getPosX(), y - m.getPosY());
	}

	@Override
	public boolean turnMachineUnit(String machineUnitID, boolean clockwise) {
		IMachineUnit m = machines.get(machineUnitID);

		boolean success = true;

		disconnect(m);

		removeFromMap(m);
		m.turn(clockwise);
		if (!machineUnitIsPlaceable(m, m.getPosX(), m.getPosY())) {
			m.turn(!clockwise);
			success = false;
		}
		placeOnMap(m);

		connect(m);

		return success;
	}

	@Override
	public List<INode> getProductSources() {
		return packageSources;
	}

	/**
	 * Check if the given MachineUnit fits the inputs/outputs of the surrounding
	 * MachineUnits and connect them
	 * 
	 * @param m
	 *            MachineUnit to connect
	 * @return
	 */
	private boolean connect(IMachineUnit m) {
		// check inputs:
		IMachineUnit m2;
		for (INode i : m.getInputNodes()) {
			int[] pos = i.getPos();
			// check for other machines around:
			m2 = map[Math.max(0, Math.min(sizex, pos[0]))][Math.max(0,
					Math.min(sizey, pos[1] - 1))]; // north
			if (m2 != null && m2 != m) {
				for (INode o : m2.getOutputNodes()) {
					try {
						i.setInput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0] + 1))][Math.max(0,
					Math.min(sizey, pos[1]))]; // east
			if (m2 != null && m2 != m) {
				for (INode o : m2.getOutputNodes()) {
					try {
						i.setInput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0]))][Math.max(0,
					Math.min(sizey, pos[1] + 1))]; // south
			if (m2 != null && m2 != m) {
				for (INode o : m2.getOutputNodes()) {
					try {
						i.setInput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0] - 1))][Math.max(0,
					Math.min(sizey, pos[1]))]; // west
			if (m2 != null && m2 != m) {
				for (INode o : m2.getOutputNodes()) {
					try {
						i.setInput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
		}

		// check outputs:
		for (INode i : m.getOutputNodes()) {
			int[] pos = i.getPos();
			// check for other machines around:
			m2 = map[Math.max(0, Math.min(sizex, pos[0]))][Math.max(0,
					Math.min(sizey, pos[1] - 1))]; // north
			if (m2 != null && m2 != m) {
				for (INode o : m2.getInputNodes()) {
					try {
						i.setOutput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0] + 1))][Math.max(0,
					Math.min(sizey, pos[1]))]; // east
			if (m2 != null && m2 != m) {
				for (INode o : m2.getInputNodes()) {
					try {
						i.setOutput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0]))][Math.max(0,
					Math.min(sizey, pos[1] + 1))]; // south
			if (m2 != null && m2 != m) {
				for (INode o : m2.getInputNodes()) {
					try {
						i.setOutput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
			m2 = map[Math.max(0, Math.min(sizex, pos[0] - 1))][Math.max(0,
					Math.min(sizey, pos[1]))]; // west
			if (m2 != null && m2 != m) {
				for (INode o : m2.getInputNodes()) {
					try {
						i.setOutput(o);
					} catch (MaxNrOfIOReached e) {
					} catch (NoChildNodeAllowedException e) {
					}
				}
			}
		}
		return m.isConnected();
	}

	/**
	 * disconnects a Machine from its neighbours
	 * 
	 * @param m
	 *            the Machine to disconnect
	 */
	private void disconnect(IMachineUnit m) {

		// inputs:
		for (INode n : m.getInputNodes()) {
			for (INode n2 : n.getInputs().values()) {
				n.deleteInput(n2);
			}
		}

		// outputs:
		for (INode n : m.getOutputNodes()) {
			for (INode n2 : n.getOutputs().values()) {
				n.deleteOutput(n2);
			}
		}
	}

	/**
	 * Removes (lifts) the given machine from the map (not from the factory!)
	 * 
	 * @param m
	 *            The IMachineUnit to remove (lift)
	 */
	private void removeFromMap(IMachineUnit m) {
		for (int mx = m.getPosX(); mx < m.getPosX() + m.getWidth(); mx++) {
			for (int my = m.getPosY(); my < m.getPosY() + m.getHeight(); my++) {
				map[mx][my] = null;
			}
		}
	}

	/**
	 * Places the given machine on the map
	 * 
	 * @param m
	 *            The IMachineUnit to place
	 */
	private void placeOnMap(IMachineUnit m) {
		for (int mx = m.getPosX(); mx < m.getPosX() + m.getWidth(); mx++) {
			for (int my = m.getPosY(); my < m.getPosY() + m.getHeight(); my++) {
				map[mx][my] = m;
			}
		}
	}

	@Override
	public List<IMachineUnit> getMachines() {
		List<IMachineUnit> ms = new ArrayList<IMachineUnit>();
		for (IMachineUnit im : machines.values()) {
			ms.add(im);
		}
		return ms;
	}

	@Override
	public int getWidth() {
		return sizex;
	}

	@Override
	public int getHeight() {
		return sizey;
	}

}
