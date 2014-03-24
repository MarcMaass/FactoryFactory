package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

/**
 * Represents the dispatch of process-output-products. Just one input is
 * expected. The DrainNode collects the finished products.
 * 
 */
public class DrainNode extends SingleIONode {
	public DrainNode(int capacity) {
		super(capacity);
	}

	public void execute() {
		// do nothing, because there is no output
	}
}
