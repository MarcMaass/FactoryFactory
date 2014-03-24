package de.hsrm.mi.swt.Business.FactoryManagement.FactoryHelper.enums;

/**
 * Geographic direction
 * 
 */
public enum Direction {
	NORTH(1), EAST(2), SOUTH(3), WEST(4), BOTTOM(-1);

	private int attr;

	private Direction(int attr) {
		this.attr = attr;
	}

	/**
	 * Returns the orientation rotated by 180°
	 * 
	 * @param dir
	 * @return
	 */
	public Direction getOppositeDir() {
		switch (this) {
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		default:
			return null;
		}
	}

	/**
	 * Getter for the directions attribute
	 * 
	 * @return
	 */
	public int getAttr() {
		return attr;
	}

	/**
	 * Returns the orientation rotated by 90°
	 * 
	 * @param dir
	 * @return
	 */
	public Direction getNextDirection() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		default:
			return null;
		}
	}

}
