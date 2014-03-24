package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import com.google.gson.annotations.Expose;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.MaxNrOfIOReached;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NextNodeFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoAcceptedProductException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoChildNodeAllowedException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueEmptyException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.ProductQueueFullException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.IMultipleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.INode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.intf.ISingleIONode;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Product.intf.IProduct;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

public class Node implements INode {
	@Expose
	protected int posx;
	@Expose
	protected int posy;
	@Expose
	private int capacity;
	
	protected Queue<IProduct> queue;
	protected Map<Direction, INode> inputs;
	protected Map<Direction, INode> outputs;
	protected List<String> acceptedProducts;
	
	public Node(int capacity) {
		queue = new ArrayBlockingQueue<IProduct>(capacity);
		this.capacity = capacity;
		inputs = new HashMap<Direction, INode>();
		outputs = new HashMap<Direction, INode>();
		acceptedProducts = new ArrayList<String>();
	}
	
	public Node(){
		
	}
	
	@Override
	public boolean addProduct(IProduct prod) throws ProductQueueFullException,
			NoAcceptedProductException {
		if (accept(prod)) {
			if (!isFull()) {
				prod.setNode(this);
				queue.offer(prod);
				return true;
			} else {
				throw new ProductQueueFullException();
			}
		} else {
			throw new NoAcceptedProductException();
		}
	}

	@Override
	public IProduct removeProduct() throws ProductQueueEmptyException {
		IProduct prod = queue.poll();
		if (!prod.equals(null)) {
			prod.setNode(null);
			return prod;
		} else {
			throw new ProductQueueEmptyException();
		}
	}

	public IProduct getProduct() throws ProductQueueEmptyException {
		IProduct prod = queue.peek();
		if (prod != null) {
			return prod;
		} else {
			throw new ProductQueueEmptyException();
		}
	}

	@Override
	public boolean isFull() {
		return queue.size() == capacity;
	}

	@Override
	public void execute() throws NodeException {
	}

	@Override
	public void send(IProduct prod) throws NextNodeFullException,
			NoAcceptedProductException {
	}

	@Override
	public void setPos(int x, int y) {
		posx = x;
		posy = y;
	}

	@Override
	public int[] getPos() {
		return new int[] { posx, posy };
	}

	@Override
	public Map<Direction, INode> getOutputs() {
		return outputs;
	}

	@Override
	public Map<Direction, INode> getInputs() {
		return inputs;
	}

	@Override
	public boolean setInput(INode n) throws MaxNrOfIOReached,
			NoChildNodeAllowedException {
		// check if to much inputs are to be set
		int maxI = 4;
		if (this instanceof ISingleIONode) {
			maxI = ISingleIONode.MAX_NR_INPUT;
		} else if (this instanceof IMultipleIONode) {
			maxI = IMultipleIONode.MAX_NR_INPUT;
		}

		// check if node is a potential neighbour
		if (isNeighbour(this, n)) {

			// check if IO is already set
			Direction connecterDir = getNeighbourDirection(this, n);
			if (outputs.containsKey(connecterDir)) {
				return false;
			}

			// no connection if there are already 3 other outputs in different
			// directions (outputs: NORTH, SOUTH, EAST, questioned: WEST)
			if (inputs.size() == maxI && !inputs.containsKey(connecterDir)) {
				throw new MaxNrOfIOReached();
			}

			// remove output on connectingDirection if it isn't connected with n
			if (inputs.containsKey(connecterDir)
					&& !inputs.get(connecterDir).equals(n)) {
				inputs.get(connecterDir).deleteOutput(this);
			} else if (inputs.containsKey(connecterDir)
					&& inputs.get(connecterDir).equals(n)) {
				return true;
			}
			// connect
			inputs.put(connecterDir, n);
			// opposite connection
			n.setOutput(this);
			return true;
		}
		return false;

	}

	@Override
	public boolean setOutput(INode n) throws MaxNrOfIOReached,
			NoChildNodeAllowedException {
		// check if to much inputs are to be set
		int maxO = 4;
		if (this instanceof ISingleIONode) {
			maxO = ISingleIONode.MAX_NR_OUTPUT;
		} else if (this instanceof IMultipleIONode) {
			maxO = IMultipleIONode.MAX_NR_OUTPUT;
		}

		// check for childs
		INode last = this;
		if (hasChild()) {
			last = getLastChild();
		}

		// check if node is a potential neighbour
		if (isNeighbour(last, n)) {

			// check if IO is already set
			Direction connecterDir = getNeighbourDirection(last, n);
			if (last.getInputs().containsKey(connecterDir)) {
				return false;
			}

			// no connection if there are already 3 other outputs in different
			// directions (outputs: NORTH, SOUTH, EAST, questioned: WEST)
			if (last.getOutputs().size() == maxO
					&& !last.getOutputs().containsKey(connecterDir)) {
				throw new MaxNrOfIOReached();
			}

			// remove output on connectingDirection if it isn't connected with n
			if (last.getOutputs().containsKey(connecterDir)
					&& !last.getOutputs().get(connecterDir).equals(n)) {
				last.getOutputs().get(connecterDir).deleteOutput(last);
			} else if (last.getOutputs().containsKey(connecterDir)) {
				return true;
			}

			// connect
			last.getOutputs().put(connecterDir, n);
			// opposite connection
			n.setInput(last);
			return true;
		} else if (isPotentialChild(last, n)) {
			last.setChildNode(n);
			return true;
		}

		return false;
	}

	private boolean isPotentialChild(INode n1, INode n2)
			throws NoChildNodeAllowedException {
		// same Position
		if (n1.getPos()[0] == n2.getPos()[0]
				&& n1.getPos()[1] == n2.getPos()[1]) {
			// must be ISingleIONodes
			if (n1 instanceof ISingleIONode && n2 instanceof ISingleIONode) {
				return true;
			} else {
				throw new NoChildNodeAllowedException();
			}
		}
		return false;
	}

	private boolean isNeighbour(INode n1, INode n2) {
		int x = n1.getPos()[0];
		int y = n1.getPos()[1];

		// x neighbour
		if (y == n2.getPos()[1]
				&& (n2.getPos()[0] + 1 == x || n2.getPos()[0] - 1 == x)) {
			return true;
		}
		// y neighbour
		if (x == n2.getPos()[0]
				&& (n2.getPos()[1] + 1 == y || n2.getPos()[1] - 1 == y)) {
			return true;
		}
		return false;
	}

	private Direction getNeighbourDirection(INode n1, INode n2) {
		if (n2.getPos()[0] + 1 == n1.getPos()[0]) {
			return Direction.WEST;
		}
		if (n2.getPos()[0] - 1 == n1.getPos()[0]) {
			return Direction.EAST;
		}
		if (n2.getPos()[1] + 1 == n1.getPos()[1]) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH;
		}
	}

	private boolean hasChild() {
		if (outputs.containsKey(Direction.BOTTOM)) {
			return true;
		}
		return false;
	}

	private INode getLastChild() {
		INode n = this;
		while (n.getOutputs().containsKey(Direction.BOTTOM)) {
			n = n.getOutputs().get(Direction.BOTTOM);
		}
		return n;
	}

	@Override
	public List<String> getAcceptedProducts() {
		return acceptedProducts;
	}

	@Override
	public void addAcceptedProduct(String type) {
		acceptedProducts.add(type);
	}

	@Override
	public boolean accept(IProduct prod) {
		return acceptedProducts.contains(prod.getType());
	}

	@Override
	public void deleteOutput(INode n) {
		for (Direction d : outputs.keySet()) {
			if (outputs.get(d).equals(n)) {
				outputs.remove(d);
				n.deleteInput(this);
			}
		}

	}

	@Override
	public void deleteInput(INode n) {
		for (Direction d : inputs.keySet()) {
			if (inputs.get(d).equals(n)) {
				inputs.remove(d);
				n.deleteOutput(this);
			}
		}

	}

	@Override
	public boolean setChildNode(INode n) throws NoChildNodeAllowedException {
		if (isPotentialChild(this, n)) {
			Map<Direction, INode> out;
			INode parent;
			if (!hasChild()) {
				out = outputs;
				parent = this;
			} else {
				INode last = getLastChild();
				out = last.getOutputs();
				parent = last;
			}
			// delete all other outputs
			for (Direction d : out.keySet()) {
				out.get(d).deleteInput(parent);
			}
			out.clear();
			// connect child
			out.put(Direction.BOTTOM, n);

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean removeChildNode(INode n) {
		if (hasChild()) {
			INode parent = this;
			// search parent node
			while (!parent.getOutputs().get(Direction.BOTTOM).equals(n)) {
				parent = parent.getOutputs().get(Direction.BOTTOM);
			}
			if (((Node) n).hasChild()) {
				// connect parent with childChild
				parent.getOutputs().put(Direction.BOTTOM,
						n.getOutputs().get(Direction.BOTTOM));
			} else {
				// delete Child from outputs
				parent.getOutputs().remove(Direction.BOTTOM);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void turn(boolean clockwise) {
		Map<Direction, INode> newinputs = new HashMap<Direction, INode>();
		Map<Direction, INode> newoutputs = new HashMap<Direction, INode>();
		
		// turn inputs:
		for(Direction d:inputs.keySet()){
			switch (d) {
			case NORTH:
				if(clockwise){
					newinputs.put(Direction.EAST, inputs.get(d));
				} else {
					newinputs.put(Direction.WEST, inputs.get(d));
				}
				break;
			case EAST:
				if(clockwise){
					newinputs.put(Direction.SOUTH, inputs.get(d));
				} else {
					newinputs.put(Direction.NORTH, inputs.get(d));
				}
				break;
			case SOUTH:
				if(clockwise){
					newinputs.put(Direction.WEST, inputs.get(d));
				} else {
					newinputs.put(Direction.EAST, inputs.get(d));
				}
				break;
			case WEST:
				if(clockwise){
					newinputs.put(Direction.NORTH, inputs.get(d));
				} else {
					newinputs.put(Direction.SOUTH, inputs.get(d));
				}
				break;
			default:
				break;
			}
		}
		inputs = newinputs;
		
		// turn outputs:
		for(Direction d:outputs.keySet()){
			switch (d) {
			case NORTH:
				if(clockwise){
					newoutputs.put(Direction.EAST, inputs.get(d));
				} else {
					newoutputs.put(Direction.WEST, inputs.get(d));
				}
				break;
			case EAST:
				if(clockwise){
					newoutputs.put(Direction.SOUTH, inputs.get(d));
				} else {
					newoutputs.put(Direction.NORTH, inputs.get(d));
				}
				break;
			case SOUTH:
				if(clockwise){
					newoutputs.put(Direction.WEST, inputs.get(d));
				} else {
					newoutputs.put(Direction.EAST, inputs.get(d));
				}
				break;
			case WEST:
				if(clockwise){
					newoutputs.put(Direction.NORTH, inputs.get(d));
				} else {
					newoutputs.put(Direction.SOUTH, inputs.get(d));
				}
				break;
			default:
				break;
			}
		}
		outputs = newoutputs;
	}

}
