package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.DrainNode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.Product;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IFactory;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMap;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.ISimulator;

public class Factory implements IFactory, ISimulator {

	private static final long serialVersionUID = -2904385450042965767L;

	
	@Expose
	private FactoryHeader header;
	
	private IMap map;
	private List<INode> packageSources = new ArrayList<INode>();

	// incrementing counter to give each new product a unique ID:
	private int productIDCounter = 0;
	@Expose
	private List<IProduct> products = new ArrayList<IProduct>();
	@Expose
	private boolean isSimulating = false;

	public Factory(long id, String name, int sizex, int sizey, String readGroup, String writeGroup) {
		header = new FactoryHeader(id, name, readGroup, writeGroup, sizex, sizey);
		
		map = new ArrayMap(sizex, sizey);
	}

	@Override
	public String placeMachineUnit(IMachineUnit machine, int x, int y) {
		header.incrementRevision();
		return map.placeMachineUnit(machine, x, y);
	}

	@Override
	public boolean machineUnitIsPlaceable(IMachineUnit machine, int x, int y) {
		header.incrementRevision();
		return map.machineUnitIsPlaceable(machine, x, y);
	}

	@Override
	public boolean deleteMachineUnit(String machineUnitID) {
		header.incrementRevision();
		return map.deleteMachineUnit(machineUnitID);
	}

	@Override
	public ISimulator getSimulator(){
		packageSources = map.getProductSources();
		try {
			return (ISimulator) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public long getId() {
		return header.getID();
	}
	
	public void setName(String newName) {
		header.setName(newName);
	}
	
	public String getName(){
		return header.getName();
	}

	@Override
	public int getRevision() {
		return header.getRevision();
	}

	@Override
	public void start() {
		isSimulating = true;
		this.run();
	}

	@Override
	public void pause() {
		isSimulating = false;
	}

	@Override
	public void stop() {
		pause();
	}

	@Override
	public void run() {
		while (isSimulating) {
			//System.out.println("Heyho, I'm simulating...");

			// WAIT
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			for (INode i : packageSources) {
				IProduct p = new Product("" + productIDCounter++, i.getAcceptedProducts().get(0));
				try {
					i.addProduct(p);
					products.add(p);
				} catch (ProductQueueFullException e) {
					e.printStackTrace();
				} catch (NoAcceptedProductException e) {
					e.printStackTrace();
				}
			}

			for(int i=0; i<products.size(); ++i){ // dirty hack against ConcurrentModificationException when removing Product during loop
				IProduct p = products.get(i);
				try {
					if(p.getNode() instanceof DrainNode){ //if a packet reached the end
						p.getNode().removeProduct();
						products.remove(i);
						--i;
						continue;
					} 
					System.out.println(p.getPosX() + "|" + p.getPosY());
					
					p.getNode().execute();
					
					
					
				} catch (NodeException e) {
				} catch (NullPointerException n){
					n.printStackTrace();
				}
			}

			System.out.println();
			// NOTIFY USERS
		}
	}

	@Override
	public boolean moveMachineUnit(String machineUnitID, int deltax, int deltay) {
		header.incrementRevision();
		return map.moveMachineUnit(machineUnitID, deltax, deltay);
	}
	
	@Override
	public boolean moveMachineUnitTo(String machineUnitID, int x, int y){
		header.incrementRevision();
		return map.moveMachineUnitTo(machineUnitID, x, y);
	}

	@Override
	public boolean turnMachineUnit(String machineUnitID, boolean clockwise) {
		header.incrementRevision();
		return map.turnMachineUnit(machineUnitID, clockwise);
	}

	@Override
	public List<IMachineUnit> getMachines() {
		return map.getMachines();
	}

	@Override
	public String getReadGroup() {
		return header.getReadGroup();
	}

	@Override
	public String getWriteGroup() {
		return header.getWriteGroup();
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	@Override
	public FactoryHeader getHeader() {
		return header;
	}

}
