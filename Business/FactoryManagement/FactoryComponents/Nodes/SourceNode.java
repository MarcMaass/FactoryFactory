package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;

/**
 * Represents the storage for process-input-products. Only one output is
 * expected. The SourceNode passes the pre-process-products to the next node.
 * 
 */
public class SourceNode extends SingleIONode {

	public SourceNode(int capacity) {
		super(capacity);
	}

	public void execute() throws NodeException{
		// just sending new products to process-line
		send(removeProduct());
	}
}
