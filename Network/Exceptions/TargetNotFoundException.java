package de.hsrm.mi.swt.Network.Exceptions;

/**
 * This Exception should be raised if the Command got an unknown Target-Entry
 * 
 * @author Justin Albert
 *
 */
public class TargetNotFoundException extends Exception {

	/**
	 * Standard Constructor
	 */
	public TargetNotFoundException() {
		super();
	}

	/**
	 * Constructur with parameter message
	 * 
	 * @param msg
	 *            contains the message of the exception
	 */
	public TargetNotFoundException(String msg) {
		super(msg);
	}
}
