package de.hsrm.mi.swt.Business.UserManagement.Exceptions;

/**
 * Exception for Logins...
 */
public class LoginException extends Exception {
	
	/**
	 * Standard Constructor
	 */
	public LoginException() {
		super();
	}

	/**
	 * Constructur with parameter message
	 * 
	 * @param msg
	 *            contains the message of the exception
	 */
	public LoginException(String msg) {
		super(msg);
	}
}
