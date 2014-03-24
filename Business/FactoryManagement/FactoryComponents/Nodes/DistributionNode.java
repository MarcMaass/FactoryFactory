package de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes;

import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NoOutputNodeAvailableException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryComponents.Nodes.Exceptions.NodeException;
import de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums.Direction;

/**
 * Represents a switch. One input and one to three outputs are expected. The
 * DistributionNode disposes products to several outputs. Therefore the clock
 * pulse for switching outputs can be set.
 * 
 */
public class DistributionNode extends MultipleIONode {

	private int currentPos;
	private int counter;
	private int nrChanges;

	public DistributionNode(int capacity, int nrChanges) {
		super(capacity);
		counter = 0;
		this.nrChanges = nrChanges;
		currentPos = Direction.NORTH.getAttr();
	}

	public void execute() throws NodeException {
		// calculate available output
		Direction output;
		output = getCurrentOutputDirection();

		if ((counter != 0) && (counter % nrChanges == 0)) {
			// switch output position
			currentPos = output.getNextDirection().getAttr();
			output = getCurrentOutputDirection();
		}

		send(removeProduct(), output);
		counter++;
	}

	private Direction getCurrentOutputDirection()
			throws NoOutputNodeAvailableException {

		if (getOutputs().size() == 0) {
			throw new NoOutputNodeAvailableException();
		}

		while (true) {
			if (currentPos == Direction.NORTH.getAttr()) {
				if (getOutputs().containsKey((Direction.NORTH))) {
					return Direction.NORTH;
				} else {
					currentPos = Direction.NORTH.getNextDirection().getAttr();
				}
			}
			if (currentPos == Direction.EAST.getAttr()) {
				if (getOutputs().containsKey((Direction.EAST))) {
					return Direction.EAST;
				} else {
					currentPos = Direction.SOUTH.getAttr();
				}
			}
			if (currentPos == Direction.SOUTH.getAttr()) {
				if (getOutputs().containsKey((Direction.SOUTH))) {
					return Direction.SOUTH;
				} else {
					currentPos = Direction.WEST.getAttr();
				}
			}
			if (currentPos == Direction.WEST.getAttr()) {
				if (getOutputs().containsKey((Direction.WEST))) {
					return Direction.WEST;
				} else {
					currentPos = Direction.NORTH.getAttr();
				}
			}
		}
	}

}
