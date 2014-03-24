package de.hsrm.mi.swt.Network.Exceptions;

/**
 * This exception should be thrown, if the server doesn't know the network
 * command
 * 
 * @author Mario Wandpflug
 * @version 1.0
 * 
 */
public class CommandNotFoundException extends Exception {

	/**
	 * Standard Constructor
	 */
	public CommandNotFoundException() {
		super();
	}

	/**
	 * Constructur with parameter message
	 * 
	 * @param msg
	 *            contains the message of the exception
	 */
	public CommandNotFoundException(String msg) {
		super(msg);
	}
}
