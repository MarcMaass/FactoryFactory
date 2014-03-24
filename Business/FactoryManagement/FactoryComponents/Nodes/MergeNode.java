package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

/**
 * Represents a container. One output but one up to three inputs are expected.
 * The MergeNode collects several products from different inputs, controlled by
 * a clock pulse for input-switching, and passes them to the output.
 * 
 */
public class MergeNode extends MultipleIONode {

	private int currentPos;
	private int counter;
	private int nrChanges;

	public MergeNode(int capacity, int nrChanges) {
		super(capacity);
		counter = 0;
		this.nrChanges = nrChanges;
		// TODO calculating
		currentPos = 0;
	}

	public void execute() {
	}
}
