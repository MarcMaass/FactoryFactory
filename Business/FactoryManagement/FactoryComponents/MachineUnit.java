package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.intf.IMachineUnit;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

public class MachineUnit implements IMachineUnit{

	private static final long serialVersionUID = 428368414130743035L;
	
	private String ID;
	private String type;
	
	private int posx;
	private int posy;
	
	private int width;
	private int height;
	
	private Direction orientation;
	
	private List<INode> nodes;
	private List<Integer> inputNodes;
	private List<Integer> outputNodes;
	
	
	public MachineUnit(String type, int width, int height, List<INode> nodes, List<Integer> inputs, List<Integer> outputs){
		this.type = type;
		
		posx = 0;
		posy = 0;
		
		this.width = width;
		this.height = height;
		
		orientation = Direction.NORTH;
		
		this.inputNodes = inputs;
		this.outputNodes = outputs;
		
		this.nodes = nodes;

	}
	
	@Override
	public int getPosY() {
		return posy;
	}

	@Override
	public int getPosX() {
		return posx;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getTypeID() {
		return type;
	}

	@Override
	public void turn(boolean clockwise) {
		globalToLocal();
		
		// angle around which to rotate:
		double angle;
		int[] transformation;
		
		// swap width/height:
		int temp = width;
		width = height;
		height = temp;
		
		if (clockwise){
			angle = -1.57079633;
			transformation = new int[]{width, 0};
			switch (orientation){
				case NORTH:
					orientation = Direction.EAST;
					break;
				case EAST:
					orientation = Direction.SOUTH;
					break;
				case SOUTH:
					orientation = Direction.WEST;
					break;
				case WEST:
					orientation = Direction.NORTH;
					break;
			default:
				break;
			}
		} else {
			angle = 1.57079633;
			transformation = new int[]{0, -height};
			switch (orientation){
				case NORTH:
					orientation = Direction.WEST;
					break;
				case EAST:
					orientation = Direction.NORTH;
					break;
				case SOUTH:
					orientation = Direction.EAST;
					break;
				case WEST:
					orientation = Direction.SOUTH;
					break;
			default:
				break;
			}
		}
		
		// rotate all Nodes
		for(INode n:nodes){
			int[] pos = n.getPos();

			int x = (int) (Math.cos(angle) * (pos[0]-posx) - Math.sin(angle) * (pos[1]-posy) + posx) + transformation[0];
			int y = (int) (Math.sin(angle) * (pos[0]-posx) + Math.cos(angle) * (pos[1]-posy) + posy) + transformation[1];
		
			n.setPos(x, y);
			
			// rotate Node-IO-ports:
			n.turn(clockwise);
		}
		
		localToGlobal();
	}

	@Override
	public void move(int deltax, int deltay) {
		globalToLocal();
		posx += deltax;
		posy += deltay;
		localToGlobal();
	}

	@Override
	public void setPos(int x, int y) {
		globalToLocal();
		posx = x;
		posy = y;
		localToGlobal();
	}
	

	/**
	 * Transform all Nodes from local to global space
	 * 
	 */
	private void localToGlobal(){
		for(INode n:nodes){
			n.setPos(n.getPos()[0]+posx, n.getPos()[1]+posy);
		}
	}
	
	/**
	 * Transform all Nodes from global to local space
	 *
	 */
	private void globalToLocal(){
		for(INode n:nodes){
			n.setPos(n.getPos()[0]-posx, n.getPos()[1]-posy);
		}
	}

	@Override
	public void setID(String id) {
		ID = id;
	}

	@Override
	public List<INode> getInputNodes() {
		ArrayList<INode> ret = new ArrayList<INode>();
		if(inputNodes != null){
			for(Integer i: inputNodes){
				ret.add(nodes.get(i));
			}
		}
		return ret;
	}

	@Override
	public List<INode> getOutputNodes() {
		ArrayList<INode> ret = new ArrayList<INode>();
		if(outputNodes != null){
			for(Integer i: outputNodes){
				ret.add(nodes.get(i));
			}
		}
		return ret;
	}
	
	@Override 
	public List<INode> getNodes(){
		return nodes;
	}

	@Override
	public boolean isConnected() {
		// check inputs:
		for(INode i: getInputNodes()){
			for(Direction d: i.getInputs().keySet()){
				if(i.getInputs().get(d) == null){
					return false;
				}
			}
		}
		
		// check outputs:
		for(INode i: getOutputNodes()){
			for(Direction d: i.getOutputs().keySet()){
				if(i.getOutputs().get(d) == null){
					return false;
				}
			}
		}
		return true;
	}
}
