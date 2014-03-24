package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

/**
 * Represents a Selector. One input and one up to three outputs are expected.
 * The SortNode passes different products to different outputs, sorted by a
 * criterion.
 * 
 */
public class SortNode extends MultipleIONode {

	public SortNode(int capacity) {
		super(capacity);
	}

	private String sortCriterion;

	public void execute() {
		
	}

}
