package de.hsrm.mi.swt.Network.Exceptions;

/**
 * Exception-Class.
 * This Exception should be raised if a Command got an unknown Action-Entry
 * @author medieninf
 *
 */
public class ActionNotFoundException extends Exception {
	/**
	 * Standard Constructor
	 */
	public ActionNotFoundException() {
		super();
	}

	/**
	 * Constructur with parameter message
	 * 
	 * @param msg
	 *            contains the message of the exception
	 */
	public ActionNotFoundException(String msg) {
		super(msg);
	}
}
