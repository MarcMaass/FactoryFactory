package de.hsrm.mi.swt.Network.Exceptions;

/**
 * This Exception should be raised if any Part in the Programm want an access to an User who does not exist
 * 
 * @author Justin Albert
 *
 */
public class UserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor with an individual Exception-Message
	 * @param exception
	 */
	public UserException(String exception){
		super(exception);
	}
	
	/**
	 * Standard-Constructor with Exception
	 */
	public UserException(){
		super("User does not exists!");
	}
	
	/**
	 * getter for the Exception-Message
	 * @return Exception Message as String
	 */
	public String getException(){
		return "User does not exists";
	}

}