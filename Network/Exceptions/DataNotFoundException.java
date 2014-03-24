package de.hsrm.mi.swt.Network.Exceptions;

/**
 * This exception should be thrown, if the server doesn't find any data
 * 
 * @author Mario Wandpflug
 * @version 1.0
 * 
 */
public class DataNotFoundException extends Exception {

	/**
	 * Standard Constructor
	 */
	public DataNotFoundException() {
		super();
	}

	/**
	 * Constructur with parameter message
	 * 
	 * @param msg
	 *            contains the message of the exception
	 */
	public DataNotFoundException(String msg) {
		super(msg);
	}
}
