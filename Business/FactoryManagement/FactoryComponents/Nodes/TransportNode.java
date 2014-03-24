package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;

/**
 * Represents a conveyor. Exactly one input and one output are expected. The
 * TransportNode only passes products.
 * 
 */
public class TransportNode extends SingleIONode {

	public TransportNode(int capacity) {
		super(capacity);
	}

	public void execute() throws NodeException{
		send(removeProduct());
	}
}
